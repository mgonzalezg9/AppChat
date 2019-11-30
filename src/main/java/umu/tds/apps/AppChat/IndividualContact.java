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
	
	// Setters.
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	
	// HashCode y equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + movil;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		IndividualContact other = (IndividualContact) obj;
		if (movil != other.movil)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
	// toString
	@Override
	public String toString() {
		return "IndividualContact [movil=" + movil + ", usuario=" + usuario + "]";
	}
}
