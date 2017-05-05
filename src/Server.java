import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    static final String[] job = {"Attacker", "Bystander"};
    static final int PORT_NO = 8080;
    static final int ATTACKER = 0;
    static final int BYSTANDER = 1;
    static final int REGISTER = 10;
    static final int NEW_GROUP = 11;
    static final int LIST_GROUP = 12;
    static int numOfGame = 0;
    static int numOfPlayer = 0;
    static ArrayList<Game> gameList = new ArrayList<>();
    static ArrayList<Player> playerList = new ArrayList<>();
    static Server serverInstance;

    public static void main(String[] args) throws IOException {
        serverInstance = new Server();
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
    Socket socket;

    public connectionThread(Socket socket) {
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
                commandReceiver(data, in, out);
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

    public void commandReceiver(String command, BufferedReader in, PrintWriter out) throws IOException {
        switch (Integer.parseInt(command)) {
            case Server.REGISTER:
                int groupNo = Integer.parseInt(in.readLine());
                String name = in.readLine();
                int code = Integer.parseInt(in.readLine());
                switch (code) {
                    case Server.ATTACKER:
                        Attacker attacker = new Attacker(name);
                        Server.playerList.add(attacker);
                        Server.gameList.get(groupNo).addPlayer(attacker);
                        break;
                    case Server.BYSTANDER:
                        Bystander bystander = new Bystander(name);
                        Server.playerList.add(bystander);
                        Server.gameList.get(groupNo).addPlayer(bystander);
                        break;
                }
                System.out.println("登録完了 : " + name + " , " + Server.job[code] + ", ID:" + Server.playerList.size());
                out.println("登録完了 : " + name + " , " + Server.job[code] + ", ID:" + Server.playerList.size());
                out.println(Server.playerList.size());
                break;
            case Server.NEW_GROUP:
                System.out.println("グループを新規作成します");
                Server.gameList.add(new Game(Server.gameList.size() + 1));
                out.println(Server.playerList.size());
                out.println(Server.gameList.size() + 1);
                break;
            case Server.LIST_GROUP:
                System.out.println("グループの一覧出力");
                out.println(Server.gameList.size());
                for (int i = 0; i < Server.gameList.size(); i++) {
                    out.println(i + 1 + ") " + Server.gameList.get(i));
                }
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
            String command = scanner.nextLine();
            switch (command) {
                case "list group": {
                    if (Server.playerList.size() == 0) {
                        System.out.println("プレイヤーがいません。");
                    }
                    for (Player player : Server.playerList) {
                        System.out.println(player);
                    }
                    break;
                }
                case "list game": {
                    for (int i = 0; i < Server.gameList.size(); i++) {
                        System.out.println(i + 1 + ") " + Server.gameList.get(i));
                    }
                }
                case "exit":
                    System.exit(1);
                    break;
            }
        }
    }
}
