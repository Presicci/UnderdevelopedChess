package main.com.udcinc.udc.game.piece.impl;

import main.com.udcinc.udc.game.GameState;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.piece.TwoDimentionalRaycast;
import main.com.udcinc.udc.game.player.Player;

public class Rook extends Piece {
    public Rook(Player player, Tile tile, GameState gs) {
    	super(player, tile, gs);
        this.name = "Rook";
    }
    
    @Override
    public boolean canMove(Tile tile) {
    	if (tile.getPosition().getX() == getPosition().getX() || tile.getPosition().getY() == getPosition().getY()) {
    		return TwoDimentionalRaycast.straightRaycast(tile, this, gs);
    	}
    	return false;
    }
}
