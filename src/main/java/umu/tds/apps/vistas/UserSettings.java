package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import umu.tds.apps.controlador.Controlador;

import java.awt.Toolkit;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;

/**
 * Ventana con los datos del usuario actual
 */
public class UserSettings extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Clase interna que gestiona el carrousel de imagenes
	 */
	private class Carrousel {
		private List<ImageIcon> imagenes;
		private int numImagen;

		/**
		 * Constructor del carrousel con las imágenes
		 * 
		 * @param imagenes Fotos de perfil a mostrar
		 */
		public Carrousel(List<ImageIcon> imagenes) {
			this.imagenes = imagenes.stream().map(i -> resizeIcon(i, 128)).collect(Collectors.toList());
			this.numImagen = 0;

			profilePhoto.setIcon(this.imagenes.get(numImagen));

			int numMostrado = numImagen + 1;
			lblIndicadorCarrousel.setText(numMostrado + "/" + imagenes.size());
			lblIndicadorCarrousel.setForeground(TEXT_COLOR_LIGHT);
		}

		/**
		 * Avanza el carrousel un número determinado de posiciones
		 * 
		 * @param offset Posiciones a avanzar
		 */
		public void desplazar(int offset) {
			numImagen = (numImagen + offset) % imagenes.size();
			if (numImagen < 0)
				numImagen += imagenes.size();
			int numMostrado = numImagen + 1;
			lblIndicadorCarrousel.setText(numMostrado + "/" + imagenes.size());

			profilePhoto.setIcon(imagenes.get(numImagen));
		}

		/**
		 * Si se ha escogido la imagen se guarda en BD
		 * 
		 * @param jfc Selector de la imagen
		 * @return Devuelve la imagen añadida o null en caso de no haber podido añadir
		 *         la imagen
		 */
		public ImageIcon addImagen(JFileChooser jfc) {
			ImageIcon icon = null;
			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				try {
					BufferedImage img = ImageIO.read(jfc.getSelectedFile());

					// Redondea la imagen
					img = roundImage(img);

					// Escala el tamaño de la imagen para mostrarla
					icon = resizeIcon(img, 128);

					// La añade a la lista
					icon.setDescription("/umu/tds/apps/photos/" + jfc.getSelectedFile().getName());
					imagenes.add(icon);
					Controlador.getInstancia().addImagenUsuario(icon);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				numImagen = imagenes.size() - 1;
				int numMostrado = numImagen + 1;
				lblIndicadorCarrousel.setText(numMostrado + "/" + imagenes.size());
				profilePhoto.setIcon(imagenes.get(numImagen));
			}
			return icon;
		}

		/**
		 * Borra una imagen del carrousel
		 * 
		 * @param op Posición de la imagen a borrar
		 * @return Devuelve si ha podido borrar la imagen
		 */
		public boolean removeImagen(int op) {
			if (imagenes.size() > 1)
				desplazar(-1);
			else {
				// Pedimos amablemente una imagen
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select an image");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG and PNG images", "jpg", "png");
				jfc.addChoosableFileFilter(filter);

				if (addImagen(jfc) == null) {
					// No efectua el cambio
					return false;
				}
			}
			imagenes.remove(op);
			Controlador.getInstancia().removeImagenUsuario(op);

			// Actualiza el indice consecuentemente
			numImagen = imagenes.size() - 1;
			int numMostrado = numImagen + 1;
			lblIndicadorCarrousel.setText(numMostrado + "/" + imagenes.size());
			profilePhoto.setIcon(imagenes.get(numImagen));

			return true;
		}

	}

	// Atributos
	private JPanel contentPane;
	private Carrousel car;
	private JLabel profilePhoto;
	private JTextArea textAreaSaludo;
	private JLabel lblIndicadorCarrousel;

	/**
	 * Crea la ventana e inicializa el carrousel.
	 */
	public UserSettings() {
		setTitle("Settings");
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
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 5, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
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
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		contentPane.add(panel_1, gbc_panel_1);

		profilePhoto = new JLabel();
		panel.add(profilePhoto);

		JButton button_3 = new JButton("-");
		button_3.setBackground(SECONDARY_COLOR);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int size = Controlador.getInstancia().getUsuarioActual().getProfilePhotos().size();
				String[] elems = new String[size];

				for (int i = 0; i < elems.length; i++) {
					elems[i] = String.valueOf(i + 1);
				}

				String op = (String) JOptionPane.showInputDialog(UserSettings.this, "Elige la imagen a eliminar:",
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

		lblIndicadorCarrousel = new JLabel();
		panel_1.add(lblIndicadorCarrousel);

		// Creamos un objeto carrousel para administrarlo
		car = new Carrousel(Controlador.getInstancia().getUsuarioActual().getProfilePhotos());
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

				// Si se ha escogido la añade al carrousel
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

		JLabel lblNewLabel = new JLabel(Controlador.getInstancia().getUsuarioActual().getName());
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

		textAreaSaludo = new JTextArea(Controlador.getInstancia().getUsuarioActual().getSaludo());
		textAreaSaludo.setBackground(MAIN_COLOR);
		textAreaSaludo.setForeground(TEXT_COLOR_LIGHT);
		scrollPane.setViewportView(textAreaSaludo);
		textAreaSaludo.setPreferredSize(new Dimension(200, 100));
		textAreaSaludo.setMinimumSize(new Dimension(10, 25));

		JButton btnSaveGreeting = new JButton("SAVE GREETING");
		btnSaveGreeting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controlador.getInstancia().setSaludoUsuario(textAreaSaludo.getText());

				// Cierra la ventana
				setVisible(false);
				// dispose();
			}
		});
		btnSaveGreeting.setBackground(SECONDARY_COLOR);
		GridBagConstraints gbc_btnSaveGreeting = new GridBagConstraints();
		gbc_btnSaveGreeting.insets = new Insets(0, 0, 5, 5);
		gbc_btnSaveGreeting.gridx = 1;
		gbc_btnSaveGreeting.gridy = 5;
		contentPane.add(btnSaveGreeting, gbc_btnSaveGreeting);
	}

}
