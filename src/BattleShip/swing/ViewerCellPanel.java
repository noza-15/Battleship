package BattleShip.swing;

import BattleShip.Server;

import javax.swing.*;

public class ViewerCellPanel extends CellPanel {

    public ViewerCellPanel(MainFrame m, JPanel sp, int cellSize) {
        super(m, sp, cellSize);
        //セルの新規作成
        for (int i = 0; i < Server.FIELD_SIZE_Y; i++) {
            for (int j = 0; j < Server.FIELD_SIZE_X; j++) {
                Cell cell = new Cell(j, i, cellSize);
                this.cells[j][i] = cell;
                cell.setBackground(COLOR_LABEL);
                this.addComponent(cell, j, i, 1, 1);
            }
        }
    }
}
