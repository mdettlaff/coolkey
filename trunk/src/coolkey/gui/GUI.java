package coolkey.gui;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
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
		shell.setText("CoolKey 0.001");
		shell.setLayout(new GridLayout());
		
		/* Wyśrodkowanie shella */
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
		
		
		new MenuBar();
		
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
		
	}
}
