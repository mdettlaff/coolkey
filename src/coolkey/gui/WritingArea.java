package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;

import coolkey.CoolKey;

/**
 * Obszar na którym odbywa się przepisywanie.
 */
public class WritingArea {
	private final int LINE_HEIGHT = 44;

	private Canvas writingArea;

	public WritingArea() {
		writingArea = new Canvas(GUI.shell, SWT.BORDER);
		writingArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		writingArea.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent keyEvent) {
				char c = keyEvent.character;
				if (c == '\r') {
					CoolKey.getCurrentLesson().typeEnter();
				} else if (c == SWT.BS) {
					CoolKey.getCurrentLesson().typeBackspace();
				} else if (!Character.isISOControl(c)) {
					CoolKey.getCurrentLesson().typeChar(c);
				}
				writingArea.redraw();
			}

			public void keyReleased(KeyEvent keyEvent) {}
		});

		writingArea.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
				gc.setFont(new Font(GUI.display, "Courier New", 10, SWT.NORMAL));
				Point areaSize = writingArea.getSize();
				gc.fillRectangle(0, 0, areaSize.x, areaSize.y);
				// narysuj tekst do przepisania
				int x = 15;
				int y = 8;
				for (String line : CoolKey.getCurrentLesson().getTextLines()) {
					gc.drawString(line, x, y);
					y += LINE_HEIGHT;
				}
				// narysuj przepisany tekst
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLUE));
				y = 28;
				for (int i=0; i < CoolKey.getCurrentLesson().getWrittenLines().size(); i++) {
					String line = CoolKey.getCurrentLesson().getWrittenLines().get(i);
					gc.drawString(line, x, y);
					y += LINE_HEIGHT;
				}
				// narysuj kursor
				String lastLine = CoolKey.getCurrentLesson().getWrittenLines().get(
						CoolKey.getCurrentLesson().getWrittenLines().size() - 1);
				String cursor = "";
				for (int i=0; i < lastLine.length(); i++) {
					cursor += ' ';
				}
				cursor += '_';
				if (CoolKey.getCurrentLesson().getMistakesCount() > 0) {
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_RED));
				}
				y -= LINE_HEIGHT;
				gc.drawString(cursor, x, y, true);
				// zaznacz znaki które zostały poprawione
				gc.setForeground(new Color(GUI.display, 192, 0, 192));
				y = 28;
				for (String line : CoolKey.getCurrentLesson().getCorrections()) {
					gc.drawString(line, x, y, true);
					y += LINE_HEIGHT;
				}
				// zaznacz pomyłki na czerwono
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_RED));
				y = 28;
				for (String line : CoolKey.getCurrentLesson().getMistakes()) {
					gc.drawString(line, x, y, true);
					y += LINE_HEIGHT;
				}
			}
		});

		writingArea.redraw();
	}
}
