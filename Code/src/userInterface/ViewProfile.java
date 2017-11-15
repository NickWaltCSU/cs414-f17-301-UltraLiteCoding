package userInterface;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.User;
import javax.swing.JTextPane;

import controller.Controller;

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
						Controller.createInvitation(user1, user2);
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
}
