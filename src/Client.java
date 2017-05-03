import java.util.Scanner;

public class Client {
    private String inputName;
    private String job;
    private Player player;

    public static void main(String[] args) {
        System.out.println("***レーダー作戦ゲームβ***");
        Client client = new Client();
        client.init();
    }

    void init(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("名前を入力してください。");
        inputName = scanner.next();
        System.out.println("役割を選んでください。\nAttacker(攻撃者),Bystander(傍観者)");
        job = scanner.next();

        switch (job.toLowerCase()) {
            case "a":
            case "attacker":
                player = new Attacker(inputName);
                break;
            case "b":
            case "bystander":
                player = new Bystander(inputName);
                break;
            default:
                System.out.println("AかBを入力してください。");
        }
    }
}
