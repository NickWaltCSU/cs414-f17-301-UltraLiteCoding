package userInterface;


import model.User;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Dashboard {

	private JFrame frame;
	private User activeUser;

	/**
	 * Launch the application.
	 */
	public static void main(User mUser) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard window = new Dashboard(mUser);
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
	public Dashboard(User aUser) {
		this.activeUser=aUser;
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
		btnDeregister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(null, "Are you sure you want to deregister?\nAll history will be lost!");
			}
		});
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
		
		
		String testGames[] = {"Game1","Game2","Game3"};
		JComboBox gamesBox = new JComboBox(testGames);
		gamesBox.setSelectedIndex(2);
		gamesBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedGame = (String)gamesBox.getSelectedItem();
				System.out.println(selectedGame);
			}
		});
		gamesBox.setBounds(12, 160, 176, 22);
		frame.getContentPane().add(gamesBox);
		
		JComboBox PlayersBox = new JComboBox();
		PlayersBox.setBounds(239, 160, 160, 22);
		frame.getContentPane().add(PlayersBox);
		
		JLabel lblActiveGamesinvites = new JLabel("Active Games/Invites");
		lblActiveGamesinvites.setBounds(12, 135, 160, 16);
		frame.getContentPane().add(lblActiveGamesinvites);
		
		JLabel lblViewPlayerProfiles = new JLabel("View player Profiles");
		lblViewPlayerProfiles.setBounds(239, 135, 160, 16);
		frame.getContentPane().add(lblViewPlayerProfiles);
	}
}
