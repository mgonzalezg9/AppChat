package umu.tds.apps.vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;

public class Principal extends JFrame {

	private JPanel contentPane;

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

	/**
	 * Create the frame.
	 */
	public Principal() {
		setTitle("AppChat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel panelListaChat = new JPanel();
		contentPane.add(panelListaChat);
		panelListaChat.setLayout(new BorderLayout(0, 0));
		
		JPanel panelToolBar = new JPanel();
		panelListaChat.add(panelToolBar, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panelToolBar.add(panel_1);
		
		JLabel lblImg = new JLabel("ajustes");
		panel_1.add(lblImg);
		
		JPanel panel = new JPanel();
		panelToolBar.add(panel);
		
		JPanel panelLista = new JPanel();
		panelListaChat.add(panelLista, BorderLayout.SOUTH);
		
		JLabel lblListaChats = new JLabel("Lista chats");
		panelLista.add(lblListaChats);
		
		JPanel panelConversacion = new JPanel();
		contentPane.add(panelConversacion);
		panelConversacion.setLayout(new BorderLayout(0, 0));
		
		JPanel panelToolBar_1 = new JPanel();
		panelConversacion.add(panelToolBar_1, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		panelToolBar_1.add(panel_2);
		
		JLabel lblFoto = new JLabel("info chat");
		panel_2.add(lblFoto);
		
		JPanel panel_3 = new JPanel();
		panelToolBar_1.add(panel_3);
		
		JPanel panelMensajes = new JPanel();
		panelConversacion.add(panelMensajes, BorderLayout.SOUTH);
		
		JLabel lblConversacion = new JLabel("Conversacion");
		panelMensajes.add(lblConversacion);
	}

}
