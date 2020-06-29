package Descuentos;

public class DescuentoCompuesto implements InterfazDescuentos{

	private DescuentoCompuesto compuesto;
	private DescuentoSimple simple;
	
	public DescuentoCompuesto(DescuentoSimple s, DescuentoCompuesto d) {
		simple = s;
		compuesto = d;
		
	}
	
	public DescuentoCompuesto getCompuesto() {
		return compuesto;
	}
	
	public DescuentoSimple getSimple() {
		return simple;
	}

	@Override
	public String toString() {
		return "DescuentoCompuesto [compuesto=" + compuesto!=null?compuesto.toString():"" 
							+ ", simple=" + simple + "]";
	}

	@Override
	public String getdescuento() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
