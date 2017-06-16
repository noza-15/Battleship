package BattleShip.swing;

import BattleShip.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationPanel extends JPanel {

    JTextField tf_name;
    MainFrame mf;
    String str;
    String user;//フォームから入力されたプレイヤーの名前
    String position;

    public RegistrationPanel(MainFrame m, String s) {
        mf = m;
        str = s;

        this.setName(s);
        this.setSize(MainFrame.WIN_WIDTH, MainFrame.WIN_HEIGHT);
        //レイアウトを設定
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lb_name = new JLabel("プレイヤー名");//label作成
        lb_name.setHorizontalAlignment(JLabel.CENTER);
        lb_name.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
        JLabel lb_job = new JLabel("役割");
        lb_job.setHorizontalAlignment(JLabel.CENTER);
        lb_job.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));

        tf_name = new JTextField(10);//text作成
        tf_name.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));


        JList<String> list = new JList<>(Server.JOB);//リスト作成
        list.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));

        JButton button = new JButton("登録");//button作成
        button.setSize(200, 200);
        button.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
        button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showConfirmDialog(mf, "ユーザー名: " + tf_name.getText()
                                        + "\n役割: " + list.getSelectedValue() + "\nでプレイヤーを登録します。",
                                "確認", JOptionPane.OK_CANCEL_OPTION);
                        user = tf_name.getText();
                        position = list.getSelectedValue();
                        m.setSize(MainFrame.WIN_WIDTH, MainFrame.WIN_HEIGHT);
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
        layout.setConstraints(lb_name, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        layout.setConstraints(tf_name, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        layout.setConstraints(lb_job, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        layout.setConstraints(list, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        layout.setConstraints(button, gbc);

        this.add(lb_name);
        this.add(tf_name);
        this.add(lb_job);
        this.add(list);
        this.add(button);
    }
}
