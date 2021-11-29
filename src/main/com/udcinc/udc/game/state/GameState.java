package main.com.udcinc.udc.game.state;

import main.com.udcinc.udc.game.GameRules;
import main.com.udcinc.udc.game.board.Board;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.settings.GameSettings;

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
	
	private GameSettings settings;
	
	private GameRules rules;
	
	private int turnsTaken;
	
	public GameState(GameSettings settings, GameRules rules) {
		this.settings = settings;
		this.rules = rules;
		this.players = new Player[2];
        this.board = new Board(settings.getSize(), rules);
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
		
		whitePlayer.initializeTimer(rules.getTimerLimit());
		blackPlayer.initializeTimer(rules.getTimerLimit());

		if (rules.getTimerLimit() > 0) {
			activePlayer.getTimer().setRunning(true);
		}
	}
	
	/**
	 * Switches the active player
	 */
	public void nextTurn() {
		if (rules.getTimerLimit() > 0) {
			activePlayer.getTimer().setRunning(false);	
		}
		if (activePlayer == players[0]) {
			activePlayer = players[1];
		} else {
			activePlayer = players[0];
		}

		if (rules.getTimerLimit() > 0) {
			activePlayer.getTimer().setRunning(true);
		}
	}
	
	
	// Getters/setters
	public GameSettings getSettings() {
		return settings;
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

	public GameRules getRules() {
		return rules;
	}
    
	public int getTurnsTaken()
	{
		return turnsTaken;
	}
	
	public void setTurnsTaken(int turnUpdate)
	{
		this.turnsTaken = turnUpdate;
	}
    
}
