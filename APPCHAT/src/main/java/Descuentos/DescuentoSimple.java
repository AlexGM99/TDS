package Descuentos;

public class DescuentoSimple implements InterfazDescuentos {

	private String name;
	private double cantidad;
	
	private String descuentoName;
	
	@Override
	public String toString() {
		return "DescuentoSimple [name=" + name + ", cantidad=" + cantidad + ", descuentoName=" + descuentoName + "]";
	}

	@Override
	public String getdescuento() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
