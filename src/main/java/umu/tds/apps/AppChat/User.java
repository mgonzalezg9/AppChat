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
	private ImageIcon icon;
	private String name;
	private LocalDate fechaNacimiento;
	private int numTelefono;
	private String nick;
	private String password;
	private boolean premium;
	private Optional<Status> estado;
	private List<Group> gruposAdmin;
	private List<Contact> contactos;
	private UserRol rol;

	// Constructor
	public User(ImageIcon icon, String name, LocalDate fechaNacimiento, int numTelefono, String nick, String password,
			boolean premium, UserRol rol) {
		super();
		this.icon = icon;
		this.name = name;
		this.fechaNacimiento = fechaNacimiento;
		this.numTelefono = numTelefono;
		this.nick = nick;
		this.password = password;
		this.premium = premium;
		this.gruposAdmin = new LinkedList<>();
		this.contactos = new LinkedList<>();
		this.estado = Optional.empty();
		this.rol = rol;
	}

	public User(ImageIcon icon, String name, LocalDate fechaNacimiento, int numTelefono, String nick, String password,
			boolean premium, Status estado, List<Group> gruposAdmin, List<Contact> contactos) {
		setCodigo(0);
		this.icon = icon;
		this.setName(name);
		this.fechaNacimiento = fechaNacimiento;
		this.numTelefono = numTelefono;
		this.nick = nick;
		this.password = password;
		this.premium = premium;
		this.setEstado(Optional.ofNullable(estado));
		this.gruposAdmin = gruposAdmin;
		this.contactos = contactos;
	}

	// QUITAR Construtor solo para pruebas en estadosOld
	public User(ImageIcon icon, String name) {
		this.icon = icon;
		this.setName(name);
	}

	// QUITAR Construtor solo para pruebas en UserStatusWindow
	public User(ImageIcon icon, String name, String mensaje, String fecha) {
		this.icon = icon;
		this.setName(name);
		this.setEstado(Optional.of(new Status(icon, mensaje)));
		this.fechaNacimiento = LocalDate.now();
	}

	// Getters
	public ImageIcon getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setEstado(Optional<Status> estado) {
		this.estado = estado;
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

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public UserRol getRol() {
		return rol;
	}

	public void setRol(UserRol rol) {
		this.rol = rol;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		result = prime * result + ((contactos == null) ? 0 : contactos.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((gruposAdmin == null) ? 0 : gruposAdmin.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + numTelefono;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + (premium ? 1231 : 1237);
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
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

		User otroUsuario = (User) obj;
		if (codigo != otroUsuario.codigo) {
			return false;
		}

		if (!contactos.equals(otroUsuario.contactos)) {
			return false;
		}
		if (!estado.equals(otroUsuario.estado)) {
			return false;
		}
		if (!fechaNacimiento.equals(otroUsuario.fechaNacimiento)) {
			return false;
		}
		if (!gruposAdmin.equals(otroUsuario.gruposAdmin)) {
			return false;
		}
		if (!icon.getDescription().equals(otroUsuario.icon.getDescription())) {
			return false;
		}
		if (!name.equals(otroUsuario.name)) {
			return false;
		}
		if (!nick.equals(otroUsuario.nick)) {
			return false;
		}
		if (numTelefono != otroUsuario.numTelefono) {
			return false;
		}
		if (!password.equals(otroUsuario.password)) {
			return false;
		}
		if (premium != otroUsuario.premium) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "User [codigo=" + codigo + ", icon=" + icon + ", name=" + name + ", fechaNacimiento=" + fechaNacimiento
				+ ", numTelefono=" + numTelefono + ", nick=" + nick + ", password=" + password + ", premium=" + premium
				+ ", estado=" + estado + ", gruposAdmin=" + gruposAdmin + ", contactos=" + contactos + ", rol=" + rol
				+ "]";
	}

}
