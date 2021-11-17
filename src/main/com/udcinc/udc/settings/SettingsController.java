package main.com.udcinc.udc.settings;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.com.udcinc.udc.game.GameRules;
import main.com.udcinc.udc.mainmenu.MainMenuController;
import main.com.udcinc.udc.settings.saving.SerializeSettings;

/**
 * Controller for Settings.fxml
 * 
 * @author Thomas Presicci
 */
public class SettingsController {
	
	@FXML private ColorPicker whiteColor, blackColor, boardBrown, boardBeige;
	@FXML private CheckBox moveHighlighting, castling, promoting, passant;
	@FXML private TextField timer, turns;
	
	private GameSettings settings = null;
	private GameRules rules = null;
	
	@FXML
	private void initialize() {
		// Listeners to restrict text field entry to only numeric values
		turns.textProperty().addListener((ob, oldV, newV) -> {
			if (!newV.matches("^[0-9]+$")) {
				turns.setText(oldV);
			}
		});
		timer.textProperty().addListener((ob, oldV, newV) -> {
			if (!newV.matches("^[0-9]+$")) {
				timer.setText(oldV);
			}
		});
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
		
		// Save all values
		settings.setBlackColor(blackColor.getValue());
		settings.setWhiteColor(whiteColor.getValue());
		settings.setBoardBrown(boardBrown.getValue());
		settings.setBoardBeige(boardBeige.getValue());
		settings.setMoveHighlighting(moveHighlighting.isSelected());
		rules.setEnPassant(passant.isSelected());
		rules.setCastling(castling.isSelected());
		rules.setPawnPromotion(promoting.isSelected());
		rules.setNumberOfTurns(Integer.parseInt(turns.getText()));
		rules.setTimerLimit(Integer.parseInt(timer.getText()));
		
		// Pass settings and rules along
        MainMenuController controller = loader.<MainMenuController>getController();
        controller.setRules(rules);
        controller.setSettings(settings);
		
        // Save settings
        SerializeSettings.save(settings);
        
		// Transition scene to gamescreen
		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.show();
	}
	
	// Setters called after initialization
	public void setSettings(GameSettings settings) {
		this.settings = settings;
		blackColor.setValue(settings.getBlackColor());
		whiteColor.setValue(settings.getWhiteColor());
		boardBrown.setValue(settings.getBoardBrown());
		boardBeige.setValue(settings.getBoardBeige());
		moveHighlighting.setSelected(settings.isMoveHightlighting());
	}
	
	public void setRules(GameRules rules) {
		this.rules = rules;
		castling.setSelected(rules.isCastlingAllowed());
		passant.setSelected(rules.isEnPassantAllowed());
		promoting.setSelected(rules.isPawnPromotionActive());
		timer.setText("" + rules.getTimerLimit());
		turns.setText("" + rules.getNumberOfTurns());
	}
}
