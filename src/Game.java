import java.util.ArrayList;

public class Game {
    ArrayList<Player> players = new ArrayList<>();
    int[][] field;
    private int numOfAttackers;
    private int numOfBystanders;
    private int numOfGame;

    public Game(int n) {
        field = new int[10][10];
        numOfGame = n;
    }

    void addPlayer(Player player) {
        switch (player.getJobCode()) {
            case Server.ATTACKER:
                numOfAttackers++;
                break;
            case Server.BYSTANDER:
                numOfBystanders++;
                break;
        }
        players.add(player);
    }


    void show() {
        for (int i = 0; i < field.length; i++) {
            System.out.print("|");
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == 0) {
                    System.out.print(" |");
                } else {
                    System.out.print("X|");
                }
            }
            System.out.println("");
        }
    }

    @Override
    public String toString() {
        return "グループ" + numOfGame +
                ": Attacker " + numOfAttackers + "人, " +
                "Bystander " + numOfBystanders + "人";
    }
}
