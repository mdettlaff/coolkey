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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Panel dodawania nowego użytkownika.
 * 
 * @author kd
 *
 */
public class AddUser {
	private static Shell addUserShell;
	
	public AddUser() {
		addUserShell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
		addUserShell.setText("Dodaj użytkownika");
		addUserShell.setLayout(new GridLayout());
		
		/*Imię*/
		Composite comp0 = new Composite(addUserShell, SWT.NONE);
	    comp0.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    comp0.setLayout(new GridLayout(2, false));
	    new Label(comp0, SWT.NONE).setText("Imię: ");
	    final Text imie = new Text(comp0, SWT.BORDER);
	    imie.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	    imie.setFocus();
		
	    /*Hasło*/
	    Group pass = new Group(addUserShell, SWT.SHADOW_NONE);
		pass.setLayout(new GridLayout());
		pass.setText("Hasło");
		Composite comp1 = new Composite(pass, SWT.NONE);
		comp1.setLayout(new GridLayout(2, false));
		comp1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final Button checkPassword = new Button(comp1, SWT.CHECK);
		checkPassword.setSelection(true);
		new Label(comp1, SWT.NONE).setText("Zabezpiecz profil hasłem");
		new Label(comp1, SWT.NONE).setText("Hasło:");
		final Text password = new Text(comp1, SWT.BORDER);
		password.setEchoChar('*');
		password.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		new Label(comp1, SWT.NONE).setText("powtórz hasło:");
		final Text passwordRepeat = new Text(comp1, SWT.BORDER);
		passwordRepeat.setEchoChar('*');
		passwordRepeat.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		/*Przycisk  "dodaj użytkownika"*/
		Button addUser = new Button(addUserShell, SWT.PUSH);
		addUser.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		addUser.setText("Dodaj użytkowika");
		
	    
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
	}
	
	public void open() {
		addUserShell.open();
	}
}
