package BattleShip.swing;

import BattleShip.*;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class BattlePanel extends JPanel {
    MainFrame mf;
    AttackCellPanel cp_bomb;
    JLabel lb_bombGrid;
    JLabel lb_turnNo;
    JButton bt_nextTurn;
    JButton bt_confirm;
    int turnNo = 1;
    AttackCellPanel[] pn_maps;
    Clip se1 = null;
    Clip se2 = null;

    public BattlePanel(MainFrame mf) {
        this.mf = mf;
        mf.setSize(900, 600);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        InputStream inputStream1 = ClassLoader.getSystemResourceAsStream(
                "BattleShip/res/sen_ge_bomsuityuu01.wav");
        InputStream inputStream2 = ClassLoader.getSystemResourceAsStream(
                "BattleShip/res/sen_ge_bomsuijou02.wav");
        try {
            AudioInputStream sein1 = AudioSystem.getAudioInputStream(inputStream1);
            AudioInputStream sein2 = AudioSystem.getAudioInputStream(inputStream2);
            AudioFormat format1 = sein1.getFormat();
            AudioFormat format2 = sein2.getFormat();
            DataLine.Info di1 = new DataLine.Info(Clip.class, format1);
            DataLine.Info di2 = new DataLine.Info(Clip.class, format2);
            se1 = (Clip) AudioSystem.getLine(di1);
            se2 = (Clip) AudioSystem.getLine(di2);
            se1.open(sein1);
            se2.open(sein2);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        AttackCellPanel cp = new AttackCellPanel(mf, this, 25);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(cp, gbc);
        cp.setEnabledAll(false);
        this.add(cp);

        cp_bomb = new AttackCellPanel(mf, this, 25);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(cp_bomb, gbc);
        this.add(cp_bomb);

        JPanel pn_info = new JPanel(layout);
        pn_info.setPreferredSize(new Dimension(250, 250));
        GridBagConstraints gbc_info = new GridBagConstraints();

        JLabel lb_groupID = new JLabel("グループ " + mf.player.getGroupID());
        lb_groupID.setHorizontalAlignment(JLabel.CENTER);
        lb_groupID.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, MainFrame.FONT_SIZE - 3));
        gbc_info.gridx = 0;
        gbc_info.gridy = 0;
        layout.setConstraints(lb_groupID, gbc_info);
        pn_info.add(lb_groupID);

        JLabel lb_playerID = new JLabel("プレイヤーID:" + mf.player.getPlayerID());
        lb_playerID.setHorizontalAlignment(JLabel.CENTER);
        lb_playerID.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, MainFrame.FONT_SIZE - 3));
        gbc_info.gridx = 0;
        gbc_info.gridy = 1;
        layout.setConstraints(lb_playerID, gbc_info);
        pn_info.add(lb_playerID);


        lb_turnNo = new JLabel("Turn: " + turnNo);
        lb_turnNo.setHorizontalAlignment(SwingConstants.CENTER);
        lb_turnNo.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, MainFrame.FONT_SIZE - 3));
        gbc_info.gridx = 0;
        gbc_info.gridy = 2;
        layout.setConstraints(lb_turnNo, gbc_info);
        pn_info.add(lb_turnNo);

        lb_bombGrid = new JLabel("攻撃地点を右から選んでください");
        lb_bombGrid.setHorizontalAlignment(SwingConstants.CENTER);
        lb_bombGrid.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, MainFrame.FONT_SIZE - 4));
        gbc_info.gridx = 0;
        gbc_info.gridy = 3;
        layout.setConstraints(lb_bombGrid, gbc_info);
        pn_info.add(lb_bombGrid);

        bt_confirm = new JButton("決定");
        bt_confirm.setHorizontalAlignment(SwingConstants.CENTER);
        bt_confirm.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, MainFrame.FONT_SIZE - 3));
        bt_confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = cp_bomb.getSelectedX();
                int y = cp_bomb.getSelectedY();
                if (x == -1 && y == -1) {
                    lb_bombGrid.setText("攻撃地点が選ばれていません");
                    return;
                }
                lb_bombGrid.setText("(" + x + ", " + y + ")");
//                cp_bomb.setEnabledAll(false);

                bt_confirm.setEnabled(false);
                nextTurn();
            }
        });
        gbc_info.gridx = 0;
        gbc_info.gridy = 4;
        layout.setConstraints(bt_confirm, gbc_info);
        pn_info.add(bt_confirm);

        bt_nextTurn = new JButton("次のターン");
        bt_nextTurn.setHorizontalAlignment(SwingConstants.CENTER);
        bt_nextTurn.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, MainFrame.FONT_SIZE - 3));
        bt_nextTurn.setEnabled(false);
        bt_nextTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp_bomb.setEnabledAll(true);
                bt_nextTurn.setEnabled(false);
                bt_confirm.setEnabled(true);
            }
        });
        gbc_info.gridx = 0;
        gbc_info.gridy = 5;
        layout.setConstraints(bt_nextTurn, gbc_info);
        pn_info.add(bt_nextTurn);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(pn_info, gbc);
        this.add(pn_info);


        JScrollPane sp = new JScrollPane();
        sp.setPreferredSize(new Dimension(880, 300));
        JPanel pn_others = new JPanel();
        GridBagLayout scrollLayout = new GridBagLayout();
        GridBagConstraints gbc_maps = new GridBagConstraints();
        pn_maps = new AttackCellPanel[mf.player.manager.getAttackersCount()];
        pn_others.setLayout(scrollLayout);
        for (int i = 0; i < pn_maps.length; i++) {
            pn_maps[i] = new AttackCellPanel(mf, this, 25);
            gbc_maps.gridx = i % 3;
            gbc_maps.gridy = i / 3;
            gbc_maps.weightx = 1.0d;
            gbc_maps.weighty = 1.0d;
            gbc_maps.insets = new Insets(10, 10, 10, 10);
            if (i == mf.player.getPlayerID()) {
                pn_maps[i].setBorder(new LineBorder(Color.RED));
            }
            scrollLayout.setConstraints(pn_maps[i], gbc_maps);
            pn_others.add(pn_maps[i]);
        }
        sp.setViewportView(pn_others);
        sp.setBounds(0, 450, 880, 300);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(sp, gbc);
        this.add(sp);

    }

    public void nextTurn() {
        ShipManager manager = mf.player.manager;
        CommandHandler cmd = mf.cmd;
        int groupID = mf.player.getGroupID();
        int playerID = mf.player.getPlayerID();
        HashMap<Integer, Integer> idTable = mf.player.getIDTable();
        int AttackersCount = mf.player.manager.getAttackersCount();
        ArrayList<Player> pList = mf.player.getPlayersList();
        se1.setFramePosition(0);
        se2.setFramePosition(0);
        if (manager.isAlive(idTable.get(playerID))) {
            int x = cp_bomb.getSelectedX();
            int y = cp_bomb.getSelectedY();
            lb_bombGrid.setText("他のプレイヤーが選択するのを待っています...");
//            lb_bombGrid.setText("(" + x + ", " + y + ")");
            cp_bomb.setEnabledAll(false);
            cp_bomb.restXY();
            cmd.send(Server.ATTACK);
            try {
                cmd.send(new AttackCommand(groupID, playerID, turnNo++, x, y));
            } catch (SocketException e) {
                e.printStackTrace();
            }
        } else {
            cmd.send(Server.ATTACK);
            try {
                cmd.send(new AttackCommand(groupID, playerID, turnNo++, -1, -1));
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        //TODO: 受信
        for (int i = 0; i < pList.size(); i++) {
            AttackCommand command = null;
            if (pList.get(i).getJobCode() == Server.ATTACKER) {
                try {
                    command = (AttackCommand) cmd.receiveObject();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
            if (i == 0) {
                lb_turnNo.setText("# Turn" + command.getTurnNo() + " START!");
            }
            for (int j = 0; j < pList.size(); j++) {
                if (pList.get(i).getJobCode() == Server.ATTACKER) {
                    int state = manager.isBombed(idTable.get(command.getPlayerID()), j, command.getX(), command.getY());
                    System.out.println("Turn" + command.getTurnNo() + " " + command.getPlayerID() + " : " + state);
                }
            }
        }
        for (int i = 0; i < pList.size(); i++) {
            boolean isSunkn = false;
            if (pList.get(i).getJobCode() == Server.ATTACKER) {
                se1.setFramePosition(0);
                se2.setFramePosition(0);
                for (int y = 0; y < Server.FIELD_SIZE_Y; y++) {
                    for (int x = 0; x < Server.FIELD_SIZE_X; x++) {
                        switch (manager.getState(i, x, y)) {
                            case ShipManager.BOMB_HIT:
                                pn_maps[i].getCell(x, y).setColor(Cell.RED);
                                se1.start();
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case ShipManager.BOMB_HIT_NEXT:
                                pn_maps[i].getCell(x, y).setColor(Cell.YELLOW);
                                break;
                            case ShipManager.BOMB_MISS:
                                pn_maps[i].getCell(x, y).setColor(Cell.BLUE);
                                break;
                            case ShipManager.UNKNOWN:
                            case ShipManager.BOMB_ALREADY_HIT:
                                break;
                        }
                        if (!manager.isAlive(i, x, y)) {
                            isSunkn = true;
                            pn_maps[i].getCell(x, y).setColor(Cell.BLACK);
                            se2.start();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
                manager.show(i);
            }
            if (isSunkn) {
                lb_bombGrid.setText(i + " :沈没");
            }
        }
        if (!manager.isAlive(idTable.get(playerID))) {
            lb_bombGrid.setText("あなたは死にました。戦闘不能です。");
            JOptionPane.showMessageDialog(this, "あなたは死にました。戦闘不能です。",
                    "敗戦", JOptionPane.WARNING_MESSAGE);
        }
        bt_nextTurn.setEnabled(true);
    }
}
