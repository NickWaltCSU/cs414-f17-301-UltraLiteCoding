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
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to deregister?\nAll history will be lost!")==0){
					Controller.deregister(activeUser);
				}
			}
		});
		btnDeregister.setBounds(310, 215, 110, 25);
		frame.getContentPane().add(btnDeregister);
		
		JButton btnViewProfile = new JButton("View My Profile");
		btnViewProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewProfile profWindow = new ViewProfile(activeUser,activeUser);
				profWindow.main(activeUser,activeUser);
			}
		});
		btnViewProfile.setBounds(12, 51, 137, 25);
		frame.getContentPane().add(btnViewProfile);
		
		
		//String testGames[] = {"Game1","Game2","Game3"};
		//Controller.getGames(activeUser)
		JComboBox gamesBox = new JComboBox();
		//gamesBox.setSelectedIndex(0);
		gamesBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//gameID = whatever was selected in combo box
				String gameID = "";
				Game game = Controller.getGame(gameID);
				GameBoard activeGame = new GameBoard(game);
				activeGame.main(game);
			}
		});
		gamesBox.setBounds(12, 129, 176, 22);
		frame.getContentPane().add(gamesBox);
		
		
		//Controller.getUsers()
		JComboBox PlayersBox = new JComboBox();
		PlayersBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				//need to do more here
				
			}
		});
		PlayersBox.setBounds(239, 129, 160, 22);
		frame.getContentPane().add(PlayersBox);
		
		JLabel lblActiveGamesinvites = new JLabel("Active Games");
		lblActiveGamesinvites.setBounds(12, 104, 160, 16);
		frame.getContentPane().add(lblActiveGamesinvites);
		
		JLabel lblViewPlayerProfiles = new JLabel("View player Profiles");
		lblViewPlayerProfiles.setBounds(239, 104, 160, 16);
		frame.getContentPane().add(lblViewPlayerProfiles);
		
		JComboBox inviteBox = new JComboBox();
		inviteBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				int index =inviteBox.getSelectedIndex();
				if(JOptionPane.showConfirmDialog(null, "Accept invite?", null, JOptionPane.YES_NO_OPTION)==0){
					// create game.
				}
				
			}
		});
		inviteBox.setBounds(12, 216, 176, 22);
		frame.getContentPane().add(inviteBox);
		
		JLabel lblInvitations = new JLabel("Invitations");
		lblInvitations.setBounds(12, 193, 76, 16);
		frame.getContentPane().add(lblInvitations);
	}
	
	public User getUser(){
		return activeUser;
	}
}
