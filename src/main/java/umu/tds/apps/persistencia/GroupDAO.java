package umu.tds.apps.persistencia;

import java.util.List;

import umu.tds.apps.AppChat.User;

public interface GroupDAO {
	public void registrarGrupo(Group grupo);
	public void borrarGrupo(Group grupo);
	public void modificarGrupo(Group grupo);
	public Group recuperarGrupo(int codigo);
	public List<Group> recuperarTodosGrupos();
}
