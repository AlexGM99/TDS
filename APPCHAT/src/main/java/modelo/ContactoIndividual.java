package modelo;

public class ContactoIndividual extends Contacto {
	
	private String movil;

	public ContactoIndividual(String nombre, String movil) {
		super(nombre);
		this.movil = movil;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

}
