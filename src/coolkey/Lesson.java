package coolkey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Ćwiczenie polegające na przepisywaniu zadanego tekstu.
 */
public class Lesson implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<String> textLines = new ArrayList<String>();
	private List<String> writtenLines = new ArrayList<String>();
	private List<String> mistakes = new ArrayList<String>();
	private List<StringBuilder> mistakesShadow = new ArrayList<StringBuilder>();
	private Date timeStarted;
	private Date timeFinished;
	private boolean isPaused;
	private List<Date> timesPaused = new ArrayList<Date>();
	private boolean isBlockedAtMistake = false;

	/**
	 * Nowe ćwiczenie polegające na przepisaniu podanego tekstu.
	 *
	 * @param text Tekst do przepisania.
	 */
	public Lesson(String text) {
		for (String line : text.replaceAll(" +", " ").replaceAll("\r", "")
				.trim().split("\n")) {
			textLines.add(line);
		}
		// łamanie linii
		int lineEndIndex = 0;
		for (int i=0; i < textLines.size(); i++) {
			for (int j=1; j < textLines.get(i).length()
					&& j <= CoolKey.MAX_CHARS_IN_LINE; j++) {
				if (textLines.get(i).charAt(j) == ' '
						|| textLines.get(i).charAt(j) == '\t') {
					lineEndIndex = j;
				}
				if (j == CoolKey.MAX_CHARS_IN_LINE) { // łamiemy linię
					textLines.add(i+1, textLines.get(i).substring(lineEndIndex + 1));
					textLines.set(i, textLines.get(i).substring(0, lineEndIndex));
				}
			}
		}

		writtenLines.add("");
		mistakes.add("");
		mistakesShadow.add(new StringBuilder());
		isPaused = false;
	}

	/**
	 * Przepisanie pojedynczego znaku.
	 *
	 * @return <code>true</code> jeśli znak został przepisany prawidłowo,
	 *         <code>false</code> w przeciwnym wypadku.
	 */
	public boolean typeChar(Character c) {
		boolean typedCorrectly = true;
		if (timeStarted == null) {
			timeStarted = new Date();
		}
		if (timeFinished != null) {
			return false;
		}
		int last = writtenLines.size() - 1;
		if (c == ' '
			&& CoolKey.getUser().getConfig().getLineBreakers().contains(" ")
			&& writtenLines.get(last).length()
				== textLines.get(last).length()) {
			// wymuś przejście do nowej linii
			String breakers = new String(
					CoolKey.getUser().getConfig().getLineBreakers());
			CoolKey.getUser().getConfig().setLineBreakers(breakers + '\n');
			typeEnter();
			CoolKey.getUser().getConfig().setLineBreakers(breakers);
			return true;
		}
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
			typedCorrectly = false;
		}
		if (CoolKey.getUser().getConfig().isContinueAtMistakes()) {
			writtenLines.set(last, writtenLines.get(last) + correctChar);
			mistakes.set(last, mistakes.get(last) + incorrectChar);
			if (writtenLines.get(last).length() > mistakesShadow.get(last).length()) {
				mistakesShadow.get(last).append(incorrectChar);
			} else if (writtenLines.get(last).length() > textLines.get(last).length()
					|| textLines.get(last).charAt(writtenLines.get(last).length() - 1) != c) {
				int cIndex = writtenLines.get(last).length() - 1;
				mistakesShadow.get(last).replace(cIndex, cIndex + 1, c + "");
			}
			isBlockedAtMistake = false;
		} else {
			if (typedCorrectly) {
				writtenLines.set(last, writtenLines.get(last) + correctChar);
				mistakes.set(last, mistakes.get(last) + incorrectChar);
				isBlockedAtMistake = false;
			} else {
				isBlockedAtMistake = true;
			}
			if (writtenLines.get(last).length() > mistakesShadow.get(last).length()) {
				mistakesShadow.get(last).append(incorrectChar);
			} else if (!typedCorrectly) {
				int cIndex = writtenLines.get(last).length();
				mistakesShadow.get(last).replace(cIndex, cIndex + 1, incorrectChar + "");
			}
		}
		return typedCorrectly;
	}

	public boolean typeEnter() {
		if (timeStarted == null || timeFinished != null) {
			return false;
		}
		if (!CoolKey.getUser().getConfig().getLineBreakers().contains("\n")) {
			return false;
		}
		int last = writtenLines.size() - 1;
		if (writtenLines.get(last).length() >= textLines.get(last).length()) {
			isBlockedAtMistake = false;
			if (writtenLines.size() < textLines.size()) {
				writtenLines.add("");
				mistakes.add("");
				if (mistakes.size() > mistakesShadow.size()) {
					mistakesShadow.add(new StringBuilder());
				}
			} else {
				timeFinished = new Date();
			}
			return true;
		} else {
			return false;
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
		isBlockedAtMistake = false;
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
		isBlockedAtMistake = false;
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
						line += '\u00A0'; // tak zaznacz poprawioną spację
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
		} else if (CoolKey.getUser().getConfig().getLineBreakers()
				.contains("\n")) {
			return '\r';
		} else {
			return ' ';
		}
	}

	/**
	 * Czy popełniono błąd przy przepisywaniu.
	 *
	 * @return <code>true</code>, jeśli ilość błędów jest większa od 0,
	 *         <code>false</code> w przeciwnym wypadku.
	 */
	public boolean isMistakeMade() {
		if (!CoolKey.getUser().getConfig().isContinueAtMistakes()
				&& isBlockedAtMistake) {
			return true;
		}
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