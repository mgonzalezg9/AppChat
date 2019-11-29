package umu.tds.apps.AppChat;

import java.util.List;

public class Group extends Contact {
	// Properties
	private int codigo;
	private List<IndividualContact> contactos;
	private User admin;
	
	// Constructor.
	public Group(String nombre, List<Message> mensajes, List<IndividualContact> contactos, User admin) {
		super(nombre, mensajes);
		this.contactos = contactos;
		this.admin = admin;
	}
	
	//Getters
	public List<IndividualContact> getContactos() {
		return contactos;
	}

	public User getAdmin() {
		return admin;
	}
	
	// Methods
	public void addMensaje(Message m) {
		super.addMensaje(m);
	}
	
	public void addIntegrante(IndividualContact c) {
		contactos.add(c);
	}
	
	public void cambiarAdmin(User u) {
		admin = u;
	}
}
