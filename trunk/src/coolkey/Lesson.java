package coolkey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Ćwiczenie polegające na przepisywaniu zadanego tekstu.
 */
public class Lesson {

	private List<String> textLines = new ArrayList<String>();
	private List<String> writtenLines = new ArrayList<String>();
	private List<String> mistakes = new ArrayList<String>();
	private List<StringBuilder> mistakesShadow = new ArrayList<StringBuilder>();
	private Date timeStarted;
	private Date timeFinished;
	private boolean isPaused;
	private List<Date> timesPaused = new ArrayList<Date>();

	/**
	 * Nowe ćwiczenie polegające na przepisaniu podanego tekstu.
	 *
	 * @param text Tekst do przepisania.
	 */
	public Lesson(String text) {
		List<String> textWords = new ArrayList<String>();
		// podziel tekst na słowa
		for (String line : text.replaceAll(" +", " ").
				trim().split("\n")) {
			for (String word : line.split(" ")) {
				textWords.add(word.trim());
			}
		}
		// podziel tekst na linie
		String line = "";
		for (String word : textWords) {
			if (line.length() + word.length() > CoolKey.MAX_CHARS_IN_LINE) {
				textLines.add(line.trim());
				line = "";
			}
			line += word + " ";
		}
		if (line.length() > 0) {
			textLines.add(line.trim());
		}

		writtenLines.add("");
		mistakes.add("");
		mistakesShadow.add(new StringBuilder());
		isPaused = false;
	}

	/**
	 * Przepisanie pojedynczego znaku.
	 */
	public void typeChar(Character c) {
		if (timeStarted == null) {
			timeStarted = new Date();
		}
		if (timeFinished != null) {
			return;
		}
		int last = writtenLines.size() - 1;
		char correctChar;
		char incorrectChar;
		if (writtenLines.get(last).length() < textLines.get(last).length() &&
				textLines.get(last).charAt(writtenLines.get(last).length()) == c) {
			correctChar = c;
			incorrectChar = ' ';
		} else {
			correctChar = ' ';
			if (c != ' ') {
				incorrectChar = c;
			} else {
				incorrectChar = '_';
			}
		}
		writtenLines.set(last, writtenLines.get(last) + correctChar);
		mistakes.set(last, mistakes.get(last) + incorrectChar);
		if (writtenLines.get(last).length() > mistakesShadow.get(last).length()) {
			mistakesShadow.get(last).append(incorrectChar);
		} else if (writtenLines.get(last).length() > textLines.get(last).length()
				|| textLines.get(last).charAt(writtenLines.get(last).length() - 1) != c) {
			int cIndex = writtenLines.get(last).length() - 1;
			mistakesShadow.get(last).replace(cIndex, cIndex + 1, c + "");
		}
	}

	public void typeEnter() {
		if (timeStarted == null || timeFinished != null) {
			return;
		}
		int last = writtenLines.size() - 1;
		if (writtenLines.get(last).length() >= textLines.get(last).length()) {
			if (writtenLines.size() < textLines.size()) {
				writtenLines.add("");
				mistakes.add("");
				if (mistakes.size() > mistakesShadow.size()) {
					mistakesShadow.add(new StringBuilder());
				}
			} else {
				timeFinished = new Date();
			}
		}
	}

	public void typeBackspace() {
		if (timeStarted == null || timeFinished != null) {
			return;
		}
		String lastLine = writtenLines.get(writtenLines.size() - 1);
		if (lastLine.length() > 0) {
			writtenLines.set(writtenLines.size() - 1,
					lastLine.substring(0, lastLine.length() - 1));
			mistakes.set(mistakes.size() - 1, mistakes.get(mistakes.size() - 1).
					substring(0, lastLine.length() - 1));
		} else if (writtenLines.size() > 1) {
			writtenLines.remove(writtenLines.size() - 1);
			mistakes.remove(mistakes.size() - 1);
		}
	}

	/**
	 * Rozpocznij lekcję od nowa.
	 */
	public void restart() {
		timeStarted = null;
		timeFinished = null;
		writtenLines.clear();
		mistakes.clear();
		mistakesShadow.clear();
		timesPaused.clear();
		writtenLines.add("");
		mistakes.add("");
		mistakesShadow.add(new StringBuilder());
		isPaused = false;
	}

	/**
	 * Tekst do przepisania w tej lekcji.
	 */
	public List<String> getTextLines() {
		return textLines;
	}

	/**
	 * Poprawny tekst przepisany przez użytkownika.
	 */
	public List<String> getWrittenLines() {
		// ukrywamy znaki pokrywające się z poprawkami
		List<String> wl = new ArrayList<String>();
		for (int j=0; j < writtenLines.size(); j++) {
			String line = "";
			for (int i=0; i < writtenLines.get(j).length(); i++) {
				if (mistakesShadow.get(j).charAt(i) != ' ' &&
						mistakes.get(j).charAt(i) == ' ') {
					line += ' ';
				} else {
					line += writtenLines.get(j).charAt(i);
				}
			}
			wl.add(line);
		}
		return wl;
	}

	/**
	 * Tekst z zaznaczonymi błędami (spacja oznacza brak błędu).
	 */
	public List<String> getMistakes() {
		return mistakes;
	}

	/**
	 * Znaki, które zostały przepisane błędnie a następnie poprawione.
	 */
	public List<String> getCorrections() {
		List<String> corrections = new ArrayList<String>();
		for (int j=0; j < writtenLines.size(); j++) {
			String line = "";
			for (int i=0; i < mistakes.get(j).length(); i++) {
				if (mistakesShadow.get(j).charAt(i) != ' ' &&
						mistakes.get(j).charAt(i) == ' ') {
					if (writtenLines.get(j).charAt(i) != ' ') {
						line += writtenLines.get(j).charAt(i);
					} else {
						line += '_'; // tak zaznacz poprawioną spację
					}
				} else {
					line += ' ';
				}
			}
			corrections.add(line);
		}
		return corrections;
	}

	/**
	 * Ilość milisekund jakie minęły od rozpoczęcia do zakończenia pisania,
	 * albo do chwili obecnej jeśli nie zakończono jeszcze pisania.
	 *
	 * @return Czas pisania w milisekundach lub <code>-1</code> jeśli
	 *         nie rozpoczęto jeszcze pisania.
	 */
	public int getWritingTimeMilliseconds() {
		if (timeStarted == null) {
			return -1;
		} else {
			Date timeElapsed = timeFinished;
			if (timeFinished == null) {
				timeElapsed = new Date();
			}
			long interval = timeElapsed.getTime() - timeStarted.getTime();
			// odejmij czas, w którym lekcja była zapauzowana
			long pausedInterval = 0;
			for (int i=0; i < timesPaused.size(); i++) {
				if (i % 2 == 0) {
					pausedInterval -= timesPaused.get(i).getTime();
				} else {
					pausedInterval += timesPaused.get(i).getTime();
				}
			}
			if (isPaused) {
				pausedInterval += timesPaused.get(
						timesPaused.size() - 1).getTime();
			}
			interval -= pausedInterval;
			return new Long(interval).intValue();
		}
	}

	/**
	 * Wyniki z tej lekcji (prędkość, poprawność itp.).
	 */
	public LessonResults getResults() {
		return new LessonResults(this);
	}

	/**
	 * Następny znak, który należy przepisać.
	 *
	 * @return Kolejny znak. Jeśli to Enter, zwraca <code>'\r'</code>.
	 */
	public char getNextChar() {
		int last = writtenLines.size() - 1;
		if (writtenLines.get(last).length() < textLines.get(last).length()) {
			return textLines.get(last).charAt(writtenLines.get(last).length());
		} else {
			return '\r';
		}
	}

	/**
	 * Czy popełniono błąd przy przepisywaniu.
	 *
	 * @return <code>true</code>, jeśli ilość błędów jest większa od 0,
	 *         <code>false</code> w przeciwnym wypadku.
	 */
	public boolean isMistakeMade() {
		for (String line : CoolKey.getCurrentLesson().getMistakes()) {
			for (int i=0; i < line.length(); i++) {
				if (line.charAt(i) != ' ') {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Sprawdź czy lekcja się rozpoczęła.
	 *
	 * @return <code>true</code> jeśli lekcja się rozpoczęła,
	 *         <code>false</code> w przeciwnym wypadku.
	 */
	public boolean isStarted() {
		return timeStarted != null;
	}

	/**
	 * Sprawdź czy lekcja się zakończyła.
	 *
	 * @return <code>true</code> jeśli lekcja się już zakończyła,
	 *         <code>false</code> w przeciwnym wypadku.
	 */
	public boolean isFinished() {
		return timeFinished != null;
	}

	/**
	 * Zapauzuj lekcję, lub wznów jeśli jest zapauzowana.
	 */
	public void pauseUnpause() {
		isPaused = !isPaused;
		timesPaused.add(new Date());
	}

	public boolean isPaused() {
		return isPaused;
	}
}
