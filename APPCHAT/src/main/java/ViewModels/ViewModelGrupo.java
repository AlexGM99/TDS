package ViewModels;

import java.util.List;

import controlador.ControladorVistaAppChat;
import modelo.ContactoIndividual;


public class ViewModelGrupo implements ViewModelDatosChat {

	private List<ContactoIndividual> contactos;
	private ContactoIndividual admin;
	private String nombre;
	private ControladorVistaAppChat controlador;
	private List<ContactoIndividual> seleccionados;
	
	public ViewModelGrupo(List<ContactoIndividual> contactos, ContactoIndividual admin, String nombre, ControladorVistaAppChat controlador) {
		this.contactos = contactos;
		this.admin = admin;
		this.nombre = nombre;
		this.controlador = controlador;
	}
	
	public ViewModelGrupo(List<ContactoIndividual> contactos, ContactoIndividual admin, String nombre, ControladorVistaAppChat controlador, List<ContactoIndividual> seleccionados) {
		this.contactos = contactos;
		this.admin = admin;
		this.nombre = nombre;
		this.controlador = controlador;
		this.seleccionados = seleccionados;
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
	public List<ContactoIndividual> getSeleccionados() {
		return seleccionados;
	}
}
