package main.com.udcinc.udc.game.player;

import java.io.Serializable;

public abstract class PlayerStatistics implements Serializable {

	private static final long serialVersionUID = 4580327507020894250L;

	// Winrate
	private int gamesPlayed, gamesCompleted, gamesWon;
	
	// Pieces captured
	private int pawnsCaptured, knightsCaptured, rooksCaptured, bishopsCaptured, queensCaptured;
	
	// Captures made with pieces
	private int pawnCaptures, knightCaptures, rookCaptures, bishopCaptures, queenCaptures, kingCaptures;
	
	// Getters / Setters
	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}
	
	public int getGamesCompleted() {
		return gamesCompleted;
	}

	public void setGamesCompleted(int gamesCompleted) {
		this.gamesCompleted = gamesCompleted;
	}
	
	public int incrementGamesCompleted() {
		return ++gamesCompleted;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}
	
	public double getWinRation() {
		return gamesCompleted / gamesWon;
	}

	public int getPawnsCaptured() {
		return pawnsCaptured;
	}

	public void setPawnsCaptured(int pawnsCaptured) {
		this.pawnsCaptured = pawnsCaptured;
	}

	public int getKnightsCaptured() {
		return knightsCaptured;
	}

	public void setKnightsCaptured(int knightsCaptured) {
		this.knightsCaptured = knightsCaptured;
	}

	public int getRooksCaptured() {
		return rooksCaptured;
	}

	public void setRooksCaptured(int rooksCaptured) {
		this.rooksCaptured = rooksCaptured;
	}

	public int getBishopsCaptured() {
		return bishopsCaptured;
	}

	public void setBishopsCaptured(int bishopsCaptured) {
		this.bishopsCaptured = bishopsCaptured;
	}

	public int getQueensCaptured() {
		return queensCaptured;
	}

	public void setQueensCaptured(int queensCaptured) {
		this.queensCaptured = queensCaptured;
	}
	
	
}
