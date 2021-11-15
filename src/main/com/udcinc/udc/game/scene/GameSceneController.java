package main.com.udcinc.udc.game.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.com.udcinc.udc.game.board.Board;
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
	
	// Static list of all currently populated move indicator circles
	private static List<Circle> moveCircles;
	
	/*
	 * Pawn promotion dialogue
	 */
	@FXML private AnchorPane promoteDialogue;
	@FXML private Button promoteRook, promoteKnight, promoteBishop, promoteQueen;
	private static Piece promotingPiece;
	
	/**
	 * This constructor overrides the base FXML constructor via
	 * a constructor factory assignment in GameSceneBuilder.java
	 * @param gs The gamestate model
	 */
	public GameSceneController(GameState gs, Player whitePlayer, Player blackPlayer) {
		this.gs = gs;
		gs.assignPlayers(whitePlayer, blackPlayer);
		moveCircles = new ArrayList<Circle>();
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
        
		// Setup the pawn promotion button images
		promoteRook.setBackground(new Background(Collections.singletonList(new BackgroundFill(
                Color.LIGHTGRAY, new CornerRadii(0), new Insets(0))),
				Collections.singletonList(new BackgroundImage(new Image("./main/resources/sprites/rook.png", 80, 80, true, true), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT))));
		promoteKnight.setBackground(new Background(Collections.singletonList(new BackgroundFill(
                Color.LIGHTGRAY, new CornerRadii(0), new Insets(0))),
				Collections.singletonList(new BackgroundImage(new Image("./main/resources/sprites/knight.png", 80, 80, true, true), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT))));
		promoteBishop.setBackground(new Background(Collections.singletonList(new BackgroundFill(
                Color.LIGHTGRAY, new CornerRadii(0), new Insets(0))),
				Collections.singletonList(new BackgroundImage(new Image("./main/resources/sprites/bishop.png", 80, 80, true, true), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT))));
		promoteQueen.setBackground(new Background(Collections.singletonList(new BackgroundFill(
                Color.LIGHTGRAY, new CornerRadii(0), new Insets(0))),
				Collections.singletonList(new BackgroundImage(new Image("./main/resources/sprites/queen.png", 80, 80, true, true), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT))));
        
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
	 * Remove all move indicator circles from the board.
	 * should be performed after each move, or on selecting a new piece
	 */
	private void resetMoveCircles() {
		for (Circle c : moveCircles) {
			board.getChildren().remove(c);
		}
		moveCircles.clear();
	}
	
	/**
	 * Adds a circle at the specified grid coordinates, indicating that tile as a possible move
	 * for the selected piece
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	private void addMoveCircle(int x, int y) {
		// Checks if move highlighting is enabled in the settings
		if (!gs.getSettings().isMoveHightlighting()) {
			return;
		}
		// Setup circle
		Circle circle = new Circle(0, 0, 15);
    	circle.setFill(Color.LIMEGREEN);
    	circle.setStroke(Color.BLACK);
    	// Remove circle on drag hover over so it does not interfere with our dropping
        circle.setOnDragEntered(event -> {
        	circle.setVisible(false);
        	for (Circle c : moveCircles) {
        		if (c != circle) {
        			c.setVisible(true);	
        		}
        	}
        });
    	// Center the circle
    	double centerX = ((board.getWidth() / gs.getBoard().getSize()) / 2) - (circle.getRadius());
    	circle.setTranslateX(centerX);
        // Add to board
    	board.add(circle, x, y);
    	moveCircles.add(circle);
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
        	resetMoveCircles();
        	
            Tile boardTile = gs.getBoard().getTiles()[row][column];
            Piece piece = boardTile.getPiece();
            
            // Debug information
            System.out.println("[" + (piece == null ? "" : piece.getName()) + "] owned by " + (piece == null ? "" : piece.getOwner().getName()));
            System.out.println("Clicked: " + row + ", " + column);
            
            // Spawn a circle on tiles that are possible moves
            if (piece != null) {
                for (Tile t : piece.getAllValidMoves()) {
                	addMoveCircle(t.getPosition().getX(), t.getPosition().getY());
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
            	addMoveCircle(t.getPosition().getX(), t.getPosition().getY());
            }
        	
        	event.consume();
        });
        
        // Used in the case of a drag not ending on a tile
        iv.setOnDragDone(event -> {
        	selectedPiece = null;
        	// Remove the move coloring
        	resetMoveCircles();
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
        	
        	resetMoveCircles();
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
        	
        	resetMoveCircles();
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
		Board board = gs.getBoard();
		Position pos = piece.getPosition();
		
		// En passant piece removal handling, has to be done here to remove the piece visually
		Tile passantTile = board.getPassantTile();
		if (passantTile != null && piece instanceof Pawn) {
			if (nextPos.getX() != pos.getX() && nextPos.getY() == passantTile.getPosition().getY() + (piece.getOwner().isWhite() ? -1 : 1) && passantTile.hasPiece()) {
				Piece passantPiece = passantTile.getPiece();
				removePieceFromBoard(passantPiece);
				board.killPiece(passantPiece);
			}
		}
		
		// Visually remove the piece from old tile
		removePieceFromBoard(piece);
		
		// Move the piece to the new tile
		board.movePiece(piece, nextPos);
		
		// Visually add the piece to new tile
		assignPieceToBoard(piece);
		
		// Pawn promotion handling
		if ((piece.getOwner().isWhite() ? (nextPos.getY() == 0) : (nextPos.getY() == board.getSize() - 1))
				&& piece instanceof Pawn 
				&& gs.getRules().isPawnPromotionActive()) {
			promotingPiece = selectedPiece;
			promoteDialogue.setVisible(true);
			return; // pause turn until dialogue is closed
		}

		// Castling handling
		if (piece instanceof King) {
			// Only time a king can move 2 tiles is when castling
			int diff = nextPos.getX() - pos.getX();
			if (Math.abs(diff) > 1) {
				if (diff > 0) { // Castling right
					Piece endPiece = board.getTile(board.getSize() - 1, pos.getY()).getPiece();
					if (endPiece != null && endPiece instanceof Rook) {
						movePiece(endPiece, new Position(nextPos.getX() - 1, nextPos.getY()));
						return;
					}
				} else { // Castling left
					Piece endPiece = board.getTile(0, pos.getY()).getPiece();
					if (endPiece != null && endPiece instanceof Rook) {
						movePiece(endPiece, new Position(nextPos.getX() + 1, nextPos.getY()));
						return;
					}
				}
			}
		}

		// Changes the active player to the other player
		gs.nextTurn();
	}
	
	/**
	 * Handler for the rook button on the pawn promotion dialogue
	 * Changes the pawn into a rook, then advances the turn
	 * @param event The event being triggered
	 */
	@FXML private void handlePromoteRook() {
		if (promotingPiece == null || !(promotingPiece instanceof Pawn)) {
			promoteDialogue.setVisible(false);
			gs.nextTurn();
			return;
		}
		Rook rook = new Rook(promotingPiece.getOwner(), gs.getBoard().getTile(promotingPiece.getPosition()), gs);
		assignPieceToBoard(rook);
		
		promoteDialogue.setVisible(false);
		promotingPiece = null;
		gs.nextTurn();
	}
	
	/**
	 * Handler for the knight button on the pawn promotion dialogue
	 * Changes the pawn into a knight, then advances the turn
	 * @param event The event being triggered
	 */
	@FXML private void handlePromoteKnight() {
		if (promotingPiece == null || !(promotingPiece instanceof Pawn)) {
			promoteDialogue.setVisible(false);
			gs.nextTurn();
			return;
		}
		Knight knight = new Knight(promotingPiece.getOwner(), gs.getBoard().getTile(promotingPiece.getPosition()), gs);
		assignPieceToBoard(knight);
		
		promoteDialogue.setVisible(false);
		promotingPiece = null;
		gs.nextTurn();
	}
	
	/**
	 * Handler for the bishop button on the pawn promotion dialogue
	 * Changes the pawn into a bishop, then advances the turn
	 * @param event The event being triggered
	 */
	@FXML private void handlePromoteBishop() {
		if (promotingPiece == null || !(promotingPiece instanceof Pawn)) {
			promoteDialogue.setVisible(false);
			gs.nextTurn();
			return;
		}
		Bishop bishop = new Bishop(promotingPiece.getOwner(), gs.getBoard().getTile(promotingPiece.getPosition()), gs);
		assignPieceToBoard(bishop);
		
		promoteDialogue.setVisible(false);
		promotingPiece = null;
		gs.nextTurn();
	}
	
	/**
	 * Handler for the queen button on the pawn promotion dialogue
	 * Changes the pawn into a queen, then advances the turn
	 * @param event The event being triggered
	 */
	@FXML private void handlePromoteQueen() {
		if (promotingPiece == null || !(promotingPiece instanceof Pawn)) {
			promoteDialogue.setVisible(false);
			gs.nextTurn();
			return;
		}
		Queen queen = new Queen(promotingPiece.getOwner(), gs.getBoard().getTile(promotingPiece.getPosition()), gs);
		assignPieceToBoard(queen);
		
		promoteDialogue.setVisible(false);
		promotingPiece = null;
		gs.nextTurn();
	}
}
