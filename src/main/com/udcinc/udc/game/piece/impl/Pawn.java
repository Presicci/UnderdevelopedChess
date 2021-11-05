package main.com.udcinc.udc.game.piece.impl;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import main.com.udcinc.udc.game.board.Board;
import main.com.udcinc.udc.game.board.Position;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.state.GameState;

/**
 * Pawn piece, can move forward once or twice forward from its starting position, or once afterwards
 * Can capture diagonally adjacent tiles
 * 
 * @author Thomas Presicci
 */
public class Pawn extends Piece {
	// Set to true as soon as the piece is moved
	boolean moved = false;
	
	public Pawn(Player player, Tile tile, GameState gs) {
    	super(player, tile, gs);
		this.name = "Pawn";
        this.image = new Image("./pawn.png");
	}

	/**
     * Moves this piece to the proived position
     * unchecked for now for testing
     * 
     * @param pos The position to move the piece to.
     */
	@Override
    public void move(Position pos) {
    	this.moved = true;
    	this.tile.setPiece(null);
    	this.position = pos;
    	this.setTile(gs.getBoard().getTiles()[pos.getX()][pos.getY()]);
    	this.tile.setPiece(this);
    }
	/**
	 * Checks that the tile is one y up or down, or that the tile is diagonally adjacent and contains an enemy piece
	 * OR if the piece has yet to move, checks that the tile is two y up or down as well
	 * @param tile The tile being checked
	 */
	@Override public boolean canMove(Tile tile) {
		Position pPos = this.getPosition();
		Position tPos = tile.getPosition();
		
		// If the pawn is in its starting position it has a linear range of 2
		int linearRange = moved ? 1 : 2;
		
		// Vertical range taking into account if the piece is in its starting position
		boolean isFirstMoveTile = tPos.getY() == (this.getOwner().isWhite() ? pPos.getY() - linearRange : pPos.getY() + linearRange);
		
		// Checks if the tile's y coord is adjacent to the piece's y coord
		boolean isYAdjacent = tPos.getY() == (this.getOwner().isWhite() ? pPos.getY() - 1 : pPos.getY() + 1);
		
		if (pPos.getX() == tPos.getX()) {	// Tile is in the same column
			return tile.hasPiece() ? false : (isFirstMoveTile || isYAdjacent);
		} else if (Math.abs(pPos.getX() - tPos.getX()) == 1) {	// Tile is in an adjacent column
			// Can only move diagonally if there is an enemy piece to cap
			return isYAdjacent && tile.hasPiece() && tile.getPiece().getOwner() != this.getOwner();
		}
		return false;
	}
	
	/**
	 * Overrides to do as few comparisons as possible,
	 * only calculates for tiles the pawn could potentially
	 * move to
	 */
	@Override public List<Tile> getAllValidMoves() {
		Position pPos = this.getPosition();
		int pX = pPos.getX();		
		List<Tile> possibleTiles = new ArrayList<>();
		Board board = gs.getBoard();
        
		// Gets the y coord for possible moves
        int y = this.getOwner().isWhite() ? pPos.getY() - 1 : pPos.getY() + 1;
        if (y < 0 || y > board.getSize() - 1) {	// If piece is at board edge, return empty list
        	return possibleTiles;
        }
        	
        // Iterates through the 3 possible pawn move tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(board.getTile(pX, y));
        tiles.add(board.getTile(pX + 1, y));
        tiles.add(board.getTile(pX - 1, y));
            
        // Y coordinate if the piece is in its starting position
        int firstMoveY = this.getOwner().isWhite() ? pPos.getY() - 2 : pPos.getY() + 2;
        if (!moved) {	// If the piece has yet to move, add an extra vertical tile
            tiles.add(board.getTile(pX, firstMoveY));
        }
        
        
        for (Tile tile : tiles) {
        	if (tile == null) {
        		continue;
        	}
        	if (tile.getPosition().getX() == pX) {	// Can only move forward if no pieces are present
        		if (!tile.hasPiece()) {
        			possibleTiles.add(tile);
        		}
        	} else {	// Can only move diagonally if an enemy piece is present
        		if (tile.hasPiece() && tile.getPiece().getOwner() != this.getOwner()) {
        			possibleTiles.add(tile);
        		}
        	}
        }
        return possibleTiles;
	}
	
	
	// Getters/setters
	public boolean hasMoved() {
		return moved;
	}
	
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
}
