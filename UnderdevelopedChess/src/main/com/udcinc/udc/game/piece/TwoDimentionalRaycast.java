package main.com.udcinc.udc.game.piece;

import java.util.ArrayList;
import java.util.List;

import main.com.udcinc.udc.game.board.Board;
import main.com.udcinc.udc.game.board.Tile;
import main.com.udcinc.udc.game.player.Player;
import main.com.udcinc.udc.game.state.GameState;

/**
 * Contains the raycast functions used to determine which tiles on the board
 * the piece can move.  Raycasts terminate when met with a friendly piece,
 * or after being met with an enemy piece.
 * 
 * @author Thomas Presicci
 */
public interface TwoDimentionalRaycast {
	/**
	 * Performs a search on the x and y coordinate of the piece to check
	 * if the tile is a valid move location.
	 * @param tile The tile on the board being checked.
	 * @param piece The piece being moved.
	 * @return True if the tile is valid, false if the tile is not in line with 
	 * the piece or if there is an obstruction in the way
	 */
	public static boolean straightRaycast(final Tile tile, final Piece piece, GameState gs, boolean friendlyFire) {
		final Tile[][] boardTiles = gs.getBoard().getTiles();
		final int tileX = tile.getPosition().getX();
		final int tileY = tile.getPosition().getY();
		final int pieceX = piece.getPosition().getX();
		final int pieceY = piece.getPosition().getY();
		if (tile.getPiece() != null && tile.getPiece().getOwner() == piece.getOwner() && !friendlyFire) {
			return false;
		}
		if (tileX == pieceX) { // Scan column
			if (tileY > pieceY) { // Scan down
				// Scan from piece to destination tile for obstructions
				for (int index = pieceY + 1; index < tileY; ++index) {
					if (boardTiles[pieceX][index].hasPiece()) {
						return false;
					}
				}
			} else { // Scan up
				// Scan from piece to destination tile for obstructions
				for (int index = tileY; index < pieceY; ++index) {
					if (index != tileY && boardTiles[pieceX][index].hasPiece()) {
						return false;
					}
				}
			}
		} else { // Scan row
			if (tileX > pieceX) { // Scan right
				// Scan from piece to destination tile for obstructions
				for (int index = pieceX + 1; index < tileX; ++index) {
					if (boardTiles[index][pieceY].hasPiece()) {
						return false;
					}
				}
			} else { // Scan left
				// Scan from piece to destination tile for obstructions
				for (int index = tileX; index < pieceX; ++index) {
					if (index != tileX && boardTiles[index][pieceY].hasPiece()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Diagonal raycast function, computes if a tile is along a diagonal of the
	 * piece, and not occluded. Inefficient for global moveset calculation, use
	 * List<Tile> implementation, diagonalRaycastList().
	 * 
	 * @param tile  The tile the raycast is being calculated to.
	 * @param piece The piece the raycast is originating from.
	 * @return True if not obstructed and pathable, false if not.
	 */
	public static boolean diagonalRaycast(Tile tile, Piece piece, GameState gs, boolean friendlyFire) {
		final Tile[][] boardTiles = gs.getBoard().getTiles();
		final int tileX = tile.getPosition().getX();
		final int tileY = tile.getPosition().getY();
		final int pieceX = piece.getPosition().getX();
		final int pieceY = piece.getPosition().getY();
		final int boardSize = gs.getBoard().getSize();
		boolean topObstruction = false;
		boolean bottomObstruction = false;
		if (tileX > pieceX) {
			for (int x = pieceX; x < boardSize; ++x) {
				for (int y = 0; y < boardSize; y++) {
					if (Math.abs(x - pieceX) == Math.abs(y - pieceY)) {
						if (pieceX == x && pieceY == y) {
							continue;
						}
						Piece tilePiece = boardTiles[x][y].getPiece();
						if (tilePiece != null) {
							if ((tileX == x && tileY == y)
									&& (!(y > pieceY && topObstruction) && !(y < pieceY && bottomObstruction))
									 && (tilePiece.getOwner() != piece.getOwner() || friendlyFire)) {
								return true;
							}
							if (y > pieceY) {
								topObstruction = true;
							} else {
								bottomObstruction = true;
							}
						}
						if (tileX != x || tileY != y) {
							continue;
						}
						return (y <= pieceY || !topObstruction) && (y >= pieceY || !bottomObstruction);
					}
				}
			}
		} else if (tileX < pieceX) {
			for (int x = pieceX; x >= 0; --x) {
				for (int y = 0; y < boardSize; y++) {
					if (Math.abs(x - pieceX) == Math.abs(y - pieceY)) {
						if (pieceX == x && pieceY == y) {
							continue;
						}
						Piece tilePiece = boardTiles[x][y].getPiece();
						if (tilePiece != null) {
							if ((tileX == x && tileY == y)
									&& (!(y > pieceY && topObstruction) && !(y < pieceY && bottomObstruction))
									 && (tilePiece.getOwner() != piece.getOwner() || friendlyFire)) {
								return true;
							}
							if (y > pieceY) {
								topObstruction = true;
							} else {
								bottomObstruction = true;
							}
						}
						if (tileX != x || tileY != y) {
							continue;
						}
						return (y <= pieceY || !topObstruction) && (y >= pieceY || !bottomObstruction);
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Diagonal raycast function, returns a list of all possible diagonal move locations.
	 * @param piece The piece that the moves are being calculated for
	 * @return A List<Tile> of valid move locations
	 */
	public static List<Tile> diagonalRaycastList(Piece piece, GameState gs, boolean friendlyFire) {
		final Tile[][] boardTiles = gs.getBoard().getTiles();
		final int pieceX = piece.getPosition().getX();
		final int pieceY = piece.getPosition().getY();
		final int boardSize = gs.getBoard().getSize();
		boolean topObstruction = false;
		boolean bottomObstruction = false;
		List<Tile> validTiles = new ArrayList<>();
		// Scans right along x, originating from the piece
		for (int x = pieceX; x < boardSize; ++x) {
			// Scans down along y, originating from 0
			for (int y = 0; y < boardSize; y++) {
				// If tile is on a diagonal of the piece
				if (Math.abs(x - pieceX) == Math.abs(y - pieceY)) {
					// Avoid matching the piece itself
					if (pieceX == x && pieceY == y) {
						continue;
					}
					Piece tilePiece = boardTiles[x][y].getPiece();
					// If a piece is present on the tile
					if (tilePiece != null) {
						// If tile is not obstructed, and the tile piece 
						// doesn't belong to the owner of the moving piece,
						// it is a valid tile
						if ((!(y > pieceY && topObstruction) && !(y < pieceY && bottomObstruction)) 
								&& (tilePiece.getOwner() != piece.getOwner() || friendlyFire)) {
							validTiles.add(boardTiles[x][y]);
						}
						// Obstruction registration
						if (y > pieceY) {
							topObstruction = true;
						} else {
							bottomObstruction = true;
						}
					}
					// If both diagonals are obstructed, go next
					if (!((y > pieceY && topObstruction) || (y < pieceY && bottomObstruction))) {
						validTiles.add(boardTiles[x][y]);
					}
				}
			}
		}
		topObstruction = false;
		bottomObstruction = false;
		// Scans left along x, originating from the piece
		for (int x = pieceX; x >= 0; --x) {
			// Scans down along y, originating from 0
			for (int y = 0; y < boardSize; y++) {
				// If tile is on a diagonal of the piece
				if (Math.abs(x - pieceX) == Math.abs(y - pieceY)) {
					// Avoid matching the piece itself
					if (pieceX == x && pieceY == y) {
						continue;
					}
					Piece tilePiece = boardTiles[x][y].getPiece();
					// If a piece is present on the tile
					if (tilePiece != null) {
						// If tile is not obstructed, and the tile piece 
						// doesn't belong to the owner of the moving piece,
						// it is a valid tile
						if ((!(y > pieceY && topObstruction) && !(y < pieceY && bottomObstruction)) 
								&& (tilePiece.getOwner() != piece.getOwner() || friendlyFire)) {
							validTiles.add(boardTiles[x][y]);
						}
						// Obstruction registration
						if (y > pieceY) {
							topObstruction = true;
						} else {
							bottomObstruction = true;
						}
					}
					// If both diagonals are obstructed, go next
					if (!((y > pieceY && topObstruction) || (y < pieceY && bottomObstruction))) {
						validTiles.add(boardTiles[x][y]);
					}
				}
			}
		}
		return validTiles;
	}
	
	/**
	 * Gets all tiles that the player's king can not move to
	 * @return List of tiles that the other player's pieces are watching
	 */
	public static List<Tile> getCheckTiles(Player player, Board board) {
		List<Tile> checkTiles = new ArrayList<>();
		for (Tile[] column : board.getTiles()) {
			for (Tile tile : column) {
				Piece piece = tile.getPiece();
				if (piece != null && piece.owner != player) {
					checkTiles.addAll(piece.getDangerousTiles());
				}
			}
		}
		return checkTiles;
	}
	
}