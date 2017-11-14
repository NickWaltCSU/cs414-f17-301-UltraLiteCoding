package model;

import java.util.ArrayList;

public class Profile {

	private String nickname;
	private ArrayList<Log> history;
	private double winLossRatio = 0.0;

	double wins = 0.0, losses = 0.0;
	
	public Profile(String nickname, ArrayList<Log> history, double winLossRatio) {
		this.nickname = nickname;
		this.history = history;
		this.winLossRatio = winLossRatio;
	}
	
	public Profile(String nickname, ArrayList<Log> history) {
		this.nickname = nickname;
		this.history = history;
		computeWLR();
	}
	
	public String toString(){
		computeWLR();
		String profileString = null;
		profileString+= nickname+"\n";
		profileString+=this.wins+" Wins "+this.losses+" losses "+this.winLossRatio+" win/loss ratio\n";
		
		//add logs
		for(int i = 0; i<this.history.size(); i++){
			profileString+=this.history.get(i).toString();
		}
		
		
		return profileString;
	}
	
	public void addEntry(Log log){
		history.add(log);
		computeWLR();
	}
	
	public void computeWLR() {
		for(Log log : history) {
			
			if(!log.isOver()) {
				continue;
			}
			
			//if(log.getWinner()) {
			//	this.wins++;
			//}else {
				this.losses++;
			//}
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
