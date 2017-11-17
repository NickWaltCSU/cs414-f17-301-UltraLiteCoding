package edu.colostate.cs.cs414.banqi.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.colostate.cs.cs414.banqi.model.Game;
import edu.colostate.cs.cs414.banqi.model.User;
import edu.colostate.cs.cs414.banqi.userInterface.GameBoard;

public class TestGameBoard {

	@Test
	public void testConstructor() {
		String user = "bfinn";
		Game game = new Game();
		GameBoard g = new GameBoard(game, user);
		assertNotEquals(null, g);
	}

}
