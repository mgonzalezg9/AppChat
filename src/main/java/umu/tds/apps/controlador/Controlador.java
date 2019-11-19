package umu.tds.apps.controlador;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import umu.tds.apps.vistas.Login;
import umu.tds.apps.AppChat.*;
import umu.tds.apps.vistas.UserSettings;

public class Controlador {
	private static Controlador unicaInstancia = null;
	private static List<ImageIcon> imagenes = new ArrayList<>();
	private static String nickname = "Manuelillo";
	private static String saludo = "Hola amigos del mundo!";
	
	private Controlador() { }
	
	// Aplicamos el patrón Singleton.
	// Consiguiendo de esta forma que exista una única instancia de la clase controlador que es accesible globalmente.
	public static Controlador getInstancia () {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}
	
	public static boolean iniciarSesion(String dni, char[] password) {
		// TODO
		return true;
	}
	
	public static String getNombreUsuario() {
		return nickname;
	}
	
	public static String getSaludo() {
		return saludo;
	}
	
	public static void setSaludo(String saludo) {
		Controlador.saludo = saludo;
	}
	
	public static List<ImageIcon> getImagenesUsuario() {
		return imagenes;
	}
	
	public static void addImagenUsuario(ImageIcon image) {
		imagenes.add(image);
	}
	
	public static ImageIcon removeImagenUsuario(int pos) {
		return imagenes.remove(pos);
	}
	
	public static boolean deleteChat() {
		return false;
	}

	public static Status getEstado(User u) {
		if (new Random().nextInt(2) == 0) {
			return new Status(new ImageIcon(Controlador.class.getResource("/umu/tds/apps/resources/user.png")), "Flying");
		} else
			return new Status(new ImageIcon(Controlador.class.getResource("/umu/tds/apps/resources/fire_120x120.png")), "Fuegooo");
	
		
		
	}
}
