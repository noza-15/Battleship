import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    BufferedReader in;
    PrintWriter out;
    Client client;
    private String inputName;
    private int jobCode;
    private int groupNo;
    private Player player;
    private int clientID;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("***レーダー作戦ゲームβ***");
        Client client = new Client();
        client.init();
    }

    private void init() {
        Scanner scanner = new Scanner(System.in);
        try {
            connectionBuilder();
            chooseGroup();
            setName();
            chooseJob();

//            client.clientID = Integer.parseInt((in.readLine()));
            System.out.println(in.readLine());
            out.print("");
            scanner.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("名前を入力してください。");
        inputName = scanner.next();
        out.println(inputName);
    }

    private void chooseJob() {
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
                chooseJob();
                break;
        }
        out.println(jobCode);
    }

    private void chooseGroup() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("操作を選択してください。\n" +
                "1) 新規グループを作成\n" +
                "2) 既存グループに参加");
        int op;
        while ((op = scanner.nextInt()) != 1 && op != 2) {
            System.out.println("1か2を入力してください。");
        }
        groupNo = 0;
        switch (op + 10) {
            case Server.NEW_GROUP:
                out.println(Server.NEW_GROUP);
                groupNo = Integer.parseInt(in.readLine());
                break;
            case Server.LIST_GROUP:
                out.println(Server.LIST_GROUP);
                int total = Integer.parseInt(in.readLine());
                for (int i = 0; i < total; i++) {
                    System.out.println(in.readLine());
                }
                System.out.println("グループを選んでください。");
                groupNo = scanner.nextInt() - 1;
                break;
        }
        out.println(Server.REGISTER);
        out.println(groupNo);
    }

    private void connectionBuilder()throws Exception{
        System.out.println("サーバーのアドレスを入力してください。");
        Scanner scanner = new Scanner(System.in);
        String inputAddr = scanner.nextLine();
        InetAddress address = InetAddress.getByName(inputAddr);
        Socket socket = new Socket(address, Server.PORT_NO);
        System.out.println("socket = " + socket);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }
}
