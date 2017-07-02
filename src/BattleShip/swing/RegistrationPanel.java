package BattleShip.swing;

import BattleShip.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;


public class RegistrationPanel extends JPanel {

    MainFrame mf;

    public RegistrationPanel(MainFrame m) {
        mf = m;

        this.setSize(MainFrame.WIN_WIDTH, MainFrame.WIN_HEIGHT);
        //レイアウトを設定
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc_out = new GridBagConstraints();
        GridBagConstraints gbc_in = new GridBagConstraints();

        //プレイヤー名
        JPanel pn_name = new JPanel();
        GridBagLayout lo_name = new GridBagLayout();
        pn_name.setLayout(lo_name);
        TitledBorder bd_name = new TitledBorder(new EtchedBorder(), "プレイヤー名", TitledBorder.LEFT, TitledBorder.TOP);
        bd_name.setTitleFont(new Font(MainFrame.FONT_NAME, Font.BOLD, 20));
        pn_name.setBorder(bd_name);

        JLabel lb_name = new JLabel("プレイヤー名を入力してください。");
        lb_name.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, m.FONT_SIZE));
        gbc_in.gridx = 0;
        gbc_in.gridy = 0;
        lo_name.setConstraints(lb_name, gbc_in);
        pn_name.add(lb_name);

        JTextField tf_name = new JTextField(10);
        tf_name.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, m.FONT_SIZE));
        gbc_in.gridx = 0;
        gbc_in.gridy = 1;
        gbc_in.insets = new Insets(5, 5, 10, 5);
        lo_name.setConstraints(tf_name, gbc_in);
        pn_name.add(tf_name);
        gbc_in.insets = new Insets(0, 0, 0, 0);

        gbc_out.gridx = 0;
        gbc_out.gridy = 0;
        gbc_out.ipadx = 50;
        gbc_out.fill = GridBagConstraints.BOTH;
        gbc_out.insets = new Insets(20, 20, 20, 20);
        layout.setConstraints(pn_name, gbc_out);
        this.add(pn_name);

        //役割
        JPanel pn_job = new JPanel();
        GridBagLayout lo_job = new GridBagLayout();
        pn_job.setLayout(lo_job);

        TitledBorder bd_job = new TitledBorder(new EtchedBorder(), "役割", TitledBorder.LEFT, TitledBorder.TOP);
        bd_job.setTitleFont(new Font(MainFrame.FONT_NAME, Font.BOLD, 20));
        pn_job.setBorder(bd_job);

        JLabel lb_job = new JLabel("役割を選択してください。");
        lb_job.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, m.FONT_SIZE));
        gbc_in.gridx = 0;
        gbc_in.gridy = 0;
        gbc_in.anchor = GridBagConstraints.LINE_START;
        lo_job.setConstraints(lb_job, gbc_in);
        pn_job.add(lb_job);

        JPanel pn_jobSub = new JPanel();
        GridBagLayout lo_jobSub = new GridBagLayout();
        pn_jobSub.setLayout(lo_jobSub);

        JRadioButton rbt_job[] = new JRadioButton[Server.JOB.length];
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < Server.JOB.length; i++) {
            rbt_job[i] = new JRadioButton(Server.JOB[i]);
            rbt_job[i].setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, 20));
            group.add(rbt_job[i]);
            gbc_in.gridx = 0;
            gbc_in.gridy = i;
            gbc_in.anchor = GridBagConstraints.LINE_START;

            rbt_job[i].setHorizontalAlignment(SwingConstants.LEFT);
            lo_jobSub.setConstraints(rbt_job[i], gbc_in);
            pn_jobSub.add(rbt_job[i]);
        }
        gbc_in.gridx = 0;
        gbc_in.gridy = 1;
        gbc_in.anchor = GridBagConstraints.CENTER;
        lo_job.setConstraints(pn_jobSub, gbc_in);
        pn_job.add(pn_jobSub);

        gbc_out.gridx = 0;
        gbc_out.gridy = 2;
        gbc_out.fill = GridBagConstraints.BOTH;
        gbc_out.insets = new Insets(20, 20, 20, 20);
        layout.setConstraints(pn_job, gbc_out);
        this.add(pn_job);

        //登録
        JButton bt_reg = new JButton("登録");//button作成
        bt_reg.setSize(200, 200);
        bt_reg.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, m.FONT_SIZE));
        bt_reg.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = tf_name.getText();
                        int selectedJob = -1;
                        for (int i = 0; i < rbt_job.length; i++) {
                            if (rbt_job[i].isSelected()) {
                                selectedJob = i;
                                break;
                            }
                        }
                        if (name.equals("")) {
                            JOptionPane.showMessageDialog(mf, "プレイヤー名を入力してください。", "エラー", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (selectedJob == -1) {
                            JOptionPane.showMessageDialog(mf, "役割を選んでください。", "エラー", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        int choice = JOptionPane.showConfirmDialog(mf, "プレイヤーを登録します。\nユーザー名: " + tf_name.getText()
                                + "\n役割: " + Server.JOB[selectedJob], "確認", JOptionPane.OK_CANCEL_OPTION);
                        if (choice == JOptionPane.CANCEL_OPTION) {
                            return;
                        }
                        switch (selectedJob) {
                            case Server.ATTACKER:
                                mf.player = new Attacker(name);
                                break;
                            case Server.BYSTANDER:
                                mf.player = new Bystander(name);
                                break;
                        }
                        try {
                            if (!register()) {
                                mf.gp.setVisible(true);
                                return;
                            }
                        } catch (SocketException e1) {
                            e1.printStackTrace();
                        }
                        m.setSize(MainFrame.WIN_WIDTH, MainFrame.WIN_HEIGHT);
                        m.setLocationRelativeTo(null);
                        setVisible(false);
                        mf.player.manager = new ShipManager();
                        m.setp.setVisible(true);
                    }
                }
        );

        gbc_out.gridx = 0;
        gbc_out.gridy = 3;
        gbc_out.fill = GridBagConstraints.NONE;
        layout.setConstraints(bt_reg, gbc_out);
        this.add(bt_reg);
    }

    private boolean register() throws SocketException {
        mf.cmd.send(Server.REGISTER);
        mf.player.setGroupID(mf.groupID);
        mf.cmd.send(mf.groupID);
        if (!mf.cmd.receiveBoolean()) {
            JOptionPane.showMessageDialog(mf, "このグループには参加できません。",
                    "エラー", JOptionPane.WARNING_MESSAGE);
            mf.rp.setVisible(false);
            return false;
        }
        mf.cmd.send(mf.player);
        mf.player.setParent(mf.cmd.receiveBoolean());
        mf.player.setPlayerID(mf.cmd.receiveInt());
        mf.player.setCmd(mf.cmd);
        JOptionPane.showMessageDialog(mf, "グループ" + mf.groupID + "に参加しました。",
                "登録完了", JOptionPane.INFORMATION_MESSAGE);
        mf.cmd.receiveString();
        if (mf.player.isParent()) {
            new GroupParentDialog(mf);
        } else {
            JDialog dialog = new JDialog(mf, "待機中", true);
            Container pane = dialog.getContentPane();
            dialog.setSize(300, 100);
            dialog.setLocationRelativeTo(null);
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            JLabel lb_wait = new JLabel("ゲーム開始を待機しています…");
            lb_wait.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, 16));
            lb_wait.setHorizontalAlignment(SwingConstants.CENTER);
            pane.add(lb_wait);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (Server.START != mf.cmd.receiveInt()) ;
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                    dialog.dispose();
                }
            }).start();
            dialog.setVisible(true);
        }


        try {
            mf.player.setPlayersList((ArrayList<Player>) mf.cmd.receiveObject());
        } catch (SocketException e1) {
            JOptionPane.showMessageDialog(mf, "サーバーとの接続が失われました。",
                    "接続エラー", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        mf.rp.setVisible(false);

        String[][] memList = new String[mf.player.getPlayersList().size()][4];
        String[] column = {"役割", "ID", "名前", "親"};
        HashMap<Integer, Integer> id = new HashMap<>();
        int i = 0;
        for (Player p : mf.player.getPlayersList()) {
            id.put(p.getPlayerID(), mf.player.getPlayersList().indexOf(p));
            memList[i][0] = Server.JOB[p.getJobCode()];
            memList[i][1] = String.valueOf(p.getPlayerID());
            memList[i][2] = p.getPlayerName();
            memList[i++][3] = String.valueOf(p.isParent());
        }
        mf.player.setIDTable(id);

        JDialog di_mem = new JDialog(mf, "メンバー", true);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        Container pn_mem = di_mem.getContentPane();
        pn_mem.setLayout(layout);
        di_mem.setSize(600, 400);
        di_mem.setLocationRelativeTo(null);

        JLabel lb_mem = new JLabel("以下のメンバーでゲームを開始します。");
        lb_mem.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, 16));
        lb_mem.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(lb_mem, gbc);
        pn_mem.add(lb_mem);

        JTable tb_mem = new JTable(memList, column);
        JScrollPane sp = new JScrollPane(tb_mem);
        sp.setPreferredSize(new Dimension(500, 200));
        tb_mem.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, mf.FONT_SIZE - 5));
        tb_mem.setFocusable(false);
        tb_mem.setCellSelectionEnabled(false);
        tb_mem.setEnabled(false);
        tb_mem.setRowHeight(30);
        tb_mem.setRowMargin(5);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(sp, gbc);
        pn_mem.add(sp);

        JButton bt_OK = new JButton("OK");
        bt_OK.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, mf.FONT_SIZE - 5));
        bt_OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                di_mem.dispose();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(bt_OK, gbc);
        pn_mem.add(bt_OK);
        di_mem.setVisible(true);

        return true;
    }
}
