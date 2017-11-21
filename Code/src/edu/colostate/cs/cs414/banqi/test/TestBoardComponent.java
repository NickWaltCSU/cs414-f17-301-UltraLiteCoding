package edu.colostate.cs.cs414.banqi.test;

import edu.colostate.cs.cs414.banqi.model.Type;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sun.glass.events.MouseEvent;

import edu.colostate.cs.cs414.banqi.model.Game;
import edu.colostate.cs.cs414.banqi.userInterface.BoardComponent;

public class TestBoardComponent {

	@Test
	public void testConstructor() {
		Game game = new Game();
		String user = "bfinn";
		BoardComponent b = new BoardComponent(game, user);
		assertNotEquals(null, b);
	}

	
}
