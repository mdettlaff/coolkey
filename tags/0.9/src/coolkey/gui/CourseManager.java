package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import coolkey.CoolKey;
import coolkey.Course;
import coolkey.TypingTest;

/**
 * Okno wyboru kursów.
 */
public class CourseManager {
	private static Shell CMShell;
	private Table courses;
	
	public CourseManager() {
		CMShell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE );
		CMShell.setText("Zarządzanie kursami");
		CMShell.setLayout(new GridLayout(2, false));
		
		courses = new Table(CMShell, SWT.SINGLE | SWT.H_SCROLL | SWT.BORDER);
		courses.setHeaderVisible(true);
		courses.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		new TableColumn(courses, SWT.NONE).setText("Twoje kursy");
		refresh();
		
		Composite buttonComp = new Composite(CMShell, SWT.NONE);
		buttonComp.setLayout(new GridLayout());
		
		Button select = new Button(buttonComp, SWT.PUSH);
		select.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		select.setText("Wybierz");
		Button delete = new Button(buttonComp, SWT.PUSH);
		delete.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		delete.setText("Usuń");

		CMShell.pack();

		select.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (courses.getSelectionIndex() < 0) {
					return;
				}
				CoolKey.getUser().selectCourse(courses.getSelectionIndex());
				CoolKey.setCurrentTest(new TypingTest(CoolKey.getUser()
						.getCurrentCourse().getCurrentLesson().getText(),
						true));
				CMShell.close();
				GUI.refresh();
			}
		});
		delete.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (courses.getSelectionIndex() < 0) {
					return;
				}
				CoolKey.getUser().deleteCourse(courses.getSelectionIndex());
				GUI.menuBar.refresh();
				refresh();
			}
		});
	}
	
	public void open() {
		CMShell.open();
	}

	public void refresh() {
		courses.removeAll();
		for (Course course : CoolKey.getUser().getCourses()) {
			new TableItem(courses, SWT.NONE).setText(course.getName()
					+ " (lekcja " + course.getProgress()[0] + "/"
					+ course.getProgress()[1] + ")");
		}
		courses.select(CoolKey.getUser().getCourses().indexOf(
				CoolKey.getUser().getCurrentCourse()));
		courses.getColumn(0).pack();
	}
}
