package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import coolkey.CoolKey;
import coolkey.User;

/**
 * Panel wyboru i edycji użytkownika.
 * 
 * @author kd
 *
 */
public class ChangeUser {
	private static Shell changeUserShell;
	private final Table users;

	public ChangeUser() {
		changeUserShell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		changeUserShell.setText("Zmień użytkownika");
		changeUserShell.setLayout(new GridLayout(2, false));
		
		users = new Table(changeUserShell, SWT.SINGLE |
				SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.BORDER);
		users.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		new TableColumn(users, SWT.NONE);
		refresh();
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

		// wybierz użytkownika
		select.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				// logowanie
				final User selectedUser = CoolKey.getUsers().get(
						users.getSelectionIndex());
				final boolean[] isPasswordValid = new boolean[1];
				if (CoolKey.getUser().equals(selectedUser) ||
						selectedUser.validatePassword(null)) {
					isPasswordValid[0] = true;
				} else {
					final Shell dialog = new Shell (GUI.shell, SWT.DIALOG_TRIM
							| SWT.APPLICATION_MODAL);
					dialog.setText("Profil zabezpieczony hasłem");
					FormLayout formLayout = new FormLayout();
					formLayout.marginWidth = 10;
					formLayout.marginHeight = 10;
					formLayout.spacing = 10;
					dialog.setLayout(formLayout);

					Label label = new Label(dialog, SWT.NONE);
					label.setText("Podaj hasło:");
					FormData data = new FormData();
					label.setLayoutData(data);

					Button cancel = new Button(dialog, SWT.PUSH);
					cancel.setText("Anuluj");
					data = new FormData();
					data.width = 60;
					data.right = new FormAttachment(100, 0);
					data.bottom = new FormAttachment(100, 0);
					cancel.setLayoutData(data);
					cancel.addListener(SWT.Selection, new Listener() {
						@Override
						public void handleEvent(Event e) {
							dialog.close();
						}
					});

					final Text text = new Text(dialog, SWT.BORDER);
					text.setEchoChar('*');
					data = new FormData();
					data.width = 200;
					data.left = new FormAttachment(label, 0, SWT.DEFAULT);
					data.right = new FormAttachment(100, 0);
					data.top = new FormAttachment(label, 0, SWT.CENTER);
					data.bottom = new FormAttachment(cancel, 0, SWT.DEFAULT);
					text.setLayoutData(data);

					Button ok = new Button(dialog, SWT.PUSH);
					ok.setText("OK");
					data = new FormData();
					data.width = 60;
					data.right = new FormAttachment(cancel, 0, SWT.DEFAULT);
					data.bottom = new FormAttachment(100, 0);
					ok.setLayoutData(data);
					ok.addListener(SWT.Selection, new Listener() {
						@Override
						public void handleEvent(Event e) {
							if (selectedUser.validatePassword(text.getText())) {
								isPasswordValid[0] = true;
								dialog.close();
							} else {
								MessageBox messageBox = new MessageBox(GUI.shell, SWT.ICON_WARNING);
								messageBox.setText("Ostrzeżenie");
								messageBox.setMessage("Podane hasło jest nieprawidłowe.");
								messageBox.open();
							}
						}
					});

					text.setFocus();
					dialog.pack();
					dialog.open();

					while (!dialog.isDisposed()) {
						if (!GUI.display.readAndDispatch())
							GUI.display.sleep();
					}
				}
				// zmiana użytkownika
				if (isPasswordValid[0]) {
					if (!CoolKey.getUser().equals(CoolKey.getUsers().get(
							users.getSelectionIndex()))) {
						if (CoolKey.getCurrentLesson().isStarted()) {
							CoolKey.getCurrentLesson().restart();
						}
						GUI.graphs.reset();
					}
					CoolKey.selectUser(users.getSelectionIndex());
					changeUserShell.dispose();
					GUI.refresh();
				}
			}
		});

		// usuń użytkownika
		delete.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				User selectedUser = CoolKey.getUsers().get(
						users.getSelectionIndex());
				if (selectedUser.getName() != CoolKey.DEFAULT_USERNAME) {
					MessageBox confirmation = new MessageBox(GUI.shell,
							SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					confirmation.setText("Potwierdzenie");
					confirmation.setMessage(
							"Czy na pewno chcesz usunąć użytkownika " +
							selectedUser + "?");
					int response = confirmation.open();
					if (response == SWT.YES) {
						CoolKey.deleteUser(selectedUser);
						refresh();
						GUI.refresh();
					}
				} else {
					MessageBox confirmation = new MessageBox(GUI.shell,
							SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					confirmation.setText("Potwierdzenie");
					confirmation.setMessage(
							"Nie można usunąć domyślnego użytkownika.\n\n" +
							"Czy chcesz zresetować domyślnego użytkownika?");
					int response = confirmation.open();
					if (response == SWT.YES) {
						CoolKey.getUsers().set(CoolKey.getUsers().indexOf(
								selectedUser), new User());
						GUI.refresh();
					}
				}
			}
		});

		// zmień hasło
		delete.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				// TODO zmiana hasła
			}
		});
	}

	public void open() {
		changeUserShell.open();
	}

	public void refresh() {
		users.removeAll();
		for (User user : CoolKey.getUsers()) {
			new TableItem(users, SWT.NONE).setText(user.getName());
		}
		users.select(CoolKey.getUsers().indexOf(CoolKey.getUser()));
	}
}
