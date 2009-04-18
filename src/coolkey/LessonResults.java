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
			writtenCharsCount += line.length();
		}
		writtenCharsCount += lesson.getWrittenLines().size() - 1; // za Entery
		if (lesson.isFinished()) {
			writtenCharsCount++; // +1 za ostatni Enter
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
				if (j < lesson.getMistakes().size() - 1) {
					mistakesCount++; // +1 za źle przepisany Enter
				} else if (lesson.isFinished()) {
					mistakesCount++; // +1 za ostatni źle przepisany Enter
				}
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
		int charsCount = getWrittenCharsCount();
		double timeMinutes = ((double)writingTimeMilliseconds) / 1000 / 60;
		return charsCount / timeMinutes;
	}

	/**
	 * Prędkość pisania, bez błędnie przepisanych znaków.
	 */
	public double getRealSpeed() {
		int charsCount = getWrittenCharsCount() - getMistakesCount();
		double timeMinutes = ((double)writingTimeMilliseconds) / 1000 / 60;
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
	 * Ilość milisekund jakie minęły od rozpoczęcia do zakończenia pisania,
	 * albo do chwili obecnej jeśli nie zakończono jeszcze pisania.
	 *
	 * @return Czas pisania w milisekundach lub <code>-1</code> jeśli
	 *         nie rozpoczęto jeszcze pisania.
	 */
	public int getWritingTimeMilliseconds() {
		return writingTimeMilliseconds;
	}

	public String toString() {
		int writingTimeSeconds = getWritingTimeMilliseconds() / 1000;
		int minutes = writingTimeSeconds / 60;
		int seconds = writingTimeSeconds % 60;
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
