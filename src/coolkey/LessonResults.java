package coolkey;

/**
 * Wyniki ćwiczenia polegającego na przepisywaniu.
 */
public class LessonResults {

	private int mistakesCount;
	private int correctionsCount;
	private int totalCharsCount;
	private int writtenCharsCount;
	private int writingTimeMilliseconds;

	/**
	 * Utwórz nowe wyniki dla danej lekcji.
	 *
	 * @param lesson Lekcja której będą dotyczyły te wyniki.
	 */
	public LessonResults(Lesson lesson) {
		// policz wszystkie znaki do przepisania w lekcji
		totalCharsCount = 0;
		for (String line : lesson.getTextLines()) {
			totalCharsCount += line.length() + 1; // +1 za Enter
		}
		// policz wszystkie przepisane znaki (również błędne)
		writtenCharsCount = 0;
		for (String line : lesson.getWrittenLines()) {
			writtenCharsCount += line.length() + 1; // +1 za Enter
		}
		// policz błędnie przepisane znaki
		mistakesCount = 0;
		for (int j=0; j < lesson.getMistakes().size(); j++) {
			String line = lesson.getMistakes().get(j);
			for (int i=0; i < line.length(); i++) {
				if (line.charAt(i) != ' ') {
					mistakesCount++;
				}
			}
			if (line.length() != lesson.getTextLines().get(j).length()) {
				mistakesCount++; // za źle przepisany Enter (za długa linia)
			}
		}
		// policz poprawione znaki
		correctionsCount = 0;
		for (String line : lesson.getCorrections()) {
			for (int i=0; i < line.length(); i++) {
				if (line.charAt(i) != ' ') {
					correctionsCount++;
				}
			}
		}
		// policz czas pisania
		writingTimeMilliseconds = lesson.getWritingTimeMilliseconds();
	}

	/**
	 * Prędkość pisania, biorąc pod uwagę błędnie przepisane znaki.
	 */
	public double getSpeed() {
		int charsCount = getTotalCharsCount();
		double timeMinutes = ((double)writingTimeMilliseconds) / 1000 / 60;
		return charsCount / timeMinutes;
	}

	/**
	 * Prędkość pisania, bez błędnie przepisanych znaków.
	 */
	public double getRealSpeed() {
		int charsCount = getTotalCharsCount() - getMistakesCount();
		double timeMinutes = ((double)writingTimeMilliseconds) / 1000 / 60;
		return charsCount / timeMinutes;
	}

	/**
	 * Poprawność w procentach. Poprawki traktujemy jako błędy.
	 */
	public double getCorrectness() {
		return ((double)(writtenCharsCount - mistakesCount - correctionsCount))
		/ totalCharsCount * 100;
	}

	/**
	 * Ilość wszystkich znaków jakie są do przepisania w lekcji.
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
	 * Ilość sekund jakie minęły od rozpoczęcia do zakończenia pisania, albo
	 * do chwili obecnej jeśli nie zakończono jeszcze pisania.
	 */
	public int getWritingTimeSeconds() {
		return writingTimeMilliseconds / 1000;
	}

	public String toString() {
		int minutes = getWritingTimeSeconds() / 60;
		int seconds = getWritingTimeSeconds() % 60;
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
