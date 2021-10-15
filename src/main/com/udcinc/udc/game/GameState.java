package main.com.udcinc.udc.game;

import main.com.udcinc.udc.game.board.Board;

/**
 * Holds all information regarding the game state.
 * Defined at game start
 * 
 * @author Thomas Presicci
 */
public class GameState {
	private Board board;
	
	public GameState() {
        board = new Board(GameSettings.getSize());
    }

    public Board getBoard() {
        return board;
    }
}
