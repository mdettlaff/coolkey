package coolkey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Statystyki prędkości i poprawności. Statystyki generowane są na podstawie
 * wyników testów ukończonych przez użytkownika.
 */
public class Statistics {

	private List<Double> speeds;
	private List<Double> realSpeeds;
	private List<Double> accuracies;
	private Map<Character, Double> charSpeeds;
	private Map<Character, Double> charAccuracies;

	public Statistics(List<TestResults> resultsList) {
		// prędkość i poprawność w kolejnych testach
		speeds = new ArrayList<Double>();
		realSpeeds = new ArrayList<Double>();
		accuracies = new ArrayList<Double>();
		for (TestResults results : resultsList) {
			speeds.add(results.getSpeed());
			realSpeeds.add(results.getRealSpeed());
			accuracies.add(results.getCorrectness());
		}
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

	/**
	 * Prędkość w kolejnych ukończonych testach.
	 */
	public List<Double> getSpeeds() {
		return speeds;
	}

	/**
	 * Realna (tzn. nie uwzględniająca błędnie przepisanych znaków) prędkość
	 * w kolejnych ukończonych testach.
	 */
	public List<Double> getRealSpeeds() {
		return realSpeeds;
	}

	/**
	 * Poprawność (w procentach) w kolejnych ukończonych testach.
	 */
	public List<Double> getAccuracies() {
		return accuracies;
	}

	/**
	 * Prędkość dla poszczególnych znaków.
	 */
	public Map<Character, Double> getCharSpeeds() {
		return charSpeeds;
	}

	/**
	 * Poprawność (w procentach) dla poszczególnych znaków.
	 */
	public Map<Character, Double> getCharAccuracies() {
		return charAccuracies;
	}
}
