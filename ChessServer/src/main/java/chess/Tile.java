package chess;

import pieces.Piece;
public class Tile
{
	public int posX;
	public int posY;
	public Piece occupyingPiece = null;
	public Tile(int x, int y)
	{
		posX = x;
		posY = y;
	}
	
	public String toString()
	{
		return String.valueOf((char)(posX + 65)).concat(String.valueOf(posY+1));
	}
}
