package userInterface;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import model.Game;

import javax.swing.JButton;

public class GameBoard {

	private JFrame frame;
	private Game game;
	private String user;

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

	/**
	 * Create the application.
	 */
	public GameBoard(Game aGame, String aUser) {
		this.user=aUser;
		this.game=aGame;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Demo");
		frame.setBounds(100, 100, 850, 939);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//code from: https://stackoverflow.com/questions/17815033/how-to-change-java-icon-in-a-jframe
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
		
		BoardComponent board = new BoardComponent(game, user);
		board.setBounds(0, 0, board.getBoardWidth()+2, board.getBoardHeight()*2+22);
		frame.getContentPane().add(board);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(10, 835, 97, 25);
		frame.getContentPane().add(btnCancel);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setToolTipText("In Banqi it is common to forfit if you can see no way of winning.");
		btnQuit.setBounds(705, 835, 97, 25);
		frame.getContentPane().add(btnQuit);
		
	}
	
	private String gameName(){
		return "";//game.getPlayers().get(0).getProfile().getNickname()+" vs. "+game.getPlayers().get(1).getProfile().getNickname();
	}

}
