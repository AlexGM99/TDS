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

import modelo.Mensaje;

// 'Usuario' tiene una lista de mensajes, ¿cuándo la actualizamos?

public class AdaptadorMensajeTDS implements IAdaptadorMensajeDAO {

	private static ServicioPersistencia servPersistencia;

	private SimpleDateFormat dateFormat; // para formatear la fecha en
											// la base de datos

	private static AdaptadorMensajeTDS unicaInstancia;

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

	/* cuando se registra un mensaje se le asigna un identificador unico */
	public void registrarMensaje(Mensaje mensaje) {
		Entidad eMensaje;
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;

		// Crear entidad venta
		eMensaje = new Entidad();

		eMensaje.setNombre("mensaje");
		eMensaje.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("texto", mensaje.getTexto()),
						new Propiedad("tlfEmisor", mensaje.getTlfEmisor()),
						new Propiedad("fecha", dateFormat.
								format(mensaje.getHora())))));
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
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "texto");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "texto", mensaje.getTexto());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "tlfEmisor");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "tlfEmisor", mensaje.getTlfEmisor());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "fecha");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "fecha", dateFormat.format(mensaje.getHora()));
	}

	public Mensaje recuperarMensaje(int codigo) {
		Entidad eMensaje;
		String texto;
		String tlfEmisor;
		Date hora = null;
		
		eMensaje = servPersistencia.recuperarEntidad(codigo);
		texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
		tlfEmisor = servPersistencia.recuperarPropiedadEntidad(eMensaje, "tlfEmisor");
		
		try {
			hora = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, "fecha"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Mensaje mensaje = new Mensaje(texto, hora, tlfEmisor);
		mensaje.setCodigo(codigo);
		return mensaje;
	}

	public List<Mensaje> recuperarTodosMensajes() {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		List<Entidad> eMensajes = servPersistencia.recuperarEntidades("mensaje");

		for (Entidad eMensaje : eMensajes) {
			mensajes.add(recuperarMensaje(eMensaje.getId()));
		}
		return mensajes;
	}

}