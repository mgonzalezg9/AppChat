package umu.tds.apps.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.AppChat.IndividualContact;
import umu.tds.apps.AppChat.Message;
import umu.tds.apps.AppChat.User;

public class AdaptadorIndividualContactTDS implements IndividualContactDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorIndividualContactTDS unicaInstancia = null;

	private AdaptadorIndividualContactTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public static AdaptadorIndividualContactTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorIndividualContactTDS();
		return unicaInstancia;
	}

	@Override
	public void registrarContacto(IndividualContact contact) {
		Entidad eContact = new Entidad();
		boolean existe = true;

		// Si la entidad está registrada no la registra de nuevo
		try {
			eContact = servPersistencia.recuperarEntidad(contact.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe)
			return;

		// Registramos primero los atributos que son objetos
		// Registrar los mensajes del contacto
		registrarSiNoExistenMensajes(contact.getMensajes());

		// Registramos al usuario correspondiente al contacto si no existe.
		registrarSiNoExisteUser(contact.getUsuario());

		// Atributos propios del contacto
		eContact.setNombre("contacto");
		eContact.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", contact.getNombre()),
				new Propiedad("movil", String.valueOf(contact.getMovil())),
				new Propiedad("mensajesRecibidos", obtenerCodigosMensajesRecibidos(contact.getMensajes())),
				new Propiedad("usuario", String.valueOf(contact.getUsuario().getCodigo())))));

		// Registrar entidad usuario
		eContact = servPersistencia.registrarEntidad(eContact);

		// Identificador unico
		contact.setCodigo(eContact.getId());
		
		// Guardamos en el pool
		PoolDAO.getInstancia().addObjeto(contact.getCodigo(), contact);
	}

	@Override
	public void borrarContacto(IndividualContact contact) {
		// Borramos los elementos de las tablas que lo componen (En este caso borramos
		// los mensajes del contacto a eliminar)
		Entidad eContact;
		AdaptadorMessageTDS adaptadorMensaje = AdaptadorMessageTDS.getInstancia();

		for (Message mensaje : contact.getMensajes()) {
			adaptadorMensaje.borrarMensaje(mensaje);
		}

		eContact = servPersistencia.recuperarEntidad(contact.getCodigo());
		servPersistencia.borrarEntidad(eContact);
		
		// Si está en el pool, borramos del pool
		if (PoolDAO.getInstancia().contiene(contact.getCodigo()))
			PoolDAO.getInstancia().removeObjeto(contact.getCodigo());
	}

	@Override
	public void modificarContacto(IndividualContact contact) {
		Entidad eContact = servPersistencia.recuperarEntidad(contact.getCodigo());

		// Se da el cambiazo a las propiedades del contacto
		servPersistencia.eliminarPropiedadEntidad(eContact, "nombre");
		servPersistencia.anadirPropiedadEntidad(eContact, "nombre", contact.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eContact, "movil");
		servPersistencia.anadirPropiedadEntidad(eContact, "movil", contact.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eContact, "mensajesRecibidos");
		servPersistencia.anadirPropiedadEntidad(eContact, "mensajesRecibidos",
				obtenerCodigosMensajesRecibidos(contact.getMensajes()));
		servPersistencia.eliminarPropiedadEntidad(eContact, "usuario");
		servPersistencia.anadirPropiedadEntidad(eContact, "usuario", String.valueOf(contact.getUsuario().getCodigo()));
	}

	@Override
	public IndividualContact recuperarContacto(int codigo) {
		// Si la entidad esta en el pool la devuelve directamente
		if (PoolDAO.getInstancia().contiene(codigo))
			return (IndividualContact) PoolDAO.getInstancia().getObjeto(codigo);

		// Sino, la recupera de la base de datos
		// Recuperamos la entidad
		Entidad eContact = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		String nombre = null;
		nombre = servPersistencia.recuperarPropiedadEntidad(eContact, "nombre");
		
		String movil = null;
		movil = servPersistencia.recuperarPropiedadEntidad(eContact, "movil");

		IndividualContact contact = new IndividualContact(nombre, new LinkedList<Message>(), Integer.valueOf(movil), null);
		contact.setCodigo(codigo);

		// Metemos al contacto en el pool antes de llamar a otros adaptadores
		PoolDAO.getInstancia().addObjeto(codigo, contact);
		
		// Mensajes que el contacto tiene
		List<Message> mensajes = obtenerMensajesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eContact, "mensajesRecibidos"));
		for (Message m : mensajes)
			contact.addMensaje(m);

		// Obtener usuario del contacto
		contact.setUsuario(obtenerUsuarioDesdeCodigo(servPersistencia.recuperarPropiedadEntidad(eContact, "usuario")));

		// Devolvemos el objeto contacto
		return contact;
	}

	@Override
	public List<IndividualContact> recuperarTodosContactos() {
		List<IndividualContact> contactos = new LinkedList<>();
		List<Entidad> eContacts = servPersistencia.recuperarEntidades("contacto");

		for (Entidad eContact : eContacts) {
			contactos.add(recuperarContacto(eContact.getId()));
		}
		
		return contactos;
	}

	// Funciones auxiliares.
	private void registrarSiNoExistenMensajes(List<Message> messages) {
		AdaptadorMessageTDS adaptadorMensajes = AdaptadorMessageTDS.getInstancia();
		messages.stream().forEach(m -> adaptadorMensajes.registrarMensaje(m));
	}

	private void registrarSiNoExisteUser(User admin) {
		AdaptadorUserTDS adaptadorUsuarios = AdaptadorUserTDS.getInstancia();
		adaptadorUsuarios.registrarUsuario(admin);
	}

	private String obtenerCodigosMensajesRecibidos(List<Message> mensajesRecibidos) {
		return mensajesRecibidos.stream().map(m -> String.valueOf(m.getCodigo())).reduce("", (l, m) -> l + m + " ")
				.trim();
	}

	private List<Message> obtenerMensajesDesdeCodigos(String codigos) {
		List<Message> mensajes = new LinkedList<>();
		StringTokenizer strTok = new StringTokenizer(codigos, " ");
		AdaptadorMessageTDS adaptadorMensajes = AdaptadorMessageTDS.getInstancia();
		while (strTok.hasMoreTokens()) {
			String code = (String) strTok.nextElement();
			mensajes.add(adaptadorMensajes.recuperarMensaje(Integer.valueOf(code)));
		}
		return mensajes;
	}
	
	private User obtenerUsuarioDesdeCodigo(String codigo) {
		AdaptadorUserTDS adaptadorUsuarios = AdaptadorUserTDS.getInstancia();
		return adaptadorUsuarios.recuperarUsuario(Integer.valueOf(codigo));
	}
}
