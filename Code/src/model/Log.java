package model;

public class Log {

	private String creator, acceptor, winner, loser;
	
	private String startTime, endTime;
	
	private String logID;
	
	private boolean ended = false;
	
	public Log() {
		//for testing purposes
	}
	
	public Log(String creator, String acceptor, String startTime) {
		this.creator = creator;
		this.acceptor = acceptor;
		this.startTime = startTime;
	}
	
	public void logEndTime(String endTime) {
		this.endTime = endTime;
		ended = true;
	}
	
	public void setLogID(String logID) {
		this.logID = logID;
	}
	
	public String getLogID() {
		return logID;
	}
	
	public void logOutcome(String winner, String loser) {
		this.winner = winner;
		this.loser = loser;
		ended = true;
	}
	
	public String getWinner(String user) {
		return winner;
	}

	public boolean isOver() {
		return ended;
	}
	
    public String getCreator() {
        return creator;
    }

    public String getAcceptor() {
        return acceptor;
    }

    public String getWinner() {
        return winner;
    }

    public String getLoser() {
        return loser;
    }
	
    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
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
