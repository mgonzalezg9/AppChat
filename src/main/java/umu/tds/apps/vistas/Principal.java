package umu.tds.apps.vistas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelListener;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setTitle("AppChat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 781, 637);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel settingsIzq = new JPanel();
		settingsIzq.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_settingsIzq = new GridBagConstraints();
		gbc_settingsIzq.insets = new Insets(0, 0, 5, 5);
		gbc_settingsIzq.fill = GridBagConstraints.BOTH;
		gbc_settingsIzq.gridx = 0;
		gbc_settingsIzq.gridy = 0;
		contentPane.add(settingsIzq, gbc_settingsIzq);
		GridBagLayout gbl_settingsIzq = new GridBagLayout();
		gbl_settingsIzq.columnWidths = new int[] { 0, 0, 0 };
		gbl_settingsIzq.rowHeights = new int[] { 10, 0 };
		gbl_settingsIzq.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_settingsIzq.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		settingsIzq.setLayout(gbl_settingsIzq);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		settingsIzq.add(panel_1, gbc_panel_1);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/Circle-PNG-Picture.png")));
		panel_1.add(label);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 0;
		settingsIzq.add(panel_2, gbc_panel_2);

		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/Circle-PNG-Picture.png")));
		panel_2.add(label_2);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/3points.png")));
		panel_2.add(label_1);

		JPanel settingsDer = new JPanel();
		settingsDer.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_settingsDer = new GridBagConstraints();
		gbc_settingsDer.insets = new Insets(0, 0, 5, 0);
		gbc_settingsDer.fill = GridBagConstraints.BOTH;
		gbc_settingsDer.gridx = 1;
		gbc_settingsDer.gridy = 0;
		contentPane.add(settingsDer, gbc_settingsDer);
		GridBagLayout gbl_settingsDer = new GridBagLayout();
		gbl_settingsDer.columnWidths = new int[] { 0, 0, 0 };
		gbl_settingsDer.rowHeights = new int[] { 0, 0 };
		gbl_settingsDer.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_settingsDer.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		settingsDer.setLayout(gbl_settingsDer);

		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_3.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.VERTICAL;
		gbc_panel_3.anchor = GridBagConstraints.WEST;
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		settingsDer.add(panel_3, gbc_panel_3);

		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/Circle-PNG-Picture.png")));
		panel_3.add(label_3);

		JLabel lblDiegoSevilla = new JLabel("Diego Sevilla");
		panel_3.add(lblDiegoSevilla);

		JPanel panel = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel.getLayout();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTHEAST;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		settingsDer.add(panel, gbc_panel);

		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(
				Principal.class.getResource("/umu/tds/apps/resources/173312_magnifying-glass-icon-png.png")));
		panel.add(label_4);

		JLabel label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/3points.png")));
		panel.add(label_5);

		JPanel listaChats = new JPanel();
		listaChats.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_listaChats = new GridBagConstraints();
		gbc_listaChats.insets = new Insets(0, 0, 0, 5);
		gbc_listaChats.fill = GridBagConstraints.BOTH;
		gbc_listaChats.gridx = 0;
		gbc_listaChats.gridy = 1;
		contentPane.add(listaChats, gbc_listaChats);
		listaChats.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		listaChats.add(scrollPane);

		JLabel profilePhoto = new JLabel();
		profilePhoto.setIcon(new ImageIcon(
				Principal.class.getResource("/umu/tds/apps/resources/173312_magnifying-glass-icon-png.png")));
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(new Object[][] { { profilePhoto, null }, },
				new String[] { "New column", "New column" }));
		scrollPane.setViewportView(table_1);

		JPanel chatPersonal = new JPanel();
		chatPersonal.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_chatPersonal = new GridBagConstraints();
		gbc_chatPersonal.fill = GridBagConstraints.BOTH;
		gbc_chatPersonal.gridx = 1;
		gbc_chatPersonal.gridy = 1;
		contentPane.add(chatPersonal, gbc_chatPersonal);
	}

}
