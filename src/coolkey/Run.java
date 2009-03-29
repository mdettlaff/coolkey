package coolkey;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import coolkey.defender.Defender;
import coolkey.defender.Engine;

public class Run {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Run");
		Point p = shell.computeSize(Engine.WIDTH, Engine.HEIGHT);
		shell.setSize(p);
		shell.setMinimumSize(p);
		Defender game = new Defender(shell);
		//game.setLocation(new Point(10, 10));
		game.showFps(true);
		game.start();
		shell.open();
		while (!shell.isDisposed()){
			if (!display.readAndDispatch()) display.sleep();
		}
		game.stop();
		display.dispose ();
	}
}
