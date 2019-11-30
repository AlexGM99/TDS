package persistencia;

import java.util.List;

import modelo.ContactoGrupo;

public interface IAdaptadorContactoGrupoDAO {

	public void registrarContactoGrupo(ContactoGrupo contacto);
	public void borrarContactoGrupo(ContactoGrupo contacto);
	public void modificarContactoGrupo(ContactoGrupo contacto);
	public ContactoGrupo recuperarContactoGrupo(int codigo);
	public List<ContactoGrupo> recuperarTodosContactoGrupos();
}
