package umu.tds.apps.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.Group;
import umu.tds.apps.AppChat.IndividualContact;
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
		AdaptadorGroupTDS adaptadorGA = AdaptadorGroupTDS.getInstancia();
		user.getGruposAdmin().stream().forEach(g -> adaptadorGA.registrarGrupo(g));
		// registrar contactos del usuario
		AdaptadorIndividualContactTDS adaptadorContactos = AdaptadorIndividualContactTDS.getInstancia();
		AdaptadorGroupTDS adaptadorGrupos = AdaptadorGroupTDS.getInstancia();
		user.getContactos().stream().forEach(c -> {
			if (c instanceof IndividualContact) {
				adaptadorContactos.registrarContacto((IndividualContact) c);
			} else {
				adaptadorGrupos.registrarGrupo((Group) c);
			}
		});

		// Atributos no presentes en la BD
		eUsuario.setNombre("usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", user.getName()),
				new Propiedad("fechanacimiento", user.getFechaNacimiento().toString()),
				new Propiedad("telefono", String.valueOf(user.getNumTelefono())), new Propiedad("nick", user.getNick()),
				new Propiedad("password", user.getPassword()), new Propiedad("imagen", user.getIcon().toString()),
				new Propiedad("premium", String.valueOf(user.isPremium())),
				new Propiedad("estado", user.getEstado().toString()),
				new Propiedad("gruposadmin", obtenerCodigosGruposAdmin(user.getGruposAdmin())),
				new Propiedad("contactos", obtenerCodigosContactoIndividual(user.getContactos())),
				new Propiedad("grupos", obtenerCodigosGrupo(user.getContactos())),
				new Propiedad("rolusuario", user.getRol().toString())
				)));

		// Registrar entidad usuario
		servPersistencia.registrarEntidad(eUsuario);
		
		// Identificador unico
		user.setCodigo(eUsuario.getId());
	}

	@Override
	public void borrarUsuario(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarUsuario(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public User recuperarUsuario(int codigo) {
		// TODO Auto-generated method stub
		return null;
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

}
