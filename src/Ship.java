public class Ship {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private String name;
    private int size;
    private int life;
    private int posX;
    private int posY;
    private int direction;
    private boolean isBombed[][];

    /**
     * @param name      戦艦の名前
     * @param size      戦艦のサイズ
     * @param posX      戦艦の船首のx座標
     * @param posY      戦艦の船首のy座標
     * @param direction 戦艦の向き
     */
    public Ship(String name, int size, int posX, int posY, int direction) {
        isBombed = new boolean[size][size];
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.life = size;
        this.direction = direction;
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


    boolean shipLife(){
        if(this.life <= 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 被弾したかを返す。同じ位置の2回目の被弾はfalse。
     *
     * @param x x座標
     * @param y y座標
     * @return 被弾したかどうか
     */

    boolean bombed(int x, int y) {
        // 正しい位置にある戦艦に対して呼び出すなら、この判定不要かも。
        switch (direction) {
            case HORIZONTAL:
                if (x != posX || (y - posY) > size) return false;
            case VERTICAL:
                if (y != posY || (x - posX) > size) return false;
                break;
        }
        if (isBombed[x - posX][y - posY]) {
            return false;
        } else {
            life--;
            isBombed[x - posX][y - posY] = true;
            return true;
        }

    }



}
