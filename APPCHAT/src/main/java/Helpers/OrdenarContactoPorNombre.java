package Helpers;

import java.util.Comparator;

import modelo.Contacto;

public class OrdenarContactoPorNombre implements Comparator<Contacto>{
	 @Override
	    public int compare(Contacto o1, Contacto o2) {
	    return o1.getNombre().compareTo(o2.getNombre()); 
	    }

}
