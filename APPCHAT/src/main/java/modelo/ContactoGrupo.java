package modelo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// TODO ¿Añadir lista de mensajes?

public class ContactoGrupo extends Contacto {
	
	private Usuario admin;
	private Set<String> miembros;

	public ContactoGrupo(String nombre, String...miembros) {
		super(nombre);
		this.admin = null;
		this.miembros = new HashSet<String>();
		Collections.addAll(this.miembros, miembros);
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

}
