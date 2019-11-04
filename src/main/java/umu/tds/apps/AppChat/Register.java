package umu.tds.apps.AppChat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import com.toedter.calendar.JDateChooser;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Register() {
		setTitle("Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 647, 368);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{20, 0, 100, 100, 20, 100, 100, 100, 20, 0};
		gbl_contentPane.rowHeights = new int[]{20, 0, 60, 0, 31, 10, 0, 0, 22, 0, 0, 20, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.SOUTHEAST;
		gbc_panel.gridwidth = 3;
		gbc_panel.gridheight = 4;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{128, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		final JLabel lblChooseImage = new JLabel("");
		lblChooseImage.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblChooseImage.setForeground(Color.RED);
		GridBagConstraints gbc_lblChooseImage = new GridBagConstraints();
		gbc_lblChooseImage.anchor = GridBagConstraints.EAST;
		gbc_lblChooseImage.insets = new Insets(0, 0, 0, 5);
		gbc_lblChooseImage.gridx = 0;
		gbc_lblChooseImage.gridy = 0;
		panel.add(lblChooseImage, gbc_lblChooseImage);
		
		
		final JLabel imgUser = new JLabel("");
		imgUser.setIcon(new ImageIcon(Register.class.getResource("/umu/tds/apps/resources/user.png")));
		GridBagConstraints gbc_imgUser = new GridBagConstraints();
		gbc_imgUser.fill = GridBagConstraints.BOTH;
		gbc_imgUser.gridx = 1;
		gbc_imgUser.gridy = 0;
		panel.add(imgUser, gbc_imgUser);
		
		imgUser.addMouseListener(
				new MouseListener() {

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
								img = ImageIO.read(jfc.getSelectedFile());
								Image imgScaled = img.getScaledInstance(128, 128, Image.SCALE_DEFAULT);
								ImageIcon icon = new ImageIcon(imgScaled);
								imgUser.setIcon(icon);
								
								Dimension imageSize = new Dimension(128, 128);
								imgUser.setPreferredSize(imageSize);

								imgUser.revalidate();
								imgUser.repaint();
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
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
			         
			      }	
			);
		
		JLabel lblUser = new JLabel("User");
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblUser.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblUser.gridx = 5;
		gbc_lblUser.gridy = 2;
		contentPane.add(lblUser, gbc_lblUser);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.anchor = GridBagConstraints.SOUTH;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 6;
		gbc_textField.gridy = 2;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 5;
		gbc_lblPassword.gridy = 3;
		contentPane.add(lblPassword, gbc_lblPassword);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 2;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 6;
		gbc_textField_1.gridy = 3;
		contentPane.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblConfirmPassword = new JLabel("Confirm password");
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 5;
		gbc_lblConfirmPassword.gridy = 4;
		contentPane.add(lblConfirmPassword, gbc_lblConfirmPassword);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.gridwidth = 2;
		gbc_textField_2.anchor = GridBagConstraints.NORTH;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 6;
		gbc_textField_2.gridy = 4;
		contentPane.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 6;
		contentPane.add(lblEmail, gbc_lblEmail);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.gridwidth = 2;
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 2;
		gbc_textField_3.gridy = 6;
		contentPane.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 5;
		gbc_lblName.gridy = 6;
		contentPane.add(lblName, gbc_lblName);
		
		textField_5 = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.gridwidth = 2;
		gbc_textField_5.insets = new Insets(0, 0, 5, 5);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 6;
		gbc_textField_5.gridy = 6;
		contentPane.add(textField_5, gbc_textField_5);
		textField_5.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Phone number");
		GridBagConstraints gbc_lblPhoneNumber = new GridBagConstraints();
		gbc_lblPhoneNumber.anchor = GridBagConstraints.EAST;
		gbc_lblPhoneNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhoneNumber.gridx = 1;
		gbc_lblPhoneNumber.gridy = 7;
		contentPane.add(lblPhoneNumber, gbc_lblPhoneNumber);
		
		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.gridwidth = 2;
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 2;
		gbc_textField_4.gridy = 7;
		contentPane.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		
		JLabel lblBirthDate = new JLabel("Birth Date");
		GridBagConstraints gbc_lblBirthDate = new GridBagConstraints();
		gbc_lblBirthDate.anchor = GridBagConstraints.EAST;
		gbc_lblBirthDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblBirthDate.gridx = 5;
		gbc_lblBirthDate.gridy = 7;
		contentPane.add(lblBirthDate, gbc_lblBirthDate);
		
		JDateChooser dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.gridwidth = 2;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 6;
		gbc_dateChooser.gridy = 7;
		contentPane.add(dateChooser, gbc_dateChooser);
		
		JButton btnNewButton = new JButton("CREATE ACCOUNT");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridwidth = 3;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 9;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblHaveAnAccount = new JLabel("Have an account?");
		GridBagConstraints gbc_lblHaveAnAccount = new GridBagConstraints();
		gbc_lblHaveAnAccount.anchor = GridBagConstraints.WEST;
		gbc_lblHaveAnAccount.gridwidth = 2;
		gbc_lblHaveAnAccount.insets = new Insets(0, 0, 5, 5);
		gbc_lblHaveAnAccount.gridx = 3;
		gbc_lblHaveAnAccount.gridy = 10;
		contentPane.add(lblHaveAnAccount, gbc_lblHaveAnAccount);
		
		JButton btnNewButton_1 = new JButton("Sign in");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 5;
		gbc_btnNewButton_1.gridy = 10;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);
	}

}
