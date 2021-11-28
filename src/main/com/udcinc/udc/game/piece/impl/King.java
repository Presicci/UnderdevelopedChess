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
 * King piece, can move to any adjacent tile, linearly and diagonally
 * 
 * @author Thomas Presicci
 */
public class King extends Piece {
	
	// Set to true as soon as the piece is moved
	private boolean moved = false;
	
	public King(Player player, Tile tile, GameState gs) {
    	super(player, tile, gs);
		this.name = "King";
        this.image = new Image("./main/resources/sprites/king.png");
	}

	/**
     * Moves this piece to the provided position
     * overwritten to set moved to true
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
	 * Checks if the tile is adjacent to the king diagonally and linearly
	 * @param tile The tile being checked
	 */
	@Override public boolean canMove(Tile tile) {
		Position pPos = this.getPosition();
		Position tPos = tile.getPosition();
		
		// Checks if the tile is adjacent to the piece
		boolean isAdjacent = Math.abs(tPos.getY() - pPos.getY()) <= 1 && Math.abs(tPos.getX() - pPos.getX()) <= 1;
		
		// Castling handling
        if (canCastleRight() && tPos.getX() == 6 && tPos.getY() == pPos.getY()) {
        	return true;
        }
        if (canCastleLeft() && tPos.getX() == 2 && tPos.getY() == pPos.getY()) {
        	return true;
        }
		
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
        
        // Castling handling
        if (canCastleRight()) {
        	tiles.add(board.getTile(6, pY));
        }
        if (canCastleLeft()) {
        	tiles.add(board.getTile(2, pY));
        }
        
        // Iterate through possible tiles
        for (Tile tile : tiles) {
        	if (tile != null && !(tile.hasPiece() && tile.getPiece().getOwner() == this.getOwner())) {
        		possibleTiles.add(tile);
        	}
        }
        
        return possibleTiles;
	}

	/**
	 * 
	 */
	@Override public List<Tile> getDangerousTiles() {
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
        	if (tile != null) {
        		possibleTiles.add(tile);
        	}
        }
        return possibleTiles;
	}
	
	private boolean canCastleLeft() {
		if (!gs.getRules().isCastlingAllowed() || moved) {
			return false;
		}
		Board board = gs.getBoard();
		List<Tile> dangerousTiles = board.getDangerousTiles(getOwner());
		Position pos = this.getPosition();
		boolean canCastle = true;
		Piece leftPiece = board.getTile(0, pos.getY()).getPiece();
		if (leftPiece != null && leftPiece instanceof Rook) {
			if (!((Rook) leftPiece).hasMoved()) {
				for (int index = 1; index < pos.getX(); index++) {
					if (board.getTile(index, pos.getY()).hasPiece() || (dangerousTiles.contains(board.getTile(index, pos.getY())) && index != 1)) {
						canCastle = false;
						break;
					}
				}
				return canCastle;
			}
		}
		return false;
	}
	
	private boolean canCastleRight() {
		if (!gs.getRules().isCastlingAllowed() || moved) {
			return false;
		}
		Board board = gs.getBoard();
		List<Tile> dangerousTiles = board.getDangerousTiles(getOwner());
		Position pos = this.getPosition();
		boolean canCastle = true;
		Piece rightPiece = board.getTile(board.getSize() - 1, pos.getY()).getPiece();
		if (rightPiece != null && rightPiece instanceof Rook) {
			if (!((Rook) rightPiece).hasMoved()) {
				for (int index = board.getSize() - 2; index > pos.getX(); index--) {
					if (board.getTile(index, pos.getY()).hasPiece() || dangerousTiles.contains(board.getTile(index, pos.getY()))) {
						canCastle = false;
						break;
					}
				}
				return canCastle;
			}
		}
		return false;
	}
	
	// Getters/setters
	public boolean hasMoved() {
		return moved;
	}
		
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
}
