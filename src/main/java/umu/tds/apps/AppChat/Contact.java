package umu.tds.apps.AppChat;

import java.util.List;

import javax.swing.ImageIcon;

public abstract class Contact {
	// Properties.
	private String nombre;
	private List<Message> mensajes;

	// Constructor.
	public Contact(String nombre, List<Message> mensajes) {
		this.nombre = nombre;
		this.mensajes = mensajes;
	}
	
	// Getters.
	public String getNombre() {
		return nombre;
	}
	public List<Message> getMensajes() {
		return mensajes;
	}
}
