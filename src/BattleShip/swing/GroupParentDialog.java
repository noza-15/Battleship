package BattleShip.swing;

import BattleShip.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;

class GroupParentDialog extends JDialog {
    final int[] atkCnt = new int[1];
    final int[] bysCnt = new int[1];
    final String[][] memList = {null};
    MainFrame mf;
    JList<String> ls_member;
    JScrollPane sp = new JScrollPane();
    JTextArea lb_mes;
    GroupParentDialog pd = this;
    Thread thread;
    Boolean continuable;

    GroupParentDialog(MainFrame mf) {
        super(mf);
        this.mf = mf;
        this.setModal(true);
        GridBagLayout layout = new GridBagLayout();
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        setTitle("参加募集締め切り");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        lb_mes = new JTextArea("あなたはこのゲームの親です。\n「締め切る」ボタンを押すと参加募集を締め切ります。");
        lb_mes.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, 16));
        lb_mes.setOpaque(false);
        lb_mes.setEditable(false);
        lb_mes.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        layout.setConstraints(lb_mes, gbc);
        this.add(lb_mes);

        getList();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        gbc.insets = new Insets(0, 0, 0, 0);
        sp.setPreferredSize(new Dimension(500, 200));
        layout.setConstraints(sp, gbc);
        this.add(sp);

        JButton bt_refresh = new JButton("更新");
        bt_refresh.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getList();
                    }
                }
        );

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0d;
        gbc.weighty = 0.5d;
        layout.setConstraints(bt_refresh, gbc);
        this.add(bt_refresh);

        JButton bt_end = new JButton("締め切る");
        bt_end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getList();
                if (atkCnt[0] < 2) {
                    JOptionPane.showMessageDialog(mf, "ゲームを開始するには、2人以上のAttackerが必要です。",
                            "エラー", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                mf.cmd.send(Server.CLOSE_APPLICATIONS);
                try {
                    if (!mf.cmd.receiveBoolean()) {
                        mf.cmd.receiveString();
                    }
                    mf.cmd.send(Server.START);
                    mf.cmd.receiveInt();
                } catch (SocketException e1) {
                    JOptionPane.showMessageDialog(mf, "サーバーとの接続が失われました。",
                            "接続エラー", JOptionPane.WARNING_MESSAGE);
                }
                continuable = false;
                pd.dispose();

            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0d;
        gbc.weighty = 0.5d;
        layout.setConstraints(bt_end, gbc);
        this.add(bt_end);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (continuable) {
                    getList();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };
        continuable = true;
        thread = new Thread(runnable);
        thread.start();

        setVisible(true);

    }

    void getList() {
        mf.cmd.send(Server.LIST_MEMBERS);
        try {
            atkCnt[0] = mf.cmd.receiveInt();
            bysCnt[0] = mf.cmd.receiveInt();
            memList[0] = new String[atkCnt[0] + bysCnt[0]];
            for (int i = 0; i < memList[0].length; i++) {
                memList[0][i] = mf.cmd.receiveString();
            }
            ls_member = new JList<>(memList[0]);
            ls_member.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, mf.FONT_SIZE - 5));
            sp.getViewport().setView(ls_member);
            lb_mes.setText("あなたはこのゲームの親です。\n" +
                    "「締め切る」ボタンを押すと参加募集を締め切ります。\n" +
                    "  現在  Attacker: " + atkCnt[0] + "人  Bystander: " + bysCnt[0] + "人");
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(mf, "サーバーとの接続が失われました。",
                    "接続エラー", JOptionPane.WARNING_MESSAGE);
        }
    }
}
