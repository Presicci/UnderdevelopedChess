package main.com.udcinc.udc.game.player;

/**
 * Represents a player in the game
 * All games will only have two players, and game statistics will be saved 
 * per player.
 * 
 * @author Thomas Presicci
 */
public class Player {
	private String name;
	
	public Player(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
