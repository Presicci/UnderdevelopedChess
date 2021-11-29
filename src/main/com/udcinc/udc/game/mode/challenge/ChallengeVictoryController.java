package main.com.udcinc.udc.game.mode.challenge;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.com.udcinc.udc.game.GameRules;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.mainmenu.MainMenuController;
import main.com.udcinc.udc.settings.GameSettings;

public class ChallengeVictoryController {

    @FXML
    private Text resultText;

    private int moveNum;
    @FXML
    private Text userMovesText;
	
	private GameRules rules;
	private GameSettings settings;
	
    @FXML
    private Button backVictory;


	public void setRules(GameRules rules) {
		// TODO Auto-generated method stub
		this.rules = rules;
	}
	//Eject to the player to the main menu upon completion and clicking the button
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


	public void setSettings(GameSettings settings) {
		// TODO Auto-generated method stub
		this.settings = settings;
	}
	public void setMoveNum(int givenMoveNum)
	{
		this.moveNum = givenMoveNum;
		userMovesText.setText("" + moveNum);
	}
    
	

}