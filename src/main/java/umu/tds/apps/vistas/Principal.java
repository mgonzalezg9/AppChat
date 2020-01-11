package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.border.BevelBorder;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import tds.BubbleText;
import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.Group;
import umu.tds.apps.AppChat.IndividualContact;
import umu.tds.apps.AppChat.Message;
import umu.tds.apps.controlador.Controlador;

import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.Scrollable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import pulsador.Luz;
import pulsador.IEncendidoListener;

import java.util.EventObject;
import java.util.HashMap;

/**
 * Clase que representa al chat con las burbujas. Se creó con el objetivo de que
 * desaparezca la scrollbar horizontal del chat.
 */
class ChatBurbujas extends JPanel implements Scrollable {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return null;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 0;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 0;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

/**
 * Ventana principal
 */
public class Principal extends JFrame {
	private static final int NUM_CHATS_CACHE = 3;
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private ChatBurbujas chat;
	private JLabel profilePhoto;
	private JTextField textField;
	private JPopupMenu popupSettsGrupos;
	private JLabel chatName;
	private JLabel chatPhoto;
	private boolean iconsVisible;
	private Controlador controlador;
	private JList<Contact> listaContactos;

	// Últimos chats accedidos por el usuario
	private Map<Contact, ChatBurbujas> chatsRecientes;
	private JScrollPane scrollBarChatBurbujas;

	/**
	 * Manda al contacto pasado como parametro el mensaje con el texto del textfield
	 * y lo muestra en el panel.
	 * 
	 * @param panel     Chat con las burbujas dónde se mostrará el mensaje enviado
	 * @param textField Campo de texto con el mensaje a enviar
	 * @param contacto  Contacto al que se le envia el mensaje
	 */
	private void sendMessage(ChatBurbujas panel, JTextField textField, Contact contacto) {
		// No permite enviar un mensaje si no hay seleccionado ningún contacto
		if (contacto == null)
			return;

		controlador.enviarMensaje(contacto, textField.getText());

		BubbleText burbuja = new BubbleText(panel, textField.getText(), SENT_MESSAGE_COLOR, "You", BubbleText.SENT,
				MESSAGE_SIZE);
		panel.add(burbuja);
		textField.setText(null);
		listaContactos.updateUI();
	}

	/**
	 * Manda al contacto pasado como parametro el mensaje con el emoticono y lo
	 * muestra en el panel.
	 * 
	 * @param panel    Panel con burbujas dónde se mostrará el mensaje enviado
	 * @param iconID   Identificador del icono a enviar
	 * @param contacto Contacto al que se le envia el mensaje
	 */
	private void sendIcon(ChatBurbujas panel, int iconID, Contact contacto) {
		// No permite enviar un emoji si no hay seleccionado ningún contacto
		if (contacto == null)
			return;

		controlador.enviarMensaje(contacto, iconID);

		BubbleText burbuja = new BubbleText(panel, iconID, SENT_MESSAGE_COLOR, "You", BubbleText.SENT, MESSAGE_SIZE);
		panel.add(burbuja);
	}

	/**
	 * Carga en pantalla toda la conversación con dicho contacto. Comprueba si el
	 * chat se encuentra en la caché de pares recientes, si no está lo añade.
	 * 
	 * @param contacto Contacto cuya conversación se quiere cargar
	 */
	private void loadChat(Contact contacto) {
		if (contacto == null) {
			return;
		}

		chat = chatsRecientes.get(contacto);

		// Si no es un chat accedido recientemente tiene que crear el panel
		if (chat == null) {
			chat = new ChatBurbujas();
			chat.setBackground(CHAT_COLOR);
			scrollBarChatBurbujas.setViewportView(chat);
			chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
			chat.setSize(400, 700);
			scrollBarChatBurbujas.getViewport().setBackground(CHAT_COLOR);

			// Coloca las burbujas en el panel nuevo
			controlador.getMensajes(contacto).stream().map(m -> {
				String emisor;
				int direccionMensaje;
				Color colorBurbuja;

				if (m.getEmisor().equals(controlador.getUsuarioActual())) {
					colorBurbuja = SENT_MESSAGE_COLOR;
					emisor = "You";
					direccionMensaje = BubbleText.SENT;
				} else {
					colorBurbuja = INCOMING_MESSAGE_COLOR;
					emisor = m.getEmisor().getName();
					direccionMensaje = BubbleText.RECEIVED;
				}

				if (m.getTexto().isEmpty()) {
					return new BubbleText(chat, m.getEmoticono(), colorBurbuja, emisor, direccionMensaje, MESSAGE_SIZE);
				}
				return new BubbleText(chat, m.getTexto(), colorBurbuja, emisor, direccionMensaje, MESSAGE_SIZE);
			}).forEach(b -> chat.add(b));

			// Si no hay espacio en los chats recientes es necesario borrar uno
			if (chatsRecientes.size() >= NUM_CHATS_CACHE) {
				chatsRecientes.remove(chatsRecientes.keySet().stream().findFirst().orElse(null));
			}

			chatsRecientes.put(contacto, chat);
		} else {
			chat.setBackground(CHAT_COLOR);
			scrollBarChatBurbujas.setViewportView(chat);
			chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
			chat.setSize(400, 700);
			scrollBarChatBurbujas.getViewport().setBackground(CHAT_COLOR);
		}

	}

	/**
	 * Crea la ventana
	 */
	public Principal() {
		controlador = Controlador.getInstancia();
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/umu/tds/apps/resources/icon.png")));
		setTitle("AppChat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 781, 637);
		contentPane = new JPanel();
		contentPane.setBackground(MAIN_COLOR_LIGHT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		iconsVisible = false;

		// Se extraen los contactos del usuario
		List<Contact> contactos = controlador.getContactosUsuarioActual();

		// Creamos el modelo
		final DefaultListModel<Contact> modelContacts = new DefaultListModel<>();

		// Rellenamos el modelo
		contactos.stream().forEach(c -> modelContacts.addElement(c));
		listaContactos = new JList<>(modelContacts);

		// Inicialmente no se ha accedido a ningún chat
		chatsRecientes = new HashMap<>();

		JPanel settingsIzq = new JPanel();
		settingsIzq.setBackground(MAIN_COLOR);
		settingsIzq.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_settingsIzq = new GridBagConstraints();
		gbc_settingsIzq.insets = new Insets(0, 0, 5, 5);
		gbc_settingsIzq.fill = GridBagConstraints.BOTH;
		gbc_settingsIzq.gridx = 0;
		gbc_settingsIzq.gridy = 0;
		contentPane.add(settingsIzq, gbc_settingsIzq);
		GridBagLayout gbl_settingsIzq = new GridBagLayout();
		gbl_settingsIzq.columnWidths = new int[] { 0, 0, 0 };
		gbl_settingsIzq.rowHeights = new int[] { 10, 0 };
		gbl_settingsIzq.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_settingsIzq.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		settingsIzq.setLayout(gbl_settingsIzq);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(MAIN_COLOR);
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		settingsIzq.add(panel_1, gbc_panel_1);

		JLabel lblMiFoto = new JLabel();
		lblMiFoto.setIcon(resizeIcon(controlador.getUsuarioActual().getProfilePhoto(), ICON_SIZE_MINI));
		lblMiFoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				UserSettings settingsUsuario = new UserSettings();
				settingsUsuario.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				settingsUsuario.setVisible(true);
			}
		});
		panel_1.add(lblMiFoto);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(MAIN_COLOR);
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 0;
		settingsIzq.add(panel_2, gbc_panel_2);

		JLabel label_2 = new JLabel();
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StatusWindow window = new StatusWindow();
				window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				window.setVisible(true);
			}
		});
		label_2.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/status-white.png")));
		panel_2.add(label_2);

		JLabel label_1 = new JLabel();

		label_1.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/3points-white.png")));
		panel_2.add(label_1);

		popupSettsGrupos = new JPopupMenu();
		addPopup(label_1, popupSettsGrupos);

		JMenuItem mntmNewMenuItem = new JMenuItem("Add Contact");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateContact window = new CreateContact(modelContacts);
				window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				window.setVisible(true);
			}
		});
		popupSettsGrupos.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("New Group");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GroupManagement window = new GroupManagement(modelContacts, null);
				window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				window.setVisible(true);
			}
		});
		popupSettsGrupos.add(mntmNewMenuItem_1);

		JMenuItem mntmModifyGroup = new JMenuItem("Modify Group");
		mntmModifyGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (controlador.getUsuarioActual().getGrupos().isEmpty()) {
					JOptionPane.showMessageDialog(Principal.this, "Groups are needed", "Modify Group",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				GroupsListForModify window = new GroupsListForModify(modelContacts);
				window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				window.setVisible(true);
			}
		});
		popupSettsGrupos.add(mntmModifyGroup);

		JMenuItem mntmMostrarContactos = new JMenuItem("Show Contacts");
		mntmMostrarContactos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ContactWindow window = new ContactWindow();
				window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				window.setVisible(true);
			}
		});
		popupSettsGrupos.add(mntmMostrarContactos);

		if (!controlador.getUsuarioActual().isPremium()) {
			JMenuItem mntmGetPremium = new JMenuItem("Get Premium");
			mntmGetPremium.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Premium window = new Premium();
					window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					window.setVisible(true);
				}
			});
			popupSettsGrupos.add(mntmGetPremium);
		}

		if (controlador.getUsuarioActual().isPremium()) {
			JMenuItem mntmShowStatistics = new JMenuItem("Show Statistics");
			mntmShowStatistics.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (controlador.getUsuarioActual().isPremium()) {
						UsageStatistics window = new UsageStatistics();
						window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						window.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(Principal.this,
								"Become premium to have access to this funcionality.", "Premium",
								JOptionPane.ERROR_MESSAGE);
						Toolkit.getDefaultToolkit().beep();
					}
				}
			});
			popupSettsGrupos.add(mntmShowStatistics);
		}

		JMenuItem mntmLogout = new JMenuItem("Sign out");
		mntmLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Principal.this.setVisible(false);
				Login window = new Login();
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setVisible(true);
			}
		});
		popupSettsGrupos.add(mntmLogout);

		JPanel settingsDer = new JPanel();
		settingsDer.setBackground(MAIN_COLOR);
		settingsDer.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_settingsDer = new GridBagConstraints();
		gbc_settingsDer.insets = new Insets(0, 0, 5, 0);
		gbc_settingsDer.fill = GridBagConstraints.BOTH;
		gbc_settingsDer.gridx = 1;
		gbc_settingsDer.gridy = 0;
		contentPane.add(settingsDer, gbc_settingsDer);
		GridBagLayout gbl_settingsDer = new GridBagLayout();
		gbl_settingsDer.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_settingsDer.rowHeights = new int[] { 0, 0 };
		gbl_settingsDer.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_settingsDer.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		settingsDer.setLayout(gbl_settingsDer);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(MAIN_COLOR);
		panel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ContactInfo window = new ContactInfo(listaContactos.getSelectedValue());
				window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				window.setVisible(true);
			}
		});
		FlowLayout flowLayout_2 = (FlowLayout) panel_3.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.anchor = GridBagConstraints.WEST;
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		settingsDer.add(panel_3, gbc_panel_3);

		JPanel panel = new JPanel();
		panel.setBackground(MAIN_COLOR);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 0;
		settingsDer.add(panel, gbc_panel);

		JLabel label_4 = new JLabel("");
		label_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Search ventanaBusqueda = new Search();
				ventanaBusqueda.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				ventanaBusqueda.setVisible(true);
			}
		});

		Luz luz = new Luz();
		luz.addEncendidoListener(new IEncendidoListener() {
			public void enteradoCambioEncendido(EventObject arg0) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Choose a WhatsApp chat");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Only txt", "txt");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					// Ventana para seleccionar el chat
					JFrame ventana = new WhatsappChatChooser(jfc.getSelectedFile().getAbsolutePath());
					ventana.setVisible(true);

					loadChat(listaContactos.getSelectedValue());
				}
			}
		});
		luz.setColor(SECONDARY_COLOR);
		panel.add(luz);
		label_4.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/search-white.png")));
		panel.add(label_4);

		JLabel label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/3points-white.png")));
		panel.add(label_5);

		JPopupMenu popupSettsChat = new JPopupMenu();
		addPopup(label_5, popupSettsChat);

		JMenuItem mntmRemoveAllMessages = new JMenuItem("Remove all messages");
		mntmRemoveAllMessages.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Contact contacto = listaContactos.getSelectedValue();
				if (contacto != null) {
					// Borra los mensajes de la conversación de la base de datos
					controlador.deleteChat(listaContactos.getSelectedValue());

					chat.removeAll();
					chat.updateUI();
				}
			}
		});
		popupSettsChat.add(mntmRemoveAllMessages);

		JMenuItem mntmDeleteContact = new JMenuItem("Delete chat");
		mntmDeleteContact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listaContactos.getSelectedValue() == null) {
					JOptionPane.showMessageDialog(Principal.this, "Unable to perform this action.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (!(listaContactos.getSelectedValue() instanceof Group)
						|| controlador.isAdmin((Group) listaContactos.getSelectedValue())) {
					JOptionPane.showMessageDialog(Principal.this, "This chat was deleted succesfully", "Chat deleted",
							JOptionPane.INFORMATION_MESSAGE);
					Contact contactoSeleccionado = listaContactos.getSelectedValue();
					modelContacts.removeElement(contactoSeleccionado);
					controlador.deleteContact(contactoSeleccionado);

					if (modelContacts.isEmpty()) {
						chat.removeAll();
						controlador.setChatActual(null);
						chatName.setText("");
						chatPhoto.setIcon(null);
					}
				} else {
					JOptionPane.showMessageDialog(Principal.this,
							"Unable to delete the chat. Please check your chat privileges.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		popupSettsChat.add(mntmDeleteContact);

		profilePhoto = new JLabel();
		profilePhoto.setIcon(new ImageIcon(
				Principal.class.getResource("/umu/tds/apps/resources/173312_magnifying-glass-icon-png.png")));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);

		chatPhoto = new JLabel("");
		if (!listaContactos.isSelectionEmpty())
			chatPhoto.setIcon(resizeIcon(listaContactos.getSelectedValue().getFoto(), ICON_SIZE_MINI));
		panel_3.add(chatPhoto);

		chatName = new JLabel();
		chatName.setForeground(TEXT_COLOR_LIGHT);
		panel_3.add(chatName);

		listaContactos.setBorder(null);
		listaContactos.setBackground(MAIN_COLOR_LIGHT);
		listaContactos.setCellRenderer(createListRenderer());
		listaContactos.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				Contact contactoActual = listaContactos.getSelectedValue();
				if (contactoActual != null) {
					loadChat(contactoActual);
					controlador.setChatActual(contactoActual);
					chatName.setText(contactoActual.getNombre());
					chatPhoto.setIcon(resizeIcon(contactoActual.getFoto(), ICON_SIZE_MINI));
				}
			}

		});

		scrollPane.setViewportView(listaContactos);

		JPanel chatPersonal = new JPanel();
		chatPersonal.setBackground(CHAT_COLOR);
		chatPersonal.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_chatPersonal = new GridBagConstraints();
		gbc_chatPersonal.fill = GridBagConstraints.BOTH;
		gbc_chatPersonal.gridx = 1;
		gbc_chatPersonal.gridy = 1;
		contentPane.add(chatPersonal, gbc_chatPersonal);
		GridBagLayout gbl_chatPersonal = new GridBagLayout();
		gbl_chatPersonal.columnWidths = new int[] { 66, 0 };
		gbl_chatPersonal.rowHeights = new int[] { 476, 80, 0, 0 };
		gbl_chatPersonal.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_chatPersonal.rowWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		chatPersonal.setLayout(gbl_chatPersonal);

		scrollBarChatBurbujas = new JScrollPane();
		scrollBarChatBurbujas.setBorder(null);
		scrollBarChatBurbujas.setBackground(CHAT_COLOR);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		chatPersonal.add(scrollBarChatBurbujas, gbc_scrollPane_1);

		chat = new ChatBurbujas();
		chat.setBackground(CHAT_COLOR);
		scrollBarChatBurbujas.setViewportView(chat);
		chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
		chat.setSize(400, 700);
		scrollBarChatBurbujas.getViewport().setBackground(CHAT_COLOR);

		// Se muestran todas las burbujas de la conversacion actual
		listaContactos.setSelectedIndex(0);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBorder(null);
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_3.gridx = 0;
		gbc_scrollPane_3.gridy = 1;
		chatPersonal.add(scrollPane_3, gbc_scrollPane_3);

		JPanel panel_iconos = new JPanel();
		scrollPane_3.setViewportView(panel_iconos);
		scrollPane_3.setBackground(CHAT_COLOR);
		panel_iconos.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_iconos.setBackground(CHAT_COLOR);

		// Añadimos todos los iconos al panel.
		for (int i = 0; i <= BubbleText.MAXICONO; i++) {
			JLabel labelIconos = new JLabel("");
			labelIconos.setIcon(BubbleText.getEmoji(i));
			labelIconos.setName(Integer.toString(i));
			panel_iconos.add(labelIconos);
			labelIconos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					sendIcon(chat, Integer.valueOf(labelIconos.getName()), listaContactos.getSelectedValue());
				}
			});
		}

		scrollPane_3.setVisible(iconsVisible);

		JPanel writeText = new JPanel();
		writeText.setBackground(SECONDARY_COLOR);
		writeText.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_writeText = new GridBagConstraints();
		gbc_writeText.fill = GridBagConstraints.HORIZONTAL;
		gbc_writeText.anchor = GridBagConstraints.SOUTH;
		gbc_writeText.gridx = 0;
		gbc_writeText.gridy = 2;
		chatPersonal.add(writeText, gbc_writeText);
		GridBagLayout gbl_writeText = new GridBagLayout();
		gbl_writeText.columnWidths = new int[] { 49, 25, 0, 0 };
		gbl_writeText.rowHeights = new int[] { 20, 0 };
		gbl_writeText.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_writeText.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		writeText.setLayout(gbl_writeText);

		JLabel lblEmoji = new JLabel("");
		lblEmoji.setHorizontalTextPosition(SwingConstants.CENTER);
		lblEmoji.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmoji.setBackground(SECONDARY_COLOR);
		lblEmoji.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				iconsVisible = !iconsVisible;
				scrollPane_3.setVisible(iconsVisible);
				chatPersonal.updateUI();
				loadChat(listaContactos.getSelectedValue());
			}
		});
		lblEmoji.setIcon(BubbleText.getEmoji(new Random().nextInt(BubbleText.MAXICONO + 1)));
		GridBagConstraints gbc_lblEmoji = new GridBagConstraints();
		gbc_lblEmoji.fill = GridBagConstraints.VERTICAL;
		gbc_lblEmoji.insets = new Insets(0, 0, 0, 5);
		gbc_lblEmoji.gridx = 0;
		gbc_lblEmoji.gridy = 0;
		writeText.add(lblEmoji, gbc_lblEmoji);

		JLabel lblSend = new JLabel("");
		lblSend.setHorizontalAlignment(SwingConstants.CENTER);
		lblSend.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSend.setBackground(SECONDARY_COLOR);
		lblSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					sendMessage(chat, textField, listaContactos.getSelectedValue());
				} catch (IllegalArgumentException e2) {
				}
			}
		});

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBackground(SECONDARY_COLOR);
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_2.gridx = 1;
		gbc_scrollPane_2.gridy = 0;
		writeText.add(scrollPane_2, gbc_scrollPane_2);

		textField = new JTextField();
		textField.setSelectionColor(SECONDARY_COLOR);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					sendMessage(chat, textField, listaContactos.getSelectedValue());
				} catch (IllegalArgumentException e) {
				}
			}
		});
		scrollPane_2.setViewportView(textField);
		textField.setColumns(10);
		lblSend.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/paper plane-white.png")));
		GridBagConstraints gbc_lblSend = new GridBagConstraints();
		gbc_lblSend.gridx = 2;
		gbc_lblSend.gridy = 0;
		writeText.add(lblSend, gbc_lblSend);
	}

	/**
	 * Crea el renderer para mostrar la lista con los chats del usuario
	 * 
	 * @return Objeto que se pasará como parámetro a la lista con los chats para que
	 *         sepa mostrarlos
	 */
	private static ListCellRenderer<? super Contact> createListRenderer() {
		return new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Contact contacto = (Contact) value;

				JPanel panel = new JPanel();
				GridBagLayout gbl_contentPane = new GridBagLayout();
				gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0 };
				gbl_contentPane.rowHeights = new int[] { 10, 26, 5, 10, 0 };
				gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
				gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
				panel.setLayout(gbl_contentPane);

				JLabel label = new JLabel();
				ImageIcon img = contacto.getFoto();
				label.setIcon(resizeIcon(img, ICON_SIZE));

				GridBagConstraints gbc_label = new GridBagConstraints();
				gbc_label.anchor = GridBagConstraints.SOUTH;
				gbc_label.gridheight = 2;
				gbc_label.insets = new Insets(0, 0, 5, 5);
				gbc_label.gridx = 0;
				gbc_label.gridy = 1;
				panel.add(label, gbc_label);

				JLabel lblContactName = new JLabel(contacto.getNombre());
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.anchor = GridBagConstraints.SOUTH;
				gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel.gridx = 1;
				gbc_lblNewLabel.gridy = 1;
				panel.add(lblContactName, gbc_lblNewLabel);

				JLabel lblUltimoMensaje;
				Message ultimoMensaje = Controlador.getInstancia().getUltimoMensaje(contacto);
				if (ultimoMensaje != null) {
					lblUltimoMensaje = new JLabel(ultimoMensaje.getHora().format(DATE_FORMAT) + " ");
					lblUltimoMensaje.setFont(lblUltimoMensaje.getFont().deriveFont(Font.PLAIN));
				} else {
					lblUltimoMensaje = new JLabel();
				}
				GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
				gbc_lblNewLabel_1.anchor = GridBagConstraints.SOUTHEAST;
				gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
				gbc_lblNewLabel_1.gridx = 2;
				gbc_lblNewLabel_1.gridy = 1;
				panel.add(lblUltimoMensaje, gbc_lblNewLabel_1);
				
				JLabel lblMensaje;
				if (ultimoMensaje != null) {
					// Si no tiene texto es un emoji
					String textoUltimoMensaje = ultimoMensaje.getTexto();
					if (textoUltimoMensaje.isEmpty()) {
						lblMensaje = new JLabel("😀");
						lblMensaje.setFont(lblMensaje.getFont().deriveFont(20));
					} else {
						// Si el último mensaje es muy largo no se muestra entero
						if (textoUltimoMensaje.length() > MAX_CHARS_LAST_MESSAGE) {
							textoUltimoMensaje = textoUltimoMensaje.substring(0, Math.min(MAX_CHARS_LAST_MESSAGE, textoUltimoMensaje.lastIndexOf(' '))) + "...";
						}
						
						lblMensaje = new JLabel(textoUltimoMensaje);
						lblMensaje.setFont(lblMensaje.getFont().deriveFont(Font.ITALIC));
					}
				} else {
					lblMensaje = new JLabel();
				}
				GridBagConstraints gbc_lblEsteHaSido = new GridBagConstraints();
				gbc_lblEsteHaSido.gridwidth = 2;
				gbc_lblEsteHaSido.anchor = GridBagConstraints.WEST;
				gbc_lblEsteHaSido.insets = new Insets(0, 0, 5, 5);
				gbc_lblEsteHaSido.gridx = 1;
				gbc_lblEsteHaSido.gridy = 2;
				panel.add(lblMensaje, gbc_lblEsteHaSido);

				panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				panel.setBackground((isSelected) ? SECONDARY_COLOR : MAIN_COLOR_LIGHT);

				return panel;
			}
		};
	}

	/**
	 * Se encarga de mostrar el menú de opciones
	 * 
	 * @param component Componente que contendrá el menú
	 * @param popup     Menú de opciones a mostrar
	 */
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				showMenu(e);
			}
		});
	}
}
