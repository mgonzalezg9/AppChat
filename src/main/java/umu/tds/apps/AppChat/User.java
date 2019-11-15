package umu.tds.apps.AppChat;

import javax.swing.ImageIcon;

public class User {
	private ImageIcon icon;
	private String name;
	
	public User(ImageIcon icon, String name) {
		this.icon = icon;
		this.name = name;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}
	
	public String getName() {
		return name;
	}
}
