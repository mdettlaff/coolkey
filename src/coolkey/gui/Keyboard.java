package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;

/**
 * Wizualizacja klawiatury.
 * @author kd
 *
 */
public class Keyboard {
	public Canvas keyboard;

	public Keyboard() {
		keyboard = new Canvas(GUI.shell, SWT.BORDER);
		keyboard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		keyboard.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.drawRoundRectangle(0, 0, 685, 229, 20, 20);
			}
		});

		keyboard.redraw();
	}
}
