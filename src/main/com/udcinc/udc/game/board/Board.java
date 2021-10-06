package main.com.udcinc.udc.game.board;

/**
 * Container which holds a collection of Tiles.
 */
public class Board {
    private Tile[][] tiles;

    public Board(int size) {
        tiles = new Tile[size][size];
    }

    public void assignTile(Tile tile, int x, int y) {
        tiles[x][y] = tile;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}