package coolkey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Statystyki prędkości i poprawności dla poszczególnych znaków. Statystyki
 * generowane są na podstawie wyników testów ukończonych przez użytkownika.
 */
public class Statistics {

	private Map<Character, Double> charSpeeds;
	private Map<Character, Double> charAccuracies;

	public Statistics(List<TestResults> resultsList) {
		// obliczamy prędkość dla poszczególnych znaków
		Map<Character, Long> totalCharCounts = new HashMap<Character, Long>();
		Map<Character, Long> totalCharTimes = new HashMap<Character, Long>();
		for (TestResults result : resultsList) {
			for (char c : result.getCharCounts().keySet()) {
				if (totalCharCounts.containsKey(c)) {
					totalCharCounts.put(c, totalCharCounts.get(c)
							+ result.getCharCounts().get(c));
				} else {
					totalCharCounts.put(c, result.getCharCounts().get(c));
				}
				if (totalCharTimes.containsKey(c)) {
					totalCharTimes.put(c, totalCharTimes.get(c)
							+ result.getCharTimes().get(c));
				} else {
					totalCharTimes.put(c, result.getCharTimes().get(c));
				}
			}
		}
		charSpeeds = new HashMap<Character, Double>();
		for (Character c : totalCharCounts.keySet()) {
			double timeMinutes = ((double)totalCharTimes.get(c)) / 1000 / 60;
			charSpeeds.put(c, totalCharCounts.get(c) / timeMinutes);
		}
		// obliczamy poprawność dla poszczególnych znaków
		Map<Character, Long> totalCharMistakes = new HashMap<Character, Long>();
		for (TestResults result : resultsList) {
			for (char c : result.getCharCounts().keySet()) {
				if (totalCharMistakes.containsKey(c)) {
					totalCharMistakes.put(c, totalCharMistakes.get(c)
							+ result.getCharMistakes().get(c));
				} else {
					totalCharMistakes.put(c, result.getCharMistakes().get(c));
				}
			}
		}
		charAccuracies = new HashMap<Character, Double>();
		for (Character c : totalCharCounts.keySet()) {
			charAccuracies.put(c, (1 - ((double)totalCharMistakes.get(c))
					/ totalCharCounts.get(c)) * 100);
		}
	}

	public Map<Character, Double> getCharSpeeds() {
		return charSpeeds;
	}

	public Map<Character, Double> getCharAccuracies() {
		return charAccuracies;
	}
}
