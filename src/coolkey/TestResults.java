package coolkey;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Wyniki ćwiczenia polegającego na przepisywaniu.
 */
public class TestResults implements Serializable {
	private static final long serialVersionUID = 1L;

	private int mistakesCount;
	private int correctionsCount;
	private int totalCharsCount;
	private int writtenCharsCount;
	private int typingTimeMilliseconds;
	private Map<Character, Long> charCounts;
	private Map<Character, Long> charTimes;
	private Map<Character, Long> charMistakes;

	/**
	 * Utwórz nowe wyniki dla danego testu.
	 *
	 * @param test Test którego będą dotyczyły te wyniki.
	 */
	public TestResults(TypingTest test, Map<Character, Long> charCounts,
			Map<Character, Long> charTimes, Map<Character, Long> charMistakes)
	{
		// policz wszystkie znaki do przepisania
		totalCharsCount = 0;
		for (String line : test.getTextLines()) {
			totalCharsCount += line.length() + 1; // +1 za Enter
		}
		// policz wszystkie przepisane znaki (również błędne)
		writtenCharsCount = 0;
		for (String line : test.getWrittenLines()) {
			writtenCharsCount += line.length();
		}
		writtenCharsCount += test.getWrittenLines().size() - 1; // za Entery
		if (test.isFinished()) {
			writtenCharsCount++; // +1 za ostatni Enter
		}
		// policz błędnie przepisane znaki
		mistakesCount = 0;
		for (int j=0; j < test.getMistakes().size(); j++) {
			String line = test.getMistakes().get(j);
			for (int i=0; i < line.length(); i++) {
				if (line.charAt(i) != ' ') {
					mistakesCount++;
				}
			}
			if (line.length() != test.getTextLines().get(j).length()) {
				if (j < test.getMistakes().size() - 1) {
					mistakesCount++; // +1 za źle przepisany Enter
				} else if (test.isFinished()) {
					mistakesCount++; // +1 za ostatni źle przepisany Enter
				}
			}
		}
		// policz poprawione znaki
		correctionsCount = 0;
		for (String line : test.getCorrections()) {
			for (int i=0; i < line.length(); i++) {
				if (line.charAt(i) != ' ') {
					correctionsCount++;
				}
			}
		}
		// policz czas pisania
		typingTimeMilliseconds = test.getTypingTimeMilliseconds();
		// statystyki
		this.charCounts = new HashMap<Character, Long>(charCounts);
		this.charTimes = new HashMap<Character, Long>(charTimes);
		this.charMistakes = new HashMap<Character, Long>(charMistakes);
	}

	/**
	 * Prędkość pisania, biorąc pod uwagę błędnie przepisane znaki.
	 */
	public double getSpeed() {
		int charsCount = getWrittenCharsCount();
		double timeMinutes = ((double)typingTimeMilliseconds) / 1000 / 60;
		return charsCount / timeMinutes;
	}

	/**
	 * Prędkość pisania, bez błędnie przepisanych znaków.
	 */
	public double getRealSpeed() {
		int charsCount = getWrittenCharsCount() - getMistakesCount();
		double timeMinutes = ((double)typingTimeMilliseconds) / 1000 / 60;
		return charsCount / timeMinutes;
	}

	/**
	 * Poprawność w procentach. Poprawki traktujemy jako błędy.
	 */
	public double getCorrectness() {
		if (writtenCharsCount == 0) {
			return 100.;
		}
		return ((double)(writtenCharsCount - mistakesCount - correctionsCount))
		/ writtenCharsCount * 100;
	}

	/**
	 * Ilość wszystkich znaków jakie są do przepisania.
	 */
	public int getTotalCharsCount() {
		return totalCharsCount;
	}

	/**
	 * Ilość wszystkich przepisanych znaków (również błędnych).
	 */
	public int getWrittenCharsCount() {
		return writtenCharsCount;
	}

	/**
	 * Ilość nieprawidłowo przepisanych znaków.
	 */
	public int getMistakesCount() {
		return mistakesCount;
	}

	/**
	 * Ilość znaków, które zostały przepisane błędnie a następnie poprawione.
	 */
	public int getCorrectionsCount() {
		return correctionsCount;
	}

	/**
	 * Ilość milisekund jakie minęły od rozpoczęcia do zakończenia pisania,
	 * albo do chwili obecnej jeśli nie zakończono jeszcze pisania.
	 *
	 * @return Czas pisania w milisekundach lub <code>-1</code> jeśli
	 *         nie rozpoczęto jeszcze pisania.
	 */
	public int getTypingTimeMilliseconds() {
		return typingTimeMilliseconds;
	}

	/**
	 * Stopień ukończenia testu w procentach.
	 */
	public double getProgress() {
		double progress = ((double)writtenCharsCount) / totalCharsCount * 100;
		if (progress > 100) {
			return 100.;
		}
		return progress;
	}

	public Map<Character, Long> getCharCounts() {
		return charCounts;
	}

	public Map<Character, Long> getCharTimes() {
		return charTimes;
	}

	public Map<Character, Long> getCharMistakes() {
		return charMistakes;
	}

	public String toString() {
		int typingTimeSeconds = getTypingTimeMilliseconds() / 1000;
		int minutes = typingTimeSeconds / 60;
		int seconds = typingTimeSeconds % 60;
		String formatString = "prędkość: %.1f znaków/min\n"
			+ "realna prędkość: %.1f znaków/min\n"
			+ "poprawność: %.1f%% (%d błędów)\n"
			+ "%d znaków przepisanych w %d min %d s";
		return String.format(formatString,
				getSpeed(), getRealSpeed(),	getCorrectness(),
				getMistakesCount() + getCorrectionsCount(),
				getWrittenCharsCount() - getMistakesCount(),
				minutes, seconds);
	}
}
