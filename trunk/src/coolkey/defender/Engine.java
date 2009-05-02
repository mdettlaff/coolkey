package coolkey.defender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
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
	
	private final int INTERVAL = 20;
	
	public static final int GAME_LIFE_MAX = 3;
	public static final int GAME_LEVEL_MAX = 9;
	public static final int GAME_GROUND = 416;
	
	private Display display;
	private Canvas container;
	
	private double fps;
	private boolean showFps;
	
	private long timeLast;
	private int countFrame;
	
	private boolean newGame;
	private int state;
	private int menuSelectId;
	
	private int gameLevel;
	private int gameLife;
	private int gameScore;
	private long gameTime;
	private String gameWord;
	private Map<String, Bomb> gameBombs;
	
	public Engine(Display display, Canvas container) {
		this.display = display;
		this.container = container;
		this.fps = 50.0;
		this.showFps = false;
		this.newGame = true;
		this.state = STATE_MENU;
		this.menuSelectId = MENU_NEW;
		this.gameNew();
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
	
	public synchronized void escapeToMenu() {
		if(!this.newGame && this.state == STATE_GAME)
			this.menuSelectId = MENU_CONTINUE;
		this.state = STATE_MENU;
	}
	
	public synchronized int gameGetLife() {
		return this.gameLife;
	}
	
	public synchronized int gameGetLevel() {
		return this.gameLevel;
	}
	
	public synchronized int gameGetScore() {
		return this.gameScore;
	}
	
	public synchronized long gameGetTime() {
		return this.gameTime;
	}
	
	public synchronized String gameGetWord() {
		return this.gameWord;
	}
	
	public synchronized Collection<Bomb> gameGetBombs() {
		return this.gameBombs.values();
	}
	
	private synchronized int gameHowBombs() {
		return this.gameLevel * 2 + 1;
	}
	
	private synchronized int gameHowFast() {
		return this.gameLevel * 5 + 10;
	}
	
	private synchronized int gameHowScore() {
		return this.gameLevel^2 + 19 * this.gameLevel - 5;
	}
	
	public synchronized void gameWordAdd(char c) {
		String specialChar = ",./;\'[]<>?:\"{}!@#$%^&*()_+|\\-=";
		if(Character.isLetterOrDigit(c) || specialChar.indexOf(c) != -1)
			this.gameWord = this.gameWord + c;
	}
	
	public synchronized void gameWordDel() {
		if(this.gameWord.length() > 0)
			this.gameWord = this.gameWord.substring(0, this.gameWord.length() - 1);
	}
	
	public synchronized void gameWordEnter() {
		if(this.gameWord.length() > 0) {
			if(this.gameBombs.containsKey(this.gameWord)) {
				this.gameScore++;
				this.gameBombs.remove(this.gameWord);
			}
			this.gameWord = "";
		}
	}
	
	private synchronized void gameNew() {
		this.gameLife = GAME_LIFE_MAX;
		this.gameScore = 0;
		this.gameTime = 0;
		this.gameLevel = 1;
		this.gameWord = "";
		this.gameBombs = new HashMap<String, Bomb>();
	}
	
	private synchronized void gameCreateBombs() {
		Random rand = new Random();
		GC gc = new GC(new Image(this.display, 1, 1));
		while(this.gameBombs.size() < this.gameHowBombs()) {
			String word = CoolKey.getDictionary().randomWord(this.gameLevel);
			int wordWidth = gc.stringExtent(word).x;
			int x = (wordWidth - 16)/2 + rand.nextInt(WIDTH - wordWidth - 6);
			int y = -48 - rand.nextInt(64);
			Bomb b = new Bomb(word, x, y, this.gameHowFast());
			this.gameBombs.put(b.getWord(), b);
		}
	}
	
	private synchronized void game() {
		if(this.gameLevel < GAME_LEVEL_MAX) {
			if(this.gameScore > this.gameHowScore())
				this.gameLevel++;
		}
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
		if(this.gameBombs.size() < this.gameHowBombs())
			this.gameCreateBombs();
		if(this.gameLife < 1) {
			this.state = STATE_RESULT;
			this.newGame = true;
		}
	}
	
	public synchronized void run() {
		long timeStart = System.currentTimeMillis();
		int time = (int)(timeStart - this.timeLast);
		if(time < 1000) {
			this.countFrame++;
		}
		else {
			this.fps = this.countFrame * 1000.0 / time;
			this.countFrame = 0;
			this.timeLast += time;
		}
		if(this.state == STATE_GAME)
			this.game();
		if(!this.container.isDisposed())
			this.container.redraw();
		else
			this.stop();
		time = INTERVAL - (int)(System.currentTimeMillis() - timeStart);
		this.display.timerExec((time < 0 ? 0 : time), this);
	}
	
	public synchronized void start() {
		this.countFrame = 0;
		this.timeLast = System.currentTimeMillis();
		this.display.timerExec(0, this);
	}
	public synchronized void stop() {
		if(!this.display.isDisposed())
			this.display.timerExec(-1, this);
	}
}
