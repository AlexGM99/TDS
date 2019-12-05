package persistencia;

import java.util.List;
import modelo.Usuario;

public interface IAdaptadorUsuarioDAO {

	public void registrarUsuario(Usuario usuario);
	public void borrarUsuario(Usuario usuario);
	public void actualizarUsuario(Usuario usuario);
	public Usuario recuperarUsuario(int codigo);
	public List<Usuario> recuperarTodosUsuarios();
}
