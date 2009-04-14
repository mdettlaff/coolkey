package coolkey;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;

/**
 * Przechowuje dane dotyczące ćwiczenia polegającego na przepisywaniu.
 */
public class Lesson {

	/**
	 * Tekst do przepisania w tej lekcji.
	 */
	private List<String> textLines = new ArrayList<String>();

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
		for (String l : textLines) {
			System.out.print(l.length());
			System.out.print(' ');
			System.out.println(l);
		}
	}

	public void type(Character c) {
		if (!Character.isISOControl(c)) {
			System.out.print(c);
		} else if (c == SWT.BS) {
			System.out.print("BACKSPACE");
		}
	}

	public List<String> getTextLines() {
		return textLines;
	}

	public void setTextLines(List<String> textLines) {
		this.textLines = textLines;
	}
}
