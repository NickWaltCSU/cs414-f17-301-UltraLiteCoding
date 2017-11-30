package edu.colostate.cs.cs414.banqi.userInterface;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;


import edu.colostate.cs.cs414.banqi.controller.Controller;
import edu.colostate.cs.cs414.banqi.model.AI;
import edu.colostate.cs.cs414.banqi.model.Game;

import javax.swing.JButton;

public class GameBoard {

	private JFrame frame;
	private Game game;
	private String user;
	private AI aiPlayer;
	
	private int posX;
	private int posY;
	
	
	
	

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
			aiPlayer = new AI();
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
		
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setToolTipText("Refresh the game screen.");
		btnRefresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				if(game.getGameID().equals("AI")){
					//AI makes move here
					
					String gameState = game.getBoard().saveBoard();
					
					
					int[] moveArray;
					do{
						moveArray = aiPlayer.makeFirstMove_temp("no state");
					}while(!game.moveToken("AI", moveArray[0], moveArray[1], moveArray[2], moveArray[3]));
					
					//board gets refreshed
					GameBoard freshGameBoard = new GameBoard(game, user, (int) frame.getLocation().getX(), (int) frame.getLocation().getY());
					freshGameBoard.main(game, user,  (int) frame.getLocation().getX(), (int) frame.getLocation().getY());
					frame.dispose();
					
				}else{
					String tempID=game.getGameID();
					game = Controller.getGame(tempID);
					GameBoard freshGameBoard = new GameBoard(game, user, (int) frame.getLocation().getX(), (int) frame.getLocation().getY());
					freshGameBoard.main(game, user,  (int) frame.getLocation().getX(), (int) frame.getLocation().getY());
					frame.dispose();
				}
			}
		});
		btnRefresh.setBounds(350, 835, 97, 25);
		frame.getContentPane().add(btnRefresh);
		
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setToolTipText("In Banqi it is common to forfit if you can see no way of winning.");
		btnQuit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				Controller.forfeitGame(user, game.getGameID());
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
