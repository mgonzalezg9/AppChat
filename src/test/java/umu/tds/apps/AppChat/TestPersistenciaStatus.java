package umu.tds.apps.AppChat;

import static org.junit.Assert.assertTrue;
import javax.swing.ImageIcon;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import umu.tds.apps.persistencia.AdaptadorStatusTDS;
import umu.tds.apps.persistencia.StatusDAO;

/**
 * Test que prueba el guardado y recuperación de estados en BD
 */
public class TestPersistenciaStatus {
	private static Status status;
	private static StatusDAO adapter;

	@BeforeClass
	public static void setUp() {
		status = new Status(new ImageIcon("/umu/tds/apps/resources/paper plane-white.png"), "Este es mi nuevo estado");
		adapter = AdaptadorStatusTDS.getInstancia();
	}

	/**
	 * Prueba que funcione el registro de un estado
	 */
	@Test
	public void registerStatus() {
		adapter.registrarEstado(status);
		assertTrue(adapter.recuperarEstado(status.getCodigo()).equals(status));
	}

	/**
	 * Prueba que funcione la eliminación de un estado
	 */
	@Test(expected = NullPointerException.class)
	public void deleteStatus() {
		registerStatus();
		adapter.borrarEstado(status);
		assertTrue(adapter.recuperarEstado(status.getCodigo()) == null);
	}

	/**
	 * Prueba que funcione la modificación de un estado
	 */

	@Test
	public void modifyStatus() {
		registerStatus();

		String newMensaje = "Este es mi NUEVO estado";
		status.setFrase(newMensaje);
		adapter.modificarEstado(status);

		assertTrue(adapter.recuperarEstado(status.getCodigo()).getFrase().equals(newMensaje));
	}

	/**
	 * Prueba que funcione la recuperación de todos los estados
	 */
	@Test
	public void getAllStatus() {
		registerStatus();

		assertTrue(adapter.recuperarTodosEstados().contains(status));
	}
	
	@AfterClass
	public static void tearDown() {
		try {
			adapter.borrarEstado(status);
		} catch (NullPointerException e) {
		}
	}
}
