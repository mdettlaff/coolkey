package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;

import coolkey.CoolKey;

public class WritingArea {
	private Canvas writingArea;
	
	private final int lineHeight = 50;

	public WritingArea() {
		writingArea = new Canvas(GUI.shell, SWT.BORDER);
		writingArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		writingArea.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent keyEvent) {
				CoolKey.getCurrentLesson().type(keyEvent.character);
			}

			public void keyReleased(KeyEvent keyEvent) {}
		});

		writingArea.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
				Point areaSize = writingArea.getSize();
				gc.fillRectangle(0, 0, areaSize.x, areaSize.y);
				int x = 15;
				int y = 10;
				for (String line : CoolKey.getCurrentLesson().getTextLines()) {
					gc.drawString(line, x, y);
					y += lineHeight;
				}
			}
		});

		writingArea.redraw();
	}
}
