package umu.tds.apps.AppChat;

import java.time.LocalDate;
import java.util.Arrays;
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
	private static final String SALUDO_INICIAL = "Hello World!";

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
	/**
	 * Constructor para la creación de un usuario nuevo en el sistema
	 * 
	 * @param icono           Foto de perfil del usuario
	 * @param name            Nombre completo del usuario
	 * @param fechaNacimiento Fecha de nacimiento
	 * @param numTelefono     Telefono
	 * @param nick            Alias elegido por el usuario
	 * @param password        contraseña para autenticarse el usuario
	 */
	public User(ImageIcon icono, String name, LocalDate fechaNacimiento, int numTelefono, String nick,
			String password) {
		this(new LinkedList<>(Arrays.asList(icono)), name, fechaNacimiento, numTelefono, nick, password, false, null,
				SALUDO_INICIAL, new LinkedList<>(), new LinkedList<>(), null);
	}

	/**
	 * Constructor empleado en persistencia para recuperar los objetos
	 * 
	 * @param iconList        Lista con las fotos de perfil del usuario
	 * @param name            Nombre completo del usuario
	 * @param fechaNacimiento Fecha de nacimiento
	 * @param numTelefono     Telefono
	 * @param nick            Alias elegido por el usuario
	 * @param password        contraseña para autenticarse el usuario
	 * @param premium         Indica si el usuario es premium
	 * @param descuento       Porcentaje de descuento que el usuario tendrá
	 * @param saludo          Saludo del usuario
	 */
	public User(List<ImageIcon> iconList, String name, LocalDate fechaNacimiento, int numTelefono, String nick,
			String password, boolean premium, Discount descuento, String saludo) {
		this(iconList, name, fechaNacimiento, numTelefono, nick, password, premium, null, saludo, new LinkedList<>(),
				new LinkedList<>(), descuento);
	}

	/**
	 * Constructor principal
	 * 
	 * @param iconList        Lista con las fotos de perfil del usuario
	 * @param name            Nombre completo del usuario
	 * @param fechaNacimiento Fecha de nacimiento
	 * @param numTelefono     Telefono
	 * @param nick            Alias elegido por el usuario
	 * @param password        contraseña para autenticarse el usuario
	 * @param premium         Indica si el usuario es premium
	 * @param estado          Estado que el usuario tendrá
	 * @param saludo          Saludo del usuario
	 * @param gruposAdmin     Grupos en los que el usuario es administrador
	 * @param contactos       Contactos que tiene guardados el usuario
	 * @param descuento       Porcentaje de descuento que el usuario tendrá
	 */
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
			return descuento.get().getDescuento(PRECIO_PREMIUM);
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
				.sorted((g1, g2) -> g2.getMisMensajesGrupo(this).size() - g1.getMisMensajesGrupo(this).size())
				.limit(NUM_GRUPOS_TARTA)
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
		profilePhotos.add(icon);
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

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + numTelefono;
		return result;
	}

	/**
	 * Dos usuarios serán iguales si tienen el mismo nick o número de teléfono
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return nick.equals(other.nick) || numTelefono == other.numTelefono;
	}
}
