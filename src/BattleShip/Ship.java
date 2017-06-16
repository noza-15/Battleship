package BattleShip;

import java.io.Serializable;

public class Ship extends SeaObject implements Serializable {

    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    private static final long serialVersionUID = -2081082270419630874L;
    private String name;
    private int size;
    private int life;
    private int posX;
    private int posY;
    private int direction;
    private boolean isAlive;

    /**
     * @param name      戦艦の名前
     * @param size      戦艦のサイズ
     * @param posX      戦艦の船首のx座標
     * @param posY      戦艦の船首のy座標
     * @param direction 戦艦の向き
     */
    Ship(String name, int size, int posX, int posY, int direction) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.life = size;
        this.direction = direction;
        this.isAlive = true;
    }

    /**
     * @return 戦艦のサイズ
     */
    public int getSize() {
        return size;
    }

    /**
     * @return 戦艦の残機数
     */
    public int getLife() {
        return life;
    }

    /**
     * @return 戦艦の名前
     */
    public String getName() {
        return name;
    }

    /**
     * ライフを減らす。
     */
    void bombed() {
        life--;
        if (life == 0) {
            isAlive = false;
        }
    }

    /**
     * @return 生存しているか
     */
    public boolean isAlive() {
        return isAlive;
    }
}
