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
		//In this test method, we create a game, assign it players,
		// set a current player, set a current color (the current
		// players color).
		// We then call switchPlayer() and check that the active player,
		// and the active color, have been swapped.
		Game game = new Game();
		User user = new User();
		User user2 = new User();
		game.setPlayers(user, user2);
		game.setCurrentPlayer(game.getPlayers().get(0));
		game.setCurrentColor(Color.RED);
		
		assertEquals(user, game.getCurrentPlayer());
		game.switchPlayer();
		assertEquals(user2, game.getCurrentPlayer());
		assertEquals(Color.BLACK, game.getCurrentColor());
		
		//We then take some token on the board and flip it, and then
		// check that it has been flipped. We then try to flip it again.
		assertFalse(game.getBoard().getToken(2, 4).isFaceUp());
		game.flipToken(2, 4);
		assertTrue(game.getBoard().getToken(2, 4).isFaceUp());
		game.flipToken(2, 4);
		assertTrue(game.getBoard().getToken(2, 4).isFaceUp());
		
		//We then use moveToken() to move some token into some other
		// EMPTY tile next to it, and then some other tile not next to it.
		// Then we try to move a token into another token, attacking that
		// token.
		
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