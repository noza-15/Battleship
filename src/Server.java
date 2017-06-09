import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static final String[] JOB = {"Attacker", "Bystander"};
    static final String[] SHIPS = {"空母", "戦艦", "巡洋艦", "潜水艦", "駆逐艦"};
    static final int[] SHIPS_SIZE = {5, 4, 3, 3, 2};

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
    static final int ATTACK = 107;

    static ArrayList<Player> allPlayerList = new ArrayList<>();
    static ArrayList<GameGroup> groupList = new ArrayList<>();

    // TODO: 実際のゲームが始まってからの通信はこれから実装する 野澤 2017/05/05
    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT_NO);
        System.out.println("Started: " + s);
        new ServerPrompt().start();
        try {
            while (true) {
                Socket socket = s.accept();
                new ConnectionThread(socket).start();
            }
        } finally {
            s.close();
        }
    }
}
