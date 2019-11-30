package umu.tds.apps.AppChat;

import java.util.List;

import javax.swing.ImageIcon;

public abstract class Contact {
	// Properties.
	private int codigo;
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
	
	public void setNombre(String nombre) { // Generado para los test
		this.nombre = nombre;
	}

	public List<Message> getMensajes() {
		return mensajes;
	}
	
	public void addMensaje(Message m) {
		mensajes.add(m);
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	// Setters
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}
