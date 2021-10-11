package main.com.udcinc.udc.game.piece;

import main.com.udcinc.udc.game.GameSettings;
import main.com.udcinc.udc.game.GameStatics;
import main.com.udcinc.udc.game.board.Tile;

public class TwoDimentionalRaycast {
	public static boolean straightRaycast(final Tile tile, final Piece piece) {
		final Tile[][] boardTiles = GameStatics.getGameState().getBoard().getTiles();
		final int tileX = tile.getPosition().getX();
		final int tileY = tile.getPosition().getY();
		final int pieceX = piece.getPosition().getX();
		final int pieceY = piece.getPosition().getY();
		// if (tile.getPiece().getOwner()) TODO return false if tile contains FRIENDLY
		// piece
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
	
	public static boolean diagonalRaycast(Tile tile, Piece piece) {
		final Tile[][] boardTiles = GameStatics.getGameState().getBoard().getTiles();
		final int tileX = tile.getPosition().getX();
		final int tileY = tile.getPosition().getY();
		final int pieceX = piece.getPosition().getX();
		final int pieceY = piece.getPosition().getY();
		final int boardSize = GameSettings.getSize();
		boolean topObstruction = false;
		boolean bottomObstruction = false;
		if (tileX > pieceX) {
			for (int x = pieceX; x < boardSize; ++x) {
				for (int y = 0; y < boardSize; y++) {
					if (Math.abs(x - pieceX) == Math.abs(y - pieceY)) {
						if (pieceX == x && pieceY == y) {
							continue;
						}
						if (boardTiles[x][y].hasPiece()) {
							if (y > pieceY) {
								topObstruction = true;
							} else {
								bottomObstruction = true;
							}
						}
						if (tileX != x || tileY != y) {
							continue;
						}
						if (topObstruction || bottomObstruction) {
							return false;
						} else {
							return true;
						}
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
						if (boardTiles[x][y].hasPiece()) {
							if (y > pieceY) {
								topObstruction = true;
							} else {
								bottomObstruction = true;
							}
						}
						if (tileX != x || tileY != y) {
							continue;
						}
						if (topObstruction || bottomObstruction) {
							return false;
						} else {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}