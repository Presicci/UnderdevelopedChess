package main.com.udcinc.udc.game;

import main.com.udcinc.udc.game.board.Board;
import main.com.udcinc.udc.game.board.Position;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.player.Player;

public class GameState {
	private Board board;
	
	public GameState() {
        board = new Board(GameSettings.getSize());
    }

    public Board getBoard() {
        return board;
    }
}
