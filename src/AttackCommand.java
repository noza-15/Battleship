import java.io.Serializable;

public class AttackCommand implements Serializable {

    private static final long serialVersionUID = -8197662008360524960L;
    private int groupID = -1;
    private int playerID = -1;
    private int x = -1;
    private int y = -1;
    private int turnNo;

    AttackCommand(int groupID, int playerID, int turnNo, int x, int y) {
        this.groupID = groupID;
        this.playerID = playerID;
        this.turnNo = turnNo;
        this.x = x;
        this.y = y;
    }

    public int getGroupID() {
        return groupID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getTurnNo() {
        return turnNo;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
