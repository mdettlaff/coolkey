package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * Panel rozpoczęcia nowego kursu.
 * 
 * @author kd
 *
 */
public class NewCourse {
	private static Shell newCourseShell;
	
	public NewCourse() {
		newCourseShell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
		newCourseShell.setText("Nowy kurs");
		newCourseShell.setLayout(new GridLayout(2, false));
		
		Table courses = new Table(newCourseShell, SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL);
		courses.setHeaderVisible(true);
	
		new TableColumn(courses, SWT.NONE).setText("Wybierz rodzaj kursu");
		new TableItem(courses, SWT.NONE).setText("Podstawowy (16 lekcji)");
		new TableItem(courses, SWT.NONE).setText("Pełny (25 lekcji)");
		new TableItem(courses, SWT.NONE).setText("Klawiatura numeryczna");
		new TableItem(courses, SWT.NONE).setText("Cyfry");

		courses.getColumn(0).pack();
		Group keyType = new Group(newCourseShell, SWT.SHADOW_IN);
		keyType.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		keyType.setLayout(new GridLayout());
		keyType.setText("Układ klawiatury");
		Button qwerty = new Button(keyType, SWT.RADIO);
		qwerty.setSelection(true);
		qwerty.setText("QWERTY");
		Button dvorak = new Button(keyType, SWT.RADIO);
		dvorak.setText("Dvorak");
		
		Button start = new Button(newCourseShell, SWT.PUSH);
		start.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true, 2, 1));
		start.setText("  Rozpocznij  ");
		
		newCourseShell.pack();
	}
	
	public void open() {
		newCourseShell.open();
	}
}
