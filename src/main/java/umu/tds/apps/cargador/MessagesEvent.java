package umu.tds.apps.cargador;

import java.util.EventObject;
import java.util.List;

import modelo.MensajeWhatsApp;

public class MessagesEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private List<MensajeWhatsApp> oldMessages, newMessages;

	public MessagesEvent(Object eventSource, List<MensajeWhatsApp> oldMessages, List<MensajeWhatsApp> newMessages) {
		super(eventSource);
		this.oldMessages = oldMessages;
		this.newMessages = newMessages;
	}

	public List<MensajeWhatsApp> getOldMessages() {
		return oldMessages;
	}

	public List<MensajeWhatsApp> getNewMessages() {
		return newMessages;
	}

}
