package test;
import org.junit.Test;

import model.Color;
import model.Status;
import model.Token;
import model.Type;

import static org.junit.Assert.*;

public class TestToken {

	@Test
	public void testConstructor() {
		Token token = new Token(Type.ADVISOR, Color.BLACK);
		assertNotEquals(null, token);
	}
	
	@Test
	public void testGettersSetters() {
		Token token = new Token(Type.ADVISOR, Color.BLACK);
		
		assertEquals(Type.ADVISOR, token.getType());
		assertEquals(Color.BLACK, token.getColor());
		assertEquals(Status.ACTIVE, token.getStatus());
		token.setStatus(Status.INACTIVE);
		assertEquals(Status.INACTIVE, token.getStatus());
		
		assertFalse(token.isFaceUp());
		token.flipToken();
		assertTrue(token.isFaceUp());
		token.flipToken();
		assertFalse(token.isFaceUp());
		token.flipToken();
	}
	
	@Test
	public void testAbbreviateAndValue() {
		Token token1 = new Token(Type.ADVISOR, Color.BLACK);
		assertEquals("B6", token1.abbreviate());
		assertEquals(6, token1.value());
		
		Token token2 = new Token(Type.ADVISOR, Color.RED);
		assertEquals("R6", token2.abbreviate());
		assertEquals(6, token2.value());
		
		Token token3 = new Token(Type.CHARIOT, Color.BLACK);
		assertEquals("B4", token3.abbreviate());
		assertEquals(4, token3.value());
		
		Token token4 = new Token(Type.HORSE, Color.RED);
		assertEquals("R3", token4.abbreviate());
		assertEquals(3, token4.value());
		
		Token token5 = new Token(Type.GENERAL, Color.RED);
		assertEquals("R7", token5.abbreviate());
		assertEquals(7, token5.value());
	}
	
}