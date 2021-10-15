package main.com.udcinc.udc.game.piece.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.udcinc.udc.game.GameState;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.piece.TwoDimentionalRaycast;
import main.com.udcinc.udc.game.player.Player;

public class Queen extends Piece {

	public Queen(Player player, Tile tile, GameState gs) {
    	super(player, tile, gs);
		this.name = "Queen";
	}
	
	@Override
	public boolean canMove(Tile tile) {
    	if (tile.getPosition().getX() == getPosition().getX() || tile.getPosition().getY() == getPosition().getY()) {
    		return TwoDimentionalRaycast.straightRaycast(tile, this, gs);
    	} else {
    		return TwoDimentionalRaycast.diagonalRaycast(tile, this, gs);
    	}
    }
	
	@Override
	public List<Tile> getAllValidMoves() {
		List<Tile> possibleTiles = new ArrayList<>();
        for (Tile[] row : gs.getBoard().getTiles()) {
            for (Tile tile : row) {
            	if ((tile.getPosition().getX() == getPosition().getX() || tile.getPosition().getY() == getPosition().getY()) 
            			&& TwoDimentionalRaycast.straightRaycast(tile, this, gs)) {
                    possibleTiles.add(tile);
                }
            }
        }
        possibleTiles.addAll(TwoDimentionalRaycast.diagonalRaycastList(this, gs));
        return possibleTiles;
    }

}
