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
	private GameSceneController controller;
	
	public GameState(GameSceneController controller) {
        this.board = new Board(GameSettings.getSize(), controller);
        this.controller = controller;
    }

    public Board getBoard() {
        return board;
    }
    
    public GameSceneController getController() {
    	return controller;
    }
}
