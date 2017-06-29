package BattleShip.swing;

import BattleShip.Server;
import BattleShip.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;

public class SettingPanel extends JPanel {

    MainFrame mf;
    JButton bt_regShip;
    JButton bt_sendShip;
    //    JLabel lb_inst;
    //    JLabel lb_size;
    int shipIndex = 0;

    public SettingPanel(MainFrame mf) {
        this.mf = mf;
        this.setSize(MainFrame.WIN_WIDTH, MainFrame.WIN_HEIGHT);
        //レイアウトを設定
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        SetCellPanel cp = new SetCellPanel(mf, this, 40);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        gbc.gridheight = 4;
        layout.setConstraints(cp, gbc);
        this.add(cp);

        JLabel lb_inst = new JLabel("船を配置してください");//label作成
        lb_inst.setHorizontalAlignment(JLabel.CENTER);
        lb_inst.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, MainFrame.FONT_SIZE - 3));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        layout.setConstraints(lb_inst, gbc);
        this.add(lb_inst);

        JLabel lb_size = new JLabel(Server.SHIPS[shipIndex] + ":" + Server.SHIPS_SIZE[shipIndex] + "マス");
        lb_size.setHorizontalAlignment(JLabel.CENTER);
        lb_size.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, MainFrame.FONT_SIZE));
        gbc.gridx = 1;
        gbc.gridy = 1;
        layout.setConstraints(lb_size, gbc);
        this.add(lb_size);

        bt_regShip = new JButton("登録");//button作成
        bt_regShip.setSize(200, 200);
        bt_regShip.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, MainFrame.FONT_SIZE));
        bt_regShip.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SettingPanel.this.mf.player.manager.setMyShip(Server.SHIPS[shipIndex], Server.SHIPS_SIZE[shipIndex],
                                cp.selectedX, cp.selectedY, cp.direction);
                        cp.reset();
                        shipIndex++;
                        if (shipIndex >= Server.SHIPS.length) {
                            bt_regShip.setEnabled(false);
                            cp.setEnabledAll(false);
                            bt_sendShip.setEnabled(true);
                            return;
                        }
                        cp.direction = -1;
                        cp.selectedX = -1;
                        cp.selectedY = -1;
                        lb_size.setText(Server.SHIPS[shipIndex] + ":" + Server.SHIPS_SIZE[shipIndex] + "マス");
                        bt_regShip.setEnabled(false);
                    }
                }
        );

        bt_regShip.setEnabled(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        layout.setConstraints(bt_regShip, gbc);
        this.add(bt_regShip);

        bt_sendShip = new JButton("送信");
        bt_sendShip.setSize(200, 200);
        bt_sendShip.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, MainFrame.FONT_SIZE));
        bt_sendShip.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SettingPanel.this.mf.cmd.send(Server.SET_SHIPS);
                        JDialog dialog = new JDialog(SettingPanel.this.mf, "待機中", true);
                        JLabel lb_wait = new JLabel("他のプレイヤーの選択を待っています…");
                        Container pane = dialog.getContentPane();
                        dialog.setSize(400, 100);
                        dialog.setLocationRelativeTo(null);
                        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                        lb_wait.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, 16));
                        lb_wait.setHorizontalAlignment(SwingConstants.CENTER);
                        pane.add(lb_wait);
                        pane.setVisible(true);
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    SettingPanel.this.mf.cmd.send(SettingPanel.this.mf.player.manager.getMyShips());
                                } catch (SocketException e1) {
                                    e1.printStackTrace();
                                }
                                SettingPanel.this.mf.cmd.send(Server.GET_SHIPS);
                                try {
                                    SettingPanel.this.mf.player.manager.setOthersShips((Ship[][][]) SettingPanel.this.mf.cmd.receiveObject());
                                } catch (SocketException e1) {
                                    e1.printStackTrace();
                                }
                                dialog.dispose();
                                JOptionPane.showMessageDialog(SettingPanel.this.mf, "盤面データの受信が完了しました。", "受信完了", JOptionPane.INFORMATION_MESSAGE);
                                mf.bp = new BattlePanel(mf);
                                mf.add(mf.bp);
                                mf.setp.setVisible(false);
                                mf.bp.setVisible(true);
                            }
                        }.start();
                        dialog.setVisible(true);
                    }
                }
        );

        bt_sendShip.setEnabled(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        layout.setConstraints(bt_sendShip, gbc);
        this.add(bt_sendShip);
    }
}
