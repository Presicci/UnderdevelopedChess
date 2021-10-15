package main.com.udcinc.udc.game.piece;

import javafx.scene.image.Image;
import main.com.udcinc.udc.game.GameState;
import main.com.udcinc.udc.game.board.Position;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Piece.java
 * Object template for the various chess pieces.
 * 
 * @author Thomas Presicci
 */
public abstract class Piece {
    //  Identity related variables assigned in subclass constructors
    protected String name;
    protected Image image;

    //  State based variables
    protected boolean isAlive;
    protected Position position;
    protected Tile tile;
    protected Player owner;
    
    // GameState reference
    protected GameState gs;

    //	Constructor
    public Piece(Player player, Tile tile, GameState gs) {
        this.owner = player;
    	this.isAlive = true;
        this.position = tile.getPosition();
        this.tile = tile;
        this.gs = gs;
        tile.setPiece(this);
    }
    
    /**
     * When a piece is taken, remove its alive status and sets its 
     * position out of play.
     */
    protected void doKill() {
        this.isAlive = false;
        this.position = new Position(-1, -1);
    }

    protected boolean canMove(Tile tile) {  // Tile tile
        return false;
    }

    /**
     * Default method used to iterate over all tiles on the board,
     * determining if those are valid moves for this piece.
     * 
     * @return A list containing all posssible moves for the piece
     */
    public List<Tile> getAllValidMoves() {
        List<Tile> possibleTiles = new ArrayList<>();
        for (Tile[] row : gs.getBoard().getTiles()) {
            for (Tile tile : row) {
                if (canMove(tile)) {
                    possibleTiles.add(tile);
                }
            }
        }
        return possibleTiles;
    }

    //	Getters/Setters
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

    public Player getOwner() {
        return owner;
    }
}