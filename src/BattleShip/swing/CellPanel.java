package BattleShip.swing;

import BattleShip.Server;

import javax.swing.*;
import java.awt.*;

public class CellPanel extends JPanel {
    static final Color COLOR_LABEL = Color.WHITE;
    MainFrame mf;
    JPanel setp;
    int direction = -1;
    int selectedX = -1;
    int selectedY = -1;
    Cell[][] cells;

    public CellPanel(MainFrame mf, JPanel setp, int cellSize) {
        this.mf = mf;
        this.setp = setp;
        int width = Server.FIELD_SIZE_X * cellSize;
        int height = Server.FIELD_SIZE_Y * cellSize;
        this.setSize(width, height);
        this.setPreferredSize(new Dimension(width, height));
        //レイアウトを設定
        this.setLayout(new GridBagLayout());
        //配列確保
        this.cells = new Cell[Server.FIELD_SIZE_X][Server.FIELD_SIZE_Y];
    }

    void addComponent(Component c, int x, int y, int w, int h) {
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

