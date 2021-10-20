package main.com.udcinc.udc.game.state;

import main.com.udcinc.udc.game.GameSettings;
import main.com.udcinc.udc.game.board.Board;
import main.com.udcinc.udc.game.scene.GameSceneController;

/**
 * Holds all information regarding the game state.
 * Defined at game start
 * 
 * @author Thomas Presicci
 */
public class GameState {
	private Board board;
	
	public GameState() {
        this.board = new Board(GameSettings.getSize());
    }

    public Board getBoard() {
        return board;
    }
}
