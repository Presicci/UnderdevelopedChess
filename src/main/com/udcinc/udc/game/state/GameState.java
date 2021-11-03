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
	
	public void assignPlayers(Player whitePlayer, Player blackPlayer) {
		whitePlayer.setWhite(true);
		activePlayer = whitePlayer;
		players[0] = whitePlayer;
		players[1] = blackPlayer;
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
}
