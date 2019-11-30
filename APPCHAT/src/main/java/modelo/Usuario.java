package modelo;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

// TODO Implementar

public class Usuario {
	private int codigo;
	private String nombre;
	private Date fechanacimiento;
	private String movil;
	private String usuario;
	private String contraseña;
	private String imagen;
	private boolean premium;
	private List<Contacto> contactos;
	private List<ContactoGrupo> administrador;

	
	public Usuario(String nombre, Date fechanacimiento, String movil, String usuario, String contraseña,
			String imagen, boolean premium) {
		this.codigo = 0;
		this.nombre = nombre;
		this.fechanacimiento = fechanacimiento;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.imagen = imagen;
		this.premium = premium;
		this.contactos = new LinkedList<Contacto>();
		this.administrador = new LinkedList<ContactoGrupo>();
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
	
	public void addAdministrador(ContactoGrupo g) {
		contactos.add(g);
	}
	
	public void addAdministrador(String nombre) {
		contactos.add(new ContactoGrupo(nombre, this));
	}
	
	public List<ContactoGrupo> getAdministrador() {
		return Collections.unmodifiableList(administrador);
	}
	
	

}
