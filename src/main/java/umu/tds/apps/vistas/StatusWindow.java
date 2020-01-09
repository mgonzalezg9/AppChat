package umu.tds.apps.vistas;

import java.awt.BorderLayout;
import static umu.tds.apps.vistas.Theme.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import umu.tds.apps.AppChat.IndividualContact;
import umu.tds.apps.AppChat.Status;
import umu.tds.apps.controlador.Controlador;

import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.BevelBorder;

/**
 * Ventana que permite al usuario añadir un nuevo estado
 */
class StatusChooser extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textField;
	private File fileChoosen;

	StatusChooser() {
		fileChoosen = null;

		setTitle("Create status");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(StatusWindow.class.getResource("/umu/tds/apps/resources/icon.png")));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 342, 203);
		contentPane = new JPanel();
		contentPane.setBackground(MAIN_COLOR_LIGHT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panelImagen = new JPanel();
		panelImagen.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_panelImagen = new GridBagConstraints();
		gbc_panelImagen.insets = new Insets(0, 0, 5, 0);
		gbc_panelImagen.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelImagen.gridx = 0;
		gbc_panelImagen.gridy = 0;
		contentPane.add(panelImagen, gbc_panelImagen);

		JLabel lblChooseAnImage = new JLabel("Choose an image:");
		panelImagen.add(lblChooseAnImage);

		JButton btnChooseImage = new JButton("Choose image");
		btnChooseImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select an image for your status");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG and PNG images", "jpg", "png");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					fileChoosen = jfc.getSelectedFile();
					lblChooseAnImage.setText(fileChoosen.getName());
				}
			}
		});
		btnChooseImage.setBackground(SECONDARY_COLOR);
		panelImagen.add(btnChooseImage);

		JPanel panelFrase = new JPanel();
		panelFrase.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_panelFrase = new GridBagConstraints();
		gbc_panelFrase.insets = new Insets(0, 0, 5, 0);
		gbc_panelFrase.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelFrase.gridx = 0;
		gbc_panelFrase.gridy = 1;
		contentPane.add(panelFrase, gbc_panelFrase);

		JLabel lblChooseYourText = new JLabel("Enter your text:");
		panelFrase.add(lblChooseYourText);

		textField = new JTextField();
		textField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textField.setBackground(Theme.MAIN_COLOR);
		textField.setForeground(Theme.TEXT_COLOR_LIGHT);
		panelFrase.add(textField);
		textField.setColumns(10);

		JButton btnSubmit = new JButton("SUBMIT");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage img;
				try {
					// Lectura de la imagen
					img = ImageIO.read(fileChoosen);
					Image imgScaled = img.getScaledInstance(Theme.STATUS_IMAGE_SIZE, Theme.STATUS_IMAGE_SIZE,
							Image.SCALE_DEFAULT);
					ImageIcon icon = new ImageIcon(imgScaled);
					icon.setDescription(fileChoosen.getPath());

					// Lectura de la frase
					String frase = textField.getText();

					if (!frase.isEmpty()) {
						Controlador.getInstancia().addEstado(icon, frase);
						StatusChooser.this.setVisible(false);
					} else {
						Toolkit.getDefaultToolkit().beep();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (IllegalArgumentException e2) {
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
		btnSubmit.setBackground(SECONDARY_COLOR);
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.gridx = 0;
		gbc_btnSubmit.gridy = 2;
		contentPane.add(btnSubmit, gbc_btnSubmit);
	}
}

/**
 * Clase que permite ver tanto el estado del usuario como el del resto de contactos.
 */
public class StatusWindow extends JFrame {
	private static final Status DEFAULT_STATUS = new Status(
			new ImageIcon(StatusWindow.class.getResource("/umu/tds/apps/resources/icon.png")), "AppChat");
	private static final long serialVersionUID = 1L;

	private JPanel contentPane, panelMiNombre, panelMiEstado;
	private JLabel lblFraseProfunda;
	private JLabel lblEstadoSeleccionado;
	private JLabel lblNombreContacto;
	private JList<IndividualContact> list;

	/**
	 * Crea la ventana
	 */
	public StatusWindow() {
		setTitle("Status");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(StatusWindow.class.getResource("/umu/tds/apps/resources/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 382);
		contentPane = new JPanel();
		contentPane.setBackground(MAIN_COLOR_LIGHT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panelIzq = new JPanel();
		panelIzq.setBackground(MAIN_COLOR_LIGHT);
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

		JPanel panelMisContactos = new JPanel();
		panelMisContactos.setBackground(MAIN_COLOR_LIGHT);

		panelMiEstado = new JPanel();
		panelMiEstado.setBackground(MAIN_COLOR);
		panelMiEstado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Optional<Status> s = Controlador.getInstancia().getUsuarioActual().getEstado();
				// Si tiene estado se muestra
				if (s.isPresent()) {
					list.clearSelection();
					//lblNombreContacto.setFont(new Font("Tahoma", Font.BOLD, 18));
					panelMiEstado.setBackground(SECONDARY_COLOR);
					panelMiNombre.setBackground(SECONDARY_COLOR);
					lblEstadoSeleccionado.setIcon(resizeIcon(s.get().getImg(), STATUS_IMAGE_SIZE));
					lblFraseProfunda.setText(s.get().getFrase());
				} else {
					lblEstadoSeleccionado.setIcon(DEFAULT_STATUS.getImg());
					lblFraseProfunda.setText(DEFAULT_STATUS.getFrase());
				}
			}
		});
		GridBagConstraints gbc_panelMiEstado = new GridBagConstraints();
		gbc_panelMiEstado.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelMiEstado.insets = new Insets(0, 0, 5, 0);
		gbc_panelMiEstado.gridx = 0;
		gbc_panelMiEstado.gridy = 0;
		panelIzq.add(panelMiEstado, gbc_panelMiEstado);
		panelMiEstado.setLayout(new BorderLayout(0, 0));

		panelMiNombre = new JPanel();
		panelMiNombre.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelMiNombre.setBackground(MAIN_COLOR);
		panelMiEstado.add(panelMiNombre, BorderLayout.CENTER);

		JLabel lblImagenContacto = new JLabel();
		panelMiNombre.add(lblImagenContacto);
		lblImagenContacto
				.setIcon(resizeIcon(Controlador.getInstancia().getUsuarioActual().getProfilePhoto(), ICON_SIZE));

		lblNombreContacto = new JLabel(Controlador.getInstancia().getUsuarioActual().getName());
		panelMiNombre.add(lblNombreContacto);
		lblNombreContacto.setForeground(TEXT_COLOR_LIGHT);
		lblNombreContacto.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JButton btnNuevoEstado = new JButton("           +           ");
		btnNuevoEstado.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnNuevoEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ventanaEstado = new StatusChooser();
				ventanaEstado.setVisible(true);
				StatusWindow.this.setVisible(false);
			}
		});
		btnNuevoEstado.setBackground(SECONDARY_COLOR);
		panelMiEstado.add(btnNuevoEstado, BorderLayout.EAST);
		panelMisContactos.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelMisContactos.setBackground(MAIN_COLOR);
		GridBagConstraints gbc_panelMisContactos = new GridBagConstraints();
		gbc_panelMisContactos.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelMisContactos.insets = new Insets(0, 0, 5, 0);
		gbc_panelMisContactos.gridx = 0;
		gbc_panelMisContactos.gridy = 1;
		panelIzq.add(panelMisContactos, gbc_panelMisContactos);
		panelMisContactos.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblYourContactsStatus = new JLabel("Contact status");
		lblYourContactsStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourContactsStatus.setForeground(TEXT_COLOR_LIGHT);
		lblYourContactsStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelMisContactos.add(lblYourContactsStatus);

		JPanel listaEstados = new JPanel();
		panelIzq.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_listaEstados = new GridBagConstraints();
		gbc_listaEstados.fill = GridBagConstraints.BOTH;
		gbc_listaEstados.gridx = 0;
		gbc_listaEstados.gridy = 2;
		panelIzq.add(listaEstados, gbc_listaEstados);
		listaEstados.setLayout(new BorderLayout(0, 0));

		JPanel panelRender = new JPanel();
		panelRender.setBackground(MAIN_COLOR_LIGHT);
		panelRender.setBorder(null);
		listaEstados.add(panelRender);
		panelRender.setLayout(new BoxLayout(panelRender, BoxLayout.Y_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		panelRender.add(scrollPane);
		scrollPane.setBorder(null);

		DefaultListModel<IndividualContact> listModel = new DefaultListModel<>();
		Controlador.getInstancia().getContactosEstado().stream().forEach(c -> listModel.addElement(c));

		list = new JList<IndividualContact>();
		list.setBackground(MAIN_COLOR_LIGHT);
		list.setFont(new Font("Tahoma", Font.PLAIN, 15));
		list.setCellRenderer(createListRenderer());
		list.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNombreContacto.setFont(new Font("Tahoma", Font.PLAIN, 18));
				panelMiEstado.setBackground(MAIN_COLOR);
				panelMiNombre.setBackground(MAIN_COLOR);

				if (list.getSelectedValue() != null) {
					Status estado = list.getSelectedValue().getEstado().get();
					lblFraseProfunda.setText(estado.getFrase());
					lblEstadoSeleccionado.setIcon(resizeIcon(estado.getImg(), STATUS_IMAGE_SIZE));
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				lblNombreContacto.setFont(new Font("Tahoma", Font.PLAIN, 18));
				panelMiEstado.setBackground(MAIN_COLOR);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		list.setSelectionBackground(SECONDARY_COLOR);
		list.setSelectionForeground(TEXT_COLOR_LIGHT);
		list.setBorder(null);
		scrollPane.setViewportView(list);
		list.setModel(listModel);

		JPanel panelDer = new JPanel();
		panelDer.setBackground(MAIN_COLOR_LIGHT);
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
		lblEstadoSeleccionado.setIcon(DEFAULT_STATUS.getImg());
		GridBagConstraints gbc_lblEstadoSeleccionado = new GridBagConstraints();
		gbc_lblEstadoSeleccionado.insets = new Insets(0, 0, 5, 0);
		gbc_lblEstadoSeleccionado.gridx = 0;
		gbc_lblEstadoSeleccionado.gridy = 0;
		panelDer.add(lblEstadoSeleccionado, gbc_lblEstadoSeleccionado);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(MAIN_COLOR_LIGHT);
		panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.SOUTH;
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panelDer.add(panel_1, gbc_panel_1);

		lblFraseProfunda = new JLabel(DEFAULT_STATUS.getFrase());
		lblFraseProfunda.setForeground(TEXT_COLOR_LIGHT);
		panel_1.add(lblFraseProfunda);
		lblFraseProfunda.setBorder(null);
	}

	/**
	 * Crea un renderer para mostrar la lista de contactos
	 * @return Objeto que se añade a la lista de contactos para que sepa como mostrarlos
	 */
	private static ListCellRenderer<? super IndividualContact> createListRenderer() {
		return new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				IndividualContact contacto = (IndividualContact) value;

				JPanel panel = new JPanel();
				ImageIcon img = contacto.getFoto();
				panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

				panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				panel.setBackground(MAIN_COLOR_LIGHT);

				JPanel panelFoto = new JPanel();
				panelFoto.setBackground(MAIN_COLOR_LIGHT);
				panel.add(panelFoto);

				JLabel label = new JLabel("");
				panelFoto.add(label);
				label.setIcon(resizeIcon(img, Theme.ICON_SIZE));

				JPanel panelDatos = new JPanel();
				panelDatos.setBackground(MAIN_COLOR_LIGHT);
				panel.add(panelDatos);
				GridBagLayout gbl_panel_2 = new GridBagLayout();
				gbl_panel_2.columnWidths = new int[] { 0, 0 };
				gbl_panel_2.rowHeights = new int[] { 0, 0, 0 };
				gbl_panel_2.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
				gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
				panelDatos.setLayout(gbl_panel_2);

				JLabel lblNewLabel = new JLabel(contacto.getNombre());
				lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
				gbc_lblNewLabel.gridx = 0;
				gbc_lblNewLabel.gridy = 0;
				panelDatos.add(lblNewLabel, gbc_lblNewLabel);

				JLabel lblFrase = new JLabel(contacto.getEstado().get().getFrase());
				GridBagConstraints gbc_lblFrase = new GridBagConstraints();
				gbc_lblFrase.gridx = 0;
				gbc_lblFrase.gridy = 1;
				panelDatos.add(lblFrase, gbc_lblFrase);

				// Seleccion del color
				if (isSelected) {
					panel.setBackground(SECONDARY_COLOR);
					panelFoto.setBackground(SECONDARY_COLOR);
					panelDatos.setBackground(SECONDARY_COLOR);
				} else {
					panel.setBackground(MAIN_COLOR_LIGHT);
					panelFoto.setBackground(MAIN_COLOR_LIGHT);
					panelDatos.setBackground(MAIN_COLOR_LIGHT);
				}

				return panel;
			}
		};
	}
}
