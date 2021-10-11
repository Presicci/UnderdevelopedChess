package main.com.udcinc.udc.game.piece.impl;

import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.piece.TwoDimentionalRaycast;

public class Bishop extends Piece {

	public Bishop(Tile tile) {
		super(tile);
		this.name = "Bishop";
		tile.setPiece(this);
	}

	@Override
	public boolean canMove(Tile tile) {
		return TwoDimentionalRaycast.diagonalRaycast(tile, this);
	}
}
