package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfazGrafica.Datos_Chat_Actual;
import persistencia.AdaptadorContactoGrupoTDS;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;

/* El catálogo mantiene los objetos en memoria, en una tabla hash
 * para mejorar el rendimiento. Esto no se podría hacer en una base de
 * datos con un número grande de objetos. En ese caso se consultaria
 * directamente la base de datos
 */
public class CatalogoUsuarios {
	private Map<String, Usuario> usuarios;
	private static CatalogoUsuarios unicaInstancia;

	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;

	public static final int CODIGO_USER_NOT_FOUND = 1;
	public static final int CODIGO_WRONG_PASSWORD = 2;
	public static final int CODIGO_LOG_IN_OK = 0;

	private CatalogoUsuarios() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorUsuario = dao.getUsuarioDAO();
			usuarios = new HashMap<String, Usuario>();
			this.cargarCatalogo();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}

	public static CatalogoUsuarios getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new CatalogoUsuarios();
		return unicaInstancia;
	}

	// devuelve todos los usuarios
	public List<Usuario> getUsuarios() {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		for (Usuario c : usuarios.values())
			lista.add(c);
		return Collections.unmodifiableList(lista);
	}

	public Usuario getUsuario(int codigo) {
		for (Usuario c : usuarios.values()) {
			if (c.getCodigo() == codigo)
				return c;
		}
		return null;
	}
	
	public String getUserName(int codigo) {
		Usuario u = getUsuario(codigo);
		return u != null?u.getUsuario():"";
	}

	public Usuario getUsuario(String movil) {
		return usuarios.get(movil);
	}
	
	public String getUserName(String movil) {
		return usuarios.get(movil).getUsuario();
	}
	
	public int getCodigo(String movil) {
		return usuarios.get(movil).getCodigo();
	}

	public void addUsuario(Usuario user) {
		usuarios.put(user.getMovil(), user);
	}

	public void removeUsuario(Usuario user) {
		usuarios.remove(user.getMovil());
	}

	public int logIn(String movil, String contraseña) {
		Usuario user = usuarios.get(movil);
		if (user == null)
			return CODIGO_USER_NOT_FOUND;
		if (user.getContraseña().equals(contraseña))
			return CODIGO_LOG_IN_OK;
		else
			return CODIGO_WRONG_PASSWORD;
	}

	/* Recupera todos los usuarios para trabajar con ellos en memoria */
	private void cargarCatalogo() throws DAOException {
		List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		for (Usuario usu : usuariosBD)
			usuarios.put(usu.getMovil(), usu);
	}
	
	// TODO wrapper de los datos de inicio y pasarlos a la vista
	
	public Datos_Chat_Actual getDatosVentana(int codigo) {
		Usuario u = getUsuario(codigo);
		return u.getMisDatosEnVentana();
	}
	
	public boolean existeUsuario(String telefono) {
		return usuarios.containsKey(telefono);
	}

}
