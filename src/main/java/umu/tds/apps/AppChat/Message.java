package umu.tds.apps.AppChat;

import java.time.LocalDate;

public class Message {
	// Properties.
	private int codigo;
	private String texto;
	private LocalDate hora;
	private int emoticono;
	private User emisor;
	private Contact receptor;

	// Constructor.
	public Message(String texto, LocalDate hora, User emisor, Contact receptor) {
		this.texto = texto;
		this.hora = hora;
		this.emisor = emisor;
		this.setReceptor(receptor);
	}

	public Message(int emoticono, LocalDate hora, User emisor, Contact receptor) {
		this.texto = "";
		this.hora = hora;
		this.emoticono = emoticono;
		this.emisor = emisor;
		this.setReceptor(receptor);
	}

	// Getters.
	public String getTexto() {
		return texto;
	}

	public LocalDate getHora() {
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

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	private void setReceptor(Contact receptor) {
		this.receptor = receptor;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
}
