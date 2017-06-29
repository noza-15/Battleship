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

        //outp -> sp -> inp の順番になっています
        JPanel outp = new JPanel();
        outp.setPreferredSize(new Dimension(870, 290));//spよりも小さくないと画面がつぶれました
        //outp.setPreferredSize(new Dimension(880, 300));
        JScrollPane sp = new JScrollPane();
        sp.setPreferredSize(new Dimension(880, 300));
        JPanel inp = new JPanel();
        GridBagLayout scrollLayout = new GridBagLayout();
        GridBagConstraints gbc_maps = new GridBagConstraints();
        AttackCellPanel[] pn_maps = new AttackCellPanel[6];//groupに属するAttackerの数を取得
        inp.setLayout(scrollLayout);
        for (int i = 0; i < 6; i++) {
            pn_maps[i] = new AttackCellPanel(mf, this, 25);
            gbc_maps.gridx = i % 3;
            gbc_maps.gridy = i / 3;
            gbc_maps.weightx = 1.0d;
            gbc_maps.weighty = 1.0d;
            gbc_maps.insets = new Insets(10, 10, 10, 10);
            scrollLayout.setConstraints(pn_maps[i], gbc_maps);
            inp.add(pn_maps[i]);//それぞれのCellPanelはinpに設置
        }
        sp.setViewportView(inp);//ScrollPaneの中にinpを設置
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        outp.add(sp);//outpにScrollPaneを配置
        layout.setConstraints(outp, gbc);//GridBagLayoutはoutpに適用
        this.add(outp);
    }
}
