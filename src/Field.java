import java.io.Serializable;
import java.util.ArrayList;

public class Field implements Serializable {
    private static final long serialVersionUID = 7418218819592240789L;
    private ArrayList<Player> players = new ArrayList<>();
    private int[][] field = new int[10][10];


    //攻撃した場所を保存
    void FieldAttack(int x, int y) {
        field[y][x] = 1;
    }

    //攻撃された場所を保存
    void FieldAttacked(boolean t, int x, int y) {
        if (t) {
            field[y][x] = 2;//当たり
        } else {
            field[y][x] = 3;//はずれ
        }
    }


    //海の状態を表示するメソッド
    void show() {
        for (int n = 1; n <= field.length; n++) {
            System.out.print("\t" + n);
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            System.out.print((i + 1) + "\t");
            for (int j = 0; j < 10; j++) {
                if (field[i][j] == 0) {
                    System.out.print("-\t");
                } else if (field[i][j] == 1) {
                    System.out.print("✖\t");
                } else if (field[i][j] == -1) {
                    System.out.print("◯\t");
                } else if (field[i][j] == 2) {
                    System.out.print("@\t");
                } else if (field[i][j] == 3) {
                    System.out.print("*\t");
                }

            }
            System.out.println();
        }
    }

}