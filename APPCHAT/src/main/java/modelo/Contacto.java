package modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Contacto implements Comparable<Contacto> {

	private int codigo;
	private String nombre;
	private List<Mensaje> mensajes;
	
	public static String FECHAINI = "@fechainicio:(\\d{1,2}-\\d{1,2}-\\d{4})";
	public static String FECHAFIN = "@fechafin:(\\d{1,2}-\\d{1,2}-\\d{4})";
	public static String USUARIO = "@usuario:(\\S*)([ ]||$)";
	
	public static String REGEXCERRADA = "(" + FECHAINI +")?" +"(" + FECHAFIN +")?" +"(" + USUARIO +")?";
	
	protected Contacto(String nombre) {
		codigo = 0;
		this.nombre = nombre;
		this.mensajes = new LinkedList<Mensaje>();
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

	public void addMensaje(Mensaje mensaje) {
		mensajes.add(mensaje);
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public String getLastMessageText() {
		if (mensajes.isEmpty())
			return "";
		return mensajes.get(mensajes.size()-1).getTexto();
	}
	public Date getLastMessageDate() {
		if (mensajes.isEmpty())
			return null;
		return mensajes.get(mensajes.size()-1).getHora();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [codigo=" + codigo + ", nombre=" + nombre + ", mensajes=" + mensajes
				+ "]";
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
		Contacto other = (Contacto) obj;
		if (codigo != other.codigo)
			return false;
		if (mensajes == null) {
			if (other.mensajes != null)
				return false;
		} else if (!mensajes.equals(other.mensajes))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	 @Override
     public int compareTo(Contacto o) {
         if (getLastMessageDate().after(o.getLastMessageDate())) {
             return -1;
         }
         if (getLastMessageDate().before(o.getLastMessageDate())) {
             return 1;
         }
         return 0;
     }
	 
	 public List<Mensaje> getMensajeFiltrados(String tlfusuario, Date fini, Date ffin, String texto){
		 boolean usuario = false;
		 boolean inicio = false;
		 boolean ffinal = false;
		 boolean text = false;
		 if (tlfusuario != null && !tlfusuario.isEmpty())
			 usuario = true;
		 if (fini != null)
			 inicio=true;
		 if (ffin != null)
			 ffinal = true;
		 if (texto != null && !texto.isEmpty())
			 text = true;
		 List<Mensaje> mensajesfiltrados = new LinkedList<Mensaje>(this.mensajes);
		 if (usuario)
			 mensajesfiltrados = mensajesfiltrados.stream().filter(p -> p.getTlfEmisor().equals(tlfusuario)).collect(Collectors.toList());
		 if (inicio)
			 mensajesfiltrados = mensajesfiltrados.stream().filter(p->p.getHora().after(fini)).collect(Collectors.toList());
		 if (ffinal)
			 mensajesfiltrados = mensajesfiltrados.stream().filter(p->p.getHora().before(ffin)).collect(Collectors.toList());
		 if (text)
			 mensajesfiltrados = mensajesfiltrados.stream().filter(p->p.getTexto().toLowerCase().contains(texto.toLowerCase())).collect(Collectors.toList());
		 
		 return mensajesfiltrados;
	 }

}
