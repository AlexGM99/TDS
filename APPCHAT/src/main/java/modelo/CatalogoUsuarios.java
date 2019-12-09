package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private static CatalogoUsuarios unicaInstancia = new CatalogoUsuarios();

	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;

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

	public Usuario getUsuario(String movil) {
		return usuarios.get(movil);
	}

	public void addUsuario(Usuario cli) {
		usuarios.put(cli.getMovil(), cli);
	}

	public void removeUsuario(Usuario cli) {
		usuarios.remove(cli.getMovil());
	}

	/* Recupera todos los usuarios para trabajar con ellos en memoria */
	private void cargarCatalogo() throws DAOException {
		List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		for (Usuario usu : usuariosBD)
			usuarios.put(usu.getMovil(), usu);
	}

}