import java.util.Scanner;

public class Attacker extends Player {
    Field sea = new Field(1);
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
    public static boolean check(int size, int x, int y, int d){
        if(x<0 || y<0){
            System.out.println("そこにはおけません！");
            return true;
        }
        else if(d==1 && x+size>=10) {
            System.out.println("そこにはおけません");
            return true;
        }
        else if(d==0 && y+size>=10){
            System.out.println("そこにはおけません");
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void newGame() {
        System.out.println("船の配置を決めます。");
        for(int i=0;i<Server.SHIPS.length;i++) {
            do {
                System.out.println(Server.SHIPS[0] + "の設定をします、支点の位置を決めてください");
                System.out.print("x:");
                int x = scan.nextInt();
                System.out.print("y:");
                int y = scan.nextInt();
                System.out.println("設置する方向を決めてください");
                System.out.println("縦:0");
                System.out.println("横:1");
                int d = scan.nextInt();
                while (check(Server.SHIPS_SIZE[i], x, y, d)) ;
            }
            //:TODO インスタンスの格納
            ships[i] = new Ship(Server.SHIPS[i], Server.SHIPS_SIZE[i], x, y, d);

        }

    }

    @Override
    //攻撃する位置を指定する。
    public void nextTurn() {

        System.out.println("どこを攻撃しますか？");//攻撃場所の読み込み
        System.out.print("x:");
        int x = scan.nextInt();//TODO:数字以外が入力されたらそこでエラーになり終了してしまう。
        System.out.print("y:");
        int y = scan.nextInt();
        sea.FieldAttacked(x - 1, y - 1);
        sea.show();
        System.out.println("他のプレイヤーが選択するのを待っています...");

        //TODO:入力されたマスを送信する。
    }

    boolean survive(Ship ship[]){
        int lives = 3;
        for(int i=0;i<Server.SHIPS.length;i++) {
            if (ship[i].getLife() <= 0) {
                lives--;
            }
        }
        if(lives <=0){
            System.out.println("You Lose");
            return false;
        }
        else return true;
    }


    @Override
    public String toString() {
        return "Attacker {" +
                "Name= " + getPlayerName() +
                ", Job= " + Server.JOB[getJobCode()] +
                "}";
    }
}
