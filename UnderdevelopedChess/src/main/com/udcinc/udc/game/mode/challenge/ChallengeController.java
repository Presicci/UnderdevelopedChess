package main.com.udcinc.udc.game.mode.challenge;

import java.io.IOException;
import main.com.udcinc.udc.game.scene.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.com.udcinc.udc.game.GameRules;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.player.saving.DeserializePlayer;
import main.com.udcinc.udc.game.scene.GameSceneBuilder;
import main.com.udcinc.udc.mainmenu.MainMenuController;
import main.com.udcinc.udc.settings.GameSettings;


/**
 * Controller for CustomScreen.fxml
 * 
 * @author Alexander Regino
 */
public class ChallengeController {

	/**
	 * Handler for the start game button
	 * @param event The event being triggered
	 * @throws IOException exception thrown if GameScreen.fxml can not be loaded
	 */

	
	private GameRules rules;
	private GameSettings settings;
	public void setSettings(GameSettings settings) {
		this.settings = settings;
	}
	
	public void setRules(GameRules rules) {
		this.rules = rules;
	}
	
	

    @FXML
    private Button back_Button;

    @FXML
    private Button challenge_1;

    @FXML
    private Button challenge_2;

    @FXML
    private Button challenge_3;

    @FXML
    void handleBack(ActionEvent event) throws IOException {
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

    @FXML
    void handle_challenge_1(ActionEvent event) throws IOException {
		Player Human = new Player("Human");
		Player CPU = new Player("CPU");
		ChallengeSceneBuilder csb = new ChallengeSceneBuilder();
		//Set the passed Challenge1 CSV
		String Challenge = "Challenge1";
		rules.setNumberOfTurns(3);
		csb.build((Stage) ((Node)event.getSource()).getScene().getWindow(), settings, rules, Human, CPU, Challenge);

    }
}


