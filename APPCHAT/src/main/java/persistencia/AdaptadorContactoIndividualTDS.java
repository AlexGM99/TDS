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
import modelo.Mensaje;

public class AdaptadorContactoIndividualTDS implements IAdaptadorContactoIndividualDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoIndividualTDS unicaInstancia = null;
	
	private static final String NOMBRE_ENTIDAD = "contactoIndividual";
	private static final String NOMBRE_PROP_NOMBRE = "nombre";
	private static final String NOMBRE_PROP_MENSAJES = "mensajes";
	private static final String NOMBRE_PROP_MOVIL = "movil";
	
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
		if (existe)
			return;

		// registrar primero los atributos que son objetos
		// registrar mensajes
		AdaptadorMensajeTDS adaptadorM = AdaptadorMensajeTDS.getUnicaInstancia();
		for (Mensaje m : contacto.getMensajes()) {
			adaptadorM.registrarMensaje(m);
		}

		// crear entidad contactoIndividual
		eContactoInd = new Entidad();
		eContactoInd.setNombre(NOMBRE_ENTIDAD);
		eContactoInd
				.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad(NOMBRE_PROP_NOMBRE, contacto.getNombre()),
						new Propiedad(NOMBRE_PROP_MENSAJES, Auxiliar.obtenerCodigos(contacto.getMensajes())),
						new Propiedad(NOMBRE_PROP_MOVIL, contacto.getMovil()))));

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
		servPersistencia.eliminarPropiedadEntidad(eContactoInd, NOMBRE_PROP_NOMBRE);
		servPersistencia.anadirPropiedadEntidad(eContactoInd, NOMBRE_PROP_NOMBRE, contacto.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eContactoInd, NOMBRE_PROP_MOVIL);
		servPersistencia.anadirPropiedadEntidad(eContactoInd, NOMBRE_PROP_MOVIL, contacto.getMovil());

		String lineas = Auxiliar.obtenerCodigos(contacto.getMensajes());
		servPersistencia.eliminarPropiedadEntidad(eContactoInd, NOMBRE_PROP_MENSAJES);
		servPersistencia.anadirPropiedadEntidad(eContactoInd, NOMBRE_PROP_MENSAJES, lineas);

		if (PoolDAO.getUnicaInstancia().contiene(contacto.getCodigo()))
			PoolDAO.getUnicaInstancia().addObjeto(contacto.getCodigo(), contacto);
	}

	public ContactoIndividual recuperarContactoIndividual(int codigo) {
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (ContactoIndividual) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		// recuperar entidad
		Entidad eContactoInd = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		String nombre;
		String movil;
		nombre = servPersistencia.recuperarPropiedadEntidad(eContactoInd, NOMBRE_PROP_NOMBRE);
		movil = servPersistencia.recuperarPropiedadEntidad(eContactoInd, NOMBRE_PROP_MOVIL);

		ContactoIndividual contacto = new ContactoIndividual(nombre, movil);
		contacto.setCodigo(codigo);

		// IMPORTANTE: añadir el mensaje al pool antes de llamar a otros adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, contacto);

		// recuperar propiedades que son objetos llamando a adaptadores
		// mensajes
		List<Mensaje> mensajes = Auxiliar.obtenerMensajesDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eContactoInd, NOMBRE_PROP_MENSAJES));

		for (Mensaje mensaje : mensajes) {
			contacto.addMensaje(mensaje);
		}

		return contacto;
	}

	public List<ContactoIndividual> recuperarTodosContactoIndividuals() {
		List<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades(NOMBRE_ENTIDAD);

		for (Entidad eContactoInd : entidades) {
			contactos.add(recuperarContactoIndividual(eContactoInd.getId()));
		}
		return contactos;
	}

	// -------------------Funciones auxiliares-----------------------------
	/*
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
	*/
}
