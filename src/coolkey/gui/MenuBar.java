package coolkey.gui;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import coolkey.CoolKey;
import coolkey.Lesson;
import coolkey.Utils;
import coolkey.defender.Defender;
import coolkey.defender.Engine;

public class MenuBar {

	public MenuBar() {
		Menu menu = new Menu(GUI.shell, SWT.BAR);

		/* Menu Użytkownik */
		final MenuItem user = new MenuItem(menu, SWT.CASCADE);
		user.setText("&Użytkownik");

		final Menu userMenu = new Menu(GUI.shell, SWT.DROP_DOWN);
		user.setMenu(userMenu);
		final MenuItem addUserItem = new MenuItem(userMenu, SWT.PUSH);
		addUserItem.setText("&Nowy użytkownik");
		final MenuItem changeUserItem = new MenuItem(userMenu, SWT.PUSH);
		changeUserItem.setText("&Zmień użytkownika");
		final MenuItem settingsItem = new MenuItem(userMenu, SWT.PUSH);
		settingsItem.setText("&Ustawienia");
		final MenuItem statsItem = new MenuItem(userMenu, SWT.PUSH);
		statsItem.setText("&Statystyka");
		//exportMenuItem.setAccelerator(SWT.CTRL+'E');
		new MenuItem(userMenu, SWT.SEPARATOR);
		final MenuItem exitMenuItem = new MenuItem(userMenu, SWT.PUSH);
		exitMenuItem.setText("&Zakończ");

		/* Kurs */
		final MenuItem course = new MenuItem(menu, SWT.CASCADE);
		course.setText("&Kurs");

		final Menu courseMenu = new Menu(GUI.shell, SWT.DROP_DOWN);
		course.setMenu(courseMenu);
		final MenuItem continueItem = new MenuItem(courseMenu, SWT.PUSH);
		continueItem.setText("Następna lekcja");
		final MenuItem newCourseItem = new MenuItem(courseMenu, SWT.PUSH);
		newCourseItem.setText("Rozpocznij nowy kurs");
		final MenuItem coursesManagerItem = new MenuItem(courseMenu, SWT.PUSH);
		coursesManagerItem.setText("Zarządzanie kursami");

		/* Pojedynczy test */
		final MenuItem singleTest = new MenuItem(menu, SWT.CASCADE);
		singleTest.setText("&Pojedynczy test");

		final Menu singleTestMenu = new Menu(GUI.shell, SWT.DROP_DOWN);
		singleTest.setMenu(singleTestMenu);
		MenuItem poemTextItem = new MenuItem(singleTestMenu, SWT.CASCADE);
		poemTextItem.setText("Teksty literackie");
		Menu poemTextMenu = new Menu(singleTestMenu);
		final MenuItem standardItem = new MenuItem(poemTextMenu, SWT.PUSH);
		standardItem.setText("Standardowe");
		final MenuItem specItem = new MenuItem(poemTextMenu, SWT.PUSH);
		specItem.setText("Specjalistyczne");
		final MenuItem poemItem = new MenuItem(poemTextMenu, SWT.PUSH);
		poemItem.setText("Poezja");
		poemTextItem.setMenu(poemTextMenu);
		MenuItem fileTextItem = new MenuItem(singleTestMenu, SWT.PUSH);
		fileTextItem.setText("Tekst z pliku");
		MenuItem autoTextItem = new MenuItem(singleTestMenu, SWT.PUSH);
		autoTextItem.setText("Automatycznie wygenerowany tekst");
		MenuItem lessonsItem = new MenuItem(singleTestMenu, SWT.CASCADE);
		lessonsItem.setText("Lekcje");
		Menu lessonsMenu = new Menu(singleTestMenu);
		final MenuItem lesson1Item = new MenuItem(lessonsMenu, SWT.PUSH);
		lesson1Item.setText("Lekcja 1: asdf");
		lessonsItem.setMenu(lessonsMenu);
		MenuItem practiseItem = new MenuItem(singleTestMenu, SWT.PUSH);
		practiseItem.setText("Wprawki");
		MenuItem ownTextItem = new MenuItem(singleTestMenu, SWT.PUSH);
		ownTextItem.setText("Test spersonalizowany");

		/* Gra */
		final MenuItem game = new MenuItem(menu, SWT.CASCADE);
		game.setText("&Gra");

		final Menu gameMenu = new Menu(GUI.shell, SWT.DROP_DOWN);
		game.setMenu(gameMenu);
		final MenuItem startItem = new MenuItem(gameMenu, SWT.PUSH);
		startItem.setText("Rozpocznij grę");

		/* Pomoc */
		final MenuItem help = new MenuItem(menu, SWT.CASCADE);
		help.setText("&Pomoc");

		final Menu helpMenu = new Menu(GUI.shell, SWT.DROP_DOWN);
		help.setMenu(helpMenu);
		final MenuItem topicsItem = new MenuItem(helpMenu, SWT.PUSH);
		topicsItem.setText("Tematy pomocy");
		final MenuItem aboutItem = new MenuItem(helpMenu, SWT.PUSH);
		aboutItem.setText("O programie");

		GUI.shell.setMenuBar(menu);

		addUserItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				AddUser addShell = new AddUser();
				addShell.open();
			}
		});
		changeUserItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				ChangeUser changeShell = new ChangeUser(false);
				changeShell.open();
			}
		});
		newCourseItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				NewCourse newCourseShell = new NewCourse();
				newCourseShell.open();
			}
		});
		statsItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				Stats statsShell = new Stats();
				statsShell.open();
			}
		});
		exitMenuItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				System.exit(0);
			}
		});
		coursesManagerItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				CourseManager cmShell = new CourseManager();
				cmShell.open();
			}
		});
		settingsItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				UserSettings usShell = new UserSettings();
				usShell.open();
			}
		});
		// załaduj plik tekstowy
		fileTextItem.addListener(SWT.Selection,  new Listener() {
			public void handleEvent(Event event) {
				FileDialog dialog = new FileDialog (GUI.shell, SWT.OPEN);
				dialog.setFileName("*.txt");
				dialog.setFilterNames (new String[] {"*.txt", "Wszystkie pliki"});
				dialog.setFilterExtensions (new String[] {"*.txt", "*" });
				dialog.setText("Otwórz plik");
				String result = dialog.open();

				if (result != null) {
					String text;
					try {
						text = Utils.readFileAsString(new File(result));
						CoolKey.setCurrentLesson(new Lesson(text));
						GUI.refresh();
					} catch (IOException e) {
						MessageBox messageBox = new MessageBox(GUI.shell, SWT.ICON_ERROR);
						messageBox.setText("Błąd");
						messageBox.setMessage("Nie można otworzyć pliku.");
						messageBox.open();
					}
				}
			}
		});
		// rozpocznij grę
		startItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				final Shell shell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				shell.setText("Defender");
				Point p = shell.computeSize(Engine.WIDTH, Engine.HEIGHT);
				shell.setSize(p);
				shell.setMinimumSize(p);
				Defender game = new Defender(shell);
				game.showFps(true);
				game.start();
				shell.open();
				while (!shell.isDisposed()){
					if (!GUI.display.readAndDispatch()) GUI.display.sleep();
				}
				game.stop();
			}
		});
	}
}
