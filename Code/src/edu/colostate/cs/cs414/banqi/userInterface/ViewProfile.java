package edu.colostate.cs.cs414.banqi.userInterface;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import edu.colostate.cs.cs414.banqi.controller.Controller;
import edu.colostate.cs.cs414.banqi.model.*;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class ViewProfile {

	private JFrame frame;
	private String user1;
	private String user2;

	/**
	 * Launch the application.
	 */
	public static void main(String mUser1, String mUser2) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewProfile window = new ViewProfile(mUser1, mUser2);
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
	public ViewProfile(String aUser1, String aUser2) {
		this.user1=aUser1;
		this.user2=aUser2;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(user2);
		frame.setBounds(100, 100, 644, 457);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//code from: https://stackoverflow.com/questions/17815033/how-to-change-java-icon-in-a-jframe
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
		
		JTextArea profileText = new JTextArea();
		profileText.setEditable(false);
		profileText.setLineWrap(true);
		profileText.setText(Controller.getProfile(user2));
		
		profileText.setBounds(12, 13, 444, 384);
		frame.getContentPane().add(profileText);
		
		
		//if(!user1.equals(user2)){
			JButton btnInviteToGame = new JButton("Invite to game.");
			btnInviteToGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.showConfirmDialog(null, "Do you want to invite this person to a game?","Accept", JOptionPane.YES_NO_OPTION)==0){
						
						//have them select the first token to flip
							//do this by generating a board state, and having them flip a token. Reveal what the token was, and then tell them they're now awaiting input
							//from the other user
						Game game = new Game();
						Color flippedColor = null;
						
						//index has the -1 at the end to indicate the off-by-one nature of how i displayed the tokens
						int index = Integer.parseInt(JOptionPane.showInputDialog("Please enter in the number of the token to-be-flipped first:"
								+ "\n25 - 26 - 27 - 28 - 29 - 30 - 31 - 32"
								+ "\n17 - 18 - 19 - 20 - 21 - 22 - 23 - 24"
								+ "\n09 - 10 - 11 - 12 - 13 - 14 - 15 - 16"
								+ "\n01 - 02 - 03 - 04 - 05 - 06 - 07 - 08"))-1;
						
						int[] xy = getXY(index);
						game.flipToken(xy[0], xy[1]);
						
						flippedColor = game.getBoard().getToken(xy[0], xy[1]).getColor();
						
						if(flippedColor == Color.RED) {
							game.setCurrentColor(Color.BLACK);
						}else {
							game.setCurrentColor(Color.RED);
						}
						
						String state = game.getBoardWithColor();						
												
						String creatorColor = "R";
						if(flippedColor == Color.BLACK) {
							creatorColor = "B";
						}
						
						Controller.createInvitation(creatorColor, state, user1, user2);
					}
				}
			});
			btnInviteToGame.setBounds(470, 13, 144, 25);
			frame.getContentPane().add(btnInviteToGame);
			if(user1.equals(user2)){
				btnInviteToGame.setVisible(false);
			}
		//}
	}
	private int[] getXY(int index) {
		//getting x
		int x = index % 8;
		
		//getting y
		int y = -1;
		
		if(index <= 7) {
			y = 4;
		}
		
		if(index > 7) {
			y = 3;
		}
		
		if(index > 15) {
			y = 2;
		}
		
		if(index > 23) {
			y = 1;
		}
		
		//it is x+1 because the board is indexed at 1 and not 0
		return new int[] {x+1, y};
	}
}
