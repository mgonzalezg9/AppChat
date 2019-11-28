package umu.tds.apps.AppChat;

import static org.junit.Assert.assertTrue;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import org.junit.BeforeClass;
import org.junit.Test;

import umu.tds.apps.persistencia.AdaptadorUserTDS;
import umu.tds.apps.persistencia.UserDAO;

/**
 * Unit test for AppChat.
 */
public class TestPersistenciaUser {
	private User u;
	private UserDAO adapter;

	@BeforeClass
	public void setUp() {
		u = new User(new ImageIcon("/umu/tds/apps/resources/paper plane-white.png"), "Manolo", "Arbertoooo", "");
		adapter = AdaptadorUserTDS.getInstancia();
	}

	/**
	 * Prueba que funcione el registro de un usuario
	 */
	@Test
	public void registerUser() {
		adapter.registrarUsuario(u);
		assertTrue(adapter.recuperarUsuario(u.getCodigo()).equals(u));
	}

	/**
	 * Prueba que funcione la eliminación de un usuario
	 */
	@Test
	public void deleteUser() {
		adapter.registrarUsuario(u);
		adapter.borrarUsuario(u);
		assertTrue(adapter.recuperarUsuario(u.getCodigo()) == null);
	}

	/**
	 * Prueba que funcione la modificación de un usuario
	 */
	@Test
	public void modifyUser() {
		registerUser();

		String newName = "Aitor Menta";
		u.setName(newName);

		assertTrue(adapter.recuperarUsuario(u.getCodigo()).getName().equals(newName));
	}
	
	/**
	 * Prueba que funcione la recuperación de todos los usuarios
	 */
	@Test
	public void getAllUsers() {
		registerUser();

		assertTrue(adapter.recuperarTodosUsuarios().contains(u));
	}

}
