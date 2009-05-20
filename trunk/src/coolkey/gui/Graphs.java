package coolkey.gui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;

import coolkey.CoolKey;
import coolkey.TestResults;

/**
 * Panel z wykresami po prawej stronie. Wykresy są animowane w osobnym wątku.
 */
public class Graphs implements Runnable	{
	private final int CANVAS_WIDTH = 195;
	private final int SLEEP_TIME = 1000;
	private final int MARGIN_TOP = 20;
	private final int MARGIN_TOP2 = 160;
	private final int MARGIN_TOP3 = 320;
	private final int MARGIN_LEFT = 20;
	/**
	 * Przedział czasu, z którego obliczana jest prędkość chwilowa.
	 */
	private final int CURRENT_SPEED_INTERVAL = 10;

	private Thread thread;
	private Canvas canvas;

	private List<TestResults> resultsList = new ArrayList<TestResults>();

	public Graphs() {
		canvas = new Canvas(GUI.shell, SWT.BORDER | SWT.DOUBLE_BUFFERED);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 3);
		gd.widthHint = CANVAS_WIDTH;
		canvas.setLayoutData(gd);

		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent pe) {
				GC gc = pe.gc;
				Point canvasSize = canvas.getSize();
				gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLACK));
				gc.fillRectangle(0, 0, canvasSize.x, canvasSize.y); // tło
				Font font = new Font(GUI.display,"Arial",7, SWT.None);
				gc.setFont(font);
	
				/* 
				 * skala1 
				 */
				int skala=500;
				int j;
				for (j=MARGIN_TOP+20; j<=140; j+=20) {
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_GRAY));
					gc.drawLine(MARGIN_LEFT-2, j, canvasSize.x-MARGIN_LEFT, j);
					gc.drawLine(MARGIN_LEFT-2, j+MARGIN_TOP2, canvasSize.x-MARGIN_LEFT, j+MARGIN_TOP2);
					gc.drawLine(MARGIN_LEFT-2, j+MARGIN_TOP3, canvasSize.x-MARGIN_LEFT, j+MARGIN_TOP3);
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLACK));
					if (skala == 0) {
						gc.drawString(Integer.toString(skala), MARGIN_LEFT-9, j-5, true);
						gc.drawString(Integer.toString(skala), MARGIN_LEFT-9, j+MARGIN_TOP2-5, true);
						gc.drawString(Integer.toString(skala), MARGIN_LEFT-9, j+MARGIN_TOP3-5, true);
						continue;
					}	
					gc.drawString(Integer.toString(skala), MARGIN_LEFT-19, j-5, true);
					gc.drawString(Integer.toString(skala), MARGIN_LEFT-19, j+MARGIN_TOP2-5, true);
					gc.drawString(Integer.toString(skala), MARGIN_LEFT-19, j+MARGIN_TOP3-5, true);
					skala-=100;
				}
				
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLACK));
				/* Osie */
				gc.drawLine(MARGIN_LEFT, MARGIN_TOP, MARGIN_LEFT, MARGIN_TOP+122);		//oś y 
				gc.drawLine(MARGIN_LEFT, MARGIN_TOP+MARGIN_TOP2, MARGIN_LEFT, MARGIN_TOP+122+MARGIN_TOP2);		//oś y 
				gc.drawLine(MARGIN_LEFT, MARGIN_TOP+MARGIN_TOP3, MARGIN_LEFT, MARGIN_TOP+122+MARGIN_TOP3);		//oś y
				gc.drawString("Prędkość średnia (ogólnie): 0", MARGIN_LEFT, 5);
				gc.drawString("Prędkość średnia (faktycznie): 0", MARGIN_LEFT, 5+MARGIN_TOP2);
				gc.drawString("Prędkość chwilowa: 0", MARGIN_LEFT, 5+MARGIN_TOP3);
				gc.drawLine(MARGIN_LEFT-2, MARGIN_TOP+120, canvasSize.x-MARGIN_LEFT, MARGIN_TOP+120);	//oś x 
				gc.drawLine(MARGIN_LEFT-2, MARGIN_TOP+120+MARGIN_TOP2, canvasSize.x-MARGIN_LEFT, MARGIN_TOP+120+MARGIN_TOP2);	//oś x
				gc.drawLine(MARGIN_LEFT-2, MARGIN_TOP+120+MARGIN_TOP3, canvasSize.x-MARGIN_LEFT, MARGIN_TOP+120+MARGIN_TOP3);	//oś x
				
				if (resultsList.size() > 0) {
					TestResults newestResults = resultsList.get(
							resultsList.size() - 1);
					String progress = String.format("%.0f%%", newestResults.getProgress());
					int seconds = newestResults.getTypingTimeMilliseconds() / 1000;
					gc.drawString("Prędkość średnia (ogólnie): " + String.format("%.1f", newestResults.getSpeed()), MARGIN_LEFT, 5);
					gc.drawString("Prędkość średnia (faktycznie): " + String.format("%.1f", newestResults.getRealSpeed()), MARGIN_LEFT, 5+MARGIN_TOP2);
					gc.drawString("Prędkość chwilowa: " + String.format("%.1f", newestResults.getSpeed()), MARGIN_LEFT, 5+MARGIN_TOP3);
					//gc.drawString(seconds + "", 15, 40);
					//gc.drawString(progress, 15, 76);
					String s = "";
					s += String.format("prędkość średnia: %.1f znaków/min\n",
							newestResults.getSpeed());
					s += String.format("realna prędkość średnia: %.1f znaków/min\n",
							newestResults.getRealSpeed());
					double currentSpeed = currentSpeed(CURRENT_SPEED_INTERVAL);
					if (!(currentSpeed < 0)) {
						s += String.format("prędkość chwilowa: %.1f znaków/min\n",
								currentSpeed);
					} else {
						s += "prędkość chwilowa: brak danych\n";
					}
					s += String.format("poprawność: %.1f%% ",
							newestResults.getCorrectness());
					s += String.format("(%d błędów, %d poprawek)",
							newestResults.getMistakesCount(),
							newestResults.getCorrectionsCount());
					System.out.println('\n' + s);
					
					/*wykres*/
					int x=MARGIN_LEFT+1;
					int y=120+MARGIN_TOP;
					double inc=(canvasSize.x-40)/resultsList.size();
					if (inc == 0) {
						inc++;
					}
					
					/* Pierwszy element z tablicy wyników */
					TestResults result = resultsList.get(0);
					double sample = result.getSpeed();
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_GREEN));
					gc.drawLine((int)inc+x, y-(int)sample/5, x, y-(int)sample/5);
					gc.drawLine((int)inc+x, (y-(int)sample/5)-1, x, (y-(int)sample/5)-1);
					sample = result.getSpeed();
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_RED));
					gc.drawLine((int)inc+x, y-(int)sample/5+MARGIN_TOP2, x, y-(int)sample/5+MARGIN_TOP2);
					gc.drawLine((int)inc+x, (y-(int)sample/5)-1+MARGIN_TOP2, x, (y-(int)sample/5)-1+MARGIN_TOP2);
					x += inc;
					
					for (int i=(resultsList.size()/159<1 ? 1 : resultsList.size()-(158*(resultsList.size()/159)-1)); i<resultsList.size(); i++) {
						result = resultsList.get(i);
						sample = result.getSpeed();
						gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_GREEN));
						gc.drawLine(x+(int)inc, y-(int)sample/5, x, y-(int)(resultsList.get(i-1).getSpeed()/5));
						gc.drawLine(x+(int)inc, (y-(int)sample/5)-1, x, (y-(int)(resultsList.get(i-1).getSpeed()/5))-1);	//pogrubiona linia
						sample = result.getRealSpeed();
						gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_RED));
						gc.drawLine(x+(int)inc, y-(int)sample/5+MARGIN_TOP2, x, y-(int)(resultsList.get(i-1).getRealSpeed()/5)+MARGIN_TOP2);
						gc.drawLine(x+(int)inc, (y-(int)sample/5)-1+MARGIN_TOP2, x, (y-(int)(resultsList.get(i-1).getRealSpeed()/5))-1+MARGIN_TOP2);
						x+=inc;
					}
					inc = 2;
					x=MARGIN_LEFT+1;
					for (int i=(resultsList.size() < 79 ? 1 : resultsList.size()-(78*(resultsList.size()/78)-1)); i<resultsList.size(); i++) {
						result = resultsList.get(i);
						sample = result.getSpeed();
						gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLUE));
						gc.drawLine(x+(int)inc, y-(int)sample/5+MARGIN_TOP3, x, y-(int)(resultsList.get(i-1).getSpeed()/5)+MARGIN_TOP3);
						gc.drawLine(x+(int)inc, (y-(int)sample/5)-1+MARGIN_TOP3, x, (y-(int)(resultsList.get(i-1).getSpeed()/5))-1+MARGIN_TOP3);	//pogrubiona linia
						x+=inc;
					}
				}
			}
		});

		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Prędkość chwilowa.
	 *
	 * @param  n Ilość pomiarów brana pod uwagę.
	 * @return   Średnia prędkość z czasu ostatnich <code>n</code> pomiarów,
	 *           lub <code>-1.0</code> jeśli jest zbyt mało danych.
	 */
	private double currentSpeed(int n) {
		if (n > resultsList.size()) {
			return -1.;
		}
		TestResults resultsOld = resultsList.get(resultsList.size() - n); 
		TestResults resultsNew = resultsList.get(resultsList.size() - 1); 
		int charsCount = resultsNew.getWrittenCharsCount()
				- resultsOld.getWrittenCharsCount();
		if (charsCount < 1) {
			return 0.;
		}
		long timeStarted = resultsOld.getTypingTimeMilliseconds();
		long timeFinished = resultsNew.getTypingTimeMilliseconds();
		long interval = timeFinished - timeStarted;
		double timeMinutes = ((double)interval) / 1000 / 60;
		return charsCount / timeMinutes;
	}

	/**
	 * Wyświetl aktualną wersję tego elementu.
	 */
	public void refresh() {
		canvas.redraw();
	}

	/**
	 * Dodaj wyniki do listy wyników. Należy używać tej metody w celu
	 * ustalenia wyników końcowych, tuż przed zakończeniem testu.
	 */
	public void addFinalResults(TestResults results) {
		resultsList.add(results);
	}

	/**
	 * Usuń stare wyniki.
	 */
	public void reset() {
		resultsList.clear();
	}

	@Override
	public void run() {
		try {
			while (!GUI.shell.isDisposed()) {
				if (CoolKey.getCurrentTest().isStarted()
						&& !CoolKey.getCurrentTest().isPaused()
						&& !CoolKey.getCurrentTest().isFinished()) {
					resultsList.add(CoolKey.getCurrentTest().getResults());
					GUI.display.asyncExec(new Runnable() {
						public void run() {
							refresh();
						}
					});
				}
				Thread.sleep(SLEEP_TIME);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
