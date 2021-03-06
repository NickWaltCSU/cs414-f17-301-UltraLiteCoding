package edu.colostate.cs.cs414.banqi.userInterface;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import edu.colostate.cs.cs414.banqi.controller.Controller;
import edu.colostate.cs.cs414.banqi.model.Color;
import edu.colostate.cs.cs414.banqi.model.Game;
import edu.colostate.cs.cs414.banqi.model.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Dashboard {

	private JFrame frame;
	private User activeUser;
	private boolean doBoxAction = true;

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
		frame = new JFrame(activeUser.getUsername());
		frame.setBounds(100, 100, 450, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
				
		//code from: https://stackoverflow.com/questions/17815033/how-to-change-java-icon-in-a-jframe
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
		
		JButton btnDeregister = new JButton("Deregister");
		btnDeregister.setToolTipText("Click if you wish to deregister from the system entirely.");
		btnDeregister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to deregister?\nAll history will be lost!", "Deregister", JOptionPane.YES_NO_OPTION)==0){

					Controller.deregister(activeUser);
					frame.dispose();
				}
			}
		});
		btnDeregister.setBounds(310, 265, 110, 25);
		frame.getContentPane().add(btnDeregister);
		
		JButton btnViewProfile = new JButton("View My Profile");
		btnViewProfile.setToolTipText("Click to view your profile.");
		btnViewProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewProfile profWindow = new ViewProfile((String)activeUser.getUsername(),(String)activeUser.getUsername());
				profWindow.main((String)activeUser.getUsername(),(String)activeUser.getUsername());
			}
		});
		btnViewProfile.setBounds(12, 101, 137, 25);
		frame.getContentPane().add(btnViewProfile);
		
		
		//String testGames[] = {"Game1","Game2","Game3"};
		//Controller.getGames(activeUser)
		JComboBox gamesBox = new JComboBox(Controller.getGames(activeUser));
		//gamesBox.setSelectedIndex(0);
		gamesBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//gameID = whatever was selected in combo box
				if(doBoxAction){
					if(!((String)gamesBox.getSelectedItem()).equals("No games.")){
						String gameName = (String) gamesBox.getSelectedItem();
						String gameID = Controller.parseInvitation(gameName);
						Game game = Controller.getGame(gameID);
						
						GameBoard activeGame = new GameBoard(game, activeUser.getUsername());
						activeGame.main(game, activeUser.getUsername());
					}
				}
			}
		});
		gamesBox.setBounds(12, 179, 176, 22);
		frame.getContentPane().add(gamesBox);
		
		
		//Player profile box
		JComboBox PlayersBox = new JComboBox(Controller.getUsers());
		PlayersBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				if(doBoxAction){
					System.out.println((String)PlayersBox.getSelectedItem());
					ViewProfile otherPlayer = new ViewProfile(activeUser.getUsername(), (String)PlayersBox.getSelectedItem());
					otherPlayer.main(activeUser.getUsername(), (String)PlayersBox.getSelectedItem());
				}
			}
		});
		PlayersBox.setBounds(239, 179, 160, 22);
		frame.getContentPane().add(PlayersBox);
		//end player box
		
		
		JLabel lblActiveGamesinvites = new JLabel("Games");
		lblActiveGamesinvites.setBounds(12, 154, 160, 16);
		frame.getContentPane().add(lblActiveGamesinvites);
		
		JLabel lblViewPlayerProfiles = new JLabel("View Player Profiles");
		lblViewPlayerProfiles.setBounds(239, 154, 160, 16);
		frame.getContentPane().add(lblViewPlayerProfiles);
		
		
		//Invitations Label
		JLabel lblInvitations = new JLabel("Invitations");
		lblInvitations.setBounds(12, 243, 76, 16);
		frame.getContentPane().add(lblInvitations);
		//Invitations BOX
		JComboBox inviteBox = new JComboBox(Controller.getInvites(activeUser));
		inviteBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(doBoxAction){
					int selectedBtn = JOptionPane.showConfirmDialog(null, "Accept invitation?", "Invites", JOptionPane.YES_NO_CANCEL_OPTION);
					if(selectedBtn==0){
						//create new game
						String sItem = (String)inviteBox.getSelectedItem();
						String ivtID =Controller.parseInvitation(sItem);
						String gameID = Controller.acceptInvitation(ivtID);
						//Game daGame = Controller.getGame(gameID);
						//GameBoard nwGame = new GameBoard(daGame);
						//nwGame.main(daGame);
						
					}else if(selectedBtn==1){
						//delete invitation
						Controller.rejectInvitation(Controller.parseInvitation((String)inviteBox.getSelectedItem()));
						
					}else{
						//do nothing
					}
				}
			}
		});
		inviteBox.setBounds(12, 266, 176, 22);
		frame.getContentPane().add(inviteBox);
		//end invitations box
		
		
		
		
		//refresh drop down boxes button
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setToolTipText("Click to update players, game, and invitations.");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doBoxAction=false;//this prevents box actions from being performed with addItem...
				
				//invites box update
				inviteBox.removeAllItems();
				String[] invitesArray = Controller.getInvites(activeUser);
				for(int i=0;i<invitesArray.length;i++){
					inviteBox.addItem(invitesArray[i]);
				}
				
				//games box update
				gamesBox.removeAllItems();
				String[] gamesArray = Controller.getGames(activeUser);
				for(int i=0;i<gamesArray.length;i++){
					gamesBox.addItem(gamesArray[i]);
				}
				
				//users box update
				PlayersBox.removeAllItems();
				String[] userArray = Controller.getUsers();
				for(int i=0;i<userArray.length;i++){
					PlayersBox.addItem(userArray[i]);
				}
				
				
				doBoxAction=true;
			}
		});
		btnRefresh.setBounds(323, 63, 97, 25);
		frame.getContentPane().add(btnRefresh);
		//end Refresh buton
		
		//AI button
		JButton btnPracticeWithAi = new JButton("Practice with AI");
		btnPracticeWithAi.setToolTipText("Practice playing Banqi with the computer!");
		btnPracticeWithAi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game aiGame = new Game();
				aiGame.setPlayers(activeUser.getUsername(), "AI");
				aiGame.setCreatorColor(Color.RED);
				aiGame.setCurrentColor(Color.RED);
				aiGame.setCurrentPlayer(activeUser.getUsername());
				aiGame.setGameID("AI");
				
				GameBoard aiWindow = new GameBoard(aiGame, activeUser.getUsername());
				aiWindow.main(aiGame, activeUser.getUsername());
				
				
				
				
				
				
			}
		});
		btnPracticeWithAi.setBounds(239, 101, 160, 25);
		frame.getContentPane().add(btnPracticeWithAi);
		
		JLabel lblWelcome = new JLabel("Welcome "+activeUser.getUsername());
		lblWelcome.setForeground(java.awt.Color.BLUE);
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWelcome.setBounds(12, 13, 299, 58);
		frame.getContentPane().add(lblWelcome);
		//end AI button
		
		
	}
	
	
	
	public User getUser(){
		return activeUser;
	}
}
