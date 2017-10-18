import java.util.ArrayList;
import java.util.Collections;

public class Board {

	private ArrayList<Token> tokens;
	private ArrayList<Tile> tiles;
	private ArrayList<Token> graveyard;
	
	public Board() {
		tokens = new ArrayList<Token>();
		tiles = new ArrayList<Tile>();
		graveyard = new ArrayList<Token>();
		setBoard();
	}
	
	public Token getToken(int x, int y) {
		for(int i=0; i<tiles.size(); i++) {
			if(tiles.get(i).getPosition()[0] == x) {
				if(tiles.get(i).getPosition()[1] == y) {
					return tiles.get(i).getToken();
				}
			}
		}
		System.out.println("Token not on board");
		return null;
	}
	
	public Tile getTile(int x, int y) {
		for(int i=0; i<tiles.size(); i++) {
			if(tiles.get(i).getPosition()[0] == x) {
				if(tiles.get(i).getPosition()[1] == y) {
					return tiles.get(i);
				}
			}
		}
		System.out.println("Error finding tile");
		return null;
	}
	
	public void createToken(Type type, Color color, int numOfTokens) {
		for(int i=0; i<numOfTokens; i++) {
			tokens.add(new Token(type, color));
		}
	}
	
	public void createAllTokens() {
		Color color = Color.RED;
		Type type = Type.GENERAL;
		createToken(type, color, 1);
		type = Type.ADVISOR;
		createToken(type, color, 2);
		type = Type.ELEPHANT;
		createToken(type, color, 2);
		type = Type.CHARIOT;
		createToken(type, color, 2);
		type = Type.HORSE;
		createToken(type, color, 2);
		type = Type.CANNON;
		createToken(type, color, 2);
		type = Type.SOLDIER;
		createToken(type, color, 5);
		
		color = Color.BLACK;
		type = Type.GENERAL;
		createToken(type, color, 1);
		type = Type.ADVISOR;
		createToken(type, color, 2);
		type = Type.ELEPHANT;
		createToken(type, color, 2);
		type = Type.CHARIOT;
		createToken(type, color, 2);
		type = Type.HORSE;
		createToken(type, color, 2);
		type = Type.CANNON;
		createToken(type, color, 2);
		type = Type.SOLDIER;
		createToken(type, color, 5);
	}
	
	public void createTiles() {
		for(int x=1; x<9; x++) {
			for(int y=1; y<5; y++) {
				tiles.add(new Tile(x, y));
			}
		}
	}
	
	public void setBoard() {
		createAllTokens();
		createTiles();
		Collections.shuffle(tokens);
		for(int i=0; i<tokens.size(); i++) {
			tiles.get(i).setToken(tokens.get(i));
		}
	}
	
	public void resetBoard() {
		tokens.clear();
		tiles.clear();
		createAllTokens();
		createTiles();
		Collections.shuffle(tokens);
		for(int i=0; i<tokens.size(); i++) {
			tiles.get(i).setToken(tokens.get(i));
		}
	}
	
	public void moveToGraveyard(Token token) {
		graveyard.add(token);
	}

	public void printBoard() {
		int i = 0;
		for(int y=1; y<5; y++) {
			for(int x=1; x<9; x++) {
				if(!tiles.get(i).getToken().isFaceUp()) {
					System.out.print("## ");
				}else System.out.print(tiles.get(i).getToken().abbreviate() + " ");
				i++;
			}
			System.out.println();
		}
		System.out.println();
	}
}
