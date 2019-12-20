package umu.tds.apps.cargador;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import umu.tds.apps.whatsappparser.MensajeWhatsApp;

public class MessagesCharger implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<MessagesListener> observadores;
	private List<MensajeWhatsApp> listaMensajes;

	// Constructores
	public MessagesCharger() {
		this(new LinkedList<>());
	}

	public MessagesCharger(List<MensajeWhatsApp> listaMensajes) {
		this.listaMensajes = listaMensajes;
		observadores = new LinkedList<>();
	}

	// Metodos get y set
	public List<MensajeWhatsApp> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<MensajeWhatsApp> nuevalistaMensajes) {
		if (!listaMensajes.equals(nuevalistaMensajes)) {
			MessagesEvent evento = new MessagesEvent(this, listaMensajes, nuevalistaMensajes);
			listaMensajes = nuevalistaMensajes;
			notificarCambioMensajes(evento);
		}
	}

	// Recorre la lista de oyentes y notifica del cambio
	private void notificarCambioMensajes(MessagesEvent evento) {
		// Para evitar problemas de concurrencia me creo una copia
		List<MessagesListener> copia = null;
		synchronized (observadores) {
			copia = new LinkedList<>(observadores);
		}

		for (MessagesListener observador : copia) {
			observador.nuevosMensajes(evento);
		}
	}

	// Manejo de los oyentes
	public synchronized void addListener(MessagesListener observador) {
		this.observadores.add(observador);
	}

	public synchronized void removeListener(MessagesListener observador) {
		this.observadores.remove(observador);
	}

}
