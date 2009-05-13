package coolkey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
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
	public static List<String> words(String txtDirectory) {
		File[] files = new File(txtDirectory).listFiles(new TextfileFilter());
		List<String> corpus = new ArrayList<String>();
		try {
			for (File file : files) {
				for (String line : Utils.readFileAsString(file, "UTF-8").
						replaceAll(" +", " ").replace(")", "").
						replace("(", "").replace("\"", "").
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
	 * Filtruje podaną listę słów ze względu na podane znaki.
	 *
	 * @param  words  Lista słów do przefiltrowania.
	 * @param  filter Lista znaków, z których co najmniej jeden musi wystąpić
	 *                w wynikowej liście. Wyjątek: słowa występujące
	 *                na początku lub na końcu zdania (zakończone kropką).
	 * @return        Nowa lista słów, powstała przez przefiltrowanie
	 *                oryginalnej, lub oryginalna lista, jeśli za filtr
	 *                podano <code>null</code>.
	 */
	public static List<String> filter(List<String> words, String filter) {
		if (filter == null) {
			return words;
		}
		List<String> filteredWords = new ArrayList<String>();
		filteredWords.add(words.get(0));
		for (int i=1; i < words.size(); i++) {
			boolean filterMatches = false;
			for (int j=0; j < filter.length(); j++) {
				if (words.get(i).indexOf(filter.charAt(j)) != -1) {
					filterMatches = true;
				}
			}
			if (words.get(i).endsWith(".")) {
				filteredWords.add("" + words.get(i));
				if (i < words.size() - 1) {
					i++;
					filteredWords.add("" + words.get(i));
				}
				continue;
			}
			if (filterMatches) {
				filteredWords.add("" + words.get(i));
			}
		}
		return filteredWords;
	}

	/**
	 * Łączy reprezentacje napisowe elementów kolekcji w jeden napis
	 * za pomocą podanego separatora.
	 */
	public static String join(Collection<?> collection, String separator) {
		String s = "";
		for (Object item : collection) {
			s += item.toString() + separator;
		}
		return s.substring(0, s.length() - separator.length());
	}

	/**
	 * Czyta treść pliku i zwraca ją jako napis. Przy odczytywaniu pliku
	 * stosowane jest kodowanie znaków domyślne dla danej platrormy.
	 *
	 * @param  file Plik który chcemy odczytać.
	 * @return      Zawartość pliku jako napis.
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

	/**
	 * Czyta treść pliku i zwraca ją jako napis.
	 *
	 * @param  file     Plik który chcemy odczytać.
	 * @param  encoding Nazwa kodowania znaków w odczytywanym pliku.
	 * @return          Zawartość pliku jako napis.
	 */
	public static String readFileAsString(File file, String encoding)
		throws IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), encoding));
		char[] buf = new char[1024];
		int numRead=0;
		while((numRead = reader.read(buf)) != -1){
			fileData.append(buf, 0, numRead);
		}
		reader.close();
		return fileData.toString();
	}
}

/**
 * Filtr sprawdzający, czy podane pliki mają rozszerzenie txt.
 */
class TextfileFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith(".txt");
	}
}
