import java.io.Serializable;
import java.util.ArrayList;

public class Field implements Serializable {
    private static final long serialVersionUID = 7418218819592240789L;
    private ArrayList<Player> players = new ArrayList<>();
    private int[][] field;
    private int numOfAttackers;
    private int numOfBystanders;
    private int numOfGame;





    // TODO: 盤面の保存形式を決める 野澤 2017/05/05

    Field(int n) {
        field = new int[10][10];
        numOfGame = n;
    }



    /*void addPlayer(Player player) {
        switch (player.getJobCode()) {
            case Server.ATTACKER:
                numOfAttackers++;
                break;
            case Server.BYSTANDER:
                numOfBystanders++;
                break;
        }
        players.add(player);
    }*/
    //自分の海を表示するメソッド
    void showmyField() {
        for (int i= 0; i < 10; i++) {
            System.out.print((i + 1));
            for (int j = 0; j < 10; j++) {
                //各マスの状態によって場合分けする。
                }
                System.out.println();
            }
        }
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
        return "グループ" + numOfGame + " - " +
                "Attacker " + numOfAttackers + "人, " +
                "Bystander " + numOfBystanders + "人";
    }
}
