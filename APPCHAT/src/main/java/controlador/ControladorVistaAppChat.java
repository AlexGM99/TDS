package controlador;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.eclipse.persistence.internal.jpa.parsing.LikeNode;
import org.eclipse.persistence.internal.queries.ListContainerPolicy;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;

import Descuentos.DescuentoCompuesto;
import Descuentos.DescuentoSimple;
import ViewModels.ViewModelDatosChat;
import ViewModels.ViewModelGrupo;
import interfazGrafica.ChatWindow;
import interfazGrafica.Crear_Grupo;
import interfazGrafica.Datos_Chat_Actual;
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
import persistencia.AdaptadorContactoGrupoTDS;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoGrupoDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class ControladorVistaAppChat{
	public static final String REGISTRO_CORRECTO = "U've been registered into the Dark Lord Army!!!!!!!!!";
	public static final String REGISTRO_NOMBRE_YA_USADO = "User already registered";
	
	
	public static final List<String> MESES_YEAR = Arrays.asList(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" });
	public static final Color[] GRAFICA_HISTOGRAMA_COLOR = new Color[] { new Color(243, 180, 159) };
	public static final String GRAFICA_HISTOGRAMA_PATH = "./Sample_Chart-H";
	public static final BitmapFormat GRAFICA_HISTOGRAMA_FORMATO = BitmapFormat.PNG;
	public static final Color[] GRAFICA_TARTA_COLORES = new Color[] { new Color(224, 68, 14), new Color(230, 105, 62), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182) };
	public static final String GRAFICA_TARTA_PATH = "./Sample_Chart-G";
	public static final BitmapFormat GRAFICA_TARTA_FORMATO = BitmapFormat.PNG;
	
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

	// TODO ordenar por fecha Mensaje y por nombre contacto
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
		ContactoIndividual cI = usuarioActual.getContactoI(code);
		if (cI!=null)
		{
			Usuario usu = catalogoUsuarios.getUsuario(cI.getMovil());
			if (usu != null)
				return usu.getImagen();
		}
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
		return cont.getCodigo();
		//String tel = cont.getMovil();
		//return catalogoUsuarios.getCodigo(tel);
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
	
	// TERMINADO
	public ViewModelDatosChat getDatos(int codigo) {
		if (usuarioActual.existContactoI(codigo))
			return catalogoUsuarios.getDatosVentana(codigo, usuarioActual);
		else if (usuarioActual.existContactoG(codigo))
			return catalogoUsuarios.getDatosVentanaGrupo(codigo, usuarioActual, unicaInstancia);
		return null;
	}
	
	public ViewModelGrupo getViewGrupo(int codigo) {
		ContactoGrupo g;
		if ( (g = usuarioActual.getContactoG(codigo)) != null) {
			List<ContactoIndividual> noGrupo = new LinkedList<ContactoIndividual>();
			List<ContactoIndividual> grupo = new LinkedList<ContactoIndividual>();
			return null; 
		}
		return null;
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
	public void registrarContacto(String usuario, String telefono) {
		ContactoIndividual cont = new ContactoIndividual(usuario, telefono);
		adaptadorContacto.registrarContactoIndividual(cont);
		usuarioActual.addContacto(cont);
		adaptadorUsuario.actualizarUsuario(usuarioActual);
		ChatWindow chat = (ChatWindow) interfaz;
		chat.addChat(cont);
	}
	
	//TERMINADO
	public boolean crearGrupo(String nombre, List<Integer> contactos) {
		ContactoGrupo creado = usuarioActual.registrarGrupo(nombre, getContactosByCodigos(contactos));
		if (creado!=null) {
			adaptadorGrupo.registrarContactoGrupo(creado);
			adaptadorUsuario.actualizarUsuario(usuarioActual);
			catalogoUsuarios.registrarGrupoEnUsuarios(creado);
			ChatWindow chat = (ChatWindow) interfaz;
			chat.addChat(creado);
		}
		return creado != null;
	}
	
	public boolean eliminarContacto(int codigo) {
		boolean borrado = false;
		ContactoGrupo g;
		if (usuarioActual.existContactoI(codigo))
		{
			 borrado = usuarioActual.DeleteContactoI(codigo);
		}
		else if ((g =usuarioActual.getContactoG(codigo)) != null){
			if (g.getAdmin().getCodigo() == usuarioActual.getCodigo()) {
				catalogoUsuarios.borrarGrupoUsers(g);
				borrado = usuarioActual.DeleteContactoG(codigo);
				adaptadorGrupo.borrarContactoGrupo(g);
			}
			else
				borrado = false;
		}
		else {
			borrado = false;
		}
		if (borrado) {
			adaptadorUsuario.actualizarUsuario(usuarioActual);
			ChatWindow chat = (ChatWindow) interfaz;
			LinkedList<Contacto> lista = (LinkedList<Contacto>)getContactos();
			chat.setChats(lista);
			}
		
		return borrado;
	}
	
	//TODO Actualizar vista de chat
	public void eliminarMensajes(int codigo) {
		ContactoGrupo g;
		ContactoIndividual c;
		if ((c =usuarioActual.getContactoI(codigo)) != null)
		{
			 c.setMensajes(new LinkedList<Mensaje>());
			 adaptadorContacto.actualizarContactoIndividual(c);
		}
		else if ((g =usuarioActual.getContactoG(codigo)) != null){
			g.setMensajes(new LinkedList<Mensaje>());
			adaptadorGrupo.actualizarContactoGrupo(g);
		}
	}
	
	public boolean isContactoInd(int codigo) {
		return (usuarioActual.existContactoI(codigo));
	}
	
	public boolean isContactoG(int codigo) {
		return (usuarioActual.existContactoG(codigo));
	}
	
	public boolean soyAdminG(int codigo) {
		return (usuarioActual.getContactoG(codigo).isAdmin(usuarioActual.getMovil()));
	}
	
	public String GetMovilI(int codigo) {
		return usuarioActual.getContactoI(codigo).getMovil();
	}
	
	public void actualizarContactoI(int codigo, String nick) {
		ContactoIndividual i = usuarioActual.modifyContactoI(codigo, nick);
		adaptadorContacto.actualizarContactoIndividual(i);
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
	
	public void informacionUso() {
		// Calcular mensajes enviados a contactos y a grupos en el año actual
		List<Integer> numMensajesContactosYear = usuarioActual.getNumMensajesPorMes(LocalDate.now().getYear(), TipoContacto.INDIVIDUAL);
		List<Integer> numMensajesGruposYear = usuarioActual.getNumMensajesPorMes(LocalDate.now().getYear(), TipoContacto.GRUPO);
		List<Integer> numMensajesTotalYear = new ArrayList<Integer>(12);
		for (int i = 0; i < 12; i++) {
			numMensajesTotalYear.set(i, numMensajesContactosYear.get(i) + numMensajesGruposYear.get(i));
		}
		// Crear histograma con los mensajes por mes del user
	    //CategoryChart graficaHistograma = new CategoryChartBuilder().width(800).height(600).title("Histograma de prueba").xAxisTitle("Mes").build();	 
	    CategoryChart graficaHistograma = new CategoryChartBuilder().xAxisTitle("Mes").build();	 
	    // Personalizar gráfico
	    graficaHistograma.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    graficaHistograma.getStyler().setHasAnnotations(true);
	    graficaHistograma.getStyler().setSeriesColors(GRAFICA_HISTOGRAMA_COLOR);
	    // Valores
	    graficaHistograma.addSeries("Mensajes enviados", MESES_YEAR, numMensajesTotalYear);	 
	    // Guardar	    
	    try {
	    	BitmapEncoder.saveBitmap(graficaHistograma, GRAFICA_HISTOGRAMA_PATH, GRAFICA_HISTOGRAMA_FORMATO);
		} catch (IOException e3) {
			// TODO Manejar excepción
			e3.printStackTrace();
		}
		
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
		
		// Crear diagrama de tarta con los 6 grupos
	    //PieChart graficaTarta = new PieChartBuilder().width(800).height(600).title("Tarta de prueba").build();
	    PieChart graficaTarta = new PieChartBuilder().build();
	    // Personalizar gráfico
	    graficaTarta.getStyler().setSeriesColors(GRAFICA_TARTA_COLORES);
	    graficaTarta.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    // Valores
	    for (String it : porcentajesMensajesGrupos.keySet()) {
	    	graficaTarta.addSeries(it, porcentajesMensajesGrupos.get(it));
		}
	    // Guardar	    
	    try {
			BitmapEncoder.saveBitmap(graficaTarta, GRAFICA_TARTA_PATH, GRAFICA_TARTA_FORMATO);
		} catch (IOException e3) {
			// TODO Manejar excepción
			e3.printStackTrace();
		}
	}
	
	// TERMINADO
	public void cerrarSesion()
	{
		InterfazVistas antigua = interfaz;
		interfaz = new LogIn(this);
		antigua.exit();
	}
}
