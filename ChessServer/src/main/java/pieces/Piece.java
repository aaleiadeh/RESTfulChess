package pieces;

import java.util.ArrayList;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import chess.Board;
import chess.Tile;
/**
 * Generic Chess Piece parent class that defines common traits among all Pieces
 * @author Ahmad
 */
public abstract class Piece 
{
	@JsonIgnore public Board game;
	@JsonIgnore public ArrayList<Tile> viableTiles;
	@JsonIgnore public HashSet<String> threat;
	@JsonIgnore public HashSet<String> pathToKing;
	@JsonIgnore public Tile occupiedTile;
	public char name;
	public String viable;
	@JsonIgnore public boolean isWhite;
	@JsonIgnore public int actionsTaken = 0;
	public Piece(boolean isWhite, Board game)
	{
		this.isWhite = isWhite;
		this.game = game;
		viableTiles = new ArrayList<Tile>();
		threat = isWhite ? game.whiteThreat : game.blackThreat;
		pathToKing = new HashSet<String>();
	}
	public String getName()
	{
		if(isWhite)
		{
			return "w"+name;
		}
		else
		{
			return "b"+name;
		}
	}
	public abstract void updateTiles(Tile start);
	public boolean inBounds(int posX, int posY)
	{
		if(posX > 7 || posY > 7 || posX < 0 || posY < 0)
			return false;
		return true;
	}
	
	public void updatejson()
	{
		viable = "";
		for(Tile tile : viableTiles)
		{
			viable = viable.concat(tile.toString());
		}
	}
	
	public void add(Tile tile)
	{
		viableTiles.add(tile);
		threat.add(tile.toString());
	}
}
