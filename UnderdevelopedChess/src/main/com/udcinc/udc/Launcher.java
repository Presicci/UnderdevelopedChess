package main.com.udcinc.udc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.com.udcinc.udc.game.GameRules;
import main.com.udcinc.udc.mainmenu.MainMenuController;
import main.com.udcinc.udc.settings.GameSettings;
import main.com.udcinc.udc.settings.saving.DeserializeSettings;

/**
 * Application launcher
 * 
 * @author Thomas Presicci
 */
public class Launcher extends Application {

	/**
	 * Starts the main menu through MainMenu.fxml
	 */
	@Override public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/MainMenu.fxml"));
		Pane root = loader.load();
		
		GameSettings settings = DeserializeSettings.load();
		GameRules rules = new GameRules();
		
		// Pass settings and rules along
        MainMenuController controller = loader.<MainMenuController>getController();
        controller.setRules(rules);
        controller.setSettings(settings);
		
        // Setup the window
        primaryStage.setTitle("Underdeveloped Chess");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
    }	
}
