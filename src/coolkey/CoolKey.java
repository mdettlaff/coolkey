package coolkey;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import coolkey.gui.GUI;

/**
 * Główna klasa programu CoolKey.
 */
public class CoolKey {

	public static final String TXT_DIRECTORY = "data" + File.separator + "txt";
	public static final int MAX_CHARS_IN_LINE = 65;
	public static final String DEFAULT_USERNAME = "(domyślny użytkownik)";

	private static Persistence persistence = new Persistence();
	private static List<User> users = new ArrayList<User>();
	private static Integer currentUserIndex = 0;

	private CoolKey() {}

	public static void main(String[] args) {
		restoreState();
		if (users.size() == 0) {
			users.add(new User());
		}

		GUI.init();

		CoolKey.persistState();
	}

	public static Lesson getCurrentLesson() {
		return getUser().getCurrentLesson();
	}

	public static void setCurrentLesson(Lesson currentLesson) {
		getUser().setCurrentLesson(currentLesson);
	}

	/**
	 * Dodaje nowego użytkownika.
	 *
	 * @param  user Nowy użytkownik.
	 * @return      <code>true</code> jeśli dodano użytkownika,
	 *              <code>false</code> jeśli użytkownik o podanej nazwie
	 *              już istnieje.
	 */
	public static boolean addUser(User user) {
		if (!users.contains(user)) {
			users.add(user);
			currentUserIndex = users.size() - 1;
			return true;
		}
		return false;
	}

	public static void deleteUser(User user) {
		if (users.indexOf(user) <= currentUserIndex) {
			currentUserIndex--;
		}
		users.remove(user);
	}

	public static void selectUser(int userIndex) {
		currentUserIndex = userIndex;
	}

	/**
	 * Aktualny użytkownik.
	 */
	public static User getUser() {
		return users.get(currentUserIndex);
	}

	public static List<User> getUsers() {
		return users;
	}

	/**
	 * Zapisuje stan programu na dysk.
	 */
	public static void persistState() {
		Persistence.write(persistence.getUsersFile(), users);
		Persistence.write(persistence.getLastUserFile(), currentUserIndex);
	}

	/**
	 * Przywraca stan programu zapisany na dysku.
	 */
	@SuppressWarnings("unchecked")
	private static void restoreState() {
		// wczytaj dane użytkowników
		List<User> usersRead = (ArrayList<User>) Persistence.read(
				persistence.getUsersFile());
		if (usersRead != null) {
			users = usersRead;
		}
		// wczytaj indeks ostatnio wybranego użytkownika
		Integer indexRead = (Integer) Persistence.read(
				persistence.getLastUserFile());
		if (indexRead != null) {
			currentUserIndex = indexRead;
		}
	}
}
