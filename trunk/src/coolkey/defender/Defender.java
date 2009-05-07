package coolkey.defender;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Shell;

public class Defender {
	private final Shell shell;
	private final Canvas container;
	private final Engine engine;
	private final Paint paint;
	
	public Defender(Shell shell) {
		this.shell = shell;
		this.container = new Canvas(this.shell, SWT.NO_SCROLL | SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
		this.container.setSize(Engine.WIDTH, Engine.HEIGHT);
		this.engine = new Engine(this.container);
		this.paint = new Paint(this.container.getDisplay(), this.engine);
		this.container.addPaintListener(this.paint);
		this.container.addKeyListener(new Key(this.engine));
		this.container.addMouseListener(new Mouse(this.engine));
		this.container.addMouseMoveListener(new MouseMove(this.engine));
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
	
	public void dispose() {
		this.stop();
		if(!this.container.isDisposed())
			this.container.dispose();
		this.paint.dispose();
		if(!this.shell.isDisposed())
			this.dispose();
	}
}
