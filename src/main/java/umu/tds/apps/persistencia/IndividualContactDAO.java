package umu.tds.apps.persistencia;

import java.util.List;

import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.IndividualContact;
import umu.tds.apps.AppChat.User;

public interface IndividualContactDAO {
	public void registrarContacto(IndividualContact contact);
	public void borrarContacto(IndividualContact contact);
	public void modificarContacto(IndividualContact contact);
	public IndividualContact recuperarContacto(int codigo);
	public List<IndividualContact> recuperarTodosContactos();
}
