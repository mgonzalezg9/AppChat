package umu.tds.apps.AppChat;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import umu.tds.apps.persistencia.AdaptadorMessageTDS;
import umu.tds.apps.persistencia.MessageDAO;

public class TestPersistenciaMessage {
	private static User usuario;
	private static Message mensaje;
	private static MessageDAO adapter;

	@BeforeClass
	public static void setUp() {
		usuario = new User(new ImageIcon("/umu/tds/apps/resources/paper plane-white.png"), "Manuel", LocalDate.now(), 0,
				"mrblacknuel", "1234", true, new YoungDiscount(), null);
		mensaje = new Message("Pasar los test", LocalDateTime.now(), usuario,
				new IndividualContact("Alberto", new LinkedList<>(), 0, usuario));
		adapter = AdaptadorMessageTDS.getInstancia();
	}

	/**
	 * Prueba que funcione el registro de un mensaje
	 */
	@Test
	public void registerMessage() {
		adapter.registrarMensaje(mensaje);
		assertTrue(adapter.recuperarMensaje(mensaje.getCodigo()).equals(mensaje));
	}

	/**
	 * Prueba que funcione la eliminación de un mensaje
	 */
	@Test(expected = NullPointerException.class)
	public void deleteMessage() {
		registerMessage();
		adapter.borrarMensaje(mensaje);
		assertNull(adapter.recuperarMensaje(mensaje.getCodigo()));
	}

	/**
	 * Prueba que funcione la modificación de un mensaje
	 */
	@Test
	public void modifyMessage() {
		registerMessage();

		String text = "Si esta nublado llueve";
		mensaje.setTexto(text);
		adapter.modificarMensaje(mensaje);

		assertTrue(adapter.recuperarMensaje(mensaje.getCodigo()).getTexto().equals(text));
	}

	/**
	 * Prueba que funcione la recuperación de todos los usuarios
	 */
	@Test
	public void getAllMessages() {
		registerMessage();

		assertTrue(adapter.recuperarTodosMensajes().contains(mensaje));
	}

	@AfterClass
	public static void tearDown() {
		try {
			adapter.borrarMensaje(mensaje);
		} catch (NullPointerException e) {
		}
	}
}
