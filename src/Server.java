import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    static final String[] job = {"Attacker", "Bystander"};
    static final int ATTACKER = 0;
    static final int BYSTANDER = 1;
    static final int REGISTER = 10;
    static final int NEW_GROUP = 20;
    static boolean hasSetGame = false;
    static int numOfPlayer = 0;
    static ArrayList<Player> playerList = new ArrayList();

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(8080);
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
                e.printStackTrace();
            }
        }
    }

    public void commandReceiver(String command, BufferedReader in, PrintWriter out) throws IOException {
        switch (Integer.parseInt(command)) {
            case Server.REGISTER:
                String name = in.readLine();
                int code = Integer.parseInt(in.readLine());
                Server.numOfPlayer++;
                switch (code) {
                    case Server.ATTACKER:
                        Server.playerList.add(new Attacker(name));
                        break;
                    case Server.BYSTANDER:
                        Server.playerList.add(new Bystander(name));
                        break;
                }
                System.out.println("登録完了 : " + name + " : " + Server.job[code]);
                out.println("登録完了 : " + name + " : " + Server.job[code]);

                break;
            case Server.NEW_GROUP:
                System.out.println("グループを新規作成します");
                Game game = new Game();
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
                case "list": {
                    for (Player player : Server.playerList) {
                        System.out.println(player);
                    }
                    break;
                }
                case "exit":
                    System.exit(0);
                    break;
            }
        }
    }


}
