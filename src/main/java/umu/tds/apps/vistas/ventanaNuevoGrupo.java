package umu.tds.apps.vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Color;

public class ventanaNuevoGrupo extends JFrame {

	private JPanel contentPane;
	private JTextField txtGroupName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventanaNuevoGrupo frame = new ventanaNuevoGrupo();
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
	public ventanaNuevoGrupo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 497, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{10, 50, 50, 50, 20, 30, 0, 20, 50, 50, 50, 10, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 50, 0, 0, 50, 0, 18, 10, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel settings = new JPanel();
		settings.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_settings = new GridBagConstraints();
		gbc_settings.gridwidth = 12;
		gbc_settings.insets = new Insets(0, 0, 5, 0);
		gbc_settings.fill = GridBagConstraints.BOTH;
		gbc_settings.gridx = 0;
		gbc_settings.gridy = 0;
		contentPane.add(settings, gbc_settings);
		GridBagLayout gbl_settings = new GridBagLayout();
		gbl_settings.columnWidths = new int[]{0, 0, 0};
		gbl_settings.rowHeights = new int[]{0, 0};
		gbl_settings.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_settings.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		settings.setLayout(gbl_settings);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		settings.add(panel, gbc_panel);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ventanaNuevoGrupo.class.getResource("/umu/tds/apps/resources/diego.jpg")));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Diego Sevilla");
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.EAST;
		gbc_panel_1.fill = GridBagConstraints.VERTICAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		settings.add(panel_1, gbc_panel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(ventanaNuevoGrupo.class.getResource("/umu/tds/apps/resources/173312_magnifying-glass-icon-png.png")));
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(ventanaNuevoGrupo.class.getResource("/umu/tds/apps/resources/3points.png")));
		panel_1.add(lblNewLabel_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Contacts", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 6;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JList contactList = new JList();
		contactList.setBackground(new Color(255, 204, 153));
		scrollPane.setViewportView(contactList);
		contactList.setModel(new AbstractListModel() {
			String[] values = new String[] {"dfgsdfg", "sdf", "gsdfg", "sdf", "gsd", "fg", "sd", "fg", "sdf", "g", "sdf", "g", "sdfg", "sd", "fg", "sd", "fg", "sdf", "g", "sdfg", "sdf", "g"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		txtGroupName = new JTextField();
		txtGroupName.setText("Group name..");
		GridBagConstraints gbc_txtGroupName = new GridBagConstraints();
		gbc_txtGroupName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGroupName.gridwidth = 4;
		gbc_txtGroupName.insets = new Insets(0, 0, 5, 5);
		gbc_txtGroupName.gridx = 4;
		gbc_txtGroupName.gridy = 1;
		contentPane.add(txtGroupName, gbc_txtGroupName);
		txtGroupName.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Added", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 6;
		gbc_scrollPane_1.gridwidth = 3;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 8;
		gbc_scrollPane_1.gridy = 1;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);
		
		JList addedList = new JList();
		addedList.setBackground(new Color(204, 255, 255));
		addedList.setModel(new AbstractListModel() {
			String[] values = new String[] {"sdfsd", "sd", "f", "f", "f", "f", "f", "ff", "f", "f", "fasdfsadf", "asdf", "asd", "f", "asdf", "as", "df", "as", "df", "as", "df", "asd", "f", "asdf", "asd", "f"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane_1.setViewportView(addedList);
		
		JButton btAddedContact = new JButton("");
		btAddedContact.setIcon(new ImageIcon(ventanaNuevoGrupo.class.getResource("/umu/tds/apps/resources/flecha-hacia-derecha.png")));
		GridBagConstraints gbc_btAddedContact = new GridBagConstraints();
		gbc_btAddedContact.gridwidth = 2;
		gbc_btAddedContact.fill = GridBagConstraints.HORIZONTAL;
		gbc_btAddedContact.insets = new Insets(0, 0, 5, 5);
		gbc_btAddedContact.gridx = 5;
		gbc_btAddedContact.gridy = 3;
		contentPane.add(btAddedContact, gbc_btAddedContact);
		
		JButton btRemoveContact = new JButton("");
		btRemoveContact.setIcon(new ImageIcon(ventanaNuevoGrupo.class.getResource("/umu/tds/apps/resources/flecha-hacia-izquierda.png")));
		GridBagConstraints gbc_btRemoveContact = new GridBagConstraints();
		gbc_btRemoveContact.gridwidth = 2;
		gbc_btRemoveContact.fill = GridBagConstraints.HORIZONTAL;
		gbc_btRemoveContact.insets = new Insets(0, 0, 5, 5);
		gbc_btRemoveContact.gridx = 5;
		gbc_btRemoveContact.gridy = 4;
		contentPane.add(btRemoveContact, gbc_btRemoveContact);
		
		JButton btnNewButton = new JButton("CREATE GROUP");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridwidth = 4;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 8;
		contentPane.add(btnNewButton, gbc_btnNewButton);
	}

}
