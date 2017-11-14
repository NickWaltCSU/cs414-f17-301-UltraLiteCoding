package controller;

import model.Color;
import model.Game;
import model.User;

import java.util.Arrays;

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
			username = username.substring(0,username.length()-1);
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
		String result = client.sendQuery("1;SELECT id FROM game WHERE game.userCreator='" + user.getUsername() + "' OR game.userOther='" + user.getUsername() + "';");
		
		if(result.equals("")) {
			return new String[] {"No active games."};
		}
		
		//rows broken up by |, columns broken up by 
		String[] rows = result.split("\\|");
		
		String[] output = new String[rows.length];
		
		int counter = 0;
		for(String row : rows) {
			String otherUser = client.sendQuery("1;SELECT userCreator FROM game WHERE game.userCreator<>'" + user.getUsername() + "';");
			if(otherUser.equals("")) {
				otherUser = client.sendQuery("1;SELECT userOther FROM game WHERE game.userOther<>'" + user.getUsername() + "';");
			}
			row += " - " + otherUser;
			output[counter] = row;
			counter++;
		}
		
		return output;
	}
	
	public static Game getGame(String gameID) {
		Game game = new Game();
		
		String state = client.sendQuery("1;SELECT state FROM game WHERE game.id='" + gameID + "';");
		String userCreator = client.sendQuery("1;SELECT userCreator FROM game WHERE game.id='" + gameID + "';");
		String userOther = client.sendQuery("1;SELECT userOther FROM game WHERE game.id='" + gameID + "';");
		String creatorColor_ = client.sendQuery("1;SELECT creatorColor FROM game WHERE game.id='" + gameID + "';");
		
		String logID = client.sendQuery("1;SELECT logID FROM game WHERE game.id='" + gameID + "';");
		
		game.setBoardWithColor(state);
		Color creatorColor = Color.RED;
		if(creatorColor_.equals("B")) {
			creatorColor = Color.BLACK;
		}
		game.setCreatorColor(creatorColor);
		
		if(game.getCurrentColor() == creatorColor) {
			game.setCurrentPlayer(userCreator);
		}else {
			game.setCurrentPlayer(userOther);
		}
		
		return game;
	}

	public static String[] getUsers() {
		String result = client.sendQuery("1;SELECT username FROM user;");
		String[] output = result.split("\\|");
		return output;
	}
	
	/**
	 * Parses some invitation, in the format of "INVITATIONID - INVITATION_SENDER", returning the INVITATIONID
	 * @param invitation
	 * @return
	 */
	public static String parseInvitation(String invitation) {
		invitation = invitation.substring(0, invitation.indexOf('-')-1);
		
		return invitation;
	}
	
	public static String[] getInvites(User user) {
		//array of "InvitationID - sender Nickname"
		String result = client.sendQuery("1;SELECT id FROM invitation WHERE invitation.userReceiver='" + user.getUsername() + "';");
				
		if(result.equals("")) {
			return new String[] {"No invitations."};
		}
		
		//rows broken up by |, columns broken up by 
		String[] rows = result.split("\\|");
		
		String[] output = new String[rows.length];
		
		int counter = 0;
		for(String row : rows) {
			String otherUser = client.sendQuery("1;SELECT userSender FROM invitation WHERE invitation.id='" + row + "';");
			
			//trimming the hanging | off of the name
			otherUser  = otherUser.substring(0, otherUser.length()-1);
			
			row += " - " + otherUser;
			output[counter] = row;
			counter++;
		}
		
		return output;
	}
	
	public static String getProfile(String nickname) {
		//nickname : win loss ratio, \nlog1: \nlog2: \n...
		double winLossRatio = 0.0, wins = 0.0, losses = 0.0;
		String logs = "";
		
		//need all logs for some user:
			//look through all games with userCreator or userOther of nickname, getting the logID's of those games
			//use those logID's to get the logs associated with them
		String logIDs_ = client.sendQuery("1;SELECT logID FROM game WHERE game.userCreator='" + nickname 
											+ "' OR game.userOther='" + nickname + "';");
								
		if(logIDs_.equals("")) {
			return nickname + " : 0.0";
		}
		
		String[] logIDs = logIDs_.split("\\|");
		
		for(String log : logIDs) {
			String result = client.sendQuery("1;SELECT * FROM log WHERE log.id='" + log + "';");
			String[] values = result.split(",");
			
			if(values[3].equals(nickname)) {//index out of bounds
				wins++;
			}else {
				losses++;
			}
			logs += "LogID: " + values[0] + ", Start Time: " + values[1] + ", End Time: " + values[2] + ", Winner: " + values[3] + ", Loser: " + values[4];
		}
		
		winLossRatio = wins/losses;
		String output = nickname + " : " + winLossRatio;
		output += logs;
		return output;
	}
	
	public static void createInvitation(String sender_nickname, String recipient_nickname) {
        client.sendQuery("2;INSERT INTO invitation (userSender, userReceiver) VALUES ('" + sender_nickname + "', '" + recipient_nickname + "');");	
	}
	
	private static String createGame(String creator_nickname, String other_nickname) {
        String startTime = client.sendQuery("1;SELECT NOW()");
        client.sendQuery("2;INSERT INTO log (startTime) VALUES ('" + startTime + "')");
        String logID = client.sendQuery("1;SELECT LAST_INSERT_ID()");
        Game game = new Game();
        client.sendQuery("2;INSERT INTO game (state, logID, userCreator, userOther) VALUES ('" + game.getBoardWithColor() + "', '" + logID + "', '" + creator_nickname + "', '" + other_nickname + "')");
        
        return client.sendQuery("1;SELECT gameID FROM game WHERE state='" + game.getBoardWithColor() + "' AND logID='" + logID + "';");
	}
	
	/**
	 * 
	 * @param invitationID
	 * @return gameID
	 */
	public static String acceptInvitation(String invitationID) {
		//accepts it, closes it, also creates the game
		String invitation = client.sendQuery("1;SELECT userSender, userReceiver FROM invitation WHERE id='" + invitationID + "';");
                
		//trim the hanging pipe ('|')
		invitation = invitation.substring(0, invitation.length()-1);
		
        String[] invitationArray = invitation.split(",");
        String sender = invitationArray[0];
        String receiver = invitationArray[1];
        client.sendQuery("2;DELETE FROM invitation WHERE id='" + invitationID + "';");
        return createGame(sender, receiver);
	}
	
	public static void rejectInvitation(String invitationID) {
		//delete invitation
        client.sendQuery("2;DELETE FROM invitation WHERE id='" + invitationID + "'");
	}
	
	public static void updateGame(Game game) {
	    client.sendQuery("2;UPDATE game SET state='" + game.getBoardWithColor() + "' WHERE id='" + game.getGameID() + "'");
        client.sendQuery("2;UPDATE game SET creatorColor='" + game.getCreatorColor() + "' WHERE id='" + game.getGameID() + "'");
        if(game.isOver()) {
            String endTime = client.sendQuery("1;SELECT NOW()");
            client.sendQuery("2;UPDATE log SET endTime=" + endTime);
            client.sendQuery("2;UPDATE log SET userWinner='" + game.getWinningPlayer());
            client.sendQuery("2;UPDATE log SET userLoser='" + game.getLosingPlayer()); 
        }
	}
	
}
