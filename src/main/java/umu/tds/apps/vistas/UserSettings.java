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
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;

public class UserSettings extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserSettings frame = new UserSettings();
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
	public UserSettings() {
		setTitle("Settings");
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserSettings.class.getResource("/umu/tds/apps/resources/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 822, 575);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 5, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		
		JLabel label = new JLabel("");
		panel.add(label);
		label.setIcon(new ImageIcon(UserSettings.class.getResource("/umu/tds/apps/resources/user.png")));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		contentPane.add(panel_1, gbc_panel_1);
		
		JButton button_3 = new JButton("-");
		panel_1.add(button_3);
		
		JButton button = new JButton("<");
		panel_1.add(button);
		
		JLabel label_1 = new JLabel("3/10");
		panel_1.add(label_1);
		
		JButton button_1 = new JButton(">");
		panel_1.add(button_1);
		
		JButton button_2 = new JButton("+");
		panel_1.add(button_2);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 2;
		contentPane.add(panel_4, gbc_panel_4);
		
		JLabel lblNewLabel = new JLabel("Dieguillo_777");
		panel_4.add(lblNewLabel);
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 5, 5);
		gbc_panel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 3;
		contentPane.add(panel_5, gbc_panel_5);
		
		JLabel lblGreeting = new JLabel("Greeting:");
		panel_5.add(lblGreeting);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JTextArea txtrRespetandoElNnn = new JTextArea();
		scrollPane.setViewportView(txtrRespetandoElNnn);
		txtrRespetandoElNnn.setPreferredSize(new Dimension(200, 100));
		txtrRespetandoElNnn.setMinimumSize(new Dimension(10, 25));
		txtrRespetandoElNnn.setText("A tope con el NNN. ¿Y tú?");
	}

}
