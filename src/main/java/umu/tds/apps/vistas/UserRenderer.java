package umu.tds.apps.vistas;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import umu.tds.apps.AppChat.User;

public class UserRenderer extends JLabel implements ListCellRenderer<User> {
	public UserRenderer () {
		setOpaque(true);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends User> list, User value, int index, boolean isSelected,
			boolean cellHasFocus) {
		addProfilePhoto(value.getProfilePhotos());
		setText(value.getName());

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		return this;
	}
}
