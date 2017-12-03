package edu.colostate.cs.cs414.banqi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.ComboBoxModel;

import edu.colostate.cs.cs414.banqi.client.Client;
import edu.colostate.cs.cs414.banqi.model.Color;
import edu.colostate.cs.cs414.banqi.model.Game;
import edu.colostate.cs.cs414.banqi.model.User;

public class Controller {
	
	private static Client client = new Client();
    private static final String salt = "pepper";

	public static User login(String email, String password) {
		String result = client.sendQuery("1;SELECT * FROM user WHERE email='" + email + "' and password='" + hashedPassword(password) + "' and active=1");		
		if(result.equals("")) {
			return null;
		}else {
			String username = client.sendQuery("1;SELECT username FROM user WHERE email='" + email + "' and password='" + hashedPassword(password) + "'");
			username = username.substring(0,username.length()-1);
			return new User(username, email, password);
		}
	}

    private static String hashedPassword(String password) {
        try {
            String saltedpassword = salt + password;
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            byte[] hash = digest.digest(saltedpassword.getBytes());
            BigInteger hashInt = new BigInteger(1, hash);
            return hashInt.toString(16);
        }
        catch(NoSuchAlgorithmException nsae) {
            System.err.println(nsae.getMessage());
            return null;
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
		boolean uniqueEmail = (emailResult.equals(""));
		String nicknameResult = client.sendQuery("1;SELECT * FROM user WHERE username='" + nickname + "'");
		boolean uniqueNickname = (nicknameResult.equals(""));
		if (uniqueEmail && uniqueNickname) {
			client.sendQuery("2;INSERT INTO user (username, email, password, active) VALUES ('" + nickname + "', '" + email + "', '" + hashedPassword(password)  + "', '1')");
			return true;
		} 
		else {
			return false;
		}
	}

	public static void deregister(User user) {
		//delete associated invitations
        String queryResult = client.sendQuery("1;SELECT * FROM invitation WHERE userSender='" + user.getUsername() + "' OR userReceiver='" + user.getUsername() + "'");
        String[] invitations = queryResult.split("\\|");
        for(String invitation : invitations) {
            String[] columns = invitation.split(",");
            String id = columns[0];
            client.sendQuery("2;DELETE FROM invitation WHERE id='" + id + "'");
        }
        //update logs
        queryResult = client.sendQuery("1;SELECT * FROM game WHERE userCreator='" + user.getUsername() + "' OR userOther='" + user.getUsername() + "'");
        String[] games = queryResult.split("\\|");
        for(String game : games) {
            String[] columns = game.split(",");
            String logID = columns[2];
            String userCreator = columns[3];
            String userOther = columns[4];
            String opponent = userCreator;
            if(user.getUsername().equals(userCreator)){
                opponent = userOther;
            }
            String endTime = client.sendQuery("1;SELECT NOW()");
            client.sendQuery("2;UPDATE log SET endTime='" + endTime + "', userWinner='" + opponent + "', userLoser='" + user.getUsername() + "' WHERE id='" + logID + "'"); 
        }
        //de-activate user
        client.sendQuery("2;UPDATE user SET active='0' WHERE email='" + user.getEmail() + "';");
	}

    public static void forfeitGame(String username, String gameID) {
        String game = client.sendQuery("1;SELECT * FROM game WHERE id='" + gameID + "'");
        String[] columns = game.split(",");
        String logID = columns[2];
        String userCreator = columns[3];
        String userOther = columns[4];
        String opponent = userCreator;
        if(username.equals(userCreator)){
            opponent = userOther;
        }
        String endTime = client.sendQuery("1;SELECT NOW()");
        client.sendQuery("2;UPDATE log SET endTime='" + endTime + "', userWinner='" + opponent + "', userLoser='" + username + "' WHERE id='" + logID + "'");
    }
    
	public static String[] getGames(User user) {
		//array of "GameID - opponent Nickname"
		String badLogsResult = client.sendQuery("1;SELECT id FROM log WHERE userWinner IS NOT NULL OR userLoser IS NOT NULL;");
		String result = client.sendQuery("1;SELECT id FROM game WHERE game.userCreator='" + user.getUsername() + "' OR game.userOther='" + user.getUsername() + "';");
		String allLogsResult = client.sendQuery("1;SELECT logID FROM game WHERE game.userCreator='" + user.getUsername() + "' OR game.userOther='" + user.getUsername() + "';");

		if(result.equals("")) {
			return new String[] {"No games."};
		}
		
		//now we have all of the logIDs for all finished games, and we have all games for some user.
		//now we check to see (to delete) any game that has a logID the same as a logID in logIDs
		String[] logs = badLogsResult.split("\\|");
		String[] allLogs = allLogsResult.split("\\|");
		//allLogs - logs = allGoodLogs
		List<String> logs_ = new ArrayList<String>(Arrays.asList(logs));
		List<String> allLogs_ = new ArrayList<String>(Arrays.asList(allLogs));
		ArrayList<String> allGoodLogs = new ArrayList<String>();
		for(String log : allLogs_) {
			if(!logs_.contains(log)) {
				allGoodLogs.add(log);
			}
		}
		
		//rows broken up by |, columns broken up by 
		String[] rows = result.split("\\|");
		
		String[] output = new String[rows.length];
		
		int counter = 0;
		for(String row : rows) {			
			String gameID = row;
			String otherUser = client.sendQuery("1;SELECT userCreator FROM game WHERE game.userCreator<>'" + user.getUsername() + "' AND game.id='" + gameID + "';");
			if(otherUser.equals("")) {
				otherUser = client.sendQuery("1;SELECT userOther FROM game WHERE game.userOther<>'" + user.getUsername() + "' AND game.id='" + gameID + "';");
			}
						
			//trimming the pipe ("|")
			otherUser = otherUser.substring(0, otherUser.length()-1);
			
			row += " - " + otherUser;
			output[counter] = row;
			counter++;
		}
		
		
		//we now have a list of games with GameID - otherUser. take each of these gameIDs, and see if the corresponding logID is in allGoodLogs. If it is not, remove it.
		List<String> games = new ArrayList<String>(Arrays.asList(output));
		ArrayList<String> goodGames = new ArrayList<String>();
		for(String game : games) {
			String gameID = game.substring(0, game.indexOf("-")-1);
			gameID = gameID.trim();
			String logID = client.sendQuery("1;SELECT logID FROM game WHERE game.id='" + gameID + "';");
			logID = logID.substring(0, logID.length()-1);
			if(allGoodLogs.contains(logID)) {
				goodGames.add(game);
			}
		}
		
		games = goodGames;
		ArrayList<String> outputGames = new ArrayList<String>();
		
		//games now represents all good games. still need to add - your turn or not
		for(String game : games) {
			boolean yourTurn = false;
			
			//for this user - it is yourTurn if the currentColor (at the end of state) is the same as the creatorColor if you are the creator (or different if you are not)
			String thisUser = user.getUsername();
			
			String gameID = game.substring(0, game.indexOf("-")-1);

			String creatorUser = client.sendQuery("1;SELECT userCreator FROM game WHERE game.id='" + gameID + "';");
			creatorUser = creatorUser.substring(0, creatorUser.length()-1);
						
			String state = client.sendQuery("1;SELECT state FROM game WHERE game.id='" + gameID + "';");
			state = state.substring(0, state.length()-1);

			String creatorColor = client.sendQuery("1;SELECT creatorColor FROM game WHERE game.id='" + gameID + "';");
			creatorColor = creatorColor.substring(0, creatorColor.length()-1);
			
			String currentColor = state.substring(state.length()-1, state.length());
			
			if(currentColor.equals(creatorColor)) {
				if(thisUser.equals(creatorUser)) {
					yourTurn = true;
				}
			}else {
				if(!(thisUser.equals(creatorUser))) {
					yourTurn = true;
				}
			}
			
			if(yourTurn) {
				game += " - Your Turn";
			}else {
				game += " - Not Your Turn";
			}
			outputGames.add(game);
		}
		
		games = outputGames;
		
		//order outputGames by YourTurn then NotYourTurn.
		outputGames = new ArrayList<String>();
		for(String game : games) {
			if(!(game.contains(" - Not Your Turn"))) {
				outputGames.add(0, game);
			}else {
				outputGames.add(outputGames.size(), game);
			}
		}
		
		games = outputGames;
		
		String[] finalOutput = new String[games.size()];
		for(int c=0;c<finalOutput.length;c++) {
			finalOutput[c] = games.get(c);
		}
		
		if(finalOutput.length == 0) {
			return new String[]{"No games."};
		}
		
		return finalOutput;
	}
	
	public static Game getGame(String gameID) {
		Game game = new Game();
		
		game.setGameID(gameID);
		
		String state = client.sendQuery("1;SELECT state FROM game WHERE game.id='" + gameID + "';");
		String userCreator = client.sendQuery("1;SELECT userCreator FROM game WHERE game.id='" + gameID + "';");
		String userOther = client.sendQuery("1;SELECT userOther FROM game WHERE game.id='" + gameID + "';");

		state = state.substring(0, state.length()-1);
		userCreator = userCreator.substring(0, userCreator.length()-1);
		userOther = userOther.substring(0, userOther.length()-1);
		
		System.out.println(state);
		
		game.setBoardWithColor(state);
		
		game.setCreatorColor(Color.RED);
		
		game.setPlayers(userCreator, userOther);
		
		if(game.getCurrentColor() == Color.RED) {
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
			
			result = result.substring(0, result.length()-1);
			
			String[] values = result.split(",");
			
			if(values[3].equals(nickname)) {//index out of bounds
				wins++;
			}else if(values[4].equals(nickname)){
				losses++;
			}
			logs += "\n\nLogID: " + values[0] + "\n\tStart Time: " + values[1] + "\n\tEnd Time: " + values[2] + "\n\tWinner: " + values[3] + "\n\tLoser: " + values[4];
		}
		
		if(losses == 0) {
			winLossRatio = wins;
		}else if(wins == 0){
			winLossRatio = -1 * losses;
		}else {
			winLossRatio = wins/losses;
		}
		String output = nickname + " : WLR=" + winLossRatio;
		output += logs;		
		return output;
	}
	
	public static void createInvitation(String creatorColor, String state, String sender_nickname, String recipient_nickname) {
        client.sendQuery("2;INSERT INTO invitation (userSender, userReceiver, state, creatorColor) VALUES ('" + sender_nickname + "', '" + recipient_nickname + "', '" + state + "', '" + creatorColor + "');");	
	}
	
	private static String createGame(String creator_nickname, String other_nickname, String state, String creatorColor) {
        String startTime = client.sendQuery("1;SELECT NOW()");
        client.sendQuery("2;INSERT INTO log (startTime) VALUES ('" + startTime + "');");
        
        String logID = client.sendQuery("1;SELECT LAST_INSERT_ID()");
        logID = logID.substring(0, logID.length()-1);
        
        Game game = new Game();           
        
        game.setBoardWithColor(state);
        if(creatorColor.equals("B")) {
        	game.setCreatorColor(Color.BLACK);
        	game.setCurrentColor(Color.RED);
        }else {
        	game.setCreatorColor(Color.RED);
        	game.setCurrentColor(Color.BLACK);
        }
        
        String query = "2;INSERT INTO game (state, logID, userCreator, userOther, creatorColor) VALUES ('" + game.getBoardWithColor() + "', '" + logID + "', '" + creator_nickname + "', '" + other_nickname + "', '" + creatorColor + "');";
        client.sendQuery(query);
        
        return client.sendQuery("1;SELECT game.id FROM game WHERE state='" + game.getBoardWithColor() + "' AND logID='" + logID + "';");
	}
	
	/**
	 * 
	 * @param invitationID
	 * @return gameID
	 */
	public static String acceptInvitation(String invitationID) {
		//accepts it, closes it, also creates the game
		String invitation = client.sendQuery("1;SELECT userSender, userReceiver, state, creatorColor FROM invitation WHERE id='" + invitationID + "';");
		
		//trim the hanging pipe ('|')
		invitation = invitation.substring(0, invitation.length()-1);
		
        String[] invitationArray = invitation.split(",");
        String sender = invitationArray[0];
        String receiver = invitationArray[1];
        String state = invitationArray[2];
        String creatorColor = invitationArray[3];
        client.sendQuery("2;DELETE FROM invitation WHERE id='" + invitationID + "';");
        return createGame(sender, receiver, state, creatorColor);
	}
	
	public static void rejectInvitation(String invitationID) {
		//delete invitation
        client.sendQuery("2;DELETE FROM invitation WHERE id='" + invitationID + "'");
	}
	
	public static void updateGame(Game game) {
	    client.sendQuery("2;UPDATE game SET state='" + game.getBoardWithColor() + "' WHERE id='" + game.getGameID() + "';");
        client.sendQuery("2;UPDATE game SET creatorColor='" + game.getCreatorColorAsString() + "' WHERE id='" + game.getGameID() + "';");
        if(game.isOver()) {
            String endTime = client.sendQuery("1;SELECT NOW()");
            client.sendQuery("2;UPDATE log SET endTime='" + endTime + "';");
            client.sendQuery("2;UPDATE log SET userWinner='" + game.getWinningPlayer() + "';");
            client.sendQuery("2;UPDATE log SET userLoser='" + game.getLosingPlayer() + "';"); 
        }
	}
	
}
