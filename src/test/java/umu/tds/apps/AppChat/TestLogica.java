package umu.tds.apps.AppChat;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import umu.tds.apps.controlador.Controlador;

// Clase para crear contenido en BD con el que probar la logica de la aplicacion
public class TestLogica {
	// Constantes
	private final static String ADMIN_NAME = "Administrador";
	private final static String ADMIN_NICK = "admin";
	private final static String ADMIN_MAIL = "admin@um.es";
	private final static int ADMIN_PHONE = 1;
	private final static Status ADMIN_STATUS = new Status(new ImageIcon("/umu/tds/apps/resources/paper plane-white.png"), "Buscadme en Telegram");
	private final static String NOADMIN_NAME = "No Administrador";
	private final static String NOADMIN_NICK = "noadmin";
	private final static String NOADMIN_MAIL = "noadmin@um.es";
	private final static int NOADMIN_PHONE = 2;
	private final static String PASSWORD = "1234";
	private final static ImageIcon ICON = new ImageIcon("/umu/tds/apps/resources/user50.png");
	private final static String GROUP_NAME = "Juernes";
	private final static int EMOJI = 3;
	
	// Persistencia
	private static Controlador controlador;

	@BeforeClass
	public static void setUpBeforeClass() {
		// Conexiones con el servidor
		controlador = Controlador.getInstancia();
	}

	@Test
	public void test() {
		// Creacion de la cuenta Admin
		assertTrue(controlador.crearCuenta(ICON, ADMIN_NICK, PASSWORD, ADMIN_MAIL, ADMIN_NAME, ADMIN_PHONE, LocalDate.now()));
		
		// Admin tiene un estado
		controlador.addEstado(ADMIN_STATUS);
		controlador.cerrarSesion();
		
		// Creacion de la cuenta No Admin
		assertTrue(controlador.crearCuenta(ICON, NOADMIN_NICK, PASSWORD, NOADMIN_MAIL, NOADMIN_NAME, NOADMIN_PHONE, LocalDate.now()));
		
		// No Admin añade como contacto a Admin
		assertTrue(controlador.crearContacto(ADMIN_NAME, ADMIN_PHONE));
		controlador.cerrarSesion();
		
		// Admin inicia sesión
		assertTrue(controlador.iniciarSesion(ADMIN_NICK, PASSWORD));
		
		// Admin añade a NoAdmin como contacto
		assertTrue(controlador.crearContacto(NOADMIN_NAME, NOADMIN_PHONE));
		
		// Admin crea un grupo compartido por ambos
		LinkedList<IndividualContact> integrantes = new LinkedList<>();
		IndividualContact contactoNoAdmin = (IndividualContact) controlador.getContacto(NOADMIN_NAME).get();
		integrantes.add(contactoNoAdmin);
		controlador.crearGrupo(GROUP_NAME, integrantes);
		
		// Admin manda un mensaje a No Admin
		controlador.enviarMensaje(contactoNoAdmin, "Ya te he metido al grupo!");
		controlador.cerrarSesion();
		
		// No Admin inicia sesión y responde por el grupo Juernes
		/*assertTrue(controlador.iniciarSesion(NOADMIN_NICK, PASSWORD));
		Group grupo = (Group) controlador.getContacto(GROUP_NAME).get();
		controlador.enviarMensaje(grupo, EMOJI);*/
		
		// NoAdmin cierra sesión
		controlador.cerrarSesion();
	}

	@AfterClass
	public static void tearDownAfterClass() {
		// Borrado (opcional) de los objetos de BD
	}

}
