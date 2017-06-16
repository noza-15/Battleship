package swing;

import javax.swing.*;
import java.awt.*;

public class CellPanel extends JPanel {
    private static final int CELL_SIZE = 50;
    private static final Color COLOR_LABEL = Color.WHITE;
    MainFrame mf;
    String str;
    SettingPanel setp;
    private int cellRows = 10;
    private int cellCols = 10;
    private Cell[][] cells;

    public CellPanel(MainFrame m, SettingPanel sp, String s) {
        mf = m;
        str = s;
        setp = sp;

        int width = this.cellCols * CELL_SIZE;
        int height = (this.cellRows + 2) * CELL_SIZE;

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
                cell.addActionListener(new SetCellEventListener(m, sp, this));
                this.addComponent(cell, j, i + 1, 1, 1);
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
        return this.COLOR_LABEL;
    }

    public void reset() {
        int maxX = cellCols - 1;
        int maxY = cellRows - 1;
        for (int j = 0; j <= maxY; j++) {
            for (int i = 0; i <= maxX; i++) {
                Cell c = cells[i][j];
                if (c.color == "RED") {
                    c.setBackground(Color.BLACK);
                    c.color = "BLACK";
                    c.setEnabled(false);
                } else if (c.color != "BLACK") {
                    c.setBackground(COLOR_LABEL);
                    c.color = "WHITE";
                    c.setEnabled(true);
                }
            }
        }
    }
}
