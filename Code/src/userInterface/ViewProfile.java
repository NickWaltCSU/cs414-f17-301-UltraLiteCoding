package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.User;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class ViewProfile {

	private JFrame frame;
	private User user1;
	private User user2;

	/**
	 * Launch the application.
	 */
	public static void main(User mUser1, User mUser2) {
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
	public ViewProfile(User aUser1, User aUser2) {
		this.user1=aUser1;
		this.user2=aUser2;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 644, 457);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea profileText = new JTextArea();
		profileText.setEditable(false);
		profileText.setLineWrap(true);
		//profileText.setText(user2.getProfile().toString());
		
		profileText.setBounds(12, 13, 444, 384);
		frame.getContentPane().add(profileText);
		
		
		if(!user1.equals(user2)){
			JButton btnInviteToGame = new JButton("Invite to game.");
			btnInviteToGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane optionPane = new JOptionPane(
					    "Do you want to invite this person to a game?",
					    JOptionPane.QUESTION_MESSAGE,
					    JOptionPane.YES_NO_OPTION);
				}
			});
			btnInviteToGame.setBounds(470, 13, 144, 25);
			frame.getContentPane().add(btnInviteToGame);
		}
	}
}
