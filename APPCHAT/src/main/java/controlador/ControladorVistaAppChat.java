package controlador;

import java.util.Date;

import interfazGrafica.InterfazVistas;
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
	
	private ControladorVistaAppChat(){
		//Inicializar adaptadores
		inicializarAdaptadores();
		//inicializar catalogos
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
		//TODO registrar el usuario
		return false;
	}
	public void changeToRegister() {
		InterfazVistas antigua = interfaz;
		antigua.exit();
		interfaz = new Register(this);
	}
	
}
