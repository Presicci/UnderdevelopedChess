package main.com.udcinc.udc.game.piece.impl;

import java.util.List;

import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.piece.TwoDimentionalRaycast;
import main.com.udcinc.udc.game.player.Player;

public class Bishop extends Piece {

	public Bishop(Player player, Tile tile) {
		super(player, tile);
		this.name = "Bishop";
	}

	@Override
	public boolean canMove(Tile tile) {
		return TwoDimentionalRaycast.diagonalRaycast(tile, this);
	}
	
	@Override
	public List<Tile> getAllValidMoves() {
        return TwoDimentionalRaycast.diagonalRaycastList(this);
    }
}
