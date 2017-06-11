import java.util.ArrayList;
import java.util.Calendar;

public class GameGroup {
    private ArrayList<Player> playersList = new ArrayList<>();
    private ArrayList<CommandHandler> outList = new ArrayList<>();
    private int groupID = -1;
    private int parentID = -1;
    private int attackersCount = 0;
    private int bystandersCount = 0;

    volatile private boolean isOpen;

    GameGroup(int groupID) {
        this.groupID = groupID;
        isOpen = true;
    }

    public ArrayList<CommandHandler> getOutList() {
        return outList;
    }

    void addPlayer(Player player) {
        playersList.add(player);
        if (player instanceof Attacker) {
            System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [Add attacker: " + player + groupID);
            attackersCount++;
        } else if (player instanceof Bystander) {
            System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [Add bystander: " + player + groupID);
            bystandersCount++;
        }
    }

    void addCommandHandler(CommandHandler handler) {
        outList.add(handler);
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

    public ArrayList<Player> getPlayersList() {
        return playersList;
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
