import java.util.ArrayList;

public class GameGroup {
    private ArrayList<Player> playersList = new ArrayList<>();

    private int groupID;
    private int parentID;
    private int attackersCount = 0;
    private int bystandersCount = 0;

    GameGroup(int groupID) {
        this.groupID = groupID;
    }

    void add(Player player) {
        playersList.add(player);
        if (player instanceof Attacker) {
            System.out.println("Add an attacker into Group" + groupID);
            attackersCount++;
        } else if (player instanceof Bystander) {
            System.out.println("Add a bystander into Group" + groupID);
            bystandersCount++;
        }
    }

    int size() {
        return playersList.size();
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int groupID) {
        this.groupID = groupID;
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

    @Override
    public String toString() {
        return "グループ" + groupID + " : " +
                "Attacker " + attackersCount + "人, " +
                "Bystander " + bystandersCount + "人";
    }
}
