package jUnit;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import modelo.ContactoGrupo;
import modelo.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoGrupoDAO;
import persistencia.IAdaptadorUsuarioDAO;


public class PruebaPersistenciaContactoGrupo {
	
	private FactoriaDAO miFactoria = null;
	private IAdaptadorContactoGrupoDAO adaptadorG;
	private IAdaptadorUsuarioDAO adaptadorU;

	
	@Before
	public void inicializar() {
		try {
			miFactoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorG = miFactoria.getGrupoDAO();
		adaptadorU = miFactoria.getUsuarioDAO();

	}
	
	@Test
	public void testRegistrarContactoGrupo() {
		
		Usuario u = new Usuario("Nombre", Date.from(Instant.now()), "email", "movilU", "usuario", "contraseña", "imagen");
		ContactoGrupo g = new ContactoGrupo("Nombre1", u.getMovil(), "movil2");
		g.setAdmin(u);
		adaptadorU.registrarUsuario(u);
		adaptadorG.registrarContactoGrupo(g);
		
		assertEquals("Prueba Registro ContactoGrupo", g.toString(),adaptadorG.recuperarContactoGrupo(g.getCodigo()).toString());
		
		adaptadorG.borrarContactoGrupo(g);
		adaptadorU.borrarUsuario(u);
	}

	@Test
	public void testActualizarContactoGrupo() {
		Usuario u = new Usuario("Nombre", Date.from(Instant.now()), "email", "movilU", "usuario", "contraseña", "imagen");
		ContactoGrupo g = new ContactoGrupo("Nombre1", u.getMovil(), "movil1", "movil2");
		ContactoGrupo g1 = new ContactoGrupo("Nombre2", u.getMovil(), "movil3", "movil4");
		g.setAdmin(u);
		adaptadorU.registrarUsuario(u);
		adaptadorG.registrarContactoGrupo(g);

		g1.setCodigo(g.getCodigo());
		g.setNombre(g1.getNombre());
		g.setMiembros(g1.getMiembros());
		
		adaptadorG.actualizarContactoGrupo(g);
		
		assertEquals("Prueba Actualizar Grupo", g1.toString(),adaptadorG.recuperarContactoGrupo(g.getCodigo()).toString());
		
		adaptadorG.borrarContactoGrupo(g);
		adaptadorU.borrarUsuario(u);
	}


}
