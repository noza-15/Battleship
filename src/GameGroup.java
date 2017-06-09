import java.io.PrintWriter;
import java.util.ArrayList;

public class GameGroup {
    private ArrayList<Player> playersList = new ArrayList<>();
    private ArrayList<PrintWriter> outList = new ArrayList<>();
    private int groupID = -1;
    private int parentID = -1;
    private int attackersCount = 0;
    private int bystandersCount = 0;

    private boolean isOpen;

    GameGroup(int groupID) {
        this.groupID = groupID;
        isOpen = true;
    }

    public ArrayList<PrintWriter> getOutList() {
        return outList;
    }

    void addPlayer(Player player) {
        playersList.add(player);
        if (player instanceof Attacker) {
            System.out.println("Add an attacker into Group" + groupID);
            attackersCount++;
        } else if (player instanceof Bystander) {
            System.out.println("Add a bystander into Group" + groupID);
            bystandersCount++;
        }
    }

    void addPrintWriter(PrintWriter writer) {
        outList.add(writer);
    }
    int size() {
        return playersList.size();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void closeGroup() {
        isOpen = false;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int playerID) {
        this.parentID = playerID;
    }

    public int getGroupID() {
        return groupID;
    }

    public int getAttackersCount() {
        return attackersCount;
    }

    public int getBystandersCount() {
        return bystandersCount;
    }

    public int getPlayersCount() {
        return attackersCount + bystandersCount;
    }

    @Override
    public String toString() {
        if (isOpen) {
            return "グループ" + groupID + " : " +
                    "Attacker " + attackersCount + "人, " +
                    "Bystander " + bystandersCount + "人, " +
                    "親のID " + parentID +
                    ", 参加者募集中";
        } else {
            return "\u001b[37mグループ" + groupID + " : " +
                    "Attacker " + attackersCount + "人, " +
                    "Bystander " + bystandersCount + "人, " +
                    "親のID " + parentID +
                    ", 開戦中\u001b[0m";
        }
    }
}
