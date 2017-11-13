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
	
	public User getWinner(User user) {
		return winner;
	}

	public boolean isOver() {
		return ended;
	}
	
	
	public String toString(){
		String outcome;
		if(equals(winner)){
			outcome = "Y";
		}else{
			outcome = "N";
		}
		return " "+outcome;
	}
}
