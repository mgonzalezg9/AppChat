package umu.tds.apps.AppChat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import umu.tds.apps.persistencia.DAOException;
import umu.tds.apps.persistencia.FactoriaDAO;
import umu.tds.apps.persistencia.UserDAO;

/** El catálogo mantiene los objetos en memoria, en una tabla hash
 * para mejorar el rendimiento. Esto no se podría hacer en una base de
 * datos con un número grande de objetos. En ese caso se consultaria
 * directamente la base de datos
 */
public class UsersCatalogue {
	private Map<String, User> usuarios; 
	private static UsersCatalogue unicaInstancia = new UsersCatalogue();
	
	private FactoriaDAO dao;
	private UserDAO adaptadorUsuario;
	
	private UsersCatalogue () {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorUsuario = dao.getUserDAO();
  			usuarios = new HashMap<String, User>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static UsersCatalogue getUnicaInstancia(){
		return unicaInstancia;
	}
	
	// Devuelve todos los usuarios
	public List<User> getUsuarios(){
		ArrayList<User> lista = new ArrayList<User>();
		for (User u : usuarios.values()) 
			lista.add(u);
		return lista;
	}
	
	public User getUsuario(int codigo) {
		return usuarios.values().stream()
				.filter(u -> u.getCodigo() == codigo)
				.findAny()
				.orElse(null); 
	}
	
	public User getUsuario(String nick) {
		return usuarios.get(nick); 
	}
	
	public Optional<User> getUsuarioNumTelf(int numTelefono) {
		return usuarios.values().stream()
				.filter(u -> u.getNumTelefono() == numTelefono)
				.findAny();
	}
	
	public void addUsuario(User user) {
		usuarios.put(user.getNick(), user);
	}
	
	public void removeUsuario (User user) {
		usuarios.remove(user.getNick());
	}
	
	// Recupera todos los usuarios para trabajar con ellos en memoria
	private void cargarCatalogo() throws DAOException {
		 List<User> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		 for (User user : usuariosBD) 
			     usuarios.put(user.getNick(), user);
	}
	
	public boolean contains(User usuario) {
		return usuarios.containsValue(usuario);
	}
	
}
