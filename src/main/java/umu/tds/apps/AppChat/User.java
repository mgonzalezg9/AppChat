package umu.tds.apps.AppChat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

public class User {
	// Properties
	private ImageIcon icon;
	private String name;
	private LocalDate fechaNacimiento;
	private int numTelefono;
	private String nick;
	private String password;
	private boolean premium;
	private Status estado;
	private List<Group> gruposAdmin;
	private List<Contact> contactos;
	
	// Constructor
	public User(ImageIcon icon, String name, LocalDate fechaNacimiento, int numTelefono, String nick, String password,
			boolean premium, Status estado, List<Group> gruposAdmin, List<Contact> contactos) {
		this.icon = icon;
		this.name = name;
		this.fechaNacimiento = fechaNacimiento;
		this.numTelefono = numTelefono;
		this.nick = nick;
		this.password = password;
		this.premium = premium;
		this.estado = estado;
		this.gruposAdmin = gruposAdmin;
		this.contactos = contactos;
	}

	
	// QUITAR Construtor solo para pruebas en estadosOld
	public User(ImageIcon icon, String name) {
		this.icon = icon;
		this.name = name;
	}
	
	// QUITAR Construtor solo para pruebas en UserStatusWindow
	public User(ImageIcon icon, String name, String mensaje, String fecha) {
			this.icon = icon;
			this.name = name;
			this.estado = new Status(icon, mensaje);
			this.fechaNacimiento = LocalDate.now();
	}
	
	// Getters
	public ImageIcon getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public int getNumTelefono() {
		return numTelefono;
	}

	public String getNick() {
		return nick;
	}

	public String getPassword() {
		return password;
	}

	public boolean isPremium() {
		return premium;
	}

	public Status getEstado() {
		return estado;
	}

	public List<Group> getGruposAdmin() {
		return gruposAdmin;
	}

	public List<Contact> getContactos() {
		return contactos;
	}
}
