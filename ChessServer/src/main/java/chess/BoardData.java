package chess;

public class BoardData {
	public Tile[][] tiles;
	public int id;
	
	public BoardData(Tile[][] tiles, int id) {
		this.tiles = tiles;
		this.id = id;
	}
}
