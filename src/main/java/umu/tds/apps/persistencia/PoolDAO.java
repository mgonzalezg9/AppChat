package umu.tds.apps.persistencia;

// Cache de la base de datos
import java.util.Hashtable;

public class PoolDAO {
	private static PoolDAO unicaInstancia;
	private Hashtable<Integer, Object> pool;

	private PoolDAO() {
		pool = new Hashtable<Integer, Object>();
	}

	public static PoolDAO getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new PoolDAO();
		return unicaInstancia;

	}

	public Object getObjeto(int id) {
		return pool.get(id);
	} // devuelve null si no encuentra el objeto

	public void addObjeto(int id, Object objeto) {
		pool.put(id, objeto);
	}

	public boolean contiene(int id) {
		return pool.containsKey(id);
	}

	public void removeObjeto(int id) {
		pool.remove(id);
	}
}
