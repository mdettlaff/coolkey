package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Panel ustawień użytkownika.
 * 
 * @author kd
 *
 */
public class UserSettings {
	private static Shell USShell;
	
	public UserSettings() {
		USShell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		USShell.setText("Ustawienia");
		USShell.setLayout(new GridLayout(2, false));
		
		Button soundCheck = new Button(USShell, SWT.CHECK);
		new Label(USShell, SWT.NONE).setText("Efekty dźwiękowe");
		Button showKeyPad = new Button(USShell, SWT.CHECK);
		new Label(USShell, SWT.NONE).setText("Pokaż klawiaturę");
		Button showGraph = new Button(USShell, SWT.CHECK);
		new Label(USShell, SWT.NONE).setText("Pokaż wykresy");
		Button evadeMistakes = new Button(USShell, SWT.CHECK);
		new Label(USShell, SWT.NONE).setText("Przejdź dalej po błędzie");
		
		Composite groupComp = new Composite(USShell, SWT.NONE);
		groupComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		groupComp.setLayout(new GridLayout());
		
		Group endLine = new Group(groupComp, SWT.SHADOW_IN);
		endLine.setLayout(new GridLayout());
		endLine.setText("Koniec linii");
		Button enter = new Button(endLine, SWT.RADIO);
		enter.setSelection(true);
		enter.setText("enter");
		Button spacebar = new Button(endLine, SWT.RADIO);
		spacebar.setText("spacja");
		Button eors = new Button(endLine, SWT.RADIO);
		eors.setText("enter lub spacja");
		
		Composite butComp = new Composite(USShell, SWT.NONE);
		butComp.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 2, 1));
		butComp.setLayout(new GridLayout(2, false));
		
		Composite left = new Composite(butComp, SWT.NONE);
		left.setLayout(new GridLayout());
		Composite right = new Composite(butComp, SWT.NONE);
		right.setLayout(new GridLayout());
		
		Button okB = new Button(left, SWT.PUSH);
		okB.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		okB.setText("Zastosuj");
		Button canB = new Button(right, SWT.PUSH);
		canB.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		canB.setText(" Anuluj ");
		
		USShell.pack();
	}
	
	public void open() {
		USShell.open();
	}
}
