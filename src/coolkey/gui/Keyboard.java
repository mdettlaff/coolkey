package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;

import coolkey.CoolKey;

/**
 * Wizualizacja klawiatury, z podświetleniem następnego klawisza.
 */
public class Keyboard {
	private final int CANVAS_HEIGHT = 240;
	private Canvas canvas;

	private Character nextChar;

	public Keyboard() {
		canvas = new Canvas(GUI.shell, SWT.BORDER | SWT.DOUBLE_BUFFERED);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd.heightHint = CANVAS_HEIGHT;
		canvas.setLayoutData(gd);

		new KeyPad().qwerty(canvas);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent pe) {
				GC gc = pe.gc;
				Point canvasSize = canvas.getSize();
				gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLACK));
				gc.fillRectangle(0, 0, canvasSize.x, canvasSize.y); // tło
				//gc.drawRoundRectangle(0, 0, canvasSize.x-5, canvasSize.y-5, 20, 20);
				/*
				 * Info dla Karola:
				 * Pamiętaj, że nasz program ma obsługiwać układ QWERTY
				 * i Dvorak, więc zrób tak, żeby można było łatwo przełączać
				 * się między tymi układami.
				 */
				/*Button q = new Button(canvas, SWT.PUSH);
				q.setBounds(5, 5, 30, 30);
				q.setText("q");*/
				if (CoolKey.getCurrentLesson().isMistakeMade()) {
					/*
					 * Po popełnieniu błędu przyciski podświetlają się
					 * na czerwono (normalnie na niebiesko).
					 */
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_RED));
				}
				if (nextChar == '\r') {
					gc.drawString("Następny: " + "Enter", 225, 100);
					//KeyPad.setFocusNext(nextChar);
				} else {
					gc.drawString("Następny: " + nextChar, 225, 100);
					//KeyPad.setFocusNext(nextChar);
				}
			}
		});

		refresh();
	}

	public void refresh() {
		nextChar = CoolKey.getCurrentLesson().getNextChar();
		canvas.redraw();
	}
}
