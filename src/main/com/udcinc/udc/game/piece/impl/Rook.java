package main.com.udcinc.udc.game.piece.impl;

import javafx.scene.image.Image;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.piece.TwoDimentionalRaycast;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.state.GameState;
/**
 * Rook piece, can move horizontally and vertically in all directions.
 * 
 * @author Thomas Presicci
 */
public class Rook extends Piece {
    public Rook(Player player, Tile tile, GameState gs) {
    	super(player, tile, gs);
        this.name = "Rook";
        this.image = new Image("./rook.png");
    }
    
    @Override
    public boolean canMove(Tile tile) {
    	if (tile.getPosition().getX() == getPosition().getX() || tile.getPosition().getY() == getPosition().getY()) {
    		return TwoDimentionalRaycast.straightRaycast(tile, this, gs);
    	}
    	return false;
    }
}
