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

public class Pawn extends Piece {

	public Pawn(Player player, Tile tile, GameState gs) {
    	super(player, tile, gs);
		this.name = "Pawn";
        this.image = new Image("./rook.png");	// temp image location
	}

	@Override
	public boolean canMove(Tile tile) {
		Position pPos = this.getPosition();
		Position tPos = tile.getPosition();
		
		// Checks if the tile's y coord is adjacent to the piece's y coord
		boolean isYAdjacent = tPos.getY() == (this.getOwner().isWhite() ? pPos.getY() - 1 : pPos.getY() + 1);
		
		if (pPos.getX() == tPos.getX()) {	// Tile is in the same column
			return tile.hasPiece() ? false : isYAdjacent;
		} else if (Math.abs(pPos.getX() - tPos.getX()) == 1) {	// Tile is in an adjacent column
			// Can only move diagonally if there is an enemy piece to cap
			return isYAdjacent && tile.hasPiece() && tile.getPiece().getOwner() != this.getOwner();
		}
		return false;
	}
	
	/**
	 * Overrides to do as few comparisons as possible,
	 * only calculates for 
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
        if (pX != board.getSize() - 1) {	// If piece is on right edge of board, don't scan right
        	tiles.add(board.getTile(pX + 1, y));
        }
        if (pX != 0) {	// If piece is on left edge of board, don't scan left
        	tiles.add(board.getTile(pX - 1, y));
        }
        
        
        for (Tile tile : tiles) {
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
}
