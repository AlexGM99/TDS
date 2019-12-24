package persistencia;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;
import modelo.Mensaje;

public abstract class Auxiliar {
	
	static <T> String obtenerCodigos(Collection<T> t) {
		String lineas = "";
		for (T it : t) {
			if (it instanceof Contacto)
				lineas += ((Contacto) it).getCodigo() + " ";
			else if (it instanceof Mensaje)
				lineas += ((Mensaje) it).getCodigo() + " ";
		}
		return lineas.trim();
	}
	
	static List<ContactoIndividual> obtenerContactosDesdeCodigos(String lineas) {

		List<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorContactoIndividualTDS adaptadorCI = AdaptadorContactoIndividualTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			contactos.add(adaptadorCI.recuperarContactoIndividual(Integer.valueOf((String) strTok.nextElement())));
		}
		return contactos;
	}

	static List<ContactoGrupo> obtenerGruposDesdeCodigos(String lineas) {

		List<ContactoGrupo> grupos = new LinkedList<ContactoGrupo>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorContactoGrupoTDS adaptadorCG = AdaptadorContactoGrupoTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			grupos.add(adaptadorCG.recuperarContactoGrupo(Integer.valueOf((String) strTok.nextElement())));
		}
		return grupos;
	}
	
	static List<Mensaje> obtenerMensajesDesdeCodigos(String lineas) {

		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorMensajeTDS adaptadorM = AdaptadorMensajeTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			mensajes.add((adaptadorM.recuperarMensaje(Integer.valueOf((String) strTok.nextElement()))));
		}
		return mensajes;
	}

}
