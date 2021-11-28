package main.com.udcinc.udc.game.piece.impl;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import main.com.udcinc.udc.game.board.Position;
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
	
	// Set to true as soon as the piece is moved
	private boolean moved = false;
		
    public Rook(Player player, Tile tile, GameState gs) {
    	super(player, tile, gs);
        this.name = "Rook";
        this.image = new Image("./main/resources/sprites/rook.png");
    }
    
    /**
     * Moves this piece to the provided position
     * overwritten to set moved to true
     * 
     * @param pos The position to move the piece to.
     */
	@Override
    public void move(Position pos) {
    	this.moved = true;
    	this.tile.setPiece(null);
    	this.position = pos;
    	this.setTile(gs.getBoard().getTiles()[pos.getX()][pos.getY()]);
    	this.tile.setPiece(this);
    }
    
    /**
     * Does a straight raycast either vertically or horizontally
     * @param tile Tile being checked
     */
    @Override public boolean canMove(Tile tile) {
    	if (tile.getPosition().getX() == getPosition().getX() || tile.getPosition().getY() == getPosition().getY()) {
    		return TwoDimentionalRaycast.straightRaycast(tile, this, gs, false);
    	}
    	return false;
    }
    
    /**
     * 
     */
    public List<Tile> getDangerousTiles() {
        List<Tile> possibleTiles = new ArrayList<>();
        for (Tile[] row : gs.getBoard().getTiles()) {
            for (Tile tile : row) {
            	if (tile.getPosition().getX() == getPosition().getX() || tile.getPosition().getY() == getPosition().getY()) {
                    possibleTiles.add(tile);
                }
            }
        }
        return possibleTiles;
    }
    
    // Getters/setters
 	public boolean hasMoved() {
 		return moved;
 	}
 	
 	public void setMoved(boolean moved) {
 		this.moved = moved;
 	}
}
