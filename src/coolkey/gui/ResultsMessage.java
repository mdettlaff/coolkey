package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import coolkey.LessonResults;

/**
 * Okienko z wynikami lekcji.
 */
public class ResultsMessage {
	public ResultsMessage(LessonResults results) {
		int style = SWT.ICON_INFORMATION;
	    String message = "Wyniki testu:\n\n";
	    message += results.toString();

		MessageBox messageBox = new MessageBox(GUI.shell, style);
		messageBox.setText("Wyniki");
		messageBox.setMessage(message);
		messageBox.open();
	}
}
