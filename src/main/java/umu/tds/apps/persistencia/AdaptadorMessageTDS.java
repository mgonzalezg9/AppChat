package umu.tds.apps.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.Group;
import umu.tds.apps.AppChat.IndividualContact;
import umu.tds.apps.AppChat.Message;
import umu.tds.apps.AppChat.User;

public class AdaptadorMessageTDS implements MessageDAO {
	private static AdaptadorMessageTDS unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;

	private AdaptadorMessageTDS() {
	}

	public static AdaptadorMessageTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorMessageTDS();
		return unicaInstancia;
	}

	@Override
	public void registrarMensaje(Message mensaje) {
		Entidad eMensaje = new Entidad();
		boolean existe = true;

		// Si la entidad está registrada no la registra de nuevo
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) {
			return;
		}

		// registrar primero los atributos que son objetos
		// registrar usuario emisor del mensaje
		registrarSiNoExisteUsuario(mensaje.getEmisor());

		// registrar contacto receptor del mensaje
		registrarSiNoExistenContactosoGrupos(mensaje.getReceptor());

		// Atributos propios del usuario
		eMensaje.setNombre("mensaje");

		// Se guarda el grupo receptor o el contacto, segun convenga
		boolean grupo = false;
		if (mensaje.getReceptor() instanceof Group) {
			grupo = true;
		}

		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("texto", mensaje.getTexto()),
				new Propiedad("hora", mensaje.getHora().toString()),
				new Propiedad("emoticono", String.valueOf(mensaje.getEmoticono())),
				new Propiedad("receptor", String.valueOf(mensaje.getReceptor().getCodigo())),
				new Propiedad("togrupo", String.valueOf(grupo)),
				new Propiedad("emisor", String.valueOf(mensaje.getEmisor().getCodigo())))));

		// Registrar entidad usuario
		servPersistencia.registrarEntidad(eMensaje);

		// Identificador unico
		mensaje.setCodigo(eMensaje.getId());
	}

	@Override
	public void borrarMensaje(Message mensaje) {
		// Se borran los elementos de las tablas que lo componen (Usuario y Contacto)
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		servPersistencia.borrarEntidad(eMensaje);
	}

	@Override
	public void modificarMensaje(Message mensaje) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());

		// Se da el cambiazo a las propiedades del usuario
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "texto");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "texto", mensaje.getTexto());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "hora");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "hora", mensaje.getHora().toString());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "emoticono");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "emoticono", String.valueOf(mensaje.getEmoticono()));
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "receptor");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "receptor", String.valueOf(mensaje.getReceptor().getCodigo()));
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "emisor");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "emisor", String.valueOf(mensaje.getEmisor().getCodigo()));
		
		boolean grupo = false;
		if (mensaje.getReceptor() instanceof Group) {
			grupo = true;
		}
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "togrupo");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "togrupo", String.valueOf(grupo));
	}

	@Override
	public Message recuperarMensaje(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> recuperarTodosMensajes() {
		List<Message> mensajes = new LinkedList<>();
		List<Entidad> eMensajes = servPersistencia.recuperarEntidades("mensaje");

		for (Entidad eMensaje : eMensajes) {
			mensajes.add(recuperarMensaje(eMensaje.getId()));
		}
		return mensajes;
	}

	// -------------------Funciones auxiliares-----------------------------
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
	
	private void registrarSiNoExistenContactosoGrupos(Contact contacto) {
		LinkedList<Contact> contactos = new LinkedList<>();
		contactos.add(contacto);
		registrarSiNoExistenContactosoGrupos(contactos);
	}

	private void registrarSiNoExisteUsuario(User emisor) {
		AdaptadorUserTDS.getInstancia().registrarUsuario(emisor);
	}

}
