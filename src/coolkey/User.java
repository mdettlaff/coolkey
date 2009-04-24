package coolkey;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Dane użytkownika.
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PASSWORD_HASH_FUNC = "MD5";

	private String name;
	private String passwordHash;
	private Lesson currentLesson;
	private static Config config = new Config();

	public User(String name, String password) {
		this.name = name;
		if (password != null && password.length() > 0) {
			this.passwordHash = hash(password, PASSWORD_HASH_FUNC);
		} else {
			this.passwordHash = null;
		}
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

	public Lesson getCurrentLesson() {
		return currentLesson;
	}

	public void setCurrentLesson(Lesson currentLesson) {
		this.currentLesson = currentLesson;
	}

	/**
	 * Ustawienia wybrane przez użytkownika.
	 */
	public static Config getConfig() {
		return config;
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
