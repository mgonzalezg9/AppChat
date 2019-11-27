package umu.tds.apps.AppChat;

import javax.swing.ImageIcon;

public class Status {
	public final static Status NONE = new Status(null, "");
	
	// Properties.
	private int codigo;
	private ImageIcon img;
	private String frase;
	
	// Constructor.
	public Status(ImageIcon img, String frs) {
		this.img = img;
		this.frase = frs;
	}
	
	// Getters.
	public ImageIcon getImg() {
		return img;
	}
	
	public String getFrase() {
		return frase;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}
