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
		for(int c=0;c<field.length;c++) {
			String token = field[c];
			if(token.charAt(2) == ('D')) {
				int[] xy = getXY(c);
				moves.add(new int[] {xy[0], xy[1], xy[0], xy[1]});
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
	 * If it cannot make the given move it simply returns null.
	 * @param move A move is an integer array of 4 numbers: {x1, y1, x2, y2}.
	 * @param state = "FIELD . GRAVEYARD"
	 * @return null if the move was not valid, the new state created otherwise.
	 */
	public String makeMove(int[] move, String state) {
		return null;
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
	
	public static void main(String[] args) {
		String state = "B1D R1D B2D R5D R3D R1D R5D R7D R3D B1D B6D B5D R1D B4D R2D B1D B2D B1D B3D R2D R1D R6D B7D R4D B4D B3U B5U R6U XXX B1D R4D R1D . B6U";
		AI ai = new AI(Color.RED);
		for(int[] move : ai.validMoves(state)) {
			System.out.println(Arrays.toString(move));
		}
	}
}