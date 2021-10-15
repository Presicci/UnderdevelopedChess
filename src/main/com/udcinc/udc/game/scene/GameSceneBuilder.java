package main.com.udcinc.udc.game.scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.com.udcinc.udc.game.GameSettings;
import main.com.udcinc.udc.game.GameState;
import main.com.udcinc.udc.game.board.Position;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.piece.impl.Bishop;
import main.com.udcinc.udc.game.piece.impl.Queen;
import main.com.udcinc.udc.game.piece.impl.Rook;
import main.com.udcinc.udc.game.player.Player;

/**
 * Builder for the game scene
 * Creates a dynamically sized board based on the size specified in the game settings
 * Lots of test lines in here atm
 * 
 * @author Thomas Presicci
 */
public class GameSceneBuilder extends Application {
	
	private GridPane boardPane;

	@Override
	public void start(Stage primaryStage) throws Exception {
        //  Active game scene
		boardPane = FXMLLoader.load(getClass().getResource("gameScreen.fxml"));
		
		GameSceneController controller = new GameSceneController(boardPane);
		
		//  Initialize our game state
		GameState gs = new GameState(controller);

        //  Handles hiding/showing GridPane columns/rows based on the desired dimensions, defined in Settings.java
        final double tileSize = 800.0 / GameSettings.getSize();
        for (int index = 0; index < boardPane.getRowConstraints().size(); index++) {
            if (index > GameSettings.getSize() - 1) {
            	boardPane.getRowConstraints().get(index).setMaxHeight(0);
            	boardPane.getColumnConstraints().get(index).setMaxWidth(0);
            } else {
            	boardPane.getRowConstraints().get(index).setMaxHeight(tileSize);
            	boardPane.getColumnConstraints().get(index).setMaxWidth(tileSize);
            }
        }

        //  Create the board
        for (int row = 0; row < GameSettings.getSize(); row++) {
            for (int column = 0; column < GameSettings.getSize(); column++) {
                StackPane tile = new StackPane();
                
                // Generates the tile pattern
                String tileColor;
                if ((row + column) % 2 == 0) {
                    tileColor = "white";
                } else {
                    tileColor = "black";
                }
                tile.setStyle("-fx-background-color: " + tileColor);
                
                // ImageView to be used for pieces
                ImageView iv = new ImageView();
                // Sets dimensions to 80x80
                iv.setFitHeight(80);
                iv.setFitWidth(80);
                // So it doesn't display yet
                iv.setOpacity(0);
                
                // Finals used for event registration lambdas
                int finalRow = row;
                int finalColumn = column;
                
                //	Registers clicking on a tile, eventually to be replaced with clicking on a piece on a tile
                iv.setOnMouseClicked(event -> {
                	controller.resetBoardColors();
                    Tile boardTile = gs.getBoard().getTiles()[finalRow][finalColumn];
                    System.out.println("Clicked: " + finalRow + ", " + finalColumn);
                    Piece piece = boardTile.getPiece();
                    System.out.println("[" + (piece == null ? "" : piece.getName()) + "]");
                    if (piece != null) {
                        for (Tile t : piece.getAllValidMoves()) {
                            for (Node node : boardPane.getChildren()) {
                                if (GridPane.getRowIndex(node) == t.getPosition().getY()
                                        && GridPane.getColumnIndex(node) == t.getPosition().getX()) {
                                    node.setStyle("-fx-background-color: orange");	// temp color assignments for testing
                                }
                            }
                        }
                    }
                    tile.setStyle("-fx-background-color: yellow");	// temp color assignments for testing
                });
                
                // Add the tile to the gridpane
                boardPane.add(tile, row, column);
                boardPane.add(iv, row, column);

                //  Game manager assignments
                Tile boardTile = new Tile(column, row);
                gs.getBoard().assignTile(boardTile, column, row);
            }
        }

        // Test player
        Player player = new Player("TestPlayer");
        Player player2 = new Player("TestPlayer2");
        
        //  Test objects
        gs.getBoard().addPiece(new Rook(player, gs.getBoard().getTiles()[1][1], gs));
        gs.getBoard().addPiece(new Bishop(player, gs.getBoard().getTiles()[1][4], gs));
        gs.getBoard().addPiece(new Queen(player2, gs.getBoard().getTiles()[3][2], gs));

        // Setup the window
        primaryStage.setTitle("test");
        primaryStage.setScene(new Scene(boardPane, 800, 800));
        primaryStage.show();
	}
	
	public static void main(String[] args) {
        // Currently just for testing we launch straight into the chess board
		launch(args);
    }	
}
