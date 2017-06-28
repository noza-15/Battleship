package BattleShip.swing;

import BattleShip.CommandHandler;
import BattleShip.Player;
import BattleShip.Server;

import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.*;

public class MainFrame extends JFrame {
    static final String FONT_NAME = "メイリオ";
    //    static final String FONT_NAME = "MS ゴシック";
    static final int FONT_SIZE = 20;
    //    int FONT_SIZE = 50;
    static final int WIN_WIDTH = 750;
    //    int WIN_WIDTH = 1500;
    static final int WIN_HEIGHT = 500;
    //    int WIN_HEIGHT = 1000;
    private static final String TITLE = Server.GAME_NAME;//frameのtitle
    StartPanel sp;
    GroupPanel gp;
    RegistrationPanel rp;
    SettingPanel setp;
    BattlePanel bp;
    CommandHandler cmd;
    int groupID;
    Player player;
    Sequencer sequencer;
    Sequence sequence;

    public MainFrame() {
        try {
            String look = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(look);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setTitle(TITLE);
        this.setSize(WIN_WIDTH, WIN_HEIGHT);
        this.setLocationRelativeTo(null);//フレームを真ん中にセットする
        this.setResizable(false);//フレームの大きさを変更不可にする
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//フレームを閉じるとプログラムが終了する
        this.setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame main = new MainFrame();
        main.sp = new StartPanel(main);
        main.add(main.sp);
//        main.sp.setVisible(true);

        main.rp = new RegistrationPanel(main);
        main.add(main.rp);
        main.rp.setVisible(false);

        main.setp = new SettingPanel(main);
        main.add(main.setp);
        main.setp.setVisible(false);
    }
}
