package chess;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin//(origins="https://aaleiadeh.github.io/")
public class BoardController {
	public Hashtable<Integer, Board> gameTable = new Hashtable<Integer, Board>();
	public int id = 0;
	public Queue<Integer> idQueue = new LinkedList<Integer>();
	@GetMapping("/newgame")
	public synchronized BoardData board()
	{
		Board game = new Board();
		if(idQueue.isEmpty()) {
			game.id = id;
			id++;
		} else {
			game.id = idQueue.poll();
		}
		game.startGame();
		gameTable.put(game.id, game);
		return new BoardData(game.getBoard(), game.id);
	}
	
	@GetMapping("/join")
	public void join(@RequestParam int id)
	{
		Board game = gameTable.get(id);
		if(game == null)
			return;
		synchronized(game) {
			game.joined = true;
			game.notify();
		}
	}
	
	@PostMapping("/end")
	public synchronized void endGame(@RequestBody String id) {
		if(gameTable.remove(Integer.parseInt(id)) != null)
			idQueue.add(Integer.parseInt(id));
	}
	
	@GetMapping("/establish")
	public boolean establish(@RequestParam int id) throws InterruptedException
	{
		Board game = gameTable.get(id);
		if(game == null)
			return false;
		synchronized(game) {
			game.wait(25000);
			if(!game.joined) {
				if(gameTable.remove(id) != null)
					idQueue.add(id);
			}
			return game.joined;
		}
	}
	
	@PostMapping("/send")
	public boolean move(@RequestBody String moveString)
	{
		int x1, y1, x2, y2;
		int id = Integer.parseInt(moveString.substring(4));
		Board game = gameTable.get(id);
		if(game == null)
			return false;
		synchronized(game) {
			game.moveData = moveString.substring(0, 4);
			game.moveSet = true;
			x1 = Character.getNumericValue(moveString.charAt(0)) - 10;
			y1 = Character.getNumericValue(moveString.charAt(1)) - 1;
			x2 = Character.getNumericValue(moveString.charAt(2)) - 10;
			y2 = Character.getNumericValue(moveString.charAt(3)) - 1;
			game.move(game.getBoard()[x1][y1], game.getBoard()[x2][y2]);
			game.notify();
		}
		return game.isCheckmate();
	}
	
	@GetMapping("/getmove")
	public MoveData getMove(@RequestParam int id) throws InterruptedException
	{
		Board game = gameTable.get(id);
		if(game == null)
			return null;
		synchronized(game) {
			if(game.moveSet) {
				game.moveSet = false;
				return new MoveData(game.getBoard(), game.moveData, game.isCheckmate());
			}
			else {
				game.wait(25000);
				if(game.moveSet) {
					game.moveSet = false;
					return new MoveData(game.getBoard(), game.moveData, game.isCheckmate());
				}
				else
					return new MoveData(null, "TIMEOUT", false);
			}
		}
	}
}
