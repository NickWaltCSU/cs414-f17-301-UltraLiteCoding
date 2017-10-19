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
	
	public Board(ArrayList<Tile> _tiles, ArrayList<Token> _tokens, ArrayList<Token> _graveyard) {
		tokens = _tokens;
		tiles = _tiles;
		graveyard = _graveyard;
		presetBoard();
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
		for(int y=4; y>0; y--) {
			for(int x=1; x<9; x++) {
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
	
	public void presetBoard() {
		for(int i=0; i<tokens.size(); i++) {
			tiles.get(i).setToken(tokens.get(i));
		}
	}
	
	public void moveToGraveyard(Token token) {
		graveyard.add(token);
		tokens.remove(token);
		for(int i=0; i<tiles.size(); i++) {
			if(tiles.get(i).getToken().equals(token)) {
				tiles.get(i).setToken(null);
			}
		}
	}

	public void printBoard() {
		int i = 0;
		for(int y=1; y<5; y++) {
			for(int x=1; x<9; x++) {
				if(tiles.get(i).getToken() == null) {
					System.out.print("   ");
				}else if(!tiles.get(i).getToken().isFaceUp()){
					System.out.print("## ");
				}else {
					System.out.print(tiles.get(i).getToken().abbreviate() + " ");
				}
				i++;
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printGraveyard() {
		System.out.print("Graveyard: ");
		for(int i=0; i<graveyard.size(); i++) {
			System.out.print(graveyard.get(i).abbreviate() + " ");
		}
		System.out.println();
	}
}
