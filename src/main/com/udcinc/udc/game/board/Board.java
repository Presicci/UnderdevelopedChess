package main.com.udcinc.udc.game.board;

import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.scene.GameSceneController;

/**
 * Container which holds a collection of Tiles.
 * Layout of the board in terms of coordinates:
 * [ (0, 0), (1, 0), (2, 0), ... (x, 0) ]
 * [ (0, 1), (1, 1), (2, 1)
 * [ (0, 2), (1, 2)
 * [  ...
 * [ (0, y)
 *
 * @author Thomas Presicci
 */
public class Board {
    private Tile[][] tiles;
    
    public Board(int size) {
        tiles = new Tile[size][size];
    }
	
	public void killPiece(Piece piece) {
		tiles[piece.getPosition().getX()][piece.getPosition().getY()].setPiece(null);
		piece.doKill();
	}

    public void assignTile(Tile tile, int x, int y) {
        tiles[x][y] = tile;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
    
    public Tile getTile(int x, int y) {
    	return tiles[x][y];
    }
    
    public Tile getTile(Position pos) {
    	return getTile(pos.getX(), pos.getY());
    }
    
    public int getSize() {
    	return tiles[0].length;
    }
}