package umu.tds.apps.vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import umu.tds.apps.AppChat.Status;
import umu.tds.apps.AppChat.User;
import umu.tds.apps.controlador.Controlador;

import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class Estados extends JFrame {

	private JPanel contentPane;
	private JLabel lblFraseProfunda;
	private JLabel lblEstadoSeleccionado;
	private JLabel lblNewLabel;
	private JList<User> list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Estados frame = new Estados();
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
	public Estados() {
		setTitle("Status");
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(Estados.class.getResource("/umu/tds/apps/resources/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 382);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panelIzq = new JPanel();
		panelIzq.setBackground(Color.WHITE);
		GridBagConstraints gbc_panelIzq = new GridBagConstraints();
		gbc_panelIzq.insets = new Insets(0, 0, 0, 5);
		gbc_panelIzq.fill = GridBagConstraints.BOTH;
		gbc_panelIzq.gridx = 0;
		gbc_panelIzq.gridy = 0;
		contentPane.add(panelIzq, gbc_panelIzq);
		GridBagLayout gbl_panelIzq = new GridBagLayout();
		gbl_panelIzq.columnWidths = new int[] { 0, 0 };
		gbl_panelIzq.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panelIzq.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelIzq.rowWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		panelIzq.setLayout(gbl_panelIzq);

		JPanel panelMiEstado = new JPanel();
		

		JPanel panel_2 = new JPanel();
		panel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
				panel_2.setBackground(new Color(255, 171, 0));
				list.clearSelection();
				Status s = Controlador.getEstado(Controlador.getUsuario());
				lblEstadoSeleccionado.setIcon(s.getImg());
				lblFraseProfunda.setText(s.getFrase());
			}
		});
		panel_2.setBackground(new Color(141, 110, 99));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		panelIzq.add(panel_2, gbc_panel_2);

		lblNewLabel = new JLabel("My status");
		panel_2.add(lblNewLabel);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel label = new JLabel("");
		panel_2.add(label);
		label.setIcon(new ImageIcon(Estados.class.getResource("/umu/tds/apps/resources/diego.jpg")));
		panelMiEstado.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelMiEstado.setBackground(new Color(141, 110, 99));
		GridBagConstraints gbc_panelMiEstado = new GridBagConstraints();
		gbc_panelMiEstado.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelMiEstado.insets = new Insets(0, 0, 5, 0);
		gbc_panelMiEstado.gridx = 0;
		gbc_panelMiEstado.gridy = 1;
		panelIzq.add(panelMiEstado, gbc_panelMiEstado);
		panelMiEstado.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel label_1 = new JLabel("My contacts");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelMiEstado.add(label_1);

		JPanel listaEstados = new JPanel();
		listaEstados.setBackground(Color.WHITE);
		GridBagConstraints gbc_listaEstados = new GridBagConstraints();
		gbc_listaEstados.fill = GridBagConstraints.BOTH;
		gbc_listaEstados.gridx = 0;
		gbc_listaEstados.gridy = 2;
		panelIzq.add(listaEstados, gbc_listaEstados);
		listaEstados.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(null);
		panel_3.setBackground(Color.WHITE);
		listaEstados.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel_3.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 256, 0 };
		gbl_panel.rowHeights = new int[] { 128, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		DefaultListModel<User> listModel = new DefaultListModel<>();
		listModel.addElement(
				new User(new ImageIcon(getClass().getResource("/umu/tds/apps/resources/diego.jpg")), "Mi crush"));
		listModel.addElement(
				new User(new ImageIcon(getClass().getResource("/umu/tds/apps/resources/diego.jpg")), "Su padre"));
		listModel.addElement(
				new User(new ImageIcon(getClass().getResource("/umu/tds/apps/resources/diego.jpg")), "Su abuelo"));
		listModel.addElement(
				new User(new ImageIcon(getClass().getResource("/umu/tds/apps/resources/diego.jpg")), "Su crush"));
		listModel.addElement(new User(
				new ImageIcon(getClass().getResource("/umu/tds/apps/resources/Circle-PNG-Picture.png")), "Yo"));

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.VERTICAL;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);
		scrollPane.setBorder(null);

		list = new JList<User>();
		list.setFont(new Font("Tahoma", Font.PLAIN, 15));
		list.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
				panel_2.setBackground(new Color(141, 110, 99));
				User u = (User) list.getSelectedValue();
				Status s = Controlador.getEstado(u);
				lblFraseProfunda.setText(s.getFrase());
				lblEstadoSeleccionado.setIcon(s.getImg());
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
				panel_2.setBackground(new Color(141, 110, 99));
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		list.setBackground(Color.WHITE);
		list.setSelectionBackground(new Color(255, 171, 0));
		list.setSelectionForeground(new Color(255, 255, 255));
		list.setBorder(null);
		list.setCellRenderer(new UserRenderer());
		scrollPane.setViewportView(list);
		list.setModel(listModel);

		JPanel panelDer = new JPanel();
		panelDer.setBackground(Color.WHITE);
		GridBagConstraints gbc_panelDer = new GridBagConstraints();
		gbc_panelDer.fill = GridBagConstraints.BOTH;
		gbc_panelDer.gridx = 1;
		gbc_panelDer.gridy = 0;
		contentPane.add(panelDer, gbc_panelDer);
		GridBagLayout gbl_panelDer = new GridBagLayout();
		gbl_panelDer.columnWidths = new int[] { 0, 0 };
		gbl_panelDer.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelDer.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelDer.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		panelDer.setLayout(gbl_panelDer);

		lblEstadoSeleccionado = new JLabel("");
		GridBagConstraints gbc_lblEstadoSeleccionado = new GridBagConstraints();
		gbc_lblEstadoSeleccionado.insets = new Insets(0, 0, 5, 0);
		gbc_lblEstadoSeleccionado.gridx = 0;
		gbc_lblEstadoSeleccionado.gridy = 0;
		panelDer.add(lblEstadoSeleccionado, gbc_lblEstadoSeleccionado);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(190, 156, 145));
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.SOUTH;
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panelDer.add(panel_1, gbc_panel_1);

		lblFraseProfunda = new JLabel("Frase profunda");
		panel_1.add(lblFraseProfunda);
		lblFraseProfunda.setBorder(null);
	}

}
