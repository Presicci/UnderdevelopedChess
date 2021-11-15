package main.com.udcinc.udc.mainmenu;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.com.udcinc.udc.game.scene.GameSceneBuilder;

/**
 * Controller for MainMenu.fxml
 * Literally just buttons
 * 
 * @author Thomas Presicci
 */
public class MainMenuController {
	
	/**
	 * Handler for the play button
	 * @param event The event being triggered
	 * @throws IOException exception thrown if GameScreen.fxml can not be loaded
	 */
	@FXML public void handlePlay(Event event) throws IOException {
		GameSceneBuilder gsb = new GameSceneBuilder();
		gsb.build((Stage) ((Node)event.getSource()).getScene().getWindow());
	}
	
	/**
	 * Handler for the settings button
	 * 
	 * @param event The event being triggered
	 * @throws IOException exception thrown if GameScreen.fxml can not be loaded
	 */
	@FXML
	public void handleSettings(Event event) throws IOException {
		Stage stage = ((Stage) ((Node)event.getSource()).getScene().getWindow());
		// Active game scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/Settings.fxml"));

		// Gets root pane for the scene
		Pane root = loader.load();

		// Transition scene to gamescreen
		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.show();
	}
}
