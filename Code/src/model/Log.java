package model;

public class Log {

	private User creator, acceptor, winner, loser;
	
	private String startTime, endTime;
	
	private boolean ended = false;
	
	public Log() {
		//for testing purposes
	}
	
	public Log(User creator, User acceptor, String startTime) {
		this.creator = creator;
		this.acceptor = acceptor;
		this.startTime = startTime;
	}
	
	public void logEndTime(String endTime) {
		this.endTime = endTime;
		ended = true;
	}
	
	public void logOutcome(User winner, User loser) {
		this.winner = winner;
		this.loser = loser;
		ended = true;
	}
	
	public boolean isWinner(User user) {
		return user == winner;
	}

	public boolean isOver() {
		return ended;
	}
}
