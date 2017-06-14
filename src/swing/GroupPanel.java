package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupPanel extends JPanel{

  JTextField text;
  MainFrame mf;
  String str;
  String group;

  public GroupPanel(MainFrame m, String s){
    mf = m;
    str = s;

    this.setName(s);
    this.setSize(m.width, m.height);
    //レイアウトを設定
    GridBagLayout layout = new GridBagLayout();
    this.setLayout(layout);
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel label1 = new JLabel("グループ新規登録");//label作成
    label1.setHorizontalAlignment(JLabel.CENTER);
    label1.setFont(new Font("MS ゴシック", Font.BOLD,m.font));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0d;
    gbc.weighty = 1.0d;
    layout.setConstraints(label1, gbc);

    JLabel label2 = new JLabel("グループ参加");
    label2.setHorizontalAlignment(JLabel.CENTER);
    label2.setFont(new Font("MS ゴシック", Font.BOLD,m.font));
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1.0d;
    gbc.weighty = 1.0d;
    layout.setConstraints(label2, gbc);

    text = new JTextField(10);//text作成
    text.setFont(new Font("MS ゴシック", Font.BOLD,m.font));
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 1.0d;
    gbc.weighty = 1.0d;
    layout.setConstraints(text, gbc);

    JButton button1 = new JButton("登録");//button1作成
    button1.setSize(200, 200);
    button1.setFont(new Font("MS ゴシック", Font.BOLD,m.font));
    button1.addActionListener(
            new ActionListener(){
              public void actionPerformed(ActionEvent e){
                group = text.getText();
                System.out.println(group);
                if("".equals(group)){
                  JLabel label = new JLabel("グループ名を入力してください");
                  label.setFont(new Font("MS ゴシック", Font.BOLD,m.font));
                  label.setForeground(Color.RED);
                  JOptionPane.showMessageDialog(m, label);
                }else{
                  setVisible(false);
                  m.rp.setVisible(true);
                }
              }
            }
    );
    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.weightx = 1.0d;
    gbc.weighty = 1.0d;
    layout.setConstraints(button1, gbc);

    String[] initData = {"First","Second","Third","a","b","c"};//初期データを登録
    JList list = new JList(initData);//リスト作成
    // list.setFixedCellWidth(100);
    // list.setFixedCellHeight(100);
    list.setFont(new Font("MS ゴシック", Font.BOLD,m.font));
    JScrollPane sp = new JScrollPane();
    sp.getViewport().setView(list);
    sp.setPreferredSize(new Dimension(300, 200));
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.weightx = 1.0d;
    gbc.weighty = 1.0d;
    layout.setConstraints(sp, gbc);

    JButton button2 = new JButton("参加");
    button2.setSize(200, 200);
    button2.setFont(new Font("MS ゴシック", Font.BOLD,m.font));
    button2.addActionListener(
            new ActionListener(){
              public void actionPerformed(ActionEvent e){
                group = (String)list.getSelectedValue();
                setVisible(false);
                m.rp.setVisible(true);
              }
            }
    );
    gbc.gridx = 2;
    gbc.gridy = 1;
    gbc.weightx = 1.0d;
    gbc.weighty = 1.0d;
    layout.setConstraints(button2, gbc);

    this.add(label1);
    this.add(text);
    this.add(button1);
    this.add(label2);
    this.add(sp);
    this.add(button2);
  }
}
