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
import Descuentos.InterfazDescuentos;



public class ControladorDescuentos {

	private static String ARCHIVO =  "..\\Descuentos\\Descuentos.txt";
	
	private List<DescuentoSimple> getDescuentos(){
		List<DescuentoSimple> desc = new LinkedList<DescuentoSimple>();
		File archivo = new File (ARCHIVO);
		FileReader fr = null;
		try {
			fr = new FileReader (archivo);
			BufferedReader br = new BufferedReader(fr);
			try {
				String linea = "";
				while ((linea=br.readLine())!=null) {
					String[] partes = linea.split(";");
					if (partes.length >= 4 && partes[0]=="1") {
						DescuentoSimple descuento = new DescuentoSimple(partes[1], Double.parseDouble(partes[2]), partes[3]);
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
	
	
	public DescuentoCompuesto getDescuentosActuales(){
		DescuentoCompuesto descuentos;
		List<DescuentoSimple> d = getDescuentos();
		descuentos = new DescuentoCompuesto();
		d.stream().forEach( nuevo -> descuentos.nuevoDescuento(nuevo));
		return descuentos;
	}
	
}
