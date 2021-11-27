package main.com.udcinc.udc.game.piece.impl;

import java.util.List;

import javafx.scene.image.Image;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.piece.TwoDimentionalRaycast;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.state.GameState;

/**
 * Bishop piece, can move diagonally in all directions.
 * 
 * @author Thomas Presicci
 */
public class Bishop extends Piece {
	
	public Bishop(Player player, Tile tile, GameState gs) {
    	super(player, tile, gs);
		this.name = "Bishop";
		this.image = new Image("./main/resources/sprites/bishop.png");
	}

	/**
	 * Checks if the tile is in an non-obstructed diagonal tile 
	 * @param tile The tile being checked
	 */
	@Override public boolean canMove(Tile tile) {
		return TwoDimentionalRaycast.diagonalRaycast(tile, this, gs, false);
	}
	
	/**
	 * Returns all tiles that are not obstructed in a diagonal from the bishop
	 */
	@Override public List<Tile> getAllValidMoves() {
        return TwoDimentionalRaycast.diagonalRaycastList(this, gs, false);
    }
	
	/**
	 * 
	 */
	@Override public List<Tile> getDangerousTiles() {
        return TwoDimentionalRaycast.diagonalRaycastList(this, gs, true);
    }
}
