package controller;

import java.util.ArrayList;

import model.Game;
import model.Invitation;
import model.Log;
import model.Profile;
import model.User;

public class Controller {

	/**
	 * For some user trying to log in, checkEmailPW verifies that the email & password correlate to a user in the database.
	 * @return whether or not the email-password is a valid combination.
	 */
	public static boolean checkEmailPW(String email, String password) {
		return false;
	}
	
	/**
	 * For some user trying to register, it adds them as a user if their information is unique, otherwise it returns false.
	 * @param email
	 * @param nickname
	 * @param password
	 * @return
	 */
	public static boolean registerEmailPW(String email, String nickname, String password) {
		return false;
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
	 */
	public static ArrayList<User> getRegisteredUsers(){
		return null;
	}
	
	/** Returns all games in which the user given is a participant.
	 * Every game needs to have the appropriate fields filled.
	 * @param user
	 * @return
	 */
	public static ArrayList<Game> getGames(User user){
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