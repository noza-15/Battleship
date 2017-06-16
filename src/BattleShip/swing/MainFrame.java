package BattleShip.swing;

import BattleShip.CommandHandler;
import BattleShip.Player;
import BattleShip.Server;

import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.*;

public class MainFrame extends JFrame {
    //    static final String DISPLAY_FONT = "MS ゴシック";
    static final String DISPLAY_FONT = "メイリオ";
    static final int WIN_WIDTH = 750;
    //    int WIN_WIDTH = 1500;
    static final int WIN_HEIGHT = 500;
    private static final String TITLE = Server.GAME_NAME;//frameのtitle
    //    int WIN_HEIGHT = 1000;
    int font = 20;
    //    int font = 50;

    StartPanel sp;
    GroupPanel gp;
    RegistrationPanel rp;
    SettingPanel setp;
    // GamePanel game = new GamePanel(this,"GamePanel");
    CommandHandler cmd;
    int groupID;
    Player player;
    Sequencer sequencer;
    Sequence sequence;

    public MainFrame() {

        this.setTitle(TITLE);
        this.setSize(WIN_WIDTH, WIN_HEIGHT);
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
