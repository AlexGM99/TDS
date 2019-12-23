package controlador;

import interfazGrafica.LogIn;

public class LanzadorIni {
	private static ControladorVistaAppChat controlador;
	public static void main(String[] args) {
		controlador = ControladorVistaAppChat.getUnicaInstancia();
		LogIn window = new LogIn(controlador);
		controlador.setInterface(window);
	}
}
