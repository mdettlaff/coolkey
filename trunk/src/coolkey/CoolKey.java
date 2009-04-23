package coolkey;

import java.io.File;

import coolkey.gui.GUI;

/**
 * Główna klasa programu.
 */
public class CoolKey {

	public static final String TXT_DIRECTORY = "data" + File.separator + "txt";
	public static final int MAX_CHARS_IN_LINE = 65;

	private static Lesson currentLesson;

	private CoolKey() {}

	public static void main(String[] args) {
		int autoGenTextSizeInLines = 10;
		int minGenTextLength = (autoGenTextSizeInLines-1) * (MAX_CHARS_IN_LINE-4);
		CoolKey.setCurrentLesson(new Lesson(Markov.generateMarkovChain(
				Utils.getWords(CoolKey.TXT_DIRECTORY), minGenTextLength)));

		GUI.init();
	}

	public static Lesson getCurrentLesson() {
		return currentLesson;
	}

	public static void setCurrentLesson(Lesson currentLesson) {
		CoolKey.currentLesson = currentLesson;
	}
}
