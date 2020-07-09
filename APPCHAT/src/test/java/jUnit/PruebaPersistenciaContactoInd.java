package jUnit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modelo.ContactoIndividual;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoIndividualDAO;

public class PruebaPersistenciaContactoInd {
	
	private FactoriaDAO miFactoria = null;
	private IAdaptadorContactoIndividualDAO adaptadorI;

	
	@Before
	public void inicializar() {
		try {
			miFactoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorI = miFactoria.getContactoIndividualDAO();

	}
	
	@Test
	public void testRegistrarContactoInd() {
		
		ContactoIndividual c = new ContactoIndividual("Nombre1", "movil1");

		adaptadorI.registrarContactoIndividual(c);
		
		assertEquals("Prueba Registro ContactoInd", c.toString(),adaptadorI.recuperarContactoIndividual(c.getCodigo()).toString());
		
		adaptadorI.borrarContactoIndividual(c);
	}

	@Test
	public void testActualizarContactoInd() {
		ContactoIndividual c = new ContactoIndividual("Nombre1", "movil1");
		ContactoIndividual c1 = new ContactoIndividual("Nombre2", "movil2");
		
		adaptadorI.registrarContactoIndividual(c);

		c1.setCodigo(c.getCodigo());
		c.setNombre(c1.getNombre());
		c.setMovil(c1.getMovil());
		
		adaptadorI.actualizarContactoIndividual(c);
		
		assertEquals("Prueba Actualizar ContactoInd", c1.toString(),adaptadorI.recuperarContactoIndividual(c.getCodigo()).toString());
		
		adaptadorI.borrarContactoIndividual(c);
	}


}
