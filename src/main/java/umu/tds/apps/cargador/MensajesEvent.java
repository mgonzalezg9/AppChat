package umu.tds.apps.cargador;

import java.util.EventObject;
import java.util.List;
import umu.tds.apps.whatsappparser.MensajeWhatsApp;

public class MensajesEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private List<MensajeWhatsApp> oldMessages, newMessages;

	public MensajesEvent(Object eventSource, List<MensajeWhatsApp> oldMessages, List<MensajeWhatsApp> newMessages) {
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

	public void setOldMessages(List<MensajeWhatsApp> oldMessages) {
		this.oldMessages = oldMessages;
	}

	public void setNewMessages(List<MensajeWhatsApp> newMessages) {
		this.newMessages = newMessages;
	}
}
