package coolkey.gui;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import coolkey.Utils;

/**
 * Informacje o programie i licencji.
 */
public class About {

	private static final String INFO =
		"Program do nauki bezwzrokowego pisania na klawiaturze\n" +
		"Copyright © 2009\n\n" +
		"Autorzy:\n" +
		"Michał Dettlaff\n" +
		"Karol Domagała\n" +
		"Łukasz Draba\n\n" +
		"Strona domowa programu:\n" +
		"<a>http://code.google.com/p/coolkey/</a>\n";
	private static final String GNU_INFO =
		"This program is free software: you can redistribute it and/or modify it under\n" +
		"the terms of the GNU General Public License as published by the Free Software\n" +
		"Foundation, either version 3 of the License, or (at your option) any later version.\n" +
		"\n" +
		"This program is distributed in the hope that it will be useful, but WITHOUT ANY\n" +
		"WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR\n" +
		"A PARTICULAR PURPOSE. See the GNU General Public License for more details.\n" +
		"\n" +
		"You should have received a copy of the GNU General Public License along with this\n" +
		"program. If not, see <a>http://www.gnu.org/licenses/</a>";
	private static final String LICENSE_FILE = "COPYING";

	public About() {
		final Shell shell = new Shell(GUI.shell, SWT.APPLICATION_MODAL
				| SWT.DIALOG_TRIM);
		shell.setText("Informacje o programie");
		shell.setLayout(new GridLayout());

		CTabFolder about = new CTabFolder(shell, SWT.BORDER | SWT.MULTI);
		CTabItem infoTab = new CTabItem(about, SWT.NONE);
		infoTab.setText("O programie");
		CTabItem licenseTab = new CTabItem(about, SWT.NONE);
		licenseTab.setText("Licencja");

		Composite info = new Composite(about, SWT.NONE);
		info.setLayout(new GridLayout());
		Composite license = new Composite(about, SWT.NONE);
		license.setLayout(new GridLayout());
		infoTab.setControl(info);
		licenseTab.setControl(license);

		// tekst z odnośnikami
		Label programName = new Label(info, SWT.NONE);
		programName.setText("CoolKey 1.0");
		programName.setFont(new Font(GUI.display, "Arial", 12, SWT.BOLD));
		Link homepage = new Link(info, SWT.NONE);
		homepage.setText(INFO);
		Link gnu = new Link(info, SWT.NONE);
		gnu.setText(GNU_INFO);

	    // tekst licencji
	    Text licenseText = new Text(license, SWT.MULTI | SWT.BORDER
	    		| SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL);
		try {
			licenseText.setText(Utils.readFileAsString(
					new File(LICENSE_FILE), "UTF-8"));
		} catch (IOException e) {
			licenseText.setText("Brak pliku z licencją.");
		}
		GridData gd = new GridData(GridData.FILL, GridData.FILL, true, true);
		gd.heightHint = 250;
		gd.widthHint = 250;
		licenseText.setLayoutData(gd);

	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText(" OK ");
	    ok.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		ok.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				shell.close();
			}
		});

		final Listener openLink = new Listener() {
			@Override
			public void handleEvent(Event e) {
				Utils.openInExternalBrowser(e.text);
			}
		};
		homepage.addListener(SWT.Selection, openLink);
		gnu.addListener(SWT.Selection, openLink);

		shell.pack();
		shell.open();
	}
}
