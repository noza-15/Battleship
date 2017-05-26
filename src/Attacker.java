import java.util.Scanner;//攻撃位置を読み込むためにimportします。

public class Attacker extends Player {
    Field sea = new Field(1);
    //Ship a1 = new Ship ("空母", 5, posX, posY, Ship.VERTICAL);
    Scanner scan = new Scanner(System.in);
    Attacker(String inputName) {
        super(inputName);
        setJobCode(Server.ATTACKER);
    }

    @Override
    public void newGame() {
        System.out.println("船の配置を決めます。");
        System.out.println("空母の設定をします");

    }

    @Override
    //攻撃する位置を指定する。
    public void nextTurn() {

        System.out.println("どこを攻撃しますか？");//攻撃場所の読み込み
        System.out.print("x:");
        int x = scan.nextInt();//TODO:数字以外が入力されたらそこでエラーになり終了してしまう。
        System.out.print("y:");
        int y = scan.nextInt();
        sea.FieldAttacked(x-1,y-1);
        sea.show();
        System.out.println("他のプレイヤーが選択するのを待っています...");

        //TODO:入力されたマスを送信する。
    }


    @Override
    public String toString() {
        return "Attacker{" +
                "Name= " + getPlayerName() +
                ", Job= " + Server.job[getJobCode()] +
                "}";
    }
}
