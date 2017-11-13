package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;

import model.User;
import javax.swing.JTextPane;

public class ViewProfile {

	private JFrame frame;
	private User user;

	/**
	 * Launch the application.
	 */
	public static void main(User mUser) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewProfile window = new ViewProfile(mUser);
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
	public ViewProfile(User aUser) {
		this.user=aUser;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 644, 457);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextPane profileText = new JTextPane();
		profileText.setText(null);
		profileText.setBounds(12, 13, 446, 384);
		frame.getContentPane().add(profileText);
	}
}
