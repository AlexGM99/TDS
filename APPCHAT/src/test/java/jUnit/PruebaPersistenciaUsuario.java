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


public class PruebaPersistenciaUsuario {
	
	private FactoriaDAO miFactoria = null;
	private IAdaptadorUsuarioDAO adaptadorU;

	
	@Before
	public void inicializar() {
		try {
			miFactoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorU = miFactoria.getUsuarioDAO();

	}
	
	@Test
	public void testRegistrarUsuario() {
		
		Usuario u = new Usuario("Nombre", Date.from(Instant.now()), "email", "movilU", "usuario", "contraseña", "imagen");
		adaptadorU.registrarUsuario(u);
		
		assertEquals("Prueba Registro Usuario", u.toString(),adaptadorU.recuperarUsuario(u.getCodigo()).toString());
		
		adaptadorU.borrarUsuario(u);
	}

	@Test
	public void testActualizarUsuario() {
		Usuario u = new Usuario("Nombre", Date.from(Instant.now()), "email", "movilU", "usuario", "contraseña", "imagen");
		Usuario u1 = new Usuario("Nombre1", Date.from(Instant.now()), "email1", "movilU1", "usuario1", "contraseña1", "imagen1");

		adaptadorU.registrarUsuario(u);
		
		u1.setCodigo(u.getCodigo());
		u.setNombre(u1.getNombre());
		u.setFechaNacimiento(u1.getFechaNacimiento());
		u.setEmail(u1.getEmail());
		u.setMovil(u1.getMovil());
		u.setUsuario(u1.getUsuario());
		u.setContraseña(u1.getContraseña());
		u.setImagen(u1.getImagen());
		
		
		adaptadorU.actualizarUsuario(u);
		
		assertEquals("Prueba Actualizar Usuario", u1.toString(),adaptadorU.recuperarUsuario(u.getCodigo()).toString());
		
		adaptadorU.borrarUsuario(u);
	}


}
