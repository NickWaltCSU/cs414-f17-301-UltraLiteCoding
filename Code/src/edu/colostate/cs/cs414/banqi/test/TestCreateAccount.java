package edu.colostate.cs.cs414.banqi.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.colostate.cs.cs414.banqi.userInterface.CreateAccount;

public class TestCreateAccount {

	@Test
	public void testConstructor() {
		String email = "test@test.com";
		String password = "test";
		CreateAccount c = new CreateAccount(email, password);
		assertNotEquals(null, c);
	}

}
