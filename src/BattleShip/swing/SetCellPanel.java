package BattleShip.swing;

import BattleShip.Server;

import javax.swing.*;
import java.awt.*;

public class SetCellPanel extends CellPanel {

    private static final Color COLOR_LABEL = Color.WHITE;
    MainFrame mf;
    JPanel setp;

    public SetCellPanel(MainFrame m, JPanel sp, int cellSize) {
        super(m, sp, cellSize);
        //セルの新規作成
        for (int i = 0; i < Server.FIELD_SIZE_Y; i++) {
            for (int j = 0; j < Server.FIELD_SIZE_X; j++) {
                Cell cell = new Cell(j, i, cellSize);
                this.cells[j][i] = cell;
                cell.setBackground(COLOR_LABEL);
                cell.addActionListener(new CellEventListener(m, (SettingPanel) sp, this));
                this.addComponent(cell, j, i, 1, 1);
            }
        }
    }
}


