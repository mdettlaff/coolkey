package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import coolkey.TestResults;

/**
 * Okienko z wynikami testu przepisywania.
 */
public class ResultsMessage {
	public ResultsMessage(TestResults results) {
		int style = SWT.ICON_INFORMATION;
	    String message = "Wyniki testu:\n\n";
	    message += results.toString();

		MessageBox messageBox = new MessageBox(GUI.shell, style);
		messageBox.setText("Wyniki");
		messageBox.setMessage(message);
		messageBox.open();
	}
}
