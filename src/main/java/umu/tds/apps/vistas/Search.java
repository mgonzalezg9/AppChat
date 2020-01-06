package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.*;

import umu.tds.apps.AppChat.*;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Toolkit;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import com.toedter.calendar.JDateChooser;

import tds.BubbleText;
import umu.tds.apps.controlador.Controlador;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;

public class Search extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private List<IndividualContact> misContactos;

	/**
	 * Create the frame.
	 */
	public Search() {
		setTitle("Search");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Search.class.getResource("/umu/tds/apps/resources/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 462);
		
		misContactos = Controlador.getInstancia().getUsuarioActual().getContactosIndividuales();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(MAIN_COLOR_LIGHT);
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 40, 0, 0, 20, 0, 10, 0};
		gbl_contentPane.rowHeights = new int[]{62, 64, 10, 0, 10, 0, 10, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Search.class.getResource("/umu/tds/apps/resources/icon.png")));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridheight = 2;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		contentPane.add(label, gbc_label);
		
		JLabel lblSender = new JLabel("Sender");
		lblSender.setForeground(TEXT_COLOR_DARK);
		GridBagConstraints gbc_lblSender = new GridBagConstraints();
		gbc_lblSender.anchor = GridBagConstraints.EAST;
		gbc_lblSender.insets = new Insets(0, 0, 5, 5);
		gbc_lblSender.gridx = 1;
		gbc_lblSender.gridy = 0;
		contentPane.add(lblSender, gbc_lblSender);
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		model.addElement("All");
		misContactos.stream().forEach(c -> model.addElement(c.getNombre()));
		
		JComboBox<String> comboBox = new JComboBox<>(model);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 0;
		contentPane.add(comboBox, gbc_comboBox);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setForeground(TEXT_COLOR_DARK);
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 3;
		gbc_lblDate.gridy = 0;
		contentPane.add(lblDate, gbc_lblDate);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.getDateEditor().getUiComponent().setBackground(MAIN_COLOR);
		dateChooser.getDateEditor().getUiComponent().setForeground(MAIN_COLOR);
		dateChooser.getDateEditor().getUiComponent().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		dateChooser.setForeground(MAIN_COLOR);
		dateChooser.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		dateChooser.setBackground(MAIN_COLOR);
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.gridx = 4;
		gbc_dateChooser.gridy = 0;
		contentPane.add(dateChooser, gbc_dateChooser);
		
		JLabel label_1 = new JLabel("-");
		label_1.setForeground(TEXT_COLOR_DARK);
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 5;
		gbc_label_1.gridy = 0;
		contentPane.add(label_1, gbc_label_1);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.getDateEditor().getUiComponent().setBackground(MAIN_COLOR);
		dateChooser_1.getDateEditor().getUiComponent().setForeground(MAIN_COLOR);
		dateChooser_1.getDateEditor().getUiComponent().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		dateChooser_1.setForeground(MAIN_COLOR);
		dateChooser_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		dateChooser_1.setBackground(MAIN_COLOR);
		GridBagConstraints gbc_dateChooser_1 = new GridBagConstraints();
		gbc_dateChooser_1.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser_1.gridx = 6;
		gbc_dateChooser_1.gridy = 0;
		contentPane.add(dateChooser_1, gbc_dateChooser_1);
		
		JLabel lblText = new JLabel("Text:");
		lblText.setForeground(TEXT_COLOR_DARK);
		GridBagConstraints gbc_lblText = new GridBagConstraints();
		gbc_lblText.insets = new Insets(0, 0, 5, 5);
		gbc_lblText.gridx = 1;
		gbc_lblText.gridy = 1;
		contentPane.add(lblText, gbc_lblText);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		textPane.setForeground(TEXT_COLOR_LIGHT);
		textPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textPane.setCaretColor(TEXT_COLOR_LIGHT);
		textPane.setBackground(MAIN_COLOR);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridwidth = 6;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 3;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);
		
		JPanel chat = new JPanel();
		chat.setBackground(CHAT_COLOR);
		scrollPane_1.setViewportView(chat);
		chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
		chat.setSize(400,700);
		
		JButton btnNewButton = new JButton("SEARCH");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Obtengo los mensajes que cumplen con los datos
				LocalDateTime fechaInicio = (dateChooser.getDate() == null) ? null : dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				LocalDateTime fechaFin = (dateChooser_1.getDate() == null) ? null : dateChooser_1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				String emisorMensaje = comboBox.getSelectedItem().toString();
				if (emisorMensaje != "All") {
					List<String> nombreUsuario = misContactos.stream().filter(c -> c.getNombre().equals(comboBox.getSelectedItem().toString())).filter(c -> c instanceof IndividualContact).map(c -> ((IndividualContact) c).getUsuario().getName()).collect(Collectors.toList());
					emisorMensaje = nombreUsuario.get(0);
				}
				List<Message> mensajes = Controlador.getInstancia().buscarMensajes(emisorMensaje, fechaInicio, fechaFin, textPane.getText());
				
				chat.removeAll();
				chat.repaint();
				
				// Muestro los mensajes encontrados
				mensajes.stream().map(m -> {
					String emisor;
					int direccionMensaje;
					Color colorBurbuja;

					if (m.getEmisor().equals(Controlador.getInstancia().getUsuarioActual())) {
						colorBurbuja = SENT_MESSAGE_COLOR;
						emisor = "Tú";
						direccionMensaje = BubbleText.SENT;
					} else {
						colorBurbuja = INCOMING_MESSAGE_COLOR;
						// Pongo su nombre de contacto
						emisor = Controlador.getInstancia().getNombreContacto(m.getEmisor());
						if (m.getReceptor() instanceof Group) {
							emisor = emisor + "@" + m.getReceptor().getNombre();
						}
						direccionMensaje = BubbleText.RECEIVED;
					}

					if (m.getTexto().isEmpty()) {
						return new BubbleText(chat, m.getEmoticono(), colorBurbuja, emisor, direccionMensaje, MESSAGE_SIZE);
					}
					
					return new BubbleText(chat, m.getTexto(), colorBurbuja, emisor, direccionMensaje, MESSAGE_SIZE);
				}).forEach(b -> chat.add(b));
			}
		});
		btnNewButton.setBackground(SECONDARY_COLOR);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 3;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 5;
		contentPane.add(btnNewButton, gbc_btnNewButton);
	}

}
