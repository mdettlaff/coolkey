package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * Panel wyboru i edycji użytkownika.
 * 
 * @author kd
 *
 */
public class ChangeUser {
	private static Shell changeUserShell;
	
	public ChangeUser() {
		changeUserShell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE );
		changeUserShell.setText("Zmień użytkownika");
		changeUserShell.setLayout(new GridLayout(2, false));
		
		Table users = new Table(changeUserShell, SWT.SINGLE | SWT.H_SCROLL | SWT.BORDER);
		users.setHeaderVisible(true);
		users.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		new TableColumn(users, SWT.NONE).setText("Użytkownicy");
		new TableItem(users, SWT.NONE).setText("Anusiak");
		new TableItem(users, SWT.NONE).setText("Czesio");
		new TableItem(users, SWT.NONE).setText("Maślana");
		new TableItem(users, SWT.NONE).setText("Pani Frau");
		new TableItem(users, SWT.NONE).setText("Konieczko");
		users.getColumn(0).pack();
		
		Composite buttonComp = new Composite(changeUserShell, SWT.NONE);
		buttonComp.setLayout(new GridLayout());
		
		Button select = new Button(buttonComp, SWT.PUSH);
		select.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		select.setText("Wybierz");
		Button delete = new Button(buttonComp, SWT.PUSH);
		delete.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		delete.setText("Usuń");
		Button changePass = new Button(buttonComp, SWT.PUSH);
		changePass.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		changePass.setText("Zmień hasło");
		
		changeUserShell.pack();
	}
	
	public void open() {
		changeUserShell.open();
	}
}
