package pieces;

import chess.Board;
import chess.Tile;
/**
 * Rook piece class that handles Rook Logistics
 * @author Ahmad
 */
public class Rook extends Piece{
	/**
	 * Creates Rook of specified color in specified board
	 * @param isWhite Color of Rook
	 * @param game Reference to residing Board
	 */
	public Rook(boolean isWhite, Board game) {
		super(isWhite, game);
		name = 'R';
	}

	@Override
	public void updateTiles(Tile start) {
		viableTiles.clear();
		int x = start.posX;
		int y = start.posY;
		while(x < 7)
		{
			x++;
			if(game.board[x][y].occupyingPiece == null)
				add(game.board[x][y]);
			else
			{
				if(game.board[x][y].occupyingPiece.isWhite != this.isWhite)
					add(game.board[x][y]);
				else
					threat.add(game.board[x][y].toString());
				break;
			}
		}
		x = start.posX;

		while(x > 0)
		{
			x--;
			if(game.board[x][y].occupyingPiece == null)
				add(game.board[x][y]);
			else
			{
				if(game.board[x][y].occupyingPiece.isWhite != this.isWhite)
					add(game.board[x][y]);
				else
					threat.add(game.board[x][y].toString());
				break;
			}
		}
		x = start.posX;

		while(y < 7)
		{
			y++;
			if(game.board[x][y].occupyingPiece == null)
				add(game.board[x][y]);
			else
			{
				if(game.board[x][y].occupyingPiece.isWhite != this.isWhite)
					add(game.board[x][y]);
				else
					threat.add(game.board[x][y].toString());
				break;
			}
		}
		y = start.posY;
		
		while(y > 0)
		{
			y--;
			if(game.board[x][y].occupyingPiece == null)
				add(game.board[x][y]);
			else
			{
				if(game.board[x][y].occupyingPiece.isWhite != this.isWhite)
					add(game.board[x][y]);
				else
					threat.add(game.board[x][y].toString());
				break;
			}
		}
		y = start.posY;
	}
}
