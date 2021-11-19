package main.com.udcinc.udc.setup;

import java.io.File;
import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.com.udcinc.udc.game.GameRules;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.player.saving.DeserializePlayer;
import main.com.udcinc.udc.game.player.saving.SerializePlayer;
import main.com.udcinc.udc.game.scene.GameSceneBuilder;
import main.com.udcinc.udc.mainmenu.MainMenuController;
import main.com.udcinc.udc.settings.GameSettings;

/**
 * Controller for GameSetup.fxml
 * 
 * @author Thomas Presicci
 */
public class SetupController {

	private GameSettings settings;
	private GameRules rules;
	
	@FXML
	private ChoiceBox<String> playerOne, playerTwo;
	
	@FXML
	private AnchorPane newPlayerDialogue;
	
	@FXML
	private TextField playerNameEntry;
	
	/**
	 * Initialize is called after constructor
	 */
	@FXML
	private void initialize() {
		loadPlayerLists();
	}
	
	/**
	 * Populates the player selection lists with all saves from the playersaves folder
	 */
	private void loadPlayerLists() {
		// Clear lists
		playerOne.getItems().clear();
		playerTwo.getItems().clear();
		// Check if folder exists
		File saveFolder = new File("playersaves");
		if (!saveFolder.exists()) {
			return;
		}
		// Add players to lists
		File[] saveList = saveFolder.listFiles();
		for (int index = 0; index < saveList.length; index++) {
			String name = saveList[index].getName();
			if (name.contains(".ser")) {
				name = name.replace(".ser", "").replace("_", " ");
				playerOne.getItems().add(name);
				playerTwo.getItems().add(name);
			}
		}
	}
	
	/**
	 * Handler for the new player button
	 * @param event The event being triggered
	 */
	@FXML public void handleNewPlayer(Event event) {
		newPlayerDialogue.setVisible(true);
	}
	
	/**
	 * Handler for the create player button
	 * @param event The event being triggered
	 */
	@FXML public void handleCreatePlayer(Event event) {
		if (playerNameEntry.getText().isEmpty()) {
			return;
		}
		newPlayerDialogue.setVisible(false);
		Player player = new Player(playerNameEntry.getText());
		File saveFolder = new File("playersaves");
		// Create directory if it doesn't exist
		if (!saveFolder.exists()) {
			saveFolder.mkdir();
		}
		// Save player
		SerializePlayer.save(player);
		loadPlayerLists();
	}
	
	
	/**
	 * Handler for the start game button
	 * @param event The event being triggered
	 * @throws IOException exception thrown if GameScreen.fxml can not be loaded
	 */
	@FXML public void handleStartGame(Event event) throws IOException {
		if (playerOne.getValue() == null || playerTwo.getValue() == null) {
			return;
		}
		Player white = DeserializePlayer.load(playerOne.getValue().replace(" ", "_"));
		Player black = DeserializePlayer.load(playerTwo.getValue().replace(" ", "_"));
		if (white == null || black == null) {
			System.out.println("Error loading");
			return;
		}
		GameSceneBuilder gsb = new GameSceneBuilder();
		gsb.build((Stage) ((Node)event.getSource()).getScene().getWindow(), settings, rules, white, black);
	}
	
	/**
	 * Handler for the back button
	 * 
	 * @param event The event being triggered
	 * @throws IOException exception thrown if GameScreen.fxml can not be loaded
	 */
	@FXML
	public void handleBack(Event event) throws IOException {
		Stage stage = ((Stage) ((Node)event.getSource()).getScene().getWindow());
		// Active game scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/MainMenu.fxml"));

		// Gets root pane for the scene
		Pane root = loader.load();
		
		// Pass settings and rules along
        MainMenuController controller = loader.<MainMenuController>getController();
        controller.setRules(rules);
        controller.setSettings(settings);
        
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
