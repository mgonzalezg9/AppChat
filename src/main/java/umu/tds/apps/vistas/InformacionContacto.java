package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.SECONDARY_COLOR;
import static umu.tds.apps.vistas.Theme.TEXT_COLOR_LIGHT;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InformacionContacto extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InformacionContacto frame = new InformacionContacto();
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
	public InformacionContacto() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{10, 100, 100, 100, 10, 0};
		gbl_contentPane.rowHeights = new int[]{10, 0, 10, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNombre = new JLabel("Alfonso");
		lblNombre.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		contentPane.add(lblNombre, gbc_lblNombre);
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setIcon(new ImageIcon(InformacionContacto.class.getResource("/umu/tds/apps/resources/user50.png")));
		GridBagConstraints gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagen.gridx = 2;
		gbc_lblImagen.gridy = 1;
		contentPane.add(lblImagen, gbc_lblImagen);
		
		JLabel lblNumTelefono = new JLabel("654781294");
		lblNumTelefono.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblNumTelefono = new GridBagConstraints();
		gbc_lblNumTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumTelefono.gridx = 3;
		gbc_lblNumTelefono.gridy = 1;
		contentPane.add(lblNumTelefono, gbc_lblNumTelefono);
	}

}
