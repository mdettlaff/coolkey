/* Copyright 2009 Michał Dettlaff, Karol Domagała, Łukasz Draba
 *
 * This file is part of CoolKey.
 *
 * CoolKey is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package coolkey;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;

import coolkey.gui.GUI;

/**
 * Klasa główna programu CoolKey.
 */
public class CoolKey {

	public static final String VERSION = "0.9";
	public static final String TEXT_DIRECTORY = "data" + File.separator
			+ "text" + File.separator;
	public static final String TEXT_NORM_DIRECTORY = TEXT_DIRECTORY
			+ "normal" + File.separator;
	public static final String SOUND_DIRECTORY = "data" + File.separator
			+ "sound" + File.separator;
	public static final int MAX_CHARS_IN_LINE = 65;
	public static final String DEFAULT_USERNAME = "(domyślny użytkownik)";

	private static Persistence persistence = new Persistence();
	private static Dictionary dictionary = new Dictionary(new File("data"
			+ File.separator + "dictionary" + File.separator + "pl_dict"));
	private static boolean isSoundAvailable = true;
	private static SoundBank soundBank;

	private static List<User> users = new ArrayList<User>();
	private static Integer currentUserIndex = 0;

	private CoolKey() {}

	public static void main(String[] args) {
		restoreState();
		if (users.size() == 0) {
			users.add(new User());
		}
		try {
			soundBank = new SoundBank();
		} catch (LineUnavailableException e) {
			isSoundAvailable = false;
		}

		GUI.init();

		CoolKey.persistState();
	}

	public static TypingTest getCurrentTest() {
		return getUser().getCurrentTest();
	}

	public static void setCurrentTest(TypingTest test) {
		getUser().setCurrentTest(test);
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
		if (users.indexOf(user) == currentUserIndex) {
			currentUserIndex = 0;
		} else if (users.indexOf(user) < currentUserIndex) {
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

	public static Dictionary getDictionary() {
		return dictionary;
	}

	/**
	 * Czy karta dźwiękowa jest dostępna.
	 */
	public static boolean isSoundAvailable() {
		return isSoundAvailable;
	}

	public static SoundBank getSoundBank() {
		return soundBank;
	}

	/**
	 * Zapisuje stan programu na dysk.
	 */
	public static void persistState() {
		Persistence.write(persistence.USERS, users);
		Persistence.write(persistence.LAST_USER, currentUserIndex);
	}

	/**
	 * Przywraca stan programu zapisany na dysku.
	 */
	@SuppressWarnings("unchecked")
	private static void restoreState() {
		// wczytaj dane użytkowników
		List<User> usersRead = (ArrayList<User>) Persistence.read(
				persistence.USERS);
		if (usersRead != null) {
			users = usersRead;
		}
		// wczytaj indeks ostatnio wybranego użytkownika
		Integer indexRead = (Integer) Persistence.read(
				persistence.LAST_USER);
		if (indexRead != null) {
			currentUserIndex = indexRead;
		}
	}
}
