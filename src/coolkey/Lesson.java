package coolkey;

import java.util.ArrayList;
import java.util.List;

/**
 * Przechowuje aktualny stan ćwiczenia polegającego na przepisywaniu.
 */
public class Lesson {

	private List<String> textLines = new ArrayList<String>();
	private List<String> writtenLines = new ArrayList<String>();
	private List<String> mistakes = new ArrayList<String>();
	private int mistakesCount = 0;

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
		getMistakes().add("");
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
		getMistakes().set(last, getMistakes().get(last) + incorrectChar);
	}

	public void typeEnter() {
		int last = writtenLines.size() - 1;
		if (writtenLines.get(last).length() >= textLines.get(last).length()) {
			writtenLines.add("");
			mistakes.add("");
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
		}
	}

	/**
	 * Tekst do przepisania w tej lekcji.
	 */
	public List<String> getTextLines() {
		return textLines;
	}

	/**
	 * Tekst przepisany przez użytkownika.
	 */
	public List<String> getWrittenLines() {
		return writtenLines;
	}

	/**
	 * Tekst z zaznaczonymi błędami (spacja oznacza brak błędu).
	 */
	public List<String> getMistakes() {
		return mistakes;
	}

	/**
	 * Ilość nieprawidłowo przepisanych znaków.
	 */
	public int getMistakesCount() {
		return mistakesCount;
	}
}
