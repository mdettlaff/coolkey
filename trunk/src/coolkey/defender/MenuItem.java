package coolkey.defender;

import org.eclipse.swt.graphics.Image;

public class MenuItem {
	private int menuID;
	private int x;
	private int y;
	private	 Image img;
	private	 Image imgSelect;
	
	public MenuItem(int menuId, int x, int y, Image img, Image imgSelect) {
		this.menuID = menuId;
		this.x = x;
		this.y = y;
		this.img = img;
		this.imgSelect = imgSelect;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Image getImg() {
		return this.img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public Image getImgSelect() {
		return imgSelect;
	}

	public void setImgSelect(Image imgSelect) {
		this.imgSelect = imgSelect;
	}

	public int getMenuID() {
		return this.menuID;
	}
}
