package BattleShip;

import java.io.Serializable;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Player implements Serializable {
    private static final long serialVersionUID = 910896110410228731L;
    public ShipManager manager;
    transient Scanner scanner = new Scanner(System.in);
    transient CommandHandler cmd;
    int groupID = -1;
    int playerID = -1;
    ArrayList<Player> playersList;
    HashMap<Integer, Integer> IDTable = new HashMap<>();
    private String playerName;
    private int jobCode;
    private boolean isParent;
    private boolean isAlive;

    Player(String inputName) {
        setPlayerName(inputName);
    }

    public void newGame() throws SocketException {
        System.out.println("他のプレイヤーの選択を待っています…");
        cmd.send(Server.GET_SHIPS);
        manager.setOthersShips((Ship[][][]) cmd.receiveObject());
        System.out.println("盤面データを受信しています…");
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
                    System.out.println("Error: 2 attackers are needed to start a game at least.");
                }
            }
            cmd.send(Server.START);
            cmd.receiveInt();
        } else {
            System.out.println("ゲーム開始を待機しています…");
            while (Server.START != cmd.receiveInt()) ;
        }
        System.out.println("プレイヤー情報を取得しています…");
        playersList = (ArrayList<Player>) cmd.receiveObject();
        for (Player p : playersList) {
            IDTable.put(p.getPlayerID(), playersList.indexOf(p));
        }
    }

    public void nextTurn() throws SocketException {

        System.out.println("他のプレイヤーが選択するのを待っています...");
        for (int i = 0; i < playersList.size(); i++) {
            AttackCommand command = null;
            if (playersList.get(i).getJobCode() == Server.ATTACKER) {
                command = (AttackCommand) cmd.receiveObject();
            }
            if (i == 0) {
                System.out.println("# Turn" + command.getTurnNo() + " START!");
            }
            for (int j = 0; j < playersList.size(); j++) {
                if (playersList.get(i).getJobCode() == Server.ATTACKER) {
                    int state = manager.isBombed(IDTable.get(command.getPlayerID()), j, command.getX(), command.getY());
                    System.out.println("Turn" + command.getTurnNo() + " " + command.getPlayerID() + " : " + state);
                }
            }
        }
        for (int i = 0; i < playersList.size(); i++) {
            if (playersList.get(i).getJobCode() == Server.ATTACKER) {
                manager.show(i);
            }
        }
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

    public ArrayList<Player> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(ArrayList<Player> playersList) {

        this.playersList = playersList;
    }

    public HashMap<Integer, Integer> getIDTable() {
        return IDTable;
    }

    public void setIDTable(HashMap<Integer, Integer> IDTable) {
        this.IDTable = IDTable;
    }

    @Override
    public String toString() {
        return Server.JOB[getJobCode()] + "{" +
                "GroupID= " + getGroupID() +
                ", PlayerID= " + getPlayerID() +
                ", Name= " + getPlayerName() +
                ", Parent= " + isParent() +
                "}";
    }

    int inputInt(int min, int max) {
        int n;
        while (true) {
            if (scanner.hasNextInt()) {
                n = scanner.nextInt();
                if (n >= min && n <= max) {
                    break;
                } else {
                    System.out.println("正しい値を入力してください。");
                }
            } else {
                scanner.next();
            }
        }
        return n;
    }
}
