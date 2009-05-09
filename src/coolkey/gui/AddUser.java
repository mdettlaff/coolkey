package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import coolkey.CoolKey;
import coolkey.User;

/**
 * Panel dodawania nowego użytkownika.
 * 
 * @author kd
 *
 */
public class AddUser {
	private static Shell addUserShell;
	
	public AddUser() {
		addUserShell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		addUserShell.setText("Nowy użytkownik");
		addUserShell.setLayout(new GridLayout());
		
		/* Imię */
		Composite comp0 = new Composite(addUserShell, SWT.NONE);
	    comp0.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    comp0.setLayout(new GridLayout(2, false));
	    new Label(comp0, SWT.NONE).setText("Imię: ");
	    final Text name = new Text(comp0, SWT.BORDER);
	    name.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	    name.setFocus();
		
	    /* Prywatność */
	    Group pass = new Group(addUserShell, SWT.SHADOW_NONE);
		pass.setLayout(new GridLayout());
		pass.setText("Prywatność");
		Composite comp1 = new Composite(pass, SWT.NONE);
		comp1.setLayout(new GridLayout(2, false));
		comp1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// przycisk wyboru (czy zabezpieczyć profil hasłem)
		Composite checkComp = new Composite(comp1, SWT.NONE);
	    checkComp.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 2, 1));
	    checkComp.setLayout(new GridLayout(2, false));
		final Button checkPassword = new Button(checkComp, SWT.CHECK);
		checkPassword.setSelection(false);
		new Label(checkComp, SWT.NONE).setText("Zabezpiecz profil hasłem");

		// hasło
		new Label(comp1, SWT.NONE).setText("Hasło:");
		final Text password = new Text(comp1, SWT.BORDER);
		password.setEchoChar('*');
		password.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		password.setEnabled(false);
		new Label(comp1, SWT.NONE).setText("Powtórz:");
		final Text passwordRepeat = new Text(comp1, SWT.BORDER);
		passwordRepeat.setEchoChar('*');
		passwordRepeat.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		passwordRepeat.setEnabled(false);
		
		/* Przyciski "Dodaj użytkownika" i "Anuluj" */
		Composite comp2 = new Composite(addUserShell, SWT.NONE);
	    comp2.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, true));
	    comp2.setLayout(new GridLayout(2, false));
		Button addUser = new Button(comp2, SWT.PUSH);
		addUser.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		addUser.setText("Dodaj użytkownika");
		Button cancel = new Button(comp2, SWT.PUSH);
		cancel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		cancel.setText("Anuluj");

	    addUserShell.pack();

	    checkPassword.addListener(SWT.Selection, new Listener(){
	    	public void handleEvent(Event event) {
				if (!checkPassword.getSelection()) {
					password.setEnabled(false);
					passwordRepeat.setEnabled(false);
				} else {
					password.setEnabled(true);
					passwordRepeat.setEnabled(true);
				}
			}
	    });

	    cancel.addListener(SWT.Selection, new Listener(){
	    	public void handleEvent(Event event) {
	    		addUserShell.close();
	    	}
	    });

	    addUser.addListener(SWT.Selection, new Listener(){
	    	public void handleEvent(Event event) {
	    		if (name.getText().length() == 0) {
	    			MessageBox messageBox = new MessageBox(GUI.shell, SWT.ICON_WARNING);
	    			messageBox.setText("Ostrzeżenie");
	    			messageBox.setMessage("Musisz podać nazwę użytkownika.");
	    			messageBox.open();
	    		} else if (checkPassword.getSelection() && password.getText().length() == 0) {
	    			MessageBox messageBox = new MessageBox(GUI.shell, SWT.ICON_WARNING);
	    			messageBox.setText("Ostrzeżenie");
	    			messageBox.setMessage("Hasło nie może być puste.");
	    			messageBox.open();
	    		} else if (!password.getText().equals(passwordRepeat.getText())) {
	    			MessageBox messageBox = new MessageBox(GUI.shell, SWT.ICON_WARNING);
	    			messageBox.setText("Ostrzeżenie");
	    			messageBox.setMessage("Hasło musi być identyczne z powtórzonym hasłem.");
	    			messageBox.open();
	    		} else {
	    			String pass;
	    			if (checkPassword.getSelection()) {
	    				pass = password.getText();
	    			} else {
	    				pass = null;
	    			}
	    			User newUser = new User(name.getText(), pass);
	    			if (!CoolKey.getUsers().contains(newUser)) {
		    			if (CoolKey.getCurrentTest().isStarted()) {
		    				CoolKey.getCurrentTest().restart();
		    			}
	    				GUI.graphs.reset();
		    			CoolKey.addUser(newUser);
		    			addUserShell.dispose();
		    			GUI.refresh();
	    			} else {
	    				MessageBox messageBox = new MessageBox(GUI.shell, SWT.ICON_WARNING);
		    			messageBox.setText("Ostrzeżenie");
	    				messageBox.setMessage("Użytkownik o podanej nazwie już istnieje.");
	    				messageBox.open();
	    			}
	    			newUser = null;
	    		}
	    	}
	    });
	}

	public void open() {
		addUserShell.open();
	}
}
