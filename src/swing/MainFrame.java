package swing;

import javax.swing.*;

public class MainFrame extends JFrame{
  private static final String TITLE = "TITLE";//frameのtitle
  int width = 1500;
  int height = 1000;
  int font = 50;

  StartPanel sp = new StartPanel(this,"StartPanel");
  GroupPanel gp = new GroupPanel(this,"GroupPanel");
  RegistrationPanel rp = new RegistrationPanel(this,"RegistrationPanel");
  SettingPanel setp = new SettingPanel(this,"SettingPanel");
  // GamePanel game = new GamePanel(this,"GamePanel");

  public MainFrame(){
    this.add(sp);sp.setVisible(true);
    this.add(gp);gp.setVisible(false);
    this.add(rp);rp.setVisible(false);
    this.add(setp);setp.setVisible(false);

    this.setTitle(MainFrame.TITLE);
    this.setSize(this.width, this.height);
    this.setLocationRelativeTo(null);//フレームを真ん中にセットする
    this.setResizable(false);//フレームの大きさを変更不可にする
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//フレームを閉じるとプログラムが終了する
    this.setVisible(true);
  }

  public static void main(String[] args) {
    MainFrame main = new MainFrame();
  }
}
