package BattleShip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class GameGroup implements Serializable {
    private static final long serialVersionUID = -7088219024468941214L;
    private ArrayList<Player> playersList = new ArrayList<>();
    private ArrayList<CommandHandler> outList = new ArrayList<>();
    private int groupID = -1;
    private int parentID = -1;
    private int attackersCount;
    private int bystandersCount;
    private Ship[][][] allShipsMap;
    private int setMapCount;


    volatile private boolean isOpen;

    GameGroup(int groupID) {
        this.groupID = groupID;
        isOpen = true;
    }

    public ArrayList<CommandHandler> getOutList() {
        return outList;
    }

    void addPlayer(Player player) {
        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                + "] [Add: " + player + "; GroupID= " + groupID + "]");
        playersList.add(player);
        if (player instanceof Attacker) {
            attackersCount++;
        } else if (player instanceof Bystander) {
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
        allShipsMap = new Ship[attackersCount][Server.FIELD_SIZE_X][Server.FIELD_SIZE_Y];
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

    public void setShipsMap(Player player, Ship[][] allShipsMap) {
        this.allShipsMap[playersList.indexOf(player)] = allShipsMap;
        setMapCount++;
    }

    public Ship[][][] getAllShipsMap() {
        return allShipsMap;
    }

    public boolean canStart() {
        return setMapCount == attackersCount;
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
            return /*\u001b[37m*/"グループ" + groupID + " : " +
                    "Attacker " + attackersCount + "人, " +
                    "Bystander " + bystandersCount + "人, " +
                    "親のID " + parentID +
                    ", 開戦中"/*\u001b[0m*/;
        }
    }
}
