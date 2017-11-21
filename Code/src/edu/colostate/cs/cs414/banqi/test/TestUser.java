package edu.colostate.cs.cs414.banqi.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.colostate.cs.cs414.banqi.model.User;

public class TestUser {

	@Test
	public void testConstructor() {
		String username = "bfinn";
		String password = "test";
		String email = "test@test.com";
		User user = new User(username, email, password);
		assertNotEquals(null, user);
		assertEquals(user.getUsername(), "bfinn");
		assertEquals(user.getEmail(), "test@test.com");
	}

}
