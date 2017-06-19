package BattleShip.swing;

import BattleShip.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPanel extends JPanel {

    MainFrame mf;
    String str;
    JButton bt_regShip;
    JButton bt_sendShip;
    //    JLabel lb_inst;
    //    JLabel lb_size;
    int shipIndex = 0;

    public SettingPanel(MainFrame m, String s) {
        mf = m;
        str = s;

        this.setName(s);
        this.setSize(MainFrame.WIN_WIDTH, MainFrame.WIN_HEIGHT);
        //レイアウトを設定
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lb_inst = new JLabel("船を配置してください");//label作成
        lb_inst.setHorizontalAlignment(JLabel.CENTER);
        lb_inst.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, MainFrame.FONT_SIZE - 5));
        gbc.gridx = 1;
        gbc.gridy = 0;
        layout.setConstraints(lb_inst, gbc);
        this.add(lb_inst);

        JLabel lb_size = new JLabel(Server.SHIPS[shipIndex] + ":" + Server.SHIPS_SIZE[shipIndex] + "マス");
        lb_size.setHorizontalAlignment(JLabel.CENTER);
        lb_size.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, MainFrame.FONT_SIZE));
        gbc.gridx = 1;
        gbc.gridy = 1;
        layout.setConstraints(lb_size, gbc);
        this.add(lb_size);

        CellPanel cp = new CellPanel(m, this, "CellPanel");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        layout.setConstraints(cp, gbc);
        this.add(cp);

        bt_regShip = new JButton("登録");//button作成
        bt_regShip.setSize(200, 200);
        bt_regShip.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, MainFrame.FONT_SIZE));
        bt_regShip.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mf.player.manager.setMyShip(Server.SHIPS[shipIndex], Server.SHIPS_SIZE[shipIndex],
                                cp.selectedX, cp.selectedY, cp.direction);
                        cp.reset();
                        shipIndex++;
                        if (shipIndex == Server.SHIPS.length) {
                            bt_regShip.setEnabled(false);
                            cp.disableAll();
                            bt_sendShip.setEnabled(true);
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
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        layout.setConstraints(bt_regShip, gbc);
        this.add(bt_regShip);

        bt_sendShip = new JButton("送信");
        bt_sendShip.setSize(200, 200);
        bt_sendShip.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, MainFrame.FONT_SIZE));
        bt_sendShip.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                    }
                }
        );

        bt_sendShip.setEnabled(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        gbc.fill = GridBagConstraints.NONE;
        layout.setConstraints(bt_sendShip, gbc);
        this.add(bt_sendShip);
    }
}
