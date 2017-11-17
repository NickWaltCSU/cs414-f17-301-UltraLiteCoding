package edu.colostate.cs.cs414.banqi.test;
import org.junit.Test;

import edu.colostate.cs.cs414.banqi.model.Board;
import edu.colostate.cs.cs414.banqi.model.Tile;
import edu.colostate.cs.cs414.banqi.model.Token;

import static org.junit.Assert.*;

public class TestBoard {

	@Test
	public void testConstructor() {
		//Not much to test here other than that the constructor worked.
		Board board = new Board();
		assertNotEquals(null, board);
	}
	
	@Test
	public void testGetToken() {
		//The constructor automatically populates the board with pieces
		//so I simply need to check the tiles to see what piece is there.
		//Because the pieces are randomly assigned tiles, there is no tile
		//in particular that needs to be checked for some piece.
		Board board = new Board();
		Token token = board.getToken(2, 2);
		assertNotEquals(null, token);
		token = board.getToken(-1, -1);
		assertEquals(null, token);
		token = board.getToken(40, 40);
		assertEquals(null, token);
	}

	@Test
	public void testGetTile() {
		//This tests that on the board created in the constructor,
		//there exists some tile (or doesn't exist some tile) at
		//the positions where tiles should/shouldn't exist.
		Board board = new Board();
		Tile tile = board.getTile(2, 2);
		assertNotEquals(null, tile);
		tile = board.getTile(-1, -1);
		assertEquals(null, tile);
		tile = board.getTile(40, 40);
		assertEquals(null, tile);
	}
	
	@Test
	public void testResetBoard() {
		//First, this testing method creates the board and flips
		//or removes some of the tokens.
		//It then calls resetBoard, which means that all pieces should
		//now be hidden & present (not removed).
		Board board = new Board();
		board.getToken(1, 1).flipToken();
		assertEquals(true, board.getToken(1, 1).isFaceUp());
		
		board.moveToGraveyard(board.getToken(3, 3));
		assertEquals(null, board.getToken(3,3));
		
		//Printing the board allows for us to see that the piece was
		//indeed flipped and the other piece removed. Of course, we've
		//already checked for that above. Still, it is nice to see.
		board.printBoard();

		board.printGraveyard();
		
		board.resetBoard();
		assertEquals(false, board.getToken(1, 1).isFaceUp());
	}
	
}