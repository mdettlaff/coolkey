package coolkey;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Zawiera operacje dyskowe wczytywania i zapisywania obiektów
 * do pliku oraz pliki do których zapisywany jest stan programu.
 */
public class Persistence {

	public File USERS;
	public File LAST_USER;
	private File stateDir;

	public Persistence() {
		String osName = System.getProperty("os.name");
		if (osName.equalsIgnoreCase("Linux")) {
			stateDir = new File(System.getProperty("user.home")
					+ File.separator + ".coolkey");
		} else if (osName.equalsIgnoreCase("Windows XP") ||
				osName.equalsIgnoreCase("Windows Vista") ||
				osName.equalsIgnoreCase("Windows 2000") ||
				osName.equalsIgnoreCase("Windows NT")) {
			stateDir = new File(System.getProperty("user.home")
					+ File.separator + "CoolKey");
		} else if (osName.equalsIgnoreCase("Windows 95") ||
				osName.equalsIgnoreCase("Windows 98")) {
			stateDir = new File("state");
		} else {
			stateDir = new File("state");
		}
		stateDir.mkdirs();
		USERS = new File(stateDir.getPath() + File.separator + "users");
		LAST_USER = new File(stateDir.getPath() + File.separator + "user");
	}

	/**
	 * Odczytuje dane z dysku.
	 *
	 * @param  file Plik z którego odczytujemy dane.
	 * @return      Obiekt danych odczytanych z pliku, <code>null</code> jeśli
	 *              wystąpi błąd.
	 */
	public static Object read(File file) {
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			return ois.readObject();
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (ois != null)
					ois.close();
				if (fis != null)
					fis.close();
			} catch (Exception e) {}
		}
	}

	/**
	 * Zapisuje dane do pliku.
	 *
	 * @param  file   Plik do którego zapisujemy dane.
	 * @param  object Obiekt danych, które chcemy zapisać.
	 * @return        <code>true</code> jeśli operacja się powiedzie,
	 *                <code>false</code> w przeciwnym wypadku.
	 */
	public static boolean write(File file, Object object) {
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (fos != null)
					fos.close();
			} catch (Exception e) {}
		}
		return true;
	}
}
