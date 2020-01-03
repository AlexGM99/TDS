package persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;
import modelo.Mensaje;
import modelo.TipoContacto;

// 'Usuario' tiene una lista de mensajes, ¿cuándo la actualizamos?

public class AdaptadorMensajeTDS implements IAdaptadorMensajeDAO {

	private static ServicioPersistencia servPersistencia;

	private SimpleDateFormat dateFormat; // para formatear la fecha en la base de datos

	private static AdaptadorMensajeTDS unicaInstancia;

	private static final String NOMBRE_ENTIDAD = "mensaje";
	private static final String NOMBRE_PROP_TEXTO = "texto";
	private static final String NOMBRE_PROP_TLFEMISOR = "tlfEmisor";
	private static final String NOMBRE_PROP_FECHA = "fecha";
	private static final String NOMBRE_PROP_RECEPTOR = "receptor";
	private static final String NOMBRE_PROP_TIPORECEPTOR = "tipoReceptor";
	
	public static AdaptadorMensajeTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorMensajeTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}

	// Cuando se registra un mensaje se le asigna un identificador unico
	public void registrarMensaje(Mensaje mensaje) {
		Entidad eMensaje;
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true;
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe)
			return;

		// registrar primero los atributos que son objetos
		// receptor
		switch (mensaje.getTipoReceptor()) {
		case GRUPO:
			AdaptadorContactoGrupoTDS adaptadorCG = AdaptadorContactoGrupoTDS.getUnicaInstancia();
			adaptadorCG.registrarContactoGrupo((ContactoGrupo) mensaje.getReceptor());
			break;

		case INDIVIDUAL:
			AdaptadorContactoIndividualTDS adaptadorCI = AdaptadorContactoIndividualTDS.getUnicaInstancia();
			adaptadorCI.registrarContactoIndividual((ContactoIndividual) mensaje.getReceptor());
			break;
		}

		// Crear entidad venta
		eMensaje = new Entidad();

		eMensaje.setNombre(NOMBRE_ENTIDAD);
		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(NOMBRE_PROP_TEXTO, mensaje.getTexto()),
				new Propiedad(NOMBRE_PROP_TLFEMISOR, mensaje.getTlfEmisor()),
				new Propiedad(NOMBRE_PROP_FECHA, dateFormat.format(mensaje.getHora())),
				new Propiedad(NOMBRE_PROP_RECEPTOR, String.valueOf(mensaje.getReceptor().getCodigo())),
				new Propiedad(NOMBRE_PROP_TIPORECEPTOR, String.valueOf(mensaje.getTipoReceptor())))));
		// registrar entidad venta
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		mensaje.setCodigo(eMensaje.getId());
	}

	public void borrarMensaje(Mensaje mensaje) {
		// No se comprueban restricciones de integridad
		Entidad eMensaje;

		eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		servPersistencia.borrarEntidad(eMensaje);
	}

	public void actualizarMensaje(Mensaje mensaje) {
		Entidad eMensaje;

		eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, NOMBRE_PROP_TEXTO);
		servPersistencia.anadirPropiedadEntidad(eMensaje, NOMBRE_PROP_TEXTO, mensaje.getTexto());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, NOMBRE_PROP_TLFEMISOR);
		servPersistencia.anadirPropiedadEntidad(eMensaje, NOMBRE_PROP_TLFEMISOR, mensaje.getTlfEmisor());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, NOMBRE_PROP_FECHA);
		servPersistencia.anadirPropiedadEntidad(eMensaje, NOMBRE_PROP_FECHA, dateFormat.format(mensaje.getHora()));
		servPersistencia.eliminarPropiedadEntidad(eMensaje, NOMBRE_PROP_RECEPTOR);
		servPersistencia.anadirPropiedadEntidad(eMensaje, NOMBRE_PROP_RECEPTOR,
				String.valueOf(mensaje.getReceptor().getCodigo()));
		servPersistencia.eliminarPropiedadEntidad(eMensaje, NOMBRE_PROP_TIPORECEPTOR);
		servPersistencia.anadirPropiedadEntidad(eMensaje, NOMBRE_PROP_TIPORECEPTOR, String.valueOf(mensaje.getTipoReceptor()));

		if (PoolDAO.getUnicaInstancia().contiene(mensaje.getCodigo()))
			PoolDAO.getUnicaInstancia().addObjeto(mensaje.getCodigo(), mensaje);
	}

	public Mensaje recuperarMensaje(int codigo) {
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Mensaje) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		// recuperar entidad
		Entidad eMensaje = servPersistencia.recuperarEntidad(codigo);
		
		// recuperar propiedades que no son objetos
		String texto;
		String tlfEmisor;
		Date hora = null;
		TipoContacto tipoReceptor;
		texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, NOMBRE_PROP_TEXTO);
		tlfEmisor = servPersistencia.recuperarPropiedadEntidad(eMensaje, NOMBRE_PROP_TLFEMISOR);
		tipoReceptor = TipoContacto.valueOf(servPersistencia.recuperarPropiedadEntidad(eMensaje, NOMBRE_PROP_TIPORECEPTOR));

		try {
			hora = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, NOMBRE_PROP_FECHA));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Mensaje mensaje = new Mensaje(texto, hora, tlfEmisor, null, tipoReceptor);
		mensaje.setCodigo(codigo);

		// IMPORTANTE: añadir el mensaje al pool antes de llamar a otros adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, mensaje);

		// recuperar propiedades que son objetos llamando a adaptadores
		// receptor

		int codigoReceptor;
		Contacto receptor;
		
		switch (tipoReceptor) {
		case GRUPO:
			AdaptadorContactoGrupoTDS adaptadorCG = AdaptadorContactoGrupoTDS.getUnicaInstancia();
			codigoReceptor = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, NOMBRE_PROP_RECEPTOR));
			receptor = adaptadorCG.recuperarContactoGrupo(codigoReceptor);
			mensaje.setReceptor(receptor);
			break;
		
		case INDIVIDUAL:
			AdaptadorContactoIndividualTDS adaptadorCI = AdaptadorContactoIndividualTDS.getUnicaInstancia();
			codigoReceptor = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, NOMBRE_PROP_RECEPTOR));
			receptor = adaptadorCI.recuperarContactoIndividual(codigoReceptor);
			mensaje.setReceptor(receptor);
			break;
		}
		
		// devolver el objeto
		return mensaje;
	}

	public List<Mensaje> recuperarTodosMensajes() {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		List<Entidad> eMensajes = servPersistencia.recuperarEntidades(NOMBRE_ENTIDAD);

		for (Entidad eMensaje : eMensajes) {
			mensajes.add(recuperarMensaje(eMensaje.getId()));
		}
		return mensajes;
	}

}