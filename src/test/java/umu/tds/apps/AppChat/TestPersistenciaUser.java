package umu.tds.apps.AppChat;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import javax.swing.ImageIcon;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import umu.tds.apps.persistencia.AdaptadorUserTDS;
import umu.tds.apps.persistencia.UserDAO;

/**
 * Unit test for AppChat.
 */
public class TestPersistenciaUser {
	private static User usuario;
	private static UserDAO adapter;

	@BeforeClass
	public static void setUp() {
		usuario = new User(new ImageIcon("/umu/tds/apps/resources/paper plane-white.png"), "Manuel", LocalDate.now(), 0,
				"mrblacknuel", "1234", true, new YoungDiscount(), null);
		usuario.setEstado(Optional.of(
				new Status(new ImageIcon("/umu/tds/apps/resources/paper plane-white.png"), "Buscadme en Telegram")));
		adapter = AdaptadorUserTDS.getInstancia();
	}

	/**
	 * Prueba que funcione el registro de un usuario
	 */
	@Test
	public void registerUser() {
		adapter.registrarUsuario(usuario);
		assertTrue(adapter.recuperarUsuario(usuario.getCodigo()).equals(usuario));
	}

	/**
	 * Prueba que funcione la eliminación de un usuario
	 */
	@Test(expected = NullPointerException.class)
	public void deleteUser() {
		registerUser();
		adapter.borrarUsuario(usuario);
		assertNull(adapter.recuperarUsuario(usuario.getCodigo()));
	}

	/**
	 * Prueba que funcione la modificación de un usuario
	 */
	@Test
	public void modifyUser() {
		registerUser();

		String newName = "Aitor Menta";
		usuario.setName(newName);
		adapter.modificarUsuario(usuario);

		assertTrue(adapter.recuperarUsuario(usuario.getCodigo()).getName().equals(newName));
	}

	/**
	 * Prueba que funcione la recuperación de todos los usuarios
	 */
	@Test
	public void getAllUsers() {
		registerUser();

		assertTrue(adapter.recuperarTodosUsuarios().contains(usuario));
	}
	
	@AfterClass
	public static void tearDown() {
		try {
			adapter.borrarUsuario(usuario);
		} catch (NullPointerException e) {
		}
	}
}
