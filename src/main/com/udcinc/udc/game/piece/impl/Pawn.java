package main.com.udcinc.udc.game.piece.impl;

import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.player.Player;

public class Pawn extends Piece {

	public Pawn(Player player, Tile tile) {
		super(player, tile);
		this.name = "Pawn";
	}

}
