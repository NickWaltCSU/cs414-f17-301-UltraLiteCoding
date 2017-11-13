package model;

import java.util.ArrayList;

public class Profile {

	private String nickname;
	private ArrayList<Log> history;
	private double winLossRatio;
	private User user;
	
	public Profile(String nickname, ArrayList<Log> history, double winLossRatio, User user) {
		this.nickname = nickname;
		this.history = history;
		this.winLossRatio = winLossRatio;
		this.user = user;
	}
	
	public void addEntry(Log log){
		history.add(log);
		computeWLR();
	}
	
	public void computeWLR() {
		double wins = 0.0, losses = 0.0;
		for(Log log : history) {
			
			if(!log.isOver()) {
				continue;
			}
			
			if(log.isWinner(user)) {
				wins++;
			}else {
				losses++;
			}
		}
		
		winLossRatio = wins/losses;
	}
	
	public ArrayList<Log> getHistory(){
		return history;
	}
	
	public double getWinLossRatio() {
		return winLossRatio;
	}
	
	public String getNickname(){
		return this.nickname;
	}
	
}
