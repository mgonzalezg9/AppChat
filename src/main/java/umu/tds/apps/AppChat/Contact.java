package umu.tds.apps.AppChat;

import java.util.LinkedList;
import java.util.List;

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

	// Devuelve los mensajes que ese contacto recibe de mi
	public List<Message> getMensajesEnviados() {
		return mensajes;
	}

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

	//public abstract List<Message> removeMensajesRecibidos();

	// Methods
	public void sendMessage(Message message) {
		mensajes.add(message);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		result = prime * result + ((mensajes == null) ? 0 : mensajes.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (codigo != other.codigo)
			return false;
		if (mensajes == null) {
			if (other.mensajes != null)
				return false;
		} else if (!mensajes.equals(other.mensajes))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
}
