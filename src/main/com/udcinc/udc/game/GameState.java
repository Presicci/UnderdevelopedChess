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
	private GameSceneController controller;
	
	public GameState(GameSceneController controller) {
        this.board = new Board(GameSettings.getSize());
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

    public Board getBoard() {
        return board;
    }
}
