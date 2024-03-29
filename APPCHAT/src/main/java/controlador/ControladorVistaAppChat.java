package controlador;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import cargadorMensajes.CargadorMensajes;
import cargadorMensajes.IMensajesListener;
import cargadorMensajes.MensajeWhatsApp;
import cargadorMensajes.MensajesEvent;
import cargadorMensajes.Plataforma;
import cargadorMensajes.SimpleTextParser;
import Descuentos.DescuentoCompuesto;
import Descuentos.DescuentoSimple;
import Helpers.AuxRender;
import Helpers.KeyValue;
import Helpers.OrdenarContactoPorNombre;
import ViewModels.ViewModelDatosChat;
import ViewModels.ViewModelGrupo;
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
import tds.BubbleText;

public class ControladorVistaAppChat implements IMensajesListener {
	
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
	
	private CargadorMensajes cargador;
	
	public KeyValue<Integer, List<Mensaje>> mensajes;
	 
	// TERMINADO
	private ControladorVistaAppChat() {
		// Inicializar adaptadores
		inicializarAdaptadores();
		// inicializar catalogos
		inicializarCatalogos();
		
		cargador = new CargadorMensajes();
		cargador.addMensajesListener(this);
		
		rendericer = new AuxRender();

		controladorDescuentos = new ControladorDescuentos();
	}

	// Singleton
	public static ControladorVistaAppChat getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorVistaAppChat();
		return unicaInstancia;
	}

	// actualiza la ventana actual
	public void setInterface(InterfazVistas interfaz) {
		this.interfaz = interfaz;
	}

	// incializa los adaptadores de la persistencia
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

	// incializa el catalogo
	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
	}

	public boolean loginUser(String name, String pass) {
		// loguear el usuario
		if (catalogoUsuarios.logIn(name, pass) == CatalogoUsuarios.CODIGO_LOG_IN_OK)
			usuarioActual = catalogoUsuarios.getUsuario(name);
		else
			return false;
		// Cambiamos la interfaz
		changeToChatWindow();
		// obtener los datos de chats e inicializar la ventana
		ChatWindow chat = (ChatWindow) interfaz;
		LinkedList<Contacto> lista = (LinkedList<Contacto>) getContactos();
		chat.setChats(lista);
		return true;
	}

	// Obtiene los contactos del usuario actual mediante los codigos
	public List<ContactoIndividual> getContactosByCodigos(List<Integer> codes) {
		List<ContactoIndividual> contactos = usuarioActual.getContactos();
		return contactos.stream().filter(p -> codes.contains(p.getCodigo())).collect(Collectors.toList());
	}

	// registrar un nuevo usuario
	public String RegisterUser(String nombre, Date fechanacimiento, String email, String movil, String usuario,
			String contraseña, String imagen, String saludo) {
		Usuario user;
		if (catalogoUsuarios.getUsuario(movil) != null)
			return REGISTRO_NOMBRE_YA_USADO;

		if (saludo.equals(""))
			user = new Usuario(nombre, fechanacimiento, email, movil, usuario, contraseña, imagen);
		else
			user = new Usuario(nombre, fechanacimiento, email, movil, usuario, contraseña, imagen, saludo);
		// actualizamos la instancia actual
		usuarioActual = user;
		catalogoUsuarios.addUsuario(user);
		adaptadorUsuario.registrarUsuario(user);
		changeToChatWindow(); // cambiamos a la ventana de chat actual
		return REGISTRO_CORRECTO;
	}

	// cambia la foto del usuario
	public boolean changePhoto(String ruta) {
		usuarioActual.setImagen(ruta);
		// actualizamos en la bbdd
		adaptadorUsuario.actualizarUsuario(usuarioActual);
		return true;
	}

	// cambia nuestro saluedo
	public boolean changeGreeting(String greeting) {
		usuarioActual.setSaludo(greeting);
		adaptadorUsuario.actualizarUsuario(usuarioActual); // actualizar bbdd
		return true;
	}
	// obtiene el saludo del usuario actual
	public String getGreeting() {
		return usuarioActual.getSaludo();
	}

	// cambiar a la ventana de registro
	public void changeToRegister() {
		InterfazVistas antigua = interfaz;
		interfaz = new Register(this);
		antigua.exit();
	}

	// cambiar a la ventana de login
	public void changeToLogin() {
		InterfazVistas antigua = interfaz;
		interfaz = new LogIn(this);
		antigua.exit();
	}

	// Cambiar a la ventana de chat actual
	public void changeToChatWindow() {
		InterfazVistas antigua = interfaz;
		interfaz = new ChatWindow(this, usuarioActual);
		antigua.exit();
	}

	// devuelve si el usuario actual es premium o no
	public boolean soypremium() {
		return usuarioActual.isPremium();
	}

	// obtiene el mejor descuento
	public DescuentoSimple getMejorDescuento() {
		DescuentoCompuesto descuentos = controladorDescuentos.getDescuentosActuales(); // delegar en el experto
		double max = 0.0;
		DescuentoSimple mejorDescuento = null;
		List<DescuentoSimple> simples = descuentos.getdescuento();
		for (DescuentoSimple s : simples) { // coger el mejor
			if (Double.parseDouble(s.getCantidad()) > max) {
				{
					max = Double.parseDouble(s.getCantidad());
					mejorDescuento = s;
				}
			}
		}
		return mejorDescuento;
	}

	// actualizar el usuario a premium
	public void vendoMiAlmaPorPremium() {
		usuarioActual.setPremium(true);
		adaptadorUsuario.actualizarUsuario(usuarioActual);
		catalogoUsuarios.addUsuario(usuarioActual);
	}

	// Obtener la imagen de un contacto individual
	public String getImage(ContactoIndividual cont) {
		String tel = cont.getMovil();
		return catalogoUsuarios.getUsuario(tel).getImagen();
	}

	// a partir del codigo del contacto obtiene su imagen o GRUPO para indicar que es la imagen por defecto para grupos
	public String getImage(int code) {
		ContactoIndividual cI = usuarioActual.getContactoI(code);
		if (cI != null) {
			Usuario usu = catalogoUsuarios.getUsuario(cI.getMovil());
			if (usu != null)
				return usu.getImagen();
		}
		return "GRUPO";
	}

	// obtiene el nick de usuario para un contacto
	public String getUserNick(ContactoIndividual cont) {
		String tel = cont.getMovil();
		return catalogoUsuarios.getUserName(tel);
	}

	// obtiene el nick de un usuario a partir del codigo
	public String getUserNick(int code) {
		return catalogoUsuarios.getUserName(code);
	}

	// obtiene el codigo de un contacto "DEPRECATED"
	public int getCode(ContactoIndividual cont) {
		return cont.getCodigo();
		// String tel = cont.getMovil();
		// return catalogoUsuarios.getCodigo(tel);
	}

	// Dice si existe o no un usuario para un teléfono
	public boolean existeUsuario(String telefono) {
		if (telefono == null)
			return false;
		return catalogoUsuarios.existeUsuario(telefono);
	}

	// obtiene la lista de contactos, tanto individuales como grupos del usuario actual
	public List<Contacto> getContactos() {
		LinkedList<Contacto> contactos = new LinkedList<Contacto>();
		usuarioActual.getContactos().stream().forEach(cont -> contactos.add(cont));
		usuarioActual.getGrupos().stream().forEach(cont -> contactos.add(cont));
		return contactos;
	}

	public Usuario getUsuarioActual() {
		return this.usuarioActual;
	}

	// Obtiene todos los contactos individuales del usuario actual
	public List<ContactoIndividual> getContactoIndividuales() {
		LinkedList<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();
		usuarioActual.getContactos().stream().forEach(cont -> addifUser(contactos, cont));
		return contactos;
	}

	// Si existe un usuario asociado a un contacto, lo añade a la lista (contactos importados podian importar contactos sin usuario en algunas versiones)
	private void addifUser(LinkedList<ContactoIndividual> c, ContactoIndividual ci) {
		if (existeUsuario(ci.getMovil()))
			c.add(ci);
	}

	// Obtiene el view model de los datos de un chat para mostrar la venta de información del contacto, grupo o individual
	public ViewModelDatosChat getDatos(int codigo) {
		if (usuarioActual.existContactoI(codigo))
			return catalogoUsuarios.getDatosVentana(codigo, usuarioActual); // datos para usuario
		else if (usuarioActual.existContactoG(codigo))
			return catalogoUsuarios.getDatosVentanaGrupo(codigo, usuarioActual, unicaInstancia); // datos para grupo
		return null;
	}

	// Obtiene los datos para un grupo, pero además lo crea con contactos seleccionados para poder ir a modificar grupos a partir de este viewmodel
	public ViewModelGrupo getViewGrupo(int codigo) {
		ContactoGrupo g;
		if ((g = usuarioActual.getContactoG(codigo)) != null) {

			List<ContactoIndividual> grupo = catalogoUsuarios.getContactosAunqueNoExistenEnUsuario(usuarioActual,
					g.getMiembros());

			List<ContactoIndividual> noGrupo = usuarioActual.getContactos().stream()
					.filter(p -> !grupo.contains(p) && existeUsuario(p.getMovil())).collect(Collectors.toList());
			return new ViewModelGrupo(noGrupo,
					new ContactoIndividual(usuarioActual.getNombre(), usuarioActual.getMovil()), g.getNombre(),
					unicaInstancia, grupo, g.getCodigo());
		}
		return null;
	}

	// TERMINADO
	public List<ContactoIndividual> setContactosFilter(List<Integer> contactos, String nombre) {
		LinkedList<ContactoIndividual> contactosI = new LinkedList<ContactoIndividual>(
				getContactosByCodigos(contactos));
		return contactosI.stream().filter(cont -> contenido(cont.getNombre(), nombre)).collect(Collectors.toList());
	}
	
	//envia un emoji en su formato texto
	public void sendEmoji(int codeEmoji, int codeChat) {
		String e = AuxRender.setEmojiAsText(codeEmoji);
		enviarMensaje(e, codeChat);
	}

	// devuelve si el string contenido está contenido en contenedor
	public boolean contenido(String contenedorB, String contenidoB) {
		int i = 0;
		if (contenidoB.length() > contenedorB.length())
			return false;
		String contenedor = contenedorB.toLowerCase();
		String contenido = contenidoB.toLowerCase();
		for (; i < contenedor.length(); i++) {
			if (contenedor.startsWith(contenido, i)) {
				return true;
			}
		}
		return false;
	}

	//  registra un nuevo contacto, lo almacena en bbdd y actualiza el usuario
	public ContactoIndividual registrarContacto(String usuario, String telefono) {
		ContactoIndividual cont = new ContactoIndividual(usuario, telefono);
		adaptadorContacto.registrarContactoIndividual(cont);
		usuarioActual.addContacto(cont);
		adaptadorUsuario.actualizarUsuario(usuarioActual);
		ChatWindow chat = (ChatWindow) interfaz;
		chat.addChat(cont);
		return cont;
	}

	// Crea un grupo
	public boolean crearGrupo(String nombre, List<Integer> contactos) {
		ContactoGrupo creado = usuarioActual.registrarGrupo(nombre, getContactosByCodigos(contactos));
		if (creado != null) {
			adaptadorGrupo.registrarContactoGrupo(creado);
			adaptadorUsuario.actualizarUsuario(usuarioActual);
			catalogoUsuarios.registrarGrupoEnUsuarios(creado);
			ChatWindow chat = (ChatWindow) interfaz;
			chat.addChat(creado);
		}
		return creado != null;
	}

	// Modifcacion de un grupo
	public boolean ModificarGrupo(String nombre, List<Integer> contactos, int code) {
		ContactoGrupo creado = usuarioActual.getContactoG(code); // lo obtenemos
		if (creado != null) { // puede ser null si no existe
		ContactoGrupo noModificado = new ContactoGrupo(creado); // copia
			creado.setNombre(nombre);
			creado.setMiembros(
					getContactosByCodigos(contactos).stream().map(p -> p.getMovil()).collect(Collectors.toSet())); // introducimos todos los miembros
			usuarioActual.DeleteContactoG(code); 
			usuarioActual.addGrupo(creado); // lo añadimos de nuevo
			catalogoUsuarios.registrarGrupoEnUsuarios(creado); // registramos el grupo en todos los usuarios seleccionados
			catalogoUsuarios.deleteEnUsuarios(creado.getMiembros(), noModificado.getMiembros(), code); // y lo quitamos en los usuario borrados
			adaptadorGrupo.actualizarContactoGrupo(creado); // actualizamos
			ChatWindow chat = (ChatWindow) interfaz;

			chat.setChats(new LinkedList<Contacto>(getContactos())); // actualizamos la lista de chats
		}
		return creado != null;
	}

	// borrar un contacto
	public boolean eliminarContacto(int codigo) {
		boolean borrado = false;
		ContactoGrupo g;
		if (usuarioActual.existContactoI(codigo)) { // vemos si es individual
			borrado = usuarioActual.DeleteContactoI(codigo); // y borramos
		} else if ((g = usuarioActual.getContactoG(codigo)) != null) {
			if (g.getAdmin().getCodigo() == usuarioActual.getCodigo()) { // pero si es grupo y eres admin
				catalogoUsuarios.borrarGrupoUsers(g); // borramos en todos los users
				borrado = usuarioActual.DeleteContactoG(codigo); // y en el nuestro
				adaptadorUsuario.actualizarUsuario(usuarioActual);
				adaptadorGrupo.borrarContactoGrupo(g);
			} else
				borrado = false;
		} else {
			borrado = false;
		}
		if (borrado) { // si se ha borrado actualizamos los chats
			adaptadorUsuario.actualizarUsuario(usuarioActual);
			ChatWindow chat = (ChatWindow) interfaz;
			LinkedList<Contacto> lista = (LinkedList<Contacto>) getContactos();
			chat.setChats(lista);
		}

		return borrado;
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
	
	// registra un nuevo mensaje y lo añade los receptores
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
	
	// muestra todos los mensajes de un chat al usuario
	public LinkedList<BubbleText> getBurbujas(JPanel c ,int codigoActivo){
		LinkedList<Mensaje> messages = new LinkedList<Mensaje>(usuarioActual.getMensajes(codigoActivo));
		Contacto cont;
		boolean grupo = false;
		if (usuarioActual.existContactoG(codigoActivo)) {
			cont = usuarioActual.getContactoG(codigoActivo);
			grupo = true;
		}
		else
			cont = usuarioActual.getContactoI(codigoActivo);
		return AuxRender.getBurbujas(c, messages, usuarioActual.getMovil(),cont,grupo, getContactoIndividuales() );
	}
	
	// añade solamente una lista concreta de mensajes, necesario para los filtros
	public void setBurbuja(JPanel c, LinkedList<Mensaje> m, int codigoActivo) {
		Contacto cont;
		boolean grupo = false;
		if (usuarioActual.existContactoG(codigoActivo)) {
			cont = usuarioActual.getContactoG(codigoActivo);
			grupo = true;
		}
		else
			cont = usuarioActual.getContactoI(codigoActivo);
		AuxRender.getBurbujas(c, m, usuarioActual.getMovil(),cont,grupo, getContactoIndividuales() );
	}
		
	//elimina los mensajes de un chat y se actualizará la pagina en la lista
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
		mensajes = new KeyValue<Integer, List<Mensaje>>(codigo, getMensajes(codigo));
	}

	// actualiza el nombre con el que se guarda un contacto
	public void actualizarContactoI(int codigo, String nick) {
		ContactoIndividual i = usuarioActual.modifyContactoI(codigo, nick);
		adaptadorContacto.actualizarContactoIndividual(i);
	}
	
	// ordena la lista de contactos por mensajes y si no hay, por nombre del chat
	
	public LinkedList<Contacto> getOrder(List<Contacto> lista) {
		List<Contacto> conMensajes = new LinkedList<Contacto>();
		List<Contacto> sinMensajes = new LinkedList<Contacto>();
		sinMensajes = lista.stream().filter( a -> a.getMensajes().isEmpty()).collect(Collectors.toList());
		conMensajes = lista.stream().filter( a -> !a.getMensajes().isEmpty()).collect(Collectors.toList());
		if (conMensajes != null) {
			Collections.sort(conMensajes);
		}
		if (sinMensajes != null) {
			sinMensajes.sort(new OrdenarContactoPorNombre());
		}
		List<Contacto> listaOrdenada = conMensajes;
		sinMensajes.stream().forEach(p -> listaOrdenada.add(p));
		return new LinkedList<Contacto>(listaOrdenada);
	}
	

	// obtiene el ultimo mensaje de un contacto por su codigo
	public String getLastMessageText(int codigo) {
		return usuarioActual.getLastMessageText(codigo);
	}
	
	// enviar mensaje a los receptores
	public void enviarMensaje(String mensaje, int codigo) {
		if (codigo<0) return;
		if (mensaje == null || mensaje.isEmpty() ) return; // tiene que existir el mensaje
		Mensaje m = usuarioActual.addMiMensaje(mensaje, codigo); // añade a mi usuario el mensaje al chat con código codigo
		adaptadorMensaje.registrarMensaje(m);
		if (m.getTipoReceptor().equals(TipoContacto.INDIVIDUAL))
			adaptadorContacto.actualizarContactoIndividual(usuarioActual.getContactoI(codigo)); // actualiza el contacto
		else
			adaptadorGrupo.actualizarContactoGrupo(usuarioActual.getContactoG(codigo));// a un grupo
		adaptadorUsuario.actualizarUsuario(usuarioActual);
		List<Usuario> receptores = catalogoUsuarios.enviarMensajeAcontactos(codigo, usuarioActual); // enviar el mensaje a todos los usuarios receptores
		if (!receptores.isEmpty()) {
			if (m.getTipoReceptor().equals(TipoContacto.INDIVIDUAL)) {
				Usuario u = receptores.get(0);
				KeyValue<Boolean, KeyValue<Mensaje,ContactoIndividual>> ciV = u.addMensajeDelCI(m, usuarioActual, codigo); // a un contacto individual
				ContactoIndividual ci = ciV.getValue().getValue();
				if (ciV.getKey()) {
					List<Mensaje> mess = ci.getMensajes();
					ci.setMensajes(new LinkedList<Mensaje>());
					adaptadorMensaje.registrarMensaje(ciV.getValue().getKey());
					adaptadorContacto.registrarContactoIndividual(ci);
					ci.setMensajes(mess);
				}
				else {
					adaptadorMensaje.registrarMensaje(ciV.getValue().getKey()); 
				}
				adaptadorContacto.actualizarContactoIndividual(ci);

				adaptadorUsuario.actualizarUsuario(u);
			}
		}
	}

	//recupera todos los chats del usuario actual
	public List<Contacto> buscarChats(String text) {
		return usuarioActual.RecuperarContactosFiltrados(text);

	}

	//recupera todos los mensajes del chat actual
	public List<Mensaje> getMensajes(int codigo) {
		return usuarioActual.getMensajes(codigo);
	}

	// Funcion para buscar un mensaje en un chat normal segun los filtros
	public void buscarMensajeContacto(String usuario, Date fini, Date ffin, String texto, int codigoActivo) {
		if (codigoActivo < 0) return;
		Contacto contacto = null;
		if (usuarioActual.existContactoG(codigoActivo))
			contacto = usuarioActual.getContactoG(codigoActivo);
		else if (usuarioActual.existContactoI(codigoActivo))
			contacto = usuarioActual.getContactoI(codigoActivo);
		if (contacto == null) return;
		ChatWindow chat = (ChatWindow) interfaz;
		mensajes = new KeyValue<Integer, List<Mensaje>>(codigoActivo, contacto.getMensajeFiltrados( usuario,  fini,  ffin,  texto)); // delegamos el filtrado al contacto
		chat.setBurbujas(new LinkedList<Mensaje>(mensajes.getValue()));
	}
	
	
	// Cargador de mensajes
	public void cargarMensajes(String fich, String formatDateWhatsApp) {
		Plataforma p;
		if (formatDateWhatsApp.equals(SimpleTextParser.FORMAT_DATE_IOS))
			p = Plataforma.IOS;
		else
			p = Plataforma.ANDROID;
		
		cargador.setFichero(fich, formatDateWhatsApp, p);
	}
	
	// pone los mensajes para un nuevo contacto seleccionado
	public List<Mensaje> setMensajesNuevoContacto(int codigoActivo){
		
		if (mensajes != null && mensajes.getKey() == codigoActivo)
			return mensajes.getValue();
		mensajes = new KeyValue<Integer, List<Mensaje>>(codigoActivo, getMensajes(codigoActivo));
		return mensajes.getValue();
	}

	// Cargador de mensajes
	@Override
	public void nuevosMensajes(MensajesEvent e) {
		List<MensajeWhatsApp> mensajes = e.getMensajes();
		// Comprueba que sea una conversación entre 2 personas
		Set<String> autores = mensajes.stream()
				.map(m -> m.getAutor())
				.collect(Collectors.toSet());
		if (autores.size() != 2)
			return;
		// Comprueba que se tenga un contacto asociado a la otra persona
		ContactoIndividual auxC = null;
		for (String it : autores) {
			if (usuarioActual.getContactos().stream()
					.anyMatch(c -> c.getNombre().equals(it)))
			auxC = usuarioActual.getContactos().stream()
					.filter(c -> c.getNombre().equals(it))
					.collect(Collectors.toList())
					.get(0);
		}
		if (auxC == null)
			return;
		// Obtiene el usuario asociado al contacto
		Usuario auxU = catalogoUsuarios.getByMovil(auxC.getMovil());
		ContactoIndividual auxC1 = null;
		if ( ! auxU.getContactos().stream()
				.anyMatch(c -> c.getNombre().equals(usuarioActual.getMovil())))
			return;
		// Obtiene el contacto del usuarioActual de la otra persona
		auxC1 = auxU.getContactos().stream()
				.filter(c -> c.getNombre().equals(usuarioActual.getMovil()))
				.collect(Collectors.toList())
				.get(0);
		// Se comprueba quien es el emisor y el receptor
		for (MensajeWhatsApp itM : mensajes) {
			if (itM.getAutor().equals(auxC.getNombre()))
				registrarMensaje(itM.getTexto(), itM.getAutor(), java.sql.Timestamp.valueOf(itM.getFecha()), auxC1, TipoContacto.INDIVIDUAL);
			else if (itM.getAutor().equals(auxC1.getNombre()))
				registrarMensaje(itM.getTexto(), itM.getAutor(), java.sql.Timestamp.valueOf(itM.getFecha()), auxC, TipoContacto.INDIVIDUAL); 				
		}
		
	}

	public boolean exportarContactos(String filePath) {
		// Obtener los contactos
		Map<String, String> contactos = new HashMap<String, String>();
		for (ContactoIndividual itC : usuarioActual.getContactos()) {
			// Si existe el usuario se coge el nick y se añade al mapa
			if (existeUsuario(itC.getMovil())) {
				contactos.put( adaptadorUsuario.recuperarTodosUsuarios().stream()
									.filter(u-> u.getMovil().equals(itC.getMovil()))
									.collect(Collectors.toList())
									.get(0).getUsuario(), itC.getMovil());
			}
			// Si es un usuario fantasma se pone su nombre como nick
			else {
				contactos.put(itC.getNombre(), itC.getMovil());
			}
		}
		// Obtener los grupos
		// Map<NombreGrupo, Map<Username, tlf>>
		Map<String, Map<String, String>> grupos = new HashMap<String, Map<String, String>>();
		// Map<NombreGrupo, Admin>
		Map<String, Usuario> auxAdmin = new HashMap<String, Usuario>();
		// Map<Username, tlf>
		Map<String, String> auxM;
		// Para cada grupo...
		for (ContactoGrupo itG : usuarioActual.getGrupos()) {
			// Creamos un mapa con <nick, tlf> de los miembros
			auxM = new HashMap<String, String>();
			// Recorremos la lista de miembros...
			for (String itTelf : itG.getMiembros()) {
				if (existeUsuario(itTelf)) {
					auxM.put(adaptadorUsuario.recuperarTodosUsuarios().stream()
									.filter(u-> u.getMovil().equals(itTelf))
									.collect(Collectors.toList())
									.get(0).getUsuario(), itTelf);
				}
				else {
					auxM.put(itTelf, itTelf);
				}
			}
			grupos.put(itG.getNombre(), auxM);
			auxAdmin.put(itG.getNombre(), itG.getAdmin());
		}	
		// PDF
		 FileOutputStream archivo;
		 try {
			archivo = new FileOutputStream(filePath);
			Document documento = new Document();
			Font bold = new Font();
			bold.setStyle(Font.BOLD);
			Font italic = new Font();
			italic.setStyle(Font.ITALIC);
			PdfWriter.getInstance(documento, archivo);
			documento.open();
			// Usuario
			documento.add(new Paragraph("User: ", bold));
			documento.add(new Paragraph("     " + usuarioActual.getUsuario()));
			// Contactos
			documento.add(new Paragraph("\nContacts:", bold));
			for (String it : contactos.keySet()) {
				documento.add(new Paragraph("     Username: " + it + "     Phone: " + contactos.get(it)));
			}
			// Grupos
			documento.add(new Paragraph("\nGroups:", bold));
			for (String it : grupos.keySet()) {
				documento.add(new Paragraph("     " + it));
				documento.add(new Paragraph("          Username: " + auxAdmin.get(it).getUsuario() + "     Phone: " + auxAdmin.get(it).getMovil() + "     (admin)", italic));
				for (String it1 : grupos.get(it).keySet()) {
					documento.add(new Paragraph("          Username: " + it1 + "     Phone: " + grupos.get(it).get(it1)));
				}
			}
			documento.close();
		} catch (DocumentException | FileNotFoundException e) {
			//e.printStackTrace();
			return false;
		}
		 return true;
	}

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


	// TERMINADO
	public void cerrarSesion() {
		InterfazVistas antigua = interfaz;
		interfaz = new LogIn(this);
		antigua.exit();
	}
	
}
