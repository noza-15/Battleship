package BattleShip.swing;

import BattleShip.Server;
import BattleShip.Ship;

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

    @Override
    public void actionPerformed(ActionEvent e) {
        Cell cell = (Cell) e.getSource();
        int x = cell.getCellX();//船のx座標
        int y = cell.getCellY();//船のy座標

        if (cell.getColor() == Cell.WHITE) {
            cell.setColor(Cell.RED);
            cp.selectedX = x;
            cp.selectedY = y;
            turnNextColor(x, y, Cell.YELLOW);
            setColorEnabled(Cell.WHITE, false);
        } else if (cell.getColor() == Cell.YELLOW) {
            turnNextColor(cp.selectedX, cp.selectedY, Cell.WHITE);
            if (cp.selectedX < x) {
                cp.direction = Ship.RIGHT;
                for (int i = 0; i < Server.SHIPS_SIZE[setp.shipIndex]; i++) {
                    cp.getCell(cp.selectedX + i, y).setColor(Cell.RED);
                }
            } else if (cp.selectedX > x) {
                cp.direction = Ship.LEFT;
                for (int i = 0; i < Server.SHIPS_SIZE[setp.shipIndex]; i++) {
                    cp.getCell(cp.selectedX - i, y).setColor(Cell.RED);
                }
            } else if (cp.selectedY > y) {
                cp.direction = Ship.DOWN;
                for (int i = 0; i < Server.SHIPS_SIZE[setp.shipIndex]; i++) {
                    cp.getCell(x, cp.selectedY - i).setColor(Cell.RED);
                }
            } else if (cp.selectedY < y) {
                cp.direction = Ship.UP;
                for (int i = 0; i < Server.SHIPS_SIZE[setp.shipIndex]; i++) {
                    cp.getCell(x, cp.selectedY + i).setColor(Cell.RED);
                }
            }
            setColorEnabled(Cell.WHITE, false);
            setp.bt_regShip.setEnabled(true);
        } else if (cell.getColor() == Cell.RED && cp.selectedX == x && cp.selectedY == y) {
            if (cp.direction == -1) {
                cell.setColor(Cell.WHITE);
                turnNextColor(x, y, Cell.WHITE);
            } else {
                changeColor(Cell.RED, Cell.WHITE);
                changeColor(Cell.YELLOW, Cell.WHITE);
                cp.direction = -1;
            }
            cp.selectedX = -1;
            cp.selectedY = -1;
            setColorEnabled(Cell.WHITE, true);
        } else if (cell.getColor() == Cell.RED && (cp.selectedX != x || cp.selectedY != y)) {
            if (cp.direction == Ship.UP || cp.direction == Ship.DOWN) {
                for (int i = 0; i < Server.FIELD_SIZE_Y; i++) {
                    if (cp.getCell(x, i).getColor() == Cell.RED) {
                        cp.getCell(x, i).setColor(Cell.WHITE);
                    }
                }
            } else if (cp.direction == Ship.LEFT || cp.direction == Ship.RIGHT) {
                for (int i = 0; i < Server.FIELD_SIZE_X; i++) {
                    if (cp.getCell(i, y).getColor() == Cell.RED) {
                        cp.getCell(i, y).setColor(Cell.WHITE);
                    }
                }
            }
            cp.direction = -1;
            cp.getCell(cp.selectedX, cp.selectedY).setColor(Cell.RED);
            cp.getCell(cp.selectedX, cp.selectedY).setEnabled(true);
            turnNextColor(cp.selectedX, cp.selectedY, Cell.YELLOW);
            setColorEnabled(Cell.WHITE, false);
            setColorEnabled(Cell.YELLOW, true);
            setp.bt_regShip.setEnabled(false);
        }

        System.out.println("x: " + x);
        System.out.println("y: " + y);
        System.out.println("dir: " + cp.direction);

//        if (index == 0 && count == 0) {//第1段階
//            this.setp.bt_regShip.setEnabled(false);
//            for (int j = 0; j <= maxY; j++) {
//                for (int i = 0; i <= maxX; i++) {
//                    c = this.cp.getCell(i, j);
//                    if (i == x && j == y) {
//                        c.setColor(Cell.RED);
//                    } else if ((i == x - 1 && j == y) || (i == x + 1 && j == y) || (i == x && j == y - 1) || (i == x && j == y + 1)) {
//                        c.setColor(Cell.YELLOW);
//                    } else {
//                        c.setEnabled(false);
//                    }
//                }
//            }
//            this.setp.count += 1;
//        } else if (count == 0) {//2つ目以降の船の設置開始
//            this.setp.bt_regShip.setEnabled(false);
//            if (this.checkBlack(cell)) {
//                for (int j = 0; j <= maxY; j++) {
//                    for (int i = 0; i <= maxX; i++) {
//                        c = this.cp.getCell(i, j);
//                        if (i == x && j == y) {
//                            c.setColor(Cell.RED);
//                        } else if ((i == x - 1 && j == y) || (i == x + 1 && j == y) || (i == x && j == y - 1) || (i == x && j == y + 1)) {
//                            if (c.color != Cell.BLACK && c.color != Cell.BLUE) c.setColor(Cell.YELLOW);
//                        } else {
//                            c.setEnabled(false);
//                        }
//                    }
//                }
//                this.setp.count += 1;
//            }
//        } else {//船を設置中
//            cell.setColor(Cell.RED);
//            if (x - 1 >= 0 && this.cp.getCell(x - 1, y).color == Cell.RED) {
//                if (x + 1 <= maxX && this.cp.getCell(x + 1, y).color != Cell.BLACK) {
//                    this.cp.getCell(x + 1, y).setColor(Cell.YELLOW);
//                }
//                if (this.count == 1) {
//                    if (y - 1 >= 0 && this.cp.getCell(x - 1, y - 1).color == Cell.YELLOW) {
//                        this.cp.getCell(x - 1, y - 1).setColor(Cell.WHITE);
//                        this.cp.getCell(x - 1, y - 1).setEnabled(false);
//                    }
//                    if (y + 1 <= maxY && this.cp.getCell(x - 1, y + 1).color == Cell.YELLOW) {
//                        this.cp.getCell(x - 1, y + 1).setColor(Cell.WHITE);
//                        this.cp.getCell(x - 1, y + 1).setEnabled(false);
//                    }
//                }
//                this.setp.count += 1;
//            } else if (x + 1 <= maxX && this.cp.getCell(x + 1, y).color == Cell.RED) {
//                if (x - 1 >= 0 && this.cp.getCell(x - 1, y).color != Cell.BLACK) {
//                    this.cp.getCell(x - 1, y).setColor(Cell.YELLOW);
//                }
//                if (this.count == 1) {
//                    if (y - 1 >= 0 && this.cp.getCell(x + 1, y - 1).color == Cell.YELLOW) {
//                        this.cp.getCell(x + 1, y - 1).setColor(Cell.WHITE);
//                        this.cp.getCell(x + 1, y - 1).setEnabled(false);
//                    }
//                    if (y + 1 <= maxY && this.cp.getCell(x + 1, y + 1).color == Cell.YELLOW) {
//                        this.cp.getCell(x + 1, y + 1).setColor(Cell.WHITE);
//                        this.cp.getCell(x + 1, y + 1).setEnabled(false);
//                    }
//                }
//                this.setp.count += 1;
//            } else if (y - 1 >= 0 && this.cp.getCell(x, y - 1).color == Cell.RED) {
//                if (y + 1 <= maxY && this.cp.getCell(x, y + 1).color != Cell.BLACK) {
//                    this.cp.getCell(x, y + 1).setColor(Cell.YELLOW);
//                }
//                if (this.count == 1) {
//                    if (x - 1 >= 0 && this.cp.getCell(x - 1, y - 1).color == Cell.YELLOW) {
//                        this.cp.getCell(x - 1, y - 1).setColor(Cell.WHITE);
//                        this.cp.getCell(x - 1, y - 1).setEnabled(false);
//                    }
//                    if (x + 1 <= maxX && this.cp.getCell(x + 1, y - 1).color == Cell.YELLOW) {
//                        this.cp.getCell(x + 1, y - 1).setColor(Cell.WHITE);
//                        this.cp.getCell(x + 1, y - 1).setEnabled(false);
//                    }
//                }
//                this.setp.count += 1;
//            } else if (y + 1 <= maxY && this.cp.getCell(x, y + 1).color == Cell.RED) {
//                if (y - 1 >= 0 && this.cp.getCell(x, y - 1).color != Cell.BLACK) {
//                    this.cp.getCell(x, y - 1).setColor(Cell.YELLOW);
//                }
//                if (this.count == 1) {
//                    if (x - 1 >= 0 && this.cp.getCell(x - 1, y + 1).color == Cell.YELLOW) {
//                        this.cp.getCell(x - 1, y + 1).setColor(Cell.WHITE);
//                        this.cp.getCell(x - 1, y + 1).setEnabled(false);
//                    }
//                    if (x + 1 <= maxX && this.cp.getCell(x + 1, y + 1).color == Cell.YELLOW) {
//                        this.cp.getCell(x + 1, y + 1).setColor(Cell.WHITE);
//                        this.cp.getCell(x + 1, y + 1).setEnabled(false);
//                    }
//                }
//                this.setp.count += 1;
//            }
//        }
//        if (this.setp.count == ship) {
//            this.setp.bt_regShip.setEnabled(true);
//            this.setp.index += 1;
//            for (int j = 0; j <= maxY; j++) {
//                for (int i = 0; i <= maxX; i++) {
//                    c = this.cp.getCell(i, j);
//                    if (c.color == Cell.YELLOW) {
//                        c.setColor(Cell.WHITE);
//                    }
//                }
//            }
//        }
    }

    private void turnNextColor(int x, int y, int color) {
        for (int i = 0; i < 4; i++) {
            if (main.player.manager.canSetShip(Server.SHIPS_SIZE[setp.shipIndex], x, y, i)) {
                switch (i) {
                    case Ship.UP:
                        if (y - 1 >= 0) {
                            cp.getCell(x, y - 1).setColor(color);
                        }
                        break;
                    case Ship.RIGHT:
                        if (x + 1 < Server.FIELD_SIZE_X) {
                            cp.getCell(x + 1, y).setColor(color);
                        }
                        break;
                    case Ship.DOWN:
                        if (y + 1 < Server.FIELD_SIZE_Y) {
                            cp.getCell(x, y + 1).setColor(color);
                        }
                        break;
                    case Ship.LEFT:
                        if (x - 1 >= 0) {
                            cp.getCell(x - 1, y).setColor(color);
                        }
                        break;
                }
            }
        }
    }

    private void setColorEnabled(int color, boolean bool) {
        for (int j = 0; j < Server.FIELD_SIZE_Y; j++) {
            for (int i = 0; i < Server.FIELD_SIZE_X; i++) {
                Cell c = cp.getCell(i, j);
                if (c.color == color) {
                    c.setEnabled(bool);
                }
            }
        }
    }

    private void changeColor(int priorColor, int postColor) {
        for (int j = 0; j < Server.FIELD_SIZE_Y; j++) {
            for (int i = 0; i < Server.FIELD_SIZE_X; i++) {
                Cell c = cp.getCell(i, j);
                if (c.color == priorColor) {
                    c.setColor(postColor);
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
