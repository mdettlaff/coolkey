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
import coolkey.Course;
import coolkey.Lesson;

/**
 * Belka z przyciskami na środku.
 */
public class ButtonBar {

	private Button pause;
	private Button restartTest;
	private Button instructions;

	public ButtonBar() {
		Composite comp = new Composite(GUI.shell, SWT.NONE);
		comp.setLayout(new GridLayout(4, false));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		// przycisk pauzy
		pause = new Button(comp, SWT.PUSH);
		pause.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				CoolKey.getCurrentTest().pauseUnpause();
				refresh();
				GUI.typingArea.setFocus();
			}
		});
		// przycisk restartujący test przepisywania
		restartTest = new Button(comp, SWT.PUSH);
		restartTest.setText("Zacznij od nowa");
		restartTest.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean wasPaused = true;
				if (!CoolKey.getCurrentTest().isPaused()) {
					wasPaused = false;
					CoolKey.getCurrentTest().pauseUnpause(); // zapauzuj
				}
				MessageBox confirmation = new MessageBox(GUI.shell,
						SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				confirmation.setText("Potwierdzenie");
				confirmation.setMessage(
						"Czy na pewno chcesz rozpocząć to ćwiczenie od nowa?");
				int response = confirmation.open();
				if (response == SWT.YES) {
					CoolKey.getCurrentTest().restart();
					GUI.keyboard.refresh();
					GUI.typingArea.refresh();
				} else if (!wasPaused) {
					CoolKey.getCurrentTest().pauseUnpause(); // odpauzuj
				}
				GUI.typingArea.setFocus();
				refresh();
			}
		});
		// przycisk pokazujący instrukcję do lekcji
		instructions = new Button(comp, SWT.PUSH);
		instructions.setText("Instrukcja do lekcji");
		instructions.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				showLessonInstructions();
				GUI.typingArea.setFocus();
			}
		});
		refresh();
	}

	public void showLessonInstructions() {
		Lesson lesson =
			CoolKey.getUser().getCurrentCourse().getCurrentLesson();
		Course course = CoolKey.getUser().getCurrentCourse();
		String message =
			"Lekcja " + course.getProgress()[0] + "/"
			+ course.getProgress()[1] + ": ";
		message += lesson.getName() + "\n\n";
		message += lesson.getInstructions();
    	int style = SWT.ICON_INFORMATION;
    	MessageBox messageBox = new MessageBox(GUI.shell, style);
    	messageBox.setText(course.getName());
    	messageBox.setMessage(message);
    	messageBox.open();
	}

	public void refresh() {
		if (CoolKey.getCurrentTest().isStarted()
				&& !CoolKey.getCurrentTest().isFinished()) {
			pause.setEnabled(true);
			if (!CoolKey.getCurrentTest().isPaused()) {
				pause.setText(" Pauza ");
			} else {
				pause.setText("Wznów");
			}
		} else {
			pause.setText(" Pauza ");
			pause.setEnabled(false);
		}
		restartTest.setEnabled(CoolKey.getCurrentTest().isStarted());
		instructions.setEnabled(CoolKey.getCurrentTest().isPartOfCourse());
	}
}
