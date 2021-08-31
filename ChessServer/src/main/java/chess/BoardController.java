package chess;

import java.util.Hashtable;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
//Todo: Stack structure for ID recycling
@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class BoardController {
	public Hashtable<Integer, Board> gameTable = new Hashtable<Integer, Board>();
	public int id = 0;
	@GetMapping("/newgame")
	public synchronized BoardData board()
	{
		Board game = new Board();
		game.id = id;
		game.startGame();
		gameTable.put(game.id, game);
		id++;
		return new BoardData(game.getBoard(), game.id);
	}
	
	@GetMapping("/join")
	public Tile[][] join(@RequestParam int id)
	{
		Board game = gameTable.get(id);
		synchronized(game) {
			game.notify();
		}
		return game.getBoard();
	}
	
	@PostMapping("/end")
	public void endGame(@RequestBody String id) {
		gameTable.remove(Integer.parseInt(id));
	}
	
	@GetMapping("/establish")
	public void establish(@RequestParam int id) throws InterruptedException
	{
		Board game = gameTable.get(id);
		synchronized(game) {
			game.wait();
			return;
		}
	}
	
	@PostMapping("/send")
	public Tile[][] move(@RequestBody String moveString)
	{
		int x1, y1, x2, y2;
		int id = Integer.parseInt(moveString.substring(4));
		Board game = gameTable.get(id);
		synchronized(game) {
			game.moveData = moveString.substring(0, 4);
			x1 = Character.getNumericValue(moveString.charAt(0)) - 10;
			y1 = Character.getNumericValue(moveString.charAt(1)) - 1;
			x2 = Character.getNumericValue(moveString.charAt(2)) - 10;
			y2 = Character.getNumericValue(moveString.charAt(3)) - 1;
			game.move(game.getBoard()[x1][y1], game.getBoard()[x2][y2]);
			game.notify();
		}
		return game.getBoard();
	}
	
	@GetMapping("/getmove")
	public MoveData getMove(@RequestParam int id) throws InterruptedException
	{
		Board game = gameTable.get(id);
		synchronized(game) {
			game.wait();
			return new MoveData(game.getBoard(), game.moveData);
		}
	}
}
