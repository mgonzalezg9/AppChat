package umu.tds.apps.persistencia;

import java.util.List;

import umu.tds.apps.AppChat.Group;
import umu.tds.apps.controlador.Controlador;

public class AdaptadorGroupTDS implements GroupDAO {
	private static AdaptadorGroupTDS unicaInstancia = null;
	
	private AdaptadorGroupTDS() {
	}
	
	public static AdaptadorGroupTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorGroupTDS();
		return unicaInstancia;
	}
	
	@Override
	public void registrarGrupo(Group grupo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void borrarGrupo(Group grupo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarGrupo(Group grupo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Group recuperarGrupo(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> recuperarTodosGrupos() {
		// TODO Auto-generated method stub
		return null;
	}

}
