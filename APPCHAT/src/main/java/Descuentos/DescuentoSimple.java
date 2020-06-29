package Descuentos;

public class DescuentoSimple implements InterfazDescuentos {

	private String name;
	private double cantidad;
	
	private String letraPequena;
	
	@Override
	public String toString() {
		return "DescuentoSimple [name=" + name + ", cantidad=" + cantidad + ", descuentoName=" + letraPequena + "]";
	}

	public DescuentoSimple(String name, double cantidad, String letraPequena) {
		this.name = name;
		this.cantidad = cantidad;
		this.letraPequena = letraPequena;
	}
	
	@Override
	public String getdescuento() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public double getCantidad() {
		return cantidad;
	}
	public String getLetraPequena() {
		return letraPequena;
	}
	public String getName() {
		return name;
	}
	

}
