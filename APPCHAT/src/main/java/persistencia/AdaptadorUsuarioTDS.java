package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Usuario;
import modelo.Venta;

//Usa un pool para evitar problemas doble referencia con ventas
public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;

	public static AdaptadorUsuarioTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorUsuarioTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorUsuarioTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia(); 
	}

	/* cuando se registra un usuario se le asigna un identificador único */
	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario;
		boolean existe = true; 
		
		// Si la entidad está registrada no la registra de nuevo
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;

		// registrar primero los atributos que son objetos
		AdaptadorContactoIndividualTDS adaptadorContactoIndividual = AdaptadorContactoIndividualTDS.getUnicaInstancia();
		for (Contacto c : usuario.getContactos())
			if (c instanceof ContactoIndividual)
				adaptadorContactoIndividual.registrarContactoIndividual(c);

		AdaptadorContactoGrupoTDS adaptadorContactoGrupo = AdaptadorContactoGrupoTDS.getUnicaInstancia();
		for (Contacto c : usuario.getContactos())
			if (c instanceof ContactoGrupo)
				adaptadorContactoGrupo.registrarContactoGrupo(c);

		// TODO POR AQUI
		// crear entidad Usuario
		eUsuario = new Entidad();
		eUsuario.setNombre("usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("dni", cliente.getDni()), new Propiedad("nombre", cliente.getNombre()),
						new Propiedad("ventas", obtenerCodigosVentas(cliente.getVentas())))));
		
		// registrar entidad cliente
		eCliente = servPersistencia.registrarEntidad(eCliente);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		cliente.setCodigo(eCliente.getId()); 
	}

	public void borrarCliente(Cliente cliente) {
		// No se comprueban restricciones de integridad con Venta
		Entidad eCliente = servPersistencia.recuperarEntidad(cliente.getCodigo());
		
		servPersistencia.borrarEntidad(eCliente);
	}

	public void modificarCliente(Cliente cliente) {

		Entidad eCliente = servPersistencia.recuperarEntidad(cliente.getCodigo());

		servPersistencia.eliminarPropiedadEntidad(eCliente, "dni");
		servPersistencia.anadirPropiedadEntidad(eCliente, "dni", cliente.getDni());
		servPersistencia.eliminarPropiedadEntidad(eCliente, "nombre");
		servPersistencia.anadirPropiedadEntidad(eCliente, "nombre", cliente.getNombre());

		String ventas = obtenerCodigosVentas(cliente.getVentas());
		servPersistencia.eliminarPropiedadEntidad(eCliente, "ventas");
		servPersistencia.anadirPropiedadEntidad(eCliente, "ventas", ventas);
	}

	public Cliente recuperarCliente(int codigo) {

		// Si la entidad est� en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Cliente) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		Entidad eCliente;
		List<Venta> ventas = new LinkedList<Venta>();
		String dni;
		String nombre;

		// recuperar entidad
		eCliente = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		dni = servPersistencia.recuperarPropiedadEntidad(eCliente, "dni");
		nombre = servPersistencia.recuperarPropiedadEntidad(eCliente, "nombre");

		Cliente cliente = new Cliente(dni, nombre);
		cliente.setCodigo(codigo);

		// IMPORTANTE:a�adir el cliente al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, cliente);

		// recuperar propiedades que son objetos llamando a adaptadores
		// ventas
		ventas = obtenerVentasDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eCliente, "ventas"));

		for (Venta v : ventas)
			cliente.addVenta(v);

		return cliente;
	}

	public List<Cliente> recuperarTodosClientes() {

		List<Entidad> eClientes = servPersistencia.recuperarEntidades("cliente");
		List<Cliente> clientes = new LinkedList<Cliente>();

		for (Entidad eCliente : eClientes) {
			clientes.add(recuperarCliente(eCliente.getId()));
		}
		return clientes;
	}

	// -------------------Funciones auxiliares-----------------------------
	private String obtenerCodigosVentas(List<Venta> listaVentas) {
		String aux = "";
		for (Venta v : listaVentas) {
			aux += v.getCodigo() + " ";
		}
		return aux.trim();
	}

	private List<Venta> obtenerVentasDesdeCodigos(String ventas) {

		List<Venta> listaVentas = new LinkedList<Venta>();
		StringTokenizer strTok = new StringTokenizer(ventas, " ");
		AdaptadorMensajeTDS adaptadorV = AdaptadorMensajeTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaVentas.add(adaptadorV.recuperarVenta(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaVentas;
	}
}
