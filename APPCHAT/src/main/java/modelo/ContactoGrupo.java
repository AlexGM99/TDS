package modelo;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactoGrupo extends Contacto {

	private Usuario admin;
	private Set<String> miembros;

	public ContactoGrupo(String nombre, String... miembros) {
		super(nombre);
		this.admin = null;
		this.miembros = new HashSet<String>();
		Collections.addAll(this.miembros, miembros);
	}

	public ContactoGrupo(String nombre, List<String> miembros) {
		super(nombre);
		this.admin = null;
		this.miembros = new HashSet<String>(miembros);
	}
	
	public Usuario getAdmin() {
		return admin;
	}

	public void setAdmin(Usuario admin) {
		this.admin = admin;
	}

	public void addMiembro(String c) {
		miembros.add(c);
	}

	public void removeMiembro(String c) {
		miembros.remove(c);
	}

	public Set<String> getMiembros() {
		return miembros;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactoGrupo other = (ContactoGrupo) obj;
		if (admin == null) {
			if (other.admin != null)
				return false;
		} else if (!admin.equals(other.admin))
			return false;
		if (miembros == null) {
			if (other.miembros != null)
				return false;
		} else if (!miembros.equals(other.miembros))
			return false;
		return true;
	}

}
