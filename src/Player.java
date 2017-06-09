import java.io.Serializable;
import java.util.Scanner;

public class Player implements Serializable {
    private static final long serialVersionUID = 910896110410228731L;
    private String playerName;
    private int jobCode;
    private int groupID;
    private int playerID;

    private boolean isParent;
    private CommandHandler cmd;

    Player(String inputName) {
        setPlayerName(inputName);
    }

    Player(String inputName, CommandHandler cmd) {
        setPlayerName(inputName);
        this.cmd = cmd;

    }

    private Player(String playerName, int jobCode) {
        this.playerName = playerName;
        this.jobCode = jobCode;
    }

    public void newGame() {
    }

    public void waitStart() {
        if (isParent()) {
            System.out.println("\"end\"を入力するとプレイヤーの募集を終了します。");
            Scanner scanner = new Scanner(System.in);
            String command;
            boolean isClosed = false;
            while (!isClosed) {
                do {
                    command = scanner.nextLine();
                } while (!command.toLowerCase().equals("end"));
                cmd.send(Server.CLOSE_APPLICATIONS);
                if (!(isClosed = cmd.receiveBoolean())) {
                    System.out.println(cmd.receiveString());
                }
            }

        } else {
            System.out.println("ゲーム開始を待機しています…");
            while (Server.START != cmd.receiveInt()) ;
        }
    }

    public void nextTurn() {

    }

    public String getPlayerName() {
        return playerName;
    }

    private void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {

        this.groupID = groupID;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public int getJobCode() {
        return jobCode;
    }

    void setJobCode(int jobCode) {
        this.jobCode = jobCode;
    }

    @Override
    public String toString() {
        return "Player{" +
//                "score=" + score +
                "Name= " + playerName +
                ", Job= " + Server.JOB[jobCode] +
                '}';
    }
}
