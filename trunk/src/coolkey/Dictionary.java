package coolkey;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Słownik, z którego można pobierać słowa o zadanych własnościach.
 */
public class Dictionary {

	String[] words;
	Map<Integer, List<String>> wordsOfLength =
		new HashMap<Integer, List<String>>();
	Random random = new Random();

	/**
	 * Tworzy nowy słownik.
	 *
	 * @param source Plik tekstowy ze słowami. Każde słowo powinno znajdować
	 *               się w osobnej linii.
	 */
	public Dictionary(File source) {
		try {
			words = Utils.readFileAsString(source, "UTF-8").split("\n");
			for (String word : words) {
				if (wordsOfLength.containsKey(word.length())) {
					wordsOfLength.get(word.length()).add(word);
				} else {
					List<String> list = new ArrayList<String>();
					list.add(word);
					wordsOfLength.put(word.length(), list);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Zwróć losowo wybrane słowo ze słownika.
	 */
	public String randomWord() {
		return words[random.nextInt(words.length)];
	}

	/**
	 * Zwróć losowo wybrane słowo o zadanej długości.
	 *
	 * @return Słowo o zadanej długości. Jeśli słownik nie zawiera słowa
	 *         o podanej długości, zwraca <code>null</code>.
	 */
	public String randomWord(int length) {
		if (wordsOfLength.containsKey(length)) {
			List<String> list = wordsOfLength.get(length);
			return list.get(random.nextInt(list.size()));
		} else {
			return null;
		}
	}
}
