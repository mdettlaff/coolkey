package coolkey.gui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;

import coolkey.CoolKey;
import coolkey.LessonResults;

/**
 * Panel z wykresami po prawej stronie. Wykresy są animowane w osobnym wątku.
 */
public class Graphs implements Runnable	{
	private final int SLEEP_TIME = 1000;
	/**
	 * Przedział czasu, z którego obliczana jest prędkość chwilowa.
	 */
	private final int CURRENT_SPEED_INTERVAL = 10;
	private Thread thread;
	private Canvas graphs;
	private List<LessonResults> resultsList = new ArrayList<LessonResults>();

	public Graphs() {
		graphs = new Canvas(GUI.shell, SWT.BORDER);
		graphs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 3));

		graphs.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				/*
				 * Info dla Karola:
				 * Twoje zadanie to zrobić tutaj rysowanie wykresów.
				 * Wykresy powinny mieć szerokość około 200 pikseli.
				 * Dane z kolejnych sekund masz w zmiennej resultsList.
				 * Powodzenia! :)
				 */
				e.gc.drawString("czas: ", 7, 24);
				e.gc.drawString("postęp: ", 7, 60);
				if (resultsList.size() > 0) {
					LessonResults newestResults = resultsList.get(
							resultsList.size() - 1);
					String progress = String.format("%.0f%%",
							newestResults.getProgress());
					int seconds = newestResults.getWritingTimeMilliseconds() / 1000;
					e.gc.drawString(seconds + "", 7, 40);
					e.gc.drawString(progress, 7, 76);
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
		LessonResults resultsOld = resultsList.get(resultsList.size() - n); 
		LessonResults resultsNew = resultsList.get(resultsList.size() - 1); 
		int charsCount = resultsNew.getWrittenCharsCount()
				- resultsOld.getWrittenCharsCount();
		if (charsCount < 1) {
			return 0.;
		}
		long timeStarted = resultsOld.getWritingTimeMilliseconds();
		long timeFinished = resultsNew.getWritingTimeMilliseconds();
		long interval = timeFinished - timeStarted;
		double timeMinutes = ((double)interval) / 1000 / 60;
		return charsCount / timeMinutes;
	}

	/**
	 * Wyświetl aktualną wersję tego elementu.
	 */
	public void refresh() {
		graphs.redraw();
	}

	/**
	 * Dodaj wyniki do listy wyników. Należy używać tej metody w celu
	 * ustalenia wyników końcowych, tuż przed zakończeniem testu.
	 */
	public void addFinalResults(LessonResults results) {
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
				if (CoolKey.getCurrentLesson().isStarted()
						&& !CoolKey.getCurrentLesson().isPaused()
						&& !CoolKey.getCurrentLesson().isFinished()) {
					resultsList.add(CoolKey.getCurrentLesson().getResults());
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
