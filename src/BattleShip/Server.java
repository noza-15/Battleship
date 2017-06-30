package BattleShip;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;

public class Server {
    public static final String GAME_NAME = "*** レーダー作戦ゲームβ ***";
    public static final int PORT_NO = 49777;
    public static final String[] JOB = {"Attacker", "Bystander"};
    public static final String[] SHIPS = {"空母", "戦艦", "巡洋艦", "潜水艦", "駆逐艦"};
    public static final int[] SHIPS_SIZE = {5, 4, 3, 3, 2};

    public static final int FIELD_SIZE_X = 10;
    public static final int FIELD_SIZE_Y = 10;

    public static final int ATTACKER = 0;
    public static final int BYSTANDER = 1;
    //command
    public static final int REGISTER = 100;
    public static final int NEW_GROUP = 101;
    public static final int LIST_GROUP = 102;
    public static final int CHECK_GROUP_EXISTENCE = 103;
    public static final int LIST_MEMBERS = 105;
    public static final int CLOSE_APPLICATIONS = 110;
    public static final int START = 115;
    public static final int SET_SHIPS = 120;
    public static final int GET_SHIPS = 121;
    public static final int ATTACK = 125;
    static ArrayList<Player> allPlayerList = new ArrayList<>();
    static ArrayList<GameGroup> groupList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT_NO);
        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [Start: " + s + "]");
        new ServerPrompt().start();
        try {
            while (true) {
                Socket socket = s.accept();
                new GameServerThread(socket).start();
            }
        } finally {
            s.close();
        }
    }
}
