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
		client.sendQuery("2;DELETE FROM user WHERE user.email='" + user.getEmail() + "';");
	}

	public static String[] getGames(User user) {
		//array of "GameID - opponent Nickname"
		String result = client.sendQuery("1;SELECT * FROM game WHERE game.userCreator='" + user.getUsername() + "' OR game.userOther='" + user.getUsername() + "';");
		
		//rows broken up by |, columns broken up by 
		String[] rows = result.split("|");
		
		for(String row : rows) {
			
		}
		
		return null;
	}
	
	public static Game getGame(String gameID) {
		return null;
	}

	public static String[] getUsers() {
		return null;
	}
	
	public static String[] getInvites(User user) {
		//array of "InvitationID - sender Nickname"
		return null;
	}
	
	public static String getProfile(String nickname) {
		//returns everything that we need for viewing a profile
		return null;
	}
	
	public static void createInvitation(String sender_nickname, String recipient_nickname) {
		
	}
	
	public static void createGame(String creator_nickname, String other_nickname) {
		
	}
	
	public static void acceptInvitation(String invitationID) {
		//accepts it, closes it, also creates the game

	}
	
	public static void rejectInvitation(String invitationID) {
		//delete invitation
        client.sendQuery("2;DELETE FROM invitation WHERE id='" + invitationID + "'");
	}
	
	public static void updateGame(Game game) {
	    client.sendQuery("2;UPDATE game SET state='" + game.getBoardWithColor() + "' WHERE id='" + game.getGameID() + "'");
        client.sendQuery("2;UPDATE game SET creatorColor='" + game.getCreatorColor() + "' WHERE id='" + game.getGameID() + "'");
	}
	
}
