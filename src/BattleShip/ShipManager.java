package BattleShip;

public class ShipManager {
    //    static final int NONE = 0;
    static final int UNKNOWN = 0;
    static final int BOMB_HIT = 2;
    static final int BOMB_MISS = 3;
    static final int BOMB_ALREADY_HIT = 4;
    static final int BOMB_HIT_NEXT = 5;
    private boolean[][] myAttacks;
    private Ship[][] myShips;
    private Ship[][][] shipsMap;
    private int[][][] state;
    private int[] sunkenCount;


    public ShipManager() {
        myShips = new Ship[Server.FIELD_SIZE_X][Server.FIELD_SIZE_Y];
        myAttacks = new boolean[Server.FIELD_SIZE_X][Server.FIELD_SIZE_Y];
    }

    /**
     * 戦艦を配置するメソッド
     *
     * @param name      戦艦の名前
     * @param size      戦艦のサイズ
     * @param x         戦艦のx座標
     * @param y         戦艦のy座標
     * @param direction 戦艦の向き(上下左右)
     */
    public void setMyShip(String name, int size, int x, int y, int direction) {
        Ship ship = new Ship(name, size, x, y, direction);
        if (x < 0 || x >= Server.FIELD_SIZE_X || y < 0 || y >= Server.FIELD_SIZE_Y) {
            return;
        }
        if (canSetShip(size, x, y, direction)) {
            for (int i = 0; i < size; i++) {
                switch (direction) {
                    case Ship.UP:
                        myShips[x][y - i] = ship;
                        break;
                    case Ship.RIGHT:
                        myShips[x + i][y] = ship;
                        break;
                    case Ship.DOWN:
                        myShips[x][y + i] = ship;
                        break;
                    case Ship.LEFT:
                        myShips[x - i][y] = ship;
                        break;
                }
            }
        }
    }

    public boolean canSetShip(int size, int x, int y, int direction) {
        boolean settable = true;
        if (x < 0 || x >= Server.FIELD_SIZE_X || y < 0 || y >= Server.FIELD_SIZE_Y) {
            return false;
        }
        switch (direction) {
            case Ship.UP:
                for (int i = 0; i < size; i++) {
                    if (y - i < 0) {
                        settable = false;
                        break;
                    } else if (myShips[x][y - i] != null) {
                        settable = false;
                        break;
                    }
                }
                break;
            case Ship.RIGHT:
                for (int i = 0; i < size; i++) {
                    if (x + i >= Server.FIELD_SIZE_X) {
                        settable = false;
                        break;
                    } else if (myShips[x + i][y] != null) {
                        settable = false;
                        break;
                    }
                }
                break;
            case Ship.DOWN:
                for (int i = 0; i < size; i++) {
                    if (y + i >= Server.FIELD_SIZE_Y) {
                        settable = false;
                        break;
                    } else if (myShips[x][y + i] != null) {
                        settable = false;
                        break;
                    }
                }
                break;
            case Ship.LEFT:
                for (int i = 0; i < size; i++) {
                    if (x - i < 0) {
                        settable = false;
                        break;
                    } else if (myShips[x - i][y] != null) {
                        settable = false;
                        break;
                    }
                }
                break;
        }
        return settable;
    }

    /**
     * @param ships 他のプレイヤーの盤面
     */
    public void setOthersShips(Ship[][][] ships) {
        this.shipsMap = ships;
        state = new int[ships.length][Server.FIELD_SIZE_X][Server.FIELD_SIZE_Y];
        sunkenCount = new int[ships.length];
    }

    void show(int ID) {
        for (int n = 0; n < Server.FIELD_SIZE_X; n++) {
            System.out.print("\t" + n);
        }
        System.out.println();
        for (int y = 0; y < Server.FIELD_SIZE_Y; y++) {
            System.out.print(y + "\t");
            for (int x = 0; x < Server.FIELD_SIZE_X; x++) {
                if (state[ID][x][y] == 1) {
                    System.out.print("✖\t");
                } else if (state[ID][x][y] == -1) {
                    System.out.print("◯\t");
                } else if (state[ID][x][y] == BOMB_HIT) {
                    System.out.print("@\t");
                } else if (state[ID][x][y] == BOMB_MISS) {
                    System.out.print("*\t");
                } else if (state[ID][x][y] == BOMB_HIT_NEXT) {
                    System.out.print("%\t");
                } else if (state[ID][x][y] == UNKNOWN) {
                    System.out.print("-\t");
                }
            }
            System.out.println();
        }
        System.out.println("沈んだ船の数：" + sunkenCount[ID]);
    }

    /**
     * 攻撃判定をするメソッド
     *
     * @param x x座標
     * @param y y座標
     * @return 今の攻撃で被弾した場合はtrue。すでに被弾したか、そこに戦艦がなかった場合はfalse。
     */
    int isBombed(int attackerID, int ID, int x, int y) {
        if (attackerID == ID) {
            return 0;
        }
        if (x == -1) {
            return 0;
        }
        if (shipsMap[ID][x][y] == null) {
            if (y + 1 < Server.FIELD_SIZE_Y && shipsMap[ID][x][y + 1] != null) {
                return state[ID][x][y] = BOMB_HIT_NEXT;
            }
            if (y - 1 >= 0 && shipsMap[ID][x][y - 1] != null) {
                return state[ID][x][y] = BOMB_HIT_NEXT;
            }
            if (x + 1 < Server.FIELD_SIZE_X && shipsMap[ID][x + 1][y] != null) {
                return state[ID][x][y] = BOMB_HIT_NEXT;
            }
            if (x - 1 >= 0 && shipsMap[ID][x - 1][y] != null) {
                return state[ID][x][y] = BOMB_HIT_NEXT;
            }
            return state[ID][x][y] = BOMB_MISS;
        } else if (state[ID][x][y] == UNKNOWN) {
            shipsMap[ID][x][y].bombed();
            if (!shipsMap[ID][x][y].isAlive()) {
                sunkenCount[ID]++;
            }
            return state[ID][x][y] = BOMB_HIT;
        } else if (state[ID][x][y] == BOMB_HIT) {
            return BOMB_ALREADY_HIT;
        } else {
            return state[ID][x][y];
        }
    }

    void setMyAttack(int x, int y) {
        myAttacks[x][y] = true;
    }

    public boolean[][] getMyAttacks() {
        return myAttacks;
    }

    boolean isAlive(int ID) {
        return sunkenCount[ID] < 3;
    }

    /**
     * @return 自分の盤面
     */
    public Ship[][] getMyShips() {
        return myShips;
    }

    Ship[][] getShipsMap(int ID) {
        return shipsMap[ID];
    }

}
