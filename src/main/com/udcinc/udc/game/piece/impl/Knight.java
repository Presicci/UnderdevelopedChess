package main.com.udcinc.udc.game.piece.impl;

import main.com.udcinc.udc.game.GameState;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.player.Player;

public class Knight extends Piece {

	public Knight(Player player, Tile tile, GameState gs) {
    	super(player, tile, gs);
		this.name = "Knight";
	}

}
