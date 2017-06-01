 
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{
  public String[] PanelNames = {"start", "registration", "setting", "buttle"};
  StartPanel start = new StartPanel(this, PanelNames[0]);
  RegistrationPanel registration = new RegistrationPanel(this, PanelNames[1]);
  SettingPanel setting = new SettingPanel(this, PanelNames[2]);
  ButtlePanel buttle = new ButtlePanel(this, PanelNames[3]);

  public MainFrame(){
    this.add(start); start.setVisible(true);
    this.add(registration); registration.setVisible(false);
    this.add(setting); setting.setVisible(false);
    this.add(buttle); buttle.setVisible(false);
  }

  public static void main(String args[]){
    MainFrame mf = new MainFrame();
    mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
    mf.setVisible(true);
  }

  public void PanelChange(JPanel jp, String str){
    String name = jp.getName();
    if (name == PanelNames[0]){
      start = (StartPanel)jp;
      start.setVisible(false);
    }else if(name==PanelNames[1]){
      registration = (RegistrationPanel)jp;
      registration.setVisible(false);
    }else if(name==PanelNames[2]){
      setting = (SettingPanel)jp;
      setting.setVisible(false);
    }else if(name==PanelNames[3]){
      buttle = (ButtlePanel)jp;
      buttle.setVisible(false);
    }
    if(str==PanelNames[0]){
      start.setVisible(true);
    }else if(str==PanelNames[1]){
      registration.setVisible(true);
    }else if(str==PanelNames[2]){
      setting.setVisible(true);
    }else if(str==PanelNames[3]){
      buttle.setVisible(true);
    }
  }
}
