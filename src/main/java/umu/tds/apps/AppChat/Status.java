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
	
	//  setters
	public void setFrase(String frase) { // Lo utilizamos para la pruebas de persistencia
		this.frase = frase;
	}
	
	// HashCode y equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		result = prime * result + ((frase == null) ? 0 : frase.hashCode());
		result = prime * result + ((img == null) ? 0 : img.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		if (codigo != other.codigo)
			return false;
		if (frase == null) {
			if (other.frase != null)
				return false;
		} else if (!frase.equals(other.frase))
			return false;
		if (img == null) {
			if (other.img != null)
				return false;
		} else if (!img.getDescription().equals(((ImageIcon) other.getImg()).getDescription()))
			return false;
		return true;
	}
	
	// toString
	@Override
	public String toString() {
		return "Status [codigo=" + codigo + ", img=" + img + ", frase=" + frase + "]";
	}
}
