package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.MAIN_COLOR_LIGHT;
import static umu.tds.apps.vistas.Theme.SECONDARY_COLOR;
import static umu.tds.apps.vistas.Theme.TEXT_COLOR_LIGHT;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.Group;
import umu.tds.apps.controlador.Controlador;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

public class GroupsListForModify extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<Contact> modelContacts;

	/**
	 * Create the frame.
	 */
	public GroupsListForModify(DefaultListModel<Contact> modelo) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/umu/tds/apps/resources/icon.png")));
		setTitle("Modify group");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 165);

		this.modelContacts = modelo;

		contentPane = new JPanel();
		contentPane.setBackground(MAIN_COLOR_LIGHT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 10, 0, 0, 0, 10, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblSeleccioneElGrupo = new JLabel("Choose the group to modify:");
		lblSeleccioneElGrupo.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblSeleccioneElGrupo = new GridBagConstraints();
		gbc_lblSeleccioneElGrupo.insets = new Insets(0, 0, 5, 0);
		gbc_lblSeleccioneElGrupo.gridx = 0;
		gbc_lblSeleccioneElGrupo.gridy = 1;
		contentPane.add(lblSeleccioneElGrupo, gbc_lblSeleccioneElGrupo);

		List<Group> grupos = Controlador.getInstancia().getUsuarioActual().getGrupos();
		DefaultComboBoxModel<Group> model = new DefaultComboBoxModel<>();
		for (int i = 0; i < grupos.size(); i++)
			model.addElement(grupos.get(i));

		JComboBox<Group> comboBox = new JComboBox<>(model);
		comboBox.setRenderer(createComboBoxRenderer());
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 2;
		contentPane.add(comboBox, gbc_comboBox);

		JButton btnModificar = new JButton("Modify");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Group selected = (Group) comboBox.getSelectedItem();
				if (selected != null) {
					// Si soy el administrador puedo modificar el grupo
					if (selected.getAdmin().getCodigo() == Controlador.getInstancia().getUsuarioActual().getCodigo()) {
						GroupManagement window = new GroupManagement(modelContacts, (Group) comboBox.getSelectedItem());
						window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						window.setVisible(true);
						GroupsListForModify.this.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(GroupsListForModify.this,
								"Unable to modify the group. Please check your privileges.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnModificar.setBackground(SECONDARY_COLOR);
		GridBagConstraints gbc_btnModificar = new GridBagConstraints();
		gbc_btnModificar.insets = new Insets(0, 0, 5, 0);
		gbc_btnModificar.gridx = 0;
		gbc_btnModificar.gridy = 3;
		contentPane.add(btnModificar, gbc_btnModificar);
	}

	private static ListCellRenderer<? super Group> createComboBoxRenderer() {
		return new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JPanel panel = new JPanel();
				JLabel label = new JLabel();
				Group contacto = (Group) value;
				label.setText(contacto.getNombre());
				panel.add(label);
				panel.setBackground((isSelected) ? SECONDARY_COLOR : list.getBackground());
				return panel;
			}
		};
	}
}
