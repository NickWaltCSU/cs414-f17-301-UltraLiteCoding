package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.Font;
import javax.swing.SwingConstants;

import controller.Controller;
import model.User;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateAccount {
	
	private String email1;
	private String password1;
	
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField txtEmail;
	private JTextField txtPassword;
	private JTextField txtNickname;
	private JLabel lblNickname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAccount window = new CreateAccount(args[0],args[1]);
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
	public CreateAccount(String email, String password) {
		this.email1=email;
		this.password1=password;
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(120, 58, 200, 22);
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(120, 93, 200, 22);
		frame.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblReenterEmailAnd = new JLabel("Reenter email and password please.");
		lblReenterEmailAnd.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblReenterEmailAnd.setBounds(98, 13, 253, 32);
		frame.getContentPane().add(lblReenterEmailAnd);
		
		JLabel lblEmail = new JLabel("email");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(52, 61, 56, 16);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(45, 96, 63, 16);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblCreateAUser = new JLabel("Create a Nickname");
		lblCreateAUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateAUser.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCreateAUser.setBounds(120, 128, 200, 16);
		frame.getContentPane().add(lblCreateAUser);
		
		txtNickname = new JTextField();
		txtNickname.setBounds(120, 157, 200, 22);
		frame.getContentPane().add(txtNickname);
		txtNickname.setColumns(10);
		
		lblNickname = new JLabel("nickname");
		lblNickname.setBounds(52, 160, 56, 16);
		frame.getContentPane().add(lblNickname);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(email1.equals(txtEmail.getText())&&password1.equals(txtPassword.getText())){

					boolean success = Controller.registerEmailPW(txtEmail.getText(), txtNickname.getText(), txtPassword.getText());
					
					//System.out.println(txtEmail.getText()+" "+txtNickname.getText()+" "+txtPassword.getText());
					if(success){
						JOptionPane.showMessageDialog(null, txtNickname.getText()+" Has created a new account!");
						frame.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "Registration fail!\nEmail or nickname may not be unique.");
					}
				}else{
					//System.out.println(email1);
					JOptionPane.showMessageDialog(null, "Email and/or password do not match first entry.");
				}
			}
		});
		btnCreate.setBounds(114, 192, 97, 25);
		frame.getContentPane().add(btnCreate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnCancel.setBounds(223, 192, 97, 25);
		frame.getContentPane().add(btnCancel);
		
	}
}

