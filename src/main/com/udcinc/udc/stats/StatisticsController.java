package main.com.udcinc.udc.stats;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.com.udcinc.udc.game.GameRules;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.player.saving.DeserializePlayer;
import main.com.udcinc.udc.mainmenu.MainMenuController;
import main.com.udcinc.udc.settings.GameSettings;
import main.com.udcinc.udc.settings.saving.SerializeSettings;

public class StatisticsController {

	private GameSettings settings;
	private GameRules rules;
	
	private ArrayList<Player> playerList = new ArrayList<Player>();
	
	@FXML
    private Text statsText;
	
    @FXML
    private PieChart playerPie; //playerPieData is a list that will populate playerPie (FXML object)
    private ObservableList<PieChart.Data> playerPieData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
    	//gets a list of saved players
    	loadPlayerLists();
    	
    	//for each player, adds a pieChart data object to playerPieData, then sets populated list to playerPie
    	for(int i = 0; i < playerList.size(); i++) {
    		playerPieData.add(new PieChart.Data(playerList.get(i).getName(), playerList.get(i).getGamesPlayed()));
    	}
    	playerPie.setData(playerPieData);
    	
    	//for each data element in the pie chart, adds a mouse pressed event that prints the player's stats
    	for (final PieChart.Data data : playerPie.getData()) {
    		data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					DeserializePlayer playerLoader = new DeserializePlayer();
					//dp = display player
					Player dp = playerLoader.load(data.getName());
					
					statsText.setText(dp.getName() + "\n\n" +
										"Games Played:\t\t" + dp.getGamesPlayed() + "\n" +
										"Games Completed:\t" + dp.getGamesCompleted() + "\n" + 
										"Games Won:\t\t" + dp.getGamesWon() + "\n\n" + 
										"Pawns Captured:\t" + dp.getPawnsCaptured() + "\n" +
										"Knights Captured:\t" + dp.getKnightsCaptured() + "\n" +
										"Rooks Captured:\t" + dp.getRooksCaptured() + "\n" +
										"Bishops Captured:\t" + dp.getBishopsCaptured() + "\n" +
										"Queens Captured:\t" + dp.getQueensCaptured() + "\n\n" + 
										"Pawn Captures:\t" + dp.getPawnCaptures() + "\n" + 
										"Knight Captures:\t" + dp.getKnightCaptures() + "\n" + 
										"Rook Captures:\t" + dp.getRookCaptures() + "\n" +
										"Bishop Captures:\t" + dp.getBishopCaptures() + "\n" +
										"Queen Captures:\t" + dp.getQueenCaptures() + "\n" +
										"King Captures:\t\t" + dp.getKingCaptures());
				}
    		});
    	}
    }
    
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
    
    private void loadPlayerLists() {
    	playerList.clear();
		// Check if folder exists
		File saveFolder = new File("playersaves");
		if (!saveFolder.exists()) {
			playerList.add(new Player("No games played yet!"));
		}
		// Add players to lists
		File[] saveList = saveFolder.listFiles();
		
		//in both fail states (folder doesn't exist || there are no players), returns a null player named "No games played yet!" just for display.
		if (saveList.length < 1) {
			playerList.add(new Player("No games played yet!"));
		}
		
		//loads each player.ser file as a player and adds it to playerList
		for (int index = 0; index < saveList.length; index++) {
			String name = saveList[index].getName();
			if (name.contains(".ser")) {
				name = name.replace(".ser", "").replace("_", " ");
				DeserializePlayer playerLoader = new DeserializePlayer();
				playerList.add(playerLoader.load(name));
			}
		}
	}
    
    public void setSettings(GameSettings settings) {
		this.settings = settings;
	}
	
    //it does rule hell ya
	public void setRules(GameRules rules) {
		this.rules = rules;
	}
    
}
