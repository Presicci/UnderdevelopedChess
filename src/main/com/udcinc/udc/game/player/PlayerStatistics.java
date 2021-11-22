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
	
	public int incrementGamesPlayed() {
		return ++gamesPlayed;
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
	
	public int incrementGamesWon() {
		return ++gamesWon;
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
	
	public int incrementPawnsCaptured() {
		return ++pawnsCaptured;
	}

	public int getKnightsCaptured() {
		return knightsCaptured;
	}
	
	public int incrementKnightsCaptured() {
		return ++knightsCaptured;
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
	
	public int incrementRooksCaptured() {
		return ++rooksCaptured;
	}

	public int getBishopsCaptured() {
		return bishopsCaptured;
	}

	public void setBishopsCaptured(int bishopsCaptured) {
		this.bishopsCaptured = bishopsCaptured;
	}
	
	public int incrementBishopsCaptured() {
		return ++bishopsCaptured;
	}

	public int getQueensCaptured() {
		return queensCaptured;
	}

	public void setQueensCaptured(int queensCaptured) {
		this.queensCaptured = queensCaptured;
	}
	
	public int incrementQueensCaptured() {
		return ++queensCaptured;
	}

	public int getPawnCaptures() {
		return pawnCaptures;
	}

	public void setPawnCaptures(int pawnCaptures) {
		this.pawnCaptures = pawnCaptures;
	}
	
	public int incrementPawnCaptures() {
		return ++pawnCaptures;
	}

	public int getKnightCaptures() {
		return knightCaptures;
	}

	public void setKnightCaptures(int knightCaptures) {
		this.knightCaptures = knightCaptures;
	}
	
	public int incrementKnightCaptures() {
		return ++knightCaptures;
	}

	public int getRookCaptures() {
		return rookCaptures;
	}

	public void setRookCaptures(int rookCaptures) {
		this.rookCaptures = rookCaptures;
	}
	
	public int incrementRookCaptures() {
		return ++rookCaptures;
	}

	public int getBishopCaptures() {
		return bishopCaptures;
	}

	public void setBishopCaptures(int bishopCaptures) {
		this.bishopCaptures = bishopCaptures;
	}
	
	public int incrementBishopCaptures() {
		return ++bishopCaptures;
	}

	public int getQueenCaptures() {
		return queenCaptures;
	}

	public void setQueenCaptures(int queenCaptures) {
		this.queenCaptures = queenCaptures;
	}
	
	public int incrementQueenCaptures() {
		return ++queenCaptures;
	}

	public int getKingCaptures() {
		return kingCaptures;
	}

	public void setKingCaptures(int kingCaptures) {
		this.kingCaptures = kingCaptures;
	}
	
	public int incrementKingCaptures() {
		return ++kingCaptures;
	}
}
