package chess;

public class MoveData {
	public Tile[][] tiles;
	public String move;
	public boolean defeat;
	public MoveData(Tile[][] tiles, String move, boolean defeat) {
		this.tiles = tiles;
		this.move = move;
		this.defeat = defeat;
	}
}
