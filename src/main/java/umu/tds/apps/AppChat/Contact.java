package umu.tds.apps.AppChat;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;

public abstract class Contact {
	// Properties.
	private int codigo;
	private String nombre;
	private List<Message> mensajes;

	// Constructor.
	/**
	 * Constructor de la clase contacto
	 * 
	 * @param nombre Nombre del contacto
	 */
	public Contact(String nombre) {
		this(nombre, new LinkedList<>());
	}

	/**
	 * Constructor sobrecargado de la clase contacto
	 * 
	 * @param nombre Nombre del contacto
	 * @param mensajes Lista de mensajes intercambiados con el usuario
	 */
	public Contact(String nombre, List<Message> mensajes) {
		this.nombre = nombre;
		this.mensajes = mensajes;
	}

	// Getters.
	/**
	 * Devuelve el nombre del contacto
	 * 
	 * @return Nombre del contacto
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve los mensajes que ese contacto recibe de mi
	 * 
	 * @return Lista con todos los mensajes
	 */
	public List<Message> getMensajesEnviados() {
		return mensajes;
	}

	/**
	 * Devuelve los mensajes que recibo de ese contacto
	 * 
	 * @param usuario Usuario al que se le mandaron los mensajes
	 * @return Lista con los mensajes que este contacto envió al usuario. Estará
	 *         vacía si no le envió ninguno
	 */
	public abstract List<Message> getMensajesRecibidos(Optional<User> usuario);

	/**
	 * Devuelve el código del contacto
	 * 
	 * @return Código del contacto
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * Devuelve la foto del contacto
	 * 
	 * @return Devuelve la foto del contacto
	 */
	public abstract ImageIcon getFoto();

	// Setters
	/**
	 * Setter para el código
	 * 
	 * @param codigo Codigo del contacto
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	/**
	 * Setter para el nombre
	 * 
	 * @param nombre Nombre del contacto
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Elimina los mensajes eliminados
	 * 
	 * @return Lista con los mensajes que le envié a ese contacto.
	 */
	public List<Message> removeMensajesEnviados() {
		List<Message> lista = new LinkedList<>(mensajes); // copia
		mensajes.clear();
		return lista;
	}

	/**
	 * Método para añadir mensajes con ese contacto
	 * 
	 * @param mensajes Mensajes a añadir a la lista de mensajes
	 */
	public void addMensajes(List<Message> mensajes) {
		this.mensajes.addAll(mensajes);
	}

	// Methods
	/**
	 * Método para añadir un mensajes con ese contacto
	 * 
	 * @param mensaje Mensaje a añadir a la lista de mensajes que le he enviado a ese contaacto
	 */
	public void sendMessage(Message message) {
		mensajes.add(message);
	}

	@Override
	public String toString() {
		return nombre;
	}
}
