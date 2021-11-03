package main.com.udcinc.udc.game.piece.impl;

import javafx.scene.image.Image;
import main.com.udcinc.udc.game.board.Position;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.state.GameState;

public class Knight extends Piece {

	public Knight(Player player, Tile tile, GameState gs) {
    	super(player, tile, gs);
		this.name = "Knight";
        this.image = new Image("./knight.png");
    }


	@Override
	public boolean canMove(Tile tile) {
		Position pPos = this.getPosition();
		Position tPos = tile.getPosition();
		
		if (pPos.getX() == tPos.getX() + 1 || pPos.getX() == tPos.getX() - 1) {
			if (Math.abs(pPos.getY() - tPos.getY()) == 2 && !(tile.hasPiece() && tile.getPiece().getOwner() == this.getOwner())) {
				return true;
			}
		} else if (pPos.getX() == tPos.getX() + 2 || pPos.getX() == tPos.getX() - 2) {
			if (Math.abs(pPos.getY() - tPos.getY()) == 1 && !(tile.hasPiece() && tile.getPiece().getOwner() == this.getOwner())) {
				return true;
			}
		}
		return false;
	}
}
