package model;

public class Log {

	private User creator, acceptor, winner, loser;
	
	private double startTime, endTime;
	
	private boolean ended = false;
	
	public Log() {
		//for testing purposes
	}
	
	public Log(User creator, User acceptor, double startTime) {
		this.creator = creator;
		this.acceptor = acceptor;
		this.startTime = startTime;
	}
	
	public void logEndTime(double endTime) {
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
	
	public String getOpponent(User user1){
		if(user1.equals(acceptor)){
			return creator.getNickname();
		}else{
			return acceptor.getNickname();
		}
	}
	
	public String toString(User user){
		String outcome;
		if(user.equals(winner)){
			outcome = "Y";
		}else{
			outcome = "N";
		}
		return getOpponent(user)+" "+outcome;
	}
}
