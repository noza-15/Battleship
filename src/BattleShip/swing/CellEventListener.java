package BattleShip.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CellEventListener implements ActionListener {
    private MainFrame main;
    private CellPanel cp;
    private SettingPanel setp;
    private int count;
    private int ship;
    private int index;

    public CellEventListener(MainFrame main, SettingPanel setp, CellPanel cp) {
        this.main = main;
        this.cp = cp;
        this.setp = setp;
    }

    public void actionPerformed(ActionEvent e) {
        Cell cell = (Cell) e.getSource();
        int x = cell.getCellX();//船のx座標
        int y = cell.getCellY();//船のy座標
        int maxX = this.cp.getCellCols() - 1;
        int maxY = this.cp.getCellRows() - 1;
        Cell c;
        index = this.setp.index;
        count = this.setp.count;
        ship = this.setp.ship;

        System.out.println("index: " + index);
        System.out.println("count: " + count);
        System.out.println("ship: " + ship);

        if (index == 0 && count == 0) {//第1段階
            this.setp.button.setEnabled(false);
            for (int j = 0; j <= maxY; j++) {
                for (int i = 0; i <= maxX; i++) {
                    c = this.cp.getCell(i, j);
                    if (i == x && j == y) {
                        c.setColor(Cell.RED);
                    } else if ((i == x - 1 && j == y) || (i == x + 1 && j == y) || (i == x && j == y - 1) || (i == x && j == y + 1)) {
                        c.setColor(Cell.YELLOW);
                    } else {
                        c.setEnabled(false);
                    }
                }
            }
            this.setp.count += 1;
        } else if (count == 0) {//2つ目以降の船の設置開始
            this.setp.button.setEnabled(false);
            if (this.checkBlack(cell)) {
                for (int j = 0; j <= maxY; j++) {
                    for (int i = 0; i <= maxX; i++) {
                        c = this.cp.getCell(i, j);
                        if (i == x && j == y) {
                            c.setColor(Cell.RED);
                        } else if ((i == x - 1 && j == y) || (i == x + 1 && j == y) || (i == x && j == y - 1) || (i == x && j == y + 1)) {
                            if (c.color != Cell.BLACK && c.color != Cell.BLUE) c.setColor(Cell.YELLOW);
                        } else {
                            c.setEnabled(false);
                        }
                    }
                }
                this.setp.count += 1;
            }
        } else {//船を設置中
            cell.setColor(Cell.RED);
            if (x - 1 >= 0 && this.cp.getCell(x - 1, y).color == Cell.RED) {
                if (x + 1 <= maxX && this.cp.getCell(x + 1, y).color != Cell.BLACK) {
                    this.cp.getCell(x + 1, y).setColor(Cell.YELLOW);
                }
                if (this.count == 1) {
                    if (y - 1 >= 0 && this.cp.getCell(x - 1, y - 1).color == Cell.YELLOW) {
                        this.cp.getCell(x - 1, y - 1).setColor(Cell.WHITE);
                        this.cp.getCell(x - 1, y - 1).setEnabled(false);
                    }
                    if (y + 1 <= maxY && this.cp.getCell(x - 1, y + 1).color == Cell.YELLOW) {
                        this.cp.getCell(x - 1, y + 1).setColor(Cell.WHITE);
                        this.cp.getCell(x - 1, y + 1).setEnabled(false);
                    }
                }
                this.setp.count += 1;
            } else if (x + 1 <= maxX && this.cp.getCell(x + 1, y).color == Cell.RED) {
                if (x - 1 >= 0 && this.cp.getCell(x - 1, y).color != Cell.BLACK) {
                    this.cp.getCell(x - 1, y).setColor(Cell.YELLOW);
                }
                if (this.count == 1) {
                    if (y - 1 >= 0 && this.cp.getCell(x + 1, y - 1).color == Cell.YELLOW) {
                        this.cp.getCell(x + 1, y - 1).setColor(Cell.WHITE);
                        this.cp.getCell(x + 1, y - 1).setEnabled(false);
                    }
                    if (y + 1 <= maxY && this.cp.getCell(x + 1, y + 1).color == Cell.YELLOW) {
                        this.cp.getCell(x + 1, y + 1).setColor(Cell.WHITE);
                        this.cp.getCell(x + 1, y + 1).setEnabled(false);
                    }
                }
                this.setp.count += 1;
            } else if (y - 1 >= 0 && this.cp.getCell(x, y - 1).color == Cell.RED) {
                if (y + 1 <= maxY && this.cp.getCell(x, y + 1).color != Cell.BLACK) {
                    this.cp.getCell(x, y + 1).setColor(Cell.YELLOW);
                }
                if (this.count == 1) {
                    if (x - 1 >= 0 && this.cp.getCell(x - 1, y - 1).color == Cell.YELLOW) {
                        this.cp.getCell(x - 1, y - 1).setColor(Cell.WHITE);
                        this.cp.getCell(x - 1, y - 1).setEnabled(false);
                    }
                    if (x + 1 <= maxX && this.cp.getCell(x + 1, y - 1).color == Cell.YELLOW) {
                        this.cp.getCell(x + 1, y - 1).setColor(Cell.WHITE);
                        this.cp.getCell(x + 1, y - 1).setEnabled(false);
                    }
                }
                this.setp.count += 1;
            } else if (y + 1 <= maxY && this.cp.getCell(x, y + 1).color == Cell.RED) {
                if (y - 1 >= 0 && this.cp.getCell(x, y - 1).color != Cell.BLACK) {
                    this.cp.getCell(x, y - 1).setColor(Cell.YELLOW);
                }
                if (this.count == 1) {
                    if (x - 1 >= 0 && this.cp.getCell(x - 1, y + 1).color == Cell.YELLOW) {
                        this.cp.getCell(x - 1, y + 1).setColor(Cell.WHITE);
                        this.cp.getCell(x - 1, y + 1).setEnabled(false);
                    }
                    if (x + 1 <= maxX && this.cp.getCell(x + 1, y + 1).color == Cell.YELLOW) {
                        this.cp.getCell(x + 1, y + 1).setColor(Cell.WHITE);
                        this.cp.getCell(x + 1, y + 1).setEnabled(false);
                    }
                }
                this.setp.count += 1;
            }
        }
        if (this.setp.count == ship) {
            this.setp.button.setEnabled(true);
            this.setp.index += 1;
            for (int j = 0; j <= maxY; j++) {
                for (int i = 0; i <= maxX; i++) {
                    c = this.cp.getCell(i, j);
                    if (c.color == Cell.YELLOW) {
                        c.setColor(Cell.WHITE);
                    }
                }
            }
        }
    }

    public boolean checkBlack(Cell cell) {
        int x = cell.getCellX();//船のx座標
        int y = cell.getCellY();//船のy座標
        int lengthX = 0;
        int lengthY = 0;
        int maxX = this.cp.getCellCols() - 1;
        int maxY = this.cp.getCellRows() - 1;

        for (int i = x - 1; i >= 0; i--) {
            Cell c = this.cp.getCell(i, y);
            if (c.color != Cell.BLACK) {
                lengthX++;
            } else break;
        }
        for (int i = x + 1; i <= maxX; i++) {
            Cell c = this.cp.getCell(i, y);
            if (c.color != Cell.BLACK) {
                lengthX++;
            } else break;
        }
        for (int i = y - 1; i >= 0; i--) {
            Cell c = this.cp.getCell(x, i);
            if (c.color != Cell.BLACK) {
                lengthY++;
            } else break;
        }
        for (int i = y + 1; i <= maxY; i++) {
            Cell c = this.cp.getCell(x, i);
            if (c.color != Cell.BLACK) {
                lengthY++;
            } else break;
        }
        if (lengthX < ship - 1 && lengthY < ship - 1) {
            for (int i = x - 1; i >= 0 && this.cp.getCell(i, y).color != Cell.BLACK; i--)
                this.cp.getCell(i, y).setColor(Cell.BLUE);
            for (int i = x + 1; i <= maxX && this.cp.getCell(i, y).color != Cell.BLACK; i++)
                this.cp.getCell(i, y).setColor(Cell.BLUE);
            for (int i = y; i >= 0 && this.cp.getCell(x, i).color != Cell.BLACK; i--)
                this.cp.getCell(x, i).setColor(Cell.BLUE);
            for (int i = y + 1; i <= maxY && this.cp.getCell(x, i).color != Cell.BLACK; i++)
                this.cp.getCell(x, i).setColor(Cell.BLUE);
            return false;
        } else if (lengthX < ship - 1) {
            for (int i = x - 1; i >= 0 && this.cp.getCell(i, y).color != Cell.BLACK; i--)
                this.cp.getCell(i, y).setColor(Cell.BLUE);
            for (int i = x + 1; i <= maxX && this.cp.getCell(i, y).color != Cell.BLACK; i++)
                this.cp.getCell(i, y).setColor(Cell.BLUE);
        } else if (lengthY < ship - 1) {
            for (int i = y - 1; i >= 0 && this.cp.getCell(x, i).color != Cell.BLACK; i--)
                this.cp.getCell(x, i).setColor(Cell.BLUE);
            for (int i = y + 1; i <= maxY && this.cp.getCell(x, i).color != Cell.BLACK; i++)
                this.cp.getCell(x, i).setColor(Cell.BLUE);
        }
        return true;
    }
}
