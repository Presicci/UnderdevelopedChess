package main.com.udcinc.udc.game.board;

import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.piece.impl.Pawn;

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
    
    private Tile passantTile = null;
    
    public Board(int size) {
        tiles = new Tile[size][size];
    }
	
    /**
     * Removes the tile from the piece and the piece from the tile
     * @param piece The piece being captured
     */
	public void killPiece(Piece piece) {
		tiles[piece.getPosition().getX()][piece.getPosition().getY()].setPiece(null);
		piece.doKill();
	}
	
	/**
	 * Moves a piece to a tile at position nextPos
	 * @param piece Piece being moved
	 * @param nextPos Position being moved to
	 */
	public void movePiece(Piece piece, Position nextPos) {
		passantTile = null;
		if (piece instanceof Pawn) {
			if (piece.getPosition().getY() == nextPos.getY() + 2 
					|| piece.getPosition().getY() == nextPos.getY() - 2) {
				passantTile = tiles[nextPos.getX()][nextPos.getY()];
			}
		}
		
		piece.move(nextPos);
	}

	/**
	 * Adds a tile to the board
	 * @param tile Tile object
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
    public void assignTile(Tile tile, int x, int y) {
        tiles[x][y] = tile;
    }

    /**
     * Gets a tile based on x and y coordinate
     * @param x X coordiante
     * @param y Y coordiante
     * @return tile object at those coordiantes, or null if outside of scope
     */
    public Tile getTile(int x, int y) {
    	// If attempting to fetch invalid tile, return null
    	if (x >= getSize() || y >= getSize() || y < 0 || x < 0) {
    		return null;
    	}
    	return tiles[x][y];
    }
    
    /**
     * Gets a tile based on position object
     * @param pos The position
     * @return tile object at that position, or null if outside of scope
     */
    public Tile getTile(Position pos) {
    	return getTile(pos.getX(), pos.getY());
    }
    
    /**
     * Gets the size of the board
     * @return Board is a square, so just return the length of one axis
     */
    public int getSize() {
    	return tiles[0].length;
    }
    
    public Tile[][] getTiles() {
        return tiles;
    }

	public Tile getPassantTile() {
		return passantTile;
	}
}