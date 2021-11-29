package main.com.udcinc.udc.game.mode.challenge;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.com.udcinc.udc.game.GameRules;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.player.saving.SerializePlayer;
import main.com.udcinc.udc.game.scene.GameSceneController;
import main.com.udcinc.udc.game.state.GameState;
import main.com.udcinc.udc.settings.GameSettings;

/**
 * Builder for the game scene
 * Handles the transition to the game screen
 * Part of the CustomScreen
 * @author Alexander Regino
 */
public class ChallengeSceneBuilder {	

	public void build(Stage stage, GameSettings settings, GameRules rules, Player whitePlayer, Player blackPlayer, String challenge) throws IOException {
        // Active game scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/GameScreenOld.fxml"));
		
		// Initialize our game state
		GameState gs = new GameState(settings, rules);
	
		// Set player colors
		whitePlayer.setWhite(true);
		whitePlayer.setColor(settings.getWhiteColor());
		blackPlayer.setWhite(false);
		blackPlayer.setColor(settings.getBlackColor());
		
		// Overloads the default controller constructor
		//Also passes the challenge string, allowing enabling a change in the current CSV. 
		//The controller is overloaded, so additional buttons can be added without affecting the main game. 
		loader.setControllerFactory(ChallengeSceneController -> new ChallengeSceneController(gs, whitePlayer, blackPlayer, challenge));
	
		// Gets root pane for the scene
		Pane root = loader.load();
		
		// Register event filter to save players when window is closed
		stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, (event) -> {
			whitePlayer.incrementGamesPlayed();
			blackPlayer.incrementGamesPlayed();
			SerializePlayer.save(whitePlayer);
			SerializePlayer.save(blackPlayer);
		});
		
		// Transition scene to gamescreen
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
	}
}