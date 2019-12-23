package modelo;

import java.util.LinkedList;
import java.util.List;

public abstract class Contacto {
	
	private int codigo;
	private String nombre;
	private List<Mensaje> mensajes;
	
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

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [codigo=" + codigo + ", nombre=" + nombre + ", mensajes=" + mensajes + "]";
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
		return true;
	}

}
