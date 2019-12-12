package umu.tds.apps.vistas;

import java.awt.EventQueue;
import static umu.tds.apps.vistas.Theme.*;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

import umu.tds.apps.controlador.Controlador;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.EtchedBorder;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField userField;
	private JPasswordField passwordField;
	private Controlador controlador;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.setVisible(true);
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
		getContentPane().setBackground(MAIN_COLOR_LIGHT);
		controlador = Controlador.getInstancia();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/umu/tds/apps/resources/icon.png")));
		setTitle("Login");
		setBounds(100, 100, 699, 463);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 120, 200, 120, 0 };
		gridBagLayout.rowHeights = new int[] { 100, 144, 96, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JPanel panelNorte = new JPanel();
		panelNorte.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_panelNorte = new GridBagConstraints();
		gbc_panelNorte.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelNorte.insets = new Insets(0, 0, 5, 5);
		gbc_panelNorte.gridx = 1;
		gbc_panelNorte.gridy = 0;
		getContentPane().add(panelNorte, gbc_panelNorte);
		GridBagLayout gbl_panelNorte = new GridBagLayout();
		gbl_panelNorte.columnWidths = new int[] { 131, 50, 42, 0 };
		gbl_panelNorte.rowHeights = new int[] { 60, 0 };
		gbl_panelNorte.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelNorte.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelNorte.setLayout(gbl_panelNorte);

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(Login.class.getResource("/umu/tds/apps/resources/icon.png")));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.NORTHEAST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panelNorte.add(label, gbc_label);

		JLabel lblAppchat = new JLabel();
		lblAppchat.setIcon(new ImageIcon(Login.class.getResource("/umu/tds/apps/resources/header.png")));
		GridBagConstraints gbc_lblAppchat = new GridBagConstraints();
		gbc_lblAppchat.anchor = GridBagConstraints.WEST;
		gbc_lblAppchat.gridx = 2;
		gbc_lblAppchat.gridy = 0;
		panelNorte.add(lblAppchat, gbc_lblAppchat);

		JPanel panelCentro = new JPanel();
		panelCentro.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_panelCentro = new GridBagConstraints();
		gbc_panelCentro.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelCentro.insets = new Insets(0, 0, 5, 5);
		gbc_panelCentro.gridx = 1;
		gbc_panelCentro.gridy = 1;
		getContentPane().add(panelCentro, gbc_panelCentro);
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelCentro.rowHeights = new int[] { 25, 0, 15, 0, 25, 0 };
		gbl_panelCentro.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelCentro.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelCentro.setLayout(gbl_panelCentro);

		JLabel userLabel = new JLabel("User");
		userLabel.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_userLabel = new GridBagConstraints();
		gbc_userLabel.insets = new Insets(0, 0, 5, 5);
		gbc_userLabel.anchor = GridBagConstraints.EAST;
		gbc_userLabel.gridx = 0;
		gbc_userLabel.gridy = 1;
		panelCentro.add(userLabel, gbc_userLabel);

		userField = new JTextField();
		userField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				userField.setBackground(MAIN_COLOR);
			}
		});
		userField.setForeground(TEXT_COLOR_LIGHT);
		userField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		userField.setCaretColor(TEXT_COLOR_LIGHT);
		userField.setBackground(MAIN_COLOR);
		GridBagConstraints gbc_userField = new GridBagConstraints();
		gbc_userField.insets = new Insets(0, 0, 5, 0);
		gbc_userField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userField.gridx = 1;
		gbc_userField.gridy = 1;
		panelCentro.add(userField, gbc_userField);
		userField.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		panelCentro.add(lblPassword, gbc_lblPassword);

		passwordField = new JPasswordField();
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordField.setBackground(MAIN_COLOR);
			}
		});
		passwordField.setForeground(TEXT_COLOR_LIGHT);
		passwordField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		passwordField.setCaretColor(TEXT_COLOR_LIGHT);
		passwordField.setBackground(MAIN_COLOR);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 3;
		panelCentro.add(passwordField, gbc_passwordField);

		JPanel panelSur = new JPanel();
		panelSur.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_panelSur = new GridBagConstraints();
		gbc_panelSur.insets = new Insets(0, 0, 0, 5);
		gbc_panelSur.gridx = 1;
		gbc_panelSur.gridy = 2;
		getContentPane().add(panelSur, gbc_panelSur);
		GridBagLayout gbl_panelSur = new GridBagLayout();
		gbl_panelSur.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelSur.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelSur.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelSur.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panelSur.setLayout(gbl_panelSur);

		JButton btnSignIn = new JButton("SIGN IN");
		btnSignIn.setBackground(SECONDARY_COLOR);
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Comprobamos que el login haya sido satisfactorio
				String user = userField.getText();
				char[] password = passwordField.getPassword();

				if (controlador.iniciarSesion(user, String.valueOf(password))) {
					// Oculta la ventana actual
					Login.this.setVisible(false);

					// Abre la nueva ventana
					JFrame mainWindow = new Principal();
					mainWindow.setVisible(true);
				} else {
					userField.setBackground(WRONG_INPUT_COLOR);
					passwordField.setBackground(WRONG_INPUT_COLOR);
				}

			}
		});
		GridBagConstraints gbc_btnSignIn = new GridBagConstraints();
		gbc_btnSignIn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSignIn.gridwidth = 2;
		gbc_btnSignIn.insets = new Insets(0, 0, 5, 5);
		gbc_btnSignIn.gridx = 0;
		gbc_btnSignIn.gridy = 0;
		panelSur.add(btnSignIn, gbc_btnSignIn);

		JLabel lblNewLabel_1 = new JLabel("Need an account? ");
		lblNewLabel_1.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panelSur.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JButton btnSignUp = new JButton("SIGN UP");
		btnSignUp.setBackground(SECONDARY_COLOR);
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Cierra la ventana actual
				Login.this.setVisible(false);

				// Abre la nueva ventana
				JFrame registerWindow = new Register();
				registerWindow.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnSignUp = new GridBagConstraints();
		gbc_btnSignUp.gridx = 1;
		gbc_btnSignUp.gridy = 1;
		panelSur.add(btnSignUp, gbc_btnSignUp);
	}

}
