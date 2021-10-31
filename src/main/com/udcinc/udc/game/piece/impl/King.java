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

public class King extends Piece {
	
	public King(Player player, Tile tile, GameState gs) {
    	super(player, tile, gs);
		this.name = "King";
        this.image = new Image("./king.png");
	}
	
	@Override
	public boolean canMove(Tile tile) {
		Position pPos = this.getPosition();
		Position tPos = tile.getPosition();
		
		// Checks if the tile is adjacent to the piece
		boolean isAdjacent = Math.abs(tPos.getY() - pPos.getY()) <= 1 && Math.abs(tPos.getX() - pPos.getX()) <= 1;
		
		if (isAdjacent) {
			// If tile does not have an allied piece, tile is valid
			return !tile.hasPiece() || tile.getPiece().getOwner() != this.getOwner();
		}
		return false;
	}
	
	/**
	 * Overrides to do as few comparisons as possible,
	 * only calculates for adjacent tiles
	 */
	@Override public List<Tile> getAllValidMoves() {
		Position pPos = this.getPosition();
		int pX = pPos.getX();		
		int pY = pPos.getY();		
		List<Tile> possibleTiles = new ArrayList<>();
		Board board = gs.getBoard();
		
        // Attempts to add all possible king moves to list
        List<Tile> tiles = new ArrayList<>();
        tiles.add(board.getTile(pX, pY + 1));
        tiles.add(board.getTile(pX, pY - 1));
        tiles.add(board.getTile(pX + 1, pY + 1));
        tiles.add(board.getTile(pX + 1, pY - 1));
        tiles.add(board.getTile(pX + 1, pY));
        tiles.add(board.getTile(pX - 1, pY + 1));
        tiles.add(board.getTile(pX - 1, pY - 1));
        tiles.add(board.getTile(pX - 1, pY));
        // Iterate through possible tiles
        for (Tile tile : tiles) {
        	if (tile != null && !(tile.hasPiece() && tile.getPiece().getOwner() == this.getOwner())) {
        		possibleTiles.add(tile);
        	}
        }
        return possibleTiles;
	}
}
