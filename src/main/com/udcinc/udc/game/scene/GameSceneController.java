package main.com.udcinc.udc.game.scene;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import main.com.udcinc.udc.game.GameSettings;
import main.com.udcinc.udc.game.board.Position;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.piece.Piece;
import main.com.udcinc.udc.game.piece.impl.Bishop;
import main.com.udcinc.udc.game.piece.impl.King;
import main.com.udcinc.udc.game.piece.impl.Knight;
import main.com.udcinc.udc.game.piece.impl.Pawn;
import main.com.udcinc.udc.game.piece.impl.Queen;
import main.com.udcinc.udc.game.piece.impl.Rook;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.state.GameState;

public class GameSceneController {

	// Reference to the gridPane that is used for the board
	@FXML private GridPane board;
	
	private final GameState gs;
	
	// static piece object used for storing object data during drag and drop
	private static Piece selectedPiece;
	
	/**
	 * This constructor overrides the base FXML constructor via
	 * a constructor factory assignment in GameSceneBuilder.java
	 * @param gs The gamestate model
	 */
	public GameSceneController(GameState gs, Player whitePlayer, Player blackPlayer) {
		this.gs = gs;
		gs.assignPlayers(whitePlayer, blackPlayer);
	}
	
	/**
	 * Initialize is called after the constructor
	 */
	@FXML
	private void initialize() {
		setupGrid();

        //  Create the board
        for (int row = 0; row < gs.getSettings().getSize(); row++) {
            for (int column = 0; column < gs.getSettings().getSize(); column++) {
                registerTile(row,  column);
            }
        }
        
        /*
         * Test data
         */
        
        //  Test objects
        assignPieceToBoard(new Rook(gs.getBlackPlayer(), gs.getBoard().getTiles()[0][0], gs));
        assignPieceToBoard(new Knight(gs.getBlackPlayer(), gs.getBoard().getTiles()[1][0], gs));
        assignPieceToBoard(new Bishop(gs.getBlackPlayer(), gs.getBoard().getTiles()[2][0], gs));
        assignPieceToBoard(new Queen(gs.getBlackPlayer(), gs.getBoard().getTiles()[3][0], gs));
        assignPieceToBoard(new King(gs.getBlackPlayer(), gs.getBoard().getTiles()[4][0], gs));
        assignPieceToBoard(new Bishop(gs.getBlackPlayer(), gs.getBoard().getTiles()[5][0], gs));
        assignPieceToBoard(new Knight(gs.getBlackPlayer(), gs.getBoard().getTiles()[6][0], gs));
        assignPieceToBoard(new Rook(gs.getBlackPlayer(), gs.getBoard().getTiles()[7][0], gs));
        assignPieceToBoard(new Pawn(gs.getBlackPlayer(), gs.getBoard().getTiles()[0][1], gs));
        assignPieceToBoard(new Pawn(gs.getBlackPlayer(), gs.getBoard().getTiles()[1][1], gs));
        assignPieceToBoard(new Pawn(gs.getBlackPlayer(), gs.getBoard().getTiles()[2][1], gs));
        assignPieceToBoard(new Pawn(gs.getBlackPlayer(), gs.getBoard().getTiles()[3][1], gs));
        assignPieceToBoard(new Pawn(gs.getBlackPlayer(), gs.getBoard().getTiles()[4][1], gs));
        assignPieceToBoard(new Pawn(gs.getBlackPlayer(), gs.getBoard().getTiles()[5][1], gs));
        assignPieceToBoard(new Pawn(gs.getBlackPlayer(), gs.getBoard().getTiles()[6][1], gs));
        assignPieceToBoard(new Pawn(gs.getBlackPlayer(), gs.getBoard().getTiles()[7][1], gs));
        
        assignPieceToBoard(new Rook(gs.getWhitePlayer(), gs.getBoard().getTiles()[0][7], gs));
        assignPieceToBoard(new Knight(gs.getWhitePlayer(), gs.getBoard().getTiles()[1][7], gs));
        assignPieceToBoard(new Bishop(gs.getWhitePlayer(), gs.getBoard().getTiles()[2][7], gs));
        assignPieceToBoard(new Queen(gs.getWhitePlayer(), gs.getBoard().getTiles()[3][7], gs));
        assignPieceToBoard(new King(gs.getWhitePlayer(), gs.getBoard().getTiles()[4][7], gs));
        assignPieceToBoard(new Bishop(gs.getWhitePlayer(), gs.getBoard().getTiles()[5][7], gs));
        assignPieceToBoard(new Knight(gs.getWhitePlayer(), gs.getBoard().getTiles()[6][7], gs));
        assignPieceToBoard(new Rook(gs.getWhitePlayer(), gs.getBoard().getTiles()[7][7], gs));
        assignPieceToBoard(new Pawn(gs.getWhitePlayer(), gs.getBoard().getTiles()[0][6], gs));
        assignPieceToBoard(new Pawn(gs.getWhitePlayer(), gs.getBoard().getTiles()[1][6], gs));
        assignPieceToBoard(new Pawn(gs.getWhitePlayer(), gs.getBoard().getTiles()[2][6], gs));
        assignPieceToBoard(new Pawn(gs.getWhitePlayer(), gs.getBoard().getTiles()[3][6], gs));
        assignPieceToBoard(new Pawn(gs.getWhitePlayer(), gs.getBoard().getTiles()[4][6], gs));
        assignPieceToBoard(new Pawn(gs.getWhitePlayer(), gs.getBoard().getTiles()[5][6], gs));
        assignPieceToBoard(new Pawn(gs.getWhitePlayer(), gs.getBoard().getTiles()[6][6], gs));
        assignPieceToBoard(new Pawn(gs.getWhitePlayer(), gs.getBoard().getTiles()[7][6], gs));
	}
	
	/**
	 * Resets the colors of the board tiles back to their native colors.
	 * 
	 * @param root The GridPane containing the board tiles.
	 */
	public void resetBoardColors() {
		for (Node node : board.getChildren()) {
			if ((GridPane.getRowIndex(node) + GridPane.getColumnIndex(node)) % 2 == 0) {
				node.setStyle("-fx-background-color: white");
			} else {
				node.setStyle("-fx-background-color: black");
			}
        }
	}
	
	/**
	 * Takes a piece and finds its position on the grid pane
	 * Sets the image to that of the piece and the color
	 * to that of the player
	 * 
	 * @param piece The piece being added to the board
	 */
	public void assignPieceToBoard(Piece piece) {
		for (Node node : board.getChildren()) {
			if (node instanceof ImageView) {
				if (GridPane.getRowIndex(node) == piece.getPosition().getY() 
						&& GridPane.getColumnIndex(node) == piece.getPosition().getX()) {
					// Set image
					((ImageView) node).setImage(piece.getImage());
					
					// Color changing
					Lighting lighting = new Lighting(new Light.Distant(0, 90, piece.getOwner().getColor()));
	                ColorAdjust bright = new ColorAdjust(0, 1, 1, 1);
	                lighting.setContentInput(bright);
	                lighting.setSurfaceScale(0.0);
	                node.setEffect(lighting);
	                
	                // Set visible
	                node.setOpacity(100);
	                return;
				}
			}
		}
	}
	
	/**
	 * Removes the image of a piece from the board.
	 * 
	 * @param piece
	 */
	public void removePieceFromBoard(Piece piece) {
		for (Node node : board.getChildren()) {
			if (node instanceof ImageView) {
				if (GridPane.getRowIndex(node) == piece.getPosition().getY() 
						&& GridPane.getColumnIndex(node) == piece.getPosition().getX()) {
					node.setOpacity(0);
					return;
				}
			}
		}
	}
	
	public void setupGrid() {
		final double tileSize = 600.0 / gs.getSettings().getSize();
        for (int index = 0; index < board.getRowConstraints().size(); index++) {
            if (index > gs.getSettings().getSize() - 1) {
            	board.getRowConstraints().get(index).setMaxHeight(0);
            	board.getColumnConstraints().get(index).setMaxWidth(0);
            } else {
            	board.getRowConstraints().get(index).setMaxHeight(tileSize);
            	board.getColumnConstraints().get(index).setMaxWidth(tileSize);
            }
        }
	}
	
	/**
	 * Sets event handling and colors the tile at index[row][column]
	 * and assigns this tile to the board
	 * @param row The row index
	 * @param column The column index
	 */
	public void registerTile(final int row, final int column) {
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
        
        //	Handles clicking on a piece
        iv.setOnMouseClicked(event -> {
        	// Reset the board to its default coloring before doing move coloring
        	resetBoardColors();
        	
            Tile boardTile = gs.getBoard().getTiles()[row][column];
            Piece piece = boardTile.getPiece();
            
            // Debug information
            System.out.println("[" + (piece == null ? "" : piece.getName()) + "] owned by " + (piece == null ? "" : piece.getOwner().getName()));
            System.out.println("Clicked: " + row + ", " + column);
            
            // Recolors the board based on which tiles the piece can move to
            if (piece != null) {
                for (Tile t : piece.getAllValidMoves()) {
                    for (Node node : board.getChildren()) {
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
        	Piece piece = gs.getBoard().getTiles()[row][column].getPiece();
        	if (piece == null) {
        		return;
        	}
        	// Piece can only be moved if it is that player's turn
        	if (gs.getActivePlayer() != piece.getOwner()) {
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
                for (Node node : board.getChildren()) {
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
        	// Remove the move coloring
        	resetBoardColors();
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
        	Tile boardTile = gs.getBoard().getTiles()[row][column];
        	// If selected piece is not null, move the piece
        	if (selectedPiece != null) {
        		if (selectedPiece.canMove(boardTile)) {
        			if (boardTile.getPiece() != null) {
        				gs.getBoard().killPiece(boardTile.getPiece());
        			}
        			movePiece(selectedPiece, new Position(row, column));
            	} else {
            		System.out.println("Invalid move!");
            	}
        	}
        	
        	resetBoardColors();
        });
        
        // When a piece is dropped onto a tile
        // *tile may or may not contain another piece
        tile.setOnDragDropped(event -> {                	
        	Tile boardTile = gs.getBoard().getTiles()[row][column];
        	// If selected piece is not null, move the piece
        	if (selectedPiece != null) {
        		if (selectedPiece.canMove(boardTile)) {
        			if (boardTile.getPiece() != null) {
        				gs.getBoard().killPiece(boardTile.getPiece());
        			}
        			movePiece(selectedPiece, new Position(row, column));
            	} else {
            		System.out.println("Invalid move!");
            	}
        	}
        	
        	resetBoardColors();
        });
        
        // Add the tile to the gridpane
        board.add(tile, row, column);
        board.add(iv, row, column);

        //  Game manager assignments
        Tile boardTile = new Tile(column, row);
        gs.getBoard().assignTile(boardTile, column, row);
	}
	
	/**
	 * Moves a piece to a new tile position
	 * @param piece The piece to move
	 * @param nextPos The position to move the piece to
	 */
	private void movePiece(Piece piece, Position nextPos) {
		// En passant piece removal handling, has to be done here to remove the piece visually
		Tile passantTile = gs.getBoard().getPassantTile();
		if (passantTile != null && piece instanceof Pawn) {
			if (nextPos.getX() != piece.getPosition().getX() && nextPos.getY() == passantTile.getPosition().getY() + (piece.getOwner().isWhite() ? -1 : 1) && passantTile.hasPiece()) {
				Piece passantPiece = passantTile.getPiece();
				removePieceFromBoard(passantPiece);
				gs.getBoard().killPiece(passantPiece);
			}
		}
		
		// Visually remove the piece from old tile
		removePieceFromBoard(piece);
		
		// Move the piece to the new tile
		gs.getBoard().movePiece(piece, nextPos);
		
		// Visually add the piece to new tile
		assignPieceToBoard(piece);
		
		// Changes the active player to the other player
		gs.nextTurn();
	}
}
