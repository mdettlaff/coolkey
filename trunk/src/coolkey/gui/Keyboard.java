package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;

import coolkey.CoolKey;

/**
 * Wizualizacja klawiatury, z podświetleniem następnego klawisza.
 */
public class Keyboard {
	private Canvas keyboard;
	private Character nextChar;

	public Keyboard() {
		keyboard = new Canvas(GUI.shell, SWT.BORDER);
		keyboard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		keyboard.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				/*
				 * Info dla Karola:
				 * Pamiętaj, że nasz program ma obsługiwać układ QWERTY
				 * i Dvorak, więc zrób tak, żeby można było łatwo przełączać
				 * się między tymi układami.
				 */
				e.gc.drawRoundRectangle(0, 0, 580, 229, 20, 20);
				if (CoolKey.getCurrentLesson().isMistakeMade()) {
					/*
					 * Po popełnieniu błędu przyciski podświetlają się
					 * na czerwono (normalnie na niebiesko).
					 */
					e.gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_RED));
				}
				if (nextChar == '\r') {
					e.gc.drawString("Następny: " + "Enter", 225, 100);
				} else {
					e.gc.drawString("Następny: " + nextChar, 225, 100);
				}
			}
		});

		refresh();
	}

	public void refresh() {
		nextChar = CoolKey.getCurrentLesson().getNextChar();
		keyboard.redraw();
	}
}
