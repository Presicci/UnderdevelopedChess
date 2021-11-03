package main.com.udcinc.udc.game.player;

import javafx.scene.paint.Color;
import main.com.udcinc.udc.game.timer.TurnTimer;

/**
 * Represents a player in the game
 * All games will only have two players, and game statistics will be saved 
 * per player.
 * 
 * @author Thomas Presicci
 */
public class Player {
	private String name;
	private Color color = Color.DARKCYAN;
	
	private TurnTimer timer;
	
	// Boolean represents the side of the board the player starts on
	private boolean white = false;
	
	public Player(String name) {
		this.setName(name);
	}
	
	public void initializeTimer(int seconds) {
		if (seconds > 0) {
			this.timer = new TurnTimer(seconds);	
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
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
