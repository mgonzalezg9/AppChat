package umu.tds.apps.whatsappparser;

import java.time.LocalDateTime;

public class MensajeWhatsApp {
	private String texto;
	private String autor;
	private LocalDateTime fecha;
	
	public MensajeWhatsApp(String texto, String autor, LocalDateTime fecha) {
		this.texto = texto;
		this.autor = autor;
		this.fecha = fecha;
	}
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	
}
