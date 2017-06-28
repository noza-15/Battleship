package BattleShip.swing;

import BattleShip.Server;

import javax.swing.*;
import java.awt.*;

public class CellPanel extends JPanel {

    private static final Color COLOR_LABEL = Color.WHITE;
    MainFrame mf;
    SettingPanel setp;
    int direction = -1;
    int selectedX = -1;
    int selectedY = -1;
    private Cell[][] cells;

    public CellPanel(MainFrame m, SettingPanel sp, int cellSize) {
        mf = m;
        setp = sp;

        int width = Server.FIELD_SIZE_X * cellSize;
        int height = Server.FIELD_SIZE_Y * cellSize;
        this.setSize(width, height);
        //レイアウトを設定
        this.setLayout(new GridBagLayout());

        //配列確保
        this.cells = new Cell[Server.FIELD_SIZE_X][Server.FIELD_SIZE_Y];

        //セルの新規作成
        for (int i = 0; i < Server.FIELD_SIZE_Y; i++) {
            for (int j = 0; j < Server.FIELD_SIZE_X; j++) {
                Cell cell = new Cell(j, i, cellSize);
                this.cells[j][i] = cell;
                cell.setBackground(COLOR_LABEL);
                cell.addActionListener(new CellEventListener(m, sp, this));
                this.addComponent(cell, j, i, 1, 1);
            }
        }
    }

    private void addComponent(Component c, int x, int y, int w, int h) {
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout mgr = (GridBagLayout) this.getLayout();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.weightx = 1;
        gbc.weighty = 1;

        mgr.setConstraints(c, gbc);
        this.add(c);
    }

    public Cell getCell(int x, int y) {
        return this.cells[x][y];
    }

    public void reset() {
        for (int j = 0; j < Server.FIELD_SIZE_Y; j++) {
            for (int i = 0; i < Server.FIELD_SIZE_X; i++) {
                Cell c = cells[i][j];
                if (c.color == Cell.RED) {
                    c.setColor(Cell.BLACK);
                } else if (c.color == Cell.WHITE) {
                    c.setEnabled(true);
                }
            }
        }
    }

    public void disableAll() {
        for (int j = 0; j < Server.FIELD_SIZE_Y; j++) {
            for (int i = 0; i < Server.FIELD_SIZE_X; i++) {
                cells[i][j].setEnabled(false);
            }
        }
    }
}
