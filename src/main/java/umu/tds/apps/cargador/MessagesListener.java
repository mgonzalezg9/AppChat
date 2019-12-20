package umu.tds.apps.cargador;

import java.util.EventListener;

public interface MessagesListener extends EventListener {
	public void nuevosMensajes(MessagesEvent ev);
}
