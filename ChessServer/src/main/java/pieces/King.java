package pieces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import chess.Board;
import chess.Tile;
/**
 * King Piece class that handles King Logistics
 * @author Ahmad
 */
public class King extends Piece{
	public King(boolean isWhite, Board game) {
		super(isWhite, game);
		name = 'K';
	}

	@Override
	public void updateTiles(Tile start) {
		viableTiles.clear();
		int x, y;
		int[] xmove = {1, 1, 1, 0, -1, -1, -1, 0};
		int[] ymove = {1, 0, -1, -1, -1, 0, 1, 1};
		HashSet<String> oppThreat = isWhite ? game.blackThreat : game.whiteThreat;
		for(int i = 0; i < 8; i++)
		{
			x = start.posX;
			y = start.posY;
			x += xmove[i];
			y += ymove[i];
			if(inBounds(x, y))
			{
				Tile moveTo = game.board[x][y];
				if(moveTo.occupyingPiece == null || moveTo.occupyingPiece.isWhite != this.isWhite) {
					if(!oppThreat.contains(moveTo.toString())) {
						add(game.board[x][y]);
					}
				}
			}
		}
		
		//Ally Pin Check
		ArrayList<Piece> oppPieces = this.isWhite ? game.blackPieces : game.whitePieces;
		ArrayList<Piece> allyPieces = this.isWhite ? game.whitePieces : game.blackPieces;
		for(Piece enemy : oppPieces) {
			if(enemy.occupiedTile == null)
				continue;
			Piece ptr = null;
			int counter = 0;
			for(Piece ally : allyPieces) {
				if(ally.occupiedTile == null || ally instanceof King)
					continue;
				if(enemy.pathToKing.contains(ally.occupiedTile.toString())) {
					counter++;
					ptr = ally;
				}
			}
			if(counter == 1) {//Only 1 Piece blocking path to King and therefore must be pinned
				Iterator<Tile> iterator = ptr.viableTiles.iterator();
				while(iterator.hasNext()) {
					Tile tile = iterator.next();
					if(tile != enemy.occupiedTile && !(enemy.pathToKing.contains(tile.toString())))
						iterator.remove();
				}
				ptr.updatejson();
			}
		}
		
		if(inCheck())
		{
			int counter = 0;
			for(Piece enemy : oppPieces) {
				if(enemy.occupiedTile == null)
					continue;
				if(enemy.viableTiles.contains(this.occupiedTile)) {
					viableTiles.remove(enemy.kingRetreat);
					counter++;
				}
			}
			
			if(counter == 1) {//Single Check
				for(Piece enemy : oppPieces) {
					if(enemy.occupiedTile == null)
						continue;
					if(enemy.viableTiles.contains(this.occupiedTile)) {
						if(enemy instanceof Pawn || enemy instanceof Knight) {
							for(Piece ally : allyPieces) {
								if(ally.occupiedTile == null || ally instanceof King)
									continue;
								Iterator<Tile> iterator = ally.viableTiles.iterator();
								while(iterator.hasNext()) {
									Tile tile = iterator.next();
									if(tile != enemy.occupiedTile)
										iterator.remove();
								}
								ally.updatejson();
							}
						} else {
							for(Piece ally : allyPieces) {
								if(ally.occupiedTile == null || ally instanceof King)
									continue;
								Iterator<Tile> iterator = ally.viableTiles.iterator();
								while(iterator.hasNext()) {
									Tile tile = iterator.next();
									if(tile != enemy.occupiedTile && !(enemy.pathToKing.contains(tile.toString())))
										iterator.remove();
								}
								ally.updatejson();
							}
						}
						break;
					}
				}
			} else {//Double Check. Only King can move. Counter cant be 0 since inCheck()
				for(Piece ally : allyPieces) {
					if(ally.occupiedTile == null || ally instanceof King)
						continue;
					ally.viableTiles.clear();
					ally.updatejson();
				}
			}
		}
		else {//Not in check. Castling conditions here
			if(this.actionsTaken == 0) {
				Tile kingTile = this.occupiedTile;
				int posX = kingTile.posX;
				int posY = kingTile.posY;
				if(castlingTileCheck(game.board[posX+1][posY]) && castlingTileCheck(game.board[posX+2][posY])) {
					Piece rightRook = game.board[posX+3][posY].occupyingPiece;
					if(rightRook instanceof Rook && rightRook.actionsTaken == 0)
						viableTiles.add(game.board[posX+2][posY]);
				}
				if(castlingTileCheck(game.board[posX-1][posY]) && castlingTileCheck(game.board[posX-2][posY])) {
					if(game.board[posX-3][posY].occupyingPiece == null) {
						Piece leftRook = game.board[posX-4][posY].occupyingPiece;
						if(leftRook instanceof Rook && leftRook.actionsTaken == 0)
							viableTiles.add(game.board[posX-2][posY]);
					}
					
				}
			}
		}
	}
	
	public boolean inCheck() {
		HashSet<String> oppSet = isWhite ? game.blackThreat : game.whiteThreat;
		if(oppSet.contains(this.occupiedTile.toString()))
			return true;
		return false;
	}
	
	private boolean castlingTileCheck(Tile tile) {
		HashSet<String> oppThreat = isWhite ? game.blackThreat : game.whiteThreat;
		return (tile.occupyingPiece == null && !oppThreat.contains(tile.toString()));
	}
}
