package userInterface;


import model.Game;
import model.User;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.Controller;

import java.awt.event.ActionListener;
import java.util.ArrayList;
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
				
				//need to do more here
				Controller.deregister(activeUser);
				
			}
		});
		btnDeregister.setBounds(310, 215, 110, 25);
		frame.getContentPane().add(btnDeregister);
		
		JButton btnViewProfile = new JButton("View Profile");
		btnViewProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewProfile profWindow = new ViewProfile(activeUser,activeUser);
				profWindow.main(activeUser,activeUser);
			}
		});
		btnViewProfile.setBounds(12, 51, 110, 25);
		frame.getContentPane().add(btnViewProfile);
		
		JButton btnNewButton = new JButton("Create Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

		
						GameBoard.main(null);
						
						//GameBoard game = new GameBoard();
						//game.main(null);
						
						//use getUsers
						
				
			}
		});
		btnNewButton.setBounds(289, 51, 110, 25);
		frame.getContentPane().add(btnNewButton);
		
		
		//String testGames[] = {"Game1","Game2","Game3"};
		JComboBox gamesBox = new JComboBox(Controller.getGames(activeUser));
		//gamesBox.setSelectedIndex(0);
		gamesBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//gameID = whatever was selected in combo box
				Game game = Controller.getGame(gameID);
				GameBoard activeGame = new GameBoard(game);
				activeGame.main(game);
			}
		});
		gamesBox.setBounds(12, 129, 176, 22);
		frame.getContentPane().add(gamesBox);
		
		JComboBox PlayersBox = new JComboBox(Controller.getUsers());
		PlayersBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				//need to do more here
			}
		});
		PlayersBox.setBounds(239, 129, 160, 22);
		frame.getContentPane().add(PlayersBox);
		
		JLabel lblActiveGamesinvites = new JLabel("Active Games/Invites");
		lblActiveGamesinvites.setBounds(12, 104, 160, 16);
		frame.getContentPane().add(lblActiveGamesinvites);
		
		JLabel lblViewPlayerProfiles = new JLabel("View player Profiles");
		lblViewPlayerProfiles.setBounds(239, 104, 160, 16);
		frame.getContentPane().add(lblViewPlayerProfiles);
	}
	
	public User getUser(){
		return activeUser;
	}
	
}
