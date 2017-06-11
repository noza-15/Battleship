import java.io.Serializable;
import java.net.SocketException;
import java.util.Scanner;

public class Player implements Serializable {
    private static final long serialVersionUID = 910896110410228731L;
    transient Scanner scanner = new Scanner(System.in);
    transient CommandHandler cmd;

    int groupID = -1;
    int playerID = -1;
    private String playerName;
    private int jobCode;
    private boolean isParent;

    Player(String inputName) {
        setPlayerName(inputName);
    }

    public void newGame() throws SocketException {
    }

    public void waitStart() throws SocketException {
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
            cmd.send(Server.START);
            cmd.receiveInt();
        } else {
            System.out.println("ゲーム開始を待機しています…");
            while (Server.START != cmd.receiveInt()) ;
        }
    }

    public void nextTurn() throws SocketException {

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

    public void setCmd(CommandHandler cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return Server.JOB[getJobCode()] + " {" +
                "Group= " + getGroupID() +
                ", PlayerID= " + getPlayerID() +
                ", Name= " + getPlayerName() +
                ", Parent= " + isParent() +
                "}";
    }
}
