package model;

public class Invitation {

	private User sender, reciever;
	private Status status;
	private Game game;
	private String invitationID;
	
	public Invitation(User sender, User reciever, Game game) {
		this.sender = sender;
		this.reciever = reciever;
		this.game = game;
		status = Status.ACTIVE;
	}
	
	public void accepted() {
		status = Status.INACTIVE;
	}
	
	public void setID(String id) {
		this.invitationID = id;
	}
	
	public String getID() {
		return invitationID;
	}
	
}
