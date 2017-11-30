package edu.colostate.cs.cs414.banqi.model;

public class AI {
	
	public AI() {
		
	}
	
	public int[] makeFirstMove_temp(String state) {
		return new int[]{1, 1, 1, 1};
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