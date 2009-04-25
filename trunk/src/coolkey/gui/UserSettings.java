package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import coolkey.Config;
import coolkey.CoolKey;

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

		final Button soundCheck = new Button(USShell, SWT.CHECK);
		soundCheck.setSelection(CoolKey.getUser().getConfig().isSoundOn());
		new Label(USShell, SWT.NONE).setText("Efekty dźwiękowe");
		final Button showKeyPad = new Button(USShell, SWT.CHECK);
		showKeyPad.setSelection(CoolKey.getUser().getConfig().isShowKeyboard());
		new Label(USShell, SWT.NONE).setText("Pokaż klawiaturę");
		final Button showGraph = new Button(USShell, SWT.CHECK);
		showGraph.setSelection(CoolKey.getUser().getConfig().isShowGraphs());
		new Label(USShell, SWT.NONE).setText("Pokaż wykresy");
		final Button evadeMistakes = new Button(USShell, SWT.CHECK);
		evadeMistakes.setSelection(CoolKey.getUser().getConfig().isContinueAtMistakes());
		new Label(USShell, SWT.NONE).setText("Przejdź dalej po błędzie");
		
		Composite groupComp = new Composite(USShell, SWT.NONE);
		groupComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		groupComp.setLayout(new GridLayout());
		
		Group endLine = new Group(groupComp, SWT.SHADOW_IN);
		endLine.setLayout(new GridLayout());
		endLine.setText("Koniec linii");
		final Button enter = new Button(endLine, SWT.RADIO);
		enter.setText("enter");
		final Button spacebar = new Button(endLine, SWT.RADIO);
		spacebar.setText("spacja");
		final Button eors = new Button(endLine, SWT.RADIO);
		eors.setText("enter lub spacja");
		String lineBreakers = CoolKey.getUser().getConfig().getLineBreakers();
		if (lineBreakers.equals("\n")) {
			enter.setSelection(true);
		} else if (lineBreakers.equals(" ")) {
			spacebar.setSelection(true);
		} else {
			eors.setSelection(true);
		}
		
		Composite butComp = new Composite(USShell, SWT.NONE);
		butComp.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 2, 1));
		butComp.setLayout(new GridLayout(2, false));
		
		Composite left = new Composite(butComp, SWT.NONE);
		left.setLayout(new GridLayout());
		Composite right = new Composite(butComp, SWT.NONE);
		right.setLayout(new GridLayout());
		
		Button okB = new Button(left, SWT.PUSH);
		okB.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		okB.setText(" OK ");
		Button canB = new Button(right, SWT.PUSH);
		canB.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		canB.setText(" Anuluj ");
		
		USShell.pack();

		okB.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				Config config = CoolKey.getUser().getConfig();
				config.setSoundOn(soundCheck.getSelection());
				config.setShowKeyboard(showKeyPad.getSelection());
				config.setShowGraphs(showGraph.getSelection());
				config.setContinueAtMistakes(evadeMistakes.getSelection());
				if (enter.getSelection()) {
					config.setLineBreakers("\n");
				} else if (spacebar.getSelection()) {
					config.setLineBreakers(" ");
				} else if (eors.getSelection()) {
					config.setLineBreakers("\n ");
				}
				USShell.dispose();
			}
		});

		canB.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				USShell.close();
			}
		});
	}
	
	public void open() {
		USShell.open();
	}
}
