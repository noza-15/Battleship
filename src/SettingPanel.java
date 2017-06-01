 
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingPanel extends JPanel{
  JButton btn;
  JLabel paneltitle;
  MainFrame mf;
  String str;

  public SettingPanel(MainFrame m, String s){
    mf = m;
    str = s;
    this.setName("Setting");
    this.setLayout(null);
    this.setSize(400, 200);
    paneltitle = new JLabel("これは"
            +getClass().getCanonicalName()+"クラスのパネルです");
    paneltitle.setBounds(0, 5, 400, 40);
    this.add(paneltitle);
    btn = new JButton("ButtlePanelに移動");
    btn.setBounds(20, 50, 150, 40);
    btn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            pc(mf.PanelNames[3]);
        }
    });
    this.add(btn);
    this.setBackground(Color.getHSBColor(65, 0.7f, 0.9f));
  }

  public void pc(String str){
    mf.PanelChange((JPanel)this, str);
  }
}