package main.com.udcinc.udc.game;

/**
 * Default game rules object
 * Contains all data relating to how the game will work
 * 
 * @author Thomas Presicci
 */
public class GameRules {	
	/**
	 * Stalemate: 
	 * If player cant move, but isnt in check game ends in a draw	
	 */
	
	
	
	/*
	 * Number of turns a player can take in succession
	 * 
	 * defaults to 1
	 */
	private int numberOfTurns = 1;
	
	/*
	 * Time a player has to take their turns
	 * Time is in seconds
	 * set to 0 to disable timers
	 * 
	 * defaults to 0
	 */
	private int timerLimit = 0;
	
	/*
	 * Whether castling is allowed or not
	 * set to false to disable castling
	 * 
	 * defaults to true
	 */
	private boolean castling = true;
	
	/*
	 * Whether pawns promote when they reach the other end of the board
	 * set to false to disable promotions
	 * 
	 * defaults to true
	 */
	private boolean pawnPromotion = true;
	
	/*
	 * Whether pawns can capture horizontally adjacent pawns in the center of the board
	 * set to false to disable en passant
	 * 
	 * defaults to true
	 */
	private boolean enPassant = true;
	
	
	
	
	
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

	public boolean isCastlingAllowed() {
		return castling;
	}

	public void setCastling(boolean castling) {
		this.castling = castling;
	}

	public boolean isPawnPromotionActive() {
		return pawnPromotion;
	}

	public void setPawnPromotion(boolean pawnPromotion) {
		this.pawnPromotion = pawnPromotion;
	}

	public boolean isEnPassantAllowed() {
		return enPassant;
	}

	public void setEnPassant(boolean enPassant) {
		this.enPassant = enPassant;
	}
	
	
}
