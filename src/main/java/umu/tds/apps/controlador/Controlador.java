package umu.tds.apps.controlador;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;

import umu.tds.apps.vistas.Login;
import umu.tds.apps.vistas.UserSettings;

public class Controlador {
	private static List<ImageIcon> imagenes = new ArrayList<>();
	private static String nickname = "Manuelillo";
	private static String saludo = "Hola amigos del mundo!";
	
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
}
