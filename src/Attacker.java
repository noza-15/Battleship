import java.net.SocketException;

public class Attacker extends Player {

    //    transient Scanner scanner = new Scanner(System.in);
    //Ship a1 = new Ship ("空母", 5, posX, posY, Ship.VERTICAL);
    int myLife = 3;
    Field mySea = new Field();
    Field sea[] = new Field[5]; //TODO:相手人数分のインスタンスの生成
    Ship ships[] = new Ship[Server.SHIPS.length];

    Attacker(String inputName) {
        super(inputName);
        setJobCode(Server.ATTACKER);
    }

    //船が置けるかどうか判別するメソッド
    private static boolean check(int size, int x, int y, int d) {
        if(d != 0 && d != 1){
            System.out.println("0か1を入力して向きを決めてください！");
            return true;
        }
        else if (x <= 0 || y <= 0) {
            System.out.println("そこにはおけません！");
            return true;
        }
        else if (d == 1 && x + size > 10 || y > 10) {
            System.out.println("そこにはおけません!");
            return true;
        }
        else if (d == 0 && y + size > 10 || x > 10) {
            System.out.println("そこにはおけません!");
            return true;
        }
        else {
            return false;
        }
    }

    //自分が戦闘可能かどうか判別するメソッド、いらないかも
    /*private boolean isurvive(Ship ships[]) {
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
    public void newGame() throws SocketException {
        System.out.println("船の配置を決めます。");
        for (int i = 0; i < Server.SHIPS.length; i++) {
            int x, y, d;
            do {
                System.out.println(Server.SHIPS[i] + "の設定をします。"
                        + Server.SHIPS[i] + "の大きさは" + Server.SHIPS_SIZE[i] + "です。" +
                        "始点の位置を決めてください");
                System.out.print("x:");
                while (true) {
                    if (scanner.hasNextInt()) {
                        x = scanner.nextInt();
                        break;
                    } else {
                        scanner.next();
                    }
                }
                System.out.print("y:");
                while (true) {
                    if (scanner.hasNextInt()) {
                        y = scanner.nextInt();
                        break;
                    } else {
                        scanner.next();
                    }
                }
                System.out.println("設置する方向を決めてください");
                    System.out.println("0:縦");
                    System.out.println("1:横");
                    d = scanner.nextInt();

            } while (check(Server.SHIPS_SIZE[i], x, y, d));
            //TODO: インスタンスの格納
            ships[i] = new Ship(Server.SHIPS[i], Server.SHIPS_SIZE[i], x, y, d);
            //TODO: サーバーに船の配置を送信する
        }

    }

    @Override
    //攻撃する位置を指定する。
    public void nextTurn() throws SocketException {
        while (true) {
            int x, y;
            System.out.println("どこを攻撃しますか？");//攻撃場所の読み込み
            System.out.print("x:");
            while (true) {
                if (scanner.hasNextInt()) {
                    x = scanner.nextInt();
                    break;
                } else {
                    scanner.next();
                }
            }
            System.out.print("y:");
            while (true) {
                if (scanner.hasNextInt()) {
                    y = scanner.nextInt();
                    break;
                } else {
                    scanner.next();
                }
            }
            mySea.FieldAttack(x - 1, y - 1);
            mySea.show();
            System.out.println("他のプレイヤーが選択するのを待っています...");

            // TODO:入力されたマスを送信する。
            cmd.send(new AttackCommand(groupID, playerID, x, y));
            // TODO:入力されたマスを受信する。
            for (int i = 0; i < 5; i++) {
                AttackCommand command = (AttackCommand) cmd.receiveObject();
                if (command.getGroupID() == groupID && command.getPlayerID() != playerID) {
                    //TODO: 要処理
                    command.getX();
                    command.getY();
                }
            }
            // TODO:全員攻撃完了した後の処理
            for (int i = 0; i < 5; i++) { //TODO:プレイヤーの人数分の攻撃処理
                for (int j = 0; j < Server.SHIPS.length; j++) {
                    mySea.FieldAttacked(ships[j].bombed(3 - 1, 2 - 1), 3 - 1, 2 - 1);
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

}
