package coolkey.defender;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Shell;

public class Defender {
	private Shell shell;
	private Canvas container;
	private Engine engine;
	
	public Defender(Shell shell) {
		this.shell = shell;
		this.container = new Canvas(this.shell, SWT.NO_SCROLL | SWT.NO_BACKGROUND);
		this.container.setSize(Engine.WIDTH, Engine.HEIGHT);
		this.engine = new Engine(this.shell.getDisplay(), this.container);
		this.container.addPaintListener(new Paint(this.shell.getDisplay(), this.engine));
		this.container.addKeyListener(new Key(this.engine));
	}

	public Point getSize() {
		return this.container.getSize();
	}
	
	public Point getLocation() {
		return this.container.getLocation();
	}
	
	public void setLocation(Point point) {
		this.container.setLocation(point);
	}
	
	public void showFps(boolean showFps) {
		this.engine.showFps(showFps);
	}
	
	public void start() {
		this.engine.start();
	}
	
	public void stop() {
		this.engine.stop();
	}
}
