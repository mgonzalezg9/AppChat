package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelListener;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.BoxLayout;
import tds.BubbleText;
import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.UserStatu;
import umu.tds.apps.controlador.Controlador;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class Principal extends JFrame {
	private JPanel contentPane;
	private JPanel chat;
	private JLabel profilePhoto;
	private JTextField textField;
	private JPopupMenu popupSettsGrupos;
	private boolean iconsVisible;
	private static PanelList panelList = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void sendMessage(JPanel panel, JTextField textField) throws IllegalArgumentException {
		BubbleText burbuja = new BubbleText(panel, textField.getText(), SENT_MESSAGE_COLOR, "Tú", BubbleText.SENT);
		chat.add(burbuja);
		textField.setText(null);

		// TODO Conectar con persistencia
	}

	private void sendIcon(JPanel panel, int iconID) throws IllegalArgumentException {
		BubbleText burbuja = new BubbleText(panel, iconID, SENT_MESSAGE_COLOR, "Tú", BubbleText.SENT, 10);
		chat.add(burbuja);
	}

	private void loadChat() {
		Controlador.getChat(null, chat).stream().forEach(b -> chat.add(b));
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
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
		gbl_settingsIzq.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		settingsIzq.setLayout(gbl_settingsIzq);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(MAIN_COLOR);
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		settingsIzq.add(panel_1, gbc_panel_1);

		JLabel label = new JLabel("");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				UserSettings settingsUsuario = new UserSettings();
				settingsUsuario.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				settingsUsuario.setVisible(true);
			}
		});
		label.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/Circle-white.png")));
		panel_1.add(label);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(MAIN_COLOR);
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 0;
		settingsIzq.add(panel_2, gbc_panel_2);

		JLabel label_2 = new JLabel("");
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserStatusWindow window = new UserStatusWindow();
				window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				window.setVisible(true);
			}
		});
		label_2.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/status-white.png")));
		panel_2.add(label_2);

		JLabel label_1 = new JLabel("");

		label_1.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/3points-white.png")));
		panel_2.add(label_1);

		popupSettsGrupos = new JPopupMenu();
		addPopup(label_1, popupSettsGrupos);

		JMenuItem mntmNewMenuItem = new JMenuItem("Add Contact");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateContact window = new CreateContact();
				window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				window.setVisible(true);
			}
		});
		popupSettsGrupos.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("New Group");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaNuevoGrupo window = new VentanaNuevoGrupo();
				window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				window.setVisible(true);
			}
		});
		popupSettsGrupos.add(mntmNewMenuItem_1);

		JMenuItem mntmModifyGroup = new JMenuItem("Modify Group");
		mntmModifyGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ModificarGrupo window = new ModificarGrupo();
				window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				window.setVisible(true);
			}
		});
		popupSettsGrupos.add(mntmModifyGroup);

		JMenuItem mntmMostrarContactos = new JMenuItem("Show Contacts");
		mntmMostrarContactos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Contactos window = new Contactos();
				window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				window.setVisible(true);
			}
		});
		popupSettsGrupos.add(mntmMostrarContactos);

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

		JMenuItem mntmShowStatistics = new JMenuItem("Show statistics");
		mntmShowStatistics.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EstadisticasUso window = new EstadisticasUso();
				window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				window.setVisible(true);
			}
		});
		popupSettsGrupos.add(mntmShowStatistics);

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
		gbl_settingsDer.columnWidths = new int[] { 0, 0, 0 };
		gbl_settingsDer.rowHeights = new int[] { 0, 0 };
		gbl_settingsDer.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_settingsDer.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		settingsDer.setLayout(gbl_settingsDer);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(MAIN_COLOR);
		panel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ContactInfo window = new ContactInfo();
				window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				window.setVisible(true);
			}
		});
		FlowLayout flowLayout_2 = (FlowLayout) panel_3.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.VERTICAL;
		gbc_panel_3.anchor = GridBagConstraints.WEST;
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		settingsDer.add(panel_3, gbc_panel_3);

		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/Circle-white.png")));
		panel_3.add(label_3);

		JLabel lblDiegoSevilla = new JLabel("Diego Sevilla");
		lblDiegoSevilla.setForeground(TEXT_COLOR_LIGHT);
		panel_3.add(lblDiegoSevilla);

		JPanel panel = new JPanel();
		panel.setBackground(MAIN_COLOR);
		FlowLayout flowLayout_3 = (FlowLayout) panel.getLayout();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTHEAST;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		settingsDer.add(panel, gbc_panel);

		JLabel label_4 = new JLabel("");
		label_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Busqueda ventanaBusqueda = new Busqueda();
				ventanaBusqueda.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				ventanaBusqueda.setVisible(true);
			}
		});
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
				chat.removeAll();
				chat.updateUI();
			}
		});
		popupSettsChat.add(mntmRemoveAllMessages);

		JMenuItem mntmDeleteContact = new JMenuItem("Delete chat");
		mntmDeleteContact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Controlador.getInstancia().deleteChat()) {
					JOptionPane.showMessageDialog(Principal.this, "This chat was deleted succesfully", "Chat deleted",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(Principal.this,
							"Unable to delete the chat. Please check your chat privileges.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		popupSettsChat.add(mntmDeleteContact);
		
		List<Contact> contactos = new LinkedList<Contact>();
		contactos.add(new Contact(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/user50.png")), "Alfonsito", "Probando los estados", "23/11/2019"));
		contactos.add(new Contact(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/user50.png")), "Manuel", "En luminata", "10/09/2019"));
		contactos.add(new Contact(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/user50.png")), "Diego", "XV6", "06/08/2009"));
		contactos.add(new Contact(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/user50.png")), "Alfon", "In github", "21/10/2019"));
		contactos.add(new Contact(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/user50.png")), "Alf", "Este es mi estado", "08/01/2010"));
		contactos.add(new Contact(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/user50.png")), "Manuel", "En el fiestódromo", "09/10/2015"));
		contactos.add(new Contact(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/user50.png")), "Blacknuel", "4nite", "12/05/2017"));
		contactos.add(new Contact(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/user50.png")), "Joseliko", "Guild Wars 2", "10/10/2010"));
		contactos.add(new Contact(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/user50.png")), "Arberto", "Madremía Arberto", "25/07/2005"));
		contactos.add(new Contact(new ImageIcon(Principal.class.getResource("/umu/tds/apps/resources/user50.png")), "Oscarizado", "Tortas fritas everywhere", "11/08/1973"));
		
		panelList = new PanelList(70, (Function<Contact, JPanel>)Principal::supplyPanel, contactos, contactos.size());
		JPanel listaChats = panelList.container;
		listaChats.setBackground(MAIN_COLOR_LIGHT);
		listaChats.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		listaChats.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				final JPanel itemUnderMouse = panelList.getItemUnderMouse(e);
                if (itemUnderMouse != null) {
                    itemUnderMouse.setBackground(SECONDARY_COLOR);
                }
            }
		});
		
		GridBagConstraints gbc_listaChats = new GridBagConstraints();
		gbc_listaChats.insets = new Insets(0, 0, 0, 5);
		gbc_listaChats.fill = GridBagConstraints.BOTH;
		gbc_listaChats.gridx = 0;
		gbc_listaChats.gridy = 1;
		contentPane.add(listaChats, gbc_listaChats);

		profilePhoto = new JLabel();
		profilePhoto.setIcon(new ImageIcon(
				Principal.class.getResource("/umu/tds/apps/resources/173312_magnifying-glass-icon-png.png")));

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

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		scrollPane_1.setBackground(CHAT_COLOR);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		chatPersonal.add(scrollPane_1, gbc_scrollPane_1);

		chat = new JPanel();
		chat.setBackground(CHAT_COLOR);
		scrollPane_1.setViewportView(chat);
		chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
		chat.setSize(400, 700);

		// Se muestran todas las burbujas de la conversacion actual
		loadChat();

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
					sendIcon(chat, Integer.valueOf(labelIconos.getName()));
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
					sendMessage(chat, textField);
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
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					sendMessage(chat, textField);
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
	
	// Cremos el panel correspondiente a un elemento de la lista.
    private static JPanel supplyPanel(Contact contacto) {
    	// Componentes del panel.
    	final JLabel icon = new JLabel("");
    	icon.setIcon(contacto.getIcon());
    	final JLabel labelUserStatus = new JLabel(contacto.getName() + ": " + contacto.getlastMessage());
    	labelUserStatus.setForeground(TEXT_COLOR_LIGHT);
        final JLabel labelDate = new JLabel(contacto.getDate());
        labelDate.setForeground(TEXT_COLOR_LIGHT);
        
        labelUserStatus.setHorizontalAlignment(SwingConstants.CENTER);
        labelUserStatus.setVerticalAlignment(SwingConstants.CENTER);

        // Creamos el panel
        final JPanel panel = new JPanel(new BorderLayout(0,
                                                         0));
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        panel.setBackground(MAIN_COLOR_LIGHT);
        
        panel.add(icon, BorderLayout.WEST);
        panel.add(labelUserStatus, BorderLayout.CENTER);
        panel.add(labelDate, BorderLayout.AFTER_LINE_ENDS);

        // Devolvemos un elemento de la lista.
        return panel;
    }

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
