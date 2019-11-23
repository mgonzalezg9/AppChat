package umu.tds.apps.vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import umu.tds.apps.AppChat.UserStatu;

public class UserStatusWindow extends JFrame {
	// Propiedades.
	private JPanel contentPane;
	private static UserStatesPanelList panelList = null;

	// Método principal.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserStatusWindow frame = new UserStatusWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Constructor.
	public UserStatusWindow() {
		// Lista de contactos a mostrar.
		List<UserStatu> usuarios = new LinkedList<UserStatu>();
		usuarios.add(new UserStatu(new ImageIcon(UserStatesPanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Alfonsito", "Probando los estados", "23/11/2019"));
		usuarios.add(new UserStatu(new ImageIcon(UserStatesPanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Manuel", "En luminata", "10/09/2019"));
		usuarios.add(new UserStatu(new ImageIcon(UserStatesPanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Diego", "XV6", "06/08/2009"));
		usuarios.add(new UserStatu(new ImageIcon(UserStatesPanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Alfon", "In github", "21/10/2019"));
		usuarios.add(new UserStatu(new ImageIcon(UserStatesPanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Alf", "Este es mi estado", "08/01/2010"));
		usuarios.add(new UserStatu(new ImageIcon(UserStatesPanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Manuel", "En el fiestódromo", "09/10/2015"));
		usuarios.add(new UserStatu(new ImageIcon(UserStatesPanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Blacknuel", "4nite", "12/05/2017"));
		usuarios.add(new UserStatu(new ImageIcon(UserStatesPanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Joseliko", "Guild Wars 2", "10/10/2010"));
		usuarios.add(new UserStatu(new ImageIcon(UserStatesPanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Arberto", "Madremía Arberto", "25/07/2005"));
		usuarios.add(new UserStatu(new ImageIcon(UserStatesPanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Oscarizado", "Tortas fritas everywhere", "11/08/1973"));
		
		// Creamos el panel de usuarios. Tamaño de cada elemento, lista de contactos y número de contactos.
		panelList = new UserStatesPanelList(150, usuarios, usuarios.size());
		
		// Establecemos el contentPane y lo mostramos.
		contentPane = panelList.container;
        contentPane.setPreferredSize(new Dimension(450, 800));
        contentPane.setBackground(Color.GRAY);
        
		setContentPane(contentPane);
		setIconImage(Toolkit.getDefaultToolkit().getImage(EstadosOld.class.getResource("/umu/tds/apps/resources/icon.png")));
		setTitle("Status");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
