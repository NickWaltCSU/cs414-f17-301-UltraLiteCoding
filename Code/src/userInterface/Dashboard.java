package userInterface;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dashboard {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard window = new Dashboard();
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
	public Dashboard() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnDeregister = new JButton("Deregister");
		btnDeregister.setBounds(12, 13, 110, 25);
		frame.getContentPane().add(btnDeregister);
		
		JButton btnViewProfile = new JButton("View Profile");
		btnViewProfile.setBounds(12, 51, 110, 25);
		frame.getContentPane().add(btnViewProfile);
		
		JButton btnNewButton = new JButton("Create Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				GameBoard.main(null);
				
				//GameBoard game = new GameBoard();
				//game.main(null);
				
				
			}
		});
		btnNewButton.setBounds(12, 89, 110, 25);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnViewPlayers = new JButton("view players");
		btnViewPlayers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String loginMessage="NO PLAYERS!!!";
				JOptionPane.showMessageDialog(null, loginMessage);
			}
		});
		btnViewPlayers.setBounds(130, 13, 97, 25);
		frame.getContentPane().add(btnViewPlayers);
	}
}
