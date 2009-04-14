package coolkey;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Zawiera generator łańcucha Markowa na podstawie zadanego tekstu.
 */
public class Markov {
	private Markov() {}

	/**
	 * Generuje automatycznie tekst na podstawie podanej listy słów.
	 * Wygenerowany tekst jest tworzony przy pomocy łańcucha Markowa.
	 *
	 * @param  corpus     Tekst zdefiniowany jako ciąg słów, na podstawie
	 *                    którego generowany jest łańcuch Markowa.
	 * @param  minTextLen Minimalna długość (w znakach) tekstu do wygenerowania.
	 * @return            Wygenerowany za pomocą łańcucha Markowa tekst.
	 */
	public static String generateMarkovChain(List<String> corpus, int minTextLen)
	{
		List<String> markovChain = new LinkedList<String>();
		String markovText = new String(); // tekst, który zwróci metoda
		Random rand = new Random(new Date().getTime());

		// zaczynamy od słowa z początku zdania
		List<String> firstWords = new LinkedList<String>(); // początki zdań
		for (int i=0; i < corpus.size()-1; i++) {
			if (corpus.get(i).endsWith(".")) {
				firstWords.add(corpus.get(i+1));
			}
		}
		markovChain.add(firstWords.get(rand.nextInt(firstWords.size())));
		markovText += markovChain.get(0) + " ";

	    while (!(markovText.length() > minTextLen && markovText.endsWith(". "))) {
			// słowa występujące w danym tekście zaraz po ostatnim słowie z
			// łańcucha
			List<String> words = new LinkedList<String>();
			// słowa występujące w danym tekście zaraz po dwóch ostatnich słowach
			// z łańcucha
			List<String> doubleWords = new LinkedList<String>();
			for (int i=0; i < corpus.size()-1; i++) {
				if (corpus.get(i).equals(markovChain.get(markovChain.size()-1))) {
					words.add(corpus.get(i+1));
					if (i > 0 && markovChain.size() > 1 && corpus.get(i-1).
							equals(markovChain.get(markovChain.size()-2))) {
						doubleWords.add(corpus.get(i+1));
					}
				}
			}
			if (doubleWords.size() > 1) {
				markovChain.add(doubleWords.get(rand.nextInt(doubleWords.size())));
			} else if (words.size() > 0) {
				markovChain.add(words.get(rand.nextInt(words.size())));
			} else {
				markovChain.add(firstWords.get(rand.nextInt(firstWords.size())));
			}
			markovText += markovChain.get(markovChain.size()-1) + " ";
		}

		return markovText.trim();
	}
}
