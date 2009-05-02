package coolkey.defender;

import org.eclipse.swt.graphics.Image;

public class MenuItem {
	private int menuID;
	private int xLocation;
	private int yLocation;
	private	 Image img;
	
	public MenuItem(int menuId, int xLocation, int yLocation, Image img) {
		this.menuID = menuId;
		this.xLocation = xLocation;
		this.yLocation = yLocation;
		this.img = img;
	}

	public int getXLocation() {
		return this.xLocation;
	}

	public void setXLocation(int xLocation) {
		this.xLocation = xLocation;
	}

	public int getYLocation() {
		return this.yLocation;
	}

	public void setYLocation(int yLocation) {
		this.yLocation = yLocation;
	}
	
	public Image getImg() {
		return this.img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getMenuID() {
		return this.menuID;
	}
	
	public int getImgX() {
		return 0;
	}
	
	public int getImgY() {
		return 0;
	}
	
	public int getImgSelectX() {
		return this.img.getBounds().width / 2;
	}
	
	public int getImgSelectY() {
		return 0;
	}
	
	public int getImgWidth() {
		return this.img.getBounds().width / 2;
	}
	
	public int getImgHeight() {
		return this.img.getBounds().height;
	}
}
