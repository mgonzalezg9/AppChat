package umu.tds.apps.vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Toolkit;

public class Login {

	private JFrame frmLogin;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmLogin.setVisible(true);
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
		frmLogin = new JFrame();
		frmLogin.setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/umu/tds/apps/resources/fire_120x120.png")));
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 614, 379);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelSur = new JPanel();
		frmLogin.getContentPane().add(panelSur, BorderLayout.SOUTH);
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_3 = new JPanel();
		panelSur.add(panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{89, 0, 0};
		gbl_panel_3.rowHeights = new int[]{23, 14, 20, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JButton btnSignIn = new JButton("SIGN IN");
		GridBagConstraints gbc_btnSignIn = new GridBagConstraints();
		gbc_btnSignIn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSignIn.gridwidth = 2;
		gbc_btnSignIn.insets = new Insets(0, 0, 5, 0);
		gbc_btnSignIn.gridx = 0;
		gbc_btnSignIn.gridy = 0;
		panel_3.add(btnSignIn, gbc_btnSignIn);
		
		JLabel lblNeedAnAccount = new JLabel("Need an account? ");
		GridBagConstraints gbc_lblNeedAnAccount = new GridBagConstraints();
		gbc_lblNeedAnAccount.insets = new Insets(0, 0, 5, 5);
		gbc_lblNeedAnAccount.anchor = GridBagConstraints.WEST;
		gbc_lblNeedAnAccount.gridx = 0;
		gbc_lblNeedAnAccount.gridy = 1;
		panel_3.add(lblNeedAnAccount, gbc_lblNeedAnAccount);
		
		JButton btnSignUp = new JButton("SIGN UP");
		GridBagConstraints gbc_btnSignUp = new GridBagConstraints();
		gbc_btnSignUp.insets = new Insets(0, 0, 5, 0);
		gbc_btnSignUp.gridx = 1;
		gbc_btnSignUp.gridy = 1;
		panel_3.add(btnSignUp, gbc_btnSignUp);
		
		JPanel panelNorte = new JPanel();
		frmLogin.getContentPane().add(panelNorte, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panelNorte.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{60, 42, 0};
		gbl_panel.rowHeights = new int[]{20, 60, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblImg = new JLabel("");
		GridBagConstraints gbc_lblImg = new GridBagConstraints();
		gbc_lblImg.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblImg.insets = new Insets(0, 0, 0, 5);
		gbc_lblImg.gridx = 0;
		gbc_lblImg.gridy = 1;
		panel.add(lblImg, gbc_lblImg);
		lblImg.setIcon(new ImageIcon(Login.class.getResource("/umu/tds/apps/resources/fire_60x60.png")));
		
		JLabel lblAppchat = new JLabel("AppChat");
		GridBagConstraints gbc_lblAppchat = new GridBagConstraints();
		gbc_lblAppchat.anchor = GridBagConstraints.WEST;
		gbc_lblAppchat.gridx = 1;
		gbc_lblAppchat.gridy = 1;
		panel.add(lblAppchat, gbc_lblAppchat);
		
		JPanel panel_4 = new JPanel();
		frmLogin.getContentPane().add(panel_4, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_4.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{77, 142, 0, 0};
		gbl_panel_2.rowHeights = new int[]{20, 0, 45, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblUser = new JLabel("User");
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.anchor = GridBagConstraints.EAST;
		gbc_lblUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblUser.gridx = 0;
		gbc_lblUser.gridy = 1;
		panel_2.add(lblUser, gbc_lblUser);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		panel_2.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 2;
		panel_2.add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 2;
		panel_2.add(passwordField, gbc_passwordField);
	}

}
