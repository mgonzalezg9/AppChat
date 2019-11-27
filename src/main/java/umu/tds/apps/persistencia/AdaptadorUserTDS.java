package umu.tds.apps.persistencia;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.Group;
import umu.tds.apps.AppChat.IndividualContact;
import umu.tds.apps.AppChat.Status;
import umu.tds.apps.AppChat.User;
import umu.tds.apps.AppChat.UserRol;

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
		registrarSiNoExistenContactos(user.getContactos());

		// Atributos propios del usuario
		eUsuario.setNombre("usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", user.getName()),
				new Propiedad("fechanacimiento", user.getFechaNacimiento().toString()),
				new Propiedad("telefono", String.valueOf(user.getNumTelefono())), new Propiedad("nick", user.getNick()),
				new Propiedad("password", user.getPassword()), new Propiedad("imagen", user.getIcon().toString()),
				new Propiedad("premium", String.valueOf(user.isPremium())),
				new Propiedad("estado", obtenerCodigosEstado(user.getEstado().orElse(Status.NONE))),
				new Propiedad("gruposadmin", obtenerCodigosGruposAdmin(user.getGruposAdmin())),
				new Propiedad("contactos", obtenerCodigosContactoIndividual(user.getContactos())),
				new Propiedad("grupos", obtenerCodigosGrupo(user.getContactos())),
				new Propiedad("rolusuario", user.getRol().toString()))));

		// Registrar entidad usuario
		servPersistencia.registrarEntidad(eUsuario);

		// Identificador unico
		user.setCodigo(eUsuario.getId());
	}

	

	@Override
	public void borrarUsuario(User user) {
		// Se borran los elementos de las tablas que lo componen (Contactos y Grupos
		// administrados)
		Entidad eUser;
		AdaptadorIndividualContactTDS adaptadorC = AdaptadorIndividualContactTDS.getInstancia();
		AdaptadorGroupTDS adaptadorG = AdaptadorGroupTDS.getInstancia();

		for (Contact chat : user.getContactos()) {
			if (chat instanceof IndividualContact) {
				adaptadorC.borrarContacto((IndividualContact) chat);
			} else
				adaptadorG.borrarGrupo((Group) chat);
		}

		for (Group grupoAdmin : user.getGruposAdmin()) {
			adaptadorG.borrarGrupo(grupoAdmin);
		}

		eUser = servPersistencia.recuperarEntidad(user.getCodigo());
		servPersistencia.borrarEntidad(eUser);
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
		servPersistencia.eliminarPropiedadEntidad(eUser, "imagen");
		servPersistencia.anadirPropiedadEntidad(eUser, "imagen", user.getIcon().toString());
		servPersistencia.eliminarPropiedadEntidad(eUser, "premium");
		servPersistencia.anadirPropiedadEntidad(eUser, "premium", String.valueOf(user.isPremium()));
		servPersistencia.eliminarPropiedadEntidad(eUser, "estado");
		servPersistencia.anadirPropiedadEntidad(eUser, "estado", user.getEstado().toString());
		servPersistencia.eliminarPropiedadEntidad(eUser, "gruposadmin");
		servPersistencia.anadirPropiedadEntidad(eUser, "gruposadmin", obtenerCodigosGruposAdmin(user.getGruposAdmin()));
		servPersistencia.eliminarPropiedadEntidad(eUser, "contactos");
		servPersistencia.anadirPropiedadEntidad(eUser, "contactos",
				obtenerCodigosContactoIndividual(user.getContactos()));
		servPersistencia.eliminarPropiedadEntidad(eUser, "grupos");
		servPersistencia.anadirPropiedadEntidad(eUser, "grupos", obtenerCodigosGrupo(user.getContactos()));
		servPersistencia.eliminarPropiedadEntidad(eUser, "rolusuario");
		servPersistencia.anadirPropiedadEntidad(eUser, "rolusuario", user.getRol().toString());
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
		String nombre = null;
		LocalDate fechaNacimiento = null;
		int telefono = 0;
		String nick = null;
		String password = null;
		ImageIcon img = null;
		Boolean premium = false;
		UserRol rol = null;
		try {
			nombre = servPersistencia.recuperarPropiedadEntidad(eUser, "nombre");
			fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(eUser, "fechanacimiento");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		User usuario = null;
		usuario.setCodigo(codigo);

		// IMPORTANTE: meter la venta en el pool antes de llamar a otros
		// adaptadores
		PoolDAO.getInstancia().addObjeto(codigo, usuario);

		// recuperar propiedades que son objetos llamando a adaptadores
		// cliente
		AdaptadorClienteTDS adaptadorCliente = AdaptadorClienteTDS.getUnicaInstancia();
		int codigoCliente = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eUser, "cliente"));

		Cliente cliente = adaptadorCliente.recuperarCliente(codigoCliente);
		usuario.setCliente(cliente);
		// lineas de venta
		List<LineaVenta> lineasVenta = obtenerLineasVentaDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eUser, "lineasventa"));

		for (LineaVenta lv : lineasVenta)
			usuario.addLineaVenta(lv);

		// devolver el objeto venta
		return usuario;
	}

	@Override
	public List<User> recuperarTodosClientes() {
		// TODO Auto-generated method stub
		return null;
	}

	// -------------------Funciones auxiliares-----------------------------
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
	
	private String obtenerCodigosEstado(Status s) {
		// Si tiene estado obtenemos la 
		if (s.equals(Status.NONE)) {
			return "";
		} else {
			return String.valueOf(s.getCodigo());
		}
	}

	private void registrarSiNoExistenGrupos(List<Group> grupos) {
		AdaptadorGroupTDS adaptadorGA = AdaptadorGroupTDS.getInstancia();
		grupos.stream().forEach(g -> adaptadorGA.registrarGrupo(g));
	}

	private void registrarSiNoExistenContactos(List<Contact> contactos) {
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

}
