package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ViewModels.ViewModelDatosChat;
import ViewModels.ViewModelGrupo;
import ViewModels.ViewModelUsuario;
import controlador.ControladorVistaAppChat;
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

	public Usuario getByMovil(String movil) {
		return usuarios.get(movil);
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

	public void registrarGrupoEnUsuarios(ContactoGrupo g) {
		g.getMiembros().stream()
			.filter(m -> existeUsuario(m))
			.forEach(m -> nuevoGrupoEnUser(getByMovil(m), g));
	}
	
	public void borrarGrupoUsers(ContactoGrupo g) {
		g.getMiembros().stream()
			.filter(m -> existeUsuario(m))
			.forEach(m->quitarGrupoUsers(getByMovil(m), g));
	}
	
	public void quitarGrupoUsers( Usuario u, ContactoGrupo g) {
		if (u.getContactoG(g.getCodigo())!=null) {
			u.DeleteContactoG(g.getCodigo());
			adaptadorUsuario.actualizarUsuario(u);
		}
	}
	
	public void deleteEnUsuarios(Set<String> nuevo, Set<String> antiguo, int g)
	{
		Set<String> s = new HashSet<String>(antiguo);
		s.removeAll(nuevo);
		s.stream().map(m -> getUsuario(m)).forEach(u -> u.DeleteContactoG(g));
		s.stream().map(m -> getUsuario(m)).forEach(u -> adaptadorUsuario.actualizarUsuario(u));
	}
	
	public void nuevoGrupoEnUser(Usuario u, ContactoGrupo g) {
		if (u.getContactoG(g.getCodigo())==null) {
			u.addGrupo(g);
			adaptadorUsuario.actualizarUsuario(u);
		}
	}
	
	/* Recupera todos los usuarios para trabajar con ellos en memoria */
	private void cargarCatalogo() throws DAOException {
		List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		for (Usuario usu : usuariosBD)
			usuarios.put(usu.getMovil(), usu);
	}
	
	// TODO wrapper de los datos de inicio y pasarlos a la vista
	
	public ViewModelDatosChat getDatosVentana(int codigo, Usuario usu) {
		ContactoIndividual u = usu.getContactoI(codigo);
		if (existeUsuario(u.getMovil())) {
			String nick = usu.getNombreContacto(u.getMovil());
			Usuario uI = getUsuario(u.getMovil());
			return new ViewModelUsuario(uI.getImagen()!=null?uI.getImagen():"", uI.getNombre(), u.getMovil(), uI.getSaludo(), nick);
		}
		else return new ViewModelUsuario("", u.getNombre(), u.getMovil(), "", u.getNombre());
	}
	public ViewModelDatosChat getDatosVentanaGrupo(int codigo, Usuario usu, ControladorVistaAppChat c) {
		ContactoGrupo u = usu.getContactoG(codigo);
		Usuario admin = u.getAdmin();
		List<ContactoIndividual> contactos = usu.getContactos(u.getMiembros());
		ContactoIndividual contAd = usu.getContactoODefault(admin);
		return new ViewModelGrupo(contactos, contAd, u.getNombre(), c);
	}
	
	public ViewModelGrupo getDatosVentanaGrupo(List<String> contactosMovil) {
		return null;
		/*List<ContactoIndividual> contactos = usu.getContactos(u.getMiembros());
		ContactoIndividual contAd = usu.getContactoODefault(admin);
		return new ViewModelGrupo(contactos, contAd, u.getNombre(), c);*/
	}
	
	
	public List<ContactoIndividual> getContactosAunqueNoExistenEnUsuario(Usuario u, Set<String> moviles){
		List<ContactoIndividual> c = new LinkedList<ContactoIndividual>();
		c = u.getContactos().stream().filter(p -> moviles.contains(p.getMovil())).collect(Collectors.toList());
		List<String> sinAsignar = new LinkedList<String>();
		c.stream().filter(p->!moviles.contains(p.getMovil())).forEach(p -> sinAsignar.add(p.getMovil()));
		for (String string : sinAsignar) {
			if (existeUsuario(string))
			{
				Usuario s = getByMovil(string);
				c.add(new ContactoIndividual(s.getNombre(), s.getMovil()));
			}
		}
		return c;
	}
	
	public List<Usuario> enviarMensajeAcontactos(int codigo, Usuario u) {
		List<Usuario> receptores = new LinkedList<Usuario>();
		if (u.existContactoI(codigo) && existeUsuario(u.getContactoI(codigo).getMovil())) {
			receptores.add(getByMovil(u.getContactoI(codigo).getMovil()));
			return receptores;
		} else if (u.existContactoG(codigo)) {
			receptores = 
					u.getContactoG(codigo).getMiembros().stream().
					filter( movil -> existeUsuario(movil) )
					.map( c -> getByMovil(c))
					.collect(Collectors.toList());
			return receptores;
		}
		return receptores;
	}
	
	public boolean existeUsuario(String telefono) {
		return usuarios.containsKey(telefono);
	}

}
