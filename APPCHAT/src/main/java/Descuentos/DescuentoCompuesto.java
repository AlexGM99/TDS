package Descuentos;

import java.util.LinkedList;
import java.util.List;

public class DescuentoCompuesto implements InterfazDescuentos{

	private DescuentoCompuesto compuesto;
	private DescuentoSimple simple;
	
	public DescuentoCompuesto(DescuentoSimple s, DescuentoCompuesto d) {
		simple = s;
		compuesto = d;
		
	}
	public DescuentoCompuesto() {}
	
	
	public DescuentoCompuesto getCompuesto() {
		return compuesto;
	}
	public DescuentoCompuesto(DescuentoSimple d) {
		this.simple = d;
	}
	
	public DescuentoSimple getSimple() {
		return simple;
	}

	@Override
	public String toString() {
		return "DescuentoCompuesto [compuesto=" + compuesto!=null?compuesto.toString():"" 
							+ ", simple=" + simple + "]";
	}
	
	public void nuevoDescuento(DescuentoSimple d) {
		if(this.simple==null)
			this.simple = d;
		else if (this.compuesto==null) 
			this.compuesto = new DescuentoCompuesto(d);
		else
			this.compuesto.nuevoDescuento(d);
	}
	
	@Override
	public List<DescuentoSimple> getdescuento() {
		List<DescuentoSimple> simples = new LinkedList<DescuentoSimple>();
		return null;
	}

}
