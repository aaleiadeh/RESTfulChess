package pieces;

import chess.Board;
import chess.Tile;
/**
 * Knight Piece class that handles Knight Logistics
 * @author Ahmad
 */
public class Knight extends Piece{
	/**
	 * Creates Knight of specified color in specified board
	 * @param isWhite Color of Knight
	 * @param game Reference to residing Board
	 */
	public Knight(boolean isWhite, Board game) {
		super(isWhite, game);
		name = 'N';
	}

	@Override
	public void updateTiles(Tile start) {
		viableTiles.clear();
		int x, y;
		int[] xmove = {1, 1, -1, -1, 2, 2, -2, -2};
		int[] ymove = {2, -2, 2, -2, 1, -1, 1, -1};
		for(int i = 0; i < 8; i++)
		{
			x = start.posX;
			y = start.posY;
			x += xmove[i];
			y += ymove[i];
			if(inBounds(x, y))
			{
				Tile moveTo = game.board[x][y];
				if(moveTo.occupyingPiece == null) {
					add(moveTo);
				}
				else {
					if(moveTo.occupyingPiece.isWhite != this.isWhite)
						add(moveTo);
					else
						threat.add(moveTo.toString());
				}
					
			}
		}
	}
}
