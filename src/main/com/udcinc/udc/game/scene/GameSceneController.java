package main.com.udcinc.udc.game.scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
import main.com.udcinc.udc.game.player.saving.SerializePlayer;
import main.com.udcinc.udc.game.state.GameState;
import main.com.udcinc.udc.mainmenu.MainMenuController;

public class GameSceneController {

	// Reference to the gridPane that is used for the board
	@FXML private GridPane board;
	
	private final GameState gs;
	
	// static piece object used for storing object data during drag and drop
	private static Piece selectedPiece;
	
	// Static list of all currently populated move indicator circles
	private static List<Circle> moveCircles;
	
	private boolean gameActive = false, hasDrawn = false;
	private Timer timerRefresh = new Timer();
	
	/*
	 * Pawn promotion dialogue
	 */
	@FXML private AnchorPane promoteDialogue, drawPane;
	@FXML private Button promoteRook, promoteKnight, promoteBishop, promoteQueen;
	private static Piece promotingPiece;
	
	@FXML
    private Text winnerText, player1Text, player2Text;
	
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
	
	public void closeWindow() {
		gs.getActivePlayer().getTimer().stop();
		timerRefresh.cancel();
		timerRefresh.purge();
		
		System.exit(0);
	}
	
	public void victory(Player winner) {
		winnerText.setText(winner.getName() + " wins! Congratulations!");
		winnerText.setVisible(true);
		gs.getBlackPlayer().incrementGamesCompleted();
		gs.getWhitePlayer().incrementGamesCompleted();
		winner.incrementGamesWon();
		
		if (gs.getActivePlayer().getTimer() != null) gs.getActivePlayer().getTimer().stop();
		timerRefresh.cancel();
		timerRefresh.purge();
		gameActive = false;
	}
	
	@FXML
	private void handleDrawYes() {
		winnerText.setText("Nobody wins! Congratulations!");
		winnerText.setVisible(true);
		
		gs.getBlackPlayer().incrementGamesCompleted();
		gs.getWhitePlayer().incrementGamesCompleted();
		if (gs.getActivePlayer().getTimer() != null) gs.getActivePlayer().getTimer().stop();
		timerRefresh.cancel();
		timerRefresh.purge();
		gameActive = false;
		drawPane.setVisible(false);
		hasDrawn = true;
	}
	
	@FXML
	private void handleDrawNo() {
		drawPane.setVisible(false);
		hasDrawn = true;
	}
	
	public void startTimers() {
		if (gs.getRules().getTimerLimit() > 0) {
			
			//	Timer task that updates player timers on screen
			timerRefresh.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					if (gameActive) {
						player2Text.setText(gs.getWhitePlayer().getName() + "\nTime: " + gs.getWhitePlayer().getTimer().getSeconds());
						player1Text.setText(gs.getBlackPlayer().getName() + "\nTime: " + gs.getBlackPlayer().getTimer().getSeconds());
						
						if (gs.getWhitePlayer().getTimer().getSeconds() < 1) victory(gs.getBlackPlayer());
						else if (gs.getBlackPlayer().getTimer().getSeconds() < 1) victory(gs.getWhitePlayer());
					}
				}
			}, 250, 250);	// every quarter second
			
		}
		else {
			player2Text.setText(gs.getWhitePlayer().getName());
			player1Text.setText(gs.getBlackPlayer().getName());
		}
		
	}
	
	/**
	 * Initialize is called after the constructor
	 */
	@FXML
	private void initialize() {
		gameActive = true;
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
		
		// Adds pieces to the board
		populateBoard();
		
		startTimers();
	}
	
	private String randPiece() {
		Random rand = new Random();
		
		int pieceType = rand.nextInt(5);
		
		switch(pieceType) {
			case 0:
				return "p";
			case 1:
				return "k";
			case 2:
				return "r";
			case 3:
				return "b";
			case 4:
				return "q";
			default:
				return "p";
		}
	}
	
	private String[][] generateChaosLayout(){
		String[][] returnLayout = new String[8][8];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if (j == 4) {
					if (i == 0) {
						returnLayout[i][j] = "bK";
						continue;
					}
					if (i == 7) {
						returnLayout[i][j] = "wK";
						continue;
					}
				}
				if (i < 2) 
					returnLayout[i][j] = "b" + randPiece();
				else if (i > 5)
					returnLayout[i][j] = "w" + randPiece();
				else
					returnLayout[i][j] = "-";
			}
		}
		return returnLayout;
	}
	
	/**
	 * Default board layout, just like normal chess
	 */
	private String[][] defaultLayout = { 
			{ "br", "bk", "bb", "bq", "bK", "bb", "bk", "br" },
			{ "bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp" },
			{ "-", "-", "-", "-", "-", "-", "-", "-", },
			{ "-", "-", "-", "-", "-", "-", "-", "-", },
			{ "-", "-", "-", "-", "-", "-", "-", "-", },
			{ "-", "-", "-", "-", "-", "-", "-", "-", },
			{ "wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp" },
			{ "wr", "wk", "wb", "wq", "wK", "wb", "wk", "wr" },
		};

	/**
	 * Modular board population
	 * Pulls from a 2d string array of piece representations
	 * 
	 * Full character table:
	 * - denotes an empty tile, or really just any incorrect syntax will too
	 * 
	 * prefixes
	 * b denotes a black owned piece
	 * w denotes a white owned piece
	 * 
	 * suffixes, case sensitive!
	 * r denotes a rook
	 * k denotes a knight
	 * b denotes a bishop
	 * q denotes a queen
	 * K denotes a king
	 * p denotes a pawn
	 */
	private void populateBoard() {
		Board b = gs.getBoard();
		int x = 0, y = 0;
		String[][] layout;
		
		switch(gs.getSettings().getGameType()) {
			case "Standard":
				layout = defaultLayout;
				break;
			case "Chaos":
				layout = generateChaosLayout();
				break;
			default:
				layout = defaultLayout;
		}
		
		
		for (String[] row : layout) {	
			for (String tile : row) {
				Player player = null;
				// Hyphen (-) denotes an empty tile
				if (tile.equalsIgnoreCase("-")) {
					++x;
					continue;
				}
				// prefix denotes player ownership, case insensitive
				if (tile.startsWith("b") || tile.startsWith("B")) {
					player = gs.getBlackPlayer();
				} else if (tile.startsWith("w") || tile.startsWith("W")) {
					player = gs.getWhitePlayer();
				}
				// If no player is read, skip piece
				if (player == null) {
					++x;
					continue;
				}
				// Suffix denotes piece type, case sensitive
				if (tile.endsWith("r")) {
					assignPieceToBoard(new Rook(player, b.getTile(x, y), gs));
				} else if (tile.endsWith("k")) {
					assignPieceToBoard(new Knight(player, b.getTile(x, y), gs));
				} else if (tile.endsWith("b")) {
					assignPieceToBoard(new Bishop(player, b.getTile(x, y), gs));
				} else if (tile.endsWith("q")) {
					assignPieceToBoard(new Queen(player, b.getTile(x, y), gs));
				} else if (tile.endsWith("K")) {
					assignPieceToBoard(new King(player, b.getTile(x, y), gs));
				} else if (tile.endsWith("p")) {
					assignPieceToBoard(new Pawn(player, b.getTile(x, y), gs));
				}
				++x;
			}
			x = 0;
			++y;
		}
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
		// Set to color to whats defined in the settings
    	circle.setFill(gs.getSettings().getMoveHighlightingColor());
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
            tileColor = colorToHex(gs.getSettings().getBoardBrown());
        } else {
            tileColor = colorToHex(gs.getSettings().getBoardBeige());
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
        	resetMoveCircles();
        });
        
        // Start drag and drop
        iv.setOnDragDetected(event -> {
        	if (!gameActive) {
        		return;
        	}
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
        			Piece capturedPiece = boardTile.getPiece();
        			if (capturedPiece != null) {
            			handleMoveStats(selectedPiece, capturedPiece);
        				gs.getBoard().killPiece(boardTile.getPiece());
        				if (capturedPiece.getName().equals("King")) {
        					victory(gs.getActivePlayer());
        				}
        			}
        			movePiece(selectedPiece, new Position(row, column));
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
        		Piece capturedPiece = boardTile.getPiece();
    				if (capturedPiece != null) {
    					handleMoveStats(selectedPiece, capturedPiece);
        				gs.getBoard().killPiece(boardTile.getPiece());
        				if (capturedPiece.getName().equals("King")) {
        					victory(gs.getActivePlayer());
        				}
        			}
        			movePiece(selectedPiece, new Position(row, column));
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
				if (passantPiece.getName().equals("King")) {
					victory(gs.getActivePlayer());
				}
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
		
		checkAllPieces();

		// Changes the active player to the other player
		gs.nextTurn();
	}
	
	private void checkAllPieces() {
		int bCount = 0, wCount = 0;
		
		for(Piece piece : gs.getBoard().getPieces()) {
			if (piece.getOwner().getName().equals(gs.getBlackPlayer().getName())) bCount++;
			else if (piece.getOwner().getName().equals(gs.getWhitePlayer().getName())) wCount++;
		}
		
		if (bCount < 1) {
			victory(gs.getWhitePlayer());
		}
		else if(wCount < 1) {
			victory(gs.getBlackPlayer());
		}
		else if(bCount == 1 && wCount == 1 && hasDrawn == false && gs.getSettings().getGameType() != "Chaos") {
			drawPane.setVisible(true);
		}
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
	
	/**
	 * Converts a javafx color into a hex string of format #000000
	 * @param color The color to convert
	 * @return A string containing the hexcode
	 */
	private String colorToHex(Color color) {
        return String.format("#%02X%02X%02X",
            (int)(color.getRed() * 255),
            (int)(color.getGreen() * 255),
            (int)(color.getBlue() * 255));
    }
	
	/**
	 * Increments player counters based on the piece being moved and the piece being captured
	 * Called when a piece captures another piece
	 * @param movedPiece The piece doing the capturing
	 * @param capturedPiece The piece being captured
	 */
	private void handleMoveStats(Piece movedPiece, Piece capturedPiece) {
		Player owner = movedPiece.getOwner();
		if (owner == null) {
			return;
		}
		if (movedPiece instanceof Pawn) {
			owner.incrementPawnCaptures();
		} else if (movedPiece instanceof Rook) {
			owner.incrementRookCaptures();
		} else if (movedPiece instanceof Bishop) {
			owner.incrementBishopCaptures();
		} else if (movedPiece instanceof Knight) {
			owner.incrementKnightCaptures();
		} else if (movedPiece instanceof Queen) {
			owner.incrementQueenCaptures();
		} else if (movedPiece instanceof King) {
			owner.incrementKingCaptures();
		}
		if (capturedPiece instanceof Pawn) {
			owner.incrementPawnsCaptured();
		} else if (capturedPiece instanceof Rook) {
			owner.incrementRooksCaptured();
		} else if (capturedPiece instanceof Bishop) {
			owner.incrementBishopsCaptured();
		} else if (capturedPiece instanceof Knight) {
			owner.incrementKnightsCaptured();
		} else if (capturedPiece instanceof Queen) {
			owner.incrementQueensCaptured();
		}
	}
	
	/**
	 * Handler for the main menu button
	 * 
	 * @param event The event being triggered
	 * @throws IOException exception thrown if GameScreen.fxml can not be loaded
	 */
	@FXML
	public void handleMainMenu(Event event) throws IOException {
		Stage stage = ((Stage) ((Node)event.getSource()).getScene().getWindow());
		// Active game scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/MainMenu.fxml"));

		// Gets root pane for the scene
		Pane root = loader.load();
		
		// Pass settings and rules along
        MainMenuController controller = loader.<MainMenuController>getController();
        controller.setRules(gs.getRules());
        controller.setSettings(gs.getSettings());
        
        Player whitePlayer = gs.getWhitePlayer();
        Player blackPlayer = gs.getBlackPlayer();
        
        whitePlayer.incrementGamesPlayed();
        blackPlayer.incrementGamesPlayed();
		SerializePlayer.save(whitePlayer);
		SerializePlayer.save(blackPlayer);
		if(whitePlayer.getTimer() != null) whitePlayer.getTimer().stop(); //only if there is a timer, stop them
		if(blackPlayer.getTimer() != null) blackPlayer.getTimer().stop();
        
		// Transition scene to gamescreen
		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Handler for the main menu button
	 * 
	 * @param event The event being triggered
	 * @throws IOException exception thrown if GameScreen.fxml can not be loaded
	 */
	@FXML
	public void handleOfferDraw(Event event) throws IOException {
		drawPane.setVisible(true);
	}
}
