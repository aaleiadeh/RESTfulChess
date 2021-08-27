package pieces;

import java.util.ArrayList;
import java.util.HashSet;

import chess.Board;
import chess.Tile;
/**
 * King Piece class that handles King Logistics
 * @author Ahmad
 */
public class King extends Piece{
	/**
	 * Creates King of specified color in specified board
	 * @param isWhite Color of King
	 * @param game Reference to residing Board
	 */
	public King(boolean isWhite, Board game) {
		super(isWhite, game);
		name = 'K';
	}
//Todo: if under threat by 2. only king can move
	//  if under threat by 1. only king can move. pieces can only move if eliminate threat or block it
	@Override
	public void updateTiles(Tile start) {
		viableTiles.clear();
		int x, y;
		int[] xmove = {1, 1, 1, 0, -1, -1, -1, 0};
		int[] ymove = {1, 0, -1, -1, -1, 0, 1, 1};
		HashSet<String> oppSet = isWhite ? game.blackThreat : game.whiteThreat;
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
					if(!oppSet.contains(moveTo.toString())) {
						add(game.board[x][y]);
					}
				}
			}
		}
	}
}
