package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import umu.tds.apps.controlador.Controlador;

import java.awt.Toolkit;
import java.util.List;
import java.util.ResourceBundle.Control;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.TexturePaint;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Clase que gestiona el carrousel de imagenes
class Carrousel {
	private JLabel profilePhoto;
	private List<ImageIcon> imagenes;
	private int numImagen;
	private JLabel indicador;

	public Carrousel(JLabel profilePhoto, List<ImageIcon> imagenes, JLabel indicador) {
		this.profilePhoto = profilePhoto;
		this.imagenes = imagenes;
		this.numImagen = 0;
		this.indicador = indicador;

		profilePhoto.setIcon(imagenes.get(numImagen));
		profilePhoto.setText("");

		int numMostrado = numImagen + 1;
		indicador.setText(numMostrado + "/" + imagenes.size());
		indicador.setForeground(TEXT_COLOR_LIGHT);
	}

	public void desplazar(int offset) {
		numImagen = (numImagen + offset) % imagenes.size();
		if (numImagen < 0)
			numImagen += imagenes.size();
		int numMostrado = numImagen + 1;
		indicador.setText(numMostrado + "/" + imagenes.size());

		profilePhoto.setIcon(imagenes.get(numImagen));
	}

	public boolean addImagen(JFileChooser jfc) {
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			try {
				// Escala la imagen
				BufferedImage img = ImageIO.read(jfc.getSelectedFile());
				Image imgScaled = img.getScaledInstance(128, 128, Image.SCALE_DEFAULT);
				ImageIcon icon = new ImageIcon(imgScaled);

				// La añade
				Controlador.getInstancia().addImagenUsuario(icon);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			int numMostrado = numImagen + 1;
			indicador.setText(numMostrado + "/" + imagenes.size());
			profilePhoto.setIcon(imagenes.get(imagenes.size() - 1));
			return true;
		}
		return false;
	}

	public void removeImagen(int op) {
		if (imagenes.size() > 1) {
			Controlador.getInstancia().removeImagenUsuario(op);
			desplazar(-1);
		} else {
			// Pedimos amablemente una imagen
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Select an image");
			jfc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG and PNG images", "jpg", "png");
			jfc.addChoosableFileFilter(filter);

			// Si se ha podido meter otra en su puesto se borra la imagen del carrousel
			if (addImagen(jfc)) {
				Controlador.getInstancia().removeImagenUsuario(op);
			}
		}

	}

}

public class UserSettings extends JFrame {

	private JPanel contentPane;
	private Carrousel car;
	private JTextArea txtrRespetandoElNnn;
	private JFrame frame;

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
		frame = this;
		setTitle("Settings");
		Controlador.getInstancia()
				.addImagenUsuario(new ImageIcon(Controlador.class.getResource("/umu/tds/apps/resources/user.png")));
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(UserSettings.class.getResource("/umu/tds/apps/resources/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 822, 575);
		contentPane = new JPanel();
		contentPane.setBackground(MAIN_COLOR_LIGHT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 5, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(MAIN_COLOR_LIGHT);
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		contentPane.add(panel_1, gbc_panel_1);

		JLabel profilePhoto = new JLabel();
		panel.add(profilePhoto);

		JButton button_3 = new JButton("-");
		button_3.setBackground(SECONDARY_COLOR);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int size = Controlador.getInstancia().getImagenesUsuario().size();
				String[] elems = new String[size];

				for (int i = 0; i < elems.length; i++) {
					elems[i] = String.valueOf(i + 1);
				}

				String op = (String) JOptionPane.showInputDialog(frame, "Elige la imagen a eliminar:",
						"Borrado de foto de perfil", JOptionPane.QUESTION_MESSAGE, null, elems, elems[0]);

				if ((op != null) && (op.length() > 0))
					car.removeImagen(Integer.valueOf(op) - 1);
			}
		});
		panel_1.add(button_3);

		JButton button = new JButton("<");
		button.setBackground(SECONDARY_COLOR);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Ver anterior imagen del carrousel
				car.desplazar(-1);
			}
		});
		panel_1.add(button);

		JButton button_1 = new JButton(">");
		button_1.setBackground(SECONDARY_COLOR);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Ver siguiente imagen del carrousel
				car.desplazar(1);
			}
		});

		JLabel carrousel = new JLabel();
		panel_1.add(carrousel);

		// Creamos un objeto carrousel para administrarlo
		car = new Carrousel(profilePhoto, Controlador.getInstancia().getImagenesUsuario(), carrousel);
		panel_1.add(button_1);

		JButton button_2 = new JButton("+");
		button_2.setBackground(SECONDARY_COLOR);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Pedimos amablemente una imagen
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select an image");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG and PNG images", "jpg", "png");
				jfc.addChoosableFileFilter(filter);

				// Si se ha escogido la añade
				car.addImagen(jfc);
			}
		});
		panel_1.add(button_2);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 2;
		contentPane.add(panel_4, gbc_panel_4);

		JLabel lblNewLabel = new JLabel(Controlador.getInstancia().getNombreUsuario());
		lblNewLabel.setForeground(TEXT_COLOR_LIGHT);
		panel_4.add(lblNewLabel);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 5, 5);
		gbc_panel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 3;
		contentPane.add(panel_5, gbc_panel_5);

		JLabel lblGreeting = new JLabel("Greeting:");
		lblGreeting.setForeground(TEXT_COLOR_LIGHT);
		panel_5.add(lblGreeting);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		contentPane.add(scrollPane, gbc_scrollPane);

		txtrRespetandoElNnn = new JTextArea(Controlador.getInstancia().getSaludo());
		txtrRespetandoElNnn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				Controlador.getInstancia().setSaludo(txtrRespetandoElNnn.getText());
			}
		});
		scrollPane.setViewportView(txtrRespetandoElNnn);
		txtrRespetandoElNnn.setPreferredSize(new Dimension(200, 100));
		txtrRespetandoElNnn.setMinimumSize(new Dimension(10, 25));
	}

}
