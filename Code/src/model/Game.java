package model;
import java.util.ArrayList;

public class Game {

	private Status status;
	private User currentPlayer;
	private Color currentColor;
	private ArrayList<User> players;
	private Log log;
	private Board board;
	
	public Game() {
		status = Status.ACTIVE;
		log = new Log();
		board = new Board();
		players = new ArrayList<User>();
	}
	
	public Game(String _board) {
		status = Status.ACTIVE;
		log = new Log();
		board = new Board(_board);
		players = new ArrayList<User>();
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status _status) {
		status = _status;
	}
	
	public User getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setCurrentPlayer(User player) {
		currentPlayer = player;
	}
	
	public Color getCurrentColor() {
		return currentColor;
	}
	
	public void setCurrentColor(Color color) {
		currentColor = color;
	}
	
	public ArrayList<User> getPlayers(){
		return players;
	}
	
	public void setPlayers(User player1, User player2) {
		players.clear();
		players.add(player1);
		players.add(player2);
	}
	
	public Log getLog() {
		return log;
	}
	
	public void setLog(Log _log) {
		log = _log;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setBoard() {
		board.resetBoard();
	}
	
	//Game actions and logic starts here
	public void switchPlayer() {
		if(currentPlayer == players.get(0)) {
			currentPlayer = players.get(1);
		}else if(currentPlayer == players.get(1)) {
			currentPlayer = players.get(0);
		}else {
			System.out.println("currentPlayer is not one of this game's players");
		}
		
		if(currentColor == Color.RED) {
			currentColor = Color.BLACK;
		}else if(currentColor == Color.BLACK) {
			currentColor = Color.RED;
		}
	}
	
	public void flipToken(int x, int y) {
		if(!board.getToken(x,y).isFaceUp()) {
			board.getToken(x,y).flipToken();
		}else {
			System.out.println("Token is already face-up!");
		}
	}
	
	public boolean moveToken(int startX, int startY, int endX, int endY) {
		Token token = board.getToken(startX, startY);
		if(!token.isFaceUp()) {
			return false;
		}
		if(isValidMove(startX, startY, endX, endY)) {
			if(board.getToken(endX, endY) == null) {
				board.getTile(endX, endY).setToken(token);
				board.getTile(startX, startY).setToken(null);
				return true;
			}else {
				Token token2 = board.getToken(endX, endY);
				if(isValidAttack(token, token2)) {
					board.moveToGraveyard(token2);
					board.getTile(endX, endY).setToken(token);
					board.getTile(startX, startY).setToken(null);
					return true;
				}else return false;
			}
		}else return false;
	}
	
	public boolean isValidMove(int startX, int startY, int endX, int endY) {
		Token token = board.getToken(startX, startY);
//		if(token.getType() == Type.CANNON) {
//			//TODO Deal with cannon movement here
//		}else {
			//Make sure the correct player is the one making the move here!
			//Also make sure they are attempting to move the correct piece!
			if(board.getToken(endX, endY) == null) {
				if(endX < 1 || endX > 8 || endY < 1 || endY > 4) {
					return false;
				}else if(Math.abs(endX-startX) > 1) {
					return false;
				}else if(Math.abs(endY-startY) > 1) {
					return false;
				}else {
					if(Math.abs(endX-startX) == 1) {
						if(Math.abs(endY-startY) != 0) {
							return false;
						}else return true;
					}else if(Math.abs(endY-startY) == 1) {
						if(Math.abs(endX-startX) != 0) {
							return false;
						}else return true;
					}
				}
			}else if(board.getToken(endX, endY) != null) {
				Token token2 = board.getToken(endX, endY);
				if(isValidAttack(token, token2)) {
					board.moveToGraveyard(token2);
					board.getTile(endX, endY).setToken(token);
					board.getTile(startX, startY).setToken(null);
					return true;
				}else return false;
			}
//		}
			return false;
	}
	
	public boolean isValidAttack(Token token, Token token2) {
		if(token.getColor().equals(token2.getColor())) {
			return false;
		}
		
		if(token.getType() == Type.GENERAL) {
			if(token2.getType() == Type.SOLDIER) {
				return false;
			}else return true;
		}else if(token.getType() == Type.SOLDIER) {
			if(token2.getType() == Type.GENERAL) {
				return true;
			}else if(token2.getType() == Type.SOLDIER) {
				return true;
			}else return false;
		}else if(token.getType() == Type.CANNON) {
			return true;
		}else {
			if(token.value() >= token2.value()) {
				return true;
			}else return false;
		}
	}
}
