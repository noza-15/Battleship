package BattleShip;

import java.net.SocketException;

public class Attacker extends Player {

    private static final long serialVersionUID = -1038402005141356247L;

    Attacker(String inputName) {
        super(inputName);
        setJobCode(Server.ATTACKER);
    }

    @Override
    public void newGame() throws SocketException {
        manager = new ShipManager();
        //船を配置する
        System.out.println("船の配置を決めます。");
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
        /*
        System.out.println("他のプレイヤーの選択を待っています…");
        manager.setOthersShips((BattleShip.Ship[][][]) cmd.receiveObject());
        System.out.println("盤面データを受信しています…");*/
        super.newGame();
    }

    @Override
    //攻撃する位置を指定する。
    public void nextTurn() throws SocketException {
        int turnNo = 1;
        while (true) {
            if (manager.isAlive(playerID)) {
                int x, y;
                System.out.println("どこを攻撃しますか？");//攻撃場所の読み込み
                System.out.print("x:");
                x = inputInt(0, Server.FIELD_SIZE_X);
                System.out.print("y:");
                y = inputInt(0, Server.FIELD_SIZE_Y);

                cmd.send(Server.ATTACK);
                cmd.send(new AttackCommand(groupID, playerID, turnNo++, x, y));
            } else {
                cmd.send(Server.ATTACK);
                cmd.send(new AttackCommand(groupID, playerID, turnNo++, -1, -1));
            }
            super.nextTurn();
            if (!manager.isAlive(playerID)) {
                System.out.println("あなたは死にました。戦闘不能です");
            }
            scanner.next();
        }
    }

}