package BattleShip.swing;

import javax.swing.*;
import java.awt.*;

class Cell extends JButton {

    static final int WHITE = 1;
    static final int RED = 2;
    static final int YELLOW = 3;
    static final int BLUE = 4;
    static final int BLACK = 5;
    private final int cellX;
    private final int cellY;
    public int color;

    private Cell() {
        super();

        this.cellX = 0;
        this.cellY = 0;
        this.color = WHITE;
    }

    public Cell(int cellX, int cellY) {
        super();
        this.cellX = cellX;
        this.cellY = cellY;
        this.color = WHITE;
    }

    public int getCellX() {
        return this.cellX;
    }

    public int getCellY() {
        return this.cellY;
    }

    public void setRED() {
        this.setBackground(Color.RED);
        this.color = RED;
//        this.setEnabled(false);
    }

    public void setYELLOW() {
        this.setBackground(Color.YELLOW);
        this.color = YELLOW;
        this.setEnabled(true);
    }

    public void setWHITE() {
        this.setBackground(Color.WHITE);
        this.color = WHITE;
        this.setEnabled(false);
    }

    public void setBLUE() {
        this.setBackground(Color.BLUE);
        this.color = BLUE;
        this.setEnabled(false);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        switch (color) {
            case YELLOW:
                this.setBackground(Color.YELLOW);
                setEnabled(true);
                break;
            case WHITE:
                this.setBackground(Color.WHITE);
                setEnabled(false);
                break;
            case RED:
                this.setBackground(Color.RED);
                setEnabled(true);
                break;
            case BLUE:
                this.setBackground(Color.BLUE);
                setEnabled(false);
                break;
            case BLACK:
                this.setBackground(Color.BLACK);
                setEnabled(false);
                break;
        }
        this.color = color;
    }
}
