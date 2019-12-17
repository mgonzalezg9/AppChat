package umu.tds.apps.AppChat;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;

public class User {
	private static final double PRECIO_PREMIUM = 19.90;
	private static final LocalDate FECHA_JOVEN = LocalDate.of(2003, 1, 1);
	private static final LocalDate FECHA_ADULTO = LocalDate.of(1955, 1, 1);
	private static final int NUM_GRUPOS_TARTA = 6;

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
	private Optional<Discount> descuento;

	// Constructores
	public User(List<ImageIcon> iconList, String name, LocalDate fechaNacimiento, int numTelefono, String nick,
			String password, boolean premium, Discount descuento, String saludo) {
		this(iconList, name, fechaNacimiento, numTelefono, nick, password, premium, null, saludo, new LinkedList<>(),
				new LinkedList<>(), descuento);
	}

	public User(List<ImageIcon> iconList, String name, LocalDate fechaNacimiento, int numTelefono, String nick,
			String password, boolean premium, Status estado, String saludo, List<Group> gruposAdmin,
			List<Contact> contactos, Discount descuento) {
		this.codigo = 0;
		this.profilePhotos = iconList;
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

		if (descuento == null) {
			// Si es joven descuento para jovenes, si es muy mayor para mayores
			if (fechaNacimiento.isAfter(FECHA_JOVEN)) {
				descuento = new JuniorDiscount();
			} else if (fechaNacimiento.isBefore(FECHA_ADULTO)) {
				descuento = new SeniorDiscount();
			}
		}
		this.descuento = Optional.ofNullable(descuento);
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

	public Optional<Discount> getDescuento() {
		return descuento;
	}

	public double getPrecio() {
		if (descuento.isPresent()) {
			return descuento.get().getDescuento() * PRECIO_PREMIUM;
		} else
			return PRECIO_PREMIUM;
	}

	// Devuelve una lista con el numero de mensajes enviados en cada mes del año en
	// curso
	public List<Long> getMensajesMes() {
		List<Long> mensajesMes = new LinkedList<>();

		LocalDate fechaActual = LocalDate.now();
		for (int i = 1; i <= fechaActual.getMonthValue(); i++) {
			// Primer y ultimo dia del mes
			LocalDate primerDia = LocalDate.of(fechaActual.getYear(), i, 1);
			LocalDate ultimoDia = LocalDate.of(fechaActual.getYear(), i, primerDia.lengthOfMonth());

			// Cuenta el numero de mensajes enviados entre el inicio de ese mes y el fin
			long num = contactos.stream().flatMap(c -> c.getMensajesEnviados().stream())
					.map(m -> m.getHora().toLocalDate()).filter(h -> h.isBefore(ultimoDia))
					.filter(h -> h.isAfter(primerDia)).count();

			mensajesMes.add(num);
		}

		return mensajesMes;
	}

	// Devuelve los seis grupos mas frecuentados con sus correspondientes mensajes
	// enviados
	public Map<String, Integer> getGruposMasFrecuentados() {
		return contactos.stream().filter(c -> c instanceof Group).map(g -> (Group) g)
				.sorted((g1, g2) -> g2.getMisMensajesGrupo(this).size() - g1.getMisMensajesGrupo(this).size()).limit(NUM_GRUPOS_TARTA)
				.collect(Collectors.toMap(Group::getNombre, g -> g.getMisMensajesGrupo(this).size()));
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

	public void setDescuento(Discount descuento) {
		this.descuento = Optional.ofNullable(descuento);
	}

	public int addProfilePhoto(ImageIcon icon) {
		this.profilePhotos.add(icon);
		return profilePhotos.size();
	}

	// Methods
	public ImageIcon removeProfilePhoto(int pos) {
		return this.profilePhotos.remove(pos);
	}

	public void addGrupoAdmin(Group g) {
		gruposAdmin.add(g);
	}

	public void modificarGrupoAdmin(Group g) {
		for (int i = 0; i < gruposAdmin.size(); i++)
			if (gruposAdmin.get(i).getCodigo() == g.getCodigo())
				gruposAdmin.set(i, g);
	}

	public void addContacto(IndividualContact c) {
		contactos.add(c);
	}

	public void addGrupo(Group g) {
		contactos.add(g);
	}

	public void modificarGrupo(Group g) {
		List<Group> grupos = contactos.stream().filter(c -> c instanceof Group).map(c -> (Group) c)
				.collect(Collectors.toList());
		for (int i = 0; i < grupos.size(); i++)
			if (grupos.get(i).getCodigo() == g.getCodigo())
				grupos.set(i, g);
	}

	public void removeContact(Contact c) {
		contactos.remove(c);
		if (c instanceof Group && ((Group) c).getAdmin().getCodigo() == this.codigo) {
			gruposAdmin.remove((Group) c);
		}
	}

	// Metodos de test
	@Override
	public String toString() {
		return "User [codigo=" + codigo + ", icon=" + profilePhotos + ", name=" + name + ", fechaNacimiento="
				+ fechaNacimiento + ", numTelefono=" + numTelefono + ", nick=" + nick + ", password=" + password
				+ ", premium=" + premium + ", saludo=" + saludo + ", estado=" + estado + ", gruposAdmin=" + gruposAdmin
				+ ", contactos=" + contactos + ", rol=" + descuento + "]";
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
		result = prime * result + ((descuento == null) ? 0 : descuento.hashCode());
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
		if (descuento == null) {
			if (other.descuento != null)
				return false;
		} else if (!descuento.equals(other.descuento))
			return false;
		if (saludo == null) {
			if (other.saludo != null)
				return false;
		} else if (!saludo.equals(other.saludo))
			return false;
		return true;
	}
}
