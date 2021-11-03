package main.com.udcinc.udc.game.scene;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.state.GameState;

/**
 * Builder for the game scene
 * Handles the transition to the game screen
 * 
 * @author Thomas Presicci
 */
public class GameSceneBuilder {	

	public void build(Stage stage) throws IOException {
        // Active game scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/GameScreen.fxml"));
		
		// Initialize our game state
		GameState gs = new GameState();
		
		// Test players
		Player whitePlayer = new Player("White");
		Player blackPlayer = new Player("Black");
		
		// Overloads the default controller constructor
		loader.setControllerFactory(GameSceneController -> new GameSceneController(gs, whitePlayer, blackPlayer));
		
		// Gets root pane for the scene
		Pane root = loader.load();
		
		// Transition scene to gamescreen
        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();
	}	
}
