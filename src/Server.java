import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    static final String[] JOB = {"Attacker", "Bystander"};
    static final String SHIPS[] = {"空母", "戦艦", "巡洋艦", "潜水艦", "駆逐艦"};
    static final int SHIPS_SIZE[] = {5, 4, 3, 3, 2};
    static final int PORT_NO = 9999;

    static final int ATTACKER = 0;
    static final int BYSTANDER = 1;
    //command
    static final int REGISTER = 100;
    static final int NEW_GROUP = 101;
    static final int LIST_GROUP = 102;
    static final int DOES_EXIST_GROUP = 103;
    static final int CLOSE_APPLICATIONS = 104;
    static final int START = 105;
    static final int SET_SHIPS = 106;

    static ArrayList<Player> allPlayerList = new ArrayList<>();
    static ArrayList<GameGroup> groupList = new ArrayList<>();

    // TODO: 実際のゲームが始まってからの通信はこれから実装する 野澤 2017/05/05
    public static void main(String[] args) throws IOException {
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
                int groupID = Integer.parseInt(in.readLine());
                String name = in.readLine();
                int jobCode = Integer.parseInt(in.readLine());
                GameGroup group = Server.groupList.get(groupID);
                switch (jobCode) {
                    case Server.ATTACKER:
                        Attacker attacker = new Attacker(name);
                        group.add(attacker);
                        Server.allPlayerList.add(attacker);
                        if (group.getAttackersCount() == 1) {
                            out.println(true);
                            group.setParentID(Server.allPlayerList.size() - 1);
                        } else {
                            out.println(false);
                        }
                        break;
                    case Server.BYSTANDER:
                        Bystander bystander = new Bystander(name);
                        group.add(bystander);
                        Server.allPlayerList.add(bystander);
                        out.println(false);
                        break;
                }
                out.println(Server.allPlayerList.size() - 1);
                String output = "登録完了 : " + name + ", " + Server.JOB[jobCode] + ", playerID: " + (Server.allPlayerList.size() - 1);
                System.out.println(output);
                out.println(output);
                break;

            case Server.NEW_GROUP:
                System.out.println("New group generated.");
                Server.groupList.add(new GameGroup(Server.groupList.size()));
                out.println(Server.groupList.size() - 1);
                break;

            case Server.DOES_EXIST_GROUP:
                System.out.println("Does ");
                out.println(Server.groupList.size());
                break;

            case Server.LIST_GROUP:
                System.out.println("Group list:");
                out.println(Server.groupList.size()); //グループ数出力
                for (int i = 0; i < Server.groupList.size(); i++) {
                    out.println(i + 1 + ": " + Server.groupList.get(i));
                }
                out.println(Server.groupList.size());
                break;

            case Server.CLOSE_APPLICATIONS:
                int id = Integer.parseInt(in.readLine());
                if (Server.groupList.get(id).getAttackersCount() > 1) ;
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
                    if (Server.allPlayerList.size() == 0) {
                        System.out.println("プレイヤーがいません。");
                    }
                    for (int i = 0; i < Server.allPlayerList.size(); i++) {
                        System.out.println(i + 1 + ": " + Server.allPlayerList.get(i));
                    }
                    break;
                case "list group":
                    if (Server.groupList.size() == 0) {
                        System.out.println("グループが存在しません。");
                    }
                    for (int i = 0; i < Server.groupList.size(); i++) {
                        System.out.println(i + 1 + ": " + Server.groupList.get(i));
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
