import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 910896110410228731L;
    private int score;
    private String playerName;
    private int jobCode;


    Player(String inputName) {
        setPlayerName(inputName);
    }

    public void newGame() {

    }

    public void nextTurn() {

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getJobCode() {
        return jobCode;
    }

    public void setJobCode(int jobCode) {
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
