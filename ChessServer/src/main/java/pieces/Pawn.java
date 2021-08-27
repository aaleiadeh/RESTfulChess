package pieces;

import chess.Board;
import chess.Tile;
/**
 * Pawn Piece class that handles Pawn Logistics
 * @author Ahmad
 */
public class Pawn extends Piece{
	/**
	 * Creates Pawn of specified color in specified board
	 * @param isWhite Color of Pawn
	 * @param game Reference to residing Board
	 */
	public Pawn(boolean isWhite, Board game) {
		super(isWhite, game);
		name = 'P';
	}
	
	@Override
	public void updateTiles(Tile start) {
		int side = start.occupyingPiece.isWhite ? 1 : -1;
		viableTiles.clear();
		if(start.posY != 0 && start.posY != 7) {
			if(game.board[start.posX][start.posY+(1*side)].occupyingPiece == null) {
				viableTiles.add(game.board[start.posX][start.posY+(1*side)]);
				if(actionsTaken == 0) {
					if(game.board[start.posX][start.posY+(2*side)].occupyingPiece == null) {
						viableTiles.add(game.board[start.posX][start.posY+(2*side)]);	
					}
				}
			}
			if(start.posX < 7)
			{
				Tile capture = game.board[start.posX+1][start.posY+(1*side)];
				Piece piece = capture.occupyingPiece;
				if(piece != null) {
					if(piece.isWhite != this.isWhite)
						add(capture);
					else
						threat.add(capture.toString());
				}
				else {
					threat.add(capture.toString());
				}
			}
			if(start.posX > 0)
			{
				Tile capture = game.board[start.posX-1][start.posY+(1*side)];
				Piece piece = capture.occupyingPiece;
				if(piece != null) {
					if(piece.isWhite != this.isWhite)
						add(capture);
					else
						threat.add(capture.toString());
				}
				else {
					threat.add(capture.toString());
				}
			}
		}
	}
}
