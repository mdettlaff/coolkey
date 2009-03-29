package coolkey.defender;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Menu {
	private List<MenuItem> menu;
	
	public Menu(Display dispaly, String texturePath) {
		this.menu = new ArrayList<MenuItem>();
		MenuItem menuContinue = new MenuItem(Engine.MENU_CONTINUE, 192, 150,
				new Image(dispaly, texturePath + "menu_continue.png"),
				new Image(dispaly, texturePath + "menu_continue_select.png"));
		MenuItem menuNew = new MenuItem(Engine.MENU_NEW, 192, 214,
				new Image(dispaly, texturePath + "menu_new.png"),
				new Image(dispaly, texturePath + "menu_new_select.png"));
		MenuItem menuTop10 = new MenuItem(Engine.MENU_TOP10, 192, 278,
				new Image(dispaly, texturePath + "menu_top10.png"),
				new Image(dispaly, texturePath + "menu_top10_select.png"));
		MenuItem menuHelp = new MenuItem(Engine.MENU_HELP, 192, 342,
				new Image(dispaly, texturePath + "menu_help.png"),
				new Image(dispaly, texturePath + "menu_help_select.png"));
		this.menu.add(menuContinue.getMenuID(), menuContinue);
		this.menu.add(menuNew.getMenuID(), menuNew);
		this.menu.add(menuTop10.getMenuID(), menuTop10);
		this.menu.add(menuHelp.getMenuID(), menuHelp);
	}

	public List<MenuItem> getMenu() {
		return this.menu;
	}
	
	public MenuItem getMenu(int menuID) {
		return this.menu.get(menuID);
	}
}
