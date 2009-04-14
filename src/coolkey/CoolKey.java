package coolkey;

import java.io.File;

import coolkey.gui.GUI;

/**
 * Główna klasa programu.
 */
public class CoolKey {

	public static final String TXT_DIRECTORY = "data" + File.separator + "txt";
	public static final int MAX_CHARS_IN_LINE = 80;

	private static Lesson currentLesson = null;

	private CoolKey() {}

	public static void main(String[] args) {
		int autoGenTextSizeInLines = 12;
		int minGenTextLength = (autoGenTextSizeInLines-1) * (MAX_CHARS_IN_LINE-5);
		currentLesson = new Lesson(Markov.generateMarkovChain(
				Utils.getWords(CoolKey.TXT_DIRECTORY), minGenTextLength));

		GUI.run();
	}

	public static Lesson getCurrentLesson() {
		return currentLesson;
	}

	public static void setCurrentLesson(Lesson currentLesson) {
		CoolKey.currentLesson = currentLesson;
	}
}
