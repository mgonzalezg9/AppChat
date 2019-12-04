package umu.tds.apps.controlador;

import java.awt.Color;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tds.BubbleText;
import umu.tds.apps.AppChat.*;
import umu.tds.apps.persistencia.DAOException;
import umu.tds.apps.persistencia.FactoriaDAO;
import umu.tds.apps.persistencia.GroupDAO;
import umu.tds.apps.persistencia.IndividualContactDAO;
import umu.tds.apps.persistencia.MessageDAO;
import umu.tds.apps.persistencia.StatusDAO;
import umu.tds.apps.persistencia.UserDAO;

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
		User cliente = catalogoUsuarios.getUsuario(nick);

		// Si la password esta bien inicia sesion
		if (cliente.getPassword().equals(password)) {
			usuarioActual = cliente;
			return true;
		}
		return false;
	}

	public boolean crearCuenta(String User, String password, String email, String name, int numTelefono,
			LocalDate fechaNacimiento) { // ALFONSITO
		// TODO Registro el usuario. Devuelvo false si el nick ya está en uso
		return false;
	}

	public User getUsuarioActual() {
		return usuarioActual;
	}

	public String getNombreUsuario() {
		return usuarioActual.getName();
	}

	public String getSaludo() {
		return usuarioActual.getSaludo();
	}

	public void setSaludo(String saludo) {
		usuarioActual.setSaludo(saludo);
	}

	public List<ImageIcon> getImagenesUsuario() { // MANUELITO
		// TODO obtener todas la imagenes del usuario
		List<ImageIcon> images = new LinkedList<>();
		images.add(usuarioActual.getProfilePhotos());
		return images;
	}

	public void addImagenUsuario(ImageIcon image) { // MANUELITO
		// TODO añadir una imagen al conjunto de imágenes del usuario
		usuarioActual.addProfilePhoto(image);
	}

	public List<Contact> obtenerMisContactos() { // ALFONSITO
		// TODO devuelvo mi lista de contactos. Saco el código del usuario actual.

		return new LinkedList<Contact>();
	}

	public Message obtenerUltimoMensaje(Contact contacto) { // ALFONSITO
		// TODO devuelvo el último mensaje con ese contacto.

		return null;
	}

	public List<Message> obtenerMensajes(Contact contacto) { // ALFONSITO
		// TODO devuelvo mi lista de mensajes con ese contacto. Saco el código del
		// contacto del que me pasan.

		return new LinkedList<Message>();
	}

	public boolean crearContacto(ImageIcon imagen, String nombre, int numTelefono) { // ALFONSITO.
		// TODO creo el contacto. Da error si tiene como nombre el de otro ya creado.

		return false;
	}

	public void crearGrupo(String nombre, List<IndividualContact> participantes) { // ALFONSITO
		// TODO creo el grupo. EL USUARIO ACTUAL ES EL ADMINISTRADOR
	}

	public List<Group> obtenerMisGruposAdministrados() { // ALFONSITO
		// TODO devuelvo una lista de mis grupos. Saco el código del usuario actual.

		return new LinkedList<Group>();
	}

	public List<IndividualContact> obtenerContactosDelGrupo(Group grupo) { // ALFONSITO
		// TODO devuelvo una lista de los contactos de mi grupo. Saco el código del
		// usuario actual.

		return new LinkedList<IndividualContact>();
	}

	public String obtenerNombreContacto(Contact contacto) {
		return contacto.getNombre();
	}

	public ImageIcon obtenerImagenContacto(IndividualContact contacto) {
		return contacto.getUsuario().getProfilePhotos();
	}

	public int obtenerNumTelefonoContacto(IndividualContact contacto) {
		return contacto.getMovil();
	}

	public String obtenerNombreGrupo(Group grupo) {
		return grupo.getNombre();
	}

	public List<String> obtenerNombresGrupos(IndividualContact contacto) { // ALFONSITO
		// TODO devuelvo una lista con los nombres de los grupos en los que se usuario y
		// yo estamos.
		return new LinkedList<String>();
	}

	public void hacerPremium() { // MANUELITO
		// TODO Ponerle algun descuento segun convenga
		usuarioActual.setPremium();
	}

	public void cerrarSesion() { // MANUELITO
		usuarioActual = null;
	}

	public List<Message> buscarMensajes(String emisor, LocalDate fechaInicio, LocalDate fechaFin, String text) { // MANUELITO
		// TODO obtener los mensaje que cumplan el filtro (predicado)

		return null;
	}

	public boolean deleteChat() {
		// TODO PREGUNTAR SI borrar los mensajes de la base de datos.
		return false;
	}

	public boolean deleteContact(Contact c) { // ALFONSITO
		// TODO borrar el contacto de la base de datos.
		return false;
	}

	public static List<BubbleText> getChat(User u, JPanel chat) {
		List<BubbleText> list = new LinkedList<BubbleText>();
		BubbleText burbuja = new BubbleText(chat, "Hola, ¿Como van las burbujas? xD", Color.LIGHT_GRAY, "Dieguin",
				BubbleText.RECEIVED);
		list.add(burbuja);
		return list;
	}
}
