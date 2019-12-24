package modelo;

import java.util.Date;
import tds.BubbleText;

// TODO Terminar de implementar
// TODO Emoticonos

public class Mensaje {
	private int codigo;
	private String texto;
	private String tlfEmisor;
	private Date hora;
	private BubbleText emoticon;
	private Contacto receptor;
	private boolean grupo;

	public Mensaje(String texto, Date hora, String tlfEmisor, Contacto receptor, boolean grupo) {
		this.codigo = 0;
		this.texto = texto;
		this.hora = hora;
		this.tlfEmisor = tlfEmisor;
		this.receptor = receptor;
		this.grupo = grupo;
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

	public Contacto getReceptor() {
		return receptor;
	}

	public void setReceptor(Contacto receptor) {
		this.receptor = receptor;
	}

	public boolean isGrupo() {
		return grupo;
	}

	public void setGrupo(boolean grupo) {
		this.grupo = grupo;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [codigo=" + codigo + ", texto=" + texto + ", tlfEmisor=" + tlfEmisor + ", hora=" + hora
				+ ", emoticon=" + emoticon + ", receptor=" + receptor.getNombre() + ", grupo=" + grupo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mensaje other = (Mensaje) obj;
		if (codigo != other.codigo)
			return false;
		if (grupo != other.grupo)
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (receptor == null) {
			if (other.receptor != null)
				return false;
		} else if ( receptor.getCodigo() != (other.receptor.getCodigo()))
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		if (tlfEmisor == null) {
			if (other.tlfEmisor != null)
				return false;
		} else if (!tlfEmisor.equals(other.tlfEmisor))
			return false;
		return true;
	}
	
}
