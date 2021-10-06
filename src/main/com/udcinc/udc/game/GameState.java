package main.com.udcinc.udc.game;

import main.com.udcinc.udc.game.board.Board;

public class GameState {
	private Board board;
	
	public void init() {
        board = new Board();
        board.init(GameSettings.getSize());
        GameStatics.setGameState(null);
    }

    public Board getBoard() {
        return board;
    }
}
