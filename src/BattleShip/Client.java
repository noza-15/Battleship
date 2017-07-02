package BattleShip;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    private Scanner scanner;

    private int groupID;
    private Player player;
    private CommandHandler cmd;

    public static void main(String[] args) {
        System.out.println("\u001b[34m" + Server.GAME_NAME + "\u001b[0m");
        Client client = new Client();
        try {
            client.initialize();
            client.play();
        } catch (SocketException se) {
            System.err.println("サーバー接続中にエラーが発生しました。");
            System.exit(1);
        }
    }

    private void initialize() throws SocketException {
        scanner = new Scanner(System.in);
        establishConnection();
        setGroup();
        setPlayer();
        register();
    }

    private void play() throws SocketException {
        player.waitStart();
        player.newGame();
        System.out.println("\u001b[34mSTART !!\u001b[0m");
        player.nextTurn();
    }

    private void establishConnection() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("サーバーのアドレスを入力してください。");
        String inputAddress = scanner.nextLine();
        InetAddress address;
        try {
            address = InetAddress.getByName(inputAddress);
        } catch (UnknownHostException e) {
            System.out.println("サーバーが見つかりませんでした。アドレスが正しいか確認してください。");
            e.printStackTrace();
            establishConnection();
            return;
        }
        try {
            Socket socket = new Socket(address, Server.PORT_NO);
            System.out.println("サーバーとの接続に成功しました。");
            System.out.println("socket = " + socket);
            cmd = new CommandHandler(socket);
        } catch (ConnectException ce) {
            System.out.println("サーバーに接続を拒否されました。");
            ce.printStackTrace();
            establishConnection();
        } catch (IOException e) {
            System.out.println("サーバー接続中にエラーが発生しました。");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void setPlayer() {
        System.out.println("プレイヤー名を入力してください。");
        String inputName = scanner.next();
        System.out.println("役割を選んでください。\nAttacker(攻撃者), Bystander(傍観者)");
        switch (scanner.next().toLowerCase()) {
            case "a":
            case "attacker":
                player = new Attacker(inputName);
                System.out.println("Attacker(攻撃者)が選択されました。");
                break;
            case "b":
            case "bystander":
                player = new Bystander(inputName);
                System.out.println("Bystander(傍観者)が選択されました。");
                break;
            default:
                System.out.println("AかBを入力してください。");
                setPlayer();
                break;
        }
    }

    private void register() throws SocketException {
        cmd.send(Server.REGISTER);
        player.setGroupID(groupID);
        cmd.send(groupID);
        if (!cmd.receiveBoolean()) {
            System.out.println("このグループには参加できません。");
            setGroup();
            register();
            return;
        }
        cmd.send(player);
        player.setParent(cmd.receiveBoolean());
        player.setPlayerID(cmd.receiveInt());
        player.setCmd(cmd);

        System.out.println("グループ" + groupID + "に参加しました。");
        System.out.println(cmd.receiveString());
        if (player.isParent()) {
            System.out.println("\u001b[35mあなたはこのゲームの親です。”end”を入力すると参加募集を締め切ります。\u001b[0m");
        }
    }

    private void setGroup() throws SocketException {
        int groupCount;
        int op;
        System.out.println("操作を選択してください。");
        System.out.println("1: 新規グループを作成");
        cmd.send(Server.CHECK_GROUP_EXISTENCE);
        groupCount = cmd.receiveInt();
        if (groupCount == 0) {
            System.out.print("\u001b[37m");
        }
        System.out.println("2: 既存グループに参加");
        if (groupCount == 0) {
            System.out.print("\u001b[0m");
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

    private void newGroup() throws SocketException {
        cmd.send(Server.NEW_GROUP);
        groupID = cmd.receiveInt();
        System.out.println("グループ" + groupID + "を作成します。");
    }

    private void listGroup(int groupCount) throws SocketException {
        Scanner scanner = new Scanner(System.in);
        cmd.send(Server.LIST_GROUP);
        int total = cmd.receiveInt();
        System.out.println("グループが " + groupCount + " 個あります。グループを選んでください。");
        for (int i = 0; i < total; i++) {
            System.out.println(cmd.receiveString());
        }
        groupID = scanner.nextInt() - 1;
        //0で前に戻れるように
        if (groupID == -1) {
            setGroup();
            return;
        }
        while (groupID + 1 > groupCount || groupID < -1) {
            System.out.println("正しい番号を選んでください");
            groupID = scanner.nextInt() - 1;
        }

    }
}