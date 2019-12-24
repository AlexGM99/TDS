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

	@Override
	public String toString() {
		return super.toString() + "[movil=" + movil + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactoIndividual other = (ContactoIndividual) obj;
		if (movil == null) {
			if (other.movil != null)
				return false;
		} else if (!movil.equals(other.movil))
			return false;
		return true;
	}

}
