package modelo;

public abstract class Contacto {
	
	private int codigo;
	private String nombre;
	
	protected Contacto(String nombre) {
		codigo = 0;
		this.nombre = nombre;
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

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [codigo=" + codigo + ", nombre=" + nombre + "]";
	}

}
