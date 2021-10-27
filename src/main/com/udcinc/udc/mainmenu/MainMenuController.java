package main.com.udcinc.udc.mainmenu;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import main.com.udcinc.udc.game.scene.GameSceneBuilder;

public class MainMenuController {
	
	@FXML
	public void handlePlay(Event event) throws IOException {
		GameSceneBuilder gsb = new GameSceneBuilder();
		gsb.build((Stage) ((Node)event.getSource()).getScene().getWindow());
	}
}
