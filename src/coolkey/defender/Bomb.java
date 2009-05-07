package coolkey.defender;

public class Bomb {
	private String word;
	private Double x;
	private Double y;
	private Double explosionStep;
	private double speed;
	
	public Bomb(String word, int x, int y, double speed) {
		this.word = word;
		this.x = new Double(x);
		this.y = new Double(y);
		this.explosionStep = new Double(0.0);
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
		this.x += x;
	}
	
	public int getY() {
		return this.y.intValue();
	}

	public void setY(int y) {
		this.y = new Double(y);
	}

	public void addY(double y) {
		this.y += y;
	}
	
	public double getSpeed() {
		return this.speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getExplosionStep() {
		return explosionStep.intValue();
	}

	public void setExplosionStep(int explosionStep) {
		this.explosionStep = new Double(explosionStep);
	}
	
	public void addExplosionStep(double explosionStep) {
		this.explosionStep += explosionStep;
	}
}
