package coolkey;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dane użytkownika.
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PASSWORD_HASH_FUNC = "MD5";

	private String name;
	private String passwordHash;
	private Config config;
	private Lesson currentLesson;
	private List<Integer> highscore;

	public User(String name, String password) {
		this.name = name;
		if (password != null && password.length() > 0) {
			this.passwordHash = hash(password, PASSWORD_HASH_FUNC);
		} else {
			this.passwordHash = null;
		}
		config = new Config();
		highscore = new ArrayList<Integer>();
		// domyślna lekcja
		int minGenTextLines = 10;
		int minGenTextLength = (minGenTextLines - 1) * (
				CoolKey.MAX_CHARS_IN_LINE - 4);
		setCurrentLesson(new Lesson(Markov.generateMarkovChain(
				Utils.getWords(CoolKey.TXT_DIRECTORY), minGenTextLength)));
	}

	/**
	 * Tworzy domyślnego użytkownika.
	 */
	public User() {
		this(CoolKey.DEFAULT_USERNAME, null);
	}

	/**
	 * Funkcja haszująca dla hasła.
	 */
	private String hash(String password, String hashFunctionName) {
		String hexString = "";
		try {
			MessageDigest digest;
			digest = MessageDigest.getInstance(PASSWORD_HASH_FUNC);
			byte[] byteHash = digest.digest(password.getBytes());
			for (int i=0; i < byteHash.length; i++) {
				hexString += Integer.toHexString(0xFF & byteHash[i]);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hexString;
	}

	/**
	 * Nazwa użytkownika.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sprawdź czy podane hasło dla użytkownika jest prawidłowe.
	 *
	 * @param  password Podane hasło.
	 * @return          <code>true</code>, jeśli hasło jest poprawne,
	 *                  <code>false</code> w przeciwnym wypadku.
	 *                  Jeśli użytkownik nie jest zabezpieczony hasłem,
	 *                  zawsze zwraca <code>true</code>.
	 */
	public boolean validatePassword(String password) {
		if (passwordHash == null) {
			return true;
		} else if (password != null && password.length() > 0) {
			return passwordHash.equals(hash(password, PASSWORD_HASH_FUNC));
		}
		return false;
	}

	/**
	 * Zmień hasło użytkownika.
	 *
	 * @param  oldPassword Aktualne hasło.
	 * @param  newPassword Nowe hasło.
	 * @return             <code>true</code>, jeśli zmiana się powiodła,
	 *                     <code>false</code> w przeciwnym wypadku.
	 *                     Jeśli użytkownik nie jest zabezpieczony hasłem,
	 *                     zawsze zwraca <code>false</code>.
	 */
	public boolean changePassword(String oldPassword, String newPassword) {
		if (passwordHash != null && validatePassword(oldPassword)) {
			this.passwordHash = hash(newPassword, PASSWORD_HASH_FUNC);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Ustawienia wybrane przez użytkownika.
	 */
	public Config getConfig() {
		return config;
	}

	public Lesson getCurrentLesson() {
		return currentLesson;
	}

	public void setCurrentLesson(Lesson currentLesson) {
		this.currentLesson = currentLesson;
	}

	/**
	 * Uaktualnij wyniki użytkownika w grze edukacyjnej.
	 */
	public void setHighscore(List<Integer> highscore) {
		this.highscore = highscore;
	}

	/**
	 * Wyniki użytkownika w grze edukacyjnej.
	 */
	public List<Integer> getHighscore() {
		return highscore;
	}

	@Override
	public boolean equals(Object obj) {
		User other = (User) obj;
		return name.equals(other.getName());
	}

	@Override
	public String toString() {
		return getName();
	}
}
