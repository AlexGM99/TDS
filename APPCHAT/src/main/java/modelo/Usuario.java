package modelo;

import java.util.Collections;
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
	private Date fechanacimiento;
	private String email;
	private String movil;
	private String usuario;
	private String contraseña;
	private String imagen;
	private String saludo;
	private boolean premium;
	private List<Contacto> contactos;
	private List<Mensaje> mensajes;

	// Constructor sin saludo
	public Usuario(String nombre, Date fechanacimiento, String email, String movil, String usuario, String contraseña,
			String imagen, boolean premium) {
		this.codigo = 0;
		this.nombre = nombre;
		this.fechanacimiento = fechanacimiento;
		this.email = email;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.imagen = imagen;
		this.saludo = null;
		this.premium = premium;
		this.contactos = new LinkedList<Contacto>();
	}
	
	// Constructor con saludo
	public Usuario(String nombre, Date fechanacimiento, String email, String movil, String usuario, String contraseña,
			String imagen, String saludo, boolean premium) {
		this.codigo = 0;
		this.nombre = nombre;
		this.fechanacimiento = fechanacimiento;
		this.email = email;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.imagen = imagen;
		this.saludo = saludo;
		this.premium = premium;
		this.contactos = new LinkedList<Contacto>();
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

	public Date getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
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

	public void addContacto(Contacto c) {
		contactos.add(c);
	}

	public void addContacto(String nombre, String telefono) {
		contactos.add(new ContactoIndividual(nombre, telefono));
	}

	public List<Contacto> getContactos() {
		return Collections.unmodifiableList(contactos);
	}

	public void crearGrupo(ContactoGrupo g) {
		contactos.add(g);
		g.setAdmin(this);
		contactos.add(g);
	}

	public void crearGrupo(String nombre, ContactoIndividual... miembros) {
		ContactoGrupo g = new ContactoGrupo(nombre, miembros);
		g.setAdmin(this);
		contactos.add(g);
	}

	public void anadirMiembros(ContactoGrupo g, ContactoIndividual... miembros) {
		if (contactos.contains(g) && g.getAdmin().equals(this))
			for (ContactoIndividual miembro : miembros) {
				g.addMiembro(miembro);
			}
	}

	public void eliminarMiembros(ContactoGrupo g, ContactoIndividual... miembros) {
		if (contactos.contains(g) && g.getAdmin().equals(this))
			for (ContactoIndividual miembro : miembros) {
				g.removeMiembro(miembro);
			}
	}
	
	public List<Mensaje> getMensajes() {
		return Collections.unmodifiableList(mensajes);
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

}
