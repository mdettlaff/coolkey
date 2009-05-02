package coolkey.defender;

public class Bomb {
	private String word;
	private Double x;
	private Double y;
	private int speed;
	
	public Bomb(String word, int x, int y, int speed) {
		this.word = word;
		this.x = new Double(x);
		this.y = new Double(y);
		this.speed = speed;
	}

	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getX() {
		return this.x.intValue();
	}

	public void setX(int x) {
		this.x = new Double(x);
	}

	public void addX(double x) {
		this.x = this.x + x;
	}
	
	public int getY() {
		return this.y.intValue();
	}

	public void setY(int y) {
		this.y = new Double(y);
	}

	public void addY(double y) {
		this.y = this.y + y;
	}
	
	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
