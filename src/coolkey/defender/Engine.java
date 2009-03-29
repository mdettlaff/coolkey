package coolkey.defender;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class Engine implements Runnable {
	public static final int STATE_MENU = 0;
	public static final int STATE_GAME = 1;
	public static final int STATE_RESULT = 2;
	public static final int STATE_TOP10 = 3;
	public static final int STATE_HELP = 4;
	
	public static final int MENU_CONTINUE = 0;
	public static final int MENU_NEW = 1;
	public static final int MENU_TOP10 = 2;
	public static final int MENU_HELP = 3;
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	
	private final int INTERVAL = 20;
	
	private Display dispaly;
	private Canvas container;
	
	private double fps;
	private boolean showFps;
	
	private long timeLast;
	private int countFrame;
	
	private boolean newGame;
	private int state;
	private int menuSelectId;
	
	public Engine(Display dispaly, Canvas container) {
		this.dispaly = dispaly;
		this.container = container;
		this.fps = 50.0;
		this.showFps = false;
		this.newGame = true;
		this.state = STATE_MENU;
		this.menuSelectId = MENU_NEW;
	}
	
	public synchronized double getFps() {
		return this.fps;
	}

	public synchronized boolean showFps() {
		return this.showFps;
	}
	
	public synchronized void showFps(boolean showFps) {
		this.showFps = showFps;
	}
	
	public synchronized int getState() {
		return this.state;
	}
	
	public synchronized boolean isNewGame() {
		return this.newGame;
	}

	public synchronized int getMenuSelectId() {
		return this.menuSelectId;
	}
	
	public synchronized void nextMenuItem() {
		if(this.menuSelectId < MENU_HELP)
			this.menuSelectId++;
	}
	
	public synchronized void prevMenuItem() {
		if(this.newGame) {
			if(this.menuSelectId > MENU_NEW)
				this.menuSelectId--;
		}
		else {
			if(this.menuSelectId > MENU_CONTINUE)
				this.menuSelectId--;
		}
	}
	
	public synchronized void enterMenuItem() {
		switch(this.menuSelectId) {
			case MENU_CONTINUE:
				this.state = STATE_GAME;
				break;
			case MENU_NEW:
				// clean
				this.newGame = false;
				this.state = STATE_GAME;
				break;
			case MENU_TOP10:
				this.state = STATE_TOP10;
				break;
			case MENU_HELP:
				this.state = STATE_HELP;
				break;
		}
	}
	
	public synchronized void escapeToMenu() {
		if(!this.newGame && this.state == STATE_GAME)
			this.menuSelectId = MENU_CONTINUE;
		this.state = STATE_MENU;
	}
	
	public void run() {
		long timeStart = System.currentTimeMillis();
		int time = (int)(timeStart - this.timeLast);
		if(time < 1000) {
			this.countFrame++;
		}
		else {
			this.fps = this.countFrame * 1000.0 / time;
			this.countFrame = 0;
			this.timeLast += 1000;
		}
		this.container.redraw();
		time = INTERVAL - (int)(System.currentTimeMillis() - timeStart);
		this.dispaly.timerExec((time < 0 ? 0 : time), this);
	}
	
	public void start() {
		this.countFrame = 0;
		this.timeLast = System.currentTimeMillis();
		this.dispaly.timerExec(0, this);
	}
	public void stop() {
		if(this.dispaly != null)
			this.dispaly.timerExec(-1, this);
	}
}
