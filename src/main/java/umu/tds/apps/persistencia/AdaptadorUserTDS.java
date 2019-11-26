package umu.tds.apps.persistencia;

import java.util.List;

import umu.tds.apps.AppChat.User;

public class AdaptadorUserTDS implements UserDAO {
	private static AdaptadorUserTDS unicaInstancia = null;

	private AdaptadorUserTDS() {
	}

	public static AdaptadorUserTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorUserTDS();
		return unicaInstancia;
	}

	@Override
	public void registrarUsuario(User user) {
		

	}

	@Override
	public void borrarUsuario(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarUsuario(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public User recuperarUsuario(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> recuperarTodosClientes() {
		// TODO Auto-generated method stub
		return null;
	}

}
