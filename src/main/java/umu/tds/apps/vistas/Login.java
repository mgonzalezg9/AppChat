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
		frmLogin.setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/umu/tds/apps/resources/icon.png")));
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 690, 444);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{150, 200, 150, 0};
		gridBagLayout.rowHeights = new int[]{100, 144, 96, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		frmLogin.getContentPane().setLayout(gridBagLayout);
		
		JPanel panelNorte = new JPanel();
		GridBagConstraints gbc_panelNorte = new GridBagConstraints();
		gbc_panelNorte.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelNorte.insets = new Insets(0, 0, 5, 5);
		gbc_panelNorte.gridx = 1;
		gbc_panelNorte.gridy = 0;
		frmLogin.getContentPane().add(panelNorte, gbc_panelNorte);
		GridBagLayout gbl_panelNorte = new GridBagLayout();
		gbl_panelNorte.columnWidths = new int[]{131, 50, 42, 0};
		gbl_panelNorte.rowHeights = new int[]{60, 0};
		gbl_panelNorte.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelNorte.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelNorte.setLayout(gbl_panelNorte);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Login.class.getResource("/umu/tds/apps/resources/icon.png")));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.NORTHEAST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panelNorte.add(label, gbc_label);
		
		JLabel lblAppchat = new JLabel("AppChat");
		GridBagConstraints gbc_lblAppchat = new GridBagConstraints();
		gbc_lblAppchat.anchor = GridBagConstraints.WEST;
		gbc_lblAppchat.gridx = 2;
		gbc_lblAppchat.gridy = 0;
		panelNorte.add(lblAppchat, gbc_lblAppchat);
		
		JPanel panelCentro = new JPanel();
		GridBagConstraints gbc_panelCentro = new GridBagConstraints();
		gbc_panelCentro.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelCentro.insets = new Insets(0, 0, 5, 5);
		gbc_panelCentro.gridx = 1;
		gbc_panelCentro.gridy = 1;
		frmLogin.getContentPane().add(panelCentro, gbc_panelCentro);
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[]{0, 0, 0};
		gbl_panelCentro.rowHeights = new int[]{25, 0, 15, 0, 25, 0};
		gbl_panelCentro.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelCentro.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelCentro.setLayout(gbl_panelCentro);
		
		JLabel lblNewLabel = new JLabel("User");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panelCentro.add(lblNewLabel, gbc_lblNewLabel);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		panelCentro.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		panelCentro.add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 3;
		panelCentro.add(passwordField, gbc_passwordField);
		
		JPanel panelSur = new JPanel();
		GridBagConstraints gbc_panelSur = new GridBagConstraints();
		gbc_panelSur.insets = new Insets(0, 0, 0, 5);
		gbc_panelSur.gridx = 1;
		gbc_panelSur.gridy = 2;
		frmLogin.getContentPane().add(panelSur, gbc_panelSur);
		GridBagLayout gbl_panelSur = new GridBagLayout();
		gbl_panelSur.columnWidths = new int[]{0, 0, 0};
		gbl_panelSur.rowHeights = new int[]{0, 0, 0};
		gbl_panelSur.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panelSur.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelSur.setLayout(gbl_panelSur);
		
		JButton btnSignIn = new JButton("SIGN IN");
		GridBagConstraints gbc_btnSignIn = new GridBagConstraints();
		gbc_btnSignIn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSignIn.gridwidth = 2;
		gbc_btnSignIn.insets = new Insets(0, 0, 5, 5);
		gbc_btnSignIn.gridx = 0;
		gbc_btnSignIn.gridy = 0;
		panelSur.add(btnSignIn, gbc_btnSignIn);
		
		JLabel lblNewLabel_1 = new JLabel("Need an account? ");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panelSur.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JButton btnSignUp = new JButton("SIGN UP");
		GridBagConstraints gbc_btnSignUp = new GridBagConstraints();
		gbc_btnSignUp.gridx = 1;
		gbc_btnSignUp.gridy = 1;
		panelSur.add(btnSignUp, gbc_btnSignUp);
	}

}