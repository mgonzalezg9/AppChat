package umu.tds.apps.AppChat;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import umu.tds.apps.persistencia.AdaptadorIndividualContactTDS;
import umu.tds.apps.persistencia.IndividualContactDAO;

/**
 * Test que prueba el guardado y recuperación de contactos individuales en BD
 */
public class TestPersistenciaContactoIndividual {
	private static IndividualContact contact;
	private static IndividualContactDAO adapter;

	@BeforeClass
	public static void setUp() {
		User user = new User(Arrays.asList(new ImageIcon("/umu/tds/apps/resources/paper plane-white.png")), "Manuel", LocalDate.now(),
				0, "mrblacknuel", "1234", true, new JuniorDiscount(), null);
		LinkedList<Message> mensajes = new LinkedList<>();
		// mensajes.add(new Message("Pasar los test", LocalDate.now(), user, new
		// IndividualContact("Alberto", new LinkedList<>(), 0, user)));
		contact = new IndividualContact("Manu", mensajes, 654789321, user);
		adapter = AdaptadorIndividualContactTDS.getInstancia();
	}

	/**
	 * Prueba que funcione el registro de un contacto individual
	 */
	@Test
	public void registerContact() {
		adapter.registrarContacto(contact);
		assertTrue(adapter.recuperarContacto(contact.getCodigo()).equals(contact));
	}

	/**
	 * Prueba que funcione la eliminación de un contacto individual
	 */
	@Test(expected = NullPointerException.class)
	public void deleteContact() {
		registerContact();
		adapter.borrarContacto(contact);
		assertNull(adapter.recuperarContacto(contact.getCodigo()));
	}

	/**
	 * Prueba que funcione la modificación de un contacto individual
	 */

	@Test
	public void modifyContact() {
		registerContact();

		String newName = "Aitor Menta";
		contact.setNombre(newName);
		adapter.modificarContacto(contact);

		assertTrue(adapter.recuperarContacto(contact.getCodigo()).getNombre().equals(newName));
	}

	/**
	 * Prueba que funcione la recuperación de todos los contactos individuales
	 */
	@Test
	public void getAllContacts() {
		registerContact();

		assertTrue(adapter.recuperarTodosContactos().contains(contact));
	}

	@AfterClass
	public static void tearDown() {
		try {
			adapter.borrarContacto(contact);
		} catch (NullPointerException e) {
		}
	}
}
