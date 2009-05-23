package coolkey;

import java.util.ArrayList;
import java.util.List;

/**
 * Stąd można pobrać dostępne w programie kursy.
 */
public class CourseFactory {

	private static String[][] qwertyBasic = {
			{ "Litery asdf", "asdf" },
			{ "Nowe znaki: jkl;", "jkl;" },
			{ "Nowe znaki: ru", "ru", },
			{ "Nowe znaki: ei", "ei" },
			{ "Nowe znaki: wo", "wo" },
			{ "Nowe znaki: qp", "qp" },
			{ "Nowe znaki: mn", "mn" },
			{ "Nowe znaki: zx", "zx" },
			{ "Nowe znaki: cv", "cv" },
			{ "Nowe znaki: gh", "gh" },
			{ "Nowe znaki: ty", "ty" },
			{ "Nowe znaki: bn", "bn" },
			{ "Nowe znaki: ,.", ",." },
			{ "Nowe znaki: ?!", "?!" },
			{ "Nowe znaki: ;:", ";:" },
			{ "Nowe znaki: \"'", "\"'" },
			{ "Nowe znaki: ()", "()" },
			{ "Nowe znaki: -_", "-_" },
			{ "Nowe znaki: /\\", "/\\" },
			{ "Nowe znaki: wielkie litery", "ABCDEFGHIJKLMNOPQRSTUVWXYZ" },
			{ "Nowe znaki: polskie znaki", "ąćęłńóśżź" }
	};
	private static String[][] digits = {
			{ "Cyfry: 12", "12" },
			{ "Cyfry: 34", "34" },
			{ "Cyfry: 56", "56" },
			{ "Cyfry: 78", "78" },
			{ "Cyfry: 90", "90" }
	};
	private static String[][] special = {
			{ "Nowe znaki: @#", "@#" },
			{ "Nowe znaki: $%", "$%" },
			{ "Nowe znaki: ^&*", "^&*" },
			{ "Nowe znaki: []", "[]" },
			{ "Nowe znaki: {}", "{}" },
			{ "Nowe znaki: +=", "+=" },
			{ "Nowe znaki: <>", "<>" }
	};

	private CourseFactory() {}

	public static List<Course> allCourses() {
		List<Course> allCourses = new ArrayList<Course>();
		// kurs pełny QWERTY
		List<Lesson> lessons = new ArrayList<Lesson>();
		String knownChars = "";
		addLessons(qwertyBasic, lessons, knownChars);
		addLessons(digits, lessons, knownChars);
		addLessons(special, lessons, knownChars);
		allCourses.add(new Course("Kurs pełny QWERTY", lessons));
		// kurs cyfr
		lessons = new ArrayList<Lesson>();
		knownChars = "";
		addLessons(digits, lessons, knownChars);
		allCourses.add(new Course("Cyfry", lessons));
		return allCourses;
	}

	private static void addLessons(String[][] newLessons, List<Lesson> lessons, String knownChars) {
		for (String[] lessonData : newLessons) {
			String newChars = lessonData[1];
			knownChars += newChars;
			lessons.add(new Lesson(lessonData[0], knownChars, newChars));
		}
	}
}
