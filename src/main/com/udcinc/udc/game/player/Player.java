package main.com.udcinc.udc.game.player;

import javafx.scene.paint.Color;
import main.com.udcinc.udc.game.timer.TurnTimer;
import main.com.udcinc.udc.settings.SerializableColor;

/**
 * Represents a player in the game
 * All games will only have two players, and game statistics will be saved 
 * per player.
 * 
 * @author Thomas Presicci
 */
public class Player extends PlayerStatistics {

	private static final long serialVersionUID = 3403203753803432579L;
	
	// Player's name
	private String name;
	// Color for the player's pieces
	private SerializableColor color = new SerializableColor(Color.DARKCYAN);
	// Boolean represents the side of the board the player starts on
	private boolean white = false;
	
	private TurnTimer timer;
	
	public Player(String name) {
		this.setName(name);
	}
	
	/**
	 * If the game has timers enabled, assign the player a timer
	 * @param seconds The amount of seconds on the timer, derived from GameSettings
	 */
	public void initializeTimer(int seconds) {
		if (seconds > 0) {
			this.timer = new TurnTimer(seconds);	
		}
	}

	
	// Getters/setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Color getColor() {
		return color.getColor();
	}
	
	public void setColor(Color color) {
		this.color = new SerializableColor(color);
	}

	public boolean isWhite() {
		return white;
	}

	public void setWhite(boolean white) {
		this.white = white;
	}

	public TurnTimer getTimer() {
		return timer;
	}

	public void setTimer(TurnTimer timer) {
		this.timer = timer;
	}
}
