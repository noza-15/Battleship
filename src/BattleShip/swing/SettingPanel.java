package BattleShip.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPanel extends JPanel {

    MainFrame mf;
    String str;
    String[] name = {"空母", "戦艦", "巡洋艦", "潜水艦", "駆逐艦"};
    int[] len = {5, 4, 3, 3, 2};
    int count;
    int index;
    int ship;
    JButton button;
    JLabel label1;
    JLabel label2;

    public SettingPanel(MainFrame m, String s) {
        mf = m;
        str = s;
        count = 0;
        index = 0;
        ship = len[index];

        this.setName(s);
        this.setSize(MainFrame.WIN_WIDTH, MainFrame.WIN_HEIGHT);
        //レイアウトを設定
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        label1 = new JLabel("船を配置してください");//label作成
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font - 5));
        label2 = new JLabel(name[index] + ":" + len[index] + "マス");
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));

        CellPanel cp = new CellPanel(m, this, "CellPanel");

        button = new JButton("登録");//button作成
        button.setSize(200, 200);
        button.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));
        // button.setEnabled(false);
        button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (index == len.length) {

                        } else {
                            cp.reset();
                            count = 0;
                            ship = len[index];
                            label2.setText(name[index] + ":" + len[index] + "マス");
                        }
                    }
                }
        );
        button.setEnabled(false);

        gbc.gridx = 0;
        gbc.gridy = 1;
        layout.setConstraints(label1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        layout.setConstraints(label2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 5.0d;
        gbc.weighty = 1.0d;
        gbc.fill = GridBagConstraints.BOTH;
        layout.setConstraints(cp, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        gbc.fill = GridBagConstraints.NONE;
        layout.setConstraints(button, gbc);

        this.add(label1);
        this.add(label2);
        this.add(cp);
        this.add(button);
    }
}
