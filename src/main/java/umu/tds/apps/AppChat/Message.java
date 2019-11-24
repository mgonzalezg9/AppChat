package umu.tds.apps.AppChat;

import java.util.Date;

public class Message {
	// Properties.
	private String texto;
	private Date hora;
	private int emoticono;
	private User emisor;
	private Contact receptor;
	
	// Constructor.
	public Message(String texto, Date hora, int emoticono, User emisor, Contact receptor) {
		this.texto = texto;
		this.hora = hora;
		this.emoticono = emoticono;
		this.emisor = emisor;
		this.receptor = receptor;
	}
	
	// Getters.
	public String getTexto() {
		return texto;
	}
	public Date getHora() {
		return hora;
	}
	public int getEmoticono() {
		return emoticono;
	}
	public User getEmisor() {
		return emisor;
	}
	public Contact getReceptor() {
		return receptor;
	}
}
