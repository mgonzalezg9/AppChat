package umu.tds.apps.AppChat;

import javax.swing.ImageIcon;

public class Contact {
	// Properties.
		private ImageIcon icon;
		private String name;
		private String lastMessage;
		private String date;
		
		// Constructor.
		public Contact(ImageIcon icon, String name, String lastMessage, String date) {
			this.icon = icon;
			this.name = name;
			this.lastMessage = lastMessage;
			this.date = date;
		}
		
		// Getters.
		public ImageIcon getIcon() {
			return icon;
		}

		public String getName() {
			return name;
		}

		public String getlastMessage() {
			return lastMessage;
		}

		public String getDate() {
			return date;
		}
}
