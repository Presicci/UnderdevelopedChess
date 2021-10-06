package main.com.udcinc.udc.game;

public class GameStatics {
	
	private static GameState gameState;

	public static GameState getGameState() {
		return gameState;
	}

	public static void setGameState(GameState gameState) {
		GameStatics.gameState = gameState;
	}

}
