package main.com.udcinc.udc.game.piece.impl;

import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;

public class Rook extends Piece {
    public Rook(Tile tile) {
        this.name = "Rook";
        this.isAlive = true;
        this.position = tile.getPosition();
        this.tile = tile;
        tile.setPiece(this);
    }
}
