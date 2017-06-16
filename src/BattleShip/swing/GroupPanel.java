package BattleShip.swing;

import BattleShip.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.util.Scanner;

public class GroupPanel extends JPanel {

    JTextField text;
    MainFrame mf;
    String str;
    String group;

    public GroupPanel(MainFrame m, String s) throws SocketException {
        mf = m;
        str = s;
        int groupCount;

        this.setName(s);
        this.setSize(MainFrame.WIN_WIDTH, MainFrame.WIN_HEIGHT);
        //レイアウトを設定
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        mf.cmd.send(Server.CHECK_GROUP_EXISTENCE);
        groupCount = mf.cmd.receiveInt();
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel lb_inst = new JLabel("操作を選択してください。");
        lb_inst.setHorizontalAlignment(JLabel.CENTER);
        lb_inst.setFont(new Font(MainFrame.DISPLAY_FONT, Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(lb_inst, gbc);

        JButton bt_newGrp = new JButton("新規グループを作成");//button1作成
        bt_newGrp.setSize(200, 200);
        bt_newGrp.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
        bt_newGrp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mf.cmd.send(Server.NEW_GROUP);
                        try {
                            mf.groupID = mf.cmd.receiveInt();
                            JOptionPane.showMessageDialog(mf, "新しくグループ " + mf.groupID + " を作成します。",
                                    "グループの新規作成", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SocketException se) {
                            JOptionPane.showMessageDialog(mf, "サーバーとの接続が失われました。",
                                    "接続エラー", JOptionPane.WARNING_MESSAGE);
                            se.printStackTrace();
                        }
                        mf.rp = new RegistrationPanel(mf, "RegistrationPanel");
                        mf.add(mf.rp);
                        setVisible(false);
                        mf.rp.setVisible(true);
                    }
                }
        );
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(bt_newGrp, gbc);

        JTextArea lb_newGrp = new JTextArea("新規にグループを作成します。\n親は最初にそのグループの" + Server.JOB[0] + "になった人です。");
//        lb_newGrp.setHorizontalAlignment(JLabel.CENTER);
        lb_newGrp.setFont(new Font(MainFrame.DISPLAY_FONT, Font.PLAIN, m.font - 5));
        lb_newGrp.setOpaque(false);
        lb_newGrp.setEditable(false);
        lb_newGrp.setFocusable(false);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(lb_newGrp, gbc);

        mf.cmd.send(Server.LIST_GROUP);
        int grpCnt = mf.cmd.receiveInt();

        final String[][] grpList = {new String[grpCnt]};
        for (int i = 0; i < grpCnt; i++) {
            grpList[0][i] = mf.cmd.receiveString();
        }
        JList<String> list = new JList<>(grpList[0]);//リスト作成

        JButton bt_joinGrp = new JButton("既存グループに参加");
        bt_joinGrp.setSize(200, 200);
        bt_joinGrp.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
        bt_joinGrp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String o = (String) JOptionPane.showInputDialog(mf, "参加するグループを選んでください。",
                                "グループ選択", JOptionPane.PLAIN_MESSAGE, null, grpList[0], grpList[0][0]);
                        System.out.println(new Scanner(o).useDelimiter(":").nextInt());
                        group = list.getSelectedValue();
                        setVisible(false);
                        m.rp.setVisible(true);
                    }
                }
        );
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(bt_joinGrp, gbc);
        final JTextArea[] lb_joinGrp = new JTextArea[1];
        if (groupCount == 0) {
            lb_joinGrp[0] = new JTextArea("既存のグループに参加します。\n現在グループが存在しません。");
            bt_joinGrp.setEnabled(false);
        } else {
            lb_joinGrp[0] = new JTextArea("既存のグループに参加します。現在" + groupCount
                    + "グループあります。\n親は最初にそのグループの" + Server.JOB[0] + "になった人です。");
        }
//        lb_newGrp.setHorizontalAlignment(JLabel.CENTER);
        lb_joinGrp[0].setFont(new Font(MainFrame.DISPLAY_FONT, Font.PLAIN, m.font - 5));
        lb_joinGrp[0].setOpaque(false);
        lb_joinGrp[0].setEditable(false);
        lb_joinGrp[0].setFocusable(false);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(lb_joinGrp[0], gbc);

        // list.setFixedCellWidth(100);
        // list.setFixedCellHeight(100);
        list.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
        JScrollPane sp = new JScrollPane();
        sp.getViewport().setView(list);
        sp.setPreferredSize(new Dimension(300, 200));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(sp, gbc);

        JButton bt_refresh = new JButton("更新");
        bt_refresh.setSize(200, 200);
        bt_refresh.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
        bt_refresh.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mf.cmd.send(Server.CHECK_GROUP_EXISTENCE);
                        int groupCount = 0;
                        try {
                            groupCount = mf.cmd.receiveInt();
                            mf.cmd.send(Server.LIST_GROUP);
                            int grpCnt = mf.cmd.receiveInt();
                            grpList[0] = new String[grpCnt];
                            for (int i = 0; i < grpCnt; i++) {
                                grpList[0][i] = mf.cmd.receiveString();
                                JList<String> list = new JList<>(grpList[0]);
                                sp.getViewport().setView(list);
                            }

                        } catch (SocketException e1) {
                            JOptionPane.showMessageDialog(mf, "サーバーとの接続が失われました。",
                                    "接続エラー", JOptionPane.WARNING_MESSAGE);
                            e1.printStackTrace();
                        }
                        if (groupCount == 0) {
                            bt_joinGrp.setEnabled(false);
                            lb_joinGrp[0].setText("既存のグループに参加します。\n現在グループが存在しません。");
                        } else {
                            bt_joinGrp.setEnabled(true);
                            lb_joinGrp[0].setText("既存のグループに参加します。現在" + groupCount
                                    + "グループあります。\n親は最初にそのグループの" + Server.JOB[0] + "になった人です。");
                        }

                    }
                }
        );
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(bt_refresh, gbc);

        this.add(lb_inst);
        this.add(lb_newGrp);
        this.add(bt_newGrp);
        this.add(lb_joinGrp[0]);
        this.add(bt_joinGrp);
        this.add(bt_refresh);
        this.add(sp);
    }
}
