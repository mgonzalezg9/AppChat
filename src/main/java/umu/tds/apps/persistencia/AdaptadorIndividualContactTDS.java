package umu.tds.apps.persistencia;

import java.util.List;

import umu.tds.apps.AppChat.IndividualContact;
import umu.tds.apps.AppChat.User;

public class AdaptadorIndividualContactTDS implements IndividualContactDAO {
	private static AdaptadorIndividualContactTDS unicaInstancia = null;

	private AdaptadorIndividualContactTDS() {
	}

	public static AdaptadorIndividualContactTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorIndividualContactTDS();
		return unicaInstancia;
	}

	@Override
	public void registrarContacto(IndividualContact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void borrarContacto(IndividualContact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarContacto(IndividualContact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public IndividualContact recuperarContacto(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IndividualContact> recuperarTodosContactos() {
		// TODO Auto-generated method stub
		return null;
	}

}
