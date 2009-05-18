package coolkey.gui;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import coolkey.CoolKey;

/**
 * Wyświetla statystyki użytkownika.
 */
public class Stats {
	private static Shell statsShell;
	private final int LEFT_MARGIN = 5;
	private final int BOTTOM_MARGIN = 10;
	private final int BOTTOM_MARGIN2 = 20;
	private final int LEGEND_WIDTH = 150;
	

	public Stats() {
		statsShell = new Shell(GUI.shell, SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL | SWT.RESIZE);
		statsShell.setText("Statystyka");
		statsShell.setLayout(new FillLayout());
		statsShell.setMinimumSize(760, 600);

		CTabFolder stats = new CTabFolder(statsShell, SWT.BORDER | SWT.MULTI);

		CTabItem speed = new CTabItem(stats, SWT.NONE);
		speed.setText("Poszczególne testy  ");
		CTabItem accuracy = new CTabItem(stats, SWT.NONE);
		accuracy.setText("Poszczególne znaki ");

		Composite speedComp = new Composite(stats, SWT.NONE);
		speedComp.setLayout(new GridLayout(1, false));
		Composite accuracyComp = new Composite(stats, SWT.NONE);
		accuracyComp.setLayout(new GridLayout(1, false));
		speed.setControl(speedComp);
		accuracy.setControl(accuracyComp);
		
		Group speedTypeTests = new Group(speedComp, SWT.SHADOW_IN);
		speedTypeTests.setLayout(new GridLayout());
		speedTypeTests.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		final Button accTests = new Button(speedTypeTests, SWT.RADIO);
		accTests.setText("Poprawność dla poszczególnych testów");
		accTests.setSelection(true);
		Button speedTests = new Button(speedTypeTests, SWT.RADIO);
		speedTests.setText("Szybkość dla poszczególnych testów");
		
		Group statsTypeKeys = new Group(accuracyComp, SWT.SHADOW_IN);
		statsTypeKeys.setLayout(new GridLayout());
		statsTypeKeys.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		final Button accKeys = new Button(statsTypeKeys, SWT.RADIO);
		accKeys.setText("Poprawność dla poszczególnych liter");
		accKeys.setSelection(true);
		Button speedKeys = new Button(statsTypeKeys, SWT.RADIO);
		speedKeys.setText("Szybkość dla poszczególnych liter");
		
		final Canvas speedCanv = new Canvas(speedComp, SWT.BORDER);
		speedCanv.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final Canvas accCanv = new Canvas(accuracyComp, SWT.BORDER);
		accCanv.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		speedCanv.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent pe) {
				GC gc = pe.gc;
				Point canvasSize = speedCanv.getSize();
				gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLACK));
				gc.fillRectangle(0, 0, canvasSize.x, canvasSize.y); // tło
				Font font = new Font(GUI.display,"Arial",8, SWT.None);
				gc.setFont(font);

				int inc;
				int x=20;	//początkowy odstęp od lewej
				if (CoolKey.getUser().getStatistics().getSpeeds().size() != 0)
					inc = canvasSize.x / CoolKey.getUser().getStatistics().getSpeeds().size()-2; //odstęp między słupkami
				else inc = 20;
				
				if (!accTests.getSelection()) {
					/*
					 * Osie i etykiety
					 */
					gc.drawLine(LEFT_MARGIN, 5, LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN);		//oś y 
					gc.drawString("znak/min", 25, 5);
					gc.drawLine(LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN, canvasSize.x-10, canvasSize.y-BOTTOM_MARGIN);	//oś x 
					gc.drawString("Test", canvasSize.x-30, canvasSize.y-30);
					
					/*
					 * Skala
					 */
					int skala=100;
					for (int j=canvasSize.y-BOTTOM_MARGIN-100; j>=40; j-=100) {
						gc.drawLine(LEFT_MARGIN, j, canvasSize.x-LEFT_MARGIN, j);
						gc.drawString(Integer.toString(skala), x-13, j-12, true);
						skala+=100;
					}
					for (int j=canvasSize.y-BOTTOM_MARGIN-50; j>=40; j-=50) {
						gc.drawLine(LEFT_MARGIN, j, canvasSize.x-LEFT_MARGIN, j);
					}
					
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
					
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_RED));
					/*
					 * Prędkość.
					 */
					x=40;
					List<Double> speeds = CoolKey.getUser().getStatistics().getSpeeds();
					for (int i=0; i<speeds.size(); i++) {
						double speed = speeds.get(i);
						gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_RED));
						gc.fillOval(x-3, canvasSize.y-(int)speed-BOTTOM_MARGIN-3, 6, 6); 
						/*
						 * prędkość nad słupkiem
						 */
						gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
						gc.drawString(String.format("%.2f", speed), x-10, canvasSize.y-(int)speed-BOTTOM_MARGIN-30);
						if (i>0) {
							gc.drawLine(x, canvasSize.y-(int)speed-BOTTOM_MARGIN, x-inc, canvasSize.y-speeds.get(i-1).intValue()-BOTTOM_MARGIN);
						}
						
						x+=inc; //zwiększenie odstępu do następnego słupka
					}
					/*
					 * Prędkość realna. (analogicznie do wyżej)
					 */
					x = 40;
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLUE));
					speeds = CoolKey.getUser().getStatistics().getRealSpeeds();
					for (int i=0; i<speeds.size(); i++) {
						double speed2 = speeds.get(i);
						gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_BLUE));
						gc.fillOval(x-3, canvasSize.y-(int)speed2-BOTTOM_MARGIN-3, 6, 6);
						gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
						gc.drawString(String.format("%.2f", speed2), x-13, canvasSize.y-(int)speed2-BOTTOM_MARGIN+15);
						if (i>0) {
							gc.drawLine(x, canvasSize.y-(int)speed2-BOTTOM_MARGIN+1, x-inc, canvasSize.y-speeds.get(i-1).intValue()-BOTTOM_MARGIN+1);
						}
						x+=inc;
					}
				} else {
					/* Poprawność */
					gc.drawLine(LEFT_MARGIN, 5, LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN);		//oś y
					gc.drawLine(LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN, canvasSize.x-LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN); //oś x
					gc.drawString("Poprawność %", 7, 5);  //etykieta y
					gc.drawString("Test", canvasSize.x-30, canvasSize.y-20); //etykieta x
					/*
					 *  Skala
					 */
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_GRAY));
					int skala=5;
					for (int j=canvasSize.y-BOTTOM_MARGIN-15; skala<100; j-=15) {
						gc.drawLine(LEFT_MARGIN, j, canvasSize.x-LEFT_MARGIN, j);
						skala+=5;
					}
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLACK));
					skala=20;
					for (int j=canvasSize.y-BOTTOM_MARGIN-60; skala<=100; j-=60) {
						gc.drawLine(LEFT_MARGIN, j, canvasSize.x-LEFT_MARGIN, j);
						gc.drawString(Integer.toString(skala), x-13, j-12, true);
						skala+=20;
					}
										
					int y;
					x = 35;
					if (CoolKey.getUser().getStatistics().getAccuracies().size()!=0)
						inc = canvasSize.x / CoolKey.getUser().getStatistics().getAccuracies().size();
					List<Double> testsSpeeds = CoolKey.getUser().getStatistics().getAccuracies();
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_RED));
					for (int i=0; i<testsSpeeds.size(); i++) {
						double s = testsSpeeds.get(i);
						y = canvasSize.y-(int)s*3-BOTTOM_MARGIN;
						gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_RED));
						gc.fillOval(x-3, y-3, 6, 6);
						if (i>0) {
							gc.drawLine(x, y+1, x-inc, canvasSize.y-testsSpeeds.get(i-1).intValue()*3-BOTTOM_MARGIN+1);
						}	
						gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
						gc.drawString(String.format("%.2f", s) + "%", x-10, y-30);
						x+=inc;
				    }
				}
				gc.dispose();
			}
		});
		
		accCanv.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent pe) {
				GC gc = pe.gc;
				Point canvasSize = accCanv.getSize();
				gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLACK));
				gc.fillRectangle(0, 0, canvasSize.x, canvasSize.y); // tło
				
				int inc=20;
				if (CoolKey.getUser().getStatistics().getCharAccuracies().size()!=0)
					inc = canvasSize.x / CoolKey.getUser().getStatistics().getCharAccuracies().size();
				int x=20;
				int y;
				
				if (accKeys.getSelection()) {
				
					Font font = new Font(GUI.display,"Arial",7, SWT.None);
					gc.setFont(font);
					gc.drawLine(LEFT_MARGIN, 5, LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN2);		//oś y
					gc.drawLine(LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN2, canvasSize.x-10, canvasSize.y-BOTTOM_MARGIN2); //oś x
					gc.drawString("Poprawność", 7, 5);
					
					Map<Character, Double> charAccuracies = CoolKey.getUser().getStatistics().getCharAccuracies();
					TreeSet<Character> chars = new TreeSet<Character>(charAccuracies.keySet());
					for (Character c : chars) {
						double accuracy = charAccuracies.get(c);
						y = canvasSize.y-(int)accuracy*3-BOTTOM_MARGIN2;
						gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_RED));
						Rectangle rec = new Rectangle(x, y, 8, (int)accuracy*3);
						gc.fillRectangle(rec);
	
						gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
						GraphicsUtils.drawVerticalText(String.format("%.2f", accuracy) + "%", x-2, y-40, gc, SWT.UP);
						
						if (c == ' ')
							gc.drawString("spac.", x, canvasSize.y-BOTTOM_MARGIN2+3, true);
						else if (c == '\r')
							gc.drawString("ent.", x, canvasSize.y-BOTTOM_MARGIN2+3, true);
						else
							gc.drawString(c.toString(), x, canvasSize.y-BOTTOM_MARGIN2+3, true);
						x+=inc;
				    }
				}  else {
					Font font = new Font(GUI.display,"Arial",7, SWT.None);
					gc.setFont(font);
					gc.drawLine(5, 5, LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN2);		//oś x 
					gc.drawString("Prędkość", 7, 5);
					gc.drawLine(LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN2, canvasSize.x-10, canvasSize.y-BOTTOM_MARGIN2);	//oś y 
					if (CoolKey.getUser().getStatistics().getCharSpeeds().size()!=0)
						inc = canvasSize.x / CoolKey.getUser().getStatistics().getCharSpeeds().size();
					/*
					 *  Skala
					 */
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_GRAY));
					for (int j=canvasSize.y-BOTTOM_MARGIN-50; j>=50; j-=50 ) {
						gc.drawLine(LEFT_MARGIN, j, canvasSize.x-LEFT_MARGIN, j);
					}
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLACK));
					int skala=100;
					for (int j=canvasSize.y-BOTTOM_MARGIN-100; j>=50; j-=100) {
						gc.drawLine(LEFT_MARGIN, j, canvasSize.x-LEFT_MARGIN, j);
						gc.drawString(Integer.toString(skala), x-13, j-12, true);
						skala+=100;
					}

					Map<Character, Double> charSpeeds = CoolKey.getUser().getStatistics().getCharSpeeds();
					TreeSet<Character> chars = new TreeSet<Character>(charSpeeds.keySet());
				    for (Character c : chars) {
				        double speed = charSpeeds.get(c);
				        gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_BLUE));
						Rectangle rec = new Rectangle(x, canvasSize.y-(int)speed-BOTTOM_MARGIN2, 8, (int)speed);
						gc.fillRectangle(rec);

						gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
						GraphicsUtils.drawVerticalText(String.format("%.2f", speed), x-1, canvasSize.y-(int)speed-BOTTOM_MARGIN2-30, gc, SWT.UP);
						if (c == ' ')
							gc.drawString("spac.", x, canvasSize.y-BOTTOM_MARGIN2+3, true);
						else if (c == '\r')
							gc.drawString("ent.", x, canvasSize.y-BOTTOM_MARGIN2+3, true);
						else
							gc.drawString(c.toString(), x, canvasSize.y-BOTTOM_MARGIN2+3);
						x += inc;
				    }
				}
			    gc.dispose();
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
		
		accTests.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				speedCanv.redraw();
			}
		});
		speedTests.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				speedCanv.redraw();
			}
		});
		accKeys.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				accCanv.redraw();
			}
		});
		speedKeys.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				accCanv.redraw();
			}
		});
	}

	public void open() {
		statsShell.open();
	}
}
