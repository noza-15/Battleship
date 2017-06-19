package BattleShip;

import java.util.Scanner;

class ServerPrompt extends Thread {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine().toLowerCase();
            switch (command) {
                case "list player":
                    if (Server.allPlayerList.size() == 0) {
                        System.out.println("プレイヤーがいません。");
                    }
                    for (int i = 0; i < Server.allPlayerList.size(); i++) {
                        System.out.println(i + 1 + ": " + Server.allPlayerList.get(i));
                    }
                    break;
                case "list group":
                    if (Server.groupList.size() == 0) {
                        System.out.println("グループが存在しません。");
                    }
                    for (int i = 0; i < Server.groupList.size(); i++) {
                        System.out.println(i + 1 + ": " + Server.groupList.get(i));
                    }
                    break;
                case "remove group":
                    System.out.println("削除するグループのIDを入力してください。");
                    int id = Integer.parseInt(scanner.nextLine());
                    if (id >= Server.groupList.size()) {
                        System.out.println("グループが存在しません。");
                        break;
                    }
                    System.out.println("次のグループを削除します。 y/n\n" + Server.groupList.get(id));
                    if (scanner.nextLine().toLowerCase().equals("y")) {
                        Server.groupList.set(id, new GameGroup(id));
                    }
                    System.out.println("削除が完了しました。");
                    break;
                case "quit":
                case "exit":
                    System.exit(1);
                    break;
                default:
                    System.out.println(command + ": コマンドが見つかりません。");
                    break;
            }
        }
    }
}
