package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import Descuentos.DescuentoCompuesto;
import Descuentos.DescuentoSimple;

public class ControladorDescuentos {
	// Windows
	//private static String ARCHIVO =  "\\src\\main\\java\\Descuentos\\Descuentos.txt";
	// Ubuntu
	private static String ARCHIVO =  "/src/main/java/Descuentos/Descuentos.txt";
	private static String DESCUENTOS = "1;Descuento Verano;10.0;Entregas también tu alma a cambio\n" + 
			"0;Invierno;5.0;Tienes que ser muy guapo\n" + 
			"1;Oferta;5.0;Vendes tu cuerpecito al diablo\n" + 
			"";
	// Obtiene los descuentos de un string constante
	private List<DescuentoSimple> getDescuentos(){
		List<DescuentoSimple> desc = new LinkedList<DescuentoSimple>();
		String[] descuento = DESCUENTOS.split("\n");
		for(String linea : descuento) {
			String partes[] = linea.split(";");
			if (partes.length >= 4 && partes[0].equals("1")) {
				DescuentoSimple descuentoS = new DescuentoSimple();
				descuentoS.setName(partes[1]);
				descuentoS.setCantidad(partes[2]);
				descuentoS.setLetraPequena(partes[3]);

				desc.add(descuentoS);
			}
		}
		return desc;
	}
	
	// Lee los descuentos del fichero dedicado a ello
	private List<DescuentoSimple> getDescuentosFromFile(){
		List<DescuentoSimple> desc = new LinkedList<DescuentoSimple>();
		File miDir = new File (".");
		String local="";
	     try {
	       local = miDir.getCanonicalPath();
	     }
	     catch(Exception e) {
	       e.printStackTrace();
	    }
		File archivo = new File (local+ARCHIVO);
		FileReader fr = null;
		try {
			fr = new FileReader (archivo);
			BufferedReader br = new BufferedReader(fr);
			try {
				String linea = "";
				while ((linea=br.readLine())!=null) {
					String[] partes = linea.split(";");
					if (partes.length >= 4 && partes[0].equals("1")) {
						DescuentoSimple descuento = new DescuentoSimple(partes[1], partes[2], partes[3]);
						desc.add(descuento);
					}
					
				}
			} catch (IOException e) {
			}
		} catch (FileNotFoundException e1) {
		}
		finally {
			try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
		}
		return desc;
	}
	
	//Función que obtiene los descuentos usando los lectores de descuentos, ahora mismo funciona con el lector de fichero
	public DescuentoCompuesto getDescuentosActuales(){
		DescuentoCompuesto descuentos;
		List<DescuentoSimple> d = getDescuentosFromFile();
		descuentos = new DescuentoCompuesto();
		d.stream().forEach( nuevo -> descuentos.nuevoDescuento(nuevo));
		return descuentos;
	}
	
}
