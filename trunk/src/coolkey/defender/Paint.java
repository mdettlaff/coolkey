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

public class Paint implements PaintListener {
	private Display display;
	private Engine engine;
	private String texturePath;
	private Image bg;
	private Image defender;
	private Image panel;
	private Image life;
	private Image bomb;
	private List<Image> menu;
	
	public Paint(Display display, Engine engine) {
		this.display = display;
		this.engine = engine;
		this.texturePath = "data" + File.separator + "texture" + File.separator;
		this.bg = new Image(this.display, this.texturePath + "background.png");
		this.defender = new Image(this.display, this.texturePath + "defender.png");
		this.panel = new Image(this.display, this.texturePath + "panel.png");
		this.life = new Image(this.display, this.texturePath + "life.png");
		this.bomb = new Image(this.display, this.texturePath + "bomb.png");
		this.menu = new ArrayList<Image>();
		this.menu.add(Engine.MENU_CONTINUE, new Image(this.display, this.texturePath + "menu_continue.png"));
		this.menu.add(Engine.MENU_NEW, new Image(this.display, this.texturePath + "menu_new.png"));
		this.menu.add(Engine.MENU_TOP10, new Image(this.display, this.texturePath + "menu_top10.png"));
		this.menu.add(Engine.MENU_HELP, new Image(this.display, this.texturePath + "menu_help.png"));
	}
	
	public void paintControl(PaintEvent pe) {
		pe.gc.setFont(new Font(this.display, "", 10, SWT.BOLD));
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
				pe.gc.drawString("Result - ESC to exit", 50, 50, true);
				break;
			case Engine.STATE_TOP10:
				pe.gc.drawString("Top10 - ESC to exit", 50, 50, true);
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
		gc.setFont(new Font(this.display, "", 8, SWT.BOLD));
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
		Color fg = gc.getForeground();
		int lineWidth = gc.getLineWidth();
		int lineStyle = gc.getLineStyle();
		gc.setLineWidth(1);
		gc.setLineStyle(SWT.LINE_DASH);
		String word = this.engine.gameGetWord();
		Point wordSize = gc.stringExtent(word);
		if(wordSize.x > 244) {
			int i = 0;
			int size = 0;
			while(wordSize.x - size > 244)
				size = gc.stringExtent(word.substring(0, ++i)).x;
			word = word.substring(i, word.length());
			wordSize = gc.stringExtent(word);
		}
		gc.setForeground(this.display.getSystemColor(SWT.COLOR_GRAY));
		gc.drawRectangle(195, 426, 250, wordSize.y + 2);
		gc.setForeground(fg);
		gc.drawString(word, (640 - wordSize.x)/2, 427, true);
		gc.setLineWidth(lineWidth);
		gc.setLineStyle(lineStyle);
	}
	
	private void drawTime(GC gc) {
		long time = this.engine.gameGetTime();
		String timeString = String.format("%1$02d:%2$02d:%3$03d", time / 60000, (time / 1000) % 60, time % 1000);
		gc.drawString(timeString, (Engine.WIDTH - gc.stringExtent(timeString).x)/2, 455, true);
	}
	
	private void drawLevel(GC gc) {
		String levelString = String.format("Poziom: %1$1d", this.engine.gameGetLevel());
		gc.drawString(levelString, Engine.WIDTH - 20 - gc.stringExtent(levelString).x, 431, true);
	}
	
	private void drawScore(GC gc) {
		String scoreString = String.format("Punkty: %1$04d", this.engine.gameGetScore());
		gc.drawString(scoreString, Engine.WIDTH - 20 - gc.stringExtent(scoreString).x, 451, true);
	}
}
