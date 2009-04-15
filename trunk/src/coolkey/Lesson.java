package coolkey;

import java.util.ArrayList;
import java.util.List;

/**
 * Ćwiczenie polegające na przepisywaniu zadanego tekstu.
 */
public class Lesson {

	private List<String> textLines = new ArrayList<String>();
	private List<String> writtenLines = new ArrayList<String>();
	private List<String> mistakes = new ArrayList<String>();
	private List<StringBuilder> mistakesShadow = new ArrayList<StringBuilder>();
	private int mistakesCount = 0;
	private boolean isFinished = false;

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
	}

	/**
	 * Przepisanie pojedynczego znaku.
	 */
	public void typeChar(Character c) {
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
			mistakesCount++;
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
		int last = writtenLines.size() - 1;
		if (writtenLines.size() == textLines.size()) {
			isFinished = true;
		} else if (writtenLines.get(last).length() >= textLines.get(last).length()) {
			writtenLines.add("");
			mistakes.add("");
			mistakesShadow.add(new StringBuilder());
		}
	}

	public void typeBackspace() {
		String lastLine = writtenLines.get(writtenLines.size() - 1);
		if (lastLine.length() > 0) {
			writtenLines.set(writtenLines.size() - 1,
					lastLine.substring(0, lastLine.length() - 1));
			if (!mistakes.get(mistakes.size() - 1).endsWith(" ")) {
				mistakesCount--;
			}
			mistakes.set(mistakes.size() - 1, mistakes.get(mistakes.size() - 1).
					substring(0, lastLine.length() - 1));
		} else if (writtenLines.size() > 1) {
			writtenLines.remove(writtenLines.size() - 1);
			mistakes.remove(mistakes.size() - 1);
			mistakesShadow.remove(mistakesShadow.size() - 1);
		}
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
		for (int j=0; j < mistakesShadow.size(); j++) {
			String line = "";
			for (int i=0; i < mistakes.get(j).length(); i++) {
				if (mistakesShadow.get(j).charAt(i) != ' ' &&
						mistakes.get(j).charAt(i) == ' ') {
					line += writtenLines.get(j).charAt(i);
				} else {
					line += ' ';
				}
			}
			corrections.add(line);
		}
		return corrections;
	}

	/**
	 * Ilość wszystkich przepisanych znaków (również błędnych).
	 */
	public int getWrittenCharsCount() {
		int writtenCharsCount = 0;
		for (String line : writtenLines) {
			writtenCharsCount += line.length();
		}
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
		int correctionsCount = 0;
		for (String line : getCorrections()) {
			for (int i=0; i < line.length(); i++) {
				if (line.charAt(i) != ' ') {
					correctionsCount++;
				}
			}
		}
		return correctionsCount;
	}

	/**
	 * @return <code>true</code> jeśli lekcja się już zakończyła,
	 *         <code>false</code> w przeciwnym wypadku.
	 */
	public boolean isFinished() {
		return isFinished;
	}
}
