package coolkey.defender;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

public class Key implements KeyListener {
	private final int KEY_ENTER = 13;
	private	 Engine engine;
	
	public Key(Engine engine) {
		this.engine = engine;
	}
	
	public void keyPressed(KeyEvent ke) {
		switch(this.engine.getState()) {
			case Engine.STATE_MENU:
				switch(ke.keyCode) {
					case SWT.ARROW_UP:
						this.engine.prevMenuItem();
						break;
					case SWT.ARROW_DOWN:
						this.engine.nextMenuItem();
						break;
					case KEY_ENTER:
						this.engine.enterMenuItem();
						break;
				}
				break;
			case Engine.STATE_GAME:
			case Engine.STATE_RESULT:
			case Engine.STATE_TOP10:
			case Engine.STATE_HELP:
				if(ke.keyCode == SWT.ESC)
						this.engine.escapeToMenu();
				break;
		}
	}

	public void keyReleased(KeyEvent ke) {

	}
}
