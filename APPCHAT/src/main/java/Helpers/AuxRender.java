package Helpers;

public class AuxRender {
	public static String PATTEREMOJI = "^:\\d+$";
	
	//Devuelve el emoji en formato texto
	public static String setEmojiAsText(int codigoEmoji) {
		return ":"+ String.valueOf(codigoEmoji);
	}
}
