import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    static final String[] job = {"Attacker", "Bystander"};
    static final int PORT_NO = 9999;
    static final int ATTACKER = 0;
    static final int BYSTANDER = 1;
    static final int REGISTER = 10;
    static final int NEW_GROUP = 11;
    static final int LIST_GROUP = 12;
    static final int DOES_EXIST_GROUP = 13;
    static final String SHIPS[] = {"空母", "戦艦", "巡洋艦", "潜水艦", "駆逐艦"};
    static int numOfGame = 0;
    static int numOfPlayer = 0;
    static ArrayList<Field> fieldList = new ArrayList<>();
    static ArrayList<Player> playerList = new ArrayList<>();
//    static Server serverInstance;

    // TODO: 実際のゲームが始まってからの通信はこれから実装する 野澤 2017/05/05
    public static void main(String[] args) throws IOException {
//        serverInstance = new Server();
        ServerSocket s = new ServerSocket(PORT_NO);
        System.out.println("Started: " + s);
        new serverPrompt().start();
        try {
            while (true) {
                Socket socket = s.accept();
                new connectionThread(socket).start();
            }
        } finally {
            s.close();
        }
    }
}

class connectionThread extends Thread {
    private Socket socket;

    // TODO: サンプルコードにあった出力が残ってる 野澤 2017/05/05
    connectionThread(Socket socket) {
        this.socket = socket;
        System.out.println("接続" + socket.getRemoteSocketAddress());
    }

    public void run() {
        try {
            System.out.println("Connection accepted: " + socket);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())),
                    true);
            String data;
            while ((data = in.readLine()) != null) {
                handleCommand(data, in, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("closing...");
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("クライアントとの接続が失われました。");
            }
        }
    }

    private void handleCommand(String command, BufferedReader in, PrintWriter out) throws IOException {
        switch (Integer.parseInt(command)) {
            case Server.REGISTER:
                int groupNo = Integer.parseInt(in.readLine());
                String name = in.readLine();
                int jobCode = Integer.parseInt(in.readLine());
                switch (jobCode) {
                    case Server.ATTACKER:
                        Attacker attacker = new Attacker(name);
                        Server.playerList.add(attacker);
//                        Server.fieldList.get(groupNo).addPlayer(attacker);
                        break;
                    case Server.BYSTANDER:
                        Bystander bystander = new Bystander(name);
                        Server.playerList.add(bystander);
//                        Server.fieldList.get(groupNo).addPlayer(bystander);
                        break;
                }
                System.out.println("登録完了 : " + name + ", " + Server.job[jobCode] + ", ID:" + Server.playerList.size());
                out.println(Server.fieldList.get(groupNo));
                out.println("登録完了 : " + name + ", " + Server.job[jobCode] + ", ID:" + Server.playerList.size());
                out.println(Server.playerList.size());
                break;
            case Server.NEW_GROUP:
                System.out.println("グループを新規作成します。");
                Server.fieldList.add(new Field(Server.fieldList.size() + 1));
//                Server.fieldList.add(new Field(Server.fieldList.size() + 1));
                out.println(Server.playerList.size());
                out.println(Server.fieldList.size() + 1);
                break;
            case Server.DOES_EXIST_GROUP:
                out.println(Server.fieldList.size());
                break;
            case Server.LIST_GROUP:
                System.out.println("グループの一覧出力");
                out.println(Server.fieldList.size());
                for (int i = 0; i < Server.fieldList.size(); i++) {
                    out.println(i + 1 + ": " + Server.fieldList.get(i));
                }
                out.println(Server.playerList.size());
                break;
            default:
                System.out.println("?");
                break;
        }

    }
}

class serverPrompt extends Thread {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine().toLowerCase();
            switch (command) {
                case "list player":
                    if (Server.playerList.size() == 0) {
                        System.out.println("プレイヤーがいません。");
                    }
                    for (int i = 0; i < Server.playerList.size(); i++) {
                        System.out.println(i + 1 + ": " + Server.playerList.get(i));
                    }
                    break;
                case "list group":
                    if (Server.fieldList.size() == 0) {
                        System.out.println("グループが存在しません。");
                    }
                    for (int i = 0; i < Server.fieldList.size(); i++) {
                        System.out.println(i + 1 + ": " + Server.fieldList.get(i));
                    }
                    break;
                case "quit":
                case "exit":
                    System.exit(1);
                    break;
                default:
                    System.out.println(command + ": コマンドが見つかりません。");
                    break;
            }
        }
    }
}
