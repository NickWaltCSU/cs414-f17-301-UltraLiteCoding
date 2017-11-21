package edu.colostate.cs.cs414.banqi.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.colostate.cs.cs414.banqi.model.User;
import edu.colostate.cs.cs414.banqi.userInterface.Dashboard;

public class TestDashboard {

	@Test
	public void testConstructor() {
		User user = new User();
		Dashboard d = new Dashboard(user);
		assertNotEquals(null, d);
	}
	
	public void testGetUser(){
		User user = new User();
		Dashboard d = new Dashboard(user);
		assertEquals(d.getUser(), user);
	}

}
