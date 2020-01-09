package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import com.toedter.calendar.JDateChooser;

import umu.tds.apps.controlador.Controlador;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Register extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@"
			+ "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	private JPanel contentPane;
	private JTextField textFieldUser;
	private JTextField textFieldPassword;
	private JTextField textFieldConfPassword;
	private JTextField textFieldEmail;
	private JTextField textFieldPNumber;
	private JTextField textFieldName;
	private JDateChooser birthDateChooser;

	/**
	 * Crea la ventana
	 */
	public Register() {
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(Register.class.getResource("/umu/tds/apps/resources/icon.png")));
		setTitle("Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 647, 368);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(MAIN_COLOR_LIGHT);
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 20, 0, 100, 100, 20, 100, 100, 100, 20, 0 };
		gbl_contentPane.rowHeights = new int[] { 20, 0, 60, 0, 31, 10, 0, 0, 22, 0, 0, 20, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.SOUTHEAST;
		gbc_panel.gridwidth = 3;
		gbc_panel.gridheight = 4;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 128, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		final JLabel lblChooseImage = new JLabel("");
		lblChooseImage.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblChooseImage.setForeground(WRONG_INPUT_COLOR);
		GridBagConstraints gbc_lblChooseImage = new GridBagConstraints();
		gbc_lblChooseImage.anchor = GridBagConstraints.EAST;
		gbc_lblChooseImage.insets = new Insets(0, 0, 0, 5);
		gbc_lblChooseImage.gridx = 0;
		gbc_lblChooseImage.gridy = 0;
		panel.add(lblChooseImage, gbc_lblChooseImage);

		final JLabel lblImgUser = new JLabel();
		ImageIcon imagenPorDefecto = new ImageIcon(Register.class.getResource("/umu/tds/apps/resources/user.png"));
		imagenPorDefecto.setDescription("/umu/tds/apps/resources/user.png");
		lblImgUser.setIcon(imagenPorDefecto);
		GridBagConstraints gbc_imgUser = new GridBagConstraints();
		gbc_imgUser.fill = GridBagConstraints.BOTH;
		gbc_imgUser.gridx = 1;
		gbc_imgUser.gridy = 0;
		panel.add(lblImgUser, gbc_imgUser);

		lblImgUser.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select an image");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG and PNG images", "jpg", "png");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					BufferedImage img;
					try {
						File file = jfc.getSelectedFile();
						img = ImageIO.read(file);

						// Redondea la imagen
						img = roundImage(img);

						// Escala el tamaño de la imagen para mostrarla
						ImageIcon icon = resizeIcon(img, 128);
						
						// Le da una descripción para guardarla a posteriori
						icon.setDescription(file.getPath());
						lblImgUser.setIcon(icon);

						/*Dimension imageSize = new Dimension(128, 128);
						lblImgUser.setPreferredSize(imageSize);*/

						lblImgUser.revalidate();
						lblImgUser.repaint();
						lblChooseImage.setText("");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblChooseImage.setText("Change your avatar");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblChooseImage.setText("");
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

		});

		JLabel lblUser = new JLabel("User");
		lblUser.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblUser.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblUser.gridx = 5;
		gbc_lblUser.gridy = 2;
		contentPane.add(lblUser, gbc_lblUser);

		textFieldUser = new JTextField();
		textFieldUser.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textFieldUser.setBackground(MAIN_COLOR);
			}
		});
		textFieldUser.setForeground(TEXT_COLOR_LIGHT);
		textFieldUser.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textFieldUser.setCaretColor(TEXT_COLOR_LIGHT);
		textFieldUser.setBackground(MAIN_COLOR);
		GridBagConstraints gbc_textFieldUser = new GridBagConstraints();
		gbc_textFieldUser.gridwidth = 2;
		gbc_textFieldUser.anchor = GridBagConstraints.SOUTH;
		gbc_textFieldUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUser.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldUser.gridx = 6;
		gbc_textFieldUser.gridy = 2;
		contentPane.add(textFieldUser, gbc_textFieldUser);
		textFieldUser.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 5;
		gbc_lblPassword.gridy = 3;
		contentPane.add(lblPassword, gbc_lblPassword);

		textFieldPassword = new JPasswordField();
		textFieldPassword.setForeground(TEXT_COLOR_LIGHT);
		textFieldPassword.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textFieldPassword.setCaretColor(TEXT_COLOR_LIGHT);
		textFieldPassword.setBackground(MAIN_COLOR);
		textFieldPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textFieldPassword.setBackground(MAIN_COLOR);
			}
		});
		GridBagConstraints gbc_textFieldPassword = new GridBagConstraints();
		gbc_textFieldPassword.gridwidth = 2;
		gbc_textFieldPassword.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPassword.gridx = 6;
		gbc_textFieldPassword.gridy = 3;
		contentPane.add(textFieldPassword, gbc_textFieldPassword);
		textFieldPassword.setColumns(10);

		JLabel lblConfirmPassword = new JLabel("Confirm password");
		lblConfirmPassword.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 5;
		gbc_lblConfirmPassword.gridy = 4;
		contentPane.add(lblConfirmPassword, gbc_lblConfirmPassword);

		textFieldConfPassword = new JPasswordField();
		textFieldConfPassword.setForeground(TEXT_COLOR_LIGHT);
		textFieldConfPassword.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textFieldConfPassword.setCaretColor(TEXT_COLOR_LIGHT);
		textFieldConfPassword.setBackground(MAIN_COLOR);
		textFieldConfPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textFieldConfPassword.setBackground(MAIN_COLOR);
			}
		});
		GridBagConstraints gbc_textFieldConfPassword = new GridBagConstraints();
		gbc_textFieldConfPassword.gridwidth = 2;
		gbc_textFieldConfPassword.anchor = GridBagConstraints.NORTH;
		gbc_textFieldConfPassword.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldConfPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldConfPassword.gridx = 6;
		gbc_textFieldConfPassword.gridy = 4;
		contentPane.add(textFieldConfPassword, gbc_textFieldConfPassword);
		textFieldConfPassword.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 6;
		contentPane.add(lblEmail, gbc_lblEmail);

		textFieldEmail = new JTextField();
		textFieldEmail.setForeground(TEXT_COLOR_LIGHT);
		textFieldEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textFieldEmail.setCaretColor(TEXT_COLOR_LIGHT);
		textFieldEmail.setBackground(MAIN_COLOR);
		textFieldEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textFieldEmail.setBackground(MAIN_COLOR);
			}
		});
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.gridwidth = 2;
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmail.gridx = 2;
		gbc_textFieldEmail.gridy = 6;
		contentPane.add(textFieldEmail, gbc_textFieldEmail);
		textFieldEmail.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 5;
		gbc_lblName.gridy = 6;
		contentPane.add(lblName, gbc_lblName);

		textFieldName = new JTextField();
		textFieldName.setForeground(TEXT_COLOR_LIGHT);
		textFieldName.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textFieldName.setCaretColor(TEXT_COLOR_LIGHT);
		textFieldName.setBackground(MAIN_COLOR);
		textFieldName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textFieldName.setBackground(MAIN_COLOR);
			}
		});
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.gridwidth = 2;
		gbc_textFieldName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 6;
		gbc_textFieldName.gridy = 6;
		contentPane.add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(10);

		JLabel lblPhoneNumber = new JLabel("Phone number");
		lblPhoneNumber.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblPhoneNumber = new GridBagConstraints();
		gbc_lblPhoneNumber.anchor = GridBagConstraints.EAST;
		gbc_lblPhoneNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhoneNumber.gridx = 1;
		gbc_lblPhoneNumber.gridy = 7;
		contentPane.add(lblPhoneNumber, gbc_lblPhoneNumber);

		textFieldPNumber = new JTextField();
		textFieldPNumber.setForeground(TEXT_COLOR_LIGHT);
		textFieldPNumber.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textFieldPNumber.setCaretColor(TEXT_COLOR_LIGHT);
		textFieldPNumber.setBackground(MAIN_COLOR);
		textFieldPNumber.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textFieldPNumber.setBackground(MAIN_COLOR);
			}
		});
		GridBagConstraints gbc_textFieldPNumber = new GridBagConstraints();
		gbc_textFieldPNumber.gridwidth = 2;
		gbc_textFieldPNumber.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPNumber.gridx = 2;
		gbc_textFieldPNumber.gridy = 7;
		contentPane.add(textFieldPNumber, gbc_textFieldPNumber);
		textFieldPNumber.setColumns(10);

		JLabel lblBirthDate = new JLabel("Birth Date");
		lblBirthDate.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblBirthDate = new GridBagConstraints();
		gbc_lblBirthDate.anchor = GridBagConstraints.EAST;
		gbc_lblBirthDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblBirthDate.gridx = 5;
		gbc_lblBirthDate.gridy = 7;
		contentPane.add(lblBirthDate, gbc_lblBirthDate);

		birthDateChooser = new JDateChooser();
		birthDateChooser.getDateEditor().getUiComponent().setBackground(MAIN_COLOR);
		birthDateChooser.getDateEditor().getUiComponent().setForeground(MAIN_COLOR);
		birthDateChooser.getDateEditor().getUiComponent().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		birthDateChooser.setForeground(MAIN_COLOR);
		birthDateChooser.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		birthDateChooser.setBackground(MAIN_COLOR);
		birthDateChooser.setDate(new Date());
		GridBagConstraints gbc_birthDateChooser = new GridBagConstraints();
		gbc_birthDateChooser.gridwidth = 2;
		gbc_birthDateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_birthDateChooser.fill = GridBagConstraints.BOTH;
		gbc_birthDateChooser.gridx = 6;
		gbc_birthDateChooser.gridy = 7;
		contentPane.add(birthDateChooser, gbc_birthDateChooser);

		JButton btnNewButton = new JButton("CREATE ACCOUNT");

		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Compruebo que los datos introducidos sean correctos
				if (!datosCorrectos())
					return;

				// Registra al usuario
				boolean creada = Controlador.getInstancia().crearCuenta((ImageIcon) (lblImgUser.getIcon()),
						textFieldUser.getText(), textFieldPassword.getText(), textFieldEmail.getText(),
						textFieldName.getText(), Integer.parseInt(textFieldPNumber.getText()),
						birthDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

				if (!creada) {
					// textFieldUser.setBackground(WRONG_INPUT_COLOR);
					JOptionPane.showMessageDialog(Register.this, "User already exists", "Create account",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// Cierra la ventana actual
					Register.this.setVisible(false);

					// Abre la nueva ventana
					JFrame principalWindow = new Principal();
					principalWindow.setVisible(true);
				}
			}
		});
		btnNewButton.setBackground(SECONDARY_COLOR);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridwidth = 3;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 9;
		contentPane.add(btnNewButton, gbc_btnNewButton);

		JLabel lblHaveAnAccount = new JLabel("Have an account?");
		lblHaveAnAccount.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblHaveAnAccount = new GridBagConstraints();
		gbc_lblHaveAnAccount.anchor = GridBagConstraints.WEST;
		gbc_lblHaveAnAccount.gridwidth = 2;
		gbc_lblHaveAnAccount.insets = new Insets(0, 0, 5, 5);
		gbc_lblHaveAnAccount.gridx = 3;
		gbc_lblHaveAnAccount.gridy = 10;
		contentPane.add(lblHaveAnAccount, gbc_lblHaveAnAccount);

		JButton btnNewButton_1 = new JButton("SIGN IN");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Cierra la ventana actual
				Register.this.setVisible(false);

				// Abre la nueva ventana
				JFrame loginWindow = new Login();
				loginWindow.setVisible(true);
			}
		});
		btnNewButton_1.setBackground(SECONDARY_COLOR);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 5;
		gbc_btnNewButton_1.gridy = 10;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);
	}

	private boolean datosCorrectos() {
		List<String> errores = new LinkedList<>();

		if (textFieldUser.getText().equals("")) {
			errores.add("User value is invalid");
			textFieldUser.setBackground(WRONG_INPUT_COLOR);
		}
		if (textFieldPassword.getText().equals("")) {
			errores.add("Password value is invalid");
			textFieldPassword.setBackground(WRONG_INPUT_COLOR);
		}
		if (textFieldConfPassword.getText().equals("")) {
			errores.add("Confirm password value is invalid");
			textFieldConfPassword.setBackground(WRONG_INPUT_COLOR);
		}
		if (textFieldEmail.getText().equals("") || !isMail(textFieldEmail.getText())) {
			errores.add("Email value is invalid");
			textFieldEmail.setBackground(WRONG_INPUT_COLOR);
		}
		if (textFieldPNumber.getText().equals("") || !isNumeric(textFieldPNumber.getText())
				|| Integer.parseInt(textFieldPNumber.getText()) < 0) {
			errores.add("Phone number value is invalid");
			textFieldPNumber.setBackground(WRONG_INPUT_COLOR);
		}
		if (textFieldName.getText().equals("")) {
			errores.add("Name value is invalid");
			textFieldName.setBackground(WRONG_INPUT_COLOR);
		}
		if (!textFieldPassword.getText().equals(textFieldConfPassword.getText())) {
			errores.add("Passwords are not equals");
			textFieldConfPassword.setBackground(WRONG_INPUT_COLOR);
		}
		if (birthDateChooser.getDate() == null || birthDateChooser.getDate().after(new Date())) {
			errores.add("Birth date value is invalid");
		}

		if (errores.size() > 0) {
			String error = "";
			for (String e : errores)
				error += e + "\n";
			JOptionPane.showMessageDialog(Register.this, error, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private boolean isMail(String email) {
		if (email == null)
			return false;
		return EMAIL_PATTERN.matcher(email).matches();
	}

	

}
