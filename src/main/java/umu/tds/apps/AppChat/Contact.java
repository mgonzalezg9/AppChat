package umu.tds.apps.AppChat;

import java.util.LinkedList;
import java.util.List;

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

	// Devuelve los mensajes que ese contacto recibe de mi
	public List<Message> getMensajesEnviados() {
		return mensajes;
	}

	public int getCodigo() {
		return codigo;
	}

	// Setters
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// Methods
	public void sendMessage(Message message) {
		mensajes.add(message);
	}
}
