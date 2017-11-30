package edu.colostate.cs.cs414.banqi.model;

import java.util.Random;

public class AI {
	
	public AI() {
		
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
	
	/**
	 * For some given string state, returns a list of all moves possible. A move is an integer array of 4 numbers: {x1, y1, x2, y2}.
	 * @param state = "FIELD . GRAVEYARD"
	 * @return An array of all integer arrays, each of which represents one move.
	 */
	public int[][] validMoves(String state){
		return null;
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
	
	
}