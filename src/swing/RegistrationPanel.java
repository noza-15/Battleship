package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationPanel extends JPanel{

    JTextField text;
    MainFrame mf;
    String str;
    String user;//フォームから入力されたプレイヤーの名前
    String position;

    public RegistrationPanel(MainFrame m, String s){
        mf = m;
        str = s;

        this.setName(s);
        this.setSize(m.width, m.height);
        //レイアウトを設定
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel label1 = new JLabel("ユーザー名");//label作成
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setFont(new Font("MS ゴシック", Font.BOLD,m.font));
        JLabel label2 = new JLabel("役職");
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setFont(new Font("MS ゴシック", Font.BOLD,m.font));

        text = new JTextField(10);//text作成
        text.setFont(new Font("MS ゴシック", Font.BOLD,m.font));

        String[] initData = {"Player","Bystander"};//初期データを登録
        JList list = new JList(initData);//リスト作成
        list.setFont(new Font("MS ゴシック", Font.BOLD,m.font));

        JButton button = new JButton("登録");//button作成
        button.setSize(200, 200);
        button.setFont(new Font("MS ゴシック", Font.BOLD,m.font));
        button.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        user = text.getText();
                        position = (String)list.getSelectedValue();
                        m.setSize(1500, 1000);
                        m.setLocationRelativeTo(null);
                        setVisible(false);
                        m.setp.setVisible(true);
                    }
                }
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(label1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        layout.setConstraints(text, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        layout.setConstraints(label2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        layout.setConstraints(list, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        layout.setConstraints(button, gbc);

        this.add(label1);
        this.add(text);
        this.add(label2);
        this.add(list);
        this.add(button);
    }
}
