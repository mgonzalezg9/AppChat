package umu.tds.apps.persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.Discount;
import umu.tds.apps.AppChat.Group;
import umu.tds.apps.AppChat.IndividualContact;
import umu.tds.apps.AppChat.Message;
import umu.tds.apps.AppChat.Normal;
import umu.tds.apps.AppChat.Premium;
import umu.tds.apps.AppChat.Status;
import umu.tds.apps.AppChat.User;
import umu.tds.apps.AppChat.UserRol;

public class AdaptadorMessageTDS implements MessageDAO {
	private static AdaptadorMessageTDS unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;

	private AdaptadorMessageTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public static AdaptadorMessageTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorMessageTDS();
		return unicaInstancia;
	}

	@Override
	public void registrarMensaje(Message message) {
		Entidad eMensaje = new Entidad();
		boolean existe = true;

		// Si la entidad está registrada no la registra de nuevo
		try {
			eMensaje = servPersistencia.recuperarEntidad(message.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) {
			return;
		}

		// registrar primero los atributos que son objetos
		// registrar usuario emisor del mensaje
		registrarSiNoExisteUsuario(message.getEmisor());

		// registrar contacto receptor del mensaje
		registrarSiNoExistenContactosoGrupos(message.getReceptor());

		// Atributos propios del usuario
		eMensaje.setNombre("mensaje");

		// Se guarda el grupo receptor o el contacto, segun convenga
		boolean grupo = false;
		if (message.getReceptor() instanceof Group) {
			grupo = true;
		}

		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("texto", message.getTexto()),
				new Propiedad("hora", message.getHora().toString()),
				new Propiedad("emoticono", String.valueOf(message.getEmoticono())),
				new Propiedad("receptor", String.valueOf(message.getReceptor().getCodigo())),
				new Propiedad("togroup", String.valueOf(grupo)),
				new Propiedad("emisor", String.valueOf(message.getEmisor().getCodigo())))));

		// Registrar entidad mensaje
		eMensaje = servPersistencia.registrarEntidad(eMensaje);

		// Identificador unico
		message.setCodigo(eMensaje.getId());
		
		// Guardamos en el pool
		PoolDAO.getInstancia().addObjeto(message.getCodigo(), message);
	}

	@Override
	public void borrarMensaje(Message mensaje) {
		// Se borran los elementos de las tablas que lo componen (Usuario y Contacto)
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		servPersistencia.borrarEntidad(eMensaje);
		
		// Si está en el pool, borramos del pool
		if (PoolDAO.getInstancia().contiene(mensaje.getCodigo()))
			PoolDAO.getInstancia().removeObjeto(mensaje.getCodigo());
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
		servPersistencia.anadirPropiedadEntidad(eMensaje, "receptor",
				String.valueOf(mensaje.getReceptor().getCodigo()));
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "emisor");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "emisor", String.valueOf(mensaje.getEmisor().getCodigo()));

		boolean grupo = false;
		if (mensaje.getReceptor() instanceof Group) {
			grupo = true;
		}
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "togroup");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "togroup", String.valueOf(grupo));
	}

	@Override
	public Message recuperarMensaje(int codigo) {
		// Si la entidad esta en el pool la devuelve directamente
		if (PoolDAO.getInstancia().contiene(codigo))
			return (Message) PoolDAO.getInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		// recuperar entidad
		Entidad eMensaje = servPersistencia.recuperarEntidad(codigo);
		
		// recuperar propiedades que no son objetos
		// fecha
		String texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
		LocalDate hora = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, "hora"));
		int emoticono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emoticono"));
		Contact receptor = null;
		Boolean toGroup = Boolean.valueOf(servPersistencia.recuperarPropiedadEntidad(eMensaje, "togroup"));
		User emisor = null;

		Message mensaje = new Message(texto, emoticono, hora);
		mensaje.setCodigo(codigo);

		// Metemos el usuario en el pool antes de llamar a otros
		// adaptadores
		PoolDAO.getInstancia().addObjeto(codigo, mensaje);

		// recuperar propiedades que son objetos llamando a adaptadores
		// mensaje
		// Usuario emisor
		AdaptadorUserTDS adaptadorU = AdaptadorUserTDS.getInstancia();
		int codigoUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emisor"));
		emisor = adaptadorU.recuperarUsuario(codigoUsuario);
		mensaje.setEmisor(emisor);

		// Contacto o grupo receptor
		int codigoContacto = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "receptor"));
		if (toGroup) {
			AdaptadorGroupTDS adaptadorG = AdaptadorGroupTDS.getInstancia();
			receptor = adaptadorG.recuperarGrupo(codigoContacto);
		} else {
			AdaptadorIndividualContactTDS adaptadorC = AdaptadorIndividualContactTDS.getInstancia();
			receptor = adaptadorC.recuperarContacto(codigoContacto);
		}
		mensaje.setReceptor(receptor);

		// devolver el objeto mensaje con todo
		return mensaje;
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
