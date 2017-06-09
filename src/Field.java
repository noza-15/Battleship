import java.io.Serializable;
import java.util.ArrayList;

public class Field implements Serializable {
    private static final long serialVersionUID = 7418218819592240789L;
    private ArrayList<Player> players = new ArrayList<>();
    private int[][] field = new int[10][10];
    private int numOfAttackers;
    private int numOfBystanders;
    private int numOfGame;


    // TODO: 盤面の保存形式を決める 野澤 2017/05/05

    Field(int n) {
        field = new int[10][10];
        numOfGame = n;
    }

    //攻撃した場所を保存
    void FieldAttacked(int x,int y){
        field[y][x] = 1;
    }
    //攻撃された場所を保存
    void FiealdAttack(int x,int y) { field[y][x] = 2 ;}


    //海の状態を表示するメソッド
    void show() {
        for(int n = 0;n < field.length; n++) {
            System.out.print("\t" + (n + 1));
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            System.out.print((i + 1) + "\t");
            for (int j = 0; j < 10; j++) {
                if (field[i][j] == 0) {
                    System.out.print("-\t");
                }
                else if (field[i][j] == 1) {
                    System.out.print("✖\t");
                }
                else if (field[i][j] == -1) {
                    System.out.print("◯\t");
                }
                else if(field[i][j] == 2){
                    System.out.println("@\t");
                }

            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "グループ" + numOfGame + " - " +
                "Attacker " + numOfAttackers + "人, " +
                "Bystander " + numOfBystanders + "人";
    }

}