package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public class GUI {

	public static Display display;
	/**
	 * Okno główne.
	 */
	public static Shell shell;
	
	/**
	 * Inicjalizuje graficzny interfejs użytkownika.
	 */
	public static void run() {
		
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
		shell.setSize(780, 580);
		
		new MenuBar();
		
		new WritingArea(); // obszar przepisywania
		
		/*panel boczny z prawej*/
		Canvas canvas2 = new Canvas(shell, SWT.BORDER);
		canvas2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 3));
		
		/*Przyciski pauzy i zakończenia lekcji*/
		Composite comp = new Composite(shell, SWT.NONE);
		comp.setLayout(new GridLayout(4, false));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		Button pause = new Button(comp, SWT.PUSH);
		pause.setText("Pauza");
		Button endLesson = new Button(comp, SWT.PUSH);
		endLesson.setText("Zakończ lekcję");

		new Keyboard();
		
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
		
	}
}
