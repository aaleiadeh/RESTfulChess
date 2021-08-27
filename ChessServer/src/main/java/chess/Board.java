package chess;

import java.util.ArrayList;
import java.util.HashSet;

import pieces.*;
public class Board 
{
	public boolean isWhiteTurn = true;
	public Tile[][] board = new Tile[8][8];
	public int id;
	public String moveData;
	public ArrayList<Piece> white;
	public ArrayList<Piece> black;
	public HashSet<String> whiteThreat;
	public HashSet<String> blackThreat;
	public Piece whiteKing;
	public Piece blackKing;
	public Board()
	{
		for(int y = 0; y < 8; y++)
		{
			for(int x = 0; x < 8; x++)
			{
				board[x][y] = new Tile(x,y);
			}
		}
		white = new ArrayList<Piece>(16);
		black = new ArrayList<Piece>(16);
		whiteThreat = new HashSet<String>();
		blackThreat = new HashSet<String>();
	}
	public void drawBoard()
	{	
		boolean black = false;
		for(int y = 7; y > -1; y--)
		{
			for(int x = 0; x < 8; x++)
			{
				if(board[x][y].occupyingPiece != null)
				{
					System.out.print(board[x][y].occupyingPiece.getName()+" ");
				}
				else
				{
					if(black)
					{
						System.out.print("## ");
					}
					else
					{
						System.out.print("   ");
					}
				}
				black = !black;
				if(x == 7)//When the final piece in the row has been printed, proceed to print row number
				{
					System.out.println(y+1);
				}
			}
			black = !black;
		}
		System.out.println(" a  b  c  d  e  f  g  h");
	}
	public void startGame()//fills board
	{
		board[0][7].occupyingPiece = new Rook(false, this); //Set up black backrow
		board[0][7].occupyingPiece.occupiedTile = board[0][7];
		black.add(board[0][7].occupyingPiece);
		
		board[1][7].occupyingPiece = new Knight(false, this);
		board[1][7].occupyingPiece.occupiedTile = board[1][7];
		black.add(board[1][7].occupyingPiece);
		
		board[2][7].occupyingPiece = new Bishop(false, this);
		board[2][7].occupyingPiece.occupiedTile = board[2][7];
		black.add(board[2][7].occupyingPiece);
		
		board[3][7].occupyingPiece = new Queen(false, this);
		board[3][7].occupyingPiece.occupiedTile = board[3][7];
		black.add(board[3][7].occupyingPiece);
		
		board[4][7].occupyingPiece = new King(false, this);
		board[4][7].occupyingPiece.occupiedTile = board[4][7];
		black.add(board[4][7].occupyingPiece);
		blackKing = board[4][7].occupyingPiece;
		
		board[5][7].occupyingPiece = new Bishop(false, this);
		board[5][7].occupyingPiece.occupiedTile = board[5][7];
		black.add(board[5][7].occupyingPiece);
		
		board[6][7].occupyingPiece = new Knight(false, this);
		board[6][7].occupyingPiece.occupiedTile = board[6][7];
		black.add(board[6][7].occupyingPiece);
		
		board[7][7].occupyingPiece = new Rook(false, this);
		board[7][7].occupyingPiece.occupiedTile = board[7][7];
		black.add(board[7][7].occupyingPiece);
		
		
		
		board[0][0].occupyingPiece = new Rook(true, this);//Set up white backrow
		board[0][0].occupyingPiece.occupiedTile = board[0][0];
		white.add(board[0][0].occupyingPiece);
		
		board[1][0].occupyingPiece = new Knight(true, this);
		board[1][0].occupyingPiece.occupiedTile = board[1][0];
		white.add(board[1][0].occupyingPiece);
		
		board[2][0].occupyingPiece = new Bishop(true, this);
		board[2][0].occupyingPiece.occupiedTile = board[2][0];
		white.add(board[2][0].occupyingPiece);
		
		board[3][0].occupyingPiece = new Queen(true, this);
		board[3][0].occupyingPiece.occupiedTile = board[3][0];
		white.add(board[3][0].occupyingPiece);
		
		board[4][0].occupyingPiece = new King(true, this);
		board[4][0].occupyingPiece.occupiedTile = board[4][0];
		white.add(board[4][0].occupyingPiece);
		whiteKing = board[4][0].occupyingPiece;
		
		board[5][0].occupyingPiece = new Bishop(true, this);
		board[5][0].occupyingPiece.occupiedTile = board[5][0];
		white.add(board[5][0].occupyingPiece);
		
		board[6][0].occupyingPiece = new Knight(true, this);
		board[6][0].occupyingPiece.occupiedTile = board[6][0];
		white.add(board[6][0].occupyingPiece);
		
		board[7][0].occupyingPiece = new Rook(true, this);
		board[7][0].occupyingPiece.occupiedTile = board[7][0];
		white.add(board[7][0].occupyingPiece);
		
		for(int i = 0; i < 8; i++)//Set up all pawns
		{
			board[i][1].occupyingPiece = new Pawn(true, this);
			board[i][1].occupyingPiece.occupiedTile = board[i][1];
			white.add(board[i][1].occupyingPiece);
			board[i][6].occupyingPiece = new Pawn(false, this);
			board[i][6].occupyingPiece.occupiedTile = board[i][6];
			black.add(board[i][6].occupyingPiece);
		}
		updateAllTiles();
	}
	public boolean move(Tile start, Tile end)
	{
		if(start == null || end == null)
			return false;
		if(start.occupyingPiece == null || !start.occupyingPiece.viableTiles.contains(end))
			return false;
		if(end.occupyingPiece != null)
			end.occupyingPiece.occupiedTile = null;
		end.occupyingPiece = start.occupyingPiece;
		start.occupyingPiece = null;
		end.occupyingPiece.actionsTaken++;
		end.occupyingPiece.occupiedTile = end;
		updateAllTiles();
		//drawBoard();
		isWhiteTurn = !isWhiteTurn;
		return true;
	}
	
	private void updateAllTiles()
	{
		whiteThreat.clear();
		blackThreat.clear();
		for(int y = 0; y < 8; y++)
		{
			for(int x = 0; x < 8; x++)
			{
				if(board[x][y].occupyingPiece != null)
				{
					if(!(board[x][y].occupyingPiece instanceof King)) {
						board[x][y].occupyingPiece.updateTiles(board[x][y].occupyingPiece.occupiedTile);
						board[x][y].occupyingPiece.updatejson();
					}
				}
			}
		}
		if(whiteKing.occupiedTile != null) {
			whiteKing.updateTiles(whiteKing.occupiedTile);
			whiteKing.updatejson();
		}
		if(blackKing.occupiedTile != null) {
			blackKing.updateTiles(blackKing.occupiedTile);
			blackKing.updatejson();
		}
	}
	
	public Tile[][] getBoard(){
		return board;
	}
}
