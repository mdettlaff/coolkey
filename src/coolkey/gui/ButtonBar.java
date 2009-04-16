package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

import coolkey.CoolKey;

/**
 * Belka z przyciskami na środku.
 */
public class ButtonBar {

	public ButtonBar() {
		Composite comp = new Composite(GUI.shell, SWT.NONE);
		comp.setLayout(new GridLayout(4, false));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		// przycisk pauzy
		Button pause = new Button(comp, SWT.PUSH);
		pause.setText("Pauza");
		// przycisk restartujący lekcję
		Button restartLesson = new Button(comp, SWT.PUSH);
		restartLesson.setText("Zacznij od nowa");
		restartLesson.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				MessageBox confirmation = new MessageBox(GUI.shell,
						SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				confirmation.setText("Potwierdzenie");
				confirmation.setMessage(
						"Czy na pewno chcesz rozpocząć to ćwiczenie od nowa?");
				int response = confirmation.open();
				if (response == SWT.YES) {
					CoolKey.getCurrentLesson().restart();
					GUI.writingArea.refresh();
				}
			}
		});
	}
}
