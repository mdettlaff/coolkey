package coolkey.defender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import coolkey.CoolKey;

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
	
	public static final int GAME_LIFE_MAX = 3;
	public static final int GAME_LEVEL_MAX = 9;
	public static final int GAME_GROUND = 416;
	
	public static final int TOP10_RESULT = 10;
	
	private final int INTERVAL = 40;
	
	private Display display;
	private Canvas container;
	
	private double fps;
	private boolean showFps;
	
	private long timeLast;
	private int countFrame;
	
	private boolean newGame;
	private int state;
	
	private int menuSelectId;
	private List<Rectangle> menu;
	
	private int gameLevel;
	private int gameLife;
	private int gameScore;
	private long gameTime;
	private Timer gameTimer;
	private String gameWord;
	private Map<String, Bomb> gameBombs;
	
	public Engine(Display display, Canvas container) {
		this.display = display;
		this.container = container;
		this.fps = 25.0;
		this.showFps = false;
		this.newGame = true;
		this.state = STATE_MENU;
		this.menuSelectId = MENU_NEW;
		this.menu = new ArrayList<Rectangle>();
		this.menu.add(MENU_CONTINUE, new Rectangle(192, 150, 256, 64));
		this.menu.add(MENU_NEW, new Rectangle(192, 214, 256, 64));
		this.menu.add(MENU_TOP10, new Rectangle(192, 278, 256, 64));
		this.menu.add(MENU_HELP, new Rectangle(192, 342, 256, 64));
		this.gameTimer = new Timer();
		this.gameTimer.schedule(new TimerTask () {
			private long timeBefor = System.currentTimeMillis();
			public void run() {
				long timeCurrent = System.currentTimeMillis();
				if(state == STATE_GAME) {
					gameTime += timeCurrent - timeBefor;		
				}
				timeBefor = timeCurrent;
			}
		}, INTERVAL, INTERVAL);
		this.gameNew();
	}
	
	public double getFps() {
		return this.fps;
	}

	public boolean showFps() {
		return this.showFps;
	}
	
	public void showFps(boolean showFps) {
		this.showFps = showFps;
	}
	
	public int getState() {
		return this.state;
	}
	
	public boolean isNewGame() {
		return this.newGame;
	}
	
	public int getMenuSelectId() {
		return this.menuSelectId;
	}
	
	public List<Rectangle> getMenu() {
		return this.menu;
	}
	
	public Rectangle getMenu(int id) {
		return this.menu.get(id);
	}
	
	public void keyNext() {
		switch(this.menuSelectId) {
			case MENU_CONTINUE:
			case MENU_NEW:
			case MENU_TOP10:
				break;
			case MENU_HELP:
				return;
			default:
				this.menuSelectId = MENU_HELP;
				return;
		}
		this.menuSelectId++;
	}
	
	public void keyPrev() {
		switch(this.menuSelectId) {
			case MENU_CONTINUE:
				return;
			case MENU_NEW:
				if(this.newGame)
					return;
			case MENU_TOP10:
			case MENU_HELP:
				break;
			default:
				if(this.newGame)
					this.menuSelectId = MENU_NEW;
				else
					this.menuSelectId = MENU_CONTINUE;
				return;	
		}
		this.menuSelectId--;
	}
	
	public void keyEnter() {
		switch(this.menuSelectId) {
			case MENU_CONTINUE:
				if(!this.newGame)
					this.state = STATE_GAME;
				break;
			case MENU_NEW:
				this.gameNew();
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
	
	public void keyEsc() {
		switch(this.state) {
		case STATE_GAME:
			if(this.newGame)
				this.menuSelectId = MENU_NEW;
			else
				this.menuSelectId = MENU_CONTINUE;
			break;
		case STATE_RESULT:
			this.menuSelectId = MENU_NEW;
			break;
		}
		this.state = STATE_MENU;
	}
	
	public void mouseUp(int x, int y) {
		if(this.state != STATE_MENU)
			return;
		Rectangle size = this.menu.get(MENU_CONTINUE);
		if(size.x > x || size.x + size.width < x)
			return;
		int select = (y - size.y) / size.height;
		if(select != this.menuSelectId)
			return;
		switch(select) {
			case MENU_CONTINUE:
				if(!this.newGame)
					this.state = STATE_GAME;
				break;
			case MENU_NEW:
				this.gameNew();
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
	
	public void mouseMove(int x, int y) {
		if(this.state != STATE_MENU)
			return;
		Rectangle size = this.menu.get(MENU_CONTINUE);
		if(size.x > x || size.x + size.width < x)
			return;
		switch((y - size.y) / size.height) {
			case MENU_CONTINUE:
				if(!this.newGame)
					this.menuSelectId = MENU_CONTINUE;
				break;
			case MENU_NEW:
				this.menuSelectId = MENU_NEW;
				break;
			case MENU_TOP10:
				this.menuSelectId = MENU_TOP10;
				break;
			case MENU_HELP:
				this.menuSelectId = MENU_HELP;
				break;
		}
	}
	
	public int gameGetLife() {
		return this.gameLife;
	}
	
	public int gameGetLevel() {
		return this.gameLevel;
	}
	
	public int gameGetScore() {
		return this.gameScore;
	}
	
	public long gameGetTime() {
		return this.gameTime;
	}
	
	public String gameGetWord() {
		return this.gameWord;
	}
	
	public Collection<Bomb> gameGetBombs() {
		return this.gameBombs.values();
	}
	
	private int gameHowBombs() {
		return this.gameLevel * 2 + 1;
	}
	
	private int gameHowFast() {
		return this.gameLevel * 5 + 10;
	}
	
	private int gameHowScore() {
		return this.gameLevel^2 + 19 * this.gameLevel - 5;
	}
	
	public void gameWordAdd(char c) {
		String specialChar = ",./;\'[]<>?:\"{}!@#$%^&*()_+|\\-=";
		if(Character.isLetterOrDigit(c) || specialChar.indexOf(c) != -1)
			this.gameWord = this.gameWord + c;
	}
	
	public void gameWordDel() {
		if(this.gameWord.length() > 0)
			this.gameWord = this.gameWord.substring(0, this.gameWord.length() - 1);
	}
	
	public void gameWordEnter() {
		if(this.gameWord.length() > 0) {
			if(this.gameBombs.containsKey(this.gameWord)) {
				this.gameScore++;
				this.gameBombs.remove(this.gameWord);
			}
			this.gameWord = "";
		}
	}
	
	private void gameNew() {
		this.gameLife = GAME_LIFE_MAX;
		this.gameScore = 0;
		this.gameTime = 0;
		this.gameLevel = 1;
		this.gameWord = "";
		this.gameBombs = new HashMap<String, Bomb>();
	}
	
	private void gameCreateBombs() {
		Thread th = new Thread(new Runnable() {
			public void run() {
				Random random = new Random();
				GC gc = new GC(new Image(display, 1, 1));
				while(gameBombs.size() < gameHowBombs()) {
					String word = CoolKey.getDictionary().randomWord(gameLevel);
					if(gameBombs.containsKey(word))
						continue;
					int wordWidth = gc.stringExtent(word).x;
					int x = (wordWidth - 16)/2 + random.nextInt(WIDTH - wordWidth - 6);
					int y = -48 - random.nextInt(64);
					Bomb b = new Bomb(word, x, y, gameHowFast());
					gameBombs.put(b.getWord(), b);
				}
			}
		}, "gameCreateBombs");
		if(!th.isAlive())
			th.start();
	}
	
	private void action() {
		switch(this.state) {
			case STATE_GAME:
				if(this.gameLevel < GAME_LEVEL_MAX && this.gameScore > this.gameHowScore())
					this.gameLevel++;
				List<String> removeBombs = new ArrayList<String>();
				for(Bomb b: this.gameGetBombs()) {
					b.addY(b.getSpeed()/this.fps);
					if(b.getY() + 48 > GAME_GROUND) {
						this.gameLife--;
						removeBombs.add(b.getWord());
					}
				}
				for(String w: removeBombs)
					this.gameBombs.remove(w);
				this.gameCreateBombs();
				if(this.gameLife < 1) {
					this.state = STATE_RESULT;
					this.newGame = true;
				}
				break;
		}
	}
	
	public void run() {
		long timeStart = System.currentTimeMillis();
		int time = (int)(timeStart - this.timeLast);
		if(time < 1000) {
			this.countFrame++;
		}
		else {
			this.fps = this.countFrame * 1000.0 / time;
			this.countFrame = 1;
			this.timeLast += time;
		}
		this.action();
		if(!this.container.isDisposed())
			this.container.redraw();
		else
			this.stop();
		time = INTERVAL - (int)(System.currentTimeMillis() - timeStart);
		this.display.timerExec((time < 0 ? 0 : time), this);
	}
	
	public void start() {
		this.countFrame = 0;
		this.timeLast = System.currentTimeMillis();
		this.display.timerExec(0, this);
	}
	public void stop() {
		this.gameTimer.cancel();
		if(!this.display.isDisposed())
			this.display.timerExec(-1, this);
	}
}
