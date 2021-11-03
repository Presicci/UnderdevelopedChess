package main.com.udcinc.udc.game.state;

import main.com.udcinc.udc.game.GameSettings;
import main.com.udcinc.udc.game.board.Board;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.scene.GameSceneController;

/**
 * Holds all information regarding the game state.
 * Defined at game start
 * 
 * @author Thomas Presicci
 */
public class GameState {
	private final Board board;
	
	// The player whose turn it is
	private Player activePlayer;
	
	// Array size 2 container for all players in the game
	private Player[] players;
	
	public GameState() {
		this.players = new Player[2];
        this.board = new Board(GameSettings.getSize());
    }
	
	/**
	 * Called at game start, sets up the players
	 * @param whitePlayer The player that will be going first, bottom side of board
	 * @param blackPlayer The player that will be going second, top side of the board
	 */
	public void assignPlayers(Player whitePlayer, Player blackPlayer) {
		whitePlayer.setWhite(true);
		activePlayer = whitePlayer;
		players[0] = whitePlayer;
		players[1] = blackPlayer;
	}
	
	/**
	 * Switches the active player
	 */
	public void nextTurn() {
		if (activePlayer == players[0]) {
			activePlayer = players[1];
		} else {
			activePlayer = players[0];
		}
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public Player getWhitePlayer() {
		return players[0];
	}
	
	public Player getBlackPlayer() {
		return players[1];
	}

    public Board getBoard() {
        return board;
    }
    
    public Player getActivePlayer() {
    	return activePlayer;
    }
}
