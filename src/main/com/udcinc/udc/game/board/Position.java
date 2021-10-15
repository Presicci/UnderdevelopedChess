package main.com.udcinc.udc.game.board;

/**
 * Simple coordinate representation of a tile on the board.
 * 
 * @author Thomas Presicci
 */
public class Position {
    private final int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
