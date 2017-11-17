package edu.colostate.cs.cs414.banqi.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.colostate.cs.cs414.banqi.userInterface.ViewProfile;

public class TestViewProfile {

	@Test
	public void testConstructor() {
		String user1 = "bfinn";
		String user2 = "max";
		ViewProfile v = new ViewProfile(user1, user2);
		assertNotEquals(null, v);
	}

}
