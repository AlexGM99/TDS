package Helpers;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Mensaje;
import tds.BubbleText;

public class AuxRender {
	public static String PATTEREMOJI = "^:\\d+$";
	
	//Devuelve el emoji en formato texto
	public static String setEmojiAsText(int codigoEmoji) {
		return ":"+ String.valueOf(codigoEmoji);
	}
	
	// devuelve si el texto corresponde a un emoji
	public static Boolean isEmoji(String text) {
		if (text.matches(PATTEREMOJI))
			return true;
		return false;
	}
	
	// obtiene el numero de emoji correspondiente a un mensaje
	public static int getNumEmoji(String text) {
		String num = text.substring(1);
		return Integer.parseInt(num);
	}
	
	// muestra todos los mensajes en formato burbuja en el panel dado
	public static LinkedList<BubbleText> getBurbujas(JPanel c, LinkedList<Mensaje> mensajes, String mimovil, Contacto cont, boolean grupo, List<ContactoIndividual> contactos){
		LinkedList<BubbleText> burbujas = new LinkedList<BubbleText>();
		for (Mensaje mensaje : mensajes) {
			boolean enviado = mimovil.equals(mensaje.getTlfEmisor()); // lo he enviado yo?
			BubbleText bubble;
			String emisor = mensaje.getTlfEmisor();
			if(!grupo && !enviado) // es un grupo
				emisor = cont.getNombre();
			else if (!enviado){ // si no lo he enviado yo
				try {
				 	emisor = contactos.stream().filter( contactito -> contactito.getMovil().equals(mensaje.getTlfEmisor())).findFirst().get().getNombre();//busco el emisor
				}
				catch (Exception e) {
				}
			}
			if (isEmoji(mensaje.getTexto())) // si es un emoji, enviamos el emoji
			{
				int emoji = getNumEmoji(mensaje.getTexto());
				if (enviado) {
					bubble = new BubbleText(c, emoji, Color.GREEN, "Yo", BubbleText.SENT, 16); // emisor yo
				}
				else {
					bubble = new BubbleText(c, emoji, Color.LIGHT_GRAY, emisor, BubbleText.RECEIVED, 16); // receptor yo
				}
			}
			else {
				if (enviado) { // texto normal
					bubble = new BubbleText(c, mensaje.getTexto(), Color.GREEN, "Yo", BubbleText.SENT, 16); // emisor yo

				}
				else {
					bubble = new BubbleText(c, mensaje.getTexto(), Color.LIGHT_GRAY, emisor, BubbleText.RECEIVED, 16); // receptor yo
				}
			}
			c.add(bubble); // añade burbuja al panel
			burbujas.add(bubble);
		}
		return burbujas;
	}
}
