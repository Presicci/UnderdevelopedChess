package main.com.udcinc.udc.game;

import main.com.udcinc.udc.game.board.Board;
import main.com.udcinc.udc.game.settings.Settings;

public class GameState {
	private Board board;
	
	public void init() {
        board = new Board();
        board.init(8);
        GameStatics.setGameState(this);
    }

    public Board getBoard() {
        return board;
    }
}
