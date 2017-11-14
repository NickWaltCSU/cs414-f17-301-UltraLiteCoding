package model;

import java.util.ArrayList;

import controller.Controller;

public class User {
	
	private String username, email, password;
	
	public User() {
		//Empty constructor for test purposes
	}
	
	
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public boolean register(String email, String nickname, String password) {
		if(!Controller.registerEmailPW(email, nickname, password)){
			return false;
		}
		
		this.email = email;
		this.password = password;
		
		return true;
	}
	
	public boolean login(String email, String password) {
		if(!Controller.checkEmailPW(email, password)) {
			return false;
		}
		
		this.email = email;
		this.password = password;
		
		return true;
	}
}
