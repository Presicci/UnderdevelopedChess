package main.com.udcinc.udc.game.piece.impl;

import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.piece.TwoDimentionalRaycast;

public class Rook extends Piece {
    public Rook(Tile tile) {
    	super(tile);
        this.name = "Rook";
        tile.setPiece(this);
    }
    
    @Override
    public boolean canMove(Tile tile) {
    	if (tile.getPosition().getX() == getPosition().getX() || tile.getPosition().getY() == getPosition().getY()) {
    		return TwoDimentionalRaycast.straightRaycast(tile, this);
    	}
    	return false;
    }
}
