package Descuentos;


public class DescuentoSimple {

	private String name;
	private String cantidad;
	
	private String letraPequena;
	
	@Override
	public String toString() {
		return "DescuentoSimple [name=" + name + ", cantidad=" + cantidad + ", descuentoName=" + letraPequena + "]";
	}

	public DescuentoSimple(String name, String cantidad, String letraPequena) {
		this.name = name;
		this.cantidad = cantidad;
		this.letraPequena = letraPequena;
	}
	public DescuentoSimple() {
		
	}
	
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	public void setLetraPequena(String letraPequena) {
		this.letraPequena = letraPequena;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCantidad() {
		return cantidad;
	}
	public String getLetraPequena() {
		return letraPequena;
	}
	public String getName() {
		return name;
	}
}
