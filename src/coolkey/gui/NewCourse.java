package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import coolkey.CoolKey;
import coolkey.Course;
import coolkey.CourseFactory;
import coolkey.TypingTest;

/**
 * Okno wyboru nowego kursu.
 */
public class NewCourse {
	private static Shell newCourseShell;
	
	public NewCourse() {
		newCourseShell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
		newCourseShell.setText("Nowy kurs");
		newCourseShell.setLayout(new GridLayout());
		
		final Table courses = new Table(newCourseShell, SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL);

		new TableColumn(courses, SWT.NONE).setText("Wybierz rodzaj kursu");
		for (Course course : CourseFactory.allCourses()) {
			new TableItem(courses, SWT.NONE).setText(course.getName()
					+ " (lekcji: " + course.getProgress()[1] + ")");
		}
		courses.getColumn(0).pack();
		
		Button start = new Button(newCourseShell, SWT.PUSH);
		start.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true, 1, 1));
		start.setText(" Rozpocznij ");

		newCourseShell.pack();

		start.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				CoolKey.getUser().addCourse(CourseFactory.allCourses().get(
						courses.getSelectionIndex()));
				GUI.menuBar.getContinueCourseItem().setEnabled(true);
				CoolKey.setCurrentTest(new TypingTest(CoolKey.getUser()
						.getCurrentCourse().getCurrentLesson().getText()));
				newCourseShell.close();
				GUI.refresh();
			}
		});
	}

	public void open() {
		newCourseShell.open();
	}
}
