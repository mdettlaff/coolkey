package coolkey;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * Lekcja ucząca danych znaków będąca częścią kursu.
 */
public class Lesson implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Schemat do ćwiczenia z dwoma nowymi znakami.
	 */
	private static final String PATTERN_2_CHARS =
		"000 111 000 111 000 111 000 111 000 111 000 111 000 111 000 111\n" +
		"010 010 101 101 010 101 000 111 000 111 101 010 101 101 010 010\n" +
		"0 1 0 1 00 11 00 11 01 01 10 10 01 01 10 10 1010 0101 1010 0101\n" +
		"000 111 000 111 010 010 101 101 001 110 001 110 011 100 011 011\n" +
		"01 01 01 01 10 10 10 10 00 00 00 00 11 11 11 11 010 010 101 101\n";
	/**
	 * Schemat do ćwiczenia z czterema nowymi znakami.
	 */
	private static final String PATTERN_4_CHARS =
		"000 111 222 333 000 111 222 333 333 222 111 000 333 222 111 000\n" +
		"010 020 030 010 020 030 101 121 131 202 212 232 303 313 323 030\n" +
		"030 020 010 131 121 101 232 212 202 323 313 303 121 323 020 131\n" +
		"010 121 232 010 121 232 101 212 323 232 121 010 323 212 101 303\n" +
		"3030 3131 3232 0101 0202 0303 3010 3020 0301 0302 1030 3010 203\n";
	/**
	 * Schemat do ćwiczenia palców.
	 */
	private static final String PATTERN_6_CHARS =
		"111 000 111 000 111 222 111 222 444 333 444 333 444 555 444 555\n" +
		"101 101 101 101 121 121 121 121 434 434 434 434 454 454 454 454\n" +
		"141 414 030 303 141 414 252 525 134 134 431 431 401 401 104 104\n" +
		"1043 1043 1245 1245 0314 0314 5241 5241 1340 4013 1542 4215 141\n" +
		"1340 1340 1310 1344 1340 1130 4031 1524 1455 1421 1155 4214 524\n";
	/**
	 * Schemat do ćwiczenia jednego rzędu klawiatury.
	 */
	private static final String PATTERN_10_CHARS =
		"000 111 222 333 444 555 666 777 888 999 000 222 777 111 888 000\n" +
		"0123 0123 9876 9876 3210 3210 6789 6789 0213 0213 9786 9786 363\n" +
		"010 121 232 343 989 878 767 656 45 36 27 18 09 90 81 72 63 54\n" +
		"000 999 111 888 222 777 333 666 444 555 333 666 222 777 111 888\n";
	/**
	 * Schemat do ćwiczenia palców wskazujących.
	 */
	private static final String PATTERN_12_CHARS =
		"111 aaa 111 aaa 000 999 000 999 222 bbb 222 bbb 444 777 444 777\n" +
		"333 666 333 666 555 888 555 888 111 333 aaa 666 111 555 aaa 888\n" +
		"101 101 a9a a9a 101 101 a9a a9a 121 121 aba aba 121 121 aba aba\n" +
		"131 131 a6a a6a 131 131 a6a a6a 151 151 a8a a8a 151 151 a8a a8a\n" +
		"1a1 474 1a1 474 090 909 2b2 b2b 474 747 363 636 585 858 141 a7a\n";
	private static final Random RANDOM = new Random();
	private static final int LENGTH_IN_LINES = 11;

	private String text = "";
	private String knownChars;
	private String newChars;
	private String name;
	private String instructions;

	/**
	 * Tworzy nową lekcję.
	 *
	 * @param knownChars Znaki które zostały już przećwiczone i mogą zostać
	 *                   użyte w lekcji.
	 * @param newChars   Znaki, których użytkownik dopiero się uczy lub ma
	 *                   zamiar je intensywnie przećwiczyć.
	 */
	public Lesson(String knownChars, String newChars,
			String name, String instructions) {
		this.knownChars = knownChars;
		this.newChars = newChars;
		this.name = name;
		this.instructions = instructions;
	}

	/**
	 * Generuje i zwraca tekst do przepisania w tej lekcji.
	 */
	public String getText() {
		if (!"".equals(text)) {
			return text;
		}
		// pierwszą część tekstu bierzemy z gotowych wzorów
		if (newChars.length() == 2) {
			text += PATTERN_2_CHARS;
		} else if (newChars.length() == 4) {
			text += PATTERN_4_CHARS;
		} else if (newChars.length() == 6) {
			text += PATTERN_6_CHARS;
		} else if (newChars.length() == 10) {
			text += PATTERN_10_CHARS;
		} else if (newChars.length() == 12) {
			text += PATTERN_12_CHARS;
		}
		// zabezpieczamy się przed przypadkiem, kiedy w tekście występują
		// cyfry lub litery 'a', 'b'
		String rare = "\u00b6"; // znak, który nie wystąpi w tekście
		text = text.replace("", rare);
		for (int i=0; i < newChars.length(); i++) {
			text = text.replace(
					Integer.toHexString(i).charAt(0) + rare,
					newChars.charAt(i) + "");
		}
		text = text.replace(rare, "");
		// drugą część tekstu tworzymy ze słów ze słownika
		List<String> words = CoolKey.getDictionary().wordsConsistingOf(
				knownChars + newChars);
		if (words.size() < 10) { return text; }
		words = Dictionary.wordsContaining(
				newChars, words.toArray(new String[10]));
		if (words.size() < 10) { return text; }
		while (text.length() < LENGTH_IN_LINES
				* CoolKey.MAX_CHARS_IN_LINE - 20) {
			text += words.get(RANDOM.nextInt(words.size())) + ' ';
		}
		return text;
	}

	public String getName() {
		return name;
	}

	public String getInstructions() {
		return instructions;
	}

	@Override
	public String toString() {
		return getName();
	}
}
