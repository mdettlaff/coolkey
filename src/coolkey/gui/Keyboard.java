package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;

/**
 * Tworzy obiekt klawiatura.
 * @author kd
 *
 */
public class Keyboard {
	public Canvas klawiatura;
		
	public Keyboard() {
		klawiatura = new Canvas(GUI.shell, SWT.BORDER);
		klawiatura.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		
		klawiatura.addPaintListener(new PaintListener() {
		      public void paintControl(PaintEvent e) {
		        e.gc.drawRoundRectangle(0, 0, 550, 200, 20, 20);
		      }
		    });
		
		klawiatura.redraw();
	}
	
	
	
}
