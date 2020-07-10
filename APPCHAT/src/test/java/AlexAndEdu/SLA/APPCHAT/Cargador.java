package AlexAndEdu.SLA.APPCHAT;

import java.io.IOException;

import cargadorMensajes.SimpleTextParser;

public class Cargador {

	public static void main(String[] args) {
		
		try {
			System.out.println(SimpleTextParser.FORMAT_DATE_ANDROID_2.equals(SimpleTextParser.detectFormatDate("/home/edupema/Escritorio/chat-tds.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
