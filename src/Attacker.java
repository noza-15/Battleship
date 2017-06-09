import java.time.Year;
import java.util.Scanner;

public class Attacker extends Player {

    int mylife = 3;
    Field mysea = new Field();
    Field sea[] = new Field[5]; //TODO:相手人数分のインスタンスの生成
    Ship ships[] = new Ship[Server.SHIPS.length];
    //Ship a1 = new Ship ("空母", 5, posX, posY, Ship.VERTICAL);
    Scanner scan = new Scanner(System.in);

    Attacker(String inputName) {
        super(inputName);
        setJobCode(Server.ATTACKER);
    }

    Attacker(String inputName, CommandHandler cmd) {
        super(inputName, cmd);
        setJobCode(Server.ATTACKER);
    }

    //船が置けるかどうか判別するメソッド
    private static boolean check(int size, int x, int y, int d) {
        if (x < 0 || y < 0) {
            System.out.println("そこにはおけません！");
            return true;
        } else if (d == 1 && x + size >= 10) {
            System.out.println("そこにはおけません");
            return true;
        } else if (d == 0 && y + size >= 10) {
            System.out.println("そこにはおけません");
            return true;
        } else {
            return false;
        }
    }

    //自分が戦闘可能かどうか判別するメソッド、いらないかも
    /*private boolean issurvive(Ship ships[]) {
        int lives = 3;
        for (int i = 0; i < Server.SHIPS.length; i++) {
            if (ships[i].getLife() <= 0) {
                lives--;
            }
        }
        if (lives <= 0) {
            return false;
        } else return true;
    }*/


    @Override
    public void newGame() {
        System.out.println("船の配置を決めます。");
        for (int i = 0; i < Server.SHIPS.length; i++) {
            int x, y, d;
            do {
                System.out.println(Server.SHIPS[i] + "の設定をします、" + Server.SHIPS[i] + "の大きさは" + Server.SHIPS_SIZE[i] + "です。支点の位置を決めてください");
                System.out.print("x:");
                x = scan.nextInt();
                System.out.print("y:");
                y = scan.nextInt();
                System.out.println("設置する方向を決めてください");
                System.out.println("縦:0");
                System.out.println("横:1");
                d = scan.nextInt();
            } while (check(Server.SHIPS_SIZE[i], x, y, d));
            //:TODO インスタンスの格納
            ships[i] = new Ship(Server.SHIPS[i], Server.SHIPS_SIZE[i], x, y, d);

        }

    }

    @Override
    //攻撃する位置を指定する。
    public void nextTurn() {
        while (true) {
            System.out.println("どこを攻撃しますか？");//攻撃場所の読み込み
            System.out.print("x:");
            int x = scan.nextInt();//TODO:数字以外が入力されたらそこでエラーになり終了してしまう。
            System.out.print("y:");
            int y = scan.nextInt();
            mysea.FieldAttack(x - 1, y - 1);
            mysea.show();
            System.out.println("他のプレイヤーが選択するのを待っています...");
            // TODO:入力されたマスを送信する。
            // TODO:入力されたマスを受信する。
            // TODO:全員攻撃完了した後の処理
            for (int i = 0; i < 5; i++) { //TODO:プレイヤーの人数分の攻撃処理
                for (int j = 0; j < Server.SHIPS.length; j++) {
                    mysea.FieldAttacked(ships[j].bombed(X - 1, Y - 1), X - 1, Y - 1);
                }
            }
            int life = 3;
            for (int k = 0; k < Server.SHIPS.length; k++) {
                if (ships[k].shipLife()) {
                    life--;
                } else {
                }
            }
            if (life <= 0) {
                System.out.println("戦闘不能です。");
                break;
            }
        }
    }






    @Override
    public String toString() {
        return "Attacker {" +
                "Name= " + getPlayerName() +
                ", Job= " + Server.JOB[getJobCode()] +
                "}";
    }
}
