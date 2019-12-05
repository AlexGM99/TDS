package persistencia;

import java.util.List;
import modelo.Mensaje;

public interface IAdaptadorMensajeDAO {

	public void registrarMensaje(Mensaje mensaje);
	public void borrarMensaje(Mensaje mensaje);
	public void actualizarMensaje(Mensaje mensaje);
	public Mensaje recuperarMensaje(int codigo);
	public List<Mensaje> recuperarTodosMensajes();
	
}
