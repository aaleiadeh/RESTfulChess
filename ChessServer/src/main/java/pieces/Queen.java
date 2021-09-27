package pieces;

import chess.Board;
import chess.Tile;
public class Queen extends Piece{
	public Queen(boolean isWhite, Board game) {
		super(isWhite, game);
		name = 'Q';
	}
	
	@Override
	public void updateTiles(Tile start) {
		viableTiles.clear();
		pathToKing.clear();
		kingRetreat = null;
		int x = start.posX;
		int y = start.posY;
		boolean blocked = false;
		boolean kingFound = false;
		while(x < 7)
		{
			x++;
			Tile tile = game.board[x][y];
			if(!blocked)//Standard Move Viability Checking
			{
				if(tile.occupyingPiece == null)
				{
					add(tile);
					if(kingFound)
						continue;
					pathToKing.add(tile.toString());
				}
				else
				{
					if(tile.occupyingPiece.isWhite != this.isWhite) {
						add(tile);
						blocked = true;
						if(kingFound)
							continue;
						pathToKing.add(tile.toString());
						if(tile.occupyingPiece instanceof King) {
							kingFound = true;
							if(x < 7)
								kingRetreat = game.board[x+1][y];
						}
					}
					else { //Blocked by ally piece, no point in searching for pathtoking
						threat.add(tile.toString());
						break;
					}
				}
			}
			else //Standard moves found, just establishing path to king here
			{
				if(kingFound)
					break;
				if(tile.occupyingPiece != null)
				{
					if(tile.occupyingPiece.isWhite == this.isWhite)
						break;
					pathToKing.add(tile.toString());
					if(tile.occupyingPiece instanceof King) {
						kingFound = true;
						if(x < 7)
							kingRetreat = game.board[x+1][y];
					}
				}
				else
					pathToKing.add(tile.toString());
			}
		}
		
		x = start.posX;
		blocked = false;
		if(!kingFound)
			pathToKing.clear();
		while(x > 0)
		{
			x--;
			Tile tile = game.board[x][y];
			if(!blocked)//Standard Move Viability Checking
			{
				if(tile.occupyingPiece == null)
				{
					add(tile);
					if(kingFound)
						continue;
					pathToKing.add(tile.toString());
				}
				else
				{
					if(tile.occupyingPiece.isWhite != this.isWhite) {
						add(tile);
						blocked = true;
						if(kingFound)
							continue;
						pathToKing.add(tile.toString());
						if(tile.occupyingPiece instanceof King) {
							kingFound = true;
							if(x > 0)
								kingRetreat = game.board[x-1][y];
						}
					}
					else { //Blocked by ally piece, no point in searching for pathtoking
						threat.add(tile.toString());
						break;
					}
				}
			}
			else //Standard moves found, just establishing path to king here
			{
				if(kingFound)
					break;
				if(tile.occupyingPiece != null)
				{
					if(tile.occupyingPiece.isWhite == this.isWhite)
						break;
					pathToKing.add(tile.toString());
					if(tile.occupyingPiece instanceof King) {
						kingFound = true;
						if(x > 0)
							kingRetreat = game.board[x-1][y];
					}
				}
				else
					pathToKing.add(tile.toString());
			}
		}
		
		x = start.posX;
		blocked = false;
		if(!kingFound)
			pathToKing.clear();
		while(y < 7)
		{
			y++;
			Tile tile = game.board[x][y];
			if(!blocked)//Standard Move Viability Checking
			{
				if(tile.occupyingPiece == null)
				{
					add(tile);
					if(kingFound)
						continue;
					pathToKing.add(tile.toString());
				}
				else
				{
					if(tile.occupyingPiece.isWhite != this.isWhite) {
						add(tile);
						blocked = true;
						if(kingFound)
							continue;
						pathToKing.add(tile.toString());
						if(tile.occupyingPiece instanceof King) {
							kingFound = true;
							if(y < 7)
								kingRetreat = game.board[x][y+1];
						}
					}
					else { //Blocked by ally piece, no point in searching for pathtoking
						threat.add(tile.toString());
						break;
					}
				}
			}
			else //Standard moves found, just establishing path to king here
			{
				if(kingFound)
					break;
				if(tile.occupyingPiece != null)
				{
					if(tile.occupyingPiece.isWhite == this.isWhite)
						break;
					pathToKing.add(tile.toString());
					if(tile.occupyingPiece instanceof King) {
						kingFound = true;
						if(y < 7)
							kingRetreat = game.board[x][y+1];
					}
				}
				else
					pathToKing.add(tile.toString());
			}
		}
		
		y = start.posY;
		blocked = false;
		if(!kingFound)
			pathToKing.clear();
		while(y > 0)
		{
			y--;
			Tile tile = game.board[x][y];
			if(!blocked)//Standard Move Viability Checking
			{
				if(tile.occupyingPiece == null)
				{
					add(tile);
					if(kingFound)
						continue;
					pathToKing.add(tile.toString());
				}
				else
				{
					if(tile.occupyingPiece.isWhite != this.isWhite) {
						add(tile);
						blocked = true;
						if(kingFound)
							continue;
						pathToKing.add(tile.toString());
						if(tile.occupyingPiece instanceof King) {
							kingFound = true;
							if(y > 0)
								kingRetreat = game.board[x][y-1];
						}
					}
					else { //Blocked by ally piece, no point in searching for pathtoking
						threat.add(tile.toString());
						break;
					}
				}
			}
			else //Standard moves found, just establishing path to king here
			{
				if(kingFound)
					break;
				if(tile.occupyingPiece != null)
				{
					if(tile.occupyingPiece.isWhite == this.isWhite)
						break;
					pathToKing.add(tile.toString());
					if(tile.occupyingPiece instanceof King) {
						kingFound = true;
						if(y > 0)
							kingRetreat = game.board[x][y-1];
					}
				}
				else
					pathToKing.add(tile.toString());
			}
		}
		y = start.posY;
		blocked = false;
		if(!kingFound)
			pathToKing.clear();
		
		
		
		while(x < 7 && y < 7)
		{
			x++;
			y++;
			Tile tile = game.board[x][y];
			if(!blocked)//Standard Move Viability Checking
			{
				if(tile.occupyingPiece == null)
				{
					add(tile);
					if(kingFound)
						continue;
					pathToKing.add(tile.toString());
				}
				else
				{
					if(tile.occupyingPiece.isWhite != this.isWhite) {
						add(tile);
						blocked = true;
						if(kingFound)
							continue;
						pathToKing.add(tile.toString());
						if(tile.occupyingPiece instanceof King) {
							kingFound = true;
							if(x < 7 && y < 7)
								kingRetreat = game.board[x+1][y+1];
						}
					}
					else { //Blocked by ally piece, no point in searching for pathtoking
						threat.add(tile.toString());
						break;
					}
				}
			}
			else //Standard moves found, just establishing path to king here
			{
				if(kingFound)
					break;
				if(tile.occupyingPiece != null)
				{
					if(tile.occupyingPiece.isWhite == this.isWhite)
						break;
					pathToKing.add(tile.toString());
					if(tile.occupyingPiece instanceof King) {
						kingFound = true;
						if(x < 7 && y < 7)
							kingRetreat = game.board[x+1][y+1];
					}
				}
				else
					pathToKing.add(tile.toString());
			}
		}
		
		x = start.posX;
		y = start.posY;
		blocked = false;
		if(!kingFound)
			pathToKing.clear();
		while(x > 0 && y > 0)
		{
			x--;
			y--;
			Tile tile = game.board[x][y];
			if(!blocked)//Standard Move Viability Checking
			{
				if(tile.occupyingPiece == null)
				{
					add(tile);
					if(kingFound)
						continue;
					pathToKing.add(tile.toString());
				}
				else
				{
					if(tile.occupyingPiece.isWhite != this.isWhite) {
						add(tile);
						blocked = true;
						if(kingFound)
							continue;
						pathToKing.add(tile.toString());
						if(tile.occupyingPiece instanceof King) {
							kingFound = true;
							if(x > 0 && y > 0)
								kingRetreat = game.board[x-1][y-1];
						}
					}
					else { //Blocked by ally piece, no point in searching for pathtoking
						threat.add(tile.toString());
						break;
					}
				}
			}
			else //Standard moves found, just establishing path to king here
			{
				if(kingFound)
					break;
				if(tile.occupyingPiece != null)
				{
					if(tile.occupyingPiece.isWhite == this.isWhite)
						break;
					pathToKing.add(tile.toString());
					if(tile.occupyingPiece instanceof King) {
						kingFound = true;
						if(x > 0 && y > 0)
							kingRetreat = game.board[x-1][y-1];
					}
				}
				else
					pathToKing.add(tile.toString());
			}
		}
		
		x = start.posX;
		y = start.posY;
		blocked = false;
		if(!kingFound)
			pathToKing.clear();
		while(x < 7 && y > 0)
		{
			x++;
			y--;
			Tile tile = game.board[x][y];
			if(!blocked)//Standard Move Viability Checking
			{
				if(tile.occupyingPiece == null)
				{
					add(tile);
					if(kingFound)
						continue;
					pathToKing.add(tile.toString());
				}
				else
				{
					if(tile.occupyingPiece.isWhite != this.isWhite) {
						add(tile);
						blocked = true;
						if(kingFound)
							continue;
						pathToKing.add(tile.toString());
						if(tile.occupyingPiece instanceof King) {
							kingFound = true;
							if(x < 7 && y > 0)
								kingRetreat = game.board[x+1][y-1];
						}
					}
					else { //Blocked by ally piece, no point in searching for pathtoking
						threat.add(tile.toString());
						break;
					}
				}
			}
			else //Standard moves found, just establishing path to king here
			{
				if(kingFound)
					break;
				if(tile.occupyingPiece != null)
				{
					if(tile.occupyingPiece.isWhite == this.isWhite)
						break;
					pathToKing.add(tile.toString());
					if(tile.occupyingPiece instanceof King) {
						kingFound = true;
						if(x < 7 && y > 0)
							kingRetreat = game.board[x+1][y-1];
					}
				}
				else
					pathToKing.add(tile.toString());
			}
		}
		
		x = start.posX;
		y = start.posY;
		blocked = false;
		if(!kingFound)
			pathToKing.clear();
		while(x > 0 && y < 7)
		{
			x--;
			y++;
			Tile tile = game.board[x][y];
			if(!blocked)//Standard Move Viability Checking
			{
				if(tile.occupyingPiece == null)
				{
					add(tile);
					if(kingFound)
						continue;
					pathToKing.add(tile.toString());
				}
				else
				{
					if(tile.occupyingPiece.isWhite != this.isWhite) {
						add(tile);
						blocked = true;
						if(kingFound)
							continue;
						pathToKing.add(tile.toString());
						if(tile.occupyingPiece instanceof King) {
							kingFound = true;
							if(x > 0 && y < 7)
								kingRetreat = game.board[x-1][y+1];
						}
					}
					else { //Blocked by ally piece, no point in searching for pathtoking
						threat.add(tile.toString());
						break;
					}
				}
			}
			else //Standard moves found, just establishing path to king here
			{
				if(kingFound)
					break;
				if(tile.occupyingPiece != null)
				{
					if(tile.occupyingPiece.isWhite == this.isWhite)
						break;
					pathToKing.add(tile.toString());
					if(tile.occupyingPiece instanceof King) {
						kingFound = true;
						if(x > 0 && y < 7)
							kingRetreat = game.board[x-1][y+1];
					}
				}
				else
					pathToKing.add(tile.toString());
			}
		}
		if(!kingFound)
			pathToKing.clear();
	}
}
