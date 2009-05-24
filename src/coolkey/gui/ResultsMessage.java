package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import coolkey.CoolKey;
import coolkey.TestResults;
import coolkey.TypingTest;

/**
 * Okienko z wynikami testu przepisywania.
 */
public class ResultsMessage {
	private static final int SPEED_THRESHOLD = 100;
	private static final int CORRECTNESS_THRESHOLD = 98;

	public ResultsMessage(TestResults results) {
	    String message = "Wyniki testu:\n\n";
	    message += results.toString();

	    if (!results.isPartOfCourse()
	    		|| CoolKey.getUser().getCurrentCourse() == null) {
	    	int style = SWT.ICON_INFORMATION;
	    	MessageBox messageBox = new MessageBox(GUI.shell, style);
	    	messageBox.setText("Wyniki");
	    	messageBox.setMessage(message);
	    	messageBox.open();
	    } else {
			MessageBox confirmation = new MessageBox(GUI.shell,
					SWT.ICON_QUESTION | SWT.YES | SWT.NO);
			confirmation.setText("Wyniki");
			// zaliczone
			if (results.getRealSpeed() >= SPEED_THRESHOLD
					&& results.getCorrectness() >= CORRECTNESS_THRESHOLD) {
				int[] progress = CoolKey.getUser().getCurrentCourse().getProgress();
				// kurs zaliczony
				if (progress[0] == progress[1]) {
					message += "\n\nGratulacje! Kurs został ukończony.";
					int style = SWT.ICON_INFORMATION;
			    	MessageBox messageBox = new MessageBox(GUI.shell, style);
			    	messageBox.setText("Wyniki");
			    	messageBox.setMessage(message);
			    	messageBox.open();
			    	return;
				}
				// lekcja zaliczona
				message += "\n\nGratulacje! Lekcja została zaliczona.\n\n";
				message += "Czy chcesz przejść do następnej lekcji?";
				confirmation.setMessage(message);
				int response = confirmation.open();
				if (response == SWT.YES) {
					CoolKey.getUser().getCurrentCourse().goToNextLesson();
					CoolKey.setCurrentTest(new TypingTest(CoolKey.getUser()
							.getCurrentCourse().getCurrentLesson().getText(),
							true));
					GUI.refresh();
					GUI.buttonBar.showLessonInstructions();
				} else {
					CoolKey.getCurrentTest().restart();
					GUI.refresh();
				}
			// niezaliczone
			} else {
				if (results.getRealSpeed() < SPEED_THRESHOLD &&
						results.getCorrectness() >= CORRECTNESS_THRESHOLD) {
					message += "\n\nTwoja poprawność jest bardzo dobra, ale nie\n";
					message += "uzyskałeś wymaganej prędkości "
						+ SPEED_THRESHOLD + " znaków/min.";
				} else if (results.getRealSpeed() >= SPEED_THRESHOLD &&
						results.getCorrectness() < CORRECTNESS_THRESHOLD) {
					message += "\n\nTwoja prędkość jest bardzo dobra, ale nie\n";
					message += "uzyskałeś wymaganej poprawności "
						+ CORRECTNESS_THRESHOLD + "%.";
				} else {
					message += "\n\nNie uzyskałeś wymaganej prędkości "
						+ SPEED_THRESHOLD + " znaków/min.\n";
					message += "Nie uzyskałeś wymaganej poprawności "
						+ CORRECTNESS_THRESHOLD + "%.";
				}
				message += "\n\nCzy chcesz powtórzyć lekcję?";
				confirmation.setMessage(message);
				int response = confirmation.open();
				if (response == SWT.YES) {
					CoolKey.getCurrentTest().restart();
					GUI.refresh();
				}
			}
	    }
	}
}
