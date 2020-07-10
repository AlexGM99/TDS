package cargadorMensajes;

import java.util.EventObject;
import java.util.List;

// Evento que contiene una lista de mensajes (en formato WhatsApp)
public class MensajesEvent extends EventObject {
	
	private static final long serialVersionUID = 7084385618840419079L;
	protected List<MensajeWhatsApp> mensajes;

	public MensajesEvent(Object source, List<MensajeWhatsApp> mensajes) {
		super(source);
		this.mensajes = mensajes;
	}

	public List<MensajeWhatsApp> getMensajes() {
		return mensajes;
	}


}
