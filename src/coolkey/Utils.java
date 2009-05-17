package coolkey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Zawiera różnego rodzaju użyteczne metody statyczne.
 */
public class Utils {
	public static final TextfileFilter TEXTFILE_FILTER = new TextfileFilter();

	private Utils() {}

	/**
	 * Zwraca tekst zawarty w plikach tekstowych z danego katalogu w postaci
	 * listy słów.
	 */
	public static List<String> words(String txtDirectory) {
		File[] files = new File(txtDirectory).listFiles(TEXTFILE_FILTER);
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
	 * @param  filter Lista znaków. W każdym słowie w wynikowej liście musi
	 *                wystąpić co najmniej jeden znak z tej listy znaków.<br>
	 *                Wyjątek: słowa występujące na początku lub na końcu
	 *                zdania (zakończone kropką).
	 * @return        Nowa lista słów, powstała przez przefiltrowanie
	 *                oryginalnej, lub oryginalna lista, jeśli za filtr
	 *                podano <code>null</code>.
	 */
	public static List<String> filter(List<String> words, CharSequence filter) {
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

	/**
	 * Otwiera link w domyślnej przeglądarce systemu operacyjnego.
	 */
	public static void openInExternalBrowser(String url) {
		final String WIN_PATH = "rundll32";
		final String WIN_FLAG = "url.dll,FileProtocolHandler";
		final String UNIX_PATH = "firefox";
		String cmd = null;

		try {
			if (System.getProperty("os.name").startsWith("Windows")) {
				cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
				Runtime.getRuntime().exec(cmd);
			} else {
				// Domyślnie próbuję odpalić w Firefoksie.
				// Wysyłam komendę (cmd) i sprawdzam wartość na wyjściu.
				// Jeśli jest 0 to działa jeśli nie to trzeba odpalić
				// inną przeglądarkę.
				cmd = UNIX_PATH + " " + url;
				Process p = Runtime.getRuntime().exec(cmd);
				
				try	{
					// Czekam na 0 - jeśli jest 0 to działa,
					// jeśli nie to próbuję inaczej.
					int exitCode = p.waitFor();
					if (exitCode != 0) {
						// nie udało się, próbuję z Operą
						cmd = "opera" + " " + url;
						p = Runtime.getRuntime().exec(cmd);
					}
				} catch(InterruptedException x) {
					System.err.println("Error bringing up browser, cmd='" + cmd + "'");
					System.err.println("Caught: " + x);
				}
			}
		} catch(IOException e) {
			System.err.println("Could not invoke browser, command=" + cmd);
			e.printStackTrace();
		}
	}
}
