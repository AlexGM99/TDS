package persistencia;

import java.util.List;

import modelo.ContactoIndividual;

public interface IAdaptadorContactoIndividualDAO {

	public void registrarContactoIndividual(ContactoIndividual contacto);
	public void borrarContactoIndividual(ContactoIndividual contacto);
	public void actualizarContactoIndividual(ContactoIndividual contacto);
	public ContactoIndividual recuperarContactoIndividual(int codigo);
	public List<ContactoIndividual> recuperarTodosContactoIndividuals();
}
