package umu.tds.apps.AppChat;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import org.junit.BeforeClass;
import org.junit.Test;

import umu.tds.apps.persistencia.AdaptadorGroupTDS;
import umu.tds.apps.persistencia.GroupDAO;

public class TestPersistenciaGroup {
	private static Group group;
	private static GroupDAO adapter;

	@BeforeClass
	public static void setUp() {
		User user = new User(new ImageIcon("/umu/tds/apps/resources/paper plane-white.png"), "Manu", LocalDate.now(), 0,
				"", "nick", true, new Premium(new YoungDiscount()));
		LinkedList<Message> mensajes = new LinkedList<>();
		mensajes.add(new Message("Pasar los test", LocalDate.now(), user, new IndividualContact("Alberto", new LinkedList<>(), 0, user)));
		LinkedList<IndividualContact> contactos = new LinkedList<>();
		contactos.add(new IndividualContact("Manu", mensajes, 654789321, user));
		group = new Group("Juernes de fiesta", mensajes, contactos, user);
		adapter = AdaptadorGroupTDS.getInstancia();
	}

	/**
	 * Prueba que funcione el registro de un grupo
	 */
	@Test
	public void registerGroup() {
		adapter.registrarGrupo(group);
		assertTrue(adapter.recuperarGrupo(group.getCodigo()).equals(group));
	}

	/**
	 * Prueba que funcione la eliminación de un grupo
	 */
	@Test
	public void deleteGroup() {
		adapter.registrarGrupo(group);
		adapter.borrarGrupo(group);
		assertTrue(adapter.recuperarGrupo(group.getCodigo()) == null);
	}

	/**
	 * Prueba que funcione la modificación de un grupo
	 */

	@Test
	public void modifyGroup() {
		registerGroup();

		String newName = "Aitor Menta";
		group.setNombre(newName);
		adapter.modificarGrupo(group);

		assertTrue(adapter.recuperarGrupo(group.getCodigo()).getNombre().equals(newName));
	}

	/**
	 * Prueba que funcione la recuperación de todos los grupos
	 */
	@Test
	public void getAllGroups() {
		registerGroup();

		assertTrue(adapter.recuperarTodosGrupos().contains(group));
	}
}
