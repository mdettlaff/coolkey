package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public class GUI {

	public static Display display;
	/**
	 * Okno główne.
	 */
	public static Shell shell;
	
	public static WritingArea writingArea;
	public static Keyboard keyboard;

	private GUI() {}

	/**
	 * Inicjalizuje graficzny interfejs użytkownika.
	 */
	public static void init() {
		
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
		
		writingArea = new WritingArea(); // obszar przepisywania
		
		/* Panel boczny z prawej */
		Canvas canvas2 = new Canvas(shell, SWT.BORDER);
		canvas2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 3));

		new ButtonBar();

		keyboard = new Keyboard();

		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
		
	}
}
