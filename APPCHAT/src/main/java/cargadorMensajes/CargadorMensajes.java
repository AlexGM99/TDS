package cargadorMensajes;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class CargadorMensajes {

	private Vector<IMensajesListener> oyentes;

	public CargadorMensajes() {
		oyentes = new Vector<IMensajesListener>();
	}

	public synchronized void addMensajesListener(IMensajesListener oyente) {
		oyentes.add(oyente);
	}

	public synchronized void removeMensajesListener(IMensajesListener oyente) {
		oyentes.remove(oyente);
	}

	// Parsea del fichero proporcionado por la vista, los mensajes y notifica a los oyentes
	public void setFichero (String ruta, String formatDate, Plataforma plataforma) {
		List<MensajeWhatsApp> chat = null;
		try {
			chat = SimpleTextParser.parse(ruta, formatDate, plataforma);
		} catch (IOException e) {
			e.printStackTrace();
		}
		notificarNuevosMensajes(new MensajesEvent(this, chat));
	}

	// Envía a los oyentes el evento con los mensajes
	@SuppressWarnings("unchecked")
	private void notificarNuevosMensajes(MensajesEvent e) {
		Vector<IMensajesListener> lista;
		synchronized (this) {
			lista = (Vector<IMensajesListener>) oyentes.clone();
		}
		for (IMensajesListener oyente : lista) {
			oyente.nuevosMensajes(e);
		}
	}
}