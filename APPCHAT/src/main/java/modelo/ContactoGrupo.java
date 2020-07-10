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
	
	public ContactoGrupo(ContactoGrupo g) {
		super(g.getNombre());
		this.admin = g.admin;
		this.miembros = g.miembros;
		g.setCodigo(g.getCodigo());
		this.setMensajes(g.getMensajes());
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
	
	public boolean isAdmin(String movil) {
		return admin.getMovil().equals(movil);
	}
	@Override
	public void setNombre(String nombre) {
		super.setNombre(nombre);
	}
	@Override
	public void setMensajes(List<Mensaje> mensajes) {
		super.setMensajes(mensajes);
	}
	public void setMiembros(Set<String> miembros) {
		this.miembros = miembros;
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
