package umu.tds.apps.AppChat;

import javax.swing.ImageIcon;

public class UserStatu {
	// Properties.
	private ImageIcon icon;
	private String name;
	private String status;
	private String date;
	
	// Constructor.
	public UserStatu(ImageIcon icon, String name, String status, String date) {
		this.icon = icon;
		this.name = name;
		this.status = status;
		this.date = date;
	}
	
	// Getters.
	public ImageIcon getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	public String getDate() {
		return date;
	}
}
