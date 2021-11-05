package main.com.udcinc.udc.mainmenu;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
}
