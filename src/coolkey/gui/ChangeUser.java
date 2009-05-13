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

	/**
	 * Otwórz okno wyboru i edycji użytkownika.
	 *
	 * @param onStartupFlag Czy okno zostało otwarte tuż po uruchomieniu
	 *                      programu. Jeśli tak, wymuś wybór.
	 */
	public ChangeUser(boolean onStartupFlag) {
		final boolean onStartup = onStartupFlag;
		changeUserShell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		changeUserShell.setText("Użytkownicy");
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

		if (onStartup) {
			changeUserShell.addListener(SWT.Close, new Listener() {
				@Override
				public void handleEvent(Event e) {
					GUI.shell.dispose();
				}
			});
		}

		// wybierz użytkownika
		Listener userSelection = new Listener() {
			@Override
			public void handleEvent(Event e) {
				// logowanie
				final User selectedUser = CoolKey.getUsers().get(
						users.getSelectionIndex());
				final boolean[] isPasswordValid = new boolean[1];
				if ((!onStartup && CoolKey.getUser().equals(selectedUser)) ||
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

					final Text passwordText = new Text(dialog, SWT.BORDER);
					passwordText.setEchoChar('*');
					data = new FormData();
					data.width = 200;
					data.left = new FormAttachment(label, 0, SWT.DEFAULT);
					data.right = new FormAttachment(100, 0);
					data.top = new FormAttachment(label, 0, SWT.CENTER);
					data.bottom = new FormAttachment(cancel, 0, SWT.DEFAULT);
					passwordText.setLayoutData(data);

					Button ok = new Button(dialog, SWT.PUSH);
					ok.setText("OK");
					data = new FormData();
					data.width = 60;
					data.right = new FormAttachment(cancel, 0, SWT.DEFAULT);
					data.bottom = new FormAttachment(100, 0);
					ok.setLayoutData(data);

					Listener confirmPassword = new Listener() {
						@Override
						public void handleEvent(Event e) {
							if (selectedUser.validatePassword(
									passwordText.getText())) {
								isPasswordValid[0] = true;
								dialog.close();
							} else {
								MessageBox messageBox = new MessageBox(
										GUI.shell, SWT.ICON_WARNING);
								messageBox.setText("Hasło");
								messageBox.setMessage("Podane hasło jest nieprawidłowe.");
								messageBox.open();
							}
						}
					};
					ok.addListener(SWT.Selection, confirmPassword);
					passwordText.addListener(SWT.DefaultSelection, confirmPassword);

					passwordText.setFocus();
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
						if (CoolKey.getCurrentTest().isStarted()) {
							CoolKey.getCurrentTest().restart();
						}
						GUI.graphs.reset();
					}
					CoolKey.selectUser(users.getSelectionIndex());
					changeUserShell.dispose();
					GUI.refresh();
				}
			}
		};

		select.addListener(SWT.Selection, userSelection);
		users.addListener(SWT.DefaultSelection, userSelection);

		// usuń użytkownika
		delete.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				User selectedUser = CoolKey.getUsers().get(
						users.getSelectionIndex());
				if (!selectedUser.getName().equals(CoolKey.DEFAULT_USERNAME)) {
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
		changePass.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				final User selectedUser = CoolKey.getUsers().get(
						users.getSelectionIndex());
				if (selectedUser.validatePassword(null)) {
					MessageBox messageBox = new MessageBox(GUI.shell,
							SWT.ICON_INFORMATION);
					messageBox.setText("Hasło");
					messageBox.setMessage("Ten profil nie jest zabezpieczony hasłem.");
					messageBox.open();
				} else {
					final Shell changePassword = new Shell(GUI.shell,
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
					changePassword.setText("Zmiana hasła");
					changePassword.setLayout(new GridLayout());

					Composite comp = new Composite(changePassword, SWT.NONE);
					comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
					comp.setLayout(new GridLayout(3, false));

					new Label(comp, SWT.NONE).setText("Aktualne hasło: ");
					final Text oldPassword = new Text(comp, SWT.BORDER);
					oldPassword.setEchoChar('*');
					oldPassword.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
					oldPassword.setFocus();
					new Label(comp, SWT.NONE).setText("Nowe hasło: ");
					final Text newPassword = new Text(comp, SWT.BORDER);
					newPassword.setEchoChar('*');
					newPassword.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

					new Label(comp, SWT.NONE).setText(" ");
					Button change = new Button(comp, SWT.PUSH);
					change.setText("Zmień hasło");
					change.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false));
					Button cancel = new Button(comp, SWT.PUSH);
					cancel.setText("Anuluj");
					cancel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false));

					Listener changePasswordListener = new Listener() {
						@Override
						public void handleEvent(Event e) {
							if (selectedUser.changePassword(
									oldPassword.getText(),
									newPassword.getText())) {
								MessageBox messageBox = new MessageBox(GUI.shell,
										SWT.ICON_INFORMATION);
								messageBox.setText("Hasło");
								messageBox.setMessage("Hasło zostało zmienione.");
								messageBox.open();
								changePassword.close();
							} else {
								MessageBox messageBox = new MessageBox(GUI.shell,
										SWT.ICON_WARNING);
								messageBox.setText("Hasło");
								messageBox.setMessage("Podane aktualne hasło jest nieprawidłowe.");
								messageBox.open();
							}
						}
					};

					change.addListener(SWT.Selection, changePasswordListener);
					newPassword.addListener(SWT.DefaultSelection,
							changePasswordListener);

					cancel.addListener(SWT.Selection, new Listener() {
						@Override
						public void handleEvent(Event e) {
							changePassword.close();
						}
					});

					changePassword.pack();
					changePassword.open();
				}
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
