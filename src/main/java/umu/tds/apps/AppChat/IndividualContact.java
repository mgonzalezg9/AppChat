package umu.tds.apps.AppChat;

import java.util.List;

public class IndividualContact extends Contact {
	// Properties.
	private int movil;
	private User usuario;
	
	// Constructor.
	public IndividualContact(String nombre, List<Message> mensajes, int movil, User usuario) {
		super(nombre, mensajes);
		this.movil = movil;
		this.usuario = usuario;
	}
	
	// Getters.
	public int getMovil() {
		return movil;
	}

	public User getUsuario() {
		return usuario;
	}
}
