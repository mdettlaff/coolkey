package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;

import coolkey.CoolKey;

/**
 * Wizualizacja klawiatury, z podświetleniem następnego klawisza.
 */
public class Keyboard {
	private Canvas canvas;
	private Image buffer;
	private GC gc;

	private Character nextChar;

	public Keyboard() {
		canvas = new Canvas(GUI.shell, SWT.BORDER);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent pe) {
				Point canvasSize = canvas.getSize();
				if (gc == null) {
					buffer = new Image(GUI.display, canvasSize.x, canvasSize.y);
					gc = new GC(buffer);
				}
				gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLACK));
				gc.fillRectangle(0, 0, canvasSize.x, canvasSize.y); // tło
				/*
				 * Info dla Karola:
				 * Pamiętaj, że nasz program ma obsługiwać układ QWERTY
				 * i Dvorak, więc zrób tak, żeby można było łatwo przełączać
				 * się między tymi układami.
				 */
				gc.drawRoundRectangle(0, 0, 580, 229, 20, 20);
				if (CoolKey.getCurrentLesson().isMistakeMade()) {
					/*
					 * Po popełnieniu błędu przyciski podświetlają się
					 * na czerwono (normalnie na niebiesko).
					 */
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_RED));
				}
				if (nextChar == '\r') {
					gc.drawString("Następny: " + "Enter", 225, 100);
				} else {
					gc.drawString("Następny: " + nextChar, 225, 100);
				}
				pe.gc.drawImage(buffer, 0, 0);
			}
		});

		refresh();
	}

	public void refresh() {
		nextChar = CoolKey.getCurrentLesson().getNextChar();
		canvas.redraw();
	}
}
