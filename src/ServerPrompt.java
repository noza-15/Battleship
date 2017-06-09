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
