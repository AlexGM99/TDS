package Helpers;

import java.awt.Color;
import java.util.LinkedList;

import javax.swing.JPanel;

import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.Mensaje;
import modelo.Usuario;
import tds.BubbleText;

public class AuxRender {
	public static String PATTEREMOJI = "^:\\d+$";
	
	//Devuelve el emoji en formato texto
	public static String setEmojiAsText(int codigoEmoji) {
		return ":"+ String.valueOf(codigoEmoji);
	}
	
	
	public static Boolean isEmoji(String text) {
		if (text.matches(PATTEREMOJI))
			return true;
		return false;
	}
	
	public static int getNumEmoji(String text) {
		String num = text.substring(1);
		return Integer.parseInt(num);
	}
	
	public static LinkedList<BubbleText> getBurbujas(JPanel c, LinkedList<Mensaje> mensajes, String mimovil, Contacto cont, boolean grupo){
		LinkedList<BubbleText> burbujas = new LinkedList<BubbleText>();
		for (Mensaje mensaje : mensajes) {
			boolean enviado = mimovil.equals(mensaje.getTlfEmisor());
			BubbleText bubble;
			String emisor = mensaje.getTlfEmisor();
			if(!grupo)
				emisor = cont.getNombre();
			if (isEmoji(mensaje.getTexto()))
			{
				int emoji = getNumEmoji(mensaje.getTexto());
				if (enviado) {
					bubble = new BubbleText(c, emoji, Color.GREEN, "Yo", BubbleText.SENT, 16);
				}
				else {
					bubble = new BubbleText(c, emoji, Color.LIGHT_GRAY, emisor, BubbleText.RECEIVED, 16);
				}
			}
			else {
				if (enviado) {
					bubble = new BubbleText(c, mensaje.getTexto(), Color.GREEN, "Yo", BubbleText.SENT, 16);

				}
				else {
					bubble = new BubbleText(c, mensaje.getTexto(), Color.LIGHT_GRAY, emisor, BubbleText.RECEIVED, 16);
				}
			}
			c.add(bubble);
			burbujas.add(bubble);
		}
		return burbujas;
	}
}
