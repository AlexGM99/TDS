package modelo;

import java.time.Instant;
import java.util.Date;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoGrupoDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class Prueba {

	public static void main(String[] args) {

		FactoriaDAO miFactoria = null;

		try {
			miFactoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		IAdaptadorContactoIndividualDAO adaptadorCI = miFactoria.getContactoIndividualDAO();
		IAdaptadorContactoGrupoDAO adaptadorCG = miFactoria.getGrupoDAO();
		IAdaptadorUsuarioDAO adaptadorU = miFactoria.getUsuarioDAO();
		IAdaptadorMensajeDAO adaptadorM = miFactoria.getMensajeDAO();

		Usuario usu1 = new Usuario("Edu", Date.from(Instant.parse("1998-12-14T01:02:03Z")), "email-edu", "movil-edu", "edupema", "passEdu", "imagenEdu");
		Usuario usu2 = new Usuario("Alex", Date.from(Instant.parse("1998-12-14T01:02:03Z")), "email-alex", "movil-alex", "alexgm", "passAlex", "imagenAlex");
		
		// Usuarios
		System.out.println("\nRegistrar usuarios");
		adaptadorU.registrarUsuario(usu1);
		System.out.println("-- usuarios 'usu1' registrado: " + usu1);
		adaptadorU.registrarUsuario(usu2);
		System.out.println("-- usuarios 'usu1' registrado: " + usu2);
				
		System.out.println("\n Recuperar usuarios");
		System.out.println("-- recuperado usu1: " + adaptadorU.recuperarUsuario(usu1.getCodigo()));
		System.out.println("-- recuperado usu2: " + adaptadorU.recuperarUsuario(usu2.getCodigo()));
		
		
		ContactoIndividual ind1 = new ContactoIndividual("Edu", usu1.getMovil());
		ContactoIndividual ind2 = new ContactoIndividual("Alex", usu2.getMovil());
		usu2.addContacto(ind1);
		usu1.addContacto(ind2);
		
		ContactoGrupo gr1 = new ContactoGrupo("Grupo 1", usu1.getMovil());
		gr1.addMiembro(usu1.getMovil());
		gr1.setAdmin(usu1);
		usu1.addGrupo(gr1);
		ContactoGrupo gr2 = new ContactoGrupo("Grupo 2", usu1.getMovil(), usu2.getMovil());
		gr2.addMiembro(usu1.getMovil());
		gr2.addMiembro(usu2.getMovil());
		gr2.setAdmin(usu2);
		usu2.addGrupo(gr2);
		
		Mensaje m1 = new Mensaje("Hola mundo", Date.from(Instant.now()), usu1.getMovil(), ind1, TipoContacto.INDIVIDUAL);
		Mensaje m2 = new Mensaje("Hello World", Date.from(Instant.now()), usu2.getMovil(), ind2, TipoContacto.INDIVIDUAL);

		// Mensajes
		System.out.println("\nRegistrar mensajes");
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
		ind1.addMensaje(m1);
		ind2.addMensaje(m2);
		System.out.println("\nRegistrar ContactosIndividuales");
		adaptadorCI.registrarContactoIndividual(ind1);
		System.out.println("-- contacto 'ind1' registrado: " + ind1);
		adaptadorCI.registrarContactoIndividual(ind2);
		System.out.println("-- contacto 'ind2' registrado: " + ind2);
		
		System.out.println("\nRecuperar ContactosInvididuales");
		System.out.println("-- recuperado ind1: " + adaptadorCI.recuperarContactoIndividual(ind1.getCodigo()));
		System.out.println("-- recuperado ind2: " + adaptadorCI.recuperarContactoIndividual(ind2.getCodigo()));

		System.out.println("\nModificar ContactosInvididuales");
		ind1.setNombre("edupema");
		adaptadorCI.actualizarContactoIndividual(ind1);
		System.out.println("--modificado ind1: " + adaptadorCI.recuperarContactoIndividual(ind1.getCodigo()));
		
		// Grupos
		System.out.println("\nRegistrar Grupos");
		adaptadorCG.registrarContactoGrupo(gr1);
		System.out.println("-- grupo 'gr1' registrado: " + gr1);
		adaptadorCG.registrarContactoGrupo(gr2);
		System.out.println("-- grupo 'gr2' registrado: " + gr2);
		
		System.out.println("\nRecuperar Grupos");
		System.out.println("-- recuperado gr1: " + adaptadorCG.recuperarContactoGrupo(gr1.getCodigo()));
		System.out.println("-- recuperado gr2: " + adaptadorCG.recuperarContactoGrupo(gr2.getCodigo()));
		
		gr1.addMiembro(usu2.getMovil());
		gr1.addMiembro("1234");
		adaptadorCG.actualizarContactoGrupo(gr1);
		System.out.println("-- actualizado gr1: " + adaptadorCG.recuperarContactoGrupo(gr1.getCodigo()));

		
		adaptadorU.actualizarUsuario(usu1);
		adaptadorU.actualizarUsuario(usu2);
		
		System.out.println("\n Recuperar usuarios");
		System.out.println("-- recuperado usu1: " + adaptadorU.recuperarUsuario(usu1.getCodigo()));
		System.out.println("-- recuperado usu2: " + adaptadorU.recuperarUsuario(usu2.getCodigo()));
		
		
		// Limpiar
		adaptadorU.borrarUsuario(usu1);
		adaptadorU.borrarUsuario(usu2);
		adaptadorCG.borrarContactoGrupo(gr1);
		adaptadorCG.borrarContactoGrupo(gr2);
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
		System.out.println("\nUsuarios residuales:");
		for (Usuario u : adaptadorU.recuperarTodosUsuarios()) {
			System.out.println("\t" + u);
		}
		System.out.println("\nGrupos residuales:");
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
