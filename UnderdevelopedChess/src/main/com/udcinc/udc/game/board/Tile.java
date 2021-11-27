package main.com.udcinc.udc.game.board;

import main.com.udcinc.udc.game.piece.Piece;

/**
 * Tile object which represents a single tile on the board.
 * Held in the Board container.
 * 
 * @author Thomas Presicci
 */
public class Tile {
    private final Position position;
    private Piece piece = null;

    public Tile(int x, int y) {
        this.position = new Position(x, y);
    }

    public Tile(Position position) {
        this.position = position;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Position getPosition() {
        return position;
    }
}