package ViewModels;

public class ViewModelUsuario implements ViewModelDatosChat{
	private String ruta;
	private String nombre; 
	private String tel;
	private String est;
	private String nick;
	
	public ViewModelUsuario(String ruta, String nombre, String tel, String est, String nick) {
		this.ruta = ruta;
		this.nombre = nombre;
		this.tel = tel;
		this.est = est;
		this.nick = nick;
	}
	public String getEst() {
		return est;
	}
	public String getNombre() {
		return nombre;
	}
	public String getRuta() {
		return ruta;
	}
	public String getTel() {
		return tel;
	}
	public String getNick() {
		return nick;
	}
}
