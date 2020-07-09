package cargadorMensajes;

import java.util.EventListener;

public interface IMensajesListener extends EventListener {
	
	public void nuevosMensajes(MensajesEvent e);	

}
