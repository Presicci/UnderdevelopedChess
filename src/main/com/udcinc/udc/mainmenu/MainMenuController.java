package main.com.udcinc.udc.mainmenu;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.com.udcinc.udc.game.GameRules;
import main.com.udcinc.udc.settings.GameSettings;
import main.com.udcinc.udc.settings.SettingsController;
import main.com.udcinc.udc.setup.SetupController;

/**
 * Controller for MainMenu.fxml
 * Literally just buttons
 * 
 * @author Thomas Presicci
 */
public class MainMenuController {
	
	private GameRules rules;
	private GameSettings settings;
	
	/**
	 * Handler for the play button
	 * @param event The event being triggered
	 * @throws IOException exception thrown if GameScreen.fxml can not be loaded
	 */
	@FXML public void handlePlay(Event event) throws IOException {
		Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
		// Active game scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/GameSetup.fxml"));

		// Gets root pane for the scene
		Pane root = loader.load();

		// Pass settings and rules along
		SetupController controller = loader.<SetupController>getController();
		controller.setSettings(settings);
		controller.setRules(rules);
		
		// Transition scene to gamescreen
		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.show();
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

		// Pass settings and rules along
        SettingsController controller = loader.<SettingsController>getController();
        controller.setSettings(settings);
        controller.setRules(rules);
		
		// Transition scene to gamescreen
		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.show();
	}
	
	public void setSettings(GameSettings settings) {
		this.settings = settings;
	}
	
	public void setRules(GameRules rules) {
		this.rules = rules;
	}
}
