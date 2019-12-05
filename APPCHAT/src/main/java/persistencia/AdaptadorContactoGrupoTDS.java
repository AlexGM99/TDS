package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.ContactoGrupo;
import modelo.Usuario;

public class AdaptadorContactoGrupoTDS implements IAdaptadorContactoGrupoDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoGrupoTDS unicaInstancia;

	public static AdaptadorContactoGrupoTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorContactoGrupoTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorContactoGrupoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un ContactoGrupo se le asigna un identificador unico */
	public void registrarContactoGrupo(ContactoGrupo contacto) {
		Entidad eContactoGr;
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true;
		try {
			eContactoGr = servPersistencia.recuperarEntidad(contacto.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe)
			return;

		// registrar primero los atributos que son objetos
		// registrar admin
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();

		AdaptadorUsuarioTDS adaptadorU = AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(adaptadorU.recuperarUsuario(contacto.getAdmin().getCodigo()));

		// crear entidad contactoGrupo
		eContactoGr = new Entidad();

		eContactoGr.setNombre("contactoGrupo");
		eContactoGr.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", contacto.getNombre()),
				new Propiedad("admin", String.valueOf(contacto.getAdmin().getCodigo())),
				new Propiedad("miembros", contacto.getMiembros().toString()))));

		// registrar entidad contactoGrupo
		eContactoGr = servPersistencia.registrarEntidad(eContactoGr);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		contacto.setCodigo(eContactoGr.getId());
	}

	public void borrarContactoGrupo(ContactoGrupo contacto) {
		// No se comprueban restricciones de integridad
		Entidad eContactoGr;

		AdaptadorUsuarioTDS adaptadorU = AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorU.borrarUsuario(contacto.getAdmin());

		eContactoGr = servPersistencia.recuperarEntidad(contacto.getCodigo());
		servPersistencia.borrarEntidad(eContactoGr);
	}

	public void actualizarContactoGrupo(ContactoGrupo contacto) {
		Entidad eContactoGr;

		eContactoGr = servPersistencia.recuperarEntidad(contacto.getCodigo());
		servPersistencia.eliminarPropiedadEntidad(eContactoGr, "nombre");
		servPersistencia.anadirPropiedadEntidad(eContactoGr, "nombre", contacto.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eContactoGr, "admin");
		servPersistencia.anadirPropiedadEntidad(eContactoGr, "admin", String.valueOf(contacto.getAdmin().getCodigo()));
		servPersistencia.eliminarPropiedadEntidad(eContactoGr, "miembros");
		servPersistencia.anadirPropiedadEntidad(eContactoGr, "miembros", contacto.getMiembros().toString());
	}

	public ContactoGrupo recuperarContactoGrupo(int codigo) {

		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (ContactoGrupo) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		// recuperar entidad
		Entidad eContactoGr = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		// nombre
		String nombre = servPersistencia.recuperarPropiedadEntidad(eContactoGr, "nombre");

		// miembros
		String lineas = servPersistencia.recuperarPropiedadEntidad(eContactoGr, "miembros");
		List<String> aux = Arrays.asList(lineas);
		String[] miembros = new String[aux.size()];
		for (int i = 0; i < miembros.length; i++) {
			miembros[i] = aux.get(i);
		}
		ContactoGrupo grupo = new ContactoGrupo(nombre, miembros);
		grupo.setCodigo(codigo);

		// IMPORTANTE:añadir el grupo al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, grupo);

		// recuperar propiedades que son objetos llamando a adaptadores
		// admin
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		int codigoUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContactoGr, "admin"));

		Usuario admin = adaptadorUsuario.recuperarUsuario(codigoUsuario);
		grupo.setAdmin(admin);

		// devolver el objeto venta
		return grupo;
	}

	public List<ContactoGrupo> recuperarTodosContactoGrupos() {
		List<ContactoGrupo> contactos = new LinkedList<ContactoGrupo>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("contactoGrupo");

		for (Entidad eContactoGr : entidades) {
			contactos.add(recuperarContactoGrupo(eContactoGr.getId()));
		}
		return contactos;
	}

}
