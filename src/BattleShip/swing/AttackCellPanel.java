package BattleShip.swing;

import BattleShip.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttackCellPanel extends CellPanel {
    int x = -1;
    int y = -1;

    public AttackCellPanel(MainFrame m, JPanel sp, int cellSize) {
        super(m, sp, cellSize);
        //セルの新規作成
        for (int i = 0; i < Server.FIELD_SIZE_Y; i++) {
            for (int j = 0; j < Server.FIELD_SIZE_X; j++) {
                Cell cell = new Cell(j, i, cellSize);
                this.cells[j][i] = cell;
                cell.setBackground(COLOR_LABEL);
                cell.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (x == -1 && y == -1) {
                            x = cell.getCellX();
                            y = cell.getCellY();
//                            cell.setText("●");
//                            cell.setColor(Cell.BLACK);
                            setEnabledAll(false);
                            cell.setEnabled(true);
                        } else {
                            cell.setText(null);
                            x = -1;
                            y = -1;
                            setEnabledAll(true);
                        }
                    }
                });
                this.addComponent(cell, j, i, 1, 1);
            }
        }
    }

    public int getSelectedX() {
        return x;
    }

    public int getSelectedY() {
        return y;
    }

    public void restXY() {
        x = -1;
        y = -1;
    }
}
