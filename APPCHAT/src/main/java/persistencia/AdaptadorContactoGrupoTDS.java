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
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;
import modelo.Usuario;

public class AdaptadorContactoGrupoTDS implements IAdaptadorContactoGrupoDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoGrupoTDS unicaInstancia;

	public static AdaptadorContactoGrupoTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorContactoGrupoTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorContactoGrupoTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un ContactoGrupo se le asigna un identificador unico */
	public void registrarContactoGrupo(ContactoGrupo contacto) {
		Entidad eContactoGr;
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eContactoGr = servPersistencia.recuperarEntidad(contacto.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		// registrar primero los atributos que son objetos
		// registrar miembros
		AdaptadorContactoIndividualTDS adaptadorCI = AdaptadorContactoIndividualTDS.getUnicaInstancia();
		for (ContactoIndividual c : contacto.getMiembros())
			adaptadorCI.registrarContactoIndividual(c);
		// registrar admin
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(contacto.getAdmin());
		// registrar cliente
		
		// crear entidad contactoGrupo
		eContactoGr = new Entidad();
		
		eContactoGr.setNombre("contactoGrupo");
		eContactoGr.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("nombre", contacto.getNombre()),
				new Propiedad("admin", String.valueOf(contacto.getAdmin().getCodigo())),
				new Propiedad("miembros", obtenerCodigosMiembros(contacto.getMiembros())))));
		
		// registrar entidad contactoGrupo
		eContactoGr = servPersistencia.registrarEntidad(eContactoGr);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		contacto.setCodigo(eContactoGr.getId());  
	}

	public void borrarContactoGrupo(ContactoGrupo contacto) {
		// No se comprueban restricciones de integridad
		Entidad eContactoGr;
		AdaptadorContactoIndividualTDS adaptadorCI = AdaptadorContactoIndividualTDS.getUnicaInstancia();
		
		for (ContactoIndividual contactoInd : contacto.getMiembros())
			adaptadorCI.borrarContactoIndividual(contactoInd);
		
		eContactoGr = servPersistencia.recuperarEntidad(contacto.getCodigo());
		servPersistencia.borrarEntidad(eContactoGr);
	}

	public void modificarContactoGrupo(ContactoGrupo contacto) {
		Entidad eContactoGr; 
		
		eContactoGr = servPersistencia.recuperarEntidad(contacto.getCodigo());
		servPersistencia.eliminarPropiedadEntidad(eContactoGr, "nombre");
		servPersistencia.anadirPropiedadEntidad(eContactoGr, "nombre", contacto.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eContactoGr, "admin");
		servPersistencia.anadirPropiedadEntidad(eContactoGr, "admin", String.valueOf(contacto.getAdmin().getCodigo()));
		
		String lineas = obtenerCodigosMiembros(contacto.getMiembros());
		servPersistencia.eliminarPropiedadEntidad(eContactoGr, "miembros");
		servPersistencia.anadirPropiedadEntidad(eContactoGr, "miembros", lineas);
	}

	public ContactoGrupo recuperarContactoGrupo(int codigo) {
		
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (ContactoGrupo) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		// recuperar entidad
		Entidad eContactoGr = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		// nombre
		String nombre = servPersistencia.recuperarPropiedadEntidad(eContactoGr, "nombre");
		
		// TODO ¿Está bien inicializar con admin a 'null'?
		ContactoGrupo grupo = new ContactoGrupo(nombre, null);
		grupo.setCodigo(codigo);

		// IMPORTANTE:añadir el grupo al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, grupo);

		// recuperar propiedades que son objetos llamando a adaptadores
		// admin
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		int codigoUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContactoGr, "admin"));
		
		Usuario admin  = adaptadorUsuario.recuperarUsuario(codigoUsuario);
		grupo.setAdmin(admin);
		// miembros
		List<ContactoIndividual> miembros = obtenerContactosIndividualesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eContactoGr, "miembros"));

		for (ContactoIndividual ci : miembros)
			grupo.addMiembro(ci);
		
		// devolver el objeto venta
		return grupo;
	}

	public List<ContactoGrupo> recuperarTodosContactoGrupos(){
		List<ContactoGrupo> contactos = new LinkedList<ContactoGrupo>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("contactoGrupo");

		for (Entidad eContactoGr : entidades) {
			contactos.add(recuperarContactoGrupo(eContactoGr.getId()));
		}
		return contactos;
	}
	
	// -------------------Funciones auxiliares-----------------------------
	private String obtenerCodigosMiembros(List<ContactoIndividual> miembros) {
		String lineas = "";
		for (ContactoIndividual miembro : miembros) {
			lineas += miembro.getCodigo() + " ";
		}
		return lineas.trim();

	}
	
	private List<ContactoIndividual> obtenerContactosIndividualesDesdeCodigos(String lineas) {

		List<ContactoIndividual> miembros = new LinkedList<ContactoIndividual>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorContactoIndividualTDS adaptadorCI = AdaptadorContactoIndividualTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			miembros.add(adaptadorCI.recuperarContactoIndividual(Integer.valueOf((String) strTok.nextElement())));
		}
		return miembros;
	}

}
