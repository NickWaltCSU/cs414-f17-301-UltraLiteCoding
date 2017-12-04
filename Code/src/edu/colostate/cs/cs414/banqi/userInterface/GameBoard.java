package edu.colostate.cs.cs414.banqi.userInterface;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.colostate.cs.cs414.banqi.controller.Controller;
import edu.colostate.cs.cs414.banqi.model.AI;
import edu.colostate.cs.cs414.banqi.model.Color;
import edu.colostate.cs.cs414.banqi.model.Game;

import javax.swing.JButton;

public class GameBoard {

	private JFrame frame;
	private Game game;
	private String user;
	private AI aiPlayer;
	
	private int posX;
	private int posY;
	private static boolean testing= false;
	
	
	//testing AI main
	public static void main(String[] args) {
		Game aiGame= new Game();
		String tUser = "Tester";
		aiGame.setPlayers(tUser, "AI");
		aiGame.setCreatorColor(Color.RED);
		aiGame.setCurrentColor(Color.RED);
		aiGame.setCurrentPlayer(tUser);
		aiGame.setGameID("AI");
		
		testing = true;
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameBoard window = new GameBoard(aiGame, tUser);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	/**
	 * Launch the application.
	 */
	public static void main(Game mGame,String mUser) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameBoard window = new GameBoard(mGame, mUser);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(Game mGame, String mUser, int x, int y) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameBoard window = new GameBoard(mGame, mUser, x, y);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	/**
	 * @wbp.parser.constructor
	 */
	public GameBoard(Game aGame, String aUser) {
		this.user=aUser;
		this.game=aGame;
		posX = 100;
		posY = 100;
		initialize();
	}
	
	public GameBoard(Game aGame, String aUser, int x, int y) {
		this.user=aUser;
		this.game=aGame;
		this.posX=x;
		this.posY=y;
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		if(game.getGameID().equals("AI")){
			aiPlayer = new AI(Color.BLACK);
		}
		frame = new JFrame(game.toString()+"---You are "+game.getPlayerColor(user)+isMyTurn());
		frame.setBounds(posX, posY, 850, 939);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//code from: https://stackoverflow.com/questions/17815033/how-to-change-java-icon-in-a-jframe
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
		
		BoardComponent board = new BoardComponent(game, user);
		board.setBounds(0, 0, board.getBoardWidth()+2, board.getBoardHeight()*2+22);
		frame.getContentPane().add(board);
		
		JButton btnCancel = new JButton("Close");
		btnCancel.setToolTipText("Close window without making a move.");
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				frame.dispose();
			}
		});
		btnCancel.setBounds(10, 835, 97, 25);
		frame.getContentPane().add(btnCancel);
		
		
		
		//Refresh button and AI move button
		JButton btnRefresh;
		if(game.getGameID().equals("AI")){
			btnRefresh = new JButton("Make AI Move");
			btnRefresh.setToolTipText("Click for the AI to make its move.");
		}else{
			btnRefresh = new JButton("Refresh");
			btnRefresh.setToolTipText("Refresh the game screen.");
		}
		btnRefresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				Random random = new Random();
				if(game.getGameID().equals("AI")){
					//AI makes move here
					if(game.getCurrentPlayer().equals("AI")){
							String gameState = game.getBoard().saveBoard();//This is where it gets an active game state, this will throw errors when using the AI test board
						//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
						//helper variables
						int[][] moveArray;
						int randMove;
						
						//get all valid moves
						moveArray = aiPlayer.validMoves(gameState);
						
						//make a random number in index range
						int moveNum = moveArray.length;
						randMove = random.nextInt(moveNum);
						
						//make a random move
						game.moveToken("AI", moveArray[randMove][0], moveArray[randMove][1], moveArray[randMove][2], moveArray[randMove][3]);
						//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
						
						/**replacement instructions for AI method
						 * 
						 * remove or comment out everything between the arrow lines "vvv,^^^"
						 * 
						 * add AI method and make move
						 * eg.
						 * aiMove[] = aiPlayer.smartMove(gameState);
						 * game.moveToken("AI", aiMove[0], aiMove[1], aiMove[2], aiMove[3]);
						 * 
						 */
						
						
						
						
						//board gets refreshed
						GameBoard freshGameBoard = new GameBoard(game, user, (int) frame.getLocation().getX(), (int) frame.getLocation().getY());
						freshGameBoard.main(game, user,  (int) frame.getLocation().getX(), (int) frame.getLocation().getY());
						frame.dispose();
					}
				}else{
					String tempID=game.getGameID();
					game = Controller.getGame(tempID);
					GameBoard freshGameBoard = new GameBoard(game, user, (int) frame.getLocation().getX(), (int) frame.getLocation().getY());
					freshGameBoard.main(game, user,  (int) frame.getLocation().getX(), (int) frame.getLocation().getY());
					frame.dispose();
				}
			}
		});
		btnRefresh.setBounds(350, 835, 125, 25);
		frame.getContentPane().add(btnRefresh);
		
		//Quit
		JButton btnQuit = new JButton("Quit");
		btnQuit.setToolTipText("In Banqi it is common to forfeit if you can see no way of winning.");
		btnQuit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				
				if(game.getGameID().equals("AI")){
					frame.dispose();
				}else{
					if(0==JOptionPane.showConfirmDialog(null, "Are you sure you want to forfeit? This will count as a loss.", "Deregister Confirm.", JOptionPane.YES_NO_OPTION)){
						Controller.forfeitGame(user, game.getGameID());
						frame.dispose();
					}
					
				}
			}
		});
		btnQuit.setBounds(705, 835, 97, 25);
		frame.getContentPane().add(btnQuit);
		
		
	}
	
	private String isMyTurn(){
		if(user.equals(game.getCurrentPlayer())){
			return " It is your turn.";
		}else{
			return " It is not your turn.";
		}
	}
	
	

}
