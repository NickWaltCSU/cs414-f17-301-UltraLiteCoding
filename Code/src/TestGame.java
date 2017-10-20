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
		
		//Testing loadBoard - which also helps with testing of everything else below.
		Game game = new Game("B1U B2D XXX XXX XXX XXX XXX XXX "
						   + "R1U XXX XXX XXX XXX XXX XXX XXX "
						   + "B1U B7U R3U XXX XXX XXX XXX XXX "
						   + "R1U XXX XXX XXX XXX XXX XXX XXX ");
		
		//If these are failing, it is because the board is not being loaded properly.
		assertNotEquals(null, game.getBoard().getToken(1, 1));
		assertNotEquals(null, game.getBoard().getToken(1, 2));
		assertNotEquals(null, game.getBoard().getToken(1, 3));
		assertNotEquals(null, game.getBoard().getToken(1, 4));
		assertNotEquals(null, game.getBoard().getToken(2, 2));
		assertNotEquals(null, game.getBoard().getToken(2, 4));
		assertNotEquals(null, game.getBoard().getToken(3, 2));
		
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
		// check that it has been flipped. We then try to flip it again;
		// a token shouldn't be able to be flipped again once face-up.
		assertFalse(game.getBoard().getToken(2, 4).isFaceUp());
		game.flipToken(2, 4);
		assertTrue(game.getBoard().getToken(2, 4).isFaceUp());
		game.flipToken(2, 4);
		assertTrue(game.getBoard().getToken(2, 4).isFaceUp());
		
		
		//We then use moveToken() to move some token into the tiles around it.
	//B1U B2U XXX XXX XXX XXX XXX XXX
	//R1U XXX XXX XXX XXX XXX XXX XXX
	//B1U B7U R3U XXX XXX XXX XXX XXX
	//R1U XXX XXX XXX XXX XXX XXX XXX
		Token token = game.getBoard().getToken(2, 2);
		
		game.moveToken(2, 2, 3, 2);
		assertEquals(token, game.getBoard().getToken(3, 2));
		assertEquals(null, game.getBoard().getToken(2, 2));		
	//B1U B2U XXX XXX XXX XXX XXX XXX
	//R1U XXX XXX XXX XXX XXX XXX XXX
	//B1U XXX B7U XXX XXX XXX XXX XXX
	//R1U XXX XXX XXX XXX XXX XXX XXX
		assertEquals(null, game.getBoard().getToken(2, 3));
		token = game.getBoard().getToken(1, 3);
		game.moveToken(1, 3, 2, 3);
		assertEquals(token, game.getBoard().getToken(2, 3));
		assertEquals(null, game.getBoard().getToken(1, 3));
	//B1U B2U XXX XXX XXX XXX XXX XXX
	//XXX R1U XXX XXX XXX XXX XXX XXX
	//B1U XXX B7U XXX XXX XXX XXX XXX
	//R1U XXX XXX XXX XXX XXX XXX XXX
		token = game.getBoard().getToken(1, 4);
		game.moveToken(1, 4, 1, 3);
		assertEquals(token, game.getBoard().getToken(1, 3));
		assertEquals(null, game.getBoard().getToken(1, 4));
	//XXX B2U XXX XXX XXX XXX XXX XXX
	//B1U R1U XXX XXX XXX XXX XXX XXX
	//B1U XXX B7U XXX XXX XXX XXX XXX
	//R1U XXX XXX XXX XXX XXX XXX XXX
		token = game.getBoard().getToken(1, 3);
		
		game.moveToken(1, 3, 2, 3);
		assertEquals(token, game.getBoard().getToken(2, 3));
		assertEquals(null, game.getBoard().getToken(1, 3));
	//XXX B2U XXX XXX XXX XXX XXX XXX
	//XXX B1U XXX XXX XXX XXX XXX XXX
	//B1U XXX B7U XXX XXX XXX XXX XXX
	//R1U XXX XXX XXX XXX XXX XXX XXX
		token = game.getBoard().getToken(1, 1);
		game.moveToken(1, 1, 1, 2);
		assertEquals(token, game.getBoard().getToken(1, 2));
		assertEquals(null, game.getBoard().getToken(1, 1));
	//XXX B2U XXX XXX XXX XXX XXX XXX
	//XXX B1U XXX XXX XXX XXX XXX XXX
	//R1U XXX B7U XXX XXX XXX XXX XXX
	//XXX XXX XXX XXX XXX XXX XXX XXX
		//Check that save board returns the correct output - including the graveyard!
		//This assumes that the graveyard is printed in the order in which tokens are entered into it -
		// I'm pretty sure the order here is correct.
		assertEquals("XXX B2U XXX XXX XXX XXX XXX XXX "
				   + "XXX B1U XXX XXX XXX XXX XXX XXX "
				   + "R1U XXX B7U XXX XXX XXX XXX XXX "
				   + "XXX XXX XXX XXX XXX XXX XXX XXX , R3U R1U B1U ", game.getBoard().saveBoard());
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