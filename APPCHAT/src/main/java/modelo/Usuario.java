package modelo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import interfazGrafica.Datos_Chat_Actual;

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
			String imagen) {
		this.codigo = 0;
		this.nombre = nombre;
		this.fechaNacimiento = fechanacimiento;
		this.email = email;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.imagen = imagen;
		this.saludo = "Hey there! I'm using AppChat";
		this.premium = false;
		this.contactos = new LinkedList<ContactoIndividual>();
		this.grupos = new LinkedList<ContactoGrupo>();
	}

	// Constructor con saludo
	public Usuario(String nombre, Date fechanacimiento, String email, String movil, String usuario, String contraseña,
			String imagen, String saludo) {
		this(nombre, fechanacimiento, email, movil, usuario, contraseña, imagen);
		this.saludo = saludo;
	}
	
	// Constructor con saludo y premium
	public Usuario(String nombre, Date fechanacimiento, String email, String movil, String usuario, String contraseña,
			String imagen, String saludo, boolean premium) {
		this(nombre, fechanacimiento, email, movil, usuario, contraseña, imagen);
		this.saludo = saludo;
		this.premium = premium;
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
	
	public String getNombreContacto(String movil) {
		try {
		return contactos.stream().filter(p -> p.getMovil().equals(movil)).findFirst().get().getNombre();
		}
		catch (Exception e) {
			return "";
		}
	}
	
	public ContactoGrupo registrarGrupo(String nombre, List<ContactoIndividual> contactos) {
		List<String> c = new LinkedList<String>();
		contactos.stream().forEach(ci->c.add(ci.getMovil()));;
		ContactoGrupo grupo = new ContactoGrupo(nombre, c);
		if(grupos.contains(grupo))
			return null;
		grupos.add(grupo);
		grupo.setAdmin(this);
		return grupo;
	}
	
	public List<Contacto> RecuperarContactosFiltrados(String text){
		LinkedList<Contacto> contactosFiltrados = new LinkedList<Contacto>();
		contactos.stream().filter(cont -> contenido(cont.getNombre(), text))
						  .forEach(cont -> contactosFiltrados.add(cont)); // Añadiendo con un for each
		contactosFiltrados.addAll(grupos.stream().filter(cont -> contenido(cont.getNombre(), text))
										.collect(Collectors.toList())); // añadiendo como coleccion completa
		return contactosFiltrados;
	}
	
	public boolean contenido(String contenedorB, String contenidoB) {
		int i = 0;
		if (contenidoB.length() > contenedorB.length()) return false;
		String contenedor = contenedorB.toLowerCase();
		String contenido = contenidoB.toLowerCase();
		for (; i< contenedor.length(); i++) {
			if (contenedor.startsWith(contenido, i)) {
				return true;
			}
		}
		return false;
	}

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
		if (contactos == null) {
			if (other.contactos != null)
				return false;
		} else if (!contactos.equals(other.contactos))
			return false;
		if (contraseña == null) {
			if (other.contraseña != null)
				return false;
		} else if (!contraseña.equals(other.contraseña))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (grupos == null) {
			if (other.grupos != null)
				return false;
		} else if (!grupos.equals(other.grupos))
			return false;
		if (imagen == null) {
			if (other.imagen != null)
				return false;
		} else if (!imagen.equals(other.imagen))
			return false;
		if (movil == null) {
			if (other.movil != null)
				return false;
		} else if (!movil.equals(other.movil))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (premium != other.premium)
			return false;
		if (saludo == null) {
			if (other.saludo != null)
				return false;
		} else if (!saludo.equals(other.saludo))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	/*
	 * @Override public String toString() { return getClass().getSimpleName() +
	 * " [codigo=" + codigo + ", nombre=" + nombre + ", fechaNacimiento=" +
	 * fechaNacimiento + ", email=" + email + ", movil=" + movil + ", usuario=" +
	 * usuario + ", contraseña=" + contraseña + ", imagen=" + imagen + ", saludo=" +
	 * saludo + ", premium=" + premium + ", contactos=" + contactos + ", grupos=" +
	 * grupos + ", mensajes=" + mensajes + "]"; }
	 */

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [\n\t codigo=" + codigo + "\n\t nombre=" + nombre
				+ "\n\t fechaNacimiento=" + fechaNacimiento + "\n\t email=" + email + "\n\t movil=" + movil
				+ "\n\t usuario=" + usuario + "\n\t contraseña=" + contraseña + "\n\t imagen=" + imagen + "\n\t saludo="
				+ saludo + "\n\t premium=" + premium + "\n\t contactos=" + contactos + "\n\t grupos=" + grupos + "]";
	}

	public int getNumMensajes(TipoContacto tipoContacto) {
		int contador = 0;
		
		switch (tipoContacto) {
		case GRUPO:
			for (Contacto c : grupos) {
				for (Mensaje m : c.getMensajes()) {
					if (m.getTlfEmisor().equals(this.movil)) {
						contador++;
					}
				}
			}
			break;

		case INDIVIDUAL:
			for (Contacto c : contactos) {
				for (Mensaje m : c.getMensajes()) {
					if (m.getTlfEmisor().equals(this.movil)) {
						contador++;
					}
				}
			}
			break;
		}
		
		return contador;
	}
	
	public List<Integer> getNumMensajesPorMes(TipoContacto tipoContacto) {
		// Creamos una lista donde guardaremos los mensajes enviados en cada mes
		List<Integer> mensajesAnual = new ArrayList<Integer>(12);
		int mes;
		switch (tipoContacto) {
		case GRUPO:
			for (Contacto c : grupos) {
				for (Mensaje m : c.getMensajes()) {
					// Obtenemos la hora del mensaje y la pasamos de Date a LocalDate
					LocalDate horaM = m.getHora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if (m.getTlfEmisor().equals(this.movil)) {
						mes = horaM.getMonthValue();
						mensajesAnual.set(mes - 1, mensajesAnual.get(mes - 1) + 1);
					}
				}
			}
			break;

		case INDIVIDUAL:
			for (Contacto c : contactos) {
				for (Mensaje m : c.getMensajes()) {
					// Obtenemos la hora del mensaje y la pasamos de Date a LocalDate
					LocalDate horaM = m.getHora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if (m.getTlfEmisor().equals(this.movil)) {
						mes = horaM.getMonthValue();
						mensajesAnual.set(mes - 1, mensajesAnual.get(mes - 1) + 1);
					}
				}
			}
			break;
		}
		return mensajesAnual;
	}
	
	public List<Integer> getNumMensajesPorMes(int year, TipoContacto tipoContacto) {
		// Creamos una lista donde guardaremos los mensajes enviados en cada mes
		List<Integer> mensajesAnual = new ArrayList<Integer>(12);
		int mes;
		switch (tipoContacto) {
		case GRUPO:
			for (Contacto c : grupos) {
				for (Mensaje m : c.getMensajes()) {
					// Obtenemos la hora del mensaje y la pasamos de Date a LocalDate
					LocalDate horaM = m.getHora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if (m.getTlfEmisor().equals(this.movil) && horaM.getYear() == year) {
						mes = horaM.getMonthValue();
						mensajesAnual.set(mes - 1, mensajesAnual.get(mes - 1) + 1);
					}
				}
			}
			break;

		case INDIVIDUAL:
			for (Contacto c : contactos) {
				for (Mensaje m : c.getMensajes()) {
					// Obtenemos la hora del mensaje y la pasamos de Date a LocalDate
					LocalDate horaM = m.getHora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if (m.getTlfEmisor().equals(this.movil) && horaM.getYear() == year) {
						mes = horaM.getMonthValue();
						mensajesAnual.set(mes - 1, mensajesAnual.get(mes - 1) + 1);
					}
				}
			}
			break;
		}
		return mensajesAnual;
	}

	public Map<String, Integer> getNumMensajesEnviadosPorGrupo() {

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
