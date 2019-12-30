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
import umu.tds.apps.AppChat.Group;
import umu.tds.apps.AppChat.IndividualContact;
import umu.tds.apps.AppChat.Message;
import umu.tds.apps.AppChat.User;

public class AdaptadorGroupTDS implements GroupDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorGroupTDS unicaInstancia = null;

	private AdaptadorGroupTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public static AdaptadorGroupTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorGroupTDS();
		return unicaInstancia;
	}

	@Override
	public void registrarGrupo(Group group) {
		Entidad eGroup = new Entidad();
		boolean existe = true;

		// Si la entidad está registrada no la registra de nuevo
		try {
			eGroup = servPersistencia.recuperarEntidad(group.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe)
			return;

		// Registramos primero los atributos que son objetos
		// Registrar los mensajes del grupo
		registrarSiNoExistenMensajes(group.getMensajesEnviados());

		// Registramos los contactos del grupo si no existen (Integrantes)
		registrarSiNoExistenContactos(group.getParticipantes());

		// Registramos a nuestro usuario administrador si no existe.
		registrarSiNoExisteAdmin(group.getAdmin());

		// Atributos propios del grupo
		eGroup.setNombre("grupo");
		eGroup.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", group.getNombre()),
				new Propiedad("mensajesRecibidos", obtenerCodigosMensajesRecibidos(group.getMensajesEnviados())),
				new Propiedad("integrantes", obtenerCodigosContactosIndividual(group.getParticipantes())),
				new Propiedad("admin", String.valueOf(group.getAdmin().getCodigo())))));

		// Registrar entidad usuario
		eGroup = servPersistencia.registrarEntidad(eGroup);

		// Identificador unico
		group.setCodigo(eGroup.getId());
		
		// Guardamos en el pool
		PoolDAO.getInstancia().addObjeto(group.getCodigo(), group);
	}

	@Override
	public void borrarGrupo(Group group) {
		// Borramos los elementos de las tablas que lo componen (En este caso borramos
		// los mensajes del grupo a eliminar)
		Entidad eGroup;
		AdaptadorMessageTDS adaptadorMensaje = AdaptadorMessageTDS.getInstancia();

		for (Message mensaje : group.getMensajesEnviados()) {
			adaptadorMensaje.borrarMensaje(mensaje);
		}

		eGroup = servPersistencia.recuperarEntidad(group.getCodigo());
		servPersistencia.borrarEntidad(eGroup);
		
		// Si está en el pool, borramos del pool
		if (PoolDAO.getInstancia().contiene(group.getCodigo()))
			PoolDAO.getInstancia().removeObjeto(group.getCodigo());
	}

	@Override
	public void modificarGrupo(Group group) {
		Entidad eGroup = servPersistencia.recuperarEntidad(group.getCodigo());

		// Se da el cambiazo a las propiedades del grupo
		servPersistencia.eliminarPropiedadEntidad(eGroup, "nombre");
		servPersistencia.anadirPropiedadEntidad(eGroup, "nombre", group.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eGroup, "mensajesRecibidos");
		servPersistencia.anadirPropiedadEntidad(eGroup, "mensajesRecibidos",
				obtenerCodigosMensajesRecibidos(group.getMensajesEnviados()));
		servPersistencia.eliminarPropiedadEntidad(eGroup, "integrantes");
		servPersistencia.anadirPropiedadEntidad(eGroup, "integrantes",
				obtenerCodigosContactosIndividual(group.getParticipantes()));
		servPersistencia.eliminarPropiedadEntidad(eGroup, "admin");
		servPersistencia.anadirPropiedadEntidad(eGroup, "admin", String.valueOf(group.getAdmin().getCodigo()));
	}

	@Override
	public Group recuperarGrupo(int codigo) {
		// Si la entidad esta en el pool la devuelve directamente
		if (PoolDAO.getInstancia().contiene(codigo))
			return (Group) PoolDAO.getInstancia().getObjeto(codigo);

		// Sino, la recupera de la base de datos
		// Recuperamos la entidad
		Entidad eGroup = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		String nombre = null;
		nombre = servPersistencia.recuperarPropiedadEntidad(eGroup, "nombre");

		Group group = new Group(nombre, new LinkedList<Message>(), new LinkedList<IndividualContact>(), null);
		group.setCodigo(codigo);

		// Metemos al grupo en el pool antes de llamar a otros adaptadores
		PoolDAO.getInstancia().addObjeto(codigo, group);
		
		// Mensajes que el grupo tiene
		List<Message> mensajes = obtenerMensajesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eGroup, "mensajesRecibidos"));
		for (Message m : mensajes)
			group.sendMessage(m);
				
		// Contactos que el grupo tiene
		List<IndividualContact> contactos = obtenerIntegrantesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eGroup, "integrantes"));
		for (IndividualContact c : contactos)
			group.addIntegrante(c);

		// Obtener admin
		group.cambiarAdmin(obtenerUsuarioDesdeCodigo(servPersistencia.recuperarPropiedadEntidad(eGroup, "admin")));

		// Devolvemos el objeto grupo
		return group;
	}

	@Override
	public List<Group> recuperarTodosGrupos() {
		List<Group> grupos = new LinkedList<>();
		List<Entidad> eGroups = servPersistencia.recuperarEntidades("grupo");

		for (Entidad eGroup : eGroups) {
			grupos.add(recuperarGrupo(eGroup.getId()));
		}
		
		return grupos;
	}

	
	// Funciones auxiliares.
	private void registrarSiNoExistenMensajes(List<Message> messages) {
		AdaptadorMessageTDS adaptadorMensajes = AdaptadorMessageTDS.getInstancia();
		messages.stream().forEach(m -> adaptadorMensajes.registrarMensaje(m));
	}

	private void registrarSiNoExistenContactos(List<IndividualContact> contacts) {
		AdaptadorIndividualContactTDS adaptadorContactos = AdaptadorIndividualContactTDS.getInstancia();
		contacts.stream().forEach(c -> adaptadorContactos.registrarContacto(c));
	}

	private void registrarSiNoExisteAdmin(User admin) {
		AdaptadorUserTDS adaptadorUsuarios = AdaptadorUserTDS.getInstancia();
		adaptadorUsuarios.registrarUsuario(admin);
	}

	private String obtenerCodigosContactosIndividual(List<IndividualContact> contactosIndividuales) {
		return contactosIndividuales.stream().map(c -> String.valueOf(c.getCodigo())).reduce("", (l, c) -> l + c + " ")
				.trim();
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
			mensajes.add(adaptadorMensajes.recuperarMensaje(Integer.valueOf((String) strTok.nextElement())));
		}
		return mensajes;
	}
	
	private List<IndividualContact> obtenerIntegrantesDesdeCodigos(String codigos) {
		List<IndividualContact> contactos = new LinkedList<>();
		StringTokenizer strTok = new StringTokenizer(codigos, " ");
		AdaptadorIndividualContactTDS adaptadorC = AdaptadorIndividualContactTDS.getInstancia();
		while (strTok.hasMoreTokens()) {
			contactos.add(adaptadorC.recuperarContacto(Integer.valueOf((String) strTok.nextElement())));
		}
		return contactos;
	}
	
	private User obtenerUsuarioDesdeCodigo(String codigo) {
		AdaptadorUserTDS adaptadorUsuarios = AdaptadorUserTDS.getInstancia();
		return adaptadorUsuarios.recuperarUsuario(Integer.valueOf(codigo));
	}
}
