package modelo;

import java.util.Date;

import tds.BubbleText;

// TODO Terminar de implementar
// TODO Emoticonos
// TODO ¿Guardar destinatarios?

public class Mensaje {
	private int codigo;
	private String texto;
	private String tlfEmisor;
	private Date hora;
	private BubbleText emoticon;
	//private _ emoticon;
	//private List<Contacto> receptores;

	public Mensaje(String texto, Date hora, String tlfEmisor) {
		this.codigo = 0;
		this.texto = texto;
		this.hora = hora;
		this.tlfEmisor = tlfEmisor;
		//this.receptores = new LinkedList<Contacto>();
		//Collections.addAll(this.receptores, receptores);

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

	public String getTlfEmisor() {
		return tlfEmisor;
	}

	public void setTlfEmisor(String tlfEmisor) {
		this.tlfEmisor = tlfEmisor;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [codigo=" + codigo + ", texto='" + texto + "', tlfEmisor=" + tlfEmisor + ", hora=" + hora + "]";
	}
	
	/*
	public void addReceptor(Contacto c) {
		receptores.add(c);
	}
	
	public void addReceptor(String nombre, String movil) {
		receptores.add(new ContactoIndividual(nombre, movil));
	}
	
	public void addReceptor(String nombre, Usuario admin) {
		receptores.add(new ContactoGrupo(nombre, admin));
	}
	
	public List<Contacto> getReceptores() {
		return Collections.unmodifiableList(receptores);
	}*/
}
