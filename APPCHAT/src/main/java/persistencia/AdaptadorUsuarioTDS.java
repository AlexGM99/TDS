package persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;

import modelo.Usuario;
import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;
import modelo.Mensaje;

public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	// Usa un pool para evitar problemas doble referencia con cliente

	private static ServicioPersistencia servPersistencia;

	private SimpleDateFormat dateFormat; // para formatear la fecha de usuario en
											// la base de datos

	private static AdaptadorUsuarioTDS unicaInstancia;

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

		eUsuario.setNombre("usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", usuario.getNombre()),
				new Propiedad("fechaNacimiento", dateFormat.format(usuario.getFechaNacimiento())),
				new Propiedad("email", usuario.getEmail()), new Propiedad("movil", usuario.getMovil()),
				new Propiedad("usuario", usuario.getUsuario()), new Propiedad("contraseña", usuario.getContraseña()),
				new Propiedad("imagen", usuario.getImagen()), new Propiedad("saludo", usuario.getSaludo()),
				new Propiedad("premium", String.valueOf(usuario.isPremium())),
				new Propiedad("contactos", obtenerCodigos(usuario.getContactos())),
				new Propiedad("grupos", obtenerCodigos(usuario.getGrupos())))));

		// registrar entidad usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eUsuario.getId());
	}

	public void borrarUsuario(Usuario usuario) {
		// No se comprueban restricciones de integridad
		Entidad eUsuario;

		AdaptadorContactoIndividualTDS adaptadorCI = AdaptadorContactoIndividualTDS.getUnicaInstancia();
		for (ContactoIndividual ci : usuario.getContactos()) {
			adaptadorCI.borrarContactoIndividual(ci);
		}

		eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		servPersistencia.borrarEntidad(eUsuario);

	}

	public void actualizarUsuario(Usuario usuario) {
		Entidad eUsuario;

		eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "nombre");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "nombre", usuario.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "fechaNacimiento");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "fechaNacimiento",
				dateFormat.format(usuario.getFechaNacimiento()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "email");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "email", usuario.getEmail());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "movil");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "movil", usuario.getMovil());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "usuario");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "usuario", usuario.getUsuario());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contraseña");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contraseña", usuario.getContraseña());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "imagen");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "imagen", usuario.getImagen());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "saludo");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "saludo", usuario.getSaludo());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "premium");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "premium", String.valueOf(usuario.isPremium()));

		String lineas = obtenerCodigos(usuario.getContactos());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contactos");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contactos", lineas);

		lineas = obtenerCodigos(usuario.getGrupos());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "grupos");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "grupos", lineas);

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
		// fecha
		String nombre, email, movil, usuario, contraseña, imagen, saludo;
		boolean premium;
		Date fecha = null;

		try {
			fecha = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		movil = servPersistencia.recuperarPropiedadEntidad(eUsuario, "movil");
		usuario = servPersistencia.recuperarPropiedadEntidad(eUsuario, "usuario");
		contraseña = servPersistencia.recuperarPropiedadEntidad(eUsuario, "contraseña");
		imagen = servPersistencia.recuperarPropiedadEntidad(eUsuario, "imagen");
		saludo = servPersistencia.recuperarPropiedadEntidad(eUsuario, "saludo");
		premium = Boolean.getBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, "premium"));

		Usuario usu = new Usuario(nombre, fecha, email, movil, usuario, contraseña, imagen, saludo, premium);
		usu.setCodigo(codigo);

		// IMPORTANTE:añadir la usuario al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, usu);

		// recuperar propiedades que son objetos llamando a adaptadores
		// contactos
		List<ContactoIndividual> contactos = obtenerContactosDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eUsuario, "contactos"));

		for (ContactoIndividual ci : contactos)
			usu.addContacto(ci);
		
		// grupos
		List<ContactoGrupo> grupos = obtenerGruposDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eUsuario, "grupos"));

		for (ContactoGrupo cg : grupos)
			usu.addGrupo(cg);

		// devolver el objeto usuario
		return usu;
	}

	public List<Usuario> recuperarTodosUsuarios() {
		List<Usuario> usuarios = new LinkedList<Usuario>();
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");

		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}

	// -------------------Funciones auxiliares-----------------------------
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

}
