package coolkey.defender;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

public class Mouse implements MouseListener {
	private final int MOUSE_BUTTON1 = 1;
	private Engine engine;
	
	public Mouse(Engine engine) {
		this.engine = engine;
	}

	public void mouseDoubleClick(MouseEvent me) {

	}

	public void mouseDown(MouseEvent me) {

	}
	
	public void mouseUp(MouseEvent me) {
		if(me.button == this.MOUSE_BUTTON1)
			this.engine.mouseUp(me.x, me.y);
	}
}
