package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import coolkey.CoolKey;

/**
 * Wyświetla statystyki użytkownika.
 */
public class Stats {
	private static Shell statsShell;
	private final int LEFT_MARGIN = 5;
	private final int BOTTOM_MARGIN = 10;
	private final int LEGEND_WIDTH = 150;
	

	public Stats() {
		statsShell = new Shell(GUI.shell, SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL | SWT.RESIZE);
		statsShell.setText("Statystyka");
		statsShell.setLayout(new FillLayout());
		statsShell.setMinimumSize(400, 500);

		CTabFolder stats = new CTabFolder(statsShell, SWT.BORDER | SWT.MULTI);

		CTabItem speed = new CTabItem(stats, SWT.NONE);
		speed.setText("Prędkość  ");
		CTabItem accuracy = new CTabItem(stats, SWT.NONE);
		accuracy.setText("Poprawność  ");

		Composite speedComp = new Composite(stats, SWT.NONE);
		speedComp.setLayout(new FillLayout());
		Composite accuracyComp = new Composite(stats, SWT.NONE);
		accuracyComp.setLayout(new FillLayout());
		speed.setControl(speedComp);
		accuracy.setControl(accuracyComp);

		final Canvas speedCanv = new Canvas(speedComp, SWT.BORDER);
		//TODO canvas statystyk prędkości
		/*Canvas accCanv = */new Canvas(accuracyComp, SWT.BORDER);
		//TODO canvas statystyk poprawności

		speedCanv.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent pe) {
				GC gc = pe.gc;
				Point canvasSize = speedCanv.getSize();
				gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLACK));
				gc.fillRectangle(0, 0, canvasSize.x, canvasSize.y); // tło
				
				/*
				 * Osie i etykiety
				 */
				gc.drawLine(5, 5, LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN);		//oś x 
				gc.drawString("znak/min", 7, 5);
				gc.drawLine(LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN, canvasSize.x-10, canvasSize.y-BOTTOM_MARGIN);	//oś y 
				gc.drawString("Test", canvasSize.x-30, canvasSize.y-30);	
				
				/*
				 * Legenda
				 */
				gc.drawRectangle(canvasSize.x-(LEGEND_WIDTH +10), 5, LEGEND_WIDTH, 40);
				gc.drawString("Prędkość", canvasSize.x-(LEGEND_WIDTH-20), 8);
				gc.drawString("Prędkość realna", canvasSize.x-(LEGEND_WIDTH-20), 24);
				gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_RED));
				gc.fillRectangle(canvasSize.x-(LEGEND_WIDTH + 5), 12, 10, 10);
				gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_BLUE));
				gc.fillRectangle(canvasSize.x-(LEGEND_WIDTH + 5), 27, 10, 10);
				
				int x=20;	//początkowy odstęp od lewej
				int inc = canvasSize.x / CoolKey.getUser().getStatistics().getSpeeds().size()-2; //odstęp między słupkami
				/*
				 * Prędkość.
				 */
				for (double speed : CoolKey.getUser().getStatistics().getSpeeds()) {
					/*
					 *  słupek
					 */
					gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_RED));
					Rectangle rec = new Rectangle(x, canvasSize.y-(int)speed-BOTTOM_MARGIN, 10, (int)speed);
					gc.fillRectangle(rec);
					/*
					 * prędkość nad słupkiem
					 */
					gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
					gc.drawString(String.format("%.2f", speed), x-10, canvasSize.y-(int)speed-BOTTOM_MARGIN-30);
					//zwiększenie odstępu do następnego słupka
					x+=inc;
				}
				x = 30; //przesunięcie o 10 względem czerwonych słupków.
				/*
				 * Prędkość realna. (analogicznie do wyżej)
				 */
				for (double speed2 : CoolKey.getUser().getStatistics().getRealSpeeds()) {
					gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_BLUE));
					Rectangle rec2 = new Rectangle(x, canvasSize.y-(int)speed2-BOTTOM_MARGIN, 10, (int)speed2);
					gc.fillRectangle(rec2);
					x+=inc;
				}
			}
		});
		
		System.out.println("Statystyki dla kolejnych ukończonych testów:");
		System.out.println("Prędkość: "
				+ CoolKey.getUser().getStatistics().getSpeeds());
		System.out.println("Realna prędkość: "
				+ CoolKey.getUser().getStatistics().getRealSpeeds());
		System.out.println("Poprawność: "
				+ CoolKey.getUser().getStatistics().getAccuracies());
		System.out.println("Statystyki dla poszczególnych znaków:");
		// Enter to '\r' (powrót karetki)
		System.out.println("Prędkość: "
				+ CoolKey.getUser().getStatistics().getCharSpeeds());
		System.out.println("Poprawność: "
				+ CoolKey.getUser().getStatistics().getCharAccuracies());

		statsShell.setSize(400, 300);
	}

	public void open() {
		statsShell.open();
	}
}
