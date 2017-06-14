import java.net.SocketException;

public class Attacker extends Player {

    int myLife = 3;
    Field mySea = new Field();
    Field sea[] = new Field[5]; //TODO:相手人数分のインスタンスの生成
    Ship ships[] = new Ship[Server.SHIPS.length];

    Attacker(String inputName) {
        super(inputName);
        setJobCode(Server.ATTACKER);
    }

    @Override
    public void newGame() throws SocketException {
        //船を配置する
        System.out.println("船の配置を決めます。");
        manager = new ShipManager();
        for (int i = 0; i < Server.SHIPS.length; i++) {
            int x, y, dir;
            do {
                System.out.println(Server.SHIPS[i] + "の設定をします。"
                        + Server.SHIPS[i] + "の大きさは" + Server.SHIPS_SIZE[i] + "です。" +
                        "始点の位置を決めてください");
                System.out.print("x:");
                x = inputInt(0, Server.FIELD_SIZE_X);
                System.out.print("y:");
                y = inputInt(0, Server.FIELD_SIZE_Y);
                System.out.println("設置する方向を決めてください");
                System.out.println("上:0 右:1 下:2 左:3");
                dir = inputInt(0, 3);
            } while (!manager.canSetShip(Server.SHIPS_SIZE[i], x, y, dir));
            manager.setMyShip(Server.SHIPS[i], Server.SHIPS_SIZE[i], x, y, dir);
        }
        //サーバーに船の配置を送信する
        cmd.send(Server.SET_SHIPS);
        cmd.send(manager.getMyShips());
        System.out.println("他のプレイヤーの選択を待っています…");
        manager.setOthersShips((ShipNew[][][]) cmd.receiveObject());
        System.out.println("盤面データを受信しています…");
    }

    @Override
    //攻撃する位置を指定する。
    public void nextTurn() throws SocketException {
        int turnNo = 1;
        while (true) {
            int x, y;
            System.out.println("どこを攻撃しますか？");//攻撃場所の読み込み
            System.out.print("x:");
            x = inputInt(0, Server.FIELD_SIZE_X);
            System.out.print("y:");
            y = inputInt(0, Server.FIELD_SIZE_Y);

//            mySea.FieldAttack(x - 1, y - 1);
//            mySea.show();
            System.out.println("他のプレイヤーが選択するのを待っています...");

            // 入力されたマスを送信する。
            cmd.send(Server.ATTACK);
            cmd.send(new AttackCommand(groupID, playerID, turnNo++, x, y));
            // TODO:入力されたマスを受信する。
            System.out.println("# Turn" + turnNo + " START!");
            for (int i = 0; i < playersList.size(); i++) {
                AttackCommand command = (AttackCommand) cmd.receiveObject();
//                if (command.getPlayerID() != playerID) {
                //TODO: 要処理
                int state = manager.isBombed(command.getPlayerID(), command.getX(), command.getY());
                System.out.println("Turn" + command.getTurnNo() + " " + command.getPlayerID() + " : " + state);
//                }
            }
            for (int i = 0; i < playersList.size(); i++) {
                manager.show(i);
            }
            if (!manager.isAlive(playerID)) {
                System.out.println("あなたは死にました。");
            }
            scanner.next();
//            // TODO:全員攻撃完了した後の処理
//            for (int i = 0; i < 5; i++) { //TODO:プレイヤーの人数分の攻撃処理
//                for (int j = 0; j < Server.SHIPS.length; j++) {
//                    mySea.FieldAttacked(ships[j].bombed(3 - 1, 2 - 1), 3 - 1, 2 - 1);
//                }
//            }
//            int life = 3;
//            for (int k = 0; k < Server.SHIPS.length; k++) {
//                if (ships[k].shipLife()) {
//                    life--;
//                } else {
//                }
//            }
//            if (life <= 0) {
//                System.out.println("戦闘不能です。");
//                break;
//            }
        }
    }

}
