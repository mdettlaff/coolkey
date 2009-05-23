package coolkey;

import java.util.ArrayList;
import java.util.List;

/**
 * Stąd można pobrać dostępne w programie kursy.
 */
public class CourseFactory {

	private static String[][] qwertyBasic = {
		{ "Litery asdf", "asdf",
			"Wpisuj literę 'a' przy pomocy małego palca,\n" +
			"literę 's' - serdecznego, 'd' środkowego,\n" +
			"a 'f' wskazującego lewej ręki."
		},
		{ "Nowe znaki: jkl;", "jkl;",
			"Wpisuj literę 'j' przy pomocy palca wskazującego,\n" +
			"literę 'k' - środkowego, 'l' serdecznego,\n" +
			"a znak ';' małego palca prawej ręki."
		},
		{ "Nowe znaki: ru", "ru",
			"Wpisuj litery 'r' i 'u' za pomocą palców\n" +
			"wskazujących. Po naciśnięciu palce powinny\n" +
			"powrócić na domyślne pozycje w środkowym rzędzie."
		},
		{ "Nowe znaki: ei", "ei",
			"Wpisuj nowe znaki za pomocą palców środkowych."
		},
		{ "Nowe znaki: wo", "wo",
			"Wpisuj nowe znaki za pomocą palców serdecznych."
		},
		{ "Nowe znaki: qp", "qp",
			"Wpisuj nowe znaki za pomocą małych palców."
		},
		{ "Nowe znaki: mn", "mn",
			"Wpisuj nowe znaki za pomocą prawego palca wskazującego."
		},
		{ "Nowe znaki: zx", "zx",
			"Wpisuj znak 'z' małym, a 'x' serdecznym palcem lewej ręki."
		},
		{ "Nowe znaki: cv", "cv",
			"Wpisuj znak 'c' środkowym, a 'v' wskazującym palcem lewej ręki."
		},
		{ "Nowe znaki: gh", "gh",
			"Wpisuj 'g' za pomocą palca wskazującego lewej ręki,\n" +
			"a 'h' palca wskazującego prawej ręki."
		},
		{ "Nowe znaki: ty", "ty",
			"Wpisuj 't' za pomocą palca wskazującego lewej ręki,\n" +
			"a 'y' palca wskazującego prawej ręki."
		},
		{ "Nowe znaki: bn", "bn",
			"Wciskaj 'b' za pomocą palca wskazującego lewej ręki,\n" +
			"a 'n' palca wskazującego prawej ręki."
		},
		{ "Nowe znaki: ,.", ",.",
			"Wpisuj znak ',' za pomocą palca środkowego,\n" +
			"a '.' za pomocą palca serdecznego prawej ręki."
		},
		{ "Nowe znaki: ?!", "?!",
			"Wciśnij klawisz Shift małym palcem prawej ręki\n" +
			"oraz '!' małym palcem lewej ręki aby uzyskać znak '!'.\n" +
			"Wciśnij klawisz Shift małym palcem lewej ręki\n" +
			"oraz '?' małym palcem lewej ręki aby uzyskać znak '?'."
		},
		{ "Nowe znaki: ;:", ";:",
			"Wpisuj nowe znaki za pomocą małego palca prawej ręki."
		},
		{ "Nowe znaki: \"'", "\"'",
			"Wpisuj nowe znaki za pomocą małego palca prawej ręki.\n" +
			"Po wpisaniu znaku palec powinien wrócić na domyślną\n" +
			"pozycję nad klawiszem ';'"
		},
		{ "Nowe znaki: ()", "()",
			"Wpisuj nawias otwierający za pomocą palca środkowego,\n" +
			"a zamykający za pomocą palca serdecznego"
		},
		{ "Nowe znaki: -_", "-_",
			"Wpisuj nowe znaki za pomocą małego palca."
		},
		{ "Nowe znaki: /\\", "/\\",
			"Wpisuj nowe znaki za pomocą małego palca."
		},
		{ "Nowe znaki: wielkie litery", "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
			"Wpisuj wielkie litery wciskając klawisz Shift\n" +
			"po przeciwnej stronie klawiatury niż wpisywany znak."
		},
		{ "Nowe znaki: polskie znaki", "ąćęłńóśżź",
			"Wpisuj polskie znaki wciskając prawy klawisz Alt."
		}
	};
	private static String[][] dvorakBasic = {
		{ "Litery aoeu", "aoeu",
			"Wpisuj literę 'a' przy pomocy małego palca,\n" +
			"literę 'o' - serdecznego, 'e' środkowego,\n" +
			"a 'u' wskazującego lewej ręki."
		},
		{ "Nowe znaki: htns", "htns",
			"Wpisuj literę 'h' przy pomocy palca wskazującego,\n" +
			"literę 't' - środkowego, 'n' serdecznego,\n" +
			"a 's' małego palca prawej ręki."
		},
		{ "Nowe znaki: pg", "pg",
			"Wpisuj litery 'p' i 'g' za pomocą palców\n" +
			"wskazujących. Po naciśnięciu palce powinny\n" +
			"powrócić na domyślne pozycje w środkowym rzędzie."
		},
		{ "Nowe znaki: .c", ".c",
			"Wpisuj nowe znaki za pomocą palców środkowych."
		},
		{ "Nowe znaki: ,r", ",r",
			"Wpisuj nowe znaki za pomocą palców serdecznych."
		},
		{ "Nowe znaki: 'l", "'l",
			"Wpisuj nowe znaki za pomocą małych palców."
		},
		{ "Nowe znaki: mb", "mb",
			"Wpisuj nowe znaki za pomocą prawego palca wskazującego."
		},
		{ "Nowe znaki: ;q", ";q",
			"Wpisuj znak ';' małym, a 'q' serdecznym palcem lewej ręki."
		},
		{ "Nowe znaki: jk", "jk",
			"Wpisuj znak 'j' środkowym, a 'k' wskazującym palcem lewej ręki."
		},
		{ "Nowe znaki: id", "id",
			"Wpisuj 'i' za pomocą palca wskazującego lewej ręki,\n" +
			"a 'd' palca wskazującego prawej ręki."
		},
		{ "Nowe znaki: yf", "yf",
			"Wpisuj 'y' za pomocą palca wskazującego lewej ręki,\n" +
			"a 'f' palca wskazującego prawej ręki."
		},
		{ "Nowe znaki: xb", "xb",
			"Wciskaj 'x' za pomocą palca wskazującego lewej ręki,\n" +
			"a 'b' palca wskazującego prawej ręki."
		},
		{ "Nowe znaki: wv", "wv",
			"Wpisuj znak 'w' za pomocą palca środkowego,\n" +
			"a 'v' za pomocą palca serdecznego prawej ręki."
		},
		{ "Nowe znaki: ?!", "?!",
			"Wciśnij klawisz Shift małym palcem prawej ręki\n" +
			"oraz '!' małym palcem lewej ręki aby uzyskać znak '!'.\n" +
			"Wciśnij klawisz Shift małym palcem lewej ręki\n" +
			"oraz '?' małym palcem lewej ręki aby uzyskać znak '?'."
		},
		{ "Nowe znaki: ;:", ";:",
			"Wpisuj nowe znaki za pomocą małego palca lewej ręki."
		},
		{ "Nowe znaki: \"'", "\"'",
			"Wpisuj nowe znaki za pomocą małego palca lewej ręki.\n" +
			"Po wpisaniu znaku palec powinien wrócić na domyślną\n" +
			"pozycję nad klawiszem 'a'"
		},
		{ "Nowe znaki: ()", "()",
			"Wpisuj nawias otwierający za pomocą palca środkowego,\n" +
			"a zamykający za pomocą palca serdecznego"
		},
		{ "Nowe znaki: -_", "-_",
			"Wpisuj nowe znaki za pomocą małego palca."
		},
		{ "Nowe znaki: /\\", "/\\",
			"Wpisuj nowe znaki za pomocą małego palca."
		},
		{ "Nowe znaki: wielkie litery", "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
			"Wpisuj wielkie litery wciskając klawisz Shift\n" +
			"po przeciwnej stronie klawiatury niż wpisywany znak."
		},
		{ "Nowe znaki: polskie znaki", "ąćęłńóśżź",
			"Wpisuj polskie znaki wciskając prawy klawisz Alt."
		}
	};
	private static String[][] digits = {
		{ "Cyfry: 58", "58",
			"Wpisuj '5' przy pomocy lewego palca wskazującego,\n" +
			"a '8' prawego palca wskazującego."
		},
		{ "Cyfry: 67", "67",
			"Wpisuj '6' przy pomocy lewego palca wskazującego,\n" +
			"a '7' prawego palca wskazującego."
		},
		{ "Cyfry: 49", "49",
			"Wpisuj nowe znaki za pomocą palców środkowych."
		},
		{ "Cyfry: 30", "30",
			"Wpisuj nowe znaki za pomocą palców serdecznych."
		},
		{ "Cyfry: 12", "12",
			"Wpisuj nowe znaki za pomocą małego palca."
		}
	};
	private static String[][] special = {
		{ "Nowe znaki: @#", "@#",
			"Wpisuj znak '@' za pomocą małego palca, a znak\n" +
			"'#' za pomocą serdecznego."
		},
		{ "Nowe znaki: $%", "$%",
			"Wpisuj znak '$' za pomocą środkowego palca, a znak\n" +
			"'%' za pomocą wskazującego."
		},
		{ "Nowe znaki: ^&&*", "^&&*",
			"Wpisuj '^' przy pomocy lewego palca wskazującego,\n" +
			"a '&' i '*' prawego palca wskazującego."
		},
		{ "Nowe znaki: []", "[]",
			"Wpisuj nowe znaki za pomocą małego palca."
		},
		{ "Nowe znaki: {}", "{}",
			"Wpisuj nowe znaki za pomocą małego palca."
		},
		{ "Nowe znaki: +=", "+=",
			"Wpisuj nowe znaki za pomocą małego palca."
		},
		{ "Nowe znaki: <>", "<>",
			"Wpisuj znak '<' za pomocą środkowego palca, a znak\n" +
			"'>' za pomocą serdecznego."
		}
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
		// kurs pełny Dvorak
		lessons = new ArrayList<Lesson>();
		knownChars = "";
		addLessons(dvorakBasic, lessons, knownChars);
		addLessons(digits, lessons, knownChars);
		addLessons(special, lessons, knownChars);
		allCourses.add(new Course("Kurs pełny Dvorak", lessons));
		// kurs cyfr
		lessons = new ArrayList<Lesson>();
		knownChars = "";
		addLessons(digits, lessons, knownChars);
		allCourses.add(new Course("Cyfry", lessons));
		// kurs cyfr i znaków specjalnych
		lessons = new ArrayList<Lesson>();
		knownChars = "";
		addLessons(digits, lessons, knownChars);
		addLessons(special, lessons, knownChars);
		allCourses.add(new Course("Cyfry i znaki specjalne", lessons));
		return allCourses;
	}

	public static List<Lesson> qwertyLessons() {
		// kurs pełny QWERTY
		List<Lesson> lessons = new ArrayList<Lesson>();
		String knownChars = "";
		addLessons(qwertyBasic, lessons, knownChars);
		addLessons(digits, lessons, knownChars);
		addLessons(special, lessons, knownChars);
		return lessons;
	}

	public static List<Lesson> dvorakLessons() {
		// kurs pełny Dvorak
		List<Lesson> lessons = new ArrayList<Lesson>();
		String knownChars = "";
		addLessons(dvorakBasic, lessons, knownChars);
		addLessons(digits, lessons, knownChars);
		addLessons(special, lessons, knownChars);
		return lessons;
	}

	private static void addLessons(String[][] newLessons,
			List<Lesson> lessons, String knownChars) {
		for (String[] lessonData : newLessons) {
			String newChars = lessonData[1];
			knownChars += newChars;
			lessons.add(new Lesson(knownChars, newChars,
					lessonData[0], lessonData[2]));
		}
	}
}
