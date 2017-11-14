package controller;

import java.util.ArrayList;

import model.Game;
import model.Invitation;
import model.Log;
import model.Profile;
import model.User;
import userInterface.Login;
import client.Client;

public class Controller {

	public static void main(String[] args){
		Controller c = new Controller();
		//System.out.println("hello");
		boolean bool = c.checkEmailPW("spencerlofing@gmail.com", "password");
		System.out.println(c.registerEmailPW("hello@gmail.com", "slofadope", "pass"));
		Login runLogin = new Login();
		runLogin.main(null);
	}
	private static Client client;
	public Controller(){
		client = new Client();
	}
	/**
	 * For some user trying to log in, checkEmailPW verifies that the email & password correlate to a user in the database.
	 * @return whether or not the email-password is a valid combination.
	 */
	public static boolean checkEmailPW(String email, String password) {
		String result = client.sendQuery("1;SELECT * FROM user WHERE email='" + email + "' and password='" + password + "'");
		//String[] resultsplit = result.split("\\|");
		return !(result.equals(""));
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
	
	/**
	 * Returns a profile object (making sure profile.nickname is set) based off of the email provided.
	 * @param email
	 * @return
	 */
	public static Profile getProfile(String email) {
		//Profile p = new Profile(
        return null;
	}
	
	/**
	 * @return all registered users in the system.
	 * need to update user class to do this
	 */
	public static ArrayList<User> getRegisteredUsers(){
		String result = client.sendQuery("1;SELECT * FROM user");
		String[] resultArray = result.split("\\|");
		ArrayList<User> resultList = new ArrayList<User>();
		for(String user : resultArray) {
			String[] userArray = user.split(",");
		}
		return null;
	}
	
	/** Returns all games in which the user given is a participant.
	 * Every game needs to have the appropriate fields filled.
	 * @param user
	 * @return
	 */
	public static ArrayList<Game> getGames(String username){
		String result = client.sendQuery("1;SELECT * FROM game WHERE playerCreator='" + username + "' OR playerOther='" + username + "'");
        String[] gamesArray = result.split("\\|");
        ArrayList<Game> gamesList = new ArrayList<Game>();
        for(String game : gamesArray) {
            String[] gameArray = game.split(",");
            String id = gameArray[0];
            String playerCreator = gameArray[1];
            String playerAcceptor = gameArray[2];
            String state = gameArray[3];
            String logID = gameArray[4];
            Game g = new Game();
            gamesList.add(g);
        }
		return gamesList;
	}
	
	/**
	 * Taking in some user, returns the full fleshed-out profile for that user.
	 * @param user
	 * @return Profile object for some user, including history/etc.
	 */
	public static Profile getProfile(String email){
        String user = client.sendQuery("1;SELECT * FROM user WHERE email='" + email + "'");
        String nickname = user.split(",")[0];
        ArrayList<Log> history = new ArrayList<Log>();
        String logs = client.sendQuery("1;SELECT * FROM log WHERE userCreator='" + nickname + "' OR userAcceptor='" + nickname + "'");    
        String[] logsArray = logs.split("\\|");
        for(String log : logsArray) {
            String[] logArray = log.split("\\|");
            String startTime = logArray[1];
            String endTime = logArray[2];
            String winner = logArray[3];
            String loser = logArray[4]
            String creator = logArray[5];
            String acceptor = logArray[6];
            Log l = new Log(creator, acceptor, startTime)
            l.logEndTime(endTime);
            l.logOutcome(winner, loser);
            history.add(l);
        }
        Profile p = new Profile(nickname, history);
        return p;
	}
	
	/**
	 * Deregisters the user, forfeiting all of their active games (and filling out the logs/etc for them appropriately).
	 * @param user
	 */
	public static void deregister(User user) {
	    	
	}
	
	/**
	 * Returns the game correlating to the given gameID.
	 * @param gameID
	 * @return
	 */
	public static Game getGame(String gameID) {
		String result = client.sendQuery("1;SELECT * FROM game WHERE id='" + gameID + "'");
        String[] gameArray = result.split(",");
        String boardState = gameArray[3];
        Game g = new Game(boardState);
        return g;
	}
	
	/**
	 * For some game indexed by gameID, update its state with the one given.
	 * @param state
	 */
	public static void updateGameState(String gameID, String state) {
		client.sendQuery("2;UPDATE game SET state='" + state + "' WHERE id='" + gameID + "'");
	}
	
	/**
	 * For some game, add it to the database.
	 * @param creator
	 * @return
	 */
	public static void addGame(Game game) {
        //create players corresponding to users
        Log l = game.getLog();
        ArrayList<User> players = game.getPlayers();
        User player1 = players.get(0);
        User player2 = players.get(1);
        client.sendQuery("2;INSERT INTO player (username, color) VALUES ('" + l.getCreator() + "', '" + "'R'");
        String playerCreator = client.sendQuery("1;SELECT LAST_INSERT_ID()").split("\\|")[0];
        client.sendQuery("2;INSERT INTO player (username, color) VALUES ('" + player2.getAcceptor() + "', '" + "'B'");
        String playerOther = client.sendQuery("1;SELECT LAST_INSERT_ID()").split("\\|")[0];
        //initialize log
        Log l = game.getLog();
        client.sendQuery("2;INSERT INTO log (startTime, endTime, userWinner, userLoser, userCreator, userAcceptor) VALUES ('" + l.getStartTime() + "', '" + l.getEndTime() + "', '" + l.getWinner() + "', '" + l.getLoser() + "', '" + l.getCreator() + "', '" + l.getAcceptor() + "'");
        //add game to database with values from object
        String logID = client.sendQuery("1;SELECT LAST_INSERT_ID()").split("\\|")[0];
        client.sendQuery("2;INSERT INTO game (id, playerCreator, playerOther, state, logID) VALUES ('" game.getID() +"', '" + playerCreator + "', '" + playerOther + "', '" + game.getBoard() + "', '" + logID + "')");

	}
	

	/**
	 * For some game in the database, update the fields in the DB to match what is given in "game".
	 * @param game
	 */
	public static void updateGameEntry(Game game) {
		client.sendQuery("2;DELETE FROM game WHERE id='" + game.getID() + "'");
        addGame(game);
	}
	
	/**
	 * Add invitation to the database.
	 * @param invitation
	 */
	public static void addInvitation(Invitation invitation){
	    client.sendQuery("2;INSERT INTO invitation (userSender, userReceiver, gameID) VALUES ('" + invitation.getSender() + "', '" + invitation.getReceiver() + "', '" + invitation.getGameID());
        String inviteID = client.sendQuery("1;SELECT LAST_INSERT_ID()")
        invitation.setID(inviteID);    
	}
	
	/**
	 * Update some invitation in the system with the information provided by the new invitation.
	 * This is also where one would close an invitation.
	 * @param invitation
	 */
	public static void updateInvitation(Invitation invitation) {
		client.sendQuery("2;DELETE FROM invitation WHERE id='" + invitation.getID() + "'");
        addInvitation(invitation);
	}
	
	/**
	 * Returns all invitations for some user.
	 * @param user
	 * @return
	 */
	public static ArrayList<Invitation> getInvitations(User user) {
		return null;
	}
	
	/**
	 * Adds some log to the DB.
	 * @param log
	 */
	public static void addLog(Log log) {
		
	}
	
	/**
	 * Updates some log in the DB.
	 * @param log
	 */
	public static void updateLog(Log log) {
		
	}
}
