package edu.colostate.cs.cs414.banqi.model;


import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class AI {
	
	Color color;
	
	public AI(Color color) {
		this.color = color;
	}
	
	
	
	public int[] makeFirstMove_temp(String state) {
		Random random = new Random();
		
		int randomX2;
		int randomY2;
		int randomX1;
		int randomY1;
		do{
		randomX1 = random.nextInt(8 + 1 - 1) + 1;
		int deltaX = random.nextInt(1 + 1 - -1) + -1;
		randomY1 = random.nextInt(4 + 1 - 1) + 1;
		int deltaY = random.nextInt(1 + 1 - -1) + -1;
		randomX2 = randomX1 + deltaX;
		randomY2 = randomY1 + deltaY;
		
		}while((randomX2>=1&&randomX2<=8)&&(randomY2>=1&&randomY2<=4));
		
		
		
		
		return new int[]{randomX1, randomY1, randomX2, randomY2};
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * This is the opposite method to getXY. It takes some XY and converts it to the indices needed for field.
	 * @param x
	 * @param y
	 * @return the index
	 */
	private int getIndex(int x, int y) {
		int index = -1;
		//the indices in the board:
			//0:1,4 1:2,4 2:3,4 ... 7:8,4
			//8:1,3 ... 15:8,3
			//16:1,2 2,2 3,2 4,2 ... 23:8,2 
			//24:1,1 2,1 3,1 ... 31:8,1
		if(y == 1) {
			index = 24;
		}else if(y == 2) {
			index = 16;
		}else if(y == 3) {
			index = 8;
		}else if(y == 4) {
			index = 0;
		}
		
		return index+(x-1);
	}
	
	/**
	 * The tokens on the field, as in validMoves, are indexed by values 0-31. However, the moves need their x/y coordinates.
	 * This converts some index to some token on the field into X,Y coordinates (i.e.; a move).
	 * @elaborating A field is a string array of all tokens on the field in the order from top-left to bottom-right, reading up-left bottom-down.
	 * @param index Some value from 0-31.
	 * @return the X,Y coordinates known from the index of some token in the field String[].
	 */
	private int[] getXY(int index) {
		//getting x
		int x = index % 8;
		
		//getting y
		int y = -1;
		
		if(index <= 7) {
			y = 4;
		}
		
		if(index >= 7) {
			y = 3;
		}
		
		if(index >= 15) {
			y = 2;
		}
		
		if(index >= 23) {
			y = 1;
		}
		
		//it is x+1 because the board is indexed at 1 and not 0
		return new int[] {x+1, y};
	}
	
	private boolean isOnBoard(int x, int y) {
		if(x<1 || x>8) {
			return false;
		}else if(y<1 || y>4) {
			return false;
		}else return true;
	}
	
	private boolean correctColor(char thisLetter, char otherLetter) {
		Color thisColor = null;
		Color otherColor = null;
		
		if(thisLetter == 'R') {
			thisColor = Color.RED;
		}else if(thisLetter == 'B') {
			thisColor = Color.BLACK;
		}else System.err.println("Error checking color!");
		
		if(otherLetter == 'R') {
			otherColor = Color.RED;
		}else if(otherLetter == 'B') {
			otherColor = Color.BLACK;
		}else System.err.println("Error checking color!");
		
		if(thisColor == color) {
			if(otherColor == thisColor) {
				return false;
			}else return true;
		}else return false;
	}
	
	/**
	 * For some given string state, returns a list of all moves possible. A move is an integer array of 4 numbers: {x1, y1, x2, y2}.
	 * @param state = "FIELD . GRAVEYARD"
	 * @return An array of all integer arrays, each of which represents one move.
	 */
	public int[][] validMoves(String state){
		ArrayList<int[]> moves = new ArrayList<int[]>();
		
		//first, split the field/graveyard and then the tokens themselves
		String[] splitState = state.split(" . ");
		String field_raw = splitState[0];
		String[] field = field_raw.split(" ");
		
		//then, add the moves of flipping all tokens
		System.out.println("Valid Flips:");
		for(int c=0;c<field.length;c++) {
			String token = field[c];
			if(token.charAt(2) == ('D')) {
				int[] xy = getXY(c);
				moves.add(new int[] {xy[0], xy[1], xy[0], xy[1]});
				System.out.println("[" + xy[0] + "," + xy[1] + " | " + xy[0] + "," + xy[1] + "]");
			}
		}
		
		System.out.println("Valid Moves:");
		for(int i=0; i<field.length; i++) {
			String token = field[i];
			
			if(token.charAt(2) == ('U')) {
				int[] xy = getXY(i);
				int x = xy[0];
				int y = xy[1];
				
				//Cannon
				//TODO
				
				//Up
				if(isOnBoard(x,y+1)) {
					if(field[getIndex(x,y+1)].charAt(0) == 'X') {
						moves.add(new int[] {x, y, x, y+1});
						System.out.println("[" + x + "," + y + " | " + x + "," + (y+1) + "]");
					}else if(correctColor(token.charAt(0), field[getIndex(x,y+1)].charAt(0))) {
						if(field[getIndex(x,y+1)].charAt(1) <= token.charAt(1)) {
							moves.add(new int[] {x, y, x, y+1});
							System.out.println("[" + x + "," + y + " | " + x + "," + (y+1) + "]");
						}	
					}
				}
				//Down
				if(isOnBoard(x,y-1)) {
					if(field[getIndex(x,y-1)].charAt(0) == 'X') {
						moves.add(new int[] {x, y, x, y-1});
						System.out.println("[" + x + "," + y + " | " + x + "," + (y-1) + "]");
					}else if(correctColor(token.charAt(0), field[getIndex(x,y-1)].charAt(0))) {
						if(field[getIndex(x,y-1)].charAt(1) <= token.charAt(1)) {
							moves.add(new int[] {x, y, x, y-1});
							System.out.println("[" + x + "," + y + " | " + x + "," + (y-1) + "]");
						}	
					}
				}
				//Left
				if(isOnBoard(x-1,y)) {
					if(field[getIndex(x-1, y)].charAt(0) == 'X') {
						moves.add(new int[] {x, y, x-1, y});
						System.out.println("[" + x + "," + y + " | " + (x-1) + "," + y + "]");
					}else if(correctColor(token.charAt(0), field[getIndex(x-1,y)].charAt(0))) {
						if(field[getIndex(x-1, y)].charAt(1) <= token.charAt(1)) {
							moves.add(new int[] {x, y, x-1, y});
							System.out.println("[" + x + "," + y + " | " + (x-1) + "," + y + "]");
						}	
					}
				}
				//Right
				if(isOnBoard(x+1,y)) {
					if(field[getIndex(x+1, y)].charAt(0) == 'X') {
						moves.add(new int[] {x, y, x+1, y});
						System.out.println("[" + x + "," + y + " | " + (x+1) + "," + y + "]");
					}else if(correctColor(token.charAt(0), field[getIndex(x+1,y)].charAt(0))) {
						if(field[getIndex(x+1, y)].charAt(1) <= token.charAt(1)) {
							moves.add(new int[] {x, y, x+1, y});
							System.out.println("[" + x + "," + y + " | " + (x+1) + "," + y + "]");
						}	
					}
				}
			}
		}
		
		//then, for each face up piece on the board:
			//look at all places to which it can move
				//(meaning an opposite colored token of a lower power level or a special circumstance)
			//add that move to moves
		
		
		//then turn out data into an actual output as expected
		int[][] output = new int[moves.size()][4];
		int counter = 0;
		for(int[] move : moves) {
			output[counter] = move;
			counter++;
		}
		return output;
	}
	
	/**
	 * For some given move, and some given state, it makes the move on that state and returns the new state created as a result.
	 * If it cannot make t	he given move it simply returns null.
	 * @param move A move is an integer array of 4 numbers: {x1, y1, x2, y2}.
	 * @param state = "FIELD . GRAVEYARD"
	 * @return null if the move was not valid, the new state created otherwise.
	 */
	public String makeMove(int[] move, String state) {
		String[] field = state.split(" . ")[0].split(" ");
		String graveyard = state.split(" . ")[1];
		int indexFirst = getIndex(move[0], move[1]);
		char firstColor = field[indexFirst].charAt(0);
		int indexSecond = getIndex(move[2], move[3]);
		char secondColor = field[indexSecond].charAt(0);
		//first check that the piece at (move[0], move[1]) exists & is face up
		if(field[indexFirst].equals("XXX") || field[indexFirst].charAt(2) == 'D') {
			return null;
		}
		
		//if we are moving into an empty space, just make the move and return appropriately
		if(field[indexSecond].equals("XXX")) {
			field[indexSecond] = field[indexFirst];
			field[indexFirst] = "XXX";
			String newState = "";
			for(String token : field) {
				newState += token + " ";
			}
			newState += graveyard;
			return newState;
		}
		
		//otherwise, check that the piece at (move[2], move[3]) either doesn't exist, or exists & is face up & is a different color than the piece at (move[0], move[1])
		if(field[indexSecond].charAt(2) == 'D' || firstColor == secondColor) {
			return null;
		}
		
		//check the first piece - if it is a cannon:
		if(field[indexFirst].charAt(1) == '2') {
		//cannon can only hop if there is one token (not one tile) between the pieces
			//so, first token and second token must share either an X value or a Y value
			boolean shareYs = false;
			boolean shareXs = false;
			if(move[0] == move[2]) {
				shareXs = true;
			}
			if(move[1] == move[3]) {
				shareYs = true;
			}
			//then, for all tokens at the X/Y values in between, there must be ONLY ONE that is XXX
			if(!shareXs && shareYs) {
				//from the lowest x to the greatest x, all tokens should be XXX except one - if there is not exactly one, return null
				int x = -1;
				int otherX = -1;
				if(move[0] > move[2]) {
					x= move[0];
					otherX = move[2];
				}else {
					x = move[2];
					otherX = move[0];
				}
				int y = move[1];
				
				boolean singleToken = false;
				while(x > otherX) {
					if(!field[getIndex(x,y)].equals("XXX")) {
						if(singleToken) {
							return null;
						}else {
							singleToken = true;
						}
					}
					x++;
				}
			}else if(shareXs && !shareYs) {
				//from the lowest y to the greatest y, all tokens should be XXX except one - if there is not exactly one, return null
				int y = -1;
				int otherY = -1;
				if(move[1] > move[3]) {
					y = move[1];
					otherY = move[3];
				}else {
					y = move[3];
					otherY = move[1];
				}
				int x = move[0];
				
				boolean singleToken = false;
				while(y > otherY) {
					if(!field[getIndex(x,y)].equals("XXX")) {
						if(singleToken) {
							return null;
						}else {
							singleToken = true;
						}
					}
					y++;
				}
			}else {
				return null;
			}
		}
		//otherwise, make sure that the tokens are within 1 space of each other
		int firstX = move[0];
		int firstY = move[1];
		int secondX = move[2];
		int secondY = move[3];
		if(!(Math.abs(firstX - secondX) == 1 || Math.abs(firstY - secondY) == 1)) {
			return null;
		}
				
		//otherwise, check that the first token is of a higher rank than the second token (unless it is a cannon)
		if(!(field[indexFirst].charAt(1) == '2')) {//if it is not a cannon
			if(field[indexFirst].charAt(1) == '7' && field[indexSecond].charAt(1) == '1') {//if the first token is a general and the second token is a soldier than it cannot take it
				return null;
			}else if(field[indexSecond].charAt(1) == '7' && field[indexFirst].charAt(1) == '1') {//if the first token is a soldier and the second token is a general than it can take it
				//do nothing
			}else if((int) field[indexFirst].charAt(1) > (int) field[indexSecond].charAt(1)) {//check to make the first token has a higher rank
				//do nothing
			}else {
				return null;
			}
		}
					
		//make a newState
		String newState = "";
		//if we still haven't returned null (meaning the move is a valid one), replace the token at (move[2], move[3]) with the token that was at (move[0], move[1])...
		//...and then replace the token at (move[0], move[1]) with XXX
		field[indexSecond] = field[indexFirst];
		field[indexFirst] = "XXX";
		//set everything up
		for(String token : field) {
			newState += token + " ";
		}
		newState += graveyard;
		//then return the newState onto which that move was made
		return newState;
	}
	

	/**
	 * For some given move, and some given state, it calculates the score of that state-move combination for use in traversing the tree later.
	 * @param move A move is an integer array of 4 numbers: {x1, y1, x2, y2}.
	 * @param state = "FIELD . GRAVEYARD"
	 * @return the score of the move-state combination.
	 */
	public Integer calculateScore(int[] move, String state) {
		return null;
	}
	
	public void printBoard(String state) {
		String[] splitState = state.split(" . ");
		String field_raw = splitState[0];
		String[] field = field_raw.split(" ");
		
		int i = 0;
		for(int y=0; y<4; y++) {
			for(int x=0; x<8; x++) {
				System.out.print(field[i] + " ");
				i++;
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		String state = "B1D R1D B2D R5D R3D R1D R5D R7D R3D B1D B6D B5D R1D B4D R2D B1D B2D B1D B3D R2D R1D R6D B7D R4D B4D B3U B5U R6U XXX B1D R4D R1D . B6U";
		AI ai = new AI(Color.RED);
		for(int[] move : ai.validMoves(state)) {
			//System.out.println(Arrays.toString(move));
		}
	}
}