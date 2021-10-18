package main.com.udcinc.udc.game.board;

import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.scene.GameSceneController;

/**
 * Container which holds a collection of Tiles.
 * Layout of the board in terms of coordinates:
 * [ (0, 0), (1, 0), (2, 0), ... (x, 0) ]
 * [ (0, 1), (1, 1), (2, 1)
 * [ (0, 2), (1, 2)
 * [  ...
 * [ (0, y)
 *
 * @author Thomas Presicci
 */
public class Board {
    private Tile[][] tiles;
    private GameSceneController controller;
    
    public Board(int size, GameSceneController controller) {
        tiles = new Tile[size][size];
        this.controller = controller;
    }
	
	/**
	 * Adds a piece to the board visually
	 * 
	 * @param piece
	 */
	public void addPiece(Piece piece) {
		controller.assignPieceToBoard(piece);
	}
	
	public void killPiece(Piece piece) {
		piece.doKill();
		tiles[piece.getPosition().getX()][piece.getPosition().getY()].setPiece(null);
	}
	
	public void movePiece(Piece piece, Position nextPos) {
		int x = nextPos.getX();
		int y = nextPos.getY();
		
		piece.getTile().setPiece(null);
		controller.removePieceFromBoard(piece);
		
		piece.move(nextPos);
		piece.setTile(tiles[x][y]);
		tiles[x][y].setPiece(piece);
		
		controller.assignPieceToBoard(piece);
	}

    public void assignTile(Tile tile, int x, int y) {
        tiles[x][y] = tile;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}