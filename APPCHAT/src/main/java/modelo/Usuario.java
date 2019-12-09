package modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

// TODO Revisar implementación
// TODO Faltan premium
// TODO Faltan estadísticas
// TODO Revisar la relación entre grupos y usuarios, ¿los usuarios crean los grupos?

public class Usuario {
	private int codigo;
	private String nombre;
	private Date fechaNacimiento;
	private String email;
	private String movil;
	private String usuario;
	private String contraseña;
	private String imagen;
	private String saludo;
	private boolean premium;
	private List<ContactoIndividual> contactos;
	private List<ContactoGrupo> grupos;
	private List<Mensaje> mensajes;

	// Constructor sin saludo
	public Usuario(String nombre, Date fechanacimiento, String email, String movil, String usuario, String contraseña,
			String imagen, boolean premium) {
		this.codigo = 0;
		this.nombre = nombre;
		this.fechaNacimiento = fechanacimiento;
		this.email = email;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.imagen = imagen;
		this.saludo = "Hey there! I'm using AppChat";
		this.premium = premium;
		this.contactos = new LinkedList<ContactoIndividual>();
		this.grupos = new LinkedList<ContactoGrupo>();
		this.mensajes = new LinkedList<Mensaje>();
	}
	
	// Constructor con saludo
	public Usuario(String nombre, Date fechanacimiento, String email, String movil, String usuario, String contraseña,
			String imagen, String saludo, boolean premium) {
		this.codigo = 0;
		this.nombre = nombre;
		this.fechaNacimiento = fechanacimiento;
		this.email = email;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.imagen = imagen;
		this.saludo = saludo;
		this.premium = premium;
		this.contactos = new LinkedList<ContactoIndividual>();
		this.grupos = new LinkedList<ContactoGrupo>();
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechanacimiento) {
		this.fechaNacimiento = fechanacimiento;
	}

	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	public String getSaludo() {
		return saludo;
	}

	public void setSaludo(String saludo) {
		this.saludo = saludo;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public void addContacto(ContactoIndividual c) {
		contactos.add(c);
	}

	public void addContacto(String nombre, String telefono) {
		contactos.add(new ContactoIndividual(nombre, telefono));
	}

	public List<ContactoIndividual> getContactos() {
		return contactos;
	}

	public void addGrupo(ContactoGrupo g) {
		this.grupos.add(g);
	}
	
	public void crearGrupo(ContactoGrupo g) {
		g.setAdmin(this);
		grupos.add(g);
	}

	public void crearGrupo(String nombre, String... miembros) {
		ContactoGrupo g = new ContactoGrupo(nombre, miembros);
		g.setAdmin(this);
		grupos.add(g);
	}

	public void anadirMiembros(ContactoGrupo g, String... miembros) {
		if (grupos.contains(g) && g.getAdmin().equals(this))
			for (String miembro : miembros) {
				g.addMiembro(miembro);
			}
	}

	public void eliminarMiembros(ContactoGrupo g, String... miembros) {
		if (grupos.contains(g) && g.getAdmin().equals(this))
			for (String miembro : miembros) {
				g.removeMiembro(miembro);
			}
	}
	
	public List<ContactoGrupo> getGrupos() {
		return grupos;
	}
	
	public List<Mensaje> getMensajes() {
		return mensajes;
	}
	
	public void addMensaje(Mensaje m) {
		mensajes.add(m);
	}

	public void addMensaje(String texto, Date hora) {
		mensajes.add(new Mensaje(texto, hora, this.movil));
	}

	// solo se compara el código porque es único
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
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
		Usuario other = (Usuario) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [codigo=" + codigo + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + ", email="
				+ email + ", movil=" + movil + ", usuario=" + usuario + ", contraseña=" + contraseña + ", imagen="
				+ imagen + ", saludo=" + saludo + ", premium=" + premium + ", contactos=" + contactos + ", grupos="
				+ grupos + ", mensajes=" + mensajes + "]";
	}

}