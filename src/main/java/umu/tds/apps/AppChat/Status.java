package umu.tds.apps.AppChat;

import javax.swing.ImageIcon;

public class Status {
	// Properties.
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
}
