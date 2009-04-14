package coolkey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Zawiera różnego rodzaju użyteczne metody statyczne.
 */
public class Utils {
	private Utils() {}

	/**
	 * Zwraca tekst zawarty w plikach tekstowych z danego katalogu w postaci
	 * listy słów.
	 */
	public static List<String> getWords(String txtDirectory) {
		File[] files = new File(txtDirectory).listFiles(new TextfileFilter());
		List<String> corpus = new ArrayList<String>();
		try {
			for (File file : files) {
				for (String line : Utils.readFileAsString(file).replaceAll(" +", " ").
						replace(")", "").replace("(", "").replace("\"", "").
						trim().split("\n")) {
					for (String word : line.split(" ")) {
						corpus.add(word.trim());
					}
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return corpus;
	}

	/**
	 * Czyta treść pliku i zwraca ją jako napis.
	 *
	 * @param file Plik który chcemy odczytać.
	 * @return     Zawartość pliku jako napis.
	 */
	public static String readFileAsString(File file)
		throws IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(
				new FileReader(file));
		char[] buf = new char[1024];
		int numRead=0;
		while((numRead = reader.read(buf)) != -1){
			fileData.append(buf, 0, numRead);
		}
		reader.close();
		return fileData.toString();
	}
}
