package modelo;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

// TODO Implementar

public class Mensaje {
	private int codigo;
	private String texto;
	private Date hora;
	// private _ emoticon;
	private Usuario emisor;
	private List<Contacto> receptores;

	public Mensaje(String texto, Date hora, Usuario emisor, Contacto... receptores) {
		this.codigo = 0;
		this.texto = texto;
		this.hora = hora;
		this.emisor = emisor;
		this.receptores = new LinkedList<Contacto>();
		Collections.addAll(this.receptores, receptores);

	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Usuario getEmisor() {
		return emisor;
	}

	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}
	
	public List<Contacto> getReceptores() {
		return Collections.unmodifiableList(receptores);
	}
}
