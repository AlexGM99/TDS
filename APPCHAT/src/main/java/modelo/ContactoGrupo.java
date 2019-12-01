package modelo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ContactoGrupo extends Contacto {
	
	private Usuario admin;
	private List<ContactoIndividual> miembros;

	public ContactoGrupo(String nombre, Usuario admin) {
		super(nombre);
		this.admin = admin;
		this.miembros = new LinkedList<ContactoIndividual>();
		admin.addAdministrador(this);
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
	
	public List<ContactoIndividual> getMiembros() {
		return Collections.unmodifiableList(miembros);
	}

}
