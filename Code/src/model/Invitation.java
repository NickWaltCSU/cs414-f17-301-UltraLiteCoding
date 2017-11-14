package model;

public class Invitation {

	private User sender, reciever;
	private Status status;
	private Game game;
    private String id;
	
	public Invitation(User sender, User reciever, Game game) {
		this.sender = sender;
		this.reciever = reciever;
		this.game = game;
		status = Status.ACTIVE;
	}
	
	public void accepted() {
		status = Status.INACTIVE;
	}

    public void getGameID() {
        return game.getID();
    }

    public void getSender() {
        return sender;
    }

    public void getReceiver() {
        return receiver;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }
	
}
