package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.IndividualContact;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

public class ContactInfo extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ContactInfo(Contact contact) {
		setTitle("Contact info");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ContactInfo.class.getResource("/umu/tds/apps/resources/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 438, 298);
		contentPane = new JPanel();
		contentPane.setBackground(MAIN_COLOR_LIGHT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{10, 127, 130, 10, 0};
		gbl_contentPane.rowHeights = new int[]{10, 0, 10, 0, 0, 10, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(resizeIcon(contact.getFoto(), 128));		
		
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblName = new JLabel("Name: ");
		lblName.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 3;
		contentPane.add(lblName, gbc_lblName);
		
		JLabel lblNameData = new JLabel(contact.getNombre());
		lblNameData.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblNameData = new GridBagConstraints();
		gbc_lblNameData.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNameData.insets = new Insets(0, 0, 5, 5);
		gbc_lblNameData.gridx = 2;
		gbc_lblNameData.gridy = 3;
		contentPane.add(lblNameData, gbc_lblNameData);
		
		if (contact instanceof IndividualContact) {
			JLabel lblTele = new JLabel("Phone: ");
			lblTele.setForeground(TEXT_COLOR_LIGHT);
			GridBagConstraints gbc_lblTele = new GridBagConstraints();
			gbc_lblTele.anchor = GridBagConstraints.EAST;
			gbc_lblTele.insets = new Insets(0, 0, 5, 5);
			gbc_lblTele.gridx = 1;
			gbc_lblTele.gridy = 4;
			contentPane.add(lblTele, gbc_lblTele);
			
			JLabel label = new JLabel(String.valueOf(((IndividualContact) contact).getMovil()));
			label.setForeground(TEXT_COLOR_LIGHT);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.fill = GridBagConstraints.HORIZONTAL;
			gbc_label.insets = new Insets(0, 0, 5, 5);
			gbc_label.gridx = 2;
			gbc_label.gridy = 4;
			contentPane.add(label, gbc_label);
		}
	}

}
