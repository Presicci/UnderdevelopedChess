package main.com.udcinc.udc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/MainMenu.fxml"));
		Pane root = loader.load();
		
        // Setup the window
        primaryStage.setTitle("Underdeveloped Chess");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
    }	
}
