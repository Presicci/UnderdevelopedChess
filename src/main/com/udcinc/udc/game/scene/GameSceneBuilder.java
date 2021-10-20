package main.com.udcinc.udc.game.scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.com.udcinc.udc.game.state.GameState;

/**
 * Builder for the game scene
 * Creates a dynamically sized board based on the size specified in the game settings
 * Lots of test lines in here atm
 * 
 * @author Thomas Presicci
 */
public class GameSceneBuilder extends Application {	

	@Override
	public void start(Stage primaryStage) throws Exception {
        // Active game scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("gameScreen.fxml"));
		
		// Initialize our game state
		GameState gs = new GameState();
		
		// Overloads the default controller constructor
		loader.setControllerFactory(GameSceneController -> new GameSceneController(gs));
		
		// Gets root pane for the scene
		Pane root = loader.load();

        // Setup the window
        primaryStage.setTitle("Underdeveloped Chess");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
	}
	
	public static void main(String[] args) {
        // Currently just for testing we launch straight into the chess board
		launch(args);
    }	
}
