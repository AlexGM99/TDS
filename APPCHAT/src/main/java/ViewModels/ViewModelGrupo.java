package ViewModels;

import java.util.List;

import controlador.ControladorVistaAppChat;
import modelo.ContactoIndividual;


public class ViewModelGrupo implements ViewModelDatosChat {

	private List<ContactoIndividual> contactos;
	private ContactoIndividual admin;
	private String nombre;
	private ControladorVistaAppChat controlador;
	
	public ViewModelGrupo(List<ContactoIndividual> contactos, ContactoIndividual admin, String nombre, ControladorVistaAppChat controlador) {
		this.contactos = contactos;
		this.admin = admin;
		this.nombre = nombre;
		this.controlador = controlador;
	}
	
	public ContactoIndividual getAdmin() {
		return admin;
	}
	public List<ContactoIndividual> getContactos() {
		return contactos;
	}
	public String getNombre() {
		return nombre;
	}
	public ControladorVistaAppChat getControlador() {
		return controlador;
	}
	
}
