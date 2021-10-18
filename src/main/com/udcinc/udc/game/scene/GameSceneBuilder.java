package main.com.udcinc.udc.game.scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.com.udcinc.udc.game.GameSettings;
import main.com.udcinc.udc.game.board.Position;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.piece.impl.Bishop;
import main.com.udcinc.udc.game.piece.impl.Queen;
import main.com.udcinc.udc.game.piece.impl.Rook;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.state.GameState;

/**
 * Builder for the game scene
 * Creates a dynamically sized board based on the size specified in the game settings
 * Lots of test lines in here atm
 * 
 * @author Thomas Presicci
 */
public class GameSceneBuilder extends Application {
	
	private GridPane boardPane;
	private static Piece selectedPiece;

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
                
                //	Handles clicking on a piece
                iv.setOnMouseClicked(event -> {
                	// Reset the board to its default coloring before doing move coloring
                	controller.resetBoardColors();
                	
                    Tile boardTile = gs.getBoard().getTiles()[finalRow][finalColumn];
                    Piece piece = boardTile.getPiece();
                    
                    // Debug information
                    System.out.println("[" + (piece == null ? "" : piece.getName()) + "] owned by " + (piece == null ? "" : piece.getOwner().getName()));
                    System.out.println("Clicked: " + finalRow + ", " + finalColumn);
                    
                    // Recolors the board based on which tiles the piece can move to
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
                });
                
                // Start drag and drop
                iv.setOnDragDetected(event -> {
                	Piece piece = gs.getBoard().getTiles()[finalRow][finalColumn].getPiece();
                	if (piece == null) {
                		return;
                	}
                	
                	// Dragboard carries data with the mouse
                	Dragboard db = iv.startDragAndDrop(TransferMode.ANY);
                	// ClipboardContent carries the image
                	ClipboardContent content = new ClipboardContent();
                	content.putImage(iv.getImage());
                	// Set the content to the dragboard
                	db.setContent(content);
                	
                	// Sets the piece being dragged
                	selectedPiece = piece;
                	
                	for (Tile t : piece.getAllValidMoves()) {
                        for (Node node : boardPane.getChildren()) {
                            if (GridPane.getRowIndex(node) == t.getPosition().getY()
                                    && GridPane.getColumnIndex(node) == t.getPosition().getX()) {
                                node.setStyle("-fx-background-color: orange");	// temp color assignments for testing
                            }
                        }
                    }
                	
                	event.consume();
                });
                
                // Used in the case of a drag not ending on a tile
                iv.setOnDragDone(event -> {
                	selectedPiece = null;
                });
                
                // Handles transfer mode acceptance of the piece on the tile
                iv.setOnDragOver(event -> {
                	if (event.getGestureSource() != iv && event.getDragboard().hasImage()) {
                		event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                	}
                	event.consume();
                });
                
                // Handles transfer mode acceptance of the tile
                tile.setOnDragOver(event -> {
                	if (event.getGestureSource() != iv && event.getDragboard().hasImage()) {
                		event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                	}
                	event.consume();
                });
                
                // When a piece is dropped onto another piece
                iv.setOnDragDropped(event -> {
                	// TODO all move validaiton here
                	
                	// If selected piece is not null, move the piece
                	if (selectedPiece != null) {
                		gs.getBoard().movePiece(selectedPiece, new Position(finalRow, finalColumn));
                	}
                	
                	controller.resetBoardColors();
                });
                
                // When a piece is dropped onto a tile
                // *tile may or may not contain another piece
                tile.setOnDragDropped(event -> {
                	// TODO all move validaiton here
                	
                	// If selected piece is not null, move the piece
                	if (selectedPiece != null) {
                		gs.getBoard().movePiece(selectedPiece, new Position(finalRow, finalColumn));
                	}
                	
                	controller.resetBoardColors();
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
