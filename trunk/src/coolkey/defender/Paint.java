package coolkey.defender;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import coolkey.CoolKey;

/**
 * Klasa służy do rysowania gry na podanym urządzeniu.
 */
public class Paint implements PaintListener {
	/**
	 * Uchwyt do urządzenia, które wyświetla obraz.
	 */
	private final Display display;
	/**
	 * Uchwyt do mechanizmu gry.
	 */
	private final Engine engine;
	/**
	 * Ścieżka do katalogu z texturami.
	 */
	private final String texturePath;
	/**
	 * Uchwyt do czcionki dla etykiet bomb.
	 */
	private final Font font8Bold;
	/**
	 * Uchwyt do czcionki. Domyślna dla całej gry.
	 */
	private final Font font10Bold;
	/**
	 * Format wyświetlania liczby zdobytych punktów.
	 */
	private final String formatScore;
	/**
	 * Format wyświetlania czasu trwania gry.
	 */
	private final String formatTime;
	/**
	 * Format wyświetlania poziomu gry.
	 */
	private final String formatLevel;
	/**
	 * Uchwyt do textury tła gry.
	 */
	private final Image bg;
	/**
	 * Uchwyt do loga gry.
	 */
	private final Image defender;
	/**
	 * Uchwyt do textury panelu.
	 */
	private final Image panel;
	/**
	 * Uchwyt do textury ilości żyć.
	 */
	private final Image life;
	/**
	 * Uchwyt do textury bomby.
	 */
	private final Image bomb;
	/**
	 * Uchwyt do textury tła w 'Top10'.
	 */
	private final Image top10;
	/**
	 * Uchwyt do textury tła w 'Koniec gry'.
	 */
	private final Image result;
	/**
	 * Uchwyt do textury eksplozji bomby.
	 */
	private final Image explosion;
	/**
	 * Uchwyt do textury niszczenia bomby.
	 */
	private final Image explosion2;
	/**
	 * Uchwyt do textury z opisem w 'Pomoc'.
	 */
	private final Image help;
	/**
	 * Uchwyt do textur dla przycisków.
	 */
	private final List<Image> menu;
	/**
	 * Tworzy nowy obiekt, który będzie rysował grę.
	 * @param display uchwyt do urządzenia, które wyświetla obraz
	 * @param engine uchwyt do mechanizmu gry
	 */
	public Paint(Display display, Engine engine) {
		this.display = display;
		this.engine = engine;
		this.texturePath = "data" + File.separator + "texture" + File.separator;
		this.font8Bold = new Font(this.display, "sans", 8, SWT.BOLD);
		this.font10Bold = new Font(this.display, "sans", 10, SWT.BOLD);
		this.formatScore = "%1$04d";
		this.formatTime = "%1$02d:%2$02d:%3$03d";
		this.formatLevel = "%1$02d";
		this.bg = new Image(this.display, this.texturePath + "background.png");
		this.defender = new Image(this.display, this.texturePath + "defender.png");
		this.panel = new Image(this.display, this.texturePath + "panel.png");
		this.life = new Image(this.display, this.texturePath + "life.png");
		this.bomb = new Image(this.display, this.texturePath + "bomb.png");
		this.top10 = new Image(this.display, this.texturePath + "top10.png");
		this.result = new Image(this.display, this.texturePath + "result.png");
		this.explosion = new Image(this.display, this.texturePath + "explosion.png");
		this.explosion2 = new Image(this.display, this.texturePath + "explosion2.png");
		this.help = new Image(this.display, this.texturePath + "help.png");
		this.menu = new ArrayList<Image>();
		this.menu.add(Engine.MENU_CONTINUE, new Image(this.display, this.texturePath + "menu_continue.png"));
		this.menu.add(Engine.MENU_NEW, new Image(this.display, this.texturePath + "menu_new.png"));
		this.menu.add(Engine.MENU_TOP10, new Image(this.display, this.texturePath + "menu_top10.png"));
		this.menu.add(Engine.MENU_HELP, new Image(this.display, this.texturePath + "menu_help.png"));
		this.menu.add(Engine.MENU_ESC, new Image(this.display, this.texturePath + "menu_esc.png"));
	}
	
	public void paintControl(PaintEvent pe) {
		pe.gc.setFont(this.font10Bold);
		pe.gc.setForeground(this.display.getSystemColor(SWT.COLOR_WHITE));
		pe.gc.drawImage(this.bg, 0, 0);
		switch(this.engine.getState()) {
			case Engine.STATE_MENU:
				pe.gc.drawImage(this.defender, 80, 20);
				if(!this.engine.isNewGame())
					this.drawMenuItem(pe.gc, Engine.MENU_CONTINUE);
				this.drawMenuItem(pe.gc, Engine.MENU_NEW);
				this.drawMenuItem(pe.gc, Engine.MENU_TOP10);
				this.drawMenuItem(pe.gc, Engine.MENU_HELP);
				break;
			case Engine.STATE_GAME:
				this.drawExplosion(pe.gc, this.engine.gameGetBombsExplosion());
				this.drawBombs(pe.gc, this.engine.gameGetWord(),
					this.engine.gameIsWordClear(),
					this.engine.gameGetBombs());
				pe.gc.drawImage(this.panel, 0, 416);
				this.drawLife(pe.gc, this.engine.gameGetLife());
				this.drawWord(pe.gc, this.engine.gameGetWord());
				this.drawTime(pe.gc, this.engine.gameGetTime());
				this.drawLevel(pe.gc, this.engine.gameGetLevel());
				this.drawScore(pe.gc, this.engine.gameGetScore());
				this.drawMenuItem(pe.gc, Engine.MENU_ESC);
				break;
			case Engine.STATE_RESULT:
				pe.gc.drawImage(this.result, 170, 50);
				this.drawResult(pe.gc, this.engine.gameGetScore(),
					this.engine.gameGetTime(),
					this.engine.gameGetLevel());
				this.drawMenuItem(pe.gc, Engine.MENU_ESC);
				break;
			case Engine.STATE_TOP10:
				pe.gc.drawImage(this.top10, 95, 50);
				this.drawTop10(pe.gc, CoolKey.getUser().getHighscore());
				this.drawMenuItem(pe.gc, Engine.MENU_ESC);
				break;
			case Engine.STATE_HELP:
				this.drawHelp(pe.gc);
				break;
		}
		if(this.engine.showFps()) {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			pe.gc.setForeground(this.display.getSystemColor(SWT.COLOR_WHITE));
			pe.gc.drawString("Fps: " + nf.format(this.engine.getFps()), 5, 5, true);
		}
		pe.gc.dispose();
	}
	/**
	 * Rysuję przycisk o podanym identyfikatorze.
	 * @param gc uchwyt do obiektu, który rysuję
	 * @param id identyfikator przycisku
	 */
	private void drawMenuItem(GC gc, int id) {
		Rectangle size = this.engine.getMenu(id);
		if(id == this.engine.getMenuSelectId() || (id == Engine.MENU_ESC && this.engine.isMenuEscSelect()))
			gc.drawImage(this.menu.get(id),
				size.width, 0, size.width, size.height,
				size.x, size.y, size.width, size.height);
		else
			gc.drawImage(this.menu.get(id),
				0, 0, size.width, size.height,
				size.x, size.y, size.width, size.height);
	}
	/**
	 * Rysuję eksplozję dla podanych bomb.
	 * @param gc uchwyt do obiektu, który rysuję
	 * @param bombs lista bomb
	 */
	private void drawExplosion(GC gc, List<Bomb> bombs) {
		for(Bomb b: bombs) {
			if(b.getExplosionStep() < 4)
				gc.drawImage(this.bomb, b.getX(), b.getY());
			if(b.getY() + 48 > Engine.GAME_GROUND) {
				gc.drawImage(this.explosion,
					64 * b.getExplosionStep(), 0, 64, 64,
					b.getX() - 21, b.getY() - 8, 64, 64);
			}
			else {
				gc.drawImage(this.explosion2,
					64 * b.getExplosionStep(), 0, 64, 64,
					b.getX() - 21, b.getY() - 8, 64, 64);
			}
		}
	}
	/**
	 * Rysuję bomby z podanej kolekcji.
	 * @param gc uchwyt do obiektu, który rysuję
	 * @param word słowo lub fragment słowa, które jest zaznaczone na bombie
	 * @param wordClear czy słowo jest wyczyszczone
	 * @param bombs kolekcja bomb
	 */
	private void drawBombs(GC gc, String word, boolean wordClear, Collection<Bomb> bombs) {
		Color fg = gc.getForeground();
		Color bg = gc.getBackground();
		Font font = gc.getFont();
		gc.setBackground(this.display.getSystemColor(SWT.COLOR_BLACK));
		gc.setFont(this.font8Bold);
		for(Bomb b: bombs) {
			gc.drawImage(this.bomb, b.getX(), b.getY());
			Point wordSize = gc.stringExtent(b.getWord());
			int labelX = b.getX() - (wordSize.x - 16)/2;
			int labelY = b.getY() - (wordSize.y - 46)/2;
			int labelWidth = wordSize.x + 6;
			int lableHeight = wordSize.y + 2;
			gc.fillRectangle(labelX, labelY, labelWidth, lableHeight);
			gc.setForeground(this.display.getSystemColor(SWT.COLOR_GRAY));
			gc.drawRectangle(labelX, labelY, labelWidth, lableHeight);
			int i = 0;
			for(; i < b.getWord().length(); i++) {
				char c = '\0';
				try {
					c = word.charAt(i);
					if(c != b.getWord().charAt(i) || wordClear)
						break;
				} catch(IndexOutOfBoundsException e) {
					break;
				}
			}
			String prefix = b.getWord().substring(0, i);
			int offset = gc.stringExtent(prefix).x;
			gc.setForeground(this.display.getSystemColor(SWT.COLOR_RED));
			gc.drawString(prefix, labelX + 3, labelY + 1, true);
			gc.setForeground(this.display.getSystemColor(SWT.COLOR_WHITE));
			gc.drawString(b.getWord().substring(i), labelX + offset + 3, labelY + 1, true);
		}
		gc.setForeground(fg);
		gc.setBackground(bg);
		gc.setFont(font);
	}
	/**
	 * Rysuję ilość pozostałych żyć.
	 * @param gc uchwyt do obiektu, który rysuję
	 * @param life ilość pozostałych żyć
	 */
	private void drawLife(GC gc, int life) {
		for(int i = 0; i < Engine.GAME_LIFE_MAX; i++) {
			if(i < life)
				gc.drawImage(this.life, 0, 0, 32, 64, i * 32, 416, 32, 64);
			else
				gc.drawImage(this.life, 32, 0, 32, 64, i * 32, 416, 32, 64);
		}
	}
	/**
	 * Rysuję pole ze słowem
	 * @param gc uchwyt do obiektu, który rysuję
	 * @param word słowo, które ma być narysowane
	 */
	private void drawWord(GC gc, String word) {
		final int inputWidth = 250;
		final Color fgOld = gc.getForeground();
		final Font fontOld = gc.getFont();
		final int lineWidthOld = gc.getLineWidth();
		final int lineStyleOld = gc.getLineStyle();
		gc.setLineWidth(1);
		gc.setLineStyle(SWT.LINE_DASH);
		gc.setFont(this.font8Bold);
		Point wordSize = gc.stringExtent(word);
		if(wordSize.x > inputWidth - 6) {
			int i = 0;
			int size = 0;
			while(wordSize.x - size > inputWidth - 6)
				size = gc.stringExtent(word.substring(0, ++i)).x;
			word = word.substring(i, word.length());
			wordSize = gc.stringExtent(word);
		}
		gc.setForeground(this.display.getSystemColor(SWT.COLOR_GRAY));
		gc.drawRectangle((Engine.WIDTH - inputWidth)/2, 426, inputWidth, wordSize.y + 4);
		gc.setForeground(this.display.getSystemColor(SWT.COLOR_WHITE));
		gc.drawString(word, (Engine.WIDTH - wordSize.x)/2, 428, true);
		gc.setForeground(fgOld);
		gc.setFont(fontOld);
		gc.setLineWidth(lineWidthOld);
		gc.setLineStyle(lineStyleOld);
	}
	/**
	 * Rysuję stoper.
	 * @param gc uchwyt do obiektu, który rysuję
	 * @param time czas, który ma być narysowany.
	 */
	private void drawTime(GC gc, long time) {
		String timeString = String.format(this.formatTime, time / 60000, (time / 1000) % 60, time % 1000);
		gc.drawString(timeString, (Engine.WIDTH - gc.stringExtent(timeString).x)/2, 455, true);
	}
	/**
	 * Rysuję poziom gry.
	 * @param gc uchwyt do obiektu, który rysuję
	 * @param level poziom, który ma być narysowany
	 */
	private void drawLevel(GC gc, int level) {
		String levelString = String.format("Poziom: " + this.formatLevel, level);
		gc.drawString(levelString, Engine.WIDTH - 64 - gc.stringExtent(levelString).x, 431, true);
	}
	/**
	 * Rysuję liczbę punktów.
	 * @param gc uchwyt do obiektu, który rysuję
	 * @param score punkty, które mają być narysowane
	 */
	private void drawScore(GC gc, int score) {
		String scoreString = String.format("Punkty: " + this.formatScore, score);
		gc.drawString(scoreString, Engine.WIDTH - 64 - gc.stringExtent(scoreString).x, 451, true);
	}
	/**
	 * Rysuję tabele z wynikami.
	 * @param gc uchwyt do obiektu, który rysuję
	 * @param highScore lista wyników
	 */
	private void drawTop10(GC gc, List<Score> highScore) {
		final int x_pos = 140;
		final int x_score = 230;
		final int x_time = 345;
		final int x_level = 460;
		final int y = 145;
		String strPos = "Poz.";
		String strScore = "Punkty";
		String strTime = "Czas";
		String strLevel = "Poziom";
		gc.drawString(strPos, x_pos - gc.stringExtent(strPos).x/2, y, true);
		gc.drawString(strScore, x_score - gc.stringExtent(strScore).x/2, y, true);
		gc.drawString(strTime, x_time - gc.stringExtent(strTime).x/2, y, true);
		gc.drawString(strLevel, x_level - gc.stringExtent(strLevel).x/2, y, true);
		for(int i = 1; i <= Engine.TOP10_RESULTS; i++) {
			strPos = i + ".";
			try {
				Score score = highScore.get(i - 1);
				strScore = String.format(this.formatScore, score.getScore()); 
				strTime = String.format(this.formatTime,
						score.getTime() / 60000,
						(score.getTime() / 1000) % 60,
						score.getTime() % 1000);
				strLevel = String.format(this.formatLevel, score.getLevel());
			} catch(IndexOutOfBoundsException e) {
				strScore = "-";
				strTime = "-";
				strLevel = "-";
			}
			int y_pos = y + i * 22;
			gc.drawString(strPos, x_pos - gc.stringExtent(strPos).x + 10, y_pos, true);
			gc.drawString(strScore, x_score - gc.stringExtent(strScore).x/2, y_pos, true);
			gc.drawString(strTime, x_time - gc.stringExtent(strTime).x/2, y_pos, true);
			gc.drawString(strLevel, x_level - gc.stringExtent(strLevel).x/2, y_pos, true);
		}
	}
	/**
	 * Rysuję wynik ukonczonej gry.
	 * @param gc uchwyt do obiektu, który rysuję
	 * @param score liczba zdobytych punktów
	 * @param time czas trwania gry
	 * @param level poziom na którym gracz skończył grę
	 */
	private void drawResult(GC gc, int score, long time, int level) {
		final int x = Engine.WIDTH / 2;
		final int y_score = 200;
		final int y_time = 222;
		final int y_level = 244;
		String strScore = "Punkty";
		String strTime = "Czas";
		String strLevel = "Poziom";
		gc.drawString(strScore, x - gc.stringExtent(strScore).x, y_score, true);
		gc.drawString(strTime, x -  gc.stringExtent(strTime).x, y_time, true);
		gc.drawString(strLevel, x -  gc.stringExtent(strLevel).x, y_level, true);
		gc.drawString(String.format(this.formatScore, score), x + 10, y_score, true);
		gc.drawString(String.format(this.formatTime,
				time / 60000,
				(time / 1000) % 60,
				time % 1000), x + 10, y_time, true);
		gc.drawString(String.format(this.formatLevel, level), x + 10, y_level, true);
	}
	/**
	 * Rysuję pomoc.
	 * @param gc uchwyt do obiektu, który rysuję
	 */
	private void drawHelp(GC gc) {
		Collection<Bomb> bombs = new ArrayList<Bomb>();
		bombs.add(new Bomb("słowo", 450, 150, 0));
		this.drawBombs(gc, "sło", false, bombs);
		gc.drawImage(this.panel, 0, 416);
		this.drawLife(gc, 3);
		this.drawWord(gc, "sło");
		this.drawTime(gc, 12345);
		this.drawLevel(gc, 1);
		this.drawScore(gc, 1);
		this.drawMenuItem(gc, Engine.MENU_ESC);
		gc.drawImage(this.help, 0, 0);
		bombs.clear();
	}
	/**
	 * Zwalnia wszystkie zajęte zasoby.
	 */
	public void dispose() {
		if(!this.font8Bold.isDisposed())
			this.font8Bold.dispose();
		if(!this.font10Bold.isDisposed())
			this.font10Bold.dispose();
		if(!this.bg.isDisposed())
			this.bg.dispose();
		if(!this.defender.isDisposed())
			this.defender.dispose();
		if(!this.panel.isDisposed())
			this.panel.dispose();
		if(!this.life.isDisposed())
			this.life.dispose();
		if(!this.bomb.isDisposed())
			this.bomb.dispose();
		if(!this.top10.isDisposed())
			this.top10.dispose();
		if(!this.result.isDisposed())
			this.result.dispose();
		if(!this.explosion.isDisposed())
			this.explosion.dispose();
		if(!this.explosion2.isDisposed())
			this.explosion2.dispose();
		if(!this.help.isDisposed())
			this.help.dispose();
		if(!this.menu.get(Engine.MENU_CONTINUE).isDisposed())
			this.menu.get(Engine.MENU_CONTINUE).dispose();
		if(!this.menu.get(Engine.MENU_NEW).isDisposed())
			this.menu.get(Engine.MENU_NEW).dispose();
		if(!this.menu.get(Engine.MENU_TOP10).isDisposed())
			this.menu.get(Engine.MENU_TOP10).dispose();
		if(!this.menu.get(Engine.MENU_HELP).isDisposed())
			this.menu.get(Engine.MENU_HELP).dispose();
		if(!this.menu.get(Engine.MENU_ESC).isDisposed())
			this.menu.get(Engine.MENU_ESC).dispose();
		this.menu.clear();
	}
}
