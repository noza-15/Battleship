import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private String inputName;
    private int jobCode;
    private Player player;
    private int clientID;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("***レーダー作戦ゲームβ***");
        System.out.println("サーバーのアドレスを入力してください。");
        String inputaddr;
        inputaddr = scanner.nextLine();
        try {
            InetAddress address = InetAddress.getByName(inputaddr);
            Socket socket = new Socket(address, 8080);
            System.out.println("socket = " + socket);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            Client client = new Client();
            client.init();
            out.println(Server.REGISTER);
            out.println(client.inputName);
            out.println(client.jobCode);
            System.out.println(in.readLine());
            System.out.println("操作を選択してください。\n" +
                    "1) 新規グループを作成\n" +
                    "2) 既存グループに参加");
            int op = scanner.nextInt();
            if(op == 1){
                out.println(Server.NEW_GROUP);
            }

            scanner.next();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }

    void init() {
        setName();
        chooseJob();
    }

    void setName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("名前を入力してください。");
        inputName = scanner.next();
    }

    void chooseJob() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("役割を選んでください。\nAttacker(攻撃者), Bystander(傍観者)");
        switch (scanner.next().toLowerCase()) {
            case "a":
            case "attacker":
                jobCode = Server.ATTACKER;
                player = new Attacker(inputName);
                System.out.println("Attacker(攻撃者)が選択されました。");
                break;
            case "b":
            case "bystander":
                jobCode = Server.BYSTANDER;
                player = new Bystander(inputName);
                System.out.println("Bystander(傍観者)が選択されました。");
                break;
            default:
                System.out.println("AかBを入力してください。");
                chooseJob();
                break;
        }
    }
}
