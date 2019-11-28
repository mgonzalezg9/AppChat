package umu.tds.apps.AppChat;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import org.junit.BeforeClass;
import org.junit.Test;

import umu.tds.apps.persistencia.AdaptadorMessageTDS;
import umu.tds.apps.persistencia.MessageDAO;

public class TestPersistenciaMessage {
	private User u;
	private Message m;
	private MessageDAO adapter;

	@BeforeClass
	public void setUp() {
		u = new User(new ImageIcon("/umu/tds/apps/resources/paper plane-white.png"), "Manolo", "Arbertoooo", "");
		m = new Message("Pasar los test", LocalDate.now(), u, new IndividualContact("Alberto", new LinkedList<>(), 0, u));
		adapter = AdaptadorMessageTDS.getInstancia();
	}

	/**
	 * Prueba que funcione el registro de un mensaje
	 */
	@Test
	public void registerMessage() {
		adapter.registrarMensaje(m);
		assertTrue(adapter.recuperarMensaje(u.getCodigo()).equals(m));
	}

	/**
	 * Prueba que funcione la eliminación de un mensaje
	 */
	@Test
	public void deleteMessage() {
		adapter.registrarMensaje(m);
		adapter.borrarMensaje(m);
		assertTrue(adapter.recuperarMensaje(u.getCodigo()) == null);
	}

	/**
	 * Prueba que funcione la modificación de un mensaje
	 */
	@Test
	public void modifyMessage() {
		registerMessage();

		String text = "Si esta nublado llueve";
		m.setTexto(text);

		assertTrue(adapter.recuperarMensaje(u.getCodigo()).getTexto().equals(text));
	}

	/**
	 * Prueba que funcione la recuperación de todos los usuarios
	 */
	@Test
	public void getAllMessages() {
		registerMessage();

		assertTrue(adapter.recuperarTodosMensajes().contains(m));
	}

}
