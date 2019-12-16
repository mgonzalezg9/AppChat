package umu.tds.apps.persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.Discount;
import umu.tds.apps.AppChat.Group;
import umu.tds.apps.AppChat.IndividualContact;
import umu.tds.apps.AppChat.Status;
import umu.tds.apps.AppChat.User;

public class AdaptadorUserTDS implements UserDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUserTDS unicaInstancia = null;

	private AdaptadorUserTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public static AdaptadorUserTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorUserTDS();
		return unicaInstancia;
	}

	@Override
	public void registrarUsuario(User user) {
		Entidad eUsuario = new Entidad();
		boolean existe = true;

		// Si la entidad está registrada no la registra de nuevo
		try {
			eUsuario = servPersistencia.recuperarEntidad(user.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) {
			return;
		}

		// registrar primero los atributos que son objetos
		// registrar grupos administrados
		registrarSiNoExistenGrupos(user.getGruposAdmin());

		// registrar contactos del usuario
		registrarSiNoExistenContactosoGrupos(user.getContactos());

		// registrar estado del usuario
		registrarSiNoExisteEstado(user.getEstado());

		// Atributos propios del usuario
		eUsuario.setNombre("usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", user.getName()),
				new Propiedad("fechanacimiento", user.getFechaNacimiento().toString()),
				new Propiedad("telefono", String.valueOf(user.getNumTelefono())), new Propiedad("nick", user.getNick()),
				new Propiedad("password", user.getPassword()),
				new Propiedad("imagenes", obtenerPathImagenes(user.getProfilePhotos())),
				new Propiedad("premium", String.valueOf(user.isPremium())),
				new Propiedad("estado", obtenerCodigosEstado(user.getEstado())),
				new Propiedad("gruposadmin", obtenerCodigosGruposAdmin(user.getGruposAdmin())),
				new Propiedad("contactos", obtenerCodigosContactoIndividual(user.getContactos())),
				new Propiedad("grupos", obtenerCodigosGrupo(user.getContactos())),
				// Si tiene un descuento se guarda en BD
				new Propiedad("descuento",
						user.getDescuento().isPresent() ? user.getDescuento().get().getClass().getName() : ""),
				new Propiedad("saludo", user.getSaludo()))));

		// Registrar entidad usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);

		// Identificador unico
		user.setCodigo(eUsuario.getId());

		// Guardamos en el pool
		PoolDAO.getInstancia().addObjeto(user.getCodigo(), user);
	}

	@Override
	public void borrarUsuario(User user) {
		// Se borran los elementos de las tablas que lo componen (Contactos y Grupos
		// administrados)
		Entidad eUser = servPersistencia.recuperarEntidad(user.getCodigo());
		AdaptadorIndividualContactTDS adaptadorC = AdaptadorIndividualContactTDS.getInstancia();
		AdaptadorGroupTDS adaptadorG = AdaptadorGroupTDS.getInstancia();
		AdaptadorStatusTDS adaptadorS = AdaptadorStatusTDS.getInstancia();

		for (Contact chat : user.getContactos()) {
			if (chat instanceof IndividualContact) {
				adaptadorC.borrarContacto((IndividualContact) chat);
			} else
				adaptadorG.borrarGrupo((Group) chat);
		}

		for (Group grupoAdmin : user.getGruposAdmin()) {
			adaptadorG.borrarGrupo(grupoAdmin);
		}

		if (user.getEstado().isPresent()) {
			adaptadorS.borrarEstado(user.getEstado().get());
		}

		servPersistencia.borrarEntidad(eUser);

		// Si esta en el Pool tambien se borra del pool
		if (PoolDAO.getInstancia().contiene(user.getCodigo())) {
			PoolDAO.getInstancia().removeObjeto(user.getCodigo());
		}
	}

	@Override
	public void modificarUsuario(User user) {
		Entidad eUser = servPersistencia.recuperarEntidad(user.getCodigo());

		// Se da el cambiazo a las propiedades del usuario
		servPersistencia.eliminarPropiedadEntidad(eUser, "nombre");
		servPersistencia.anadirPropiedadEntidad(eUser, "nombre", user.getName());
		servPersistencia.eliminarPropiedadEntidad(eUser, "fechanacimiento");
		servPersistencia.anadirPropiedadEntidad(eUser, "fechanacimiento", user.getFechaNacimiento().toString());
		servPersistencia.eliminarPropiedadEntidad(eUser, "telefono");
		servPersistencia.anadirPropiedadEntidad(eUser, "telefono", String.valueOf(user.getNumTelefono()));
		servPersistencia.eliminarPropiedadEntidad(eUser, "nick");
		servPersistencia.anadirPropiedadEntidad(eUser, "nick", user.getNick());
		servPersistencia.eliminarPropiedadEntidad(eUser, "password");
		servPersistencia.anadirPropiedadEntidad(eUser, "password", user.getPassword());
		servPersistencia.eliminarPropiedadEntidad(eUser, "imagenes");
		servPersistencia.anadirPropiedadEntidad(eUser, "imagenes", obtenerPathImagenes(user.getProfilePhotos()));
		servPersistencia.eliminarPropiedadEntidad(eUser, "premium");
		servPersistencia.anadirPropiedadEntidad(eUser, "premium", String.valueOf(user.isPremium()));
		servPersistencia.eliminarPropiedadEntidad(eUser, "estado");
		servPersistencia.anadirPropiedadEntidad(eUser, "estado", obtenerCodigosEstado(user.getEstado()));
		servPersistencia.eliminarPropiedadEntidad(eUser, "gruposadmin");
		servPersistencia.anadirPropiedadEntidad(eUser, "gruposadmin", obtenerCodigosGruposAdmin(user.getGruposAdmin()));
		servPersistencia.eliminarPropiedadEntidad(eUser, "contactos");
		servPersistencia.anadirPropiedadEntidad(eUser, "contactos",
				obtenerCodigosContactoIndividual(user.getContactos()));
		servPersistencia.eliminarPropiedadEntidad(eUser, "grupos");
		servPersistencia.anadirPropiedadEntidad(eUser, "grupos", obtenerCodigosGrupo(user.getContactos()));
		servPersistencia.eliminarPropiedadEntidad(eUser, "descuento");
		servPersistencia.anadirPropiedadEntidad(eUser, "descuento",
				user.getDescuento().isPresent() ? user.getDescuento().get().getClass().getName() : "");
		servPersistencia.eliminarPropiedadEntidad(eUser, "saludo");
		servPersistencia.anadirPropiedadEntidad(eUser, "saludo", user.getSaludo());
	}

	@Override
	public User recuperarUsuario(int codigo) {
		// Si la entidad esta en el pool la devuelve directamente
		if (PoolDAO.getInstancia().contiene(codigo))
			return (User) PoolDAO.getInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		// recuperar entidad
		Entidad eUser = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		// fecha
		String nombre = servPersistencia.recuperarPropiedadEntidad(eUser, "nombre");
		LocalDate fechaNacimiento = LocalDate
				.parse(servPersistencia.recuperarPropiedadEntidad(eUser, "fechanacimiento"));
		int telefono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eUser, "telefono"));
		String nick = servPersistencia.recuperarPropiedadEntidad(eUser, "nick");
		String password = servPersistencia.recuperarPropiedadEntidad(eUser, "password");
		Boolean premium = Boolean.valueOf(servPersistencia.recuperarPropiedadEntidad(eUser, "premium"));
		String saludo = servPersistencia.recuperarPropiedadEntidad(eUser, "saludo");
		String descuento = servPersistencia.recuperarPropiedadEntidad(eUser, "descuento");
		Discount descuentoOpt = null;
		if (!descuento.isEmpty()) {
			try {
				descuentoOpt = (Discount) Class.forName(descuento).newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		String pathImages = servPersistencia.recuperarPropiedadEntidad(eUser, "imagenes");
		List<ImageIcon> imagenes;
		imagenes = obtenerImagenesDesdePath(pathImages);

		User usuario = new User(imagenes, nombre, fechaNacimiento, telefono, nick, password, premium, descuentoOpt, saludo);
		usuario.setCodigo(codigo);

		// Metemos el usuario en el pool antes de llamar a otros
		// adaptadores
		PoolDAO.getInstancia().addObjeto(codigo, usuario);

		// recuperar propiedades que son objetos llamando a adaptadores
		// cliente
		AdaptadorStatusTDS adaptadorEstado = AdaptadorStatusTDS.getInstancia();
		String codigoEstado = servPersistencia.recuperarPropiedadEntidad(eUser, "estado");
		Status estado = null;
		if (!codigoEstado.isEmpty()) {
			estado = adaptadorEstado.recuperarEstado(Integer.parseInt(codigoEstado));
		}
		usuario.setEstado(Optional.ofNullable(estado));

		// Grupos que el usuario administra
		List<Group> gruposAdmin = obtenerGruposDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUser, "gruposadmin"));

		for (Group g : gruposAdmin)
			usuario.addGrupoAdmin(g);

		// Contactos que el usuario tiene
		List<IndividualContact> contactos = obtenerContactosDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eUser, "contactos"));

		for (IndividualContact c : contactos)
			usuario.addContacto(c);

		// Grupos que el usuario tiene
		List<Group> grupos = obtenerGruposDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUser, "grupos"));

		for (Group g : grupos)
			usuario.addGrupo(g);

		// devolver el objeto usuario con todo
		return usuario;
	}

	@Override
	public List<User> recuperarTodosUsuarios() {
		List<User> usuarios = new LinkedList<>();
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");

		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}

	// -------------------Funciones auxiliares-----------------------------
	private void registrarSiNoExisteEstado(Optional<Status> estado) {
		if (estado.isPresent()) {
			AdaptadorStatusTDS.getInstancia().registrarEstado(estado.get());
		}
	}

	private void registrarSiNoExistenGrupos(List<Group> grupos) {
		AdaptadorGroupTDS adaptadorGA = AdaptadorGroupTDS.getInstancia();
		grupos.stream().forEach(g -> adaptadorGA.registrarGrupo(g));
	}

	private void registrarSiNoExistenContactosoGrupos(List<Contact> contactos) {
		AdaptadorIndividualContactTDS adaptadorContactos = AdaptadorIndividualContactTDS.getInstancia();
		AdaptadorGroupTDS adaptadorGrupos = AdaptadorGroupTDS.getInstancia();
		contactos.stream().forEach(c -> {
			if (c instanceof IndividualContact) {
				adaptadorContactos.registrarContacto((IndividualContact) c);
			} else {
				adaptadorGrupos.registrarGrupo((Group) c);
			}
		});
	}
	
	private List<ImageIcon> obtenerImagenesDesdePath(String pathImages) {
		List<ImageIcon> imagenes = new LinkedList<>();
		StringTokenizer strTok = new StringTokenizer(pathImages, "?");
		while (strTok.hasMoreTokens()) {
			String path = (String) strTok.nextElement();
			ImageIcon imagen = new ImageIcon(AdaptadorUserTDS.class.getResource(path));
			imagen.setDescription(path);
			imagenes.add(imagen);
		}
		return imagenes;
	}

	private String obtenerCodigosGruposAdmin(List<Group> gruposAdmin) {
		String grupos = "";
		for (Group grupo : gruposAdmin) {
			grupos += grupo.getCodigo() + " ";
		}
		return grupos.trim();
	}

	private String obtenerCodigosContactoIndividual(List<Contact> contactos) {
		return contactos.stream().filter(c -> c instanceof IndividualContact) // me quedo con los contactos
																				// individuales
				.map(c -> String.valueOf(c.getCodigo())).reduce("", (l, c) -> l + c + " ") // concateno todos los
																							// codigos
				.trim();
	}

	private String obtenerCodigosGrupo(List<Contact> grupos) {
		return grupos.stream().filter(c -> c instanceof Group) // me quedo con los contactos
																// individuales
				.map(c -> String.valueOf(c.getCodigo())).reduce("", (l, c) -> l + c + " ") // concateno todos los
																							// codigos
				.trim();
	}

	private String obtenerCodigosEstado(Optional<Status> estado) {
		// Si tiene estado obtenemos el codigo
		if (estado.isPresent()) {
			return String.valueOf(estado.get().getCodigo());
		} else {
			return "";
		}
	}

	private List<Group> obtenerGruposDesdeCodigos(String codigos) {
		List<Group> grupos = new LinkedList<>();
		StringTokenizer strTok = new StringTokenizer(codigos, " ");
		AdaptadorGroupTDS adaptadorG = AdaptadorGroupTDS.getInstancia();
		while (strTok.hasMoreTokens()) {
			grupos.add(adaptadorG.recuperarGrupo(Integer.valueOf((String) strTok.nextElement())));
		}
		return grupos;
	}

	private List<IndividualContact> obtenerContactosDesdeCodigos(String codigos) {
		List<IndividualContact> contactos = new LinkedList<>();
		StringTokenizer strTok = new StringTokenizer(codigos, " ");
		AdaptadorIndividualContactTDS adaptadorC = AdaptadorIndividualContactTDS.getInstancia();
		while (strTok.hasMoreTokens()) {
			contactos.add(adaptadorC.recuperarContacto(Integer.valueOf((String) strTok.nextElement())));
		}
		return contactos;
	}

	private String obtenerPathImagenes(List<ImageIcon> imagenes) {
		String paths = imagenes.stream().map(i -> String.valueOf(i.getDescription())).reduce("", (l, c) -> l + c + "?").trim();
		//System.out.println(paths.substring(0, paths.length() - 1));
		return paths.substring(0, paths.length() - 1);
	}

}
