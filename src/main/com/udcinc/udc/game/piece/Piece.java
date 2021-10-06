package main.com.udcinc.udc.game.piece;

import javafx.scene.image.Image;
import main.com.udcinc.udc.game.board.Position;
import main.com.udcinc.udc.game.board.Tile;

import java.util.List;

public abstract class Piece {
    //  Default constructed finals based on piece constructor
    protected String name;
    protected Image image;

    //  GameState vars
    protected boolean isAlive;
    protected Position position;
    protected Tile tile;
    protected String owner;   // TODO probably make owner obj

    //  Methods
    protected void doKill() {
        this.isAlive = false;
        this.position = new Position(-1, -1);
    }

    protected boolean canMove(Tile tile) {
        return false;
    }

    protected List<Tile> getAllValidMoves() {
        return null;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Position getPosition() {
        return position;
    }

    public Tile getTile() {
        return tile;
    }

    public String getOwner() {
        return owner;
    }
}