package test;
import org.junit.Test;

import model.Color;
import model.Tile;
import model.Token;
import model.Type;

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