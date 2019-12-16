package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.IndividualContact;
import umu.tds.apps.controlador.Controlador;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.awt.event.ActionEvent;

public class ContactWindow extends JFrame {

	private JPanel contentPane;
	private JList<IndividualContact> list;
	private JScrollPane scrollPane;
	private JPanel panel_1;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContactWindow frame = new ContactWindow();
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
	public ContactWindow() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(ContactWindow.class.getResource("/umu/tds/apps/resources/icon.png")));
		setTitle("Contacts");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 346);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(MAIN_COLOR_LIGHT);
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(null, "Contacts info", TitledBorder.LEADING, TitledBorder.TOP, null,
				TEXT_COLOR_LIGHT));
		scrollPane.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);

		final DefaultListModel<IndividualContact> modelContact = new DefaultListModel<>();
		List<IndividualContact> contactosIndividuales = Controlador.getInstancia()
				.getContactosIndividualesUsuarioActual();
		for (int i = 0; i < contactosIndividuales.size(); i++)
			modelContact.add(i, contactosIndividuales.get(i));

		list = new JList<>(modelContact);
		scrollPane.setViewportView(list);
		list.setCellRenderer(createListRenderer());
		list.setBackground(MAIN_COLOR_LIGHT);

		panel_1 = new JPanel();
		panel_1.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		contentPane.add(panel_1, gbc_panel_1);

		btnNewButton_2 = new JButton("Show shared groups");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IndividualContact selected = list.getSelectedValue();
				if (selected != null) {
					SharedGroups window = new SharedGroups(selected);
					window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					window.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(ContactWindow.this, "Please select any contact.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton_2.setBackground(SECONDARY_COLOR);
		panel_1.add(btnNewButton_2);

		btnNewButton_1 = new JButton("EXPORT");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Funcionalidad Premium
				if (Controlador.getInstancia().getUsuarioActual().isPremium()) {
					// TODO Exporto a PDF toda la información
					try {
						FileOutputStream archivo;
						archivo = new FileOutputStream("C:\\Users\\Alfonso\\hola.pdf");
						Document documento = new Document();
						PdfWriter.getInstance(documento, archivo);
						documento.open();
						documento.add(new Paragraph("Hola Mundo!"));
						documento.close();
					} catch (DocumentException | FileNotFoundException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(ContactWindow.this,
							"Become premium to have access to this funcionality.", "Premium",
							JOptionPane.ERROR_MESSAGE);
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
		btnNewButton_1.setBackground(SECONDARY_COLOR);
		panel_1.add(btnNewButton_1);
	}

	private static ListCellRenderer<? super Contact> createListRenderer() {
		return new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				IndividualContact contacto = (IndividualContact) value;

				JPanel panel = new JPanel();
				panel.setBorder(new EmptyBorder(5, 5, 5, 5));
				GridBagLayout gbl_contentPane = new GridBagLayout();
				gbl_contentPane.columnWidths = new int[] { 10, 100, 100, 100, 10, 0 };
				gbl_contentPane.rowHeights = new int[] { 10, 0, 10, 0 };
				gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
				gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
				panel.setLayout(gbl_contentPane);

				JLabel lblNombre = new JLabel(contacto.getNombre());
				lblNombre.setForeground(TEXT_COLOR_LIGHT);
				GridBagConstraints gbc_lblNombre = new GridBagConstraints();
				gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
				gbc_lblNombre.gridx = 1;
				gbc_lblNombre.gridy = 1;
				panel.add(lblNombre, gbc_lblNombre);

				JLabel lblImagen = new JLabel("");
				lblImagen.setIcon(Theme.resizeIcon(contacto.getFoto(), 50));
				GridBagConstraints gbc_lblImagen = new GridBagConstraints();
				gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
				gbc_lblImagen.gridx = 2;
				gbc_lblImagen.gridy = 1;
				panel.add(lblImagen, gbc_lblImagen);

				JLabel lblNumTelefono = new JLabel(String.valueOf(contacto.getMovil()));
				lblNumTelefono.setForeground(TEXT_COLOR_LIGHT);
				GridBagConstraints gbc_lblNumTelefono = new GridBagConstraints();
				gbc_lblNumTelefono.insets = new Insets(0, 0, 5, 5);
				gbc_lblNumTelefono.gridx = 3;
				gbc_lblNumTelefono.gridy = 1;
				panel.add(lblNumTelefono, gbc_lblNumTelefono);

				panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				panel.setBackground((isSelected) ? SECONDARY_COLOR : MAIN_COLOR_LIGHT);

				return panel;
			}
		};
	}
}
