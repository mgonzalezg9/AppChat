package umu.tds.apps.controlador;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tds.BubbleText;
import umu.tds.apps.vistas.Login;
import umu.tds.apps.AppChat.*;
import umu.tds.apps.persistencia.DAOException;
import umu.tds.apps.persistencia.FactoriaDAO;
import umu.tds.apps.persistencia.GroupDAO;
import umu.tds.apps.persistencia.IndividualContactDAO;
import umu.tds.apps.persistencia.MessageDAO;
import umu.tds.apps.persistencia.StatusDAO;
import umu.tds.apps.persistencia.UserDAO;
import umu.tds.apps.vistas.UserSettings;

public class Controlador {
	// Instancia del controlador.
	private static Controlador unicaInstancia = null;
	
	// Adaptadores
	private GroupDAO adaptadorGrupo;
	private IndividualContactDAO adaptadorContactoIndividual;
	private MessageDAO adaptadorMensaje;
	private UserDAO adaptadorUsuario;
	private StatusDAO adaptadorEstado;

	// Catálogos
	private UsersCatalogue catalogoUsuarios;
	
	// Nuestro usuario.
	private User usuarioActual;

	private Controlador() {
		inicializarAdaptadores(); // debe ser la primera linea para evitar error de sincronización
		inicializarCatalogos();
	}

	// Aplicamos el patrón Singleton.
	// Consiguiendo de esta forma que exista una única instancia de la clase
	// controlador que es accesible globalmente.
	public static Controlador getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}
	
	// Inicializamos los adaptadores
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		adaptadorGrupo = factoria.getGrupoDAO();
		adaptadorContactoIndividual = factoria.getContactoIndividualDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
		adaptadorUsuario = factoria.getUserDAO();
		adaptadorEstado = factoria.getEstadoDAO();
	}

	// Inicializamos los catálogos
	private void inicializarCatalogos() {
		catalogoUsuarios = UsersCatalogue.getUnicaInstancia();
	}

	public boolean iniciarSesion(String nick, String password) {
		// TODO
		return false;
	}

	
	
	
	public String getNombreUsuario() {
		return nickname;
	}

	public String getSaludo() {
		return saludo;
	}

	public void setSaludo(String saludo) {
		Controlador.saludo = saludo;
	}

	public List<ImageIcon> getImagenesUsuario() {
		return imagenes;
	}

	public void addImagenUsuario(ImageIcon image) {
		imagenes.add(image);
	}

	public ImageIcon removeImagenUsuario(int pos) {
		return imagenes.remove(pos);
	}

	public boolean deleteChat() {
		return false;
	}

	public Status getEstado(User u) {
		if (new Random().nextInt(2) == 0) {
			return new Status(new ImageIcon(Controlador.class.getResource("/umu/tds/apps/resources/user.png")),
					"Flying");
		} else
			return new Status(new ImageIcon(Controlador.class.getResource("/umu/tds/apps/resources/fire_120x120.png")),
					"Fuegooo");
	}

	public User getUsuario() {
		return new User(new ImageIcon(), "");
	}

	public static List<BubbleText> getChat(User u, JPanel chat) {
		List<BubbleText> list = new LinkedList<BubbleText>();
		BubbleText burbuja = new BubbleText(chat, "Hola, ¿Como van las burbujas? xD", Color.LIGHT_GRAY, "Dieguin",
				BubbleText.RECEIVED);
		list.add(burbuja);
		return list;
	}
}
