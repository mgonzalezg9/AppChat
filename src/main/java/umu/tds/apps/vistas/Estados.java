package umu.tds.apps.vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

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

public class Estados extends JFrame {

	private JPanel contentPane;
	private JLabel lblFraseProfunda;
	private JLabel lblEstadoSeleccionado;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Estados.class.getResource("/umu/tds/apps/resources/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		panel.add(panel_2, gbc_panel_2);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Estados.class.getResource("/umu/tds/apps/resources/diego.jpg")));
		panel_2.add(label);
		
		JLabel lblNewLabel = new JLabel("Mi estado");
		panel_2.add(lblNewLabel);
		
		JPanel listaEstados = new JPanel();
		listaEstados.setBackground(Color.WHITE);
		GridBagConstraints gbc_listaEstados = new GridBagConstraints();
		gbc_listaEstados.fill = GridBagConstraints.BOTH;
		gbc_listaEstados.gridx = 0;
		gbc_listaEstados.gridy = 1;
		panel.add(listaEstados, gbc_listaEstados);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		listaEstados.add(scrollPane);
		
		JList list = new JList();
		list.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("hola");
				User u = (User) list.getSelectedValue();
				Status s = Controlador.getEstado(u);
				lblFraseProfunda.setText(s.getFrase());
				lblEstadoSeleccionado.setIcon(s.getImg());
			}
		});
		list.setBackground(Color.WHITE);
		list.setBorder(null);
		list.setCellRenderer(new UserRenderer());
		DefaultListModel<User> listModel = new DefaultListModel<>();
		listModel.addElement(new User(new ImageIcon(getClass().getResource("/umu/tds/apps/resources/diego.jpg")), "Mi crush"));
		listModel.addElement(new User(new ImageIcon(getClass().getResource("/umu/tds/apps/resources/diego.jpg")), "Su padre"));
		listModel.addElement(new User(new ImageIcon(getClass().getResource("/umu/tds/apps/resources/diego.jpg")), "Su abuelo"));
		listModel.addElement(new User(new ImageIcon(getClass().getResource("/umu/tds/apps/resources/diego.jpg")), "Su crush"));
		listModel.addElement(new User(new ImageIcon(getClass().getResource("/umu/tds/apps/resources/Circle-PNG-Picture.png")), "Yo"));
		list.setModel(listModel);
		scrollPane.setViewportView(list);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		contentPane.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		lblEstadoSeleccionado = new JLabel("");
		GridBagConstraints gbc_lblEstadoSeleccionado = new GridBagConstraints();
		gbc_lblEstadoSeleccionado.insets = new Insets(0, 0, 5, 0);
		gbc_lblEstadoSeleccionado.gridx = 0;
		gbc_lblEstadoSeleccionado.gridy = 0;
		panel_1.add(lblEstadoSeleccionado, gbc_lblEstadoSeleccionado);
		
		lblFraseProfunda = new JLabel("Frase profunda");
		GridBagConstraints gbc_lblFraseProfunda = new GridBagConstraints();
		gbc_lblFraseProfunda.anchor = GridBagConstraints.SOUTH;
		gbc_lblFraseProfunda.gridx = 0;
		gbc_lblFraseProfunda.gridy = 1;
		panel_1.add(lblFraseProfunda, gbc_lblFraseProfunda);
	}

}
