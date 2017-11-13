package controller;

import java.util.ArrayList;

import model.Game;
import model.Invitation;
import model.Log;
import model.Profile;
import model.User;
import client.Client;

public class Controller {

	public static void main(String[] args){
		Controller c = new Controller();
		boolean bool = c.checkEmailPW("spencerlofing@gmail.com", "password");
		System.out.println(c.registerEmailPW("hello@gmail.com", "slofadope", "pass"));
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
		String result = client.sendQuery("SELECT * FROM user WHERE email='" + email + "' and password='" + password + "'");
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
		String emailResult = client.sendQuery("SELECT * FROM user WHERE email='" + email + "'");
		System.out.println("Result = " + emailResult);
		String[] emailResultSplit = emailResult.split("\\|");
		System.out.println("length " + emailResultSplit.length);
		boolean uniqueEmail = (emailResult.equals(""));
		String nicknameResult = client.sendQuery("SELECT * FROM user WHERE username='" + nickname + "'");
		String[] nicknameResultSplit = nicknameResult.split("\\|");
		boolean uniqueNickname = (nicknameResult.equals(""));
		if (uniqueEmail && uniqueNickname) {
			client.sendQuery("INSERT INTO user (username, email, password) VALUES ('" + nickname + "', '" + email + "', '" + password + "')");
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
		return null;
	}
	
	/**
	 * @return all registered users in the system.
	 * need to update user class to do this
	 */
	public static ArrayList<User> getRegisteredUsers(){
		String result = client.sendQuery("SELECT * FROM user");
		String[] resultArray = result.split("|");
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
	public static ArrayList<Game> getGames(User user){
		//String result = client.sendQuery("SELECT * FROM game WHERE
		return null;
	}
	
	/**
	 * Taking in some user, returns the full fleshed-out profile for that user.
	 * @param user
	 * @return Profile object for some user, including history/etc.
	 */
	public static Profile getProfile(User user){
		return null;
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
		return null;
	}
	
	/**
	 * For some game indexed by gameID, update its state with the one given.
	 * @param state
	 */
	public static void updateGameState(String gameID, String state) {
		
	}
	
	/**
	 * For some game, add it to the database.
	 * @param creator
	 * @return
	 */
	public static void addGame(Game game) {

	}
	

	/**
	 * For some game in the database, update the fields in the DB to match what is given in "game".
	 * @param game
	 */
	public static void updateGameEntry(Game game) {
		
	}
	
	/**
	 * Add invitation to the database.
	 * @param invitation
	 */
	public static void addInvitation(Invitation invitation){
		
	}
	
	/**
	 * Update some invitation in the system with the information provided by the new invitation.
	 * This is also where one would close an invitation.
	 * @param invitation
	 */
	public static void updateInvitation(Invitation invitation) {
		
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
