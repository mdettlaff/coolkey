package coolkey.gui;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import coolkey.CoolKey;
import coolkey.TypingTest;
import coolkey.Markov;
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
				CoolKey.persistState();
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
						CoolKey.setCurrentTest(new TypingTest(text));
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
		// automatycznie wygenerowany tekst
		autoTextItem.addListener(SWT.Selection,  new Listener() {
			public void handleEvent(Event event) {
				final Shell shell = new Shell(GUI.shell, SWT.DIALOG_TRIM
						| SWT.APPLICATION_MODAL);
				shell.setText("Autotekst");
				shell.setLayout(new GridLayout(2, false));

				Composite settings = new Composite(shell, SWT.NONE);
				settings.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 2, 1));
				settings.setLayout(new GridLayout(2, false));

				Label minLines = new Label(settings, SWT.NONE);
				minLines.setText("Ilość linii (minimum):");
				final Spinner spinner = new Spinner(settings, SWT.BORDER);
				spinner.setMinimum(1);
				spinner.setMaximum(100);
				spinner.setSelection(10);
				spinner.setIncrement(1);
				spinner.setPageIncrement(5);

				Composite buttons = new Composite(shell, SWT.NONE);
				buttons.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 2, 1));
				buttons.setLayout(new GridLayout(2, false));

				Composite left = new Composite(buttons, SWT.NONE);
				left.setLayout(new GridLayout());
				Composite right = new Composite(buttons, SWT.NONE);
				right.setLayout(new GridLayout());

				Button confirm = new Button(left, SWT.PUSH);
				confirm.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
				confirm.setText(" Wygeneruj ");
				Button cancel = new Button(right, SWT.PUSH);
				cancel.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
				cancel.setText(" Anuluj ");

				Listener generate = new Listener() {
					@Override
					public void handleEvent(Event e) {
						int minGenTextLines = spinner.getSelection();
						int minGenTextLength = (minGenTextLines - 1) * (
								CoolKey.MAX_CHARS_IN_LINE - 1);
						CoolKey.setCurrentTest(new TypingTest(
								Markov.generateMarkovChain(
										Utils.words(
												CoolKey.TEXT_DIRECTORY),
												minGenTextLength)));
						GUI.refresh();
						shell.dispose();
					}
				};
				confirm.addListener(SWT.Selection, generate);
				spinner.addListener(SWT.DefaultSelection, generate);

				cancel.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event e) {
						shell.close();
					}
				});

				shell.pack();
				shell.open();
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
				Defender defender = new Defender(shell);
				// defender.showFps(true);
				defender.start();
				shell.open();
				while (!shell.isDisposed()){
					if (!GUI.display.readAndDispatch()) GUI.display.sleep();
				}
				defender.dispose();
			}
		});
	}
}
