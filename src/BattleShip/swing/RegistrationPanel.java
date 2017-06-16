package BattleShip.swing;

import BattleShip.Attacker;
import BattleShip.Bystander;
import BattleShip.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;

public class RegistrationPanel extends JPanel {

    JTextField tf_name;
    MainFrame mf;
    String str;
    String user;//フォームから入力されたプレイヤーの名前
    int position;

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
        list.setFont(new Font(MainFrame.DISPLAY_FONT, Font.PLAIN, m.font));

        JButton bt_reg = new JButton("登録");//button作成
        bt_reg.setSize(200, 200);
        bt_reg.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
        bt_reg.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        user = tf_name.getText();
                        position = list.getSelectedIndex();
                        if (position == -1) {
                            JOptionPane.showMessageDialog(mf, "役割を選んでください。", "エラー", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        JOptionPane.showConfirmDialog(mf, "ユーザー名: " + tf_name.getText()
                                        + "\n役割: " + list.getSelectedValue() + "\nでプレイヤーを登録します。",
                                "確認", JOptionPane.OK_CANCEL_OPTION);
                        switch (position) {
                            case Server.ATTACKER:
                                mf.player = new Attacker(user);
                                break;
                            case Server.BYSTANDER:
                                mf.player = new Bystander(user);
                                break;
                        }
                        try {
                            register();
                        } catch (SocketException e1) {
                            e1.printStackTrace();
                        }
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
        layout.setConstraints(bt_reg, gbc);

        this.add(lb_name);
        this.add(tf_name);
        this.add(lb_job);
        this.add(list);
        this.add(bt_reg);
    }

    private void register() throws SocketException {
        mf.cmd.send(Server.REGISTER);
        mf.player.setGroupID(mf.groupID);
        mf.cmd.send(mf.groupID);
        if (!mf.cmd.receiveBoolean()) {
            JOptionPane.showMessageDialog(mf, "このグループには参加できません。", "エラー", JOptionPane.WARNING_MESSAGE);
        }
        mf.cmd.send(mf.player);
        mf.player.setParent(mf.cmd.receiveBoolean());
        mf.player.setPlayerID(mf.cmd.receiveInt());
        mf.player.setCmd(mf.cmd);
        JOptionPane.showMessageDialog(mf, "グループ" + mf.groupID + "に参加しました。", "登録完了", JOptionPane.INFORMATION_MESSAGE);
        System.out.println(mf.cmd.receiveString());
        if (mf.player.isParent()) {
            System.out.println("\u001b[35mあなたはこのゲームの親です。”end”を入力すると参加募集を締め切ります。\u001b[0m"); //TODO: GUI
        }
    }
}
