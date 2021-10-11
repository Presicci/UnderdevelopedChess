package main.com.udcinc.udc.game.piece.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.udcinc.udc.game.GameStatics;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.piece.TwoDimentionalRaycast;

public class Queen extends Piece {

	public Queen(Tile tile) {
		super(tile);
		this.name = "Queen";
		tile.setPiece(this);
	}
	
	@Override
	public boolean canMove(Tile tile) {
    	if (tile.getPosition().getX() == getPosition().getX() || tile.getPosition().getY() == getPosition().getY()) {
    		return TwoDimentionalRaycast.straightRaycast(tile, this);
    	} else {
    		return TwoDimentionalRaycast.diagonalRaycast(tile, this);
    	}
    }
	
	@Override
	public List<Tile> getAllValidMoves() {
		List<Tile> possibleTiles = new ArrayList<>();
        for (Tile[] row : GameStatics.getGameState().getBoard().getTiles()) {
            for (Tile tile : row) {
            	if ((tile.getPosition().getX() == getPosition().getX() || tile.getPosition().getY() == getPosition().getY()) 
            			&& TwoDimentionalRaycast.straightRaycast(tile, this)) {
                    possibleTiles.add(tile);
                }
            }
        }
        possibleTiles.addAll(TwoDimentionalRaycast.diagonalRaycastList(this));
        return possibleTiles;
    }

}
