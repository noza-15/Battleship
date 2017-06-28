package BattleShip.swing;

import javax.swing.*;
import java.awt.*;

public class BattlePanel extends JPanel {
    MainFrame mf;

    public BattlePanel(MainFrame mf) {
        this.mf = mf;
        mf.setSize(900, 600);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        AttackCellPanel cp = new AttackCellPanel(mf, this, 25);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(cp, gbc);
        this.add(cp);

        JScrollPane sp = new JScrollPane();
        sp.setPreferredSize(new Dimension(880, 300));
        JPanel pn_others = new JPanel();
        GridBagLayout scrollLayout = new GridBagLayout();
        GridBagConstraints gbc_maps = new GridBagConstraints();
        AttackCellPanel[] pn_maps = new AttackCellPanel[6];
        pn_others.setLayout(scrollLayout);
        for (int i = 0; i < 6; i++) {
            pn_maps[i] = new AttackCellPanel(mf, this, 25);
            gbc_maps.gridx = i % 3;
            gbc_maps.gridy = i / 3;
            gbc_maps.weightx = 1.0d;
            gbc_maps.weighty = 1.0d;
            gbc_maps.insets = new Insets(10, 10, 10, 10);
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
}
