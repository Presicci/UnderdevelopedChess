package main.com.udcinc.udc.game;

/**
 * Default game rules object
 * Contains all data relating to how the game will work
 * 
 * @author Thomas Presicci
 */
public class GameRules {
	/*
	 * Number of turns a player can take in succession
	 * 
	 * defaults to 1
	 */
	private int numberOfTurns = 1;
	
	/*
	 * Time a player has to take their turns
	 * TODO maybe implement the full chess timing rules idk yet
	 * set to 0 to disable timers
	 * 
	 * defaults to 0
	 */
	private int timerLimit = 0;
	
	// Getters/setters
	public int getTimerLimit() {
		return timerLimit;
	}

	public void setTimerLimit(int timerLimit) {
		this.timerLimit = timerLimit;
	}

	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = numberOfTurns;
	}
}
