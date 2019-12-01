package modelo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// TODO ¿Añadir lista de mensajes?

public class ContactoGrupo extends Contacto {
	
	private Usuario admin;
	private Set<ContactoIndividual> miembros;

	public ContactoGrupo(String nombre, ContactoIndividual...miembros) {
		super(nombre);
		this.admin = null;
		this.miembros = new HashSet<ContactoIndividual>();
		Collections.addAll(this.miembros, miembros);
	}

	public Usuario getAdmin() {
		return admin;
	}

	public void setAdmin(Usuario admin) {
		this.admin = admin;
	}

	public void addMiembro(ContactoIndividual c) {
		miembros.add(c);
	}
	
	public void addMiembro(String nombre, String movil) {
		miembros.add(new ContactoIndividual(nombre, movil));
	}
	
	public void removeMiembro(ContactoIndividual c) {
		miembros.remove(c);
	}
	
	public Set<ContactoIndividual> getMiembros() {
		return Collections.unmodifiableSet(miembros);
	}

}
