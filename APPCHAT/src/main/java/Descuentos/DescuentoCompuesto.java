package Descuentos;

public class DescuentoCompuesto implements InterfazDescuentos{

	private DescuentoCompuesto compuesto;
	private DescuentoSimple simple;
	
	
	public DescuentoCompuesto getCompuesto() {
		return compuesto;
	}
	
	public DescuentoSimple getSimple() {
		return simple;
	}
}
