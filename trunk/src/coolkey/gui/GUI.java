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

package coolkey.gui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import coolkey.CoolKey;

/**
 * Klasa główna graficznego interfejsu użytkownika programu CoolKey.
 */
public class GUI {

	public static Display display;
	/**
	 * Okno główne.
	 */
	public static Shell shell;

	public static MenuBar menuBar;
	public static TypingArea typingArea;
	public static ButtonBar buttonBar;
	public static Keyboard keyboard;
	public static Graphs graphs;

	private static Image icon;

	private GUI() {}

	/**
	 * Inicjalizuje graficzny interfejs użytkownika.
	 */
	public static void init() {
		display = new Display();
		shell = new Shell(display, SWT.DIALOG_TRIM);
		icon = new Image(display, "data" + File.separator + "images"
				+ File.separator + "icons" + File.separator + "coolkey.png");
		shell.setText("CoolKey " + CoolKey.VERSION);
		shell.setImage(icon);
		shell.setLayout(new GridLayout(2, false));

		/* Wyśrodkowanie shella */
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
		shell.setSize(780, 580);

		menuBar = new MenuBar();
		typingArea = new TypingArea(); // obszar przepisywania
		graphs = new Graphs(); // panel z wykresami po prawej (nowy wątek)
		buttonBar = new ButtonBar();
		keyboard = new Keyboard();

		shell.open();

		if (!CoolKey.isSoundAvailable()) {
			MessageBox messageBox = new MessageBox(GUI.shell,
					SWT.ICON_WARNING);
			messageBox.setText("Karta dźwiękowa");
			messageBox.setMessage("Karta dźwiękowa jest niedostępna.\n\n"
					+ "Sprawdź czy nie jest ona zajęta przez inny program.");
			messageBox.open();
		}

		if (CoolKey.getUsers().size() > 1) {
			new ChangeUser(true).open();
		}

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		// zamykanie programu
		if (CoolKey.getCurrentTest().isStarted()) {
			CoolKey.getCurrentTest().restart();
		}
		display.dispose();
	}

	public static void refresh() {
		menuBar.refresh();
		typingArea.refresh();
		buttonBar.refresh();
		keyboard.refresh();
		graphs.refresh();
	}
}
