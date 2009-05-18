package coolkey.defender;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;

/**
 * Klasa obsługująca zdarzenie przesunięcia myszki.
 */
public class MouseMove implements MouseMoveListener {
	/**
	 * Uchwyt do mechanizmu gry.
	 */
	private Engine engine;
	/**
	 * Tworzy nowy obiekt obsługujący zdarzenie przesunięcia myszki.
	 * @param engine uchwyt do mechanizmu gry
	 */
	public MouseMove(Engine engine) {
		this.engine = engine;
	}

	public void mouseMove(MouseEvent me) {
		this.engine.mouseMove(me.x, me.y);
	}
}
