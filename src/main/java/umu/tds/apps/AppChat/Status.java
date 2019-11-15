package umu.tds.apps.AppChat;

import javax.swing.ImageIcon;

public class Status {
	private ImageIcon img;
	private String frase;
	
	public Status(ImageIcon img, String frs) {
		this.img = img;
		this.frase = frs;
	}
	
	public ImageIcon getImg() {
		return img;
	}
	
	public String getFrase() {
		return frase;
	}
}
