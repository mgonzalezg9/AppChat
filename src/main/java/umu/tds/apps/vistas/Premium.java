package umu.tds.apps.vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Premium.class.getResource("/umu/tds/apps/resources/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 502, 372);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Premium.class.getResource("/umu/tds/apps/resources/icon.png")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblBecomePremiumFor = new JLabel("Become Premium for a low prize!");
		GridBagConstraints gbc_lblBecomePremiumFor = new GridBagConstraints();
		gbc_lblBecomePremiumFor.insets = new Insets(0, 0, 5, 0);
		gbc_lblBecomePremiumFor.gridx = 0;
		gbc_lblBecomePremiumFor.gridy = 1;
		contentPane.add(lblBecomePremiumFor, gbc_lblBecomePremiumFor);
		
		JLabel lblOnlyA = new JLabel("Only 15.99$ a month before January 2020");
		GridBagConstraints gbc_lblOnlyA = new GridBagConstraints();
		gbc_lblOnlyA.insets = new Insets(0, 0, 5, 0);
		gbc_lblOnlyA.gridx = 0;
		gbc_lblOnlyA.gridy = 2;
		contentPane.add(lblOnlyA, gbc_lblOnlyA);
		
		JButton btnStartNow = new JButton("START NOW");
		GridBagConstraints gbc_btnStartNow = new GridBagConstraints();
		gbc_btnStartNow.gridx = 0;
		gbc_btnStartNow.gridy = 3;
		contentPane.add(btnStartNow, gbc_btnStartNow);
	}

}
