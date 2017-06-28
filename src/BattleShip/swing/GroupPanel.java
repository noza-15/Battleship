package BattleShip.swing;

import BattleShip.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.util.Scanner;

public class GroupPanel extends JPanel {

    MainFrame mf;

    public GroupPanel(MainFrame m) {
        mf = m;
        int groupCount = 0;

        this.setSize(MainFrame.WIN_WIDTH, MainFrame.WIN_HEIGHT);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        mf.cmd.send(Server.CHECK_GROUP_EXISTENCE);
        try {
            groupCount = mf.cmd.receiveInt();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        JLabel lb_inst = new JLabel("操作を選択してください。");
        lb_inst.setHorizontalAlignment(JLabel.CENTER);
        lb_inst.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(lb_inst, gbc);
        this.add(lb_inst);

        JButton bt_newGrp = new JButton("新規グループを作成");
        bt_newGrp.setSize(200, 200);
        bt_newGrp.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, MainFrame.FONT_SIZE));
        int finalGroupCount = groupCount;
        bt_newGrp.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int choice = JOptionPane.showConfirmDialog(mf, "新しくグループ " + finalGroupCount + " を作成します。",
                                "グループの新規作成", JOptionPane.YES_NO_OPTION);
                        try {
                            mf.cmd.send(Server.NEW_GROUP);
                            mf.groupID = mf.cmd.receiveInt();
                        } catch (SocketException se) {
                            JOptionPane.showMessageDialog(mf, "サーバーとの接続が失われました。",
                                    "接続エラー", JOptionPane.WARNING_MESSAGE);
                            se.printStackTrace();
                        }
                        if (choice == JOptionPane.NO_OPTION) {
                            return;
                        }
                        mf.rp = new RegistrationPanel(mf);
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
        this.add(bt_newGrp);

        JLabel lb_newGrp = new JLabel("<html>新規にグループを作成します。<br>親は最初にそのグループの" + Server.JOB[0] + "になった人です。");
        lb_newGrp.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, MainFrame.FONT_SIZE - 5));
        lb_newGrp.setHorizontalAlignment(SwingConstants.CENTER);
        lb_newGrp.setFocusable(false);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(lb_newGrp, gbc);
        this.add(lb_newGrp);

        mf.cmd.send(Server.LIST_GROUP);
        int grpCnt = 0;
        try {
            grpCnt = mf.cmd.receiveInt();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        final String[][] grpList = {new String[grpCnt]};
        try {
            for (int i = 0; i < grpCnt; i++) {
                grpList[0][i] = mf.cmd.receiveString();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        JButton bt_joinGrp = new JButton("既存グループに参加");
        bt_joinGrp.setSize(200, 200);
        bt_joinGrp.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, MainFrame.FONT_SIZE));
        bt_joinGrp.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String o = (String) JOptionPane.showInputDialog(mf, "参加するグループを選んでください。",
                                "グループ選択", JOptionPane.PLAIN_MESSAGE, null, grpList[0], grpList[0][0]);
                        try {
                            mf.groupID = new Scanner(o).useDelimiter(":").nextInt() - 1;
                        } catch (NullPointerException ne) {
                            return;
                        }
                        setVisible(false);
                        mf.rp = new RegistrationPanel(mf);
                        mf.add(mf.rp);
                        mf.rp.setVisible(true);
                    }
                }
        );
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(bt_joinGrp, gbc);
        this.add(bt_joinGrp);

        final JTextArea[] lb_joinGrp = new JTextArea[1];
        if (groupCount == 0) {
            lb_joinGrp[0] = new JTextArea("既存のグループに参加します。\n現在グループが存在しません。");
            bt_joinGrp.setEnabled(false);
        } else {
            lb_joinGrp[0] = new JTextArea("既存のグループに参加します。現在" + groupCount
                    + "グループあります。\n親は最初にそのグループの" + Server.JOB[0] + "になった人です。");
        }
//        lb_newGrp.setHorizontalAlignment(JLabel.CENTER);
        lb_joinGrp[0].setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, MainFrame.FONT_SIZE - 5));
        lb_joinGrp[0].setOpaque(false);
        lb_joinGrp[0].setEditable(false);
        lb_joinGrp[0].setFocusable(false);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(lb_joinGrp[0], gbc);
        this.add(lb_joinGrp[0]);

        JList<String> ls_group = new JList<>(grpList[0]);
        ls_group.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, MainFrame.FONT_SIZE - 5));
        JScrollPane sp = new JScrollPane();
        sp.getViewport().setView(ls_group);
        sp.setPreferredSize(new Dimension(500, 200));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(sp, gbc);
        this.add(sp);

        JButton bt_refresh = new JButton("更新");
        bt_refresh.setSize(200, 200);
        bt_refresh.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, MainFrame.FONT_SIZE));
        bt_refresh.addActionListener(
                new ActionListener() {
                    @Override
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
                                list.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, MainFrame.FONT_SIZE - 5));
                                sp.getViewport().setView(list);
                            }

                        } catch (SocketException e1) {
                            JOptionPane.showMessageDialog(mf, "サーバーとの接続が失われました。",
                                    "接続エラー", JOptionPane.WARNING_MESSAGE);
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
        this.add(bt_refresh);

    }
}
