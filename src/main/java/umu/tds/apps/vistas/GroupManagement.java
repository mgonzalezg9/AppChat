package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.Group;
import umu.tds.apps.AppChat.IndividualContact;
import umu.tds.apps.controlador.Controlador;

import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;

/**
 * Clase que permite crear un grupo o modificar uno ya existente
 */
public class GroupManagement extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtGroupName;
	private DefaultListModel<Contact> modelContacts;
	private Group groupToModify;

	/**
	 * Crea la ventana
	 */
	public GroupManagement(DefaultListModel<Contact> modelo, Group grupo) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(GroupManagement.class.getResource("/umu/tds/apps/resources/icon.png")));
		setTitle("Group managment");
		setBounds(100, 100, 497, 340);

		this.modelContacts = modelo;
		this.groupToModify = grupo;

		contentPane = new JPanel();
		contentPane.setBackground(MAIN_COLOR_LIGHT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 10, 50, 50, 50, 20, 30, 0, 20, 50, 50, 50, 10, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 50, 0, 0, 50, 0, 18, 10, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel settings = new JPanel();
		settings.setBackground(MAIN_COLOR);
		settings.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_settings = new GridBagConstraints();
		gbc_settings.gridwidth = 12;
		gbc_settings.insets = new Insets(0, 0, 5, 0);
		gbc_settings.fill = GridBagConstraints.BOTH;
		gbc_settings.gridx = 0;
		gbc_settings.gridy = 0;
		contentPane.add(settings, gbc_settings);
		GridBagLayout gbl_settings = new GridBagLayout();
		gbl_settings.columnWidths = new int[] { 0, 0, 0 };
		gbl_settings.rowHeights = new int[] { 0, 0 };
		gbl_settings.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_settings.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		settings.setLayout(gbl_settings);

		JPanel panel = new JPanel();
		panel.setBackground(MAIN_COLOR);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		settings.add(panel, gbc_panel);

		JLabel lblProfilePhoto = new JLabel();
		lblProfilePhoto
				.setIcon(resizeIcon(Controlador.getInstancia().getUsuarioActual().getProfilePhoto(), ICON_SIZE_MINI));
		panel.add(lblProfilePhoto);

		JLabel lblUserName = new JLabel(Controlador.getInstancia().getUsuarioActual().getName());
		lblUserName.setForeground(TEXT_COLOR_LIGHT);
		panel.add(lblUserName);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(MAIN_COLOR);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.EAST;
		gbc_panel_1.fill = GridBagConstraints.VERTICAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		settings.add(panel_1, gbc_panel_1);

		JLabel lblSearchMessages = new JLabel();
		lblSearchMessages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Abre la nueva ventana
				JFrame busquedaWindow = new Search();
				busquedaWindow.setDefaultCloseOperation(HIDE_ON_CLOSE);
				busquedaWindow.setVisible(true);
			}
		});
		lblSearchMessages
				.setIcon(new ImageIcon(GroupManagement.class.getResource("/umu/tds/apps/resources/search-white.png")));
		panel_1.add(lblSearchMessages);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(MAIN_COLOR_LIGHT);
		scrollPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Contacts",
				TitledBorder.LEADING, TitledBorder.TOP, null, TEXT_COLOR_LIGHT));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 6;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);

		final DefaultListModel<IndividualContact> notAddedModel = new DefaultListModel<>();
		List<IndividualContact> contactosIndividuales = Controlador.getInstancia()
				.getUsuarioActual().getContactosIndividuales();

		for (IndividualContact contacto : contactosIndividuales) {
			if (groupToModify == null || !groupToModify.hasParticipante(contacto.getUsuario())) {
				notAddedModel.addElement(contacto);
			}
		}

		final JList<IndividualContact> notAddedList = new JList<>(notAddedModel);
		notAddedList.setCellRenderer(createListRenderer());
		notAddedList.setBackground(new Color(255, 204, 153));
		scrollPane.setViewportView(notAddedList);

		txtGroupName = new JTextField();
		if (groupToModify != null) {
			txtGroupName.setText(groupToModify.getNombre());
		} else {
			txtGroupName.setText("Your Group");
		}

		txtGroupName.setForeground(TEXT_COLOR_LIGHT);
		txtGroupName.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtGroupName.setCaretColor(TEXT_COLOR_LIGHT);
		txtGroupName.setBackground(MAIN_COLOR);

		GridBagConstraints gbc_txtGroupName = new GridBagConstraints();
		gbc_txtGroupName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGroupName.gridwidth = 4;
		gbc_txtGroupName.insets = new Insets(0, 0, 5, 5);
		gbc_txtGroupName.gridx = 4;
		gbc_txtGroupName.gridy = 1;
		contentPane.add(txtGroupName, gbc_txtGroupName);
		txtGroupName.setColumns(10);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBackground(MAIN_COLOR_LIGHT);
		scrollPane_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Added",
				TitledBorder.LEADING, TitledBorder.TOP, null, TEXT_COLOR_LIGHT));
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 6;
		gbc_scrollPane_1.gridwidth = 3;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 8;
		gbc_scrollPane_1.gridy = 1;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);

		final DefaultListModel<IndividualContact> modelAdded = new DefaultListModel<>();

		if (groupToModify != null) {
			for (IndividualContact integrante : groupToModify.getParticipantes()) {
				modelAdded.addElement(integrante);
			}
		}

		final JList<IndividualContact> addedList = new JList<>(modelAdded);
		addedList.setCellRenderer(createListRenderer());
		addedList.setBackground(new Color(204, 255, 255));
		scrollPane_1.setViewportView(addedList);

		JButton btAddedContact = new JButton();
		btAddedContact.setBackground(SECONDARY_COLOR);
		btAddedContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IndividualContact selected = (IndividualContact) notAddedList.getSelectedValue();
				if (selected != null) {
					modelAdded.add(modelAdded.getSize(), selected);
					notAddedModel.remove(notAddedList.getSelectedIndex());
				}
			}
		});

		notAddedList.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					IndividualContact selected = (IndividualContact) notAddedList.getSelectedValue();
					modelAdded.add(modelAdded.getSize(), selected);
					notAddedModel.remove(notAddedList.getSelectedIndex());
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		addedList.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					IndividualContact selected = (IndividualContact) addedList.getSelectedValue();
					notAddedModel.add(notAddedModel.getSize(), selected);
					modelAdded.remove(addedList.getSelectedIndex());
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		btAddedContact.setIcon(
				new ImageIcon(GroupManagement.class.getResource("/umu/tds/apps/resources/flecha-hacia-derecha.png")));
		GridBagConstraints gbc_btAddedContact = new GridBagConstraints();
		gbc_btAddedContact.gridwidth = 2;
		gbc_btAddedContact.fill = GridBagConstraints.HORIZONTAL;
		gbc_btAddedContact.insets = new Insets(0, 0, 5, 5);
		gbc_btAddedContact.gridx = 5;
		gbc_btAddedContact.gridy = 3;
		contentPane.add(btAddedContact, gbc_btAddedContact);

		JButton btRemoveContact = new JButton("");
		btRemoveContact.setBackground(SECONDARY_COLOR);
		btRemoveContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IndividualContact selected = (IndividualContact) addedList.getSelectedValue();
				if (selected != null) {
					notAddedModel.add(notAddedModel.getSize(), selected);
					modelAdded.remove(addedList.getSelectedIndex());
				}
			}
		});
		btRemoveContact.setIcon(
				new ImageIcon(GroupManagement.class.getResource("/umu/tds/apps/resources/flecha-hacia-izquierda.png")));
		GridBagConstraints gbc_btRemoveContact = new GridBagConstraints();
		gbc_btRemoveContact.gridwidth = 2;
		gbc_btRemoveContact.fill = GridBagConstraints.HORIZONTAL;
		gbc_btRemoveContact.insets = new Insets(0, 0, 5, 5);
		gbc_btRemoveContact.gridx = 5;
		gbc_btRemoveContact.gridy = 4;
		contentPane.add(btRemoveContact, gbc_btRemoveContact);

		JButton btnNewButton = new JButton((groupToModify == null) ? "CREATE GROUP" : "MODIFY GROUP");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<IndividualContact> participantes = new LinkedList<>();
				for (int i = 0; i < modelAdded.size(); i++)
					participantes.add(modelAdded.get(i));

				if (groupToModify != null) {
					Group grupoModificado = Controlador.getInstancia().modificarGrupo(groupToModify,
							txtGroupName.getText(), participantes);
					int i = 0;
					while (modelContacts.get(i).getCodigo() != grupoModificado.getCodigo() && i < modelContacts.size())
						i++;
					if (modelContacts.get(i).getCodigo() == grupoModificado.getCodigo())
						modelContacts.set(i, grupoModificado);
				} else {
					Group nuevoGrupo = Controlador.getInstancia().crearGrupo(txtGroupName.getText(), participantes);

					// Si ha podido crear el grupo lo añade a la lista de contactos. En caso
					// contrario muestra error.
					if (nuevoGrupo != null) {
						modelContacts.add(modelContacts.getSize(), nuevoGrupo);
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(GroupManagement.this,
								"Unable to create group. Another group with the same name exists.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				GroupManagement.this.setVisible(false);
			}
		});
		btnNewButton.setBackground(SECONDARY_COLOR);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridwidth = 4;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 8;
		contentPane.add(btnNewButton, gbc_btnNewButton);
	}

	/**
	 * Renderer que permite mostrar los contactos en la lista
	 * 
	 * @return Objeto que le indica a la lista como mostrar los contactos en la
	 *         vista
	 */
	private static ListCellRenderer<? super Contact> createListRenderer() {
		return new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JPanel panel = new JPanel();
				JLabel label = new JLabel();
				IndividualContact contacto = (IndividualContact) value;
				label.setText(contacto.getNombre());
				panel.add(label);
				panel.setBackground((isSelected) ? SECONDARY_COLOR : list.getBackground());
				return panel;
			}
		};
	}

}
