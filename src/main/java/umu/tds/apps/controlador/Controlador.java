package umu.tds.apps.controlador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;

import umu.tds.apps.vistas.Login;
import umu.tds.apps.vistas.UserSettings;

public class Controlador {
	public static boolean iniciarSesion(String dni, char[] password) {
		// TODO
		return false;
	}
	
	public static List<ImageIcon> getImagenesUsuario() {
		ArrayList<ImageIcon> lista = new ArrayList<>();
		
		// TODO Recuperar las imagenes del usuario
		Collections.addAll(lista, new ImageIcon(Controlador.class.getResource("/umu/tds/apps/resources/user.png")), new ImageIcon(Login.class.getResource("/umu/tds/apps/resources/icon.png")), new ImageIcon(Controlador.class.getResource("/umu/tds/apps/resources/fire_120x120.png")));
		
		return lista;
	}
}
