package modelo;

import java.time.Instant;
import java.util.Date;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoGrupoDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;
import persistencia.PoolDAO;

public class Prueba {

	public static void main(String[] args) {

		FactoriaDAO miFactoria = null;

		try {
			miFactoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}

		
		Usuario usu1 = new Usuario("Edu", Date.from(Instant.parse("1998-12-14T01:02:03Z")), "email-edu", "movil-edu", "edupema", "passEdu", "imagenEdu", false);
		Usuario usu2 = new Usuario("Alex", Date.from(Instant.parse("1998-12-14T01:02:03Z")), "email-edu", "movil-edu", "edupema", "passAlex", "imagenAlex", false);
		
		ContactoIndividual ind1 = new ContactoIndividual("Edu", usu1.getMovil());
		ContactoIndividual ind2 = new ContactoIndividual("Alex", usu2.getMovil());
		
		ContactoGrupo gr1 = new ContactoGrupo("Grupo 1", usu1.getMovil());
		usu1.crearGrupo(gr1);
		ContactoGrupo gr2 = new ContactoGrupo("Grupo 2", usu1.getMovil(), usu2.getMovil());
		
		Mensaje m1 = new Mensaje("Hola mundo", Date.from(Instant.now()), usu1.getMovil());
		Mensaje m2 = new Mensaje("Hello World", Date.from(Instant.now()), usu2.getMovil());
		
		IAdaptadorContactoIndividualDAO adaptadorCI = miFactoria.getContactoIndividualDAO();
		
		IAdaptadorContactoGrupoDAO adaptadorCG = miFactoria.getGrupoDAO();
		IAdaptadorUsuarioDAO adaptadorU = miFactoria.getUsuarioDAO();

		IAdaptadorMensajeDAO adaptadorM = miFactoria.getMensajeDAO();
		
		// Usuarios
		System.out.println("\tRegistrar usuarios");
		adaptadorU.registrarUsuario(usu1);
		System.out.println("-- usuarios 'usu1' registrado: " + usu1);
		
		System.out.println("\t Recuperar usuarios");
		System.out.println("-- recuperado usu1: " + adaptadorU.recuperarUsuario(usu1.getCodigo()));

		// Mensajes
		System.out.println("Registrar mensajes");
		adaptadorM.registrarMensaje(m1);
		System.out.println("-- mensaje 'm1' registrado: " + m1);
		adaptadorM.registrarMensaje(m2);
		System.out.println("-- mensaje 'm2' registrado: " + m2);

		System.out.println("\nRecuperar mensajes");
		System.out.println("-- recuperado m1: " + adaptadorM.recuperarMensaje(m1.getCodigo()));
		System.out.println("-- recuperado m2: " + adaptadorM.recuperarMensaje(m2.getCodigo()));
		
		System.out.println("\nModificar Mensajes");
		m1.setTexto("Texto cambiado");
		adaptadorM.actualizarMensaje(m1);
		System.out.println("--modificado m1: " + adaptadorM.recuperarMensaje(m1.getCodigo()));
		
		
		// Contactos individuales
		System.out.println("\nRegistrar ContactosIndividuales");
		adaptadorCI.registrarContactoIndividual(ind1);
		System.out.println("-- contacto 'ind1' registrado: " + ind1);
		adaptadorCI.registrarContactoIndividual(ind2);
		System.out.println("-- contacto 'ind2' registrado: " + ind2);
		
		System.out.println("\nRecuperar ContactosInvididuales");
		adaptadorCI.recuperarContactoIndividual(ind1.getCodigo());
		System.out.println("-- recuperado ind1: " + ind1);
		adaptadorCI.recuperarContactoIndividual(ind2.getCodigo());
		System.out.println("-- recuperado ind2: " + ind2);

		System.out.println("\nModificar ContactosInvididuales");
		ind1.setNombre("edupema");
		adaptadorCI.actualizarContactoIndividual(ind1);
		System.out.println("--modificado ind1: " + adaptadorCI.recuperarContactoIndividual(ind1.getCodigo()));
		
		/*System.out.println("\nPrueba POOLcliente");
		System.out.println("añado cliente usu1 al pool");
		PoolDAO.getUnicaInstancia().addObjeto(usu1.getCodigo(), usu1);
		System.out.println("pool contiene usu1=" + PoolDAO.getUnicaInstancia().contiene(usu1.getCodigo()));
		// System.exit(0);*/
		

		
		
		// Limpiar
		adaptadorU.borrarUsuario(usu1);
		adaptadorU.borrarUsuario(usu2);
		adaptadorCI.borrarContactoIndividual(ind1);
		adaptadorCI.borrarContactoIndividual(ind2);
		adaptadorM.borrarMensaje(m1);
		adaptadorM.borrarMensaje(m2);
		
		System.out.println("\nContactos residuales:");
		for (ContactoIndividual c : adaptadorCI.recuperarTodosContactoIndividuals()) {
			System.out.println("\t" + c);
		}
		System.out.println("\nMensajes residuales:");
		for (Mensaje m : adaptadorM.recuperarTodosMensajes()) {
			System.out.println("\t" + m);
		}
		System.out.println("\tUsuarios residuales:");
		for (Usuario u : adaptadorU.recuperarTodosUsuarios()) {
			System.out.println("\t" + u);
		}
		System.out.println("\tGrupos residuales:");
		for (ContactoGrupo g : adaptadorCG.recuperarTodosContactoGrupos()) {
			System.out.println("\t" + g);
		}

		
		// test ContactoGrupo
		/*adaptadorCG.registrarContactoGrupo(gr1);
		System.out.println("-- registrado grupo g1 con codigo: " + gr1.getCodigo());
		adaptadorCG.registrarContactoGrupo(gr2);
		System.out.println("-- registrado grupo g2 con codigo: " + gr2.getCodigo());
		
		System.out.println(gr1.getCodigo() + " " + gr1.getNombre() +  " " + gr1.getAdmin() + " " + gr1.getMiembros());
		System.out.println(gr2.getCodigo() + " " + gr2.getNombre() +  " " + gr2.getAdmin() + " " + gr2.getMiembros());
		*/
		
		
		// listar
		/*
		Cliente auxC;
		System.out.println("recupero cliente " + c1.getCodigo());
		auxC = adaptadorCliente.recuperarCliente(c1.getCodigo());
		System.out.println("DNI:" + auxC.getDni());
		System.out.println("Nombre" + auxC.getNombre());
		System.out.println("Ventas-----");
		for (Venta v : auxC.getVentas()) {
			System.out.println("codigo:" + v.getCodigo() + "  Total=" + v.getTotal());
		}
		*/
	}

}
