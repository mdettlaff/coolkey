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

public class CourseManager {
	private static Shell CMShell;
	
	public CourseManager() {
		CMShell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE );
		CMShell.setText("Zarządzanie kursami");
		CMShell.setLayout(new GridLayout(2, false));
		
		Table users = new Table(CMShell, SWT.SINGLE | SWT.H_SCROLL | SWT.BORDER);
		users.setHeaderVisible(true);
		users.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		new TableColumn(users, SWT.NONE).setText("Twoje kursy");
		new TableItem(users, SWT.NONE).setText("Kurs 1");
		new TableItem(users, SWT.NONE).setText("Kurs 2");
		new TableItem(users, SWT.NONE).setText("Kurs 3");
		new TableItem(users, SWT.NONE).setText("Kurs ...");
		new TableItem(users, SWT.NONE).setText("...");
		users.getColumn(0).pack();
		
		Composite buttonComp = new Composite(CMShell, SWT.NONE);
		buttonComp.setLayout(new GridLayout());
		
		Button select = new Button(buttonComp, SWT.PUSH);
		select.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		select.setText("Wybierz");
		Button delete = new Button(buttonComp, SWT.PUSH);
		delete.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		delete.setText("Usuń");
		
		CMShell.pack();
	}
	
	public void open() {
		CMShell.open();
	}
}
