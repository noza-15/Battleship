package BattleShip.swing;

import BattleShip.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;

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
        this.setSize(m.width, m.height);
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
                        group = text.getText();
                        System.out.println(group);
                        if ("".equals(group)) {
                            JLabel label = new JLabel("グループ名を入力してください");
                            label.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
                            label.setForeground(Color.RED);
                            JOptionPane.showMessageDialog(m, label);
                        } else {
                            setVisible(false);
                            m.rp.setVisible(true);
                        }
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

        /*        JLabel label1 = new JLabel("グループ新規登録");//label作成
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(label1, gbc);

        JLabel label2 = new JLabel("グループ参加");
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(label2, gbc);

        text = new JTextField(10);//text作成
        text.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(text, gbc);

        JButton button1 = new JButton("登録");//button1作成
        button1.setSize(200, 200);
        button1.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
        button1.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        group = text.getText();
                        System.out.println(group);
                        if ("".equals(group)) {
                            JLabel label = new JLabel("グループ名を入力してください");
                            label.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
                            label.setForeground(Color.RED);
                            JOptionPane.showMessageDialog(m, label);
                        } else {
                            setVisible(false);
                            m.rp.setVisible(true);
                        }
                    }
                }
        );
        */
//        gbc.gridx = 2;
//        gbc.gridy = 1;
//        gbc.weightx = 1.0d;
//        gbc.weighty = 1.0d;
//        layout.setConstraints(bt_newGrp, gbc);
        mf.cmd.send(Server.LIST_GROUP);
        int grpCnt = mf.cmd.receiveInt();
        //TODO: GUI
        String[] grpList = new String[grpCnt];
        for (int i = 0; i < grpCnt; i++) {
            grpList[i] = mf.cmd.receiveString();
        }
//        String[] initData = {"First", "Second", "Third", "a", "b", "c"};//初期データを登録
        JList list = new JList(grpList);//リスト作成

        JButton bt_joinGrp = new JButton("既存グループに参加");
        bt_joinGrp.setSize(200, 200);
        bt_joinGrp.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
        bt_joinGrp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        group = (String) list.getSelectedValue();
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
        JTextArea lb_joinGrp;
        if (groupCount == 0) {
            lb_joinGrp = new JTextArea("既存のグループに参加します。\n現在グループが存在しません。");
            bt_joinGrp.setEnabled(false);

        } else {
            lb_joinGrp = new JTextArea("既存のグループに参加します。現在" + groupCount
                    + "グループあります。\n親は最初にそのグループの" + Server.JOB[0] + "になった人です。");
        }
//        lb_newGrp.setHorizontalAlignment(JLabel.CENTER);
        lb_joinGrp.setFont(new Font(MainFrame.DISPLAY_FONT, Font.PLAIN, m.font - 5));
        lb_joinGrp.setOpaque(false);
        lb_joinGrp.setEditable(false);
        lb_joinGrp.setFocusable(false);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(lb_joinGrp, gbc);

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


//        this.add(label1);
//        this.add(text);
        this.add(lb_inst);
        this.add(lb_newGrp);
        this.add(bt_newGrp);
        this.add(lb_joinGrp);
//        this.add(label2);
        this.add(sp);
        this.add(bt_joinGrp);
    }
}
