package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.ContactoGrupo;
import modelo.Mensaje;
import modelo.Usuario;

public class AdaptadorContactoGrupoTDS implements IAdaptadorContactoGrupoDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoGrupoTDS unicaInstancia;
	
	private static final String NOMBRE_ENTIDAD = "contactoGrupo";
	private static final String NOMBRE_PROP_NOMBRE = "nombre";
	private static final String NOMBRE_PROP_MENSAJES = "mensajes";
	private static final String NOMBRE_PROP_ADMIN = "admin";
	private static final String NOMBRE_PROP_MIEMBROS = "miembros";
	
	

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
		if (existe)
			return;

		// registrar primero los atributos que son objetos
		
		// registrar mensajes
		AdaptadorMensajeTDS adaptadorM = AdaptadorMensajeTDS.getUnicaInstancia();
		for (Mensaje m : contacto.getMensajes()) {
			adaptadorM.registrarMensaje(m);
		}
		
		// crear entidad contactoGrupo
		eContactoGr = new Entidad();

		eContactoGr.setNombre(NOMBRE_ENTIDAD);
		eContactoGr.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(NOMBRE_PROP_NOMBRE, contacto.getNombre()),
				new Propiedad(NOMBRE_PROP_MENSAJES, Auxiliar.obtenerCodigos(contacto.getMensajes())),
				new Propiedad(NOMBRE_PROP_ADMIN, String.valueOf(contacto.getAdmin().getCodigo())),
				new Propiedad(NOMBRE_PROP_MIEMBROS, Auxiliar.obtenerCodigos(contacto.getMiembros())))));

		// registrar entidad contactoGrupo
		eContactoGr = servPersistencia.registrarEntidad(eContactoGr);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		contacto.setCodigo(eContactoGr.getId());
	}

	public void borrarContactoGrupo(ContactoGrupo contacto) {
		// No se comprueban restricciones de integridad
		Entidad eContactoGr;

		AdaptadorMensajeTDS adaptadorM = AdaptadorMensajeTDS.getUnicaInstancia();
		for (Mensaje m : contacto.getMensajes()) {
			adaptadorM.borrarMensaje(m);
		}
		
		eContactoGr = servPersistencia.recuperarEntidad(contacto.getCodigo());
		servPersistencia.borrarEntidad(eContactoGr);
	}

	public void actualizarContactoGrupo(ContactoGrupo contacto) {
		Entidad eContactoGr;

		eContactoGr = servPersistencia.recuperarEntidad(contacto.getCodigo());
		servPersistencia.eliminarPropiedadEntidad(eContactoGr, NOMBRE_PROP_NOMBRE);
		servPersistencia.anadirPropiedadEntidad(eContactoGr, NOMBRE_PROP_NOMBRE, contacto.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eContactoGr, NOMBRE_PROP_ADMIN);
		servPersistencia.anadirPropiedadEntidad(eContactoGr, NOMBRE_PROP_ADMIN, String.valueOf(contacto.getAdmin().getCodigo()));
		
		String lineas = Auxiliar.obtenerCodigos(contacto.getMensajes());
		servPersistencia.eliminarPropiedadEntidad(eContactoGr, NOMBRE_PROP_MENSAJES);
		servPersistencia.anadirPropiedadEntidad(eContactoGr, NOMBRE_PROP_MENSAJES, lineas);
		
		lineas = Auxiliar.obtenerCodigos(contacto.getMiembros());
		servPersistencia.eliminarPropiedadEntidad(eContactoGr, NOMBRE_PROP_MIEMBROS);
		servPersistencia.anadirPropiedadEntidad(eContactoGr, NOMBRE_PROP_MIEMBROS, lineas);
		
		if (PoolDAO.getUnicaInstancia().contiene(contacto.getCodigo()))
			PoolDAO.getUnicaInstancia().addObjeto(contacto.getCodigo(), contacto);
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
		String nombre = servPersistencia.recuperarPropiedadEntidad(eContactoGr, NOMBRE_PROP_NOMBRE);

		// miembrosList
		String lineas = servPersistencia.recuperarPropiedadEntidad(eContactoGr, NOMBRE_PROP_MIEMBROS);
		List<String> aux = Arrays.asList(lineas);
		String[] miembros = new String[aux.size()];
		for (int i = 0; i < miembros.length; i++) {
			miembros[i] = aux.get(i);
		}
		ContactoGrupo grupo = new ContactoGrupo(nombre, miembros);
		grupo.setCodigo(codigo);

		// IMPORTANTE:añadir el grupo al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, grupo);

		// recuperar propiedades que son objetos llamando a adaptadores
		// admin
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		int codigoUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContactoGr, NOMBRE_PROP_ADMIN));
		
		Usuario admin = adaptadorUsuario.recuperarUsuario(codigoUsuario);
		grupo.setAdmin(admin);

		// mensajes
		List<Mensaje> mensajes = Auxiliar.obtenerMensajesDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eContactoGr, NOMBRE_PROP_MENSAJES));
		
		for (Mensaje mensaje : mensajes) {
			grupo.addMensaje(mensaje);
		}
		
		// devolver el objeto
		return grupo;
	}

	public List<ContactoGrupo> recuperarTodosContactoGrupos() {
		List<ContactoGrupo> contactos = new LinkedList<ContactoGrupo>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades(NOMBRE_ENTIDAD);

		for (Entidad eContactoGr : entidades) {
			contactos.add(recuperarContactoGrupo(eContactoGr.getId()));
		}
		return contactos;
	}
	
	// -------------------Funciones auxiliares-----------------------------
//	private String obtenerCodigosMiembros(Set<String> miembros) {
//		String lineas = "";
//		for (String miembro : miembros) {
//			lineas += miembro + " ";
//		}
//		return lineas.trim();
//
//	}
//	
//	private String obtenerCodigosMensajes(List<Mensaje> mensajes) {
//		String lineas = "";
//		for (Mensaje mensaje : mensajes) {
//			lineas += mensaje.getCodigo() + " ";
//		}
//		return lineas.trim();
//
//	}
//	
//	private List<Mensaje> obtenerMensajesDesdeCodigos(String lineas) {
//
//		List<Mensaje> mensajes = new LinkedList<Mensaje>();
//		StringTokenizer strTok = new StringTokenizer(lineas, " ");
//		AdaptadorMensajeTDS adaptadorM = AdaptadorMensajeTDS.getUnicaInstancia();
//		while (strTok.hasMoreTokens()) {
//			mensajes.add((adaptadorM.recuperarMensaje(Integer.valueOf((String) strTok.nextElement()))));
//		}
//		return mensajes;
//	}
}
