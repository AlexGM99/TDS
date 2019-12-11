package persistencia;

/*Esta clase implementa un pool para los adaptadores que lo necesiten*/

import java.util.Hashtable;

public class PoolDAO {
	private static PoolDAO unicaInstancia;
	private Hashtable<Integer, Object> pool;

	private PoolDAO() {
		pool = new Hashtable<Integer, Object>();
	}

	public static PoolDAO getUnicaInstancia() {
		if (unicaInstancia == null) unicaInstancia = new PoolDAO();
		return unicaInstancia;
		
	}
	
	public Object getObjeto(int id) {
		return pool.get(id);
	} // devuelve null si no encuentra el objeto

	public void addObjeto(int id, Object objeto) {
		pool.put(id, objeto);
	}

	public boolean contiene(int id) {
		return pool.containsKey(id);
	}
	
	//aux
	public void mostrarTodos() {
		System.out.println("\nPool:");
		for (Object it : pool.values()) {
			System.out.println("\t" + it);
		}
	}
}
