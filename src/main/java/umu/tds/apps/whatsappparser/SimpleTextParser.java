package umu.tds.apps.whatsappparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

public class SimpleTextParser {

	// filePath - Ruta absoluta o relativa
	// (cualquier path válido para FielReader
	public static List<MensajeWhatsApp> parse(String filePath, String formatDate, Plataforma plataforma) throws IOException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate);

		FileReader input = new FileReader(filePath);
		BufferedReader bufRead = new BufferedReader(input);
		String line = null;

		LinkedList<MensajeWhatsApp> mensajes = new LinkedList<>();

		String fecha = null;
		String autor = null;
		String texto = null;
		List<String> lines = new LinkedList<>();

		while ((line = bufRead.readLine()) != null) {
			lines.add(line);
		}
		bufRead.close();
		int textNumber = 0;
		System.out.println("Reading texts");
		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);

			switch (plataforma) {
			case ANDROID:
				if (line.indexOf("-") == -1)
					continue;
				fecha = line.substring(0, line.indexOf("-"));
				line = line.substring(line.indexOf("-") + 2);
				break;
			case IOS:
				fecha = line.substring(1, line.indexOf("]"));
				line = line.substring(line.indexOf("]") + 2);
				break;
			}
			LocalDateTime localDate = LocalDateTime.parse(fecha.trim(), formatter);

			if (line.indexOf(":") == -1)
				continue;
			autor = line.substring(0, line.indexOf(":"));
			texto = line.substring(line.indexOf(":") + 1);

			while (i + 1 < lines.size() && !nextLineIsDate(plataforma, formatter, lines.get(i + 1))) {
				i++;
				texto += " " + lines.get(i);
			}
			textNumber++;
			System.out.print(".");
			if (textNumber % 10 == 0)
				System.out.println("[" + textNumber + "]");
			mensajes.add(new MensajeWhatsApp(texto, autor, localDate));
		}
		System.out.println("Done!");

		return mensajes;
	}

	private static boolean nextLineIsDate(Plataforma plataforma, DateTimeFormatter formatter, String nextLine) {
		switch (plataforma) {
		case IOS: return nextLine.startsWith("[");
		case ANDROID:
			try {
				if (nextLine.indexOf("-") == -1) return false;
				LocalDateTime.parse(nextLine.substring(0, nextLine.indexOf("-")).trim(), formatter); 
			}
			catch (DateTimeParseException e) {
				return false;
			}
			return true; 
		}
		
		return false;
	}

}
