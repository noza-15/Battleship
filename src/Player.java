import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 910896110410228731L;
    private int score;
    private String playerName;
    private int jobCode;
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

    public void nextTurn() {

    }

    public String getPlayerName() {
        return playerName;
    }

    private void setPlayerName(String playerName) {
        this.playerName = playerName;
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
                ", Job= " + Server.job[jobCode] +
                '}';
    }
}
