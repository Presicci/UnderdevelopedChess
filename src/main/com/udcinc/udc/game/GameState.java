package main.com.udcinc.udc.game;

import main.com.udcinc.udc.game.board.Board;
import main.com.udcinc.udc.game.piece.Piece;

/**
 * Holds all information regarding the game state.
 * Defined at game start
 * 
 * @author Thomas Presicci
 */
public class GameState {
	private Board board;
	private GameSceneBuilder gsb;
	
	public GameState(GameSceneBuilder gsb) {
        this.board = new Board(GameSettings.getSize());
        this.gsb = gsb;
    }
	
	/**
	 * Adds a piece to the board visually
	 * 
	 * @param piece
	 */
	public void addPiece(Piece piece) {
		gsb.assignPieceToBoard(piece);
	}

    public Board getBoard() {
        return board;
    }
}
