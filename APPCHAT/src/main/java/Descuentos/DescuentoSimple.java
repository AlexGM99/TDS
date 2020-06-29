package Descuentos;

import java.util.List;

public class DescuentoSimple {

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
