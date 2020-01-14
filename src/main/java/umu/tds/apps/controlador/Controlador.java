package umu.tds.apps.controlador;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.ImageIcon;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler.LegendPosition;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.PdfWriter;

import componente.MessagesCharger;
import componente.MessagesEvent;
import componente.MessagesListener;
import modelo.MensajeWhatsApp;
import modelo.Plataforma;
import umu.tds.apps.AppChat.*;
import umu.tds.apps.persistencia.DAOException;
import umu.tds.apps.persistencia.FactoriaDAO;
import umu.tds.apps.persistencia.GroupDAO;
import umu.tds.apps.persistencia.IndividualContactDAO;
import umu.tds.apps.persistencia.MessageDAO;
import umu.tds.apps.persistencia.StatusDAO;
import umu.tds.apps.persistencia.UserDAO;
import umu.tds.apps.vistas.Theme;


public class Controlador implements MessagesListener {
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

	// Chat seleccionado
	private Contact chatActual;

	// Componente encargado de cargar los mensajes de Whatsapp
	private MessagesCharger cargador;

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
		if (nick.isEmpty() || password.isEmpty())
			return false;

		User cliente = catalogoUsuarios.getUsuario(nick);
		if (cliente == null)
			return false;

		// Si la password esta bien inicia sesion
		if (cliente.getPassword().equals(password)) {
			usuarioActual = cliente;

			// Inicializa el cargador de mensajes
			cargador = new MessagesCharger();
			cargador.addListener(this);
			return true;
		}
		return false;
	}

	// Registro el usuario. Devuelvo false si el nick ya está en uso
	public boolean crearCuenta(ImageIcon imagen, String nick, String password, String email, String name,
			int numTelefono, LocalDate fechaNacimiento) {
		User nuevoUsuario = new User(imagen, name, fechaNacimiento, numTelefono, nick, password);
		if (!catalogoUsuarios.contains(nuevoUsuario)) {
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

		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	// Añade una imagen al conjunto de imágenes del usuario
	public void addImagenUsuario(ImageIcon image) {
		int pos = usuarioActual.addProfilePhoto(image);

		// Guarda la imagen en el proyecto y con su nueva ruta
		Theme.saveImage(image, Theme.PROFILE_PHOTO_NAME, usuarioActual.getCodigo(), pos);

		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	// Borra una imagen del conjunto de imágenes del usuario
	public ImageIcon removeImagenUsuario(int pos) {
		ImageIcon img = usuarioActual.removeProfilePhoto(pos);

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

	/**
	 * Obtiene la lista de mensajes con ese contacto
	 * 
	 * @param contacto Contacto cuya conversación quiere obtenerse
	 * @return Lista con los mensajes de la conversación
	 */
	public List<Message> getMensajes(Contact contacto) {
		// Si la conversacion es conmigo mismo es suficiente con mostrar mis mensajes
		if (contacto instanceof IndividualContact && !((IndividualContact) contacto).isUser(usuarioActual)) {
			return Stream
					.concat(contacto.getMensajesEnviados().stream(),
							contacto.getMensajesRecibidos(Optional.of(usuarioActual)).stream())
					.sorted().collect(Collectors.toList());
		} else {
			// Dentro de los enviados estan contenidos todos los mensajes
			return contacto.getMensajesEnviados().stream().sorted().collect(Collectors.toList());
		}
	}

	// Devuelvo el último mensaje con ese contacto.
	public Message getUltimoMensaje(Contact contacto) {
		List<Message> mensajes = getMensajes(contacto);
		if (mensajes.isEmpty())
			return null;
		return mensajes.get(mensajes.size() - 1);
	}

	/**
	 * Crea el contacto especificado.
	 * 
	 * @param nombre      Nombre del contacto a guardar
	 * @param numTelefono Número de telefono del contacto a guardar
	 * @return El contacto creado. Devuelve null en caso de que ya existiese el
	 *         contacto o el contacto no se corresponda con un usuario real.
	 */
	public IndividualContact crearContacto(String nombre, int numTelefono) {
		// Si no tiene el contacto guardado lo guarda
		if (!usuarioActual.hasIndividualContact(nombre)) {
			Optional<User> usuarioOpt = catalogoUsuarios.getUsuarioNumTelf(numTelefono);

			if (usuarioOpt.isPresent()) {
				IndividualContact nuevoContacto = new IndividualContact(nombre, numTelefono, usuarioOpt.get());
				usuarioActual.addContacto(nuevoContacto);

				adaptadorContactoIndividual.registrarContacto(nuevoContacto);

				adaptadorUsuario.modificarUsuario(usuarioActual);
				return nuevoContacto;
			}
		}
		return null;
	}

	/**
	 * Crea un grupo para el que el usuario actual es el administrador.
	 * 
	 * @param nombre        Nombre del grupo
	 * @param participantes Contactos añadidos al grupo
	 * @return Grupo creado. En caso de que ya exista un grupo con ese nombre
	 *         devuelve null
	 */
	public Group crearGrupo(String nombre, List<IndividualContact> participantes) {
		if (usuarioActual.hasGroup(nombre)) {
			return null;
		}

		Group nuevoGrupo = new Group(nombre, new LinkedList<Message>(), participantes, usuarioActual);

		// Se añade el grupo al usuario actual y al resto de participantes
		usuarioActual.addGrupo(nuevoGrupo);
		usuarioActual.addGrupoAdmin(nuevoGrupo);
		participantes.stream().forEach(p -> p.addGrupo(nuevoGrupo));

		// Conexion con persistencia
		adaptadorGrupo.registrarGrupo(nuevoGrupo);

		adaptadorUsuario.modificarUsuario(usuarioActual);

		participantes.stream().forEach(p -> {
			User usuario = p.getUsuario();
			adaptadorUsuario.modificarUsuario(usuario);
		});

		return nuevoGrupo;
	}

	/**
	 * Modifica un grupo.
	 * 
	 * @param grupo         Contacto grupo que tiene el administrador
	 * @param nombre        Nombre que tomará el grupo tras la modificación
	 * @param participantes Lista con los nuevos integrantes del grupo
	 * @return
	 */
	public Group modificarGrupo(Group grupo, String nombre, List<IndividualContact> participantes) {
		grupo.setNombre(nombre);

		// Creo listas para las altas y las bajas
		List<IndividualContact> nuevos = new LinkedList<>();
		List<IndividualContact> mantenidos = new LinkedList<>();

		for (IndividualContact contacto : participantes) {
			if (grupo.hasParticipante(contacto.getUsuario())) {
				mantenidos.add(contacto);
			} else {
				nuevos.add(contacto);
			}
		}

		List<IndividualContact> eliminados = new LinkedList<>(grupo.getParticipantes());
		eliminados.removeAll(participantes);

		// Le modifico el grupo si el usuario ya existia. Si es nuevo, se lo añado
		mantenidos.stream().forEach(p -> p.modificarGrupo(grupo));
		nuevos.stream().forEach(p -> p.addGrupo(grupo));

		// Elimino el grupo de los participantes que ya no lo tienen
		eliminados.stream().forEach(p -> {
			p.eliminarGrupo(grupo);
			adaptadorUsuario.modificarUsuario(p.getUsuario());
		});

		// Se le cambia al grupo la lista de participantes
		grupo.setIntegrantes(participantes);

		// Conexion con persistencia
		adaptadorGrupo.modificarGrupo(grupo);

		// Actualiza los usuarios que no estaban antes en el grupo
		nuevos.stream().map(IndividualContact::getUsuario).forEach(u -> adaptadorUsuario.modificarUsuario(u));

		return grupo;
	}

	public List<Group> getGruposAdminUsuarioActual() {
		// Devuelvo una lista de mis grupos. Saco el código del usuario actual.
		return usuarioActual.getGruposAdmin();
	}

	public String getNombreContacto(User usuario) {
		return Controlador.getInstancia().getContactosUsuarioActual().stream()
				.filter(c -> c instanceof IndividualContact).map(c -> (IndividualContact) c)
				.filter(c -> c.getUsuario().getCodigo() == usuario.getCodigo()).collect(Collectors.toList()).get(0)
				.getNombre();
	}

	// Devuelvo una lista con los grupos en los que ese usuario y yo estamos.
	public Set<Group> getGruposEnComun(IndividualContact contacto) {
		return usuarioActual.getContactos().stream().filter(c -> c instanceof Group).map(c -> (Group) c)
				.filter(g -> g.hasParticipante(contacto.getUsuario()))
				.collect(Collectors.toSet());
	}

	public void hacerPremium() {
		usuarioActual.setPremium();

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
		List<Message> mensajes = Controlador.getInstancia().getContactosUsuarioActual().stream()
				.flatMap(c -> Controlador.getInstancia().getMensajes(c).stream()).collect(Collectors.toList());

		return mensajes.stream().filter(m -> emisor.equals("All") || m.getEmisor().getName().equals(emisor))
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
			Group grupo = (Group) c;
			grupo.getParticipantes().stream().forEach(p -> {
				p.eliminarGrupo(grupo);
				adaptadorUsuario.modificarUsuario(p.getUsuario());
			});
			adaptadorGrupo.borrarGrupo((Group) c);
		}

		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	public void addEstado(ImageIcon icono, String frase) {
		// Se guarda para poder recuperarla después
		Theme.saveImage(icono, Theme.STATUS_NAME, usuarioActual.getCodigo(), 0);

		Status estado = new Status(icono, frase);
		usuarioActual.setEstado(Optional.of(estado));
		adaptadorEstado.registrarEstado(estado);

		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	public void enviarMensaje(Contact contacto, String message) {
		Message mensaje = new Message(message, LocalDateTime.now(), usuarioActual, contacto);
		contacto.sendMessage(mensaje);

		adaptadorMensaje.registrarMensaje(mensaje);

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

		if (contacto instanceof IndividualContact) {
			adaptadorContactoIndividual.modificarContacto((IndividualContact) contacto);
		} else {
			adaptadorGrupo.modificarGrupo((Group) contacto);
		}
	}

	// Borra todos los mensajes enviados al contacto pasado como parametro
	public void deleteChat(Contact contacto) {
		// Borra mensajes recibidos y enviados
		List<Message> enviados = contacto.removeMensajesEnviados();
		List<Message> recibidos;
		if (contacto instanceof IndividualContact) {
			recibidos = ((IndividualContact) contacto).removeMensajesRecibidos(usuarioActual);
		} else {
			recibidos = ((Group) contacto).removeMensajesRecibidos();
		}

		// Actualiza la base de datos
		if (contacto instanceof IndividualContact) {
			adaptadorContactoIndividual.modificarContacto((IndividualContact) contacto);
			adaptadorContactoIndividual.modificarContacto(((IndividualContact) contacto).getContacto(usuarioActual));
		} else {
			adaptadorGrupo.modificarGrupo((Group) contacto);
		}
		enviados.stream().forEach(m -> adaptadorMensaje.borrarMensaje(m));
		recibidos.stream().forEach(m -> adaptadorMensaje.borrarMensaje(m));
	}

	public boolean crearPDFInfoContacto(String ruta) {
		List<IndividualContact> contactos = usuarioActual.getContactosIndividuales();

		try {
			// Creamos el documento PDF
			FileOutputStream archivo = new FileOutputStream(ruta);
			Document documento = new Document();
			PdfWriter.getInstance(documento, archivo);
			documento.open();

			// Escribimos en el documento PDF
			int i = 1;
			// Recorremos los contactos del usuario actual
			for (IndividualContact contacto : contactos) {
				documento.add(new Paragraph("Contact no. " + i));
				documento.add(Chunk.NEWLINE);

				// Imagen del contacto
				Image img = Image.getInstance(Theme.resizeIcon(contacto.getFoto(), 100).getImage(), null);
				documento.add(img);

				// Nombre del contacto
				Paragraph parrafoActual = new Paragraph();
				parrafoActual.setTabSettings(new TabSettings(30f));
				parrafoActual.add(Chunk.TABBING);
				parrafoActual.add(new Chunk("- Contact name: " + contacto.getNombre()));
				documento.add(parrafoActual);

				// Número de teléfono del contacto
				parrafoActual = new Paragraph();
				parrafoActual.setTabSettings(new TabSettings(30f));
				parrafoActual.add(Chunk.TABBING);
				parrafoActual.add(new Chunk("- Phone: " + contacto.getMovil()));
				documento.add(parrafoActual);

				// Mostramos la información de los grupos en común que tenemos con ese contacto
				parrafoActual = new Paragraph();
				parrafoActual.setTabSettings(new TabSettings(30f));
				parrafoActual.add(Chunk.TABBING);
				parrafoActual.add(new Chunk("- Groups shared:"));
				documento.add(parrafoActual);

				// Recorremos todos los grupos en común
				int z = 1;
				for (Group grupo : getGruposEnComun(contacto)) {
					// Nombre del grupo
					parrafoActual = new Paragraph();
					parrafoActual.setTabSettings(new TabSettings(60f));
					parrafoActual.add(Chunk.TABBING);
					parrafoActual.add(new Chunk("+ Group name: " + grupo.getNombre()));
					documento.add(parrafoActual);

					// Admin del grupo
					parrafoActual = new Paragraph();
					parrafoActual.setTabSettings(new TabSettings(60f));
					parrafoActual.add(Chunk.TABBING);
					parrafoActual.add(new Chunk("+ Group admin: "
							+ ((grupo.getAdmin().getCodigo() == usuarioActual.getCodigo()) ? usuarioActual.getName()
									: getNombreContacto(grupo.getAdmin()))));
					documento.add(parrafoActual);

					// Mostramos los participantes del grupo
					parrafoActual = new Paragraph();
					parrafoActual.setTabSettings(new TabSettings(60f));
					parrafoActual.add(Chunk.TABBING);
					parrafoActual.add(new Chunk("+ Participants:"));
					documento.add(parrafoActual);

					// Recorro todos los participantes
					for (IndividualContact participante : grupo.getParticipantes()) {
						// Información del participante
						parrafoActual = new Paragraph();
						parrafoActual.setTabSettings(new TabSettings(90f));
						parrafoActual.add(Chunk.TABBING);
						parrafoActual.add(new Chunk("- Name: " + participante.getNombre()));
						documento.add(parrafoActual);

						parrafoActual = new Paragraph();
						parrafoActual.setTabSettings(new TabSettings(90f));
						parrafoActual.add(Chunk.TABBING);
						parrafoActual.add(new Chunk("- Phone: " + participante.getMovil()));
						documento.add(parrafoActual);
					}

					if (z < grupo.getParticipantes().size())
						documento.add(Chunk.NEWLINE);
					z++;
				}

				// Estado de mi contacto, si tiene
				Optional<Status> estado = contacto.getEstado();
				if (estado.isPresent()) {
					parrafoActual = new Paragraph();
					parrafoActual.setTabSettings(new TabSettings(30f));
					parrafoActual.add(Chunk.TABBING);
					parrafoActual.add(new Chunk("- Status:"));
					documento.add(parrafoActual);

					Paragraph parrafoDescEstado = new Paragraph();
					parrafoDescEstado.setTabSettings(new TabSettings(60f));
					parrafoDescEstado.add(Chunk.TABBING);
					Image estadoImg = Image.getInstance(Theme.resizeIcon(estado.get().getImg(), 200).getImage(), null);
					parrafoDescEstado.add(estadoImg);
					documento.add(parrafoDescEstado);

					parrafoDescEstado = new Paragraph();
					parrafoDescEstado.setTabSettings(new TabSettings(60f));
					parrafoDescEstado.add(Chunk.TABBING);
					parrafoDescEstado.add(new Chunk("\"" + estado.get().getFrase() + "\""));
					documento.add(parrafoDescEstado);
				}

				if (i < contactos.size()) {
					documento.add(Chunk.NEWLINE);
					documento.add(Chunk.NEWLINE);
				}
				i++;
			}

			documento.close();
		} catch (DocumentException | IOException e1) {
			return false;
		}

		return true;
	}

	public CategoryChart crearHistograma(String titulo) {
		// Recupera para cada mes del año en curso los mensajes
		List<Long> mensajesEnviados = usuarioActual.getMensajesMes();
		List<Long> meses = new LinkedList<>();

		for (int i = 1; i <= mensajesEnviados.size(); i++) {
			meses.add((long) i);
		}

		// Create Chart
		CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title(titulo).xAxisTitle("Month")
				.yAxisTitle("Messages sent").build();

		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setHasAnnotations(true);

		// Series
		chart.addSeries("test", meses, mensajesEnviados);

		return chart;
	}

	public Chart<PieStyler, PieSeries> crearTarta(String titulo) {
		PieChart chart = new PieChartBuilder().width(800).height(600).title("Pie Diagram").build();
		chart.getStyler().setSeriesColors(Theme.PIECHART_COLORS);
		chart.getStyler().setLegendVisible(true);

		// Recupera los seis grupos mas frecuentados
		Map<String, Integer> gruposMasFrecuentados = usuarioActual.getGruposMasFrecuentados();
		for (String nomGrupo : gruposMasFrecuentados.keySet()) {
			chart.addSeries(nomGrupo, gruposMasFrecuentados.get(nomGrupo));
		}

		return chart;
	}

	// Devuelve los contactos del usuario actual que tienen un estado
	public List<IndividualContact> getContactosEstado() {
		return usuarioActual.getContactos().stream().filter(c -> c instanceof IndividualContact)
				.map(c -> (IndividualContact) c).filter(c -> c.getEstado().isPresent()).collect(Collectors.toList());
	}

	public Optional<Contact> getContacto(String nombre) {
		return getContactosUsuarioActual().stream().filter(c -> c.getNombre().equals(nombre)).findAny();
	}

	private Optional<User> getUser(String name) {
		return catalogoUsuarios.getUsuarios().stream().filter(u -> u.getName().equals(name)).findAny();
	}

	// Cuando se hace click en el pulsador guarda los mensajes cargados en BD
	@Override
	public void nuevosMensajes(MessagesEvent ev) {
		List<Message> mensajes = new LinkedList<>();

		for (MensajeWhatsApp message : ev.getNewMessages()) {
			Optional<User> autor;
			if (message.getAutor().equals(usuarioActual.getName())) {
				autor = Optional.of(usuarioActual);
			} else {
				autor = getUser(message.getAutor());
			}

			if (autor.isPresent())
				mensajes.add(new Message(message.getTexto(), message.getFecha(), autor.get(), chatActual));
			else
				return;
		}

		chatActual.addMensajes(mensajes);

		// Guardar los mensajes en BD
		mensajes.stream().forEach(m -> adaptadorMensaje.registrarMensaje(m));

		if (chatActual instanceof Group) {
			adaptadorGrupo.modificarGrupo((Group) chatActual);
		} else {
			adaptadorContactoIndividual.modificarContacto((IndividualContact) chatActual);
		}
	}

	// Guarda una lista de mensajes de Whatsapp
	public void cargarMensajes(String path, String opElegida) throws IOException {
		Plataforma plataforma;
		String dateFormat;

		if (opElegida.equals(MessagesCharger.IOS)) {
			plataforma = Plataforma.IOS;
			dateFormat = MessagesCharger.IOS_DATE;
		} else {
			plataforma = Plataforma.ANDROID;
			if (opElegida.equals(MessagesCharger.ANDROID_1)) {
				dateFormat = MessagesCharger.ANDROID_1_DATE;
			} else {
				dateFormat = MessagesCharger.ANDROID_2_DATE;
			}
		}

		cargador.setFichero(path, plataforma, dateFormat);
	}

	public void setChatActual(Contact contacto) {
		chatActual = contacto;
	}

	public boolean isAdmin(Group selectedValue) {
		return selectedValue.getAdmin().getCodigo() == usuarioActual.getCodigo();
	}
}
