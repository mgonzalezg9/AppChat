package umu.tds.apps.AppChat;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;

public class IndividualContact extends Contact {
	// Properties.
	private int movil;
	private User usuario;

	// Constructor.
	public IndividualContact(String nombre, int movil, User usuario) {
		super(nombre);
		this.movil = movil;
		this.usuario = usuario;
	}

	public IndividualContact(String nombre, LinkedList<Message> mensajes, int movil, User usuario) {
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

	@Override
	public ImageIcon getFoto() {
		return usuario.getProfilePhoto();
	}

	// Setters.
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	// Methods
	// Dado un usuario me devuelve el contacto que este usuario tiene (como lo ve desde su perspectiva)
	public IndividualContact getContacto(User usuario) {
		return this.usuario.getContactos().stream().filter(c -> c instanceof IndividualContact)
		.map(c -> (IndividualContact) c).filter(c -> c.getUsuario().getNick().equals(usuario.getNick())).findAny().get();
	}
	
	// Devuelve los mensajes que el usuario pasado como parametro recibe de este
	// contacto
	public List<Message> getMensajesRecibidos(User usuario) {
		return getContacto(usuario).getMensajesEnviados();
	}

	// Devuelve el estado del contacto
	public Optional<Status> getEstado() {
		return usuario.getEstado();
	}

	// Añade al contacto al grupo en cuestion
	public void addGrupo(Group grupo) {
		usuario.addGrupo(grupo);
	}

	// Expulsamos al contacto del grupo en cuestión
	public void eliminarGrupo(Group grupo) {
		usuario.removeContact(grupo);
	}

	// Modifica el grupo del contacto
	public void modificarGrupo(Group g) {
		List<Group> grupos = usuario.getContactos().stream().filter(c -> c instanceof Group).map(c -> (Group) c)
				.collect(Collectors.toList());
		for (int i = 0; i < grupos.size(); i++)
			if (grupos.get(i).getCodigo() == g.getCodigo())
				grupos.set(i, g);
	}

	// Borra los mensajes que le ha mandado este contacto al usuarioActual
	public List<Message> removeMensajesRecibidos(User usuarioActual) {
		List<Message> recibidos = getContacto(usuarioActual).getMensajesEnviados();
		List<Message> copia = new LinkedList<>(recibidos);
		recibidos.clear();
		return copia;
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
