package chess;

import java.util.ArrayList;
import java.util.HashSet;

import pieces.*;
public class Board 
{
	public Tile[][] board = new Tile[8][8];
	public int id;
	public boolean joined;
	public String moveData;
	public boolean moveSet;
	public ArrayList<Piece> whitePieces;
	public ArrayList<Piece> blackPieces;
	public HashSet<String> whiteThreat;
	public HashSet<String> blackThreat;
	public Piece whiteKing;
	public Piece blackKing;
	public boolean whiteTurn;
	public Board()
	{
		for(int y = 0; y < 8; y++)
		{
			for(int x = 0; x < 8; x++)
			{
				board[x][y] = new Tile(x,y);
			}
		}
		whitePieces = new ArrayList<Piece>(16);
		blackPieces = new ArrayList<Piece>(16);
		whiteThreat = new HashSet<String>();
		blackThreat = new HashSet<String>();
		whiteTurn = true;
		joined = false;
	}
	
	public void startGame()//fills board
	{
		board[0][7].occupyingPiece = new Rook(false, this); //Set up black backrow
		board[0][7].occupyingPiece.occupiedTile = board[0][7];
		blackPieces.add(board[0][7].occupyingPiece);
		
		board[1][7].occupyingPiece = new Knight(false, this);
		board[1][7].occupyingPiece.occupiedTile = board[1][7];
		blackPieces.add(board[1][7].occupyingPiece);
		
		board[2][7].occupyingPiece = new Bishop(false, this);
		board[2][7].occupyingPiece.occupiedTile = board[2][7];
		blackPieces.add(board[2][7].occupyingPiece);
		
		board[3][7].occupyingPiece = new Queen(false, this);
		board[3][7].occupyingPiece.occupiedTile = board[3][7];
		blackPieces.add(board[3][7].occupyingPiece);
		
		board[4][7].occupyingPiece = new King(false, this);
		board[4][7].occupyingPiece.occupiedTile = board[4][7];
		blackPieces.add(board[4][7].occupyingPiece);
		blackKing = board[4][7].occupyingPiece;
		
		board[5][7].occupyingPiece = new Bishop(false, this);
		board[5][7].occupyingPiece.occupiedTile = board[5][7];
		blackPieces.add(board[5][7].occupyingPiece);
		
		board[6][7].occupyingPiece = new Knight(false, this);
		board[6][7].occupyingPiece.occupiedTile = board[6][7];
		blackPieces.add(board[6][7].occupyingPiece);
		
		board[7][7].occupyingPiece = new Rook(false, this);
		board[7][7].occupyingPiece.occupiedTile = board[7][7];
		blackPieces.add(board[7][7].occupyingPiece);
		
		
		
		board[0][0].occupyingPiece = new Rook(true, this);//Set up white backrow
		board[0][0].occupyingPiece.occupiedTile = board[0][0];
		whitePieces.add(board[0][0].occupyingPiece);
		
		board[1][0].occupyingPiece = new Knight(true, this);
		board[1][0].occupyingPiece.occupiedTile = board[1][0];
		whitePieces.add(board[1][0].occupyingPiece);
		
		board[2][0].occupyingPiece = new Bishop(true, this);
		board[2][0].occupyingPiece.occupiedTile = board[2][0];
		whitePieces.add(board[2][0].occupyingPiece);
		
		board[3][0].occupyingPiece = new Queen(true, this);
		board[3][0].occupyingPiece.occupiedTile = board[3][0];
		whitePieces.add(board[3][0].occupyingPiece);
		
		board[4][0].occupyingPiece = new King(true, this);
		board[4][0].occupyingPiece.occupiedTile = board[4][0];
		whitePieces.add(board[4][0].occupyingPiece);
		whiteKing = board[4][0].occupyingPiece;
		
		board[5][0].occupyingPiece = new Bishop(true, this);
		board[5][0].occupyingPiece.occupiedTile = board[5][0];
		whitePieces.add(board[5][0].occupyingPiece);
		
		board[6][0].occupyingPiece = new Knight(true, this);
		board[6][0].occupyingPiece.occupiedTile = board[6][0];
		whitePieces.add(board[6][0].occupyingPiece);
		
		board[7][0].occupyingPiece = new Rook(true, this);
		board[7][0].occupyingPiece.occupiedTile = board[7][0];
		whitePieces.add(board[7][0].occupyingPiece);
		
		for(int i = 0; i < 8; i++)//Set up all pawns
		{
			board[i][1].occupyingPiece = new Pawn(true, this);
			board[i][1].occupyingPiece.occupiedTile = board[i][1];
			whitePieces.add(board[i][1].occupyingPiece);
			board[i][6].occupyingPiece = new Pawn(false, this);
			board[i][6].occupyingPiece.occupiedTile = board[i][6];
			blackPieces.add(board[i][6].occupyingPiece);
		}
		updateAllTiles();
	}
	
	public boolean move(Tile start, Tile end)
	{
		//Enpassant
		if(start.occupyingPiece instanceof Pawn) {
			if(start.posX != end.posX) {
				if(end.occupyingPiece == null) {
					int side = start.occupyingPiece.isWhite ? -1 : 1;
					Tile tile = board[end.posX][end.posY+(1*side)];
					tile.occupyingPiece.occupiedTile = null;
					tile.occupyingPiece = null;
				}
			}
		}
		for(Piece piece : whitePieces) {
			if(piece instanceof Pawn)
				((Pawn) (piece)).enpassantable = false;
		}
		for(Piece piece : blackPieces) {
			if(piece instanceof Pawn)
				((Pawn) (piece)).enpassantable = false;
		}
		if(start.occupyingPiece instanceof Pawn) {
			if(Math.abs(start.posY-end.posY) == 2)//DoubleStep
				((Pawn) start.occupyingPiece).enpassantable = true;
		}
		
		//Castling
		if(start.occupyingPiece instanceof King) {
			int direction = end.posX - start.posX;
			if(direction == 2) {//Right Side Castling
				Tile rightRookTile = board[end.posX+1][end.posY];
				Piece rightRook = rightRookTile.occupyingPiece;
				Tile moveTo = board[end.posX-1][end.posY];
				
				moveTo.occupyingPiece = rightRook;
				rightRookTile.occupyingPiece = null;
				rightRook.occupiedTile = moveTo;
				rightRook.actionsTaken++;
			}
			if(direction == -2) {//Left Side Castling
				Tile leftRookTile = board[end.posX-2][end.posY];
				Piece leftRook = leftRookTile.occupyingPiece;
				Tile moveTo = board[end.posX+1][end.posY];
				
				moveTo.occupyingPiece = leftRook;
				leftRookTile.occupyingPiece = null;
				leftRook.occupiedTile = moveTo;
				leftRook.actionsTaken++;
			}
		}
		
		if(end.occupyingPiece != null)
			end.occupyingPiece.occupiedTile = null;
		end.occupyingPiece = start.occupyingPiece;
		start.occupyingPiece = null;
		end.occupyingPiece.occupiedTile = end;
		end.occupyingPiece.actionsTaken++;
		updateAllTiles();
		whiteTurn = !whiteTurn;
		//drawBoard();
		return true;
	}
	
	public boolean isCheckmate() {
		ArrayList<Piece> allies = whiteTurn ? whitePieces : blackPieces;
		for(Piece piece : allies) {
			if(piece.occupiedTile == null)
				continue;
			if(!(piece.viableTiles.isEmpty()))
				return false;
		}
		return true;
	}
	
	public Tile[][] getBoard(){
		return board;
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
	
	private void drawBoard()
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
}
