package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// TODO Revisar implementación
// TODO Faltan premium
// TODO Faltan estadísticas

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
	}
	
	// Constructor con saludo
	public Usuario(String nombre, Date fechanacimiento, String email, String movil, String usuario, String contraseña,
			String imagen, String saludo, boolean premium) {
		this(nombre, fechanacimiento, email, movil, usuario, contraseña, imagen, premium);
		this.saludo = saludo;
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
	
	public List<ContactoGrupo> getGrupos() {
		return grupos;
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

	/*@Override
	public String toString() {
		return getClass().getSimpleName() + " [codigo=" + codigo + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + ", email="
				+ email + ", movil=" + movil + ", usuario=" + usuario + ", contraseña=" + contraseña + ", imagen="
				+ imagen + ", saludo=" + saludo + ", premium=" + premium + ", contactos=" + contactos + ", grupos="
				+ grupos + ", mensajes=" + mensajes + "]";
	}*/
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [\n\t codigo=" + codigo + "\n\t nombre=" + nombre + "\n\t fechaNacimiento=" + fechaNacimiento + "\n\t email="
				+ email + "\n\t movil=" + movil + "\n\t usuario=" + usuario + "\n\t contraseña=" + contraseña + "\n\t imagen="
				+ imagen + "\n\t saludo=" + saludo + "\n\t premium=" + premium + "\n\t contactos=" + contactos + "\n\t grupos="
				+ grupos + "]";
	}
	
	public List<Integer> getMensajesPorMes(){
		// Creamos una lista con todos los contactos: contactosInd + grupos
		List<Contacto> contacts = new LinkedList<Contacto>(this.contactos);
		for (ContactoGrupo cg : this.grupos) {
			contacts.add(cg);
		}
		List<Integer> mensajesAnual = new ArrayList<Integer>(12);
		int mes;
		int yearActual = LocalDate.now().getYear();
		for (Contacto c : contacts) {
			for (Mensaje m : c.getMensajes()) {
				if (m.getTlfEmisor().equals(this.movil) && m.getHora().getYear() == yearActual) {
					mes = m.getHora().getMonthValue();
					mensajesAnual.set(mes - 1, mensajesAnual.get(mes - 1)+ 1);
				}
			}
		}
		return mensajesAnual;
	}
	
	public Map<String, Integer> getMensajesPorGrupo(){
		
		Map<String, Integer> mensajesGrupo = new HashMap<String, Integer>(grupos.size());
		int contador;
		for (ContactoGrupo cg : grupos) {
			contador = 0;
			for (Mensaje m : cg.getMensajes()) {
				if (m.getTlfEmisor().equals(this.movil)) {
					contador++;
				}
			}
			mensajesGrupo.put(cg.getNombre(), contador);
		}
		return mensajesGrupo;
	}

}
