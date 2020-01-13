package umu.tds.apps.persistencia;

import java.util.List;

import umu.tds.apps.AppChat.Message;

public interface MessageDAO {
	public void registrarMensaje(Message mensaje);
	public void borrarMensaje(Message mensaje);
	public void modificarMensaje(Message mensaje);
	public Message recuperarMensaje(int codigo);
	public List<Message> recuperarTodosMensajes();
}
