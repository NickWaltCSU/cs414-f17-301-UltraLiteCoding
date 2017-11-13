package model;

import java.util.ArrayList;

import controller.Controller;

public class User {
	
	private String email, password;
	private Profile profile;
	private ArrayList<Game> games = new ArrayList<Game>();
	private ArrayList<Invitation> invitations = new ArrayList<Invitation>();
	
	public User() {
		//Empty constructor for test purposes
	}
	
	
	public User(String email, String password, Profile profile, ArrayList<Game> games, ArrayList<Invitation> invitations) {
		this.email = email;
		this.password = password;
		this.profile = profile;
		this.games = games;
		this.invitations = invitations;
	}
	
	public User(String email, String password, Profile profile) {
		this.email = email;
		this.password = password;
		this.profile = profile;
	}
	
	public boolean register(String email, String nickname, String password) {
		if(!Controller.registerEmailPW(email, nickname, password)){
			return false;
		}
		
		this.email = email;
		this.password = password;
		this.profile = new Profile(nickname, new ArrayList<Log>(), 0.0, this);
		
		return true;
	}
	
	public boolean login(String email, String password) {
		if(!Controller.checkEmailPW(email, password)) {
			return false;
		}
		
		this.email = email;
		this.password = password;
		
		this.profile = Controller.getProfile(email);
		//this.games= Controller.getGames(profile.getNickname());
		return true;
	}
	
	public ArrayList<Game> getGames(){
		return games;
	}
	
	public ArrayList<Invitation> getInvitations(){
		return invitations;
	}
	
	public void setGames(ArrayList<Game> games){
		this.games = games;
	}
	
	public void setInvitations(ArrayList<Invitation> invitations){
		this.invitations = invitations;
	}
	
	public Profile getProfile(){
		return this.profile;
	}
}
