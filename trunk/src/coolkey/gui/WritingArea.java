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
import coolkey.Sound;

/**
 * Obszar na którym odbywa się przepisywanie.
 */
public class WritingArea {
	public static final Color COLOR_CORRECTION = new Color(GUI.display, 192, 0, 216);

	private final int MAX_LINES = 10;
	/**
	 * Maksymalna ilość przepisanych linii jaką widać na ekranie.
	 */
	private final int MAX_TYPING_LINES = 3;
	private final int LEFT_MARGIN = 15;
	private final int TOP_MARGIN_TEXT = 6;
	private final int TOP_MARGIN_WRITTEN = 27;
	private final int LINE_HEIGHT = 44;

	private Canvas canvas;
	private Sound typingSound;
	private Sound mistakeSound;

	public WritingArea() {
		canvas = new Canvas(GUI.shell, SWT.BORDER | SWT.DOUBLE_BUFFERED);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		typingSound = new Sound(0);
		mistakeSound = new Sound(1);

		canvas.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				pressKey(keyEvent.character);
			}

			@Override
			public void keyReleased(KeyEvent keyEvent) {}
		});

		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent pe) {
				GC gc = pe.gc;
				Point canvasSize = canvas.getSize();
				gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLACK));
				gc.setFont(new Font(GUI.display, "Courier New", 10, SWT.NORMAL));
				gc.fillRectangle(0, 0, canvasSize.x, canvasSize.y); // tło
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
				if (CoolKey.getCurrentLesson().isMistakeMade()) {
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_RED));
				}
				y -= LINE_HEIGHT;
				gc.drawString(cursor, x, y, true);
				// zaznacz znaki które zostały poprawione
				gc.setForeground(COLOR_CORRECTION);
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

		canvas.redraw();
	}

	/**
	 * Obsługa zdarzenia polegającego na wpisaniu danego znaku.
	 *
	 * @param c Wpisywany znak.
	 */
	public void pressKey(char c) {
		if (!CoolKey.getCurrentLesson().isFinished()) {
			if (c == '\r') {
				if (CoolKey.getUser().getConfig().isSoundOn()) {
					typingSound.play();
				}
				CoolKey.getCurrentLesson().typeEnter();
				if (CoolKey.getCurrentLesson().isFinished()) {
					LessonResults finalResults = CoolKey.getCurrentLesson().getResults();
					GUI.graphs.addFinalResults(finalResults);
					GUI.graphs.refresh();
					new ResultsMessage(finalResults);
				}
			} else if (c == SWT.BS) {
				if (CoolKey.getUser().getConfig().isSoundOn()) {
					typingSound.play();
				}
				CoolKey.getCurrentLesson().typeBackspace();
			} else if (!Character.isISOControl(c)) {
				if (!CoolKey.getCurrentLesson().isStarted()) {
					GUI.graphs.reset(); // zaczynamy przepisywanie
				}
				if (CoolKey.getCurrentLesson().typeChar(c)) {
					if (CoolKey.getUser().getConfig().isSoundOn()) {
						typingSound.play();
					}
				} else {
					if (CoolKey.getUser().getConfig().isSoundOn()) {
						mistakeSound.play();
					}
				}
			}
			canvas.redraw();
			if (CoolKey.getCurrentLesson().isPaused()) {
				CoolKey.getCurrentLesson().pauseUnpause();
			}
			GUI.buttonBar.refresh();
			GUI.keyboard.refresh();
		}
	}

	/**
	 * Wyświetl aktualną wersję tego elementu.
	 */
	public void refresh() {
		canvas.redraw();
	}

	/**
	 * Skup uwagę na tym obszarze, aby przechwytywać zdarzenia.
	 */
	public void setFocus() {
		canvas.setFocus();
	}

	public void close() {
		typingSound.close();
		mistakeSound.close();
	}
}
