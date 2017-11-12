package model;

public class Invitation {

	private User sender, reciever;
	private Status status;
	private Game game;
	
	public Invitation(User sender, User reciever, Game game) {
		this.sender = sender;
		this.reciever = reciever;
		this.game = game;
		status = Status.ACTIVE;
	}
	
	public void accepted() {
		status = Status.INACTIVE;
	}
	
}
