package umu.tds.apps.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelListener;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.BoxLayout;
import tds.BubbleText;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {
	private JPanel contentPane;
	private JPanel chat;
	private JTable table;
	private JTable table_1;
	private JLabel profilePhoto;
	private JTextField textField;

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

	private void sendMessage(JPanel panel, JTextField textField) throws IllegalArgumentException {
		BubbleText burbuja = new BubbleText(panel, textField.getText(), Color.GREEN, "Tú", BubbleText.SENT);
		chat.add(burbuja);
		textField.setText(null);
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/umu/tds/apps/resources/icon.png")));
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

		profilePhoto = new JLabel();
		profilePhoto.setIcon(new ImageIcon(
				Principal.class.getResource("/umu/tds/apps/resources/173312_magnifying-glass-icon-png.png")));
		table_1 = new JTable();
		table_1.setShowVerticalLines(false);
		table_1.setShowGrid(false);
		table_1.setBorder(null);
		table_1.setModel(new TableModel() {
			private Object[] columnNames = { "Profile photo", "Name" };

			private Object[][] data = { { profilePhoto, "Diego Sevilla" },

			};

			public int getColumnCount() {
				return columnNames.length;
			}

			public int getRowCount() {
				return data.length;
			}

			public String getColumnName(int col) {
				return columnNames[col].toString();
			}

			public Object getValueAt(int row, int col) {
				return data[row][col];
			}

			public Class<?> getColumnClass(int c) {
				return Icon.class;
			}

			@Override
			public void addTableModelListener(TableModelListener l) {

			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}

			@Override
			public void removeTableModelListener(TableModelListener l) {

			}

			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

			}
		});
		scrollPane.setViewportView(table_1);

		JPanel chatPersonal = new JPanel();
		chatPersonal.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_chatPersonal = new GridBagConstraints();
		gbc_chatPersonal.fill = GridBagConstraints.BOTH;
		gbc_chatPersonal.gridx = 1;
		gbc_chatPersonal.gridy = 1;
		contentPane.add(chatPersonal, gbc_chatPersonal);
		GridBagLayout gbl_chatPersonal = new GridBagLayout();
		gbl_chatPersonal.columnWidths = new int[] { 66, 0 };
		gbl_chatPersonal.rowHeights = new int[] { 20, 0, 0 };
		gbl_chatPersonal.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_chatPersonal.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		chatPersonal.setLayout(gbl_chatPersonal);

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		chatPersonal.add(scrollPane_1, gbc_scrollPane_1);

		chat = new JPanel();
		scrollPane_1.setViewportView(chat);
		chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
		chat.setSize(400, 700);

		BubbleText burbuja = new BubbleText(chat, "Hola, ¿Como van las burbujas? xD", Color.LIGHT_GRAY, "Dieguin",
				BubbleText.RECEIVED);
		chat.add(burbuja);

		JPanel writeText = new JPanel();
		writeText.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_writeText = new GridBagConstraints();
		gbc_writeText.anchor = GridBagConstraints.SOUTH;
		gbc_writeText.fill = GridBagConstraints.HORIZONTAL;
		gbc_writeText.gridx = 0;
		gbc_writeText.gridy = 1;
		chatPersonal.add(writeText, gbc_writeText);
		GridBagLayout gbl_writeText = new GridBagLayout();
		gbl_writeText.columnWidths = new int[] { 49, 25, 0, 0 };
		gbl_writeText.rowHeights = new int[] { 20, 0 };
		gbl_writeText.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_writeText.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		writeText.setLayout(gbl_writeText);

		JLabel lblEmoji = new JLabel("");
		lblEmoji.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO
			}
		});
		lblEmoji.setIcon(BubbleText.getEmoji(new Random().nextInt(BubbleText.MAXICONO + 1)));
		GridBagConstraints gbc_lblEmoji = new GridBagConstraints();
		gbc_lblEmoji.anchor = GridBagConstraints.EAST;
		gbc_lblEmoji.fill = GridBagConstraints.VERTICAL;
		gbc_lblEmoji.insets = new Insets(0, 0, 0, 5);
		gbc_lblEmoji.gridx = 0;
		gbc_lblEmoji.gridy = 0;
		writeText.add(lblEmoji, gbc_lblEmoji);

		JLabel lblSend = new JLabel("");
		lblSend.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					sendMessage(chat, textField);
				} catch (IllegalArgumentException e2) {
				}
			}
		});

		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_2.gridx = 1;
		gbc_scrollPane_2.gridy = 0;
		writeText.add(scrollPane_2, gbc_scrollPane_2);

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					sendMessage(chat, textField);
				} catch (IllegalArgumentException e) {
				}
			}
		});
		scrollPane_2.setViewportView(textField);
		textField.setColumns(10);
		lblSend.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/paper plane.png")));
		GridBagConstraints gbc_lblSend = new GridBagConstraints();
		gbc_lblSend.gridx = 2;
		gbc_lblSend.gridy = 0;
		writeText.add(lblSend, gbc_lblSend);
	}

}
