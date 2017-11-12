package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Window;

import javax.swing.SwingConstants;

import model.User;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JTextField txtPassword;
	private JTextField txtEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 300, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEmail = new JLabel("e-mail");
		lblEmail.setBounds(70, 62, 74, 16);
		frame.getContentPane().add(lblEmail);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(156, 94, 165, 22);
		//txtPassword.setEchoChar('*');
		frame.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(156, 59, 165, 22);
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(70, 97, 74, 16);
		frame.getContentPane().add(lblPassword);
		
		
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String loginMessage="Logging in as: "+txtEmail.getText();
				JOptionPane.showMessageDialog(null, loginMessage);
				User regUser = new User(); 
				Dashboard dashboard=new Dashboard(regUser);
				dashboard.main(regUser);
				frame.setVisible(false);
			}
		});
		btnLogin.setBounds(70, 129, 115, 25);
		frame.getContentPane().add(btnLogin);
		JButton btnCreateAccount = new JButton("Create account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] info={txtEmail.getText(),txtPassword.getText()};
				
				CreateAccount createAccountWindow= new CreateAccount(txtEmail.getText(),txtPassword.getText());
				createAccountWindow.main(info);
			}
		});
		btnCreateAccount.setBounds(197, 129, 124, 25);
		frame.getContentPane().add(btnCreateAccount);
		
		JLabel lblWelcomeToBanqi = new JLabel("Welcome to Banqi!");
		lblWelcomeToBanqi.setForeground(Color.BLUE);
		lblWelcomeToBanqi.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToBanqi.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblWelcomeToBanqi.setBounds(60, 13, 297, 36);
		frame.getContentPane().add(lblWelcomeToBanqi);
	}

}
