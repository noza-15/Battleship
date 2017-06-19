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
    JLabel lb_inst;
    JLabel lb_size;

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

        lb_inst = new JLabel("船を配置してください");//label作成
        lb_inst.setHorizontalAlignment(JLabel.CENTER);
        lb_inst.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font - 5));
        lb_size = new JLabel(name[index] + ":" + len[index] + "マス");
        lb_size.setHorizontalAlignment(JLabel.CENTER);
        lb_size.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, m.font));

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
                            lb_size.setText(name[index] + ":" + len[index] + "マス");
                        }
                    }
                }
        );
        button.setEnabled(false);

        gbc.gridx = 1;
        gbc.gridy = 0;
        layout.setConstraints(lb_inst, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        layout.setConstraints(lb_size, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        layout.setConstraints(cp, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        layout.setConstraints(button, gbc);

        this.add(lb_inst);
        this.add(lb_size);
        this.add(cp);
        this.add(button);
    }
}
