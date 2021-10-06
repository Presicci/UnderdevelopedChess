package main.com.udcinc.udc.game.piece.impl;

import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;

public class Rook extends Piece {
    public Rook(Tile tile) {
    	super(tile);
        this.name = "Rook";
        tile.setPiece(this);
    }
}
