package coolkey;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
	 * Zwraca losowo wybrane słowo ze słownika.
	 */
	public String randomWord() {
		return words[random.nextInt(words.length)];
	}

	/**
	 * Zwraca losowo wybrane słowo o zadanej długości.
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

	/**
	 * Zwraca z podanego zbioru słów listę słów z możliwie dużą ilością
	 * wystąpień znaków z podanego zbioru znaków.
	 *
	 * @return Podzbiór słownika, lub cały słownik, jeśli za argument podano
	 *         <code>null</code>.
	 */
	public List<String> wordsContaining(String filter) {
		return wordsContaining(filter, words);
	}

	/**
	 * Zwraca ze słownika listę słów z możliwie dużą ilością wystąpień znaków
	 * z podanego zbioru znaków.
	 *
	 * @return Podzbiór słownika, lub cały słownik, jeśli za argument podano
	 *         <code>null</code>.
	 */
	public static List<String> wordsContaining(String filter, String[] words) {
		if (filter == null) {
			return Arrays.asList(words);
		}
		List<String> filteredWords = new ArrayList<String>();
		for (String word : words) {
			int filterMatchesCount = 0;
			for (int i=0; i < word.length(); i++) {
				if (filter.indexOf(word.charAt(i)) != -1) {
					filterMatchesCount++;
				}
			}
			if (word.length() < 5 && filterMatchesCount > 0
					|| word.length() < 7 && filterMatchesCount > 1
					|| word.length() < 10 && filterMatchesCount > 2
					|| filterMatchesCount > 3) {
				filteredWords.add(word);
			}
		}
		return filteredWords;
	}

	/**
	 * Zwraca listę słów składających się wyłącznie z podanych liter.
	 */
	public List<String> wordsConsistingOf(String chars) {
		List<String> matches = new ArrayList<String>();
		for (String word : words) {
			boolean isMatching = true;
			for (int i=0; i < word.length(); i++) {
				if (chars.indexOf(word.charAt(i)) == -1) {
					isMatching = false;
					break;
				}
			}
			if (isMatching) {
				matches.add(word);
			}
		}
		return matches;
	}
}
