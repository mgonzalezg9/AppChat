package umu.tds.apps.AppChat;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
	/**
	 * Dado un usuario me devuelve el contacto que este usuario tiene (como lo ve
	 * desde su perspectiva)
	 * 
	 * @param usuario Usuario cuyo contacto quiero obtener
	 * @return Devuelve el contacto que tengo guardado para el usuario pasado como
	 *         parámetro. Null si no lo tengo guardado
	 */
	public IndividualContact getContacto(User usuario) {
		return this.usuario.getContactos().stream().filter(c -> c instanceof IndividualContact)
				.map(c -> (IndividualContact) c).filter(c -> c.getUsuario().equals(usuario)).findAny().orElse(null);
	}

	@Override
	public List<Message> getMensajesRecibidos(Optional<User> usuario) {
		IndividualContact contacto = getContacto(usuario.orElse(null));
		if (contacto != null) {
			return contacto.getMensajesEnviados();
		} else
			return new LinkedList<>();
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

	/**
	 * Modifica el grupo del contacto
	 * 
	 * @param g Grupo ya modificado
	 */
	public void modificarGrupo(Group g) {
		List<Group> grupos = usuario.getGrupos();

		grupos.remove(g);
		grupos.add(g);
	}

	// Borra los mensajes que le ha mandado este contacto al usuarioActual
	public List<Message> removeMensajesRecibidos(User usuarioActual) {
		List<Message> recibidos = getContacto(usuarioActual).getMensajesEnviados();
		List<Message> copia = new LinkedList<>(recibidos);
		recibidos.clear();
		return copia;
	}

	// HashCode e Equals
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + movil;
		return result;
	}

	/**
	 * Dos contactos son iguales si tienen el mismo número de teléfono
	 * 
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
		IndividualContact other = (IndividualContact) obj;
		if (movil != other.movil)
			return false;
		return true;
	}

	/**
	 * Comprueba si se corresponde con el usuario pasado como parámetro
	 * 
	 * @param otherUser Usuario con el que realizar la comprobación
	 * @return Devuelve si el usuario asociado al contacto es el mismo que el pasado
	 *         como parámetro
	 */
	public boolean isUser(User otherUser) {
		return usuario.equals(otherUser);
	}

}
