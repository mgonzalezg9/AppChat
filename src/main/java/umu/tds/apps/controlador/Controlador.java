package umu.tds.apps.controlador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import umu.tds.apps.AppChat.*;
import umu.tds.apps.persistencia.DAOException;
import umu.tds.apps.persistencia.FactoriaDAO;
import umu.tds.apps.persistencia.GroupDAO;
import umu.tds.apps.persistencia.IndividualContactDAO;
import umu.tds.apps.persistencia.MessageDAO;
import umu.tds.apps.persistencia.StatusDAO;
import umu.tds.apps.persistencia.UserDAO;
import umu.tds.apps.vistas.Theme;

public class Controlador {
	// Instancia del controlador.
	private static Controlador unicaInstancia = null;

	// Adaptadores
	private GroupDAO adaptadorGrupo;
	private IndividualContactDAO adaptadorContactoIndividual;
	private MessageDAO adaptadorMensaje;
	private UserDAO adaptadorUsuario;
	private StatusDAO adaptadorEstado;

	// Catálogos
	private UsersCatalogue catalogoUsuarios;

	// Nuestro usuario.
	private User usuarioActual;

	private Controlador() {
		inicializarAdaptadores(); // debe ser la primera linea para evitar error de sincronización
		inicializarCatalogos();
	}

	// Aplicamos el patrón Singleton.
	// Consiguiendo de esta forma que exista una única instancia de la clase
	// controlador que es accesible globalmente.
	public static Controlador getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}

	// Inicializamos los adaptadores
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}

		adaptadorGrupo = factoria.getGrupoDAO();
		adaptadorContactoIndividual = factoria.getContactoIndividualDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
		adaptadorUsuario = factoria.getUserDAO();
		adaptadorEstado = factoria.getEstadoDAO();
	}

	// Inicializamos los catálogos
	private void inicializarCatalogos() {
		catalogoUsuarios = UsersCatalogue.getUnicaInstancia();
	}

	public boolean iniciarSesion(String nick, String password) {
		if (nick.isEmpty() || password.isEmpty()) {
			return false;
		}

		User cliente = catalogoUsuarios.getUsuario(nick);

		if (cliente == null)
			return false;

		// Si la password esta bien inicia sesion
		if (cliente.getPassword().equals(password)) {
			usuarioActual = cliente;
			return true;
		}
		return false;
	}

	// Registro el usuario. Devuelvo false si el nick ya está en uso
	public boolean crearCuenta(ImageIcon imagen, String nick, String password, String email, String name,
			int numTelefono, LocalDate fechaNacimiento) {
		// TODO Comprobar que no haya un usuario con ese numero de telefono
		User u = catalogoUsuarios.getUsuario(nick);
		if (u == null) {
			User nuevoUsuario = new User(Arrays.asList(imagen), name, fechaNacimiento, numTelefono, nick, password,
					false, null, null);

			// Guarda la imagen en el proyecto
			Theme.saveImage(imagen, Theme.PROFILE_PHOTO_NAME, nuevoUsuario.getCodigo(), 1);

			// Conexion con la persistencia
			catalogoUsuarios.addUsuario(nuevoUsuario);
			adaptadorUsuario.registrarUsuario(nuevoUsuario);

			return iniciarSesion(nick, password);
		}
		return false;
	}

	public User getUsuarioActual() {
		return usuarioActual;
	}

	public void setSaludoUsuario(String saludo) {
		usuarioActual.setSaludo(saludo);
		catalogoUsuarios.addUsuario(usuarioActual);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	// Añade una imagen al conjunto de imágenes del usuario
	public void addImagenUsuario(ImageIcon image) {
		int pos = usuarioActual.addProfilePhoto(image);

		// Guarda la imagen en el proyecto y con su nueva ruta
		Theme.saveImage(image, Theme.PROFILE_PHOTO_NAME, usuarioActual.getCodigo(), pos);

		catalogoUsuarios.addUsuario(usuarioActual);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	// Borra una imagen del conjunto de imágenes del usuario
	public ImageIcon removeImagenUsuario(int pos) {
		ImageIcon img = usuarioActual.removeProfilePhoto(pos);
		catalogoUsuarios.addUsuario(usuarioActual);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		return img;
	}

	// Devuelvo mi lista de contactos.
	public List<Contact> getContactosUsuarioActual() {
		if (usuarioActual == null)
			return new LinkedList<Contact>();
		User u = catalogoUsuarios.getUsuario(usuarioActual.getCodigo());
		return u.getContactos();
	}
	
	// Devuelvo mi lista de contactos individuales.
		public List<IndividualContact> getContactosIndividualesUsuarioActual() {
		return getContactosUsuarioActual().stream().filter(c -> c instanceof IndividualContact).map(c -> (IndividualContact) c).collect(Collectors.toList());
	}
	
	// Devuelvo la lista de mis grupos.
	public List<Group> getGruposUsuarioActual() {
		return getContactosUsuarioActual().stream().filter(c -> c instanceof Group).map(c -> (Group) c).collect(Collectors.toList());
	}

	// Devuelvo mi lista de mensajes con ese contacto
	public List<Message> getMensajes(Contact contacto) {
		if (contacto instanceof IndividualContact) {
			return Stream
					.concat(contacto.getMensajesEnviados().stream(),
							((IndividualContact) contacto).getMensajesRecibidos(usuarioActual).stream())
					.sorted().collect(Collectors.toList());
		} else {
			return ((Group) contacto).getMensajesGrupo();
		}
	}

	// Devuelvo el último mensaje con ese contacto.
	public Message getUltimoMensaje(Contact contacto) {
		List<Message> mensajes = getMensajes(contacto);
		if (mensajes.isEmpty())
			return null;
		return mensajes.get(mensajes.size() - 1);
	}

	// Creo el contacto. Da error si tiene como telefono de otro ya creado.
	public IndividualContact crearContacto(String nombre, int numTelefono) {
		boolean existeContacto = false;
		if (!usuarioActual.getContactos().isEmpty()) {
			existeContacto = usuarioActual.getContactos().stream().filter(c -> c instanceof IndividualContact)
					.map(c -> (IndividualContact) c).anyMatch(c -> c.getMovil() == numTelefono);
		}

		if (!existeContacto) {
			User usuario = catalogoUsuarios.getUsuarios().stream().filter(u -> u.getNumTelefono() == numTelefono)
					.collect(Collectors.toList()).get(0);
			IndividualContact nuevoContacto = new IndividualContact(nombre, numTelefono, usuario);
			usuarioActual.addContacto(nuevoContacto);
			adaptadorContactoIndividual.registrarContacto(nuevoContacto);
			catalogoUsuarios.addUsuario(usuarioActual);
			adaptadorUsuario.modificarUsuario(usuarioActual);
			return nuevoContacto;
		}
		return null;
	}

	// Creo el grupo.
	public Group crearGrupo(String nombre, List<IndividualContact> participantes) {
		Group nuevoGrupo = new Group(nombre, new LinkedList<Message>(), participantes, usuarioActual);

		// Se añade el grupo al usuario actual y al resto de participantes
		usuarioActual.addGrupo(nuevoGrupo);
		usuarioActual.addGrupoAdmin(nuevoGrupo);
		participantes.stream().forEach(p -> p.addGrupo(nuevoGrupo));

		// Conexion con persistencia
		adaptadorGrupo.registrarGrupo(nuevoGrupo);
		catalogoUsuarios.addUsuario(usuarioActual);
		adaptadorUsuario.modificarUsuario(usuarioActual);

		participantes.stream().forEach(p -> {
			User usuario = p.getUsuario();
			catalogoUsuarios.addUsuario(usuario);
			adaptadorUsuario.modificarUsuario(usuario);
		});
		
		return nuevoGrupo;
	}
	
	// Modifico el grupo.
	public Group modificarGrupo(Group grupo, String nombre, List<IndividualContact> participantes) {
		grupo.setNombre(nombre);
		
		// Creo una lista con los nuevos participantes, los participantes eliminados y los que mantengo
		List<IndividualContact> nuevos = new LinkedList<>();
		List<IndividualContact> eliminados = new LinkedList<>();
		List<IndividualContact> mantenidos = new LinkedList<>();
		for (IndividualContact participante : grupo.getContactos()) {
			if (participantes.stream().anyMatch(p -> p.getCodigo() == participante.getCodigo())) {
				mantenidos.add(participante);
			} else {
				eliminados.add(participante);
			}
		}
		
		for (IndividualContact participanteNuevo : participantes)
			if (!grupo.getContactos().stream().anyMatch(p -> p.getCodigo() == participanteNuevo.getCodigo()))
				nuevos.add(participanteNuevo);
		
		grupo.modificarIntegrantes(participantes);

		// Se añade el grupo al usuario actual y al resto de participantes
		usuarioActual.modificarGrupo(grupo);
		usuarioActual.modificarGrupoAdmin(grupo);
		// Le modifico el grupo si el usuario ya existia, si es nuevo, se lo añado
		mantenidos.stream().forEach(p -> p.modificarGrupo(grupo));
		nuevos.stream().forEach(p -> p.addGrupo(grupo));
		
		//TODO elimino el grupo de los participantes que ya no lo tienen
		eliminados.stream().forEach(p -> {
			p.eliminarGrupo(grupo);
			adaptadorUsuario.modificarUsuario(p.getUsuario());
		});

		// Conexion con persistencia
		adaptadorGrupo.modificarGrupo(grupo);
		catalogoUsuarios.addUsuario(usuarioActual);
		adaptadorUsuario.modificarUsuario(usuarioActual);

		participantes.stream().forEach(p -> {
			User usuario = p.getUsuario();
			catalogoUsuarios.addUsuario(usuario);
			adaptadorUsuario.modificarUsuario(usuario);
		});
		
		return grupo;
	}

	public List<Group> getGruposAdminUsuarioActual() {
		// Devuelvo una lista de mis grupos. Saco el código del usuario actual.
		return usuarioActual.getGruposAdmin();
	}
	
	public String getNombreContactoEmisor (User usuario) {
		return Controlador.getInstancia().getContactosUsuarioActual().stream()
		.filter(c -> c instanceof IndividualContact)
		.map(c -> (IndividualContact) c)
		.filter(c -> c.getUsuario().getCodigo() == usuario.getCodigo())
		.collect(Collectors.toList()).get(0).getNombre();
	}

	// Devuelvo una lista con los nombres de los grupos en los que se usuario y yo
	// estamos.
	public List<String> getNombresGrupo(IndividualContact contacto) {
		return usuarioActual.getContactos().stream().filter(c -> c instanceof Group).map(c -> (Group) c)
				.filter(g -> g.getContactos().stream().anyMatch(c -> c.getMovil() == contacto.getMovil()) || g.getAdmin().getNumTelefono() == contacto.getMovil())
				.map(g -> g.getNombre()).collect(Collectors.toList());
	}

	public void hacerPremium() {
		usuarioActual.setPremium();
		catalogoUsuarios.addUsuario(usuarioActual);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	
	public double getPrecio() {
		return usuarioActual.getPrecio();
	}

	public void cerrarSesion() {
		usuarioActual = null;
	}

	public List<Message> buscarMensajes(String emisor, LocalDateTime fechaInicio, LocalDateTime fechaFin, String text) {
		// Recupero los mensajes que he enviado
		/*List<Message> enviados = usuarioActual.getContactos().stream().flatMap(c -> c.getMensajesEnviados().stream())
				.collect(Collectors.toList());

		// Recupero los mensajes que he recibido
		List<Message> recibidos = usuarioActual.getContactos().stream().flatMap(c -> {
			List<Message> m;
			if (c instanceof IndividualContact)
				m = ((IndividualContact) c).getMensajesRecibidos(usuarioActual);
			else
				m = ((Group) c).getMensajesEnviados();
			return m.stream();
		}).collect(Collectors.toList());*/
		
		List<Message> mensajes = Controlador.getInstancia()
				.getContactosUsuarioActual().stream()
				.flatMap(c -> Controlador.getInstancia().getMensajes(c).stream())
				.collect(Collectors.toList());

		return mensajes.stream()
				.filter(m -> emisor.equals("All") || m.getEmisor().getName().equals(emisor))
				.filter(m -> fechaInicio == null || m.getHora().isBefore(fechaInicio))
				.filter(m -> fechaFin == null || m.getHora().isAfter(fechaFin))
				.filter(m -> text == "" || m.getTexto().contains(text)).collect(Collectors.toList());
	}

	// Borramos el contacto
	public void deleteContact(Contact c) {
		usuarioActual.removeContact(c);
		if (c instanceof IndividualContact) {
			adaptadorContactoIndividual.borrarContacto((IndividualContact) c);
		} else {
			adaptadorGrupo.borrarGrupo((Group) c);
		}
		catalogoUsuarios.addUsuario(usuarioActual);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	public void addEstado(ImageIcon icono, String frase) {
		// Se guarda para poder recuperarla después
		Theme.saveImage(icono, Theme.STATUS_NAME, usuarioActual.getCodigo(), 0);
		
		Status estado = new Status(icono, frase);
		usuarioActual.setEstado(Optional.of(estado));
		adaptadorEstado.registrarEstado(estado);
		catalogoUsuarios.addUsuario(usuarioActual);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	public List<Status> getEstados(List<Contact> contactos) { // ALFONSITO
		return null;
	}

	public void enviarMensaje(Contact contacto, String message) {
		Message mensaje = new Message(message, LocalDateTime.now(), usuarioActual, contacto);
		contacto.sendMessage(mensaje);
		adaptadorMensaje.registrarMensaje(mensaje);
		catalogoUsuarios.addUsuario(usuarioActual);
		if (contacto instanceof IndividualContact) {
			adaptadorContactoIndividual.modificarContacto((IndividualContact) contacto);
		} else {
			adaptadorGrupo.modificarGrupo((Group) contacto);
		}
	}

	public void enviarMensaje(Contact contacto, int emoji) {
		Message mensaje = new Message(emoji, LocalDateTime.now(), usuarioActual, contacto);
		contacto.sendMessage(mensaje);
		adaptadorMensaje.registrarMensaje(mensaje);
		catalogoUsuarios.addUsuario(usuarioActual);
		if (contacto instanceof IndividualContact) {
			adaptadorContactoIndividual.modificarContacto((IndividualContact) contacto);
		} else {
			adaptadorGrupo.modificarGrupo((Group) contacto);
		}
	}

	// Devuelve los contactos del usuario actual que tienen un estado
	public List<IndividualContact> getContactosEstado() {
		return usuarioActual.getContactos().stream().filter(c -> c instanceof IndividualContact)
				.map(c -> (IndividualContact) c)
				.filter(c -> c.getEstado().isPresent()).collect(Collectors.toList());
	}

	public Optional<Contact> getContacto(String nombre) {
		return getContactosUsuarioActual().stream().filter(c -> c.getNombre().equals(nombre)).findAny();
	}
}
