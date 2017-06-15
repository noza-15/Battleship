package BattleShip.swing;

import BattleShip.CommandHandler;
import BattleShip.Server;

import javax.swing.*;

public class MainFrame extends JFrame {
    static final String DISPLAY_FONT = "MS ゴシック";
    private static final String TITLE = Server.GAME_NAME;//frameのtitle
    int width = 750;
    //    int width = 1500;
    int height = 500;
    //    int height = 1000;
    int font = 20;
    //    int font = 50;

    StartPanel sp;
    GroupPanel gp;
    RegistrationPanel rp;
    SettingPanel setp;
    // GamePanel game = new GamePanel(this,"GamePanel");
    CommandHandler cmd;

    public MainFrame() {

        this.setTitle(TITLE);
        this.setSize(this.width, this.height);
        this.setLocationRelativeTo(null);//フレームを真ん中にセットする
        this.setResizable(false);//フレームの大きさを変更不可にする
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//フレームを閉じるとプログラムが終了する
        this.setVisible(true);

    }

    public static void main(String[] args) {
        MainFrame main = new MainFrame();
        main.sp = new StartPanel(main, "StartPanel");
        main.add(main.sp);
        main.sp.setVisible(true);

        main.rp = new RegistrationPanel(main, "RegistrationPanel");
        main.setp = new SettingPanel(main, "SettingPanel");

        main.add(main.rp);
        main.rp.setVisible(false);
        main.add(main.setp);
        main.setp.setVisible(false);

    }
}
