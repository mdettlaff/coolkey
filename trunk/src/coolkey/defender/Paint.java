package coolkey.defender;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
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

public class Paint implements PaintListener {
	private final Display display;
	private final Engine engine;
	private final String texturePath;
	private final Font font8Bold;
	private final Font font10Bold;
	private final String formatScore;
	private final String formatTime;
	private final String formatLevel;
	private final Image bg;
	private final Image defender;
	private final Image panel;
	private final Image life;
	private final Image bomb;
	private final Image top10;
	private final Image result;
	private final List<Image> menu;
	
	public Paint(Display display, Engine engine) {
		this.display = display;
		this.engine = engine;
		this.texturePath = "data" + File.separator + "texture" + File.separator;
		this.font8Bold = new Font(this.display, "", 8, SWT.BOLD);
		this.font10Bold = new Font(this.display, "", 10, SWT.BOLD);
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
		this.menu = new ArrayList<Image>();
		this.menu.add(Engine.MENU_CONTINUE, new Image(this.display, this.texturePath + "menu_continue.png"));
		this.menu.add(Engine.MENU_NEW, new Image(this.display, this.texturePath + "menu_new.png"));
		this.menu.add(Engine.MENU_TOP10, new Image(this.display, this.texturePath + "menu_top10.png"));
		this.menu.add(Engine.MENU_HELP, new Image(this.display, this.texturePath + "menu_help.png"));
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
				this.drawBombs(pe.gc);
				pe.gc.drawImage(this.panel, 0, 416);
				this.drawLife(pe.gc);
				this.drawWord(pe.gc);
				this.drawTime(pe.gc);
				this.drawLevel(pe.gc);
				this.drawScore(pe.gc);
				break;
			case Engine.STATE_RESULT:
				pe.gc.drawImage(this.result, 170, 50);
				this.drawResult(pe.gc);
				pe.gc.drawString("ESC - Powrót do menu", 20, 450, true);
				break;
			case Engine.STATE_TOP10:
				pe.gc.drawImage(this.top10, 95, 50);
				this.drawTop10(pe.gc);
				pe.gc.drawString("ESC - Powrót do menu", 20, 450, true);
				break;
			case Engine.STATE_HELP:
				pe.gc.drawString("Help - ESC to exit", 50, 50, true);
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
	
	private void drawMenuItem(GC gc, int id) {
		Rectangle size = this.engine.getMenu(id);
		if(size == null)
			return;
		if(id == this.engine.getMenuSelectId())
			gc.drawImage(this.menu.get(id),
					size.width, 0, size.width, size.height,
					size.x, size.y, size.width, size.height);
		gc.drawImage(this.menu.get(id),
				0, 0, size.width, size.height,
				size.x, size.y, size.width, size.height);
	}
	
	private void drawBombs(GC gc) {
		Color fg = gc.getForeground();
		Color bg = gc.getBackground();
		Font font = gc.getFont();
		gc.setForeground(this.display.getSystemColor(SWT.COLOR_WHITE));
		gc.setBackground(this.display.getSystemColor(SWT.COLOR_BLACK));
		gc.setFont(this.font8Bold);
		for(Bomb b: this.engine.gameGetBombs()) {
			gc.drawImage(this.bomb, b.getX(), b.getY());
			Point wordSize = gc.stringExtent(b.getWord());
			int xLabel = b.getX() - (wordSize.x - 16)/2;
			int yLabel = b.getY() - (wordSize.y - 54)/2;
			gc.fillRectangle(xLabel, yLabel, wordSize.x + 6, wordSize.y + 2);
			gc.setForeground(this.display.getSystemColor(SWT.COLOR_GRAY));
			gc.drawRectangle(xLabel, yLabel, wordSize.x + 6, wordSize.y + 2);
			gc.setForeground(this.display.getSystemColor(SWT.COLOR_WHITE));
			gc.drawString(b.getWord(), xLabel + 3, yLabel + 1, true);
		}
		gc.setForeground(fg);
		gc.setBackground(bg);
		gc.setFont(font);
	}
	
	private void drawLife(GC gc) {
		for(int i = 0; i < Engine.GAME_LIFE_MAX; i++) {
			if(i < this.engine.gameGetLife())
				gc.drawImage(this.life, 0, 0, 32, 64, i * 32, 416, 32, 64);
			else
				gc.drawImage(this.life, 32, 0, 32, 64, i * 32, 416, 32, 64);
		}
	}
	
	private void drawWord(GC gc) {
		final int inputWidth = 244;
		final Color fg = gc.getForeground();
		final int lineWidth = gc.getLineWidth();
		final int lineStyle = gc.getLineStyle();
		gc.setLineWidth(1);
		gc.setLineStyle(SWT.LINE_DASH);
		String word = this.engine.gameGetWord();
		Point wordSize = gc.stringExtent(word);
		if(wordSize.x > inputWidth) {
			int i = 0;
			int size = 0;
			while(wordSize.x - size > inputWidth)
				size = gc.stringExtent(word.substring(0, ++i)).x;
			word = word.substring(i, word.length());
			wordSize = gc.stringExtent(word);
		}
		gc.setForeground(this.display.getSystemColor(SWT.COLOR_GRAY));
		gc.drawRectangle(195, 426, inputWidth + 6, wordSize.y + 2);
		gc.setForeground(fg);
		gc.drawString(word, (640 - wordSize.x)/2, 427, true);
		gc.setLineWidth(lineWidth);
		gc.setLineStyle(lineStyle);
	}
	
	private void drawTime(GC gc) {
		long time = this.engine.gameGetTime();
		String timeString = String.format(this.formatTime, time / 60000, (time / 1000) % 60, time % 1000);
		gc.drawString(timeString, (Engine.WIDTH - gc.stringExtent(timeString).x)/2, 455, true);
	}
	
	private void drawLevel(GC gc) {
		String levelString = String.format("Poziom: " + this.formatLevel, this.engine.gameGetLevel());
		gc.drawString(levelString, Engine.WIDTH - 20 - gc.stringExtent(levelString).x, 431, true);
	}
	
	private void drawScore(GC gc) {
		String scoreString = String.format("Punkty: " + this.formatScore, this.engine.gameGetScore());
		gc.drawString(scoreString, Engine.WIDTH - 20 - gc.stringExtent(scoreString).x, 451, true);
	}
	
	private void drawTop10(GC gc) {
		final int x_pos = 140;
		final int x_score = 230;
		final int x_time = 345;
		final int x_level = 460;
		final int y = 145;
		List<Score> highScore = CoolKey.getUser().getHighscore();
		String strPos = "Poz.";
		String strScore = "Punkty";
		String strTime = "Czas";
		String strLevel = "Poziom";
		gc.drawString(strPos, x_pos - gc.stringExtent(strPos).x/2, y, true);
		gc.drawString(strScore, x_score - gc.stringExtent(strScore).x/2, y, true);
		gc.drawString(strTime, x_time - gc.stringExtent(strTime).x/2, y, true);
		gc.drawString(strLevel, x_level - gc.stringExtent(strLevel).x/2, y, true);
		for(int i = 1; i <= Engine.TOP10_RESULT; i++) {
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
	
	private void drawResult(GC gc) {
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
		gc.drawString(String.format(this.formatScore, this.engine.gameGetScore()), x + 10, y_score, true);
		long time = this.engine.gameGetTime();
		gc.drawString(String.format(this.formatTime,
				time / 60000,
				(time / 1000) % 60,
				time % 1000), x + 10, y_time, true);
		gc.drawString(String.format(this.formatLevel, this.engine.gameGetLevel()), x + 10, y_level, true);
	}
}
