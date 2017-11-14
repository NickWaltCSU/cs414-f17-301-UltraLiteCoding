package controller;

import model.Game;
import model.User;

import javax.swing.ComboBoxModel;

import client.Client;

public class Controller {
	
	private static Client client = new Client();
	
	public static User login(String email, String password) {
		String result = client.sendQuery("1;SELECT * FROM user WHERE email='" + email + "' and password='" + password + "'");
		if(result.equals("")) {
			return null;
		}else {
			String username = client.sendQuery("1;SELECT username FROM user WHERE email='" + email + "' and password='" + password + "'");
			return new User(username, email, password);
		}
	}
	
	/**
	 * For some user trying to register, it adds them as a user if their information is unique, otherwise it returns false.
	 * @param email
	 * @param nickname
	 * @param password
	 * @return
	 */
	public static boolean registerEmailPW(String email, String nickname, String password) {
		String emailResult = client.sendQuery("1;SELECT * FROM user WHERE email='" + email + "'");
        //System.out.println("email result " + emailResult);
		boolean uniqueEmail = (emailResult.equals(""));
		String nicknameResult = client.sendQuery("1;SELECT * FROM user WHERE username='" + nickname + "'");
        //System.out.println("nickname result " + nicknameResult);
		boolean uniqueNickname = (nicknameResult.equals(""));
		if (uniqueEmail && uniqueNickname) {
			client.sendQuery("2;INSERT INTO user (username, email, password) VALUES ('" + nickname + "', '" + email + "', '" + password + "')");
			return true;
		} 
		else {
			return false;
		}
	}

	public static void deregister(User user) {
		
	}

	public static String[] getGames(User user) {
		return null;
	}
	
	public static Game getGame(String gameID) {
		return null;
	}

	public static String[] getUsers() {
		return null;
	}
}
