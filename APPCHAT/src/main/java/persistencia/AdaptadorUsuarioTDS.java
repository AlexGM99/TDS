package persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;

import modelo.Usuario;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;

public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {

	private static ServicioPersistencia servPersistencia;

	private SimpleDateFormat dateFormat; // para formatear la fecha de usuario en
											// la base de datos

	private static AdaptadorUsuarioTDS unicaInstancia;

	private static final String NOMBRE_ENTIDAD = "usuario";
	private static final String NOMBRE_PROP_NOMBRE = "nombre";
	private static final String NOMBRE_PROP_FECHANACIMIENTO = "fechaNacimiento";
	private static final String NOMBRE_PROP_EMAIL = "email";
	private static final String NOMBRE_PROP_MOVIL = "movil";
	private static final String NOMBRE_PROP_USUARIO = "usuario";
	private static final String NOMBRE_PROP_CONTRASEÑA = "contraseña";
	private static final String NOMBRE_PROP_IMAGEN = "imagen";
	private static final String NOMBRE_PROP_SALUDO = "saludo";
	private static final String NOMBRE_PROP_PREMIUM = "premium";
	private static final String NOMBRE_PROP_CONTACTOS = "contactos";
	private static final String NOMBRE_PROP_GRUPOS = "grupos";	
	
	public static AdaptadorUsuarioTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorUsuarioTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}

	/* cuando se registra un usuario se le asigna un identificador unico */
	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario;
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true;
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe)
			return;

		// registrar primero los atributos que son objetos
		// registrar contactos
		AdaptadorContactoIndividualTDS adaptadorCI = AdaptadorContactoIndividualTDS.getUnicaInstancia();
		for (ContactoIndividual ci : usuario.getContactos())
			adaptadorCI.registrarContactoIndividual(ci);

		// registrar grupos
		AdaptadorContactoGrupoTDS adaptadorCG = AdaptadorContactoGrupoTDS.getUnicaInstancia();
		for (ContactoGrupo cg : usuario.getGrupos())
			adaptadorCG.registrarContactoGrupo(cg);

		// Crear entidad usuario
		eUsuario = new Entidad();

		eUsuario.setNombre(NOMBRE_ENTIDAD);
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(NOMBRE_PROP_NOMBRE, usuario.getNombre()),
				new Propiedad(NOMBRE_PROP_FECHANACIMIENTO, dateFormat.format(usuario.getFechaNacimiento())),
				new Propiedad(NOMBRE_PROP_EMAIL, usuario.getEmail()),
				new Propiedad(NOMBRE_PROP_MOVIL, usuario.getMovil()),
				new Propiedad(NOMBRE_PROP_USUARIO, usuario.getUsuario()),
				new Propiedad(NOMBRE_PROP_CONTRASEÑA, usuario.getContraseña()),
				new Propiedad(NOMBRE_PROP_IMAGEN, usuario.getImagen()),
				new Propiedad(NOMBRE_PROP_SALUDO, usuario.getSaludo()),
				new Propiedad(NOMBRE_PROP_PREMIUM, String.valueOf(usuario.isPremium())),
				new Propiedad(NOMBRE_PROP_CONTACTOS, Auxiliar.obtenerCodigos(usuario.getContactos())),
				new Propiedad(NOMBRE_PROP_GRUPOS, Auxiliar.obtenerCodigos(usuario.getGrupos())))));

		// registrar entidad usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eUsuario.getId());
	}

	public void borrarUsuario(Usuario usuario) {
		// No se comprueban restricciones de integridad
		Entidad eUsuario;

		/*AdaptadorContactoIndividualTDS adaptadorCI = AdaptadorContactoIndividualTDS.getUnicaInstancia();
		for (ContactoIndividual ci : usuario.getContactos()) {
			adaptadorCI.borrarContactoIndividual(ci);
		}*/

		eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		servPersistencia.borrarEntidad(eUsuario);

	}

	public void actualizarUsuario(Usuario usuario) {
		Entidad eUsuario;

		eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBRE_PROP_NOMBRE);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBRE_PROP_NOMBRE, usuario.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBRE_PROP_FECHANACIMIENTO);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBRE_PROP_FECHANACIMIENTO,
				dateFormat.format(usuario.getFechaNacimiento()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBRE_PROP_EMAIL);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBRE_PROP_EMAIL, usuario.getEmail());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBRE_PROP_MOVIL);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBRE_PROP_MOVIL, usuario.getMovil());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBRE_PROP_USUARIO);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBRE_PROP_USUARIO, usuario.getUsuario());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBRE_PROP_CONTRASEÑA);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBRE_PROP_CONTRASEÑA, usuario.getContraseña());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBRE_PROP_IMAGEN);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBRE_PROP_IMAGEN, usuario.getImagen());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBRE_PROP_SALUDO);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBRE_PROP_SALUDO, usuario.getSaludo());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBRE_PROP_PREMIUM);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBRE_PROP_PREMIUM, String.valueOf(usuario.isPremium()));

		String lineas = Auxiliar.obtenerCodigos(usuario.getContactos());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBRE_PROP_CONTACTOS);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBRE_PROP_CONTACTOS, lineas);

		lineas = Auxiliar.obtenerCodigos(usuario.getGrupos());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBRE_PROP_GRUPOS);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBRE_PROP_GRUPOS, lineas);

		if (PoolDAO.getUnicaInstancia().contiene(usuario.getCodigo()))
			PoolDAO.getUnicaInstancia().addObjeto(usuario.getCodigo(), usuario);
		
	}

	public Usuario recuperarUsuario(int codigo) {
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		// recuperar entidad
		Entidad eUsuario = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		String nombre, email, movil, usuario, contraseña, imagen, saludo;
		boolean premium;
		Date fecha = null;

		try {
			fecha = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE_PROP_FECHANACIMIENTO));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE_PROP_NOMBRE);
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE_PROP_EMAIL);
		movil = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE_PROP_MOVIL);
		usuario = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE_PROP_USUARIO);
		contraseña = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE_PROP_CONTRASEÑA);
		imagen = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE_PROP_IMAGEN);
		saludo = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE_PROP_SALUDO);
		premium = Boolean.valueOf(servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE_PROP_PREMIUM));

		Usuario usu = new Usuario(nombre, fecha, email, movil, usuario, contraseña, imagen, saludo, premium);
		usu.setCodigo(codigo);

		// IMPORTANTE:añadir la usuario al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, usu);

		// recuperar propiedades que son objetos llamando a adaptadores
		// contactos
		List<ContactoIndividual> contactos = Auxiliar.obtenerContactosDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE_PROP_CONTACTOS));

		for (ContactoIndividual ci : contactos)
			usu.addContacto(ci);
		
		// grupos
		List<ContactoGrupo> grupos = Auxiliar.obtenerGruposDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE_PROP_GRUPOS));

		for (ContactoGrupo cg : grupos)
			usu.addGrupo(cg);

		// devolver el objeto usuario
		return usu;
	}

	public List<Usuario> recuperarTodosUsuarios() {
		List<Usuario> usuarios = new LinkedList<Usuario>();
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades(NOMBRE_ENTIDAD);

		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}

	// -------------------Funciones auxiliares-----------------------------
	/*
	private <T> String obtenerCodigos(List<T> t) {
		String lineas = "";
		for (T it : t) {
			if (it instanceof Contacto)
				lineas += ((Contacto) it).getCodigo() + " ";
			else if (it instanceof Mensaje)
				lineas += ((Mensaje) it).getCodigo() + " ";
		}
		return lineas.trim();
	}

	private List<ContactoIndividual> obtenerContactosDesdeCodigos(String lineas) {

		List<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorContactoIndividualTDS adaptadorCI = AdaptadorContactoIndividualTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			contactos.add(adaptadorCI.recuperarContactoIndividual(Integer.valueOf((String) strTok.nextElement())));
		}
		return contactos;
	}

	private List<ContactoGrupo> obtenerGruposDesdeCodigos(String lineas) {

		List<ContactoGrupo> grupos = new LinkedList<ContactoGrupo>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorContactoGrupoTDS adaptadorCG = AdaptadorContactoGrupoTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			grupos.add(adaptadorCG.recuperarContactoGrupo(Integer.valueOf((String) strTok.nextElement())));
		}
		return grupos;
	}
	*/

}
