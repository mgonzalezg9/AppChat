package umu.tds.apps.persistencia;

import java.util.List;

import umu.tds.apps.AppChat.User;

public interface UserDAO {
	public void registrarUsuario(User user);
	public void borrarUsuario(User user);
	public void modificarUsuario(User user);
	public User recuperarUsuario(int codigo);
	public List<User> recuperarTodosClientes();
}
