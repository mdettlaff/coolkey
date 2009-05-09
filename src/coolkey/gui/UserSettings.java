package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import coolkey.Config;
import coolkey.CoolKey;

/**
 * Okno ustawień użytkownika.
 * 
 * @author kd
 *
 */
public class UserSettings {
	private static Shell USShell;

	public UserSettings() {
		USShell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		USShell.setText("Ustawienia");
		USShell.setLayout(new GridLayout(1, false));

		final Button soundOn = new Button(USShell, SWT.CHECK);
		soundOn.setSelection(CoolKey.getUser().getConfig().isSoundOn());
		soundOn.setText("Efekty dźwiękowe");

		final Button showKeyboard = new Button(USShell, SWT.CHECK);
		showKeyboard.setSelection(CoolKey.getUser().getConfig().isShowKeyboard());
		showKeyboard.setText("Pokaż klawiaturę");

		// układ klawiatury
		Composite keybLayComp = new Composite(USShell, SWT.NONE);
		keybLayComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		keybLayComp.setLayout(new GridLayout());
		Group keybLayout = new Group(keybLayComp, SWT.SHADOW_IN);
		keybLayout.setLayout(new GridLayout());
		keybLayout.setText("Układ klawiszy");
		final Button qwerty = new Button(keybLayout, SWT.RADIO);
		qwerty.setText("QWERTY");
		qwerty.setSelection(CoolKey.getUser().getConfig().getKeyboardLayout()
				== Config.QWERTY);
		qwerty.setEnabled(CoolKey.getUser().getConfig().isShowKeyboard());
		final Button dvorak = new Button(keybLayout, SWT.RADIO);
		dvorak.setText("Dvorak");
		dvorak.setSelection(CoolKey.getUser().getConfig().getKeyboardLayout()
				== Config.DVORAK);
		dvorak.setEnabled(CoolKey.getUser().getConfig().isShowKeyboard());

		final Button showGraphs = new Button(USShell, SWT.CHECK);
		showGraphs.setSelection(CoolKey.getUser().getConfig().isShowGraphs());
		showGraphs.setText("Pokaż wykresy");
		final Button passMistakes = new Button(USShell, SWT.CHECK);
		passMistakes.setSelection(CoolKey.getUser().getConfig().isContinueAtMistakes());
		passMistakes.setText("Przejdź dalej po błędzie");

		// łamanie linii
		Composite groupComp = new Composite(USShell, SWT.NONE);
		groupComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		groupComp.setLayout(new GridLayout());

		Group endLine = new Group(groupComp, SWT.SHADOW_IN);
		endLine.setLayout(new GridLayout());
		endLine.setText("Łamanie linii");
		final Button enter = new Button(endLine, SWT.RADIO);
		enter.setText("Enter");
		final Button spacebar = new Button(endLine, SWT.RADIO);
		spacebar.setText("spacja");
		final Button eors = new Button(endLine, SWT.RADIO);
		eors.setText("Enter lub spacja");
		String lineBreakers = CoolKey.getUser().getConfig().getLineBreakers();
		if (lineBreakers.equals("\r")) {
			enter.setSelection(true);
		} else if (lineBreakers.equals(" ")) {
			spacebar.setSelection(true);
		} else {
			eors.setSelection(true);
		}

		// przyciski na dole
		Composite butComp = new Composite(USShell, SWT.NONE);
		butComp.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 2, 1));
		butComp.setLayout(new GridLayout(2, false));
		
		Composite left = new Composite(butComp, SWT.NONE);
		left.setLayout(new GridLayout());
		Composite right = new Composite(butComp, SWT.NONE);
		right.setLayout(new GridLayout());
		
		Button ok = new Button(left, SWT.PUSH);
		ok.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		ok.setText(" OK ");
		Button cancel = new Button(right, SWT.PUSH);
		cancel.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		cancel.setText(" Anuluj ");

		USShell.pack();

		showKeyboard.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				qwerty.setEnabled(!qwerty.isEnabled());
				dvorak.setEnabled(!dvorak.isEnabled());
			}
		});

		ok.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				Config config = CoolKey.getUser().getConfig();
				config.setSoundOn(soundOn.getSelection());
				config.setShowKeyboard(showKeyboard.getSelection());
				config.setShowGraphs(showGraphs.getSelection());
				config.setContinueAtMistakes(passMistakes.getSelection());
				if (enter.getSelection()) {
					config.setLineBreakers("\r");
				} else if (spacebar.getSelection()) {
					config.setLineBreakers(" ");
				} else if (eors.getSelection()) {
					config.setLineBreakers("\r ");
				}
				if (qwerty.getSelection()) {
					config.setKeyboardLayout(Config.QWERTY);
					GUI.keyboard.refresh();
				} else if (dvorak.getSelection()) {
					config.setKeyboardLayout(Config.DVORAK);
					GUI.keyboard.refresh();
				}
				CoolKey.persistState();
				USShell.dispose();
			}
		});

		cancel.addListener(SWT.Selection, new Listener() {
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
