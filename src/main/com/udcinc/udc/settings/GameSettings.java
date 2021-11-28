package main.com.udcinc.udc.settings;

import java.io.Serializable;

import javafx.scene.paint.Color;

/**
 * Static for now for the case of testing,
 * will eventually be serializable and passed into the 
 * game state at game creation.
 * 
 * @author Thomas Presicci
 */
public class GameSettings implements Serializable {
	
	private static final long serialVersionUID = -1099610309216855140L;

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
    
    /*
     * The color of white's pieces
     * defaults to WHITE, #FFFFFF
     */
    private SerializableColor whiteColor = new SerializableColor(Color.WHITE);
    
    /*
     * The color of black's pieces
     * defaults to BLACK, #000000
     */
    private SerializableColor blackColor = new SerializableColor(Color.BLACK);
    
    /*
     * The color of the brown tiles on the board
     * defaults to BROWN, #A52A2A
     */
    private SerializableColor boardBrown = new SerializableColor(Color.BROWN);
    
    /*
     * The color of the beige tiles on the board
     * defaults to BEIGE, #F5F5DC
     */
    private SerializableColor boardBeige = new SerializableColor(Color.BEIGE);

    /*
     * The color of the move highlighting on the board
     * defaults to LIMEGREEN, #32CD32
     */
    private SerializableColor moveHighlightingColor = new SerializableColor(Color.LIMEGREEN);

    private String gameType;
    
    
    // Getters/setters
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isMoveHightlighting() {
		return enableMoveHighlighting;
	}

	public void setMoveHighlighting(boolean enableMoveHighlighting) {
		this.enableMoveHighlighting = enableMoveHighlighting;
	}

	public Color getWhiteColor() {
		return whiteColor.getColor();
	}

	public void setWhiteColor(Color whiteColor) {
		this.whiteColor = new SerializableColor(whiteColor);
	}

	public Color getBlackColor() {
		return blackColor.getColor();
	}

	public void setBlackColor(Color blackColor) {
		this.blackColor = new SerializableColor(blackColor);
	}

	public Color getBoardBrown() {
		return boardBrown.getColor();
	}

	public void setBoardBrown(Color boardBrown) {
		this.boardBrown = new SerializableColor(boardBrown);
	}

	public Color getBoardBeige() {
		return boardBeige.getColor();
	}

	public void setBoardBeige(Color boardBeige) {
		this.boardBeige = new SerializableColor(boardBeige);
	}

	public Color getMoveHighlightingColor() {
		return moveHighlightingColor.getColor();
	}

	public void setMoveHighlightingColor(Color moveHighlightingColor) {
		this.moveHighlightingColor = new SerializableColor(moveHighlightingColor);
	}
	
	public String getGameType() {
		return gameType;
	}
	
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
}
