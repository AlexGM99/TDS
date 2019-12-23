package jUnit;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import modelo.ContactoIndividual;
import modelo.Mensaje;
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
		Mensaje m = new Mensaje("Mensaje de prueba", fecha, "868884831", new ContactoIndividual(nombre, movil));
		adaptadorM.registrarMensaje(m);		
		
		assertEquals("Prueba Registro Mensaje: Codigo", m.getCodigo() ,adaptadorM.recuperarMensaje(m.getCodigo()).getCodigo());
		assertEquals("Prueba Registro Mensaje: Texto", m.getTexto() ,adaptadorM.recuperarMensaje(m.getCodigo()).getTexto());
		assertEquals("Prueba Registro Mensaje: Emisor", m.getTlfEmisor() ,adaptadorM.recuperarMensaje(m.getCodigo()).getTlfEmisor());
		assertEquals("Prueba Registro Mensaje: Fecha", m.getHora().toString() ,adaptadorM.recuperarMensaje(m.getCodigo()).getHora().toString());
		
		//assertEquals("Prueba Registro Mensaje Fecha", m,adaptadorM.recuperarMensaje(m.getCodigo()));
		
		
	}

	/*
	@Test
	public void testBorrarMensaje() {
		fail("Not yet implemented");
	}

	@Test
	public void testActualizarMensaje() {
		fail("Not yet implemented");
	}

	@Test
	public void testRecuperarMensaje() {
		fail("Not yet implemented");
	}

	@Test
	public void testRecuperarTodosMensajes() {
		fail("Not yet implemented");
	}*/

}
