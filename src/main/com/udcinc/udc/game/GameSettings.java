package main.com.udcinc.udc.game;

/**
 * Static for now for the case of testing,
 * will eventually be serializable and passed into the 
 * game state at game creation.
 * 
 * @author Thomas Presicci
 */
public class GameSettings {
    private int size = 8;

    public int getSize() {
        return size;
    }
}
