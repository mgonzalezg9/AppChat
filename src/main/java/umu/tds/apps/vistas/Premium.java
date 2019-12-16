package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import umu.tds.apps.controlador.Controlador;

import java.awt.Toolkit;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Premium extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Premium frame = new Premium();
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
	public Premium() {
		setTitle("Get premium");
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(Premium.class.getResource("/umu/tds/apps/resources/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 502, 372);
		contentPane = new JPanel();
		contentPane.setBackground(MAIN_COLOR_LIGHT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Premium.class.getResource("/umu/tds/apps/resources/icon.png")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBackground(MAIN_COLOR_LIGHT);
		panel.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel lblBeco = new JLabel("Become premium for a LOW price!");
		lblBeco.setForeground(TEXT_COLOR_LIGHT);
		panel.add(lblBeco);

		JLabel lblNowWithDiscounts = new JLabel("Now with discounts to the youth");
		lblNowWithDiscounts.setForeground(TEXT_COLOR_LIGHT);
		panel.add(lblNowWithDiscounts);

		JLabel lblOnlyA = new JLabel("Only " + Controlador.getInstancia().getPrecio() + "$ a month for being yourself");
		lblOnlyA.setForeground(TEXT_COLOR_LIGHT);
		GridBagConstraints gbc_lblOnlyA = new GridBagConstraints();
		gbc_lblOnlyA.insets = new Insets(0, 0, 5, 0);
		gbc_lblOnlyA.gridx = 0;
		gbc_lblOnlyA.gridy = 2;
		contentPane.add(lblOnlyA, gbc_lblOnlyA);

		JButton btnStartNow = new JButton("START NOW");
		btnStartNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Controlador.getInstancia().getUsuarioActual().isPremium()) {
					JOptionPane.showMessageDialog(Premium.this,
							"You have already paid the subscription. Enjoy", "Already premium",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					Controlador.getInstancia().hacerPremium();
					setVisible(false);
				}
			}
		});
		btnStartNow.setBackground(SECONDARY_COLOR);
		GridBagConstraints gbc_btnStartNow = new GridBagConstraints();
		gbc_btnStartNow.gridx = 0;
		gbc_btnStartNow.gridy = 3;
		contentPane.add(btnStartNow, gbc_btnStartNow);
	}

}
