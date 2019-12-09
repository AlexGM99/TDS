package controlador;

import java.util.Date;
import java.util.List;

import modelo.CatalogoUsuarios;
import modelo.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoGrupoDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class ControladorAppChat {

	private static ControladorAppChat unicaInstancia;

	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoIndividualDAO adaptadorContacto;
	private IAdaptadorContactoGrupoDAO adaptadorGrupo;
	private IAdaptadorMensajeDAO adaptadorMensaje;

	private CatalogoUsuarios catalogoUsuarios;

	private Usuario usuarioActual;

	private ControladorAppChat() {
		inicializarAdaptadores(); // debe ser la primera linea para evitar error
									// de sincronización
		inicializarCatalogos();
	}

	public static ControladorAppChat getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorAppChat();
		return unicaInstancia;
	}

	public void registrarUsuario(String nombre, Date fechanacimiento, String email, String movil, String usuario, String contraseña, String imagen, String saludo, boolean premium) {
		// No se controla que existan dnis duplicados
		Usuario user = new Usuario(nombre, fechanacimiento, email, movil, usuario, contraseña, imagen, saludo, premium);
		adaptadorUsuario.registrarUsuario(user);
		catalogoUsuarios.addUsuario(user);
	}
	
	public void registrarUsuario(String nombre, Date fechanacimiento, String email, String movil, String usuario, String contraseña, String imagen, boolean premium) {
		// No se controla que existan dnis duplicados
		Usuario user = new Usuario(nombre, fechanacimiento, email, movil, usuario, contraseña, imagen, premium);
		adaptadorUsuario.registrarUsuario(user);
		catalogoUsuarios.addUsuario(user);
	}

	
	
	
	public void registrarProducto(double precio, String nombre, String descripcion) {
		// No se controla que el valor del string precio sea un double
		Producto producto = new Producto(precio, nombre, descripcion);
		adaptadorProducto.registrarProducto(producto);

		catalogoProductos.addProducto(producto);
	}

	public void crearVenta() {
		ventaActual = new Venta();
	}

	public void anadirLineaVenta(int unidades, Producto producto) {
		ventaActual.addLineaVenta(unidades, producto);
	}

	public void registrarVenta(String dni, Date fecha) {
		Cliente cliente = catalogoClientes.getCliente(dni);
		ventaActual.setCliente(cliente);
		ventaActual.setFecha(fecha);

		adaptadorVenta.registrarVenta(ventaActual);

		catalogoVentas.addVenta(ventaActual);

		cliente.addVenta(ventaActual);
		adaptadorCliente.modificarCliente(cliente);
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

	public boolean existeUsuario(String movil) {
		return CatalogoUsuarios.getUnicaInstancia().getUsuario(movil) != null;
	}

	public List<Producto> getProductos() {
		return catalogoProductos.getProductos();
	}
}
