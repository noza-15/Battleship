import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private Scanner scanner;

    private int groupID;
    private Player player;
    private CommandHandler cmd;

    public static void main(String[] args) {
        System.out.println("\u001b[34m*** レーダー作戦ゲームβ ***\u001b[0m"); //TODO: GUI
        Client client = new Client();
        client.initialize();
        client.play();
    }

    private void initialize() {
        scanner = new Scanner(System.in);
        establishConnection();
        setGroup();
        setPlayer();
        register();
    }

    private void play() {
        player.waitStart();
        player.newGame();
        player.nextTurn();
    }

    private void establishConnection() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("サーバーのアドレスを入力してください。"); //TODO: GUI
        String inputAddr = scanner.nextLine();
        InetAddress address = null;
        try {
            address = InetAddress.getByName(inputAddr);
        } catch (UnknownHostException e) {
            System.err.println("サーバーが見つかりませんでした。"); //TODO: GUI
            System.exit(1);
        }
        try {
            Socket socket = new Socket(address, Server.PORT_NO);
            System.out.println("socket = " + socket); //TODO: GUI
            cmd = new CommandHandler(socket);
        } catch (IOException e) {
            System.err.println("サーバー接続中にエラーが発生しました。"); //TODO: GUI
            System.exit(1);
        }
    }

    private void setPlayer() {
        //TODO: GUI
        System.out.println("プレイヤー名を入力してください。");
        String inputName = scanner.next();
        System.out.println("役割を選んでください。\nAttacker(攻撃者), Bystander(傍観者)");
        switch (scanner.next().toLowerCase()) {
            case "a":
            case "attacker":
                player = new Attacker(inputName, cmd);
                player.setJobCode(Server.ATTACKER);
                System.out.println("Attacker(攻撃者)が選択されました。");
                break;
            case "b":
            case "bystander":
                player = new Bystander(inputName, cmd);
                player.setJobCode(Server.BYSTANDER);
                System.out.println("Bystander(傍観者)が選択されました。");
                break;
            default:
                System.out.println("AかBを入力してください。");
                setPlayer();
                break;
        }
    }

    private void register() {
        cmd.send(Server.REGISTER);
        player.setGroupID(groupID);
        cmd.send(player.getGroupID());
        cmd.send(player.getPlayerName());
        cmd.send(player.getJobCode());
        if (!cmd.receiveBoolean()) {
            System.out.println("このグループには参加できません"); //TODO: GUI
        }
        player.setParent(cmd.receiveBoolean());
        player.setPlayerID(cmd.receiveInt());

        System.out.println("グループ" + groupID + "に参加しました。"); //TODO: GUI
        System.out.println(cmd.receiveString());
        if (player.isParent()) {
            System.out.println("\u001b[35mあなたはこのゲームの親です。”end”を入力すると参加募集を締め切ります。\u001b[0m"); //TODO: GUI
        }
    }

    private void setGroup() {
        int groupCount;
        int op;
        //TODO: GUI
        System.out.println("操作を選択してください。");
        System.out.println("1: 新規グループを作成");
        cmd.send(Server.DOES_EXIST_GROUP);
        groupCount = cmd.receiveInt();
        if (groupCount > 0) {
            System.out.println("2: 既存グループに参加");
        }
        op = scanner.nextInt();
        while (op != 1 && groupCount == 0 || op > 2 || op < 1) {
            System.out.println("数字を入力してください。");
            op = scanner.nextInt();
        }
        switch (op + 100) {
            case Server.NEW_GROUP:
                newGroup();
                break;
            case Server.LIST_GROUP:
                listGroup(groupCount);
                break;
        }
    }

    private void newGroup() {
        cmd.send(Server.NEW_GROUP);
        groupID = cmd.receiveInt();
        System.out.println("グループ" + groupID + "を作成します。"); //TODO: GUI
    }

    private void listGroup(int groupCount) {
        Scanner scanner = new Scanner(System.in);
        cmd.send(Server.LIST_GROUP);
        int total = cmd.receiveInt();
        //TODO: GUI
        System.out.println("グループが " + groupCount + " 個あります。グループを選んでください。");
        for (int i = 0; i < total; i++) {
            System.out.println(cmd.receiveString());
        }
        groupID = scanner.nextInt() - 1;
        //TODO: 0で前に戻れるように
        if (groupID == -1) {
            setGroup();
        }
        while (groupID + 1 > groupCount || groupID < -1) {
            System.out.println("正しい番号を選んでください");
            groupID = scanner.nextInt() - 1;
        }

    }
}