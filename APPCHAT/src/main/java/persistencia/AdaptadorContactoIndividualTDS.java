package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.ContactoIndividual;
import modelo.Mensaje;

public class AdaptadorContactoIndividualTDS implements IAdaptadorContactoIndividualDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoIndividualTDS unicaInstancia = null;

	public static AdaptadorContactoIndividualTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorContactoIndividualTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorContactoIndividualTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public void registrarContactoIndividual(ContactoIndividual contacto) {
		Entidad eContactoInd;
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eContactoInd = servPersistencia.recuperarEntidad(contacto.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		// registrar primero los atributos que son objetos
		// registrar mensajes
		AdaptadorMensajeTDS adaptadorM = AdaptadorMensajeTDS.getUnicaInstancia();
		for (Mensaje m : contacto.getMensajes()) {
			adaptadorM.registrarMensaje(m);
		}
		
		// crear entidad contactoIndividual
		eContactoInd = new Entidad();
		eContactoInd.setNombre("contactoIndividual");
		eContactoInd.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("nombre", contacto.getNombre()),
				new Propiedad("mensajes", obtenerCodigosMensajes(contacto.getMensajes())),
				new Propiedad("movil", contacto.getMovil()))));
		
		// registrar entidad contactoIndividual
		eContactoInd = servPersistencia.registrarEntidad(eContactoInd);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		contacto.setCodigo(eContactoInd.getId());  
	}

	public void borrarContactoIndividual(ContactoIndividual contacto) {
		// No se comprueba integridad
		Entidad eContactoInd = servPersistencia.recuperarEntidad(contacto.getCodigo());
		servPersistencia.borrarEntidad(eContactoInd);
	}

	public void actualizarContactoIndividual(ContactoIndividual contacto) {
		Entidad eContactoInd;
		try {
			eContactoInd = servPersistencia.recuperarEntidad(contacto.getCodigo());
		} catch (NullPointerException e) {
			System.err.println("ERROR: No se puede modificar un 'ContactoIndividual' no registrado");
			return;
		}
		servPersistencia.eliminarPropiedadEntidad(eContactoInd, "nombre");
		servPersistencia.anadirPropiedadEntidad(eContactoInd, "nombre", contacto.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eContactoInd, "movil");
		servPersistencia.anadirPropiedadEntidad(eContactoInd, "movil", contacto.getMovil());
		
		String lineas = obtenerCodigosMensajes(contacto.getMensajes());
		servPersistencia.eliminarPropiedadEntidad(eContactoInd, "mensajes");
		servPersistencia.anadirPropiedadEntidad(eContactoInd, "mensajes", lineas);
	}

	public ContactoIndividual recuperarContactoIndividual(int codigo) {
		Entidad eContactoInd;
		String nombre;
		String movil;

		try {
			eContactoInd = servPersistencia.recuperarEntidad(codigo);
		} catch (NullPointerException e) {
			System.err.println("ERROR: No se puede recuperar un 'ContactoIndividual' no registrado");
			return null;
		}
		nombre = servPersistencia.recuperarPropiedadEntidad(eContactoInd, "nombre");
		movil = servPersistencia.recuperarPropiedadEntidad(eContactoInd, "movil");

		ContactoIndividual contacto = new ContactoIndividual(nombre, movil);
		contacto.setCodigo(codigo);
		
		// recuperar propiedades que son objetos llamando a adaptadores
		// mensajes
		List<Mensaje> mensajes = obtenerMensajesDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eContactoInd, "mensajes"));
		
		for (Mensaje mensaje : mensajes) {
			contacto.addMensaje(mensaje);
		}
		
		return contacto;
	}

	public List<ContactoIndividual> recuperarTodosContactoIndividuals(){
		List<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("contactoIndividual");

		for (Entidad eContactoInd : entidades) {
			contactos.add(recuperarContactoIndividual(eContactoInd.getId()));
		}
		return contactos;
	}
	
	// -------------------Funciones auxiliares-----------------------------	
	private String obtenerCodigosMensajes(List<Mensaje> mensajes) {
		String lineas = "";
		for (Mensaje mensaje : mensajes) {
			lineas += mensaje.getCodigo() + " ";
		}
		return lineas.trim();

	}
	
	private List<Mensaje> obtenerMensajesDesdeCodigos(String lineas) {

		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorMensajeTDS adaptadorM = AdaptadorMensajeTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			mensajes.add((adaptadorM.recuperarMensaje(Integer.valueOf((String) strTok.nextElement()))));
		}
		return mensajes;
	}

}
