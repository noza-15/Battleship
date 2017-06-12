class ShipManager {
    private ShipNew[][] myShips;
    private ShipNew[][][] othersShips;
    private boolean[][] isBombed;
    private int aliveCount;
    private int sunkenCount;

    ShipManager() {
        myShips = new ShipNew[Server.FIELD_SIZE_X][Server.FIELD_SIZE_Y];
        isBombed = new boolean[Server.FIELD_SIZE_X][Server.FIELD_SIZE_Y];
    }

    /**
     * 戦艦を配置するメソッド
     *
     * @param name      戦艦の名前
     * @param size      戦艦のサイズ
     * @param x         戦艦のx座標
     * @param y         戦艦のy座標
     * @param direction 戦艦の向き(上下左右)
     * @return 戦艦を配置できた場合はtrue。できなかった場合はfalse。
     */
    boolean setShip(String name, int size, int x, int y, int direction) {
        boolean settable = true;
        ShipNew ship = new ShipNew(name, size, x, y, direction);
        if (x < 0 || x >= Server.FIELD_SIZE_X || y < 0 || y >= Server.FIELD_SIZE_Y) {
            return false;
        }
        switch (direction) {
            case Ship.UP:
                for (int i = 0; i < size; i++) {
                    if (y + i >= Server.FIELD_SIZE_Y) {
                        settable = false;
                        break;
                    } else if (myShips[x][y + i] != null) {
                        settable = false;
                        break;
                    }
                }
                if (settable) {
                    for (int i = 0; i < size; i++) {
                        myShips[x][y + i] = ship;
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
                if (settable) {
                    for (int i = 0; i < size; i++) {
                        myShips[x + i][y] = ship;
                    }
                }
                break;
            case Ship.DOWN:
                for (int i = 0; i < size; i++) {
                    if (y - i < 0) {
                        settable = false;
                        break;
                    } else if (myShips[x][y - i] != null) {
                        settable = false;
                        break;
                    }
                }
                if (settable) {
                    for (int i = 0; i < size; i++) {
                        myShips[x][y - i] = ship;
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
                if (settable) {
                    for (int i = 0; i < size; i++) {
                        myShips[x - i][y] = ship;
                    }
                }
                break;
        }
        if (settable) {
            aliveCount++;
        }
        return settable;
    }

    /**
     * 攻撃判定をするメソッド
     *
     * @param x x座標
     * @param y y座標
     * @return 今の攻撃で被弾した場合はtrue。すでに被弾したか、そこに戦艦がなかった場合はfalse。
     */
    boolean isBombed(int x, int y) {
        if (!isBombed[x][y]) {
            myShips[x][y].bombed();
            isBombed[x][y] = true;
            if (!myShips[x][y].isAlive()) {
                sunkenCount++;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param ships 他のプレイヤーの盤面
     */
    void setOthersShips(ShipNew[][][] ships) {
        this.othersShips = ships;
    }

    /**
     * @return 自分の盤面
     */
    ShipNew[][] getMyShips() {
        return myShips;
    }

}
