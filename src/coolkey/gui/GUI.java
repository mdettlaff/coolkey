package coolkey.gui;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import coolkey.CoolKey;

public class GUI {

	public static Display display;
	/**
	 * Okno główne.
	 */
	public static Shell shell;

	public static WritingArea writingArea;
	public static ButtonBar buttonBar;
	public static Keyboard keyboard;
	public static Graphs graphs;

	private GUI() {}

	/**
	 * Inicjalizuje graficzny interfejs użytkownika.
	 */
	public static void init() {
		display = new Display();
		shell = new Shell(display);
		shell.setText("CoolKey 0.1");
		shell.setLayout(new GridLayout(2, false));

		/* Wyśrodkowanie shella */
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
		shell.setSize(780, 580);

		new MenuBar();
		writingArea = new WritingArea(); // obszar przepisywania
		graphs = new Graphs(); // panel z wykresami po prawej (nowy wątek)
		buttonBar = new ButtonBar();
		keyboard = new Keyboard();

		shell.open();

		if (CoolKey.getUsers().size() > 1) {
			new ChangeUser(true).open();
		}

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		// zamykanie programu
		GUI.writingArea.close();
		if (CoolKey.getCurrentLesson().isStarted()) {
			CoolKey.getCurrentLesson().restart();
		}
		display.dispose();
	}

	public static void refresh() {
		writingArea.refresh();
		buttonBar.refresh();
		keyboard.refresh();
		graphs.refresh();
	}
}
