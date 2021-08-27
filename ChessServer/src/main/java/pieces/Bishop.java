package pieces;

import chess.Board;
import chess.Tile;
/**
 * Bishop Piece class that handles Bishop Logistics
 * @author Ahmad
 */
public class Bishop extends Piece{
	/**
	 * Creates Bishop of specified color in specified board
	 * @param isWhite Color of Bishop
	 * @param game Reference to residing Board
	 */
	public Bishop(boolean isWhite, Board game) {
		super(isWhite, game);
		name = 'B';
	}

	@Override
	public void updateTiles(Tile start) 
	{
		viableTiles.clear();
		int x = start.posX;
		int y = start.posY;
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
