package umu.tds.apps.AppChat;

import static umu.tds.apps.vistas.Theme.*;

import java.util.LinkedList;
import java.util.List;
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
	public List<IndividualContact> getContactos() {
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

	public void modificarIntegrantes(List<IndividualContact> contactos) {
		this.integrantes = contactos;
	}

	// Devuelve los mensajes que han enviado el resto de usuarios por el grupo
	public List<Message> getMensajesRecibidos() {
		return this.integrantes.stream().flatMap(c -> c.getUsuario().getContactos().stream())
				.filter(c -> c instanceof Group).map(c -> (Group) c).filter(g -> g.getCodigo() == this.getCodigo())
				.flatMap(g -> g.getMensajesEnviados().stream()).collect(Collectors.toList());
	}

	public List<Message> getMisMensajesGrupo(User usuario) {
		return getMensajesEnviados().stream().filter(m -> m.getEmisor().getCodigo() == usuario.getCodigo())
				.collect(Collectors.toList());
	}

	// Borra los mensajes que recibo de ese contacto
	public List<Message> removeMensajesRecibidos() {
		List<Message> recibidos = getMensajesRecibidos();
		List<Message> copia = new LinkedList<Message>(recibidos);
		recibidos.clear();
		return copia;
	}

}
