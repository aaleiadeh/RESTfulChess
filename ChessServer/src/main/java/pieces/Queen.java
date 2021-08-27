package pieces;

import chess.Board;
import chess.Tile;
/**
 * Queen Piece class that handles Queen Logistics
 * @author Ahmad
 */
public class Queen extends Piece{
	/**
	 * Creates Queen of specified color in specified board
	 * @param isWhite Color of Queen
	 * @param game Reference to residing Board
	 */
	public Queen(boolean isWhite, Board game) {
		super(isWhite, game);
		name = 'Q';
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
		
		while(x < 7 && y < 7)
		{
			x++;
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
		x = start.posX;
		y = start.posY;
		
		while(x > 0 && y > 0)
		{
			x--;
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
		x = start.posX;
		y = start.posY;
		
		while(x < 7 && y > 0)
		{
			x++;
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
		x = start.posX;
		y = start.posY;
		
		while(x > 0 && y < 7)
		{
			x--;
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
		x = start.posX;
		y = start.posY;
	}
}
