package umu.tds.apps.persistencia;

import java.util.List;

import umu.tds.apps.AppChat.Status;

public class AdaptadorStatusTDS implements StatusDAO {
	private static AdaptadorStatusTDS unicaInstancia = null;

	private AdaptadorStatusTDS() {
	}

	public static AdaptadorStatusTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorStatusTDS();
		return unicaInstancia;
	}
	
	@Override
	public void registrarEstado(Status status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void borrarEstado(Status status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarEstado(Status status) {
		// TODO Auto-generated method stub

	}

	@Override
	public Status recuperarEstado(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Status> recuperarTodosEstados() {
		// TODO Auto-generated method stub
		return null;
	}

}