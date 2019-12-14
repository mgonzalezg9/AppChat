package umu.tds.apps.AppChat;

import static umu.tds.apps.vistas.Theme.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;

public class Group extends Contact {
	// Properties
	private List<IndividualContact> contactos;
	private User admin;

	// Constructor.
	public Group(String nombre, List<IndividualContact> contactos, User admin) {
		super(nombre);
		this.contactos = contactos;
		this.admin = admin;
	}

	public Group(String nombre, List<Message> mensajes, List<IndividualContact> contactos, User admin) {
		super(nombre, mensajes);
		this.contactos = contactos;
		this.admin = admin;
	}

	// Getters
	public List<IndividualContact> getContactos() {
		return contactos;
	}

	public User getAdmin() {
		return admin;
	}
	
	@Override
	public ImageIcon getFoto() {
		ImageIcon imagen = new ImageIcon(Group.class.getResource(GROUP_ICON_PATH));
		imagen.setDescription(GROUP_ICON_PATH);
		return imagen;
	}

	// Methods
	public void addIntegrante(IndividualContact c) {
		contactos.add(c);
	}

	public void cambiarAdmin(User u) {
		admin = u;
	}

	// Devuelve los mensajes que han enviado el resto de usuarios por el grupo
	public List<Message> getMensajesRecibidos() {
		return this.contactos.stream().flatMap(c -> c.getUsuario().getContactos().stream())
				.filter(c -> c instanceof Group).map(c -> (Group) c).filter(g -> g.getCodigo() == this.getCodigo())
				.flatMap(g -> g.getMensajesEnviados().stream())
				.collect(Collectors.toList());
	}

	public List<Message> getMensajesGrupo() {
		return getMensajesEnviados();
	}

	// hashCode y equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((admin == null) ? 0 : admin.hashCode());
		result = prime * result + ((contactos == null) ? 0 : contactos.hashCode());
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
		Group other = (Group) obj;
		if (admin == null) {
			if (other.admin != null)
				return false;
		} else if (!admin.equals(other.admin))
			return false;
		if (contactos == null) {
			if (other.contactos != null)
				return false;
		} else if (!contactos.equals(other.contactos))
			return false;
		return true;
	}

	// toString
	/*@Override
	public String toString() {
		return "Group [contactos=" + contactos + ", admin=" + admin + "]";
	}*/
}
