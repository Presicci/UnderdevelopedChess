package main.com.udcinc.udc.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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

	@Override
	public void start(Stage primaryStage) throws Exception {
		//  Initialize our game state
		GameState gs = new GameState();

        //  Active game scene
        GridPane root = FXMLLoader.load(getClass().getResource("gameScreen.fxml"));

        //  Handles hiding/showing GridPane columns/rows based on the desired dimensions, defined in Settings.java
        final double tileSize = 800.0 / GameSettings.getSize();
        for (int index = 0; index < root.getRowConstraints().size(); index++) {
            if (index > GameSettings.getSize() - 1) {
                root.getRowConstraints().get(index).setMaxHeight(0);
                root.getColumnConstraints().get(index).setMaxWidth(0);
            } else {
                root.getRowConstraints().get(index).setMaxHeight(tileSize);
                root.getColumnConstraints().get(index).setMaxWidth(tileSize);
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
                
                // Finals used for event registration lambdas
                int finalRow = row;
                int finalColumn = column;
                
                //	Registers clicking on a tile, eventually to be replaced with clicking on a piece on a tile
                tile.setOnMouseClicked(event -> {
                	resetBoardColors(root);
                    Tile boardTile = gs.getBoard().getTiles()[finalRow][finalColumn];
                    System.out.println("Clicked: " + finalRow + ", " + finalColumn);
                    Piece piece = boardTile.getPiece();
                    System.out.println("[" + (piece == null ? "" : piece.getName()) + "]");
                    if (piece != null) {
                        for (Tile t : piece.getAllValidMoves()) {
                            for (Node node : root.getChildren()) {
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
                root.add(tile, row, column);

                //  Game manager assignments
                Tile boardTile = new Tile(column, row);
                gs.getBoard().assignTile(boardTile, column, row);
            }
        }

        // Test player
        Player player = new Player("TestPlayer");
        Player player2 = new Player("TestPlayer2");
        
        //  Test objects
        Rook rook = new Rook(player, gs.getBoard().getTiles()[1][1], gs);
        Bishop bishop = new Bishop(player, gs.getBoard().getTiles()[1][4], gs);
        Queen queen = new Queen(player2, gs.getBoard().getTiles()[3][2], gs);

        // Setup the window
        primaryStage.setTitle("test");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
	}
	
	/**
	 * Resets the colors of the board tiles back to their native colors.
	 * 
	 * @param root The GridPane containing the board tiles.
	 */
	private void resetBoardColors(GridPane root) {
		for (Node node : root.getChildren()) {
			if ((GridPane.getRowIndex(node) + GridPane.getColumnIndex(node)) % 2 == 0) {
				node.setStyle("-fx-background-color: white");
			} else {
				node.setStyle("-fx-background-color: black");
			}
        }
	}
	
	public static void main(String[] args) {
        // Currently just for testing we launch straight into the chess board
		launch(args);
    }

}
