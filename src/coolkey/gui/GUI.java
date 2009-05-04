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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
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
		shell.setSize(932, 580);

		new MenuBar();
		writingArea = new WritingArea(); // obszar przepisywania
		graphs = new Graphs(); // panel z wykresami po prawej (nowy wątek)
		buttonBar = new ButtonBar();
		keyboard = new Keyboard();

		shell.getDisplay().addFilter(SWT.KeyDown, new Listener() {
			public void handleEvent(Event event) {
				if (event.keyCode == SWT.CONTROL) {
					KeyPad.setFocus((int)17);
					KeyPad.setFocus((int)14);
					return;
				}
				if (event.keyCode == SWT.ALT) {
					KeyPad.setFocus((int)0);
					KeyPad.setFocus((int)1);
					return;
				}
				if (event.keyCode == SWT.SHIFT) {
					KeyPad.setFocus((int)16);
					KeyPad.setFocus((int)18);
					return;
				}
				if (event.keyCode == SWT.CAPS_LOCK) {
					KeyPad.setFocus((int)20);
					return;
				}
				KeyPad.setFocus(event.keyCode);
			}
		});
		shell.getDisplay().addFilter(SWT.KeyUp, new Listener() {
			public void handleEvent(Event event) {
				if (event.keyCode == SWT.CONTROL) {
					KeyPad.removeFocus((int)17);
					KeyPad.removeFocus((int)14);
					return;
				}
				if (event.keyCode == SWT.ALT) {
					KeyPad.removeFocus((int)0);
					KeyPad.removeFocus((int)1);
					return;
				}
				if (event.keyCode == SWT.SHIFT) {
					KeyPad.removeFocus((int)16);
					KeyPad.removeFocus((int)18);
					return;
				}
				if (event.keyCode == SWT.CAPS_LOCK) {
					KeyPad.removeFocus((int)20);
					return;
				}
				KeyPad.removeFocus(event.keyCode);
			}
		});
		
		shell.open();

		if (!CoolKey.isSoundAvailable()) {
			MessageBox messageBox = new MessageBox(GUI.shell,
					SWT.ICON_WARNING);
			messageBox.setText("Ostrzeżenie");
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