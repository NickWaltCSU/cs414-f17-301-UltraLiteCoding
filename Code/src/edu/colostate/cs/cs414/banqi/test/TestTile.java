package edu.colostate.cs.cs414.banqi.test;
import org.junit.Test;

import edu.colostate.cs.cs414.banqi.model.Color;
import edu.colostate.cs.cs414.banqi.model.Tile;
import edu.colostate.cs.cs414.banqi.model.Token;
import edu.colostate.cs.cs414.banqi.model.Type;

import static org.junit.Assert.*;

public class TestTile {

	@Test
	public void testConstructor() {
		Tile tile = new Tile(1,1);
		assertNotEquals(null, tile);
	}
	
	@Test
	public void testGettersSetters() {
		Tile tile = new Tile(5,5);
		assertArrayEquals(new int[]{5,5}, tile.getPosition());
		tile.setToken(new Token(Type.SOLDIER, Color.BLACK));
		assertEquals("B1", tile.getToken().abbreviate());
	}
}