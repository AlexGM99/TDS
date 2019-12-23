package controlador;

import java.util.Date;

import interfazGrafica.InterfazVistas;
import interfazGrafica.LogIn;
import interfazGrafica.Register;
import modelo.CatalogoUsuarios;
import modelo.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoGrupoDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class ControladorVistaAppChat {
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
		System.out.println(name + " " + pass);
		// TODO loguear el usuario
		return false;
	}

	public String RegisterUser(String nombre, Date fechanacimiento, String email, String movil, String usuario,
			String contraseña, String imagen, String saludo) {
		Usuario user;
		if (catalogoUsuarios.getUsuario(movil) != null)
			return "User already registered";
		
		if (saludo.equals(""))
			user = new Usuario(nombre, fechanacimiento, email, movil, usuario, contraseña, imagen);
		else
			user = new Usuario(nombre, fechanacimiento, email, movil, usuario, contraseña, imagen, saludo);
		
		adaptadorUsuario.registrarUsuario(user);
		catalogoUsuarios.addUsuario(user);
		
		return "U've been registered into the Dark Lord Army!!!!!!!!!";
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
}
