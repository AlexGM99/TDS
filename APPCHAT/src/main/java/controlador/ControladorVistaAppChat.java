package controlador;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import interfazGrafica.ChatWindow;
import interfazGrafica.InterfazVistas;
import interfazGrafica.LogIn;
import interfazGrafica.Register;
import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.Mensaje;
import modelo.TipoContacto;
import modelo.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoGrupoDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class ControladorVistaAppChat {
	public static final String REGISTRO_CORRECTO = "U've been registered into the Dark Lord Army!!!!!!!!!";
	public static final String REGISTRO_NOMBRE_YA_USADO = "User already registered";
	
	private static ControladorVistaAppChat unicaInstancia;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoIndividualDAO adaptadorContacto;
	private IAdaptadorContactoGrupoDAO adaptadorGrupo;
	private IAdaptadorMensajeDAO adaptadorMensaje;

	private CatalogoUsuarios catalogoUsuarios;

	private Usuario usuarioActual;

	private InterfazVistas interfaz;

	private ControladorVistaAppChat() {
		// Inicializar adaptadores
		inicializarAdaptadores();
		// inicializar catalogos
		inicializarCatalogos();
	}

	public static ControladorVistaAppChat getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorVistaAppChat();
		return unicaInstancia;
	}

	public void setInterface(InterfazVistas interfaz) {
		this.interfaz = interfaz;
	}

	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorContacto = factoria.getContactoIndividualDAO();
		adaptadorGrupo = factoria.getGrupoDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
	}

	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
	}

	public boolean loginUser(String name, String pass) {
		// TO DO loguear el usuario
		if (catalogoUsuarios.logIn(name , pass) == CatalogoUsuarios.CODIGO_LOG_IN_OK)
			usuarioActual = catalogoUsuarios.getUsuario(name);
		else return false;
		// Cambiamos la interfaz
		changeToChatWindow();
		// TODO obtener los datos de chats e inicializar la ventana
		
		return true;
	}

	public String RegisterUser(String nombre, Date fechanacimiento, String email, String movil, String usuario,
			String contraseña, String imagen, String saludo) {
		Usuario user;
		if (catalogoUsuarios.getUsuario(movil) != null)
			return REGISTRO_NOMBRE_YA_USADO;
		
		if (saludo.equals(""))
			user = new Usuario(nombre, fechanacimiento, email, movil, usuario, contraseña, imagen);
		else
			user = new Usuario(nombre, fechanacimiento, email, movil, usuario, contraseña, imagen, saludo);
		
		catalogoUsuarios.addUsuario(user);
		adaptadorUsuario.registrarUsuario(user);
		usuarioActual = user;
		changeToChatWindow();
		return REGISTRO_CORRECTO;
	}
	
	public boolean changePhoto(String ruta) {
		usuarioActual.setImagen(ruta);
		//TO DO cambiar en la bbdd la imagen
		adaptadorUsuario.actualizarUsuario(usuarioActual);
		return true;
	}
	
	public boolean changeGreeting(String greeting) {
		usuarioActual.setSaludo(greeting);
		adaptadorUsuario.actualizarUsuario(usuarioActual);
		return true;
	}
	public String getGreeting() {
		return usuarioActual.getSaludo();
	}

	public void changeToRegister() {
		InterfazVistas antigua = interfaz;
		interfaz = new Register(this);
		antigua.exit();
	}

	public void changeToLogin() {
		InterfazVistas antigua = interfaz;
		interfaz = new LogIn(this);
		antigua.exit();
	}
	
	public void changeToChatWindow()
	{
		InterfazVistas antigua = interfaz;
		interfaz = new ChatWindow(this, usuarioActual);
		antigua.exit();
	}
	// TODO Funcion para buscar mensajes en un grupo
	public List<Mensaje> buscarMensajeGrupo(String usuario, String texto, LocalDate fecha1, LocalDate fecha2) {
		// Cualquiera de los parámetros puede ser opcional
		return null;
	}
	
	// TODO Funcion para buscar un mensaje en un chat normal
	public List<Mensaje> buscarMensajeContacto(String texto, LocalDate fecha1, LocalDate fecha2) {
		// Cualquiera de los parámetros puede ser opcional
		return null;
	}
	
	public void informacionUso() {
		// TODO Mensajes enviados a contactos y a grupos
		List<Integer> mensajesGrupos = usuarioActual.getMensajesPorMes(LocalDate.now().getYear(), TipoContacto.GRUPO);
		
		// TODO Obtener los 6 grupos con más mensajes enviados por el user
		Map<String, Integer> mensajesPorGrupo = usuarioActual.getNumMensajesEnviadosPorGrupo();
		// Obtener el % que representan del total
		
	}
	
	
}
