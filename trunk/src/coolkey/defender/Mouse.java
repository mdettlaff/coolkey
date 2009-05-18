package coolkey.defender;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

/**
 * Klasa obsługująca zdarzenia wciśnięcia przycisku myszy.
 */
public class Mouse implements MouseListener {
	/**
	 * Stała zdefiniowana jako lewy przycisk myszy.
	 */
	private final int MOUSE_BUTTON1 = 1;
	/**
	 * Stała zdefiniowana jako prawy przycisk myszy.
	 */
	private final int MOUSE_BUTTON3 = 3;
	/**
	 * Uchwyt do mechanizmu gry.
	 */
	private Engine engine;
	/**
	 * Tworzy nowy obiekt obsługujący zdarzenia wciśnięcia przycisku myszy.
	 * @param engine uchwyt do mechanizmu gry
	 */
	public Mouse(Engine engine) {
		this.engine = engine;
	}

	public void mouseDoubleClick(MouseEvent me) {

	}

	public void mouseDown(MouseEvent me) {

	}
	
	public void mouseUp(MouseEvent me) {
		switch(me.button) {
			case MOUSE_BUTTON1:
				this.engine.mouseUp(me.x, me.y);
				break;
			case MOUSE_BUTTON3:
				this.engine.mouseUpEsc();
		}
	}
}
