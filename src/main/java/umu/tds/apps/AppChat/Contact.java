package umu.tds.apps.AppChat;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;

public abstract class Contact {
	// Properties.
	private int codigo;
	private String nombre;
	private List<Message> mensajes;

	// Constructor.
	public Contact(String nombre) {
		this(nombre, new LinkedList<>());
	}

	public Contact(String nombre, List<Message> mensajes) {
		this.nombre = nombre;
		this.mensajes = mensajes;
	}

	// Getters.
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

	public int getCodigo() {
		return codigo;
	}

	public abstract ImageIcon getFoto();

	// Setters
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Message> removeMensajesEnviados() {
		List<Message> lista = new LinkedList<>(mensajes); // copia
		mensajes.clear();
		return lista;
	}

	public void addMensajes(List<Message> mensajes) {
		this.mensajes.addAll(mensajes);
	}

	// public abstract List<Message> removeMensajesRecibidos();

	// Methods
	public void sendMessage(Message message) {
		mensajes.add(message);
	}

}
