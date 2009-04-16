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
import coolkey.LessonResults;

/**
 * Obszar na którym odbywa się przepisywanie.
 */
public class WritingArea {
	private final int MAX_LINES = 10;
	/**
	 * Maksymalna ilość przepisanych linii jaką widać na ekranie.
	 */
	private final int MAX_TYPING_LINES = 3;
	private final int LEFT_MARGIN = 15;
	private final int TOP_MARGIN_TEXT = 6;
	private final int TOP_MARGIN_WRITTEN = 27;
	private final int LINE_HEIGHT = 44;

	private Canvas writingArea;

	public WritingArea() {
		writingArea = new Canvas(GUI.shell, SWT.BORDER);
		writingArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		writingArea.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if (!CoolKey.getCurrentLesson().isFinished()) {
					char c = keyEvent.character;
					if (c == '\r') {
						CoolKey.getCurrentLesson().typeEnter();
						if (CoolKey.getCurrentLesson().isFinished()) {
							LessonResults results =
								new LessonResults(CoolKey.getCurrentLesson());
							new ResultsMessage(results);
						}
					} else if (c == SWT.BS) {
						CoolKey.getCurrentLesson().typeBackspace();
					} else if (!Character.isISOControl(c)) {
						CoolKey.getCurrentLesson().typeChar(c);
					}
					writingArea.redraw();
				}
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
				// zakres linii do wyświetlenia
				int startLine = CoolKey.getCurrentLesson().
						getWrittenLines().size() - MAX_TYPING_LINES;
				if (startLine < 0) {
					startLine = 0;
				}
				int endLine = startLine + MAX_TYPING_LINES - 1;
				if (endLine > CoolKey.getCurrentLesson().getWrittenLines().size() - 1) {
					endLine = CoolKey.getCurrentLesson().getWrittenLines().size() - 1;
				}
				// narysuj tekst do przepisania
				int x = LEFT_MARGIN;
				int y = TOP_MARGIN_TEXT;
				for (int i = startLine; i <= endLine; i++) {
					String line = CoolKey.getCurrentLesson().getTextLines().get(i);
					gc.drawString(line, x, y);
					y += LINE_HEIGHT;
				}
				for (int i = endLine + 1; i < CoolKey.getCurrentLesson().getTextLines().size()
						&& i < endLine + 1 + MAX_LINES - 2 * (1 + endLine - startLine); i++) {
					String line = CoolKey.getCurrentLesson().getTextLines().get(i);
					gc.drawString(line, x, y);
					y += LINE_HEIGHT / 2;
				}
				// narysuj przepisany tekst
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLUE));
				y = TOP_MARGIN_WRITTEN;
				for (int i = startLine; i <= endLine; i++) {
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
				boolean noMistakes = true;
				for (String line : CoolKey.getCurrentLesson().getMistakes()) {
					for (int i=0; i < line.length(); i++) {
						if (line.charAt(i) != ' ') {
							noMistakes = false;
							break;
						}
					}
					if (!noMistakes) {
						break;
					}
				}
				if (!noMistakes) {
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_RED));
				}
				y -= LINE_HEIGHT;
				gc.drawString(cursor, x, y, true);
				// zaznacz znaki które zostały poprawione
				gc.setForeground(new Color(GUI.display, 192, 0, 216));
				y = TOP_MARGIN_WRITTEN;
				for (int i = startLine; i <= endLine; i++) {
					String line = CoolKey.getCurrentLesson().getCorrections().get(i);
					gc.drawString(line, x, y, true);
					y += LINE_HEIGHT;
				}
				// zaznacz pomyłki na czerwono
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_RED));
				y = TOP_MARGIN_WRITTEN;
				for (int i = startLine; i <= endLine; i++) {
					String line = CoolKey.getCurrentLesson().getMistakes().get(i);
					gc.drawString(line, x, y, true);
					y += LINE_HEIGHT;
				}
			}
		});

		writingArea.redraw();
	}
}
