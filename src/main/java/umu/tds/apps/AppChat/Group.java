package umu.tds.apps.AppChat;

import static umu.tds.apps.vistas.Theme.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;

public class Group extends Contact {
	// Properties
	private List<IndividualContact> integrantes;
	private User admin;

	// Constructor.
	public Group(String nombre, List<IndividualContact> contactos, User admin) {
		super(nombre);
		this.integrantes = contactos;
		this.admin = admin;
	}

	public Group(String nombre, List<Message> mensajes, List<IndividualContact> contactos, User admin) {
		super(nombre, mensajes);
		this.integrantes = contactos;
		this.admin = admin;
	}

	// Getters
	public List<IndividualContact> getParticipantes() {
		return integrantes;
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
		integrantes.add(c);
	}

	public void cambiarAdmin(User u) {
		admin = u;
	}

	public void setIntegrantes(List<IndividualContact> contactos) {
		this.integrantes = contactos;
	}

	// Devuelve los mensajes que han enviado el resto de usuarios por el grupo. El valor del parametro pasado como parámetro no importa
	@Override
	public List<Message> getMensajesRecibidos(Optional<User> emptyOpt) {
		return this.integrantes.stream().flatMap(c -> c.getUsuario().getContactos().stream())
				.filter(c -> c instanceof Group).map(c -> (Group) c).filter(g -> this.equals(g))
				.flatMap(g -> g.getMensajesEnviados().stream()).collect(Collectors.toList());
	}

	public List<Message> getMisMensajesGrupo(User usuario) {
		return getMensajesEnviados().stream().filter(m -> m.getEmisor().getCodigo() == usuario.getCodigo())
				.collect(Collectors.toList());
	}

	// Borra los mensajes que recibo de ese contacto
	public List<Message> removeMensajesRecibidos() {
		List<Message> recibidos = getMensajesRecibidos(Optional.empty());
		List<Message> copia = new LinkedList<Message>(recibidos);
		recibidos.clear();
		return copia;
	}

	/**
	 * Indica si el usuario pertenece al grupo
	 * @param usuario Usuario a comprobar si está en el grupo
	 * @return Devuelve si el usuario pertenece al grupo
	 */
	public boolean hasParticipante(User usuario) {
		return integrantes.stream()
				.anyMatch(i -> i.getUsuario().equals(usuario));
	}

	// HashCode e Equals
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getNombre() == null) ? 0 : getNombre().hashCode());
		return result;
	}

	/**
	 * Dos grupos son iguales si tienen el mismo nombre.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (getNombre() == null) {
			if (other.getNombre() != null)
				return false;
		} else if (!getNombre().equals(other.getNombre()))
			return false;
		return true;
	}
}
