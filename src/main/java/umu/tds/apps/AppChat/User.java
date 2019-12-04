package umu.tds.apps.AppChat;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;

public class User {
	// Properties
	private int codigo;
	private List<ImageIcon> profilePhotos;
	private String name;
	private LocalDate fechaNacimiento;
	private int numTelefono;
	private String nick;
	private String password;
	private boolean premium;
	private String saludo;
	private Optional<Status> estado;
	private List<Group> gruposAdmin;
	private List<Contact> contactos;
	private UserRol rol;

	// Constructores
	public User(ImageIcon icon, String name, LocalDate fechaNacimiento, int numTelefono, String nick, String password,
			boolean premium, UserRol rol, String saludo) {
		this(icon, name, fechaNacimiento, numTelefono, nick, password, premium, null, saludo, new LinkedList<>(),
				new LinkedList<>());
	}

	public User(ImageIcon icon, String name, LocalDate fechaNacimiento, int numTelefono, String nick, String password,
			boolean premium, Status estado, String saludo, List<Group> gruposAdmin, List<Contact> contactos) {
		this.codigo = 0;
		this.profilePhotos = new LinkedList<>();
		this.profilePhotos.add(icon);
		this.name = name;
		this.fechaNacimiento = fechaNacimiento;
		this.numTelefono = numTelefono;
		this.nick = nick;
		this.password = password;
		this.premium = premium;
		this.estado = Optional.ofNullable(estado);
		this.saludo = saludo;
		this.gruposAdmin = gruposAdmin;
		this.contactos = contactos;
	}

	// Getters
	public List<ImageIcon> getProfilePhotos() {
		return profilePhotos;
	}

	public ImageIcon getProfilePhoto() {
		return profilePhotos.get(profilePhotos.size() - 1);
	}

	public String getName() {
		return name;
	}

	public String getSaludo() {
		return saludo;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public int getNumTelefono() {
		return numTelefono;
	}

	public String getNick() {
		return nick;
	}

	public String getPassword() {
		return password;
	}

	public boolean isPremium() {
		return premium;
	}

	public Optional<Status> getEstado() {
		return estado;
	}

	public List<Group> getGruposAdmin() {
		return gruposAdmin;
	}

	public List<Contact> getContactos() {
		return contactos;
	}

	public int getCodigo() {
		return codigo;
	}

	public UserRol getRol() {
		return rol;
	}

	// Setters
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSaludo(String saludo) {
		this.saludo = saludo;
	}

	public void setPremium() {
		premium = true;
	}

	public void setEstado(Optional<Status> estado) {
		this.estado = estado;
	}

	public void setRol(UserRol rol) {
		this.rol = rol;
	}

	public void addProfilePhoto(ImageIcon icon) {
		this.profilePhotos.add(icon);
	}

	public ImageIcon removeProfilePhoto(int pos) {
		return this.profilePhotos.remove(pos);
	}

	public void addGrupoAdmin(Group g) {
		gruposAdmin.add(g);
	}

	public void addContacto(IndividualContact c) {
		contactos.add(c);
	}

	public void addGrupo(Group g) {
		contactos.add(g);
	}

	// Metodos de test
	@Override
	public String toString() {
		return "User [codigo=" + codigo + ", icon=" + profilePhotos + ", name=" + name + ", fechaNacimiento="
				+ fechaNacimiento + ", numTelefono=" + numTelefono + ", nick=" + nick + ", password=" + password
				+ ", premium=" + premium + ", saludo=" + saludo + ", estado=" + estado + ", gruposAdmin=" + gruposAdmin
				+ ", contactos=" + contactos + ", rol=" + rol + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		result = prime * result + ((contactos == null) ? 0 : contactos.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((gruposAdmin == null) ? 0 : gruposAdmin.hashCode());
		result = prime * result + ((profilePhotos == null) ? 0 : profilePhotos.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + numTelefono;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + (premium ? 1231 : 1237);
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		result = prime * result + ((saludo == null) ? 0 : saludo.hashCode());
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
		User other = (User) obj;
		if (codigo != other.codigo)
			return false;
		if (contactos == null) {
			if (other.contactos != null)
				return false;
		} else if (!contactos.equals(other.contactos))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (gruposAdmin == null) {
			if (other.gruposAdmin != null)
				return false;
		} else if (!gruposAdmin.equals(other.gruposAdmin))
			return false;
		if (profilePhotos == null) {
			if (other.profilePhotos != null)
				return false;
		} else if (!profilePhotos.equals(other.profilePhotos))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		if (numTelefono != other.numTelefono)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (premium != other.premium)
			return false;
		if (rol == null) {
			if (other.rol != null)
				return false;
		} else if (!rol.equals(other.rol))
			return false;
		if (saludo == null) {
			if (other.saludo != null)
				return false;
		} else if (!saludo.equals(other.saludo))
			return false;
		return true;
	}
}
