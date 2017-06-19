package BattleShip.swing;

import BattleShip.Server;

import javax.swing.*;
import java.awt.*;

public class CellPanel extends JPanel {
    private static final int CELL_SIZE = 5;
    private static final Color COLOR_LABEL = Color.WHITE;
    MainFrame mf;
    String str;
    SettingPanel setp;
    int direction = -1;
    int selectedX = -1;
    int selectedY = -1;
    private int cellRows = 10;
    private int cellCols = 10;
    private Cell[][] cells;

    public CellPanel(MainFrame m, SettingPanel sp, String s) {
        mf = m;
        str = s;
        setp = sp;

        int width = this.cellCols * CELL_SIZE;
        int height = (this.cellRows) * CELL_SIZE;

        this.setName(s);
        this.setSize(width, height);
        //レイアウトを設定
        this.setLayout(new GridBagLayout());

        //配列確保
        this.cells = new Cell[this.cellCols][this.cellRows];

        //セルの新規作成
        for (int i = 0; i < this.cellRows; i++) {
            for (int j = 0; j < this.cellCols; j++) {
                Cell cell = new Cell(j, i);
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

    public int getCellRows() {
        return this.cellRows;
    }

    public int getCellCols() {
        return this.cellCols;
    }

    public Color getCOLOR_LABEL() {
        return COLOR_LABEL;
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
