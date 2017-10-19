import org.junit.Test;
import static org.junit.Assert.*;

public class TestGame {

	@Test
	public void testConstructor() {
		Game game = new Game();
		assertNotEquals(null, game);
		assertEquals(Status.ACTIVE, game.getStatus());
		assertNotEquals(null, game.getLog());
		assertNotEquals(null, game.getBoard());
	}
	
	@Test
	public void testGameLogic() {
		
	}
	
	@Test
	public void testGettersSetters() {
		Game game = new Game();
		
		assertEquals(Status.ACTIVE, game.getStatus());
		game.setStatus(Status.INACTIVE);
		assertEquals(Status.INACTIVE, game.getStatus());
		
		assertEquals(null, game.getCurrentPlayer());
		User user = new User();
		game.setCurrentPlayer(user);
		assertEquals(user, game.getCurrentPlayer());
		
		assertEquals(null, game.getCurrentColor());
		game.setCurrentColor(Color.BLACK);
		assertEquals(Color.BLACK, game.getCurrentColor());
		assertNotEquals(Color.RED, game.getCurrentColor());
		
		assertNotEquals(null, game.getPlayers());
		User user2 = new User();
		game.setPlayers(user, user2);
		assertTrue(game.getPlayers().contains(user));
		assertTrue(game.getPlayers().contains(user2));
		
		assertNotEquals(null, game.getLog());
		Log log = new Log();
		game.setLog(log);
		assertEquals(log, game.getLog());
		
		assertNotEquals(null, game.getBoard());
		game.setBoard();
		//setBoard() is tested in TestBoard.java, as it just calls
		//resetBoard().	
	}
	
}