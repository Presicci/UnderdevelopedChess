package main.com.udcinc.udc.game.board;

import java.util.ArrayList;
import java.util.List;

import main.com.udcinc.udc.game.GameRules;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.piece.impl.Pawn;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.state.GameState;

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
    
    // Tile of a pawn that has moved two tiles vertically last turn
    private Tile passantTile = null;
    // Whether en passant is enabled or not
    private boolean enPassant;
    
    public Board(int size, GameRules rules) {
        tiles = new Tile[size][size];
        this.enPassant = rules.isEnPassantAllowed();
    }
	
    /**
     * Removes the tile from the piece and the piece from the tile
     * @param piece The piece being captured
     */
	public void killPiece(Piece piece) {
		Tile tile = tiles[piece.getPosition().getX()][piece.getPosition().getY()];
		// Avoid possibly removing another piece from the tile, ordering
		if (tile.getPiece() == piece) {
			tile.setPiece(null);
		}
		piece.doKill();
	}
	
	/**
	 * Moves a piece to a tile at position nextPos
	 * @param piece Piece being moved
	 * @param nextPos Position being moved to
	 */
	public void movePiece(Piece piece, Position nextPos) {
		/* En passant handling */
		// Reset the value of the passant tile, whether or not we are reassigning it
		passantTile = null;
		if (piece instanceof Pawn && enPassant) {
			if (piece.getPosition().getY() == nextPos.getY() + 2 
					|| piece.getPosition().getY() == nextPos.getY() - 2) {
				// Assign passant tile
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
     * Returns a list of all tiles that are dangerous to the given player's king
     * @param player The player that dangerous tiles are being calculated for
     * @return List of tiles that are dangerous to the player's king
     */
    public List<Tile> getDangerousTiles(Player player) {
    	List<Tile> dangerousTiles = new ArrayList<>();
    	for (Tile[] column : tiles) {
    		for (Tile tile : column) {
    			Piece piece = tile.getPiece();
        		if (piece != null && piece.getOwner() != player) {
        			dangerousTiles.addAll(piece.getDangerousTiles());
        		}
    		}
    	}
    	return dangerousTiles;
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