package pieces;

import com.fasterxml.jackson.annotation.JsonIgnore;

import chess.Board;
import chess.Tile;
public class Pawn extends Piece{
	
	@JsonIgnore public boolean enpassantable;
	public Pawn(boolean isWhite, Board game) {
		super(isWhite, game);
		name = 'P';
		enpassantable = false;
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
				Piece neighborPiece = game.board[start.posX+1][start.posY].occupyingPiece;
				Tile capture = game.board[start.posX+1][start.posY+(1*side)];
				Piece piece = capture.occupyingPiece;
				if(piece != null) {
					if(piece.isWhite != this.isWhite)
						viableTiles.add(capture);
				}
				else {
					if(neighborPiece instanceof Pawn) {
						if(neighborPiece.isWhite != this.isWhite) {
							if(((Pawn) neighborPiece).enpassantable) {
								viableTiles.add(capture);
							}
						}
					}
				}
				threat.add(capture.toString());
			}
			if(start.posX > 0)
			{
				Piece neighborPiece = game.board[start.posX-1][start.posY].occupyingPiece;
				Tile capture = game.board[start.posX-1][start.posY+(1*side)];
				Piece piece = capture.occupyingPiece;
				if(piece != null) {
					if(piece.isWhite != this.isWhite)
						viableTiles.add(capture);
				}
				else {
					if(neighborPiece instanceof Pawn) {
						if(neighborPiece.isWhite != this.isWhite) {
							if(((Pawn) neighborPiece).enpassantable) {
								viableTiles.add(capture);
							}
						}
					}
				}
				threat.add(capture.toString());
			}
		}
	}
}
