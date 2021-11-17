package main.com.udcinc.udc.game.scene;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.com.udcinc.udc.game.GameRules;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.state.GameState;
import main.com.udcinc.udc.settings.GameSettings;

/**
 * Builder for the game scene
 * Handles the transition to the game screen
 * 
 * @author Thomas Presicci
 */
public class GameSceneBuilder {	

	public void build(Stage stage, GameSettings settings, GameRules rules) throws IOException {
        // Active game scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/GameScreen.fxml"));
		
		// Initialize our game state
		GameState gs = new GameState(settings, rules);
		
		// Test players
		Player whitePlayer = new Player("White");
		Player blackPlayer = new Player("Black");
		blackPlayer.setColor(settings.getBlackColor());
		whitePlayer.setColor(settings.getWhiteColor());
		
		// Overloads the default controller constructor
		loader.setControllerFactory(GameSceneController -> new GameSceneController(gs, whitePlayer, blackPlayer));
		
		// Gets root pane for the scene
		Pane root = loader.load();
		
		// Transition scene to gamescreen
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
	}	
}
