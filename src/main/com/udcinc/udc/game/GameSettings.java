package main.com.udcinc.udc.game;

/**
 * Static for now for the case of testing,
 * will eventually be serializable and passed into the 
 * game state at game creation.
 * 
 * @author Thomas Presicci
 */
public class GameSettings {
	/*
	 * Board is created and scaled based on the size
	 * defaults to 8
	 */
    private int size = 8;
    
    /*
     * If possible moves for a piece should be displayed to the player
     * defaults to true 
     */
    private boolean enableMoveHighlighting = true;
    
    
    
    // Getters/setters
    public int getSize() {
        return size;
    }
    
    public boolean isMoveHightlighting() {
    	return enableMoveHighlighting;
    }
}
