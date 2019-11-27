package umu.tds.apps.AppChat;

import static org.junit.Assert.assertTrue;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

import umu.tds.apps.persistencia.AdaptadorUserTDS;

/**
 * Unit test for AppChat.
 */
public class TestPersistenciaUser {
	private User u;
	private AdaptadorUserTDS adapter;

	@Before
	public void setUp() {
		adapter = AdaptadorUserTDS.getInstancia();
		u = new User(new ImageIcon("/umu/tds/apps/resources/paper plane-white.png"), "Manolo", "Arbertoooo", "");
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
		registerUser();
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
}
