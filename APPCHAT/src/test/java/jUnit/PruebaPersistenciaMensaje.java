package jUnit;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import modelo.ContactoIndividual;
import modelo.Mensaje;
import modelo.TipoContacto;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorMensajeDAO;

public class PruebaPersistenciaMensaje {
	
	private FactoriaDAO miFactoria = null;
	private IAdaptadorMensajeDAO adaptadorM;

	
	@Before
	public void inicializar() {
		try {
			miFactoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorM = miFactoria.getMensajeDAO();

	}
	
	@Test
	public void testRegistrarMensaje() {
		Date fecha = Date.from(Instant.now());
		Mensaje m = new Mensaje("Mensaje de prueba", fecha, "868884831", new ContactoIndividual("Nombre", "Movil"), TipoContacto.INDIVIDUAL);
		adaptadorM.registrarMensaje(m);		
		
		assertEquals("Prueba Registro Mensaje", m.toString(),adaptadorM.recuperarMensaje(m.getCodigo()).toString());
		
		adaptadorM.borrarMensaje(m);
	}

	/*@Test
	public void testBorrarMensaje() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testActualizarMensaje() {
		Mensaje m = new Mensaje("Texto Antes", Date.from(Instant.now()), "TlfEmisorAntes", new ContactoIndividual("ContactoAntes", "MoovilContactoAntes"), TipoContacto.INDIVIDUAL);
		Mensaje m2 = new Mensaje("Texto Despues", Date.from(Instant.now()), "TlfEmisorDespues", new ContactoIndividual("ContactoDespues", "MoovilContactoDespues"), TipoContacto.INDIVIDUAL);
		
		adaptadorM.registrarMensaje(m);
		
		m2.setCodigo(m.getCodigo());
		m.setTexto(m2.getTexto());
		m.setHora(m2.getHora());
		m.setTlfEmisor(m2.getTlfEmisor());
		m.setReceptor(m2.getReceptor());
		adaptadorM.actualizarMensaje(m);
		
		assertEquals("Prueba Actualizar Mensaje", m2.toString(),adaptadorM.recuperarMensaje(m.getCodigo()).toString());
	}

	/*
	 * @Test public void testRecuperarMensaje() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testRecuperarTodosMensajes() { fail("Not yet implemented");
	 * }
	 */

}
