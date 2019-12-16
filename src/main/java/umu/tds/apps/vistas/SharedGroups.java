package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.MAIN_COLOR_LIGHT;
import static umu.tds.apps.vistas.Theme.SECONDARY_COLOR;
import static umu.tds.apps.vistas.Theme.TEXT_COLOR_LIGHT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.Group;
import umu.tds.apps.AppChat.IndividualContact;
import umu.tds.apps.controlador.Controlador;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JList;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

public class SharedGroups extends JFrame {

	private JPanel contentPane;
	private IndividualContact contact;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//SharedGroups frame = new SharedGroups();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SharedGroups(IndividualContact contacto) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		this.contact = contacto;
		
		contentPane = new JPanel();
		contentPane.setBackground(MAIN_COLOR_LIGHT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		final DefaultListModel<String> modelNameGroup = new DefaultListModel<>();
		List<String> gruposCompartidos = Controlador.getInstancia().getNombresGrupo(contact);
		for (int i = 0; i < gruposCompartidos.size(); i++)
			modelNameGroup.add(i, gruposCompartidos.get(i));
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JList<String> listSharedGroups = new JList<>(modelNameGroup);
		listSharedGroups.setCellRenderer(createListRenderer());
		scrollPane.setViewportView(listSharedGroups);
		listSharedGroups.setBackground(MAIN_COLOR_LIGHT);
	}
	
	private static ListCellRenderer<? super String> createListRenderer() {
		return new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				String nombreGrupo = (String) value;
				
				JPanel panel = new JPanel();
				panel.setBorder(new EmptyBorder(5, 5, 5, 5));
				GridBagLayout gbl_contentPane = new GridBagLayout();
				gbl_contentPane.columnWidths = new int[]{0, 0};
				gbl_contentPane.rowHeights = new int[]{5, 0, 5, 0};
				gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
				gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
				panel.setLayout(gbl_contentPane);
				
				JLabel lblNombreGrupo = new JLabel(nombreGrupo);
				lblNombreGrupo.setForeground(TEXT_COLOR_LIGHT);
				GridBagConstraints gbc_lblEsaEss = new GridBagConstraints();
				gbc_lblEsaEss.insets = new Insets(0, 0, 5, 0);
				gbc_lblEsaEss.gridx = 0;
				gbc_lblEsaEss.gridy = 1;
				panel.add(lblNombreGrupo, gbc_lblEsaEss);

				panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				panel.setBackground((isSelected) ? SECONDARY_COLOR : MAIN_COLOR_LIGHT);

				return panel;
			}
		};
	}
}
