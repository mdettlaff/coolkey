package coolkey.defender;

import java.io.File;
import java.text.NumberFormat;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Paint implements PaintListener {
	private Display dispaly;
	private Engine engine;
	private Image buffer;
	private GC gc;
	private Image bg;
	private Image logo;
	private Menu menu;
	
	public Paint(Display dispaly, Engine engine) {
		this.dispaly = dispaly;
		this.engine = engine;
		this.buffer = new Image(this.dispaly, Engine.WIDTH, Engine.HEIGHT);
		this.gc = new GC(this.buffer);
		String texturePath = "data" + File.separator + "texture" + File.separator;
		this.bg = new Image(this.dispaly, texturePath + "background.png");
		this.logo = new Image(this.dispaly, texturePath + "logo.png");
		this.menu = new Menu(this.dispaly, texturePath);
	}
	
	public void paintControl(PaintEvent pe) {
		gc.drawImage(this.bg, 0, 0);
		switch(this.engine.getState()) {
			case Engine.STATE_MENU:
				gc.drawImage(this.logo, 80, 20);
				if(!this.engine.isNewGame())
					drawMenuItem(Engine.MENU_CONTINUE);
				drawMenuItem(Engine.MENU_NEW);
				drawMenuItem(Engine.MENU_TOP10);
				drawMenuItem(Engine.MENU_HELP);
				break;
			case Engine.STATE_GAME:
				break;
			case Engine.STATE_RESULT:
				break;
			case Engine.STATE_TOP10:
				break;
			case Engine.STATE_HELP:
				break;
		}
		if(this.engine.showFps()) {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			gc.setForeground(this.dispaly.getSystemColor(SWT.COLOR_WHITE));
			gc.drawString("Fps: " + nf.format(this.engine.getFps()), 5, 5, true);
		}
		pe.gc.drawImage(this.buffer, 0, 0);
	}
	
	private void drawMenuItem(int menuID) {
		MenuItem mi = this.menu.getMenu(menuID);
		if(mi.getMenuID() == this.engine.getMenuSelectId())
			gc.drawImage(mi.getImgSelect(), mi.getX(), mi.getY());
		gc.drawImage(mi.getImg(), mi.getX(), mi.getY());
	}
}
