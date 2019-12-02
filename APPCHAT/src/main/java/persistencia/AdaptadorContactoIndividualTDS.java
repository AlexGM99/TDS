package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.ContactoIndividual;

// Dependencia con 'Usuario' (contactos) y 'ContactoGrupo' (miembros)
// ¿Cuando se elimina un ContactoIndividual actualizar listas?

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

	/* cuando se registra un ContactoIndividual se le asigna un identificador unico */
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
		
		// crear entidad contactoIndividual
		eContactoInd = new Entidad();
		eContactoInd.setNombre("contactoIndividual");
		eContactoInd.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("nombre", contacto.getNombre()),
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

	public void modificarContactoIndividual(ContactoIndividual contacto) {
		Entidad eContactoInd;
		
		eContactoInd = servPersistencia.recuperarEntidad(contacto.getCodigo());
		servPersistencia.eliminarPropiedadEntidad(eContactoInd, "nombre");
		servPersistencia.anadirPropiedadEntidad(eContactoInd, "nombre", contacto.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eContactoInd, "movil");
		servPersistencia.anadirPropiedadEntidad(eContactoInd, "movil", contacto.getMovil());
	}

	public ContactoIndividual recuperarContactoIndividual(int codigo) {
		Entidad eContactoInd;
		String nombre;
		String movil;

		eContactoInd = servPersistencia.recuperarEntidad(codigo);
		nombre = servPersistencia.recuperarPropiedadEntidad(eContactoInd, "nombre");
		movil = servPersistencia.recuperarPropiedadEntidad(eContactoInd, "movil");

		ContactoIndividual contacto = new ContactoIndividual(nombre, movil);
		contacto.setCodigo(codigo);
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

}
