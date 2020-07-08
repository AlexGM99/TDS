package controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cargadorMensajes.MensajeWhatsApp;
import cargadorMensajes.Plataforma;
import cargadorMensajes.SimpleTextParser;
import Descuentos.DescuentoCompuesto;
import Descuentos.DescuentoSimple;
import ViewModels.ViewModelDatosChat;
import interfazGrafica.ChatWindow;
import interfazGrafica.InterfazVistas;
import interfazGrafica.LogIn;
import interfazGrafica.Register;
import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;
import modelo.Mensaje;
import modelo.TipoContacto;
import modelo.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoGrupoDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class ControladorVistaAppChat{
	public static final String REGISTRO_CORRECTO = "U've been registered into the Dark Lord Army!!!!!!!!!";
	public static final String REGISTRO_NOMBRE_YA_USADO = "User already registered";
	
	private AuxRender rendericer;
	private static ControladorVistaAppChat unicaInstancia;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoIndividualDAO adaptadorContacto;
	private IAdaptadorContactoGrupoDAO adaptadorGrupo;
	private IAdaptadorMensajeDAO adaptadorMensaje;

	private CatalogoUsuarios catalogoUsuarios;

	private Usuario usuarioActual;

	private InterfazVistas interfaz;

	private ControladorDescuentos controladorDescuentos;
	
	// TERMINADO
	private ControladorVistaAppChat() {
		// Inicializar adaptadores
		inicializarAdaptadores();
		// inicializar catalogos
		inicializarCatalogos();
		
		rendericer = new AuxRender();
		
		controladorDescuentos = new ControladorDescuentos();
	}

	// TERMINADO
	public static ControladorVistaAppChat getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorVistaAppChat();
		return unicaInstancia;
	}

	// TERMINADO
	public void setInterface(InterfazVistas interfaz) {
		this.interfaz = interfaz;
	}

	// TERMINADO
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

	// TERMINADO
	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
	}

	// TERMINADO
	public boolean loginUser(String name, String pass) {
		// loguear el usuario
		if (catalogoUsuarios.logIn(name , pass) == CatalogoUsuarios.CODIGO_LOG_IN_OK)
			usuarioActual = catalogoUsuarios.getUsuario(name);
		else return false;
		// Cambiamos la interfaz
		changeToChatWindow();
		// obtener los datos de chats e inicializar la ventana
		ChatWindow chat = (ChatWindow) interfaz;
		LinkedList<Contacto> lista = (LinkedList<Contacto>)getContactos();
		chat.setChats(lista);
		return true;
	}

	// TERMINADO
	public List<ContactoIndividual> getContactosByCodigos(List<Integer> codes){
		List<ContactoIndividual> contactos = usuarioActual.getContactos();
		return contactos.stream().filter(p -> codes.contains(p.getCodigo())).collect(Collectors.toList());
	}
	// TERMINADO
	public String RegisterUser(String nombre, Date fechanacimiento, String email, String movil, String usuario,
			String contraseña, String imagen, String saludo) {
		Usuario user;
		if (catalogoUsuarios.getUsuario(movil) != null)
			return REGISTRO_NOMBRE_YA_USADO;
		
		if (saludo.equals(""))
			user = new Usuario(nombre, fechanacimiento, email, movil, usuario, contraseña, imagen);
		else
			user = new Usuario(nombre, fechanacimiento, email, movil, usuario, contraseña, imagen, saludo);
		usuarioActual = user;
		catalogoUsuarios.addUsuario(user);
		adaptadorUsuario.registrarUsuario(user);
		changeToChatWindow();
		return REGISTRO_CORRECTO;
	}
	
	// TERMINADO
	public boolean changePhoto(String ruta) {
		usuarioActual.setImagen(ruta);
		// actualizamos en la bbdd
		adaptadorUsuario.actualizarUsuario(usuarioActual);
		return true;
	}
	
	// TERMINADO
	public boolean changeGreeting(String greeting) {
		usuarioActual.setSaludo(greeting);
		adaptadorUsuario.actualizarUsuario(usuarioActual);
		return true;
	}
	// TERMINADO
	public String getGreeting() {
		return usuarioActual.getSaludo();
	}

	// TERMINADO
	public void changeToRegister() {
		InterfazVistas antigua = interfaz;
		interfaz = new Register(this);
		antigua.exit();
	}
	
	// TERMINADO
	public void changeToLogin() {
		InterfazVistas antigua = interfaz;
		interfaz = new LogIn(this);
		antigua.exit();
	}
	
	// TERMINADO
	public void changeToChatWindow()
	{
		InterfazVistas antigua = interfaz;
		interfaz = new ChatWindow(this, usuarioActual);
		antigua.exit();
	}
	
	// TERMINADO
	public boolean soypremium() {
		return usuarioActual.isPremium();
	}
	
	// TERMINADO
	public DescuentoSimple getMejorDescuento() {
		DescuentoCompuesto descuentos = controladorDescuentos.getDescuentosActuales();
		double max = 0.0;
		DescuentoSimple mejorDescuento = null;
		List<DescuentoSimple> simples = descuentos.getdescuento();
		for(DescuentoSimple s: simples) {
			if(Double.parseDouble(s.getCantidad())>max){
				{
					max = Double.parseDouble(s.getCantidad());
					mejorDescuento = s;
				}
			}
		}
		return mejorDescuento;
	}
	
	// TERMINADO
	public void vendoMiAlmaPorPremium() {
		usuarioActual.setPremium(true);
		adaptadorUsuario.actualizarUsuario(usuarioActual);
		catalogoUsuarios.addUsuario(usuarioActual);
	}
	
	// TERMINADO
	public String getImage(ContactoIndividual cont) {
		String tel = cont.getMovil();
		return catalogoUsuarios.getUsuario(tel).getImagen();
	}
	
	// TERMINADO
	public String getImage(int code) { 
		Usuario usu = catalogoUsuarios.getUsuario(code);
		if (usu != null)
			return usu.getImagen();
		return "GRUPO";
	}
	
	// TERMINADO
	public String getUserNick(ContactoIndividual cont) {
		String tel = cont.getMovil();
		return catalogoUsuarios.getUserName(tel);
	}
	
	// TERMINADO
	public String getUserNick(int code) {
		return catalogoUsuarios.getUserName(code);
	}
	
	// TERMINADO
	public int getCode(ContactoIndividual cont) {
		String tel = cont.getMovil();
		return catalogoUsuarios.getCodigo(tel);
	}
	
	// TERMINADO
	public boolean existeUsuario(String telefono) {
		if (telefono == null) return false;
		return catalogoUsuarios.existeUsuario(telefono);
	}
	
	// TERMINADO
	public List<Contacto> getContactos(){
		LinkedList<Contacto> contactos = new LinkedList<Contacto>();
		usuarioActual.getContactos().stream().
									forEach(cont -> contactos.add(cont));
		//TO DO poner los grupos
		usuarioActual.getGrupos().stream().forEach(cont -> contactos.add(cont));
		return contactos;
	}
	
	// TERMINADO
	public Usuario getUsuarioActual() {
		return this.usuarioActual;
	}
	
	// TERMINADO
	public List<ContactoIndividual> getContactoIndividuales(){
		LinkedList<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();
		usuarioActual.getContactos().stream().
									forEach(cont -> contactos.add(cont));
		return contactos;
	}
	
	// TODO incluir grupos
	public ViewModelDatosChat getDatos(int codigo) {
		
		return catalogoUsuarios.getDatosVentana(codigo, usuarioActual);
	}
	
	//TERMINADO
	public List<ContactoIndividual> setContactosFilter(List<Integer> contactos, String nombre) {
		LinkedList<ContactoIndividual> contactosI = new LinkedList<ContactoIndividual>(getContactosByCodigos(contactos));
		return contactosI.stream()
				.filter(cont -> contenido(cont.getNombre(), nombre))
				.collect(Collectors.toList());
	}
	// TERMINADO
	public boolean contenido(String contenedorB, String contenidoB) {
		int i = 0;
		if (contenidoB.length() > contenedorB.length()) return false;
		String contenedor = contenedorB.toLowerCase();
		String contenido = contenidoB.toLowerCase();
		for (; i< contenedor.length(); i++) {
			if (contenedor.startsWith(contenido, i)) {
				return true;
			}
		}
		return false;
	}
	
	// TERMINADO
	public ContactoIndividual registrarContacto(String usuario, String telefono) {
		ContactoIndividual cont = new ContactoIndividual(usuario, telefono);
		adaptadorContacto.registrarContactoIndividual(cont);
		usuarioActual.addContacto(cont);
		adaptadorUsuario.actualizarUsuario(usuarioActual);
		ChatWindow chat = (ChatWindow) interfaz;
		chat.addChat(cont);
		return cont;
	}
	
	// TERMINADO
	public void registrarMensaje(String texto, String emisor, Date hora, Contacto receptor, TipoContacto tipoReceptor) {
		Mensaje m = new Mensaje(texto, hora, emisor, receptor, tipoReceptor);
		receptor.addMensaje(m);
		adaptadorMensaje.registrarMensaje(m);
		if (tipoReceptor.equals(TipoContacto.GRUPO)) {
			adaptadorGrupo.actualizarContactoGrupo((ContactoGrupo) receptor);
		}
		else if (tipoReceptor.equals(TipoContacto.INDIVIDUAL)) {
			adaptadorContacto.actualizarContactoIndividual((ContactoIndividual) receptor);
		}
		
	}
		
	//TERMINADO
	public boolean crearGrupo(String nombre, List<Integer> contactos) {
		ContactoGrupo creado = usuarioActual.registrarGrupo(nombre, getContactosByCodigos(contactos));
		if (creado!=null) {
			adaptadorGrupo.registrarContactoGrupo(creado);
			adaptadorUsuario.actualizarUsuario(usuarioActual);
		}
		return creado != null;
	}
	
	public void enviarMensaje(String mensaje, int codigo) {
		//TODO coger el usuario que envio el mensaje y enviarlo
		
		
		
	}
	
	//TODO patron observer para recoger un mensaje del bbdd
	
	
	public List<Contacto> buscarChats(String text){
		return usuarioActual.RecuperarContactosFiltrados(text);
		
	}
	
	// TODO Funcion para buscar un mensaje en un chat normal
	public List<Mensaje> buscarMensajeContacto(String texto, LocalDate fecha1, LocalDate fecha2) {
		// Cualquiera de los parámetros puede ser opcional
		return null;
	}
	
	// TERMINADO
	public List<Integer> getInformacionUsoAnual() {
			// Calcular mensajes enviados a contactos y a grupos en el año actual
			List<Integer> numMensajesContactosYear = usuarioActual.getNumMensajesPorMes(LocalDate.now().getYear(), TipoContacto.INDIVIDUAL);
			List<Integer> numMensajesGruposYear = usuarioActual.getNumMensajesPorMes(LocalDate.now().getYear(), TipoContacto.GRUPO);
			List<Integer> numMensajesTotalYear = new ArrayList<Integer>(12);
			for (int i = 0; i < 12; i++) {
				numMensajesTotalYear.add(numMensajesContactosYear.get(i) + numMensajesGruposYear.get(i));
			}
			return numMensajesTotalYear;
		}
	
	// TERMINADO
	public Map<String, Double> getInformacionUsoGrupos() {
			// Obtener los 6 grupos con más mensajes enviados por el user
			Map<String, Integer> mensajesPorGrupo = usuarioActual.getNumMensajesEnviadosPorGrupo();
			Map<String, Integer> mensajesGruposMasEnviados = mensajesPorGrupo.entrySet().stream()
					.sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
					.limit(6)
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));	
			
			// Obtener el % que representan del total
			int numMensajesTotal = usuarioActual.getNumMensajes(TipoContacto.INDIVIDUAL) + usuarioActual.getNumMensajes(TipoContacto.GRUPO);
			
			Map<String, Double> porcentajesMensajesGrupos = new LinkedHashMap<String, Double>(mensajesGruposMasEnviados.size());
			for (String it : mensajesGruposMasEnviados.keySet()) {
				porcentajesMensajesGrupos.put(it, new Double(mensajesGruposMasEnviados.get(it)));
			}
			porcentajesMensajesGrupos.entrySet().stream()
				.forEach(e -> e.setValue(e.getValue() * 100 / numMensajesTotal));
			return porcentajesMensajesGrupos;
	}
	
	// TODO Cargador de mensajes
	public boolean cargarMensajes(String fich, String formatDateWhatsApp) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDateWhatsApp);
		Plataforma plataforma;
		if (formatDateWhatsApp.equals(SimpleTextParser.FORMAT_DATE_IOS))
			plataforma = Plataforma.IOS;
		else
			plataforma = Plataforma.ANDROID;
		
		List<MensajeWhatsApp> chat = null;
		try {
			chat = SimpleTextParser.parse(fich, formatDateWhatsApp, plataforma);
			// Comprobar si es un grupo o un chat individual
			TipoContacto tipoReceptor;
			Contacto receptor = null;
			Set<String> miembrosChat = new HashSet<String>();
			for (MensajeWhatsApp mensaje : chat) {
				miembrosChat.add(mensaje.getAutor());
			}
			// Comprobar cómo está guardado el nombre
			String uAct = "";
			if (miembrosChat.contains(usuarioActual.getNombre()))
				uAct = usuarioActual.getNombre();
			else if (miembrosChat.contains(usuarioActual.getMovil()))
				uAct = usuarioActual.getMovil();
			else if (miembrosChat.contains(usuarioActual.getUsuario()))
				uAct = usuarioActual.getUsuario();
			if (uAct.equals(""))
				return false;
			miembrosChat.remove(uAct);
			if (miembrosChat.size() != 1) {
				/*tipoReceptor = TipoContacto.GRUPO;
				List<>
				crearGrupo("Grupo importado" + chat.get(0).getFecha().toString(),)*/
				return false;
			}
			else {
				tipoReceptor = TipoContacto.INDIVIDUAL;
				// Comprobar si la otra persona está guardada como contacto
				List<String> aux = new LinkedList<String>(miembrosChat);
				String aux1 = aux.get(0);
				List<ContactoIndividual> contactos = usuarioActual.getContactos();
				Iterator<ContactoIndividual> it = contactos.iterator();
				boolean encontrado = false;
				while (it.hasNext() && ! encontrado) {
					if (it.next().getNombre().equals(aux1)) {
						receptor = it.next();
						encontrado = true;
					}
					it.next();
				}
				if (! encontrado) {
					receptor = registrarContacto(aux1, "imported" + aux1 + LocalDate.now().toString());
				}				
			}
			for (MensajeWhatsApp mensaje : chat) {
				// Registrar los mensajes
				Date fecha = java.sql.Timestamp.valueOf(mensaje.getFecha());
				registrarMensaje(mensaje.getTexto(), mensaje.getAutor(), fecha, receptor, tipoReceptor);
				System.out.println(">" + mensaje.getFecha().format(formatter) +
									" " + mensaje.getAutor() + " : " + mensaje.getTexto());
			}
		} catch (IOException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// TERMINADO
	public void cerrarSesion()
	{
		InterfazVistas antigua = interfaz;
		interfaz = new LogIn(this);
		antigua.exit();
	}
}
