package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.MAIN_COLOR_LIGHT;
import static umu.tds.apps.vistas.Theme.TEXT_COLOR_LIGHT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.User;

public class ExamplePanelList extends JFrame {
	// Propiedades.
	private JPanel contentPane;
	private PanelList<User> panelList = null;

	// Método principal.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExamplePanelList frame = new ExamplePanelList();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Constructor.
	public ExamplePanelList() {
		// Lista de contactos a mostrar.
		List<User> usuarios = new LinkedList<User>();
		usuarios.add(new User(new ImageIcon(ExamplePanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Alfonsito", "Probando los estados", "23/11/2019"));
		usuarios.add(new User(new ImageIcon(ExamplePanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Manuel", "En luminata", "10/09/2019"));
		usuarios.add(new User(new ImageIcon(ExamplePanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Diego", "XV6", "06/08/2009"));
		usuarios.add(new User(new ImageIcon(ExamplePanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Alfon", "In github", "21/10/2019"));
		usuarios.add(new User(new ImageIcon(ExamplePanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Alf", "Este es mi estado", "08/01/2010"));
		usuarios.add(new User(new ImageIcon(ExamplePanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Manuel", "En el fiestódromo", "09/10/2015"));
		usuarios.add(new User(new ImageIcon(ExamplePanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Blacknuel", "4nite", "12/05/2017"));
		usuarios.add(new User(new ImageIcon(ExamplePanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Joseliko", "Guild Wars 2", "10/10/2010"));
		usuarios.add(new User(new ImageIcon(ExamplePanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Arberto", "Madremía Arberto", "25/07/2005"));
		usuarios.add(new User(new ImageIcon(ExamplePanelList.class.getResource("/umu/tds/apps/resources/user.png")), "Oscarizado", "Tortas fritas everywhere", "11/08/1973"));
		
		// Creamos el panel de usuarios. Tamaño de cada elemento, lista de contactos y número de contactos.
		panelList = new PanelList<User>(150, (Function<User, JPanel>)ExamplePanelList::supplyPanel, usuarios, usuarios.size());
		
		// Establecemos el contentPane y lo mostramos.
		contentPane = panelList.container;
        contentPane.setPreferredSize(new Dimension(450, 800));
        contentPane.setBackground(Color.GRAY);
        
		setContentPane(contentPane);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Estados.class.getResource("/umu/tds/apps/resources/icon.png")));
		setTitle("Status");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	 // Cremos el panel correspondiente a un elemento de la lista.
    private static JPanel supplyPanel(User usuario) {
    	// Componentes del panel.
    	final JLabel icon = new JLabel("");
    	icon.setIcon(usuario.getIcon());
    	final JLabel labelUserStatus = new JLabel(usuario.getName() + ": " + usuario.getEstado().orElse(null).getFrase());
    	labelUserStatus.setForeground(TEXT_COLOR_LIGHT);
        final JLabel labelDate = new JLabel(usuario.getFechaNacimiento().toString());
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
}