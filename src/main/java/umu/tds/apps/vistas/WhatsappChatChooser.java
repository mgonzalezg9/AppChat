package umu.tds.apps.vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import umu.tds.apps.cargador.MessagesCharger;
import umu.tds.apps.controlador.Controlador;

import java.awt.Toolkit;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WhatsappChatChooser extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public WhatsappChatChooser(String path) {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(WhatsappChatChooser.class.getResource("/umu/tds/apps/resources/icon.png")));
		setTitle("Choose the chat to import");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 333, 144);
		contentPane = new JPanel();
		contentPane.setBackground(Theme.MAIN_COLOR);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(Theme.MAIN_COLOR);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);

		JLabel lblChooseFormat = new JLabel("Choose format ");
		lblChooseFormat.setForeground(Theme.TEXT_COLOR_LIGHT);
		panel.add(lblChooseFormat);

		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { MessagesCharger.ANDROID_1, MessagesCharger.ANDROID_2, MessagesCharger.IOS }));

		panel.add(comboBox);

		JButton btnContinue = new JButton("CONTINUE");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String opElegida = (String) comboBox.getSelectedItem();
				try {
					Controlador.getInstancia().cargarMensajes(path, opElegida);
				} catch (Exception e2) {
					getToolkit().beep();
					JOptionPane.showMessageDialog(WhatsappChatChooser.this, "Please check the chat file is correct.", "Error parsing messages",
							JOptionPane.ERROR_MESSAGE);
				}
				setVisible(false);
			}
		});
		btnContinue.setBackground(Theme.SECONDARY_COLOR);
		GridBagConstraints gbc_btnContinue = new GridBagConstraints();
		gbc_btnContinue.gridx = 0;
		gbc_btnContinue.gridy = 1;
		contentPane.add(btnContinue, gbc_btnContinue);
	}

}
