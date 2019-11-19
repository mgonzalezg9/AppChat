package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class Busqueda extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
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
	public Busqueda() {
		setTitle("Search");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/umu/tds/apps/resources/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 462);
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
		label.setIcon(new ImageIcon(Busqueda.class.getResource("/umu/tds/apps/resources/icon.png")));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridheight = 2;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		contentPane.add(label, gbc_label);
		
		JLabel lblSender = new JLabel("Sender");
		lblSender.setForeground(LETTERS_COLOR);
		GridBagConstraints gbc_lblSender = new GridBagConstraints();
		gbc_lblSender.anchor = GridBagConstraints.EAST;
		gbc_lblSender.insets = new Insets(0, 0, 5, 5);
		gbc_lblSender.gridx = 1;
		gbc_lblSender.gridy = 0;
		contentPane.add(lblSender, gbc_lblSender);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Alf", "Manu", "Perico"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 0;
		contentPane.add(comboBox, gbc_comboBox);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setForeground(LETTERS_COLOR);
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 3;
		gbc_lblDate.gridy = 0;
		contentPane.add(lblDate, gbc_lblDate);
		
		JDateChooser dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.gridx = 4;
		gbc_dateChooser.gridy = 0;
		contentPane.add(dateChooser, gbc_dateChooser);
		
		JLabel label_1 = new JLabel("-");
		label_1.setForeground(LETTERS_COLOR);
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 5;
		gbc_label_1.gridy = 0;
		contentPane.add(label_1, gbc_label_1);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		GridBagConstraints gbc_dateChooser_1 = new GridBagConstraints();
		gbc_dateChooser_1.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser_1.gridx = 6;
		gbc_dateChooser_1.gridy = 0;
		contentPane.add(dateChooser_1, gbc_dateChooser_1);
		
		JLabel lblText = new JLabel("Text:");
		lblText.setForeground(LETTERS_COLOR);
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

		BubbleText burbuja = new BubbleText(chat, "Primer mensaje encontrado", Color.LIGHT_GRAY, "Dieguin",
				BubbleText.RECEIVED);
		chat.add(burbuja);
		BubbleText burbuja1 = new BubbleText(chat, "Segundo mensaje encontrado chavales", Color.LIGHT_GRAY, "Manusín",
				BubbleText.RECEIVED);
		chat.add(burbuja1);
		BubbleText burbuja2 = new BubbleText(chat, "Gensanta, si es que no paro de encontrar mensajes", Color.LIGHT_GRAY, "DiegoOriginals",
				BubbleText.RECEIVED);
		chat.add(burbuja2);
		
		JButton btnNewButton = new JButton("SEARCH");
		btnNewButton.setBackground(SECONDARY_COLOR);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 3;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 5;
		contentPane.add(btnNewButton, gbc_btnNewButton);
	}

}
