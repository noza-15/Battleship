import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private BufferedReader in;
    private PrintWriter out;
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;
    private String inputName;
    private int jobCode;
    private int groupNo;
    private Player player;
    private int clientID;
    private CommandHandler cmd;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** レーダー作戦ゲームβ ***");
        Client client = new Client();
        client.initialize();
    }

    private void initialize() {
        Scanner scanner = new Scanner(System.in);
        try {
            establishConnection();
            setGroup();
            setName();
            setJob();

            clientID = cmd.receiveInt();
            System.out.println("グループ" + (groupNo + 1) + "に参加しました。");
            System.out.println(cmd.receiveString());
            System.out.println(cmd.receiveString());
            switch (jobCode) {
                case Server.ATTACKER:
                    player = new Attacker(inputName, cmd);
                    break;
                case Server.BYSTANDER:
                    player = new Bystander(inputName);
                    break;
            }
            player.newGame();
            player.nextTurn();

            scanner.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("プレイヤー名を入力してください。");
        inputName = scanner.next();

        out.println(inputName);
    }

    private void setJob() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("役割を選んでください。\nAttacker(攻撃者), Bystander(傍観者)");
        switch (scanner.next().toLowerCase()) {
            case "a":
            case "attacker":
                jobCode = Server.ATTACKER;
                player = new Attacker(inputName);
                System.out.println("Attacker(攻撃者)が選択されました。");
                break;
            case "b":
            case "bystander":
                jobCode = Server.BYSTANDER;
                player = new Bystander(inputName);
                System.out.println("Bystander(傍観者)が選択されました。");
                break;
            default:
                System.out.println("AかBを入力してください。");
                setJob();
                break;
        }
        cmd.send(jobCode);
    }

    private void setGroup() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("操作を選択してください。");
        System.out.println("1: 新規グループを作成");
        out.println(Server.DOES_EXIST_GROUP);
        int groupCnt = Integer.parseInt(in.readLine());
        if (groupCnt > 0) {
            System.out.println("2: 既存グループに参加");
        }
        int op;
        while ((op = scanner.nextInt()) != 1 && op != 2) {
            System.out.println("数字を入力してください。");
        }
        groupNo = 0;
        switch (op + 10) {
            case Server.NEW_GROUP:
                cmd.send(Server.NEW_GROUP);
                groupNo = cmd.receiveInt();
                System.out.println("グループ" + (groupNo + 1) + "を作成します。");
                break;
            case Server.LIST_GROUP:
                cmd.send(Server.LIST_GROUP);
                int total = cmd.receiveInt();
                System.out.println("グループが" + groupCnt + "個あります。グループを選んでください。");
                for (int i = 0; i < total; i++) {
                    System.out.println(in.readLine());
                }
                // TODO: 存在しないグループを選べてしまう
                while ((groupNo = scanner.nextInt() - 1) + 1 > groupCnt && (groupNo) < 0) {
                    System.out.println("正しい番号を選んでください");
                }
                ;
                break;
        }
        cmd.send(Server.REGISTER);
        cmd.send(groupNo);
    }

    private void establishConnection() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("サーバーのアドレスを入力してください。");
        String inputAddr = scanner.nextLine();
        InetAddress address = null;
        try {
            address = InetAddress.getByName(inputAddr);
        } catch (UnknownHostException e) {
            System.err.println("サーバーが見つかりませんでした。");
            System.exit(1);
        }
        try {
            Socket socket = new Socket(address, Server.PORT_NO);
            System.out.println("socket = " + socket);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(inputStream));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)), true);
            cmd = new CommandHandler(in, out);
            //            objIn = new ObjectInputStream(inputStream);
//            objOut = new ObjectOutputStream(outputStream);
//            objOut.writeObject(new Player("test"));

        } catch (IOException e) {
            System.err.println("サーバー接続中にエラーが発生しました。");
            System.exit(1);
        }
    }
}

class CommandHandler {
    private BufferedReader in;
    private PrintWriter out;

    CommandHandler(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    void send(String command) {
        out.print(command);
    }

    void send(int command) {
        out.print(command);
    }

    int receiveInt() {
        try {
            return Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    String receiveString() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
    }
}
