package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
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

	public Stats() {
		statsShell = new Shell(GUI.shell, SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL | SWT.RESIZE);
		statsShell.setText("Statystyka");
		statsShell.setLayout(new FillLayout());

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

		/*Canvas speedCanv = */new Canvas(speedComp, SWT.BORDER);
		//TODO canvas statystyk prędkości
		/*Canvas accCanv = */new Canvas(accuracyComp, SWT.BORDER);
		//TODO canvas statystyk poprawności

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
