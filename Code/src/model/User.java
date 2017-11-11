package model;

import java.util.ArrayList;

public class User {
	
	private String email, password;
	private Profile profile;
	private ArrayList<Game> games = new ArrayList<Game>();
	private ArrayList<Invitation> invitations = new ArrayList<Invitation>();
	
	public User() {
		//beaware that this constructor for User is used in testGettersSetters()
		//in TestGame.java - if you change this constructor, that
		//testing method may be broken. If it is, please fix it :) - Nicholas
	}
	
	public Invitation createInvitation() {
		return null;
	}
	
	public void acceptInvitation() {
		
	}
	
	public Game createGame() {
		return null;
	}
	
	public void playGame() {
		
	}
	
	public void viewProfile() {
		
	}
	
	public void quitGame() {
		
	}
	
}
