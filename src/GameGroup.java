import java.util.ArrayList;

public class GameGroup {
    private ArrayList<Player> playersList = new ArrayList<>();
    private int groupID;
    private int numOfAttackers = 0;
    private int numOfBystanders = 0;

    GameGroup(int groupID) {
        this.groupID = groupID;
    }

    void add(Player player) {
        playersList.add(player);
        if (player instanceof Attacker) {
            numOfAttackers++;
        } else if (player instanceof Bystander) {
            numOfBystanders++;
        }
    }

    int size() {
        return playersList.size();
    }

    @Override
    public String toString() {
        return "グループ" + groupID + " : " +
                "Attacker " + numOfAttackers + "人, " +
                "Bystander " + numOfBystanders + "人";
    }
}
