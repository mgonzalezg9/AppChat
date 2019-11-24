package umu.tds.apps.persistencia;

import java.util.List;

public class AdaptadorMessageTDS implements MessageDAO {
	private static AdaptadorMessageTDS unicaInstancia = null;

	private AdaptadorMessageTDS() {
	}

	public static AdaptadorMessageTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorMessageTDS();
		return unicaInstancia;
	}

	@Override
	public void registrarMensaje(Message mensaje) {
		// TODO Auto-generated method stub

	}

	@Override
	public void borrarMensaje(Message mensaje) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarMensaje(Message mensaje) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message recuperarMensaje(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> recuperarTodosMensajes() {
		// TODO Auto-generated method stub
		return null;
	}

}
