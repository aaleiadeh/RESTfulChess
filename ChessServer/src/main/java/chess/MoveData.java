package chess;

public class MoveData {
	public Tile[][] tiles;
	public String move;
	
	public MoveData(Tile[][] tiles, String move) {
		this.tiles = tiles;
		this.move = move;
	}
}
