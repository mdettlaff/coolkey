package coolkey.defender;
/**
 * Zawiera dane bomby.
 */
public class Bomb {
	/**
	 * Słowo opisujące bombę.
	 */
	private String word;
	/**
	 * Współrzędna bomby na osi x.
	 */
	private Double x;
	/**
	 * Współrzędna bomby na osi y.
	 */
	private Double y;
	/**
	 * Aktualny etap wybuchu bomby.
	 */
	private Double explosionStep;
	/**
	 * Szybkość spadania bomby.
	 */
	private double speed;
	
	/**
	 * Tworzy nową bombę.
	 * @param word słowo opisujące bombę
	 * @param x współrzędna na osi x
	 * @param y współrzędna na osi y
	 * @param speed szybkość spadania
	 */
	public Bomb(String word, int x, int y, double speed) {
		this.word = word;
		this.x = new Double(x);
		this.y = new Double(y);
		this.explosionStep = new Double(0.0);
		this.speed = speed;
	}
	/**
	 * Zwraca słowo opisujące bombę.
	 * @return słowo opisujące bombę
	 */
	public String getWord() {
		return this.word;
	}
	/**
	 * Ustawia słowo opisujące bombę.
	 * @param word słowo na które ma być zmienione
	 */
	public void setWord(String word) {
		this.word = word;
	}
	/**
	 * Zwraca współrzędną na osi x.
	 * @return współrzęna na osi x
	 */
	public int getX() {
		return this.x.intValue();
	}
	/**
	 * Ustawia współrzędną na osi x.
	 * @param x współrzęna na osi x
	 */
	public void setX(int x) {
		this.x = new Double(x);
	}
	/**
	 * Dodaję podaną wartość do współrzędnej na osi x.
	 * @param x wartość która będzie dodana
	 */
	public void addX(double x) {
		this.x += x;
	}
	/**
	 * Zwraca współrzędną na osi y.
	 * @return współrzęna na osi y
	 */
	public int getY() {
		return this.y.intValue();
	}
	/**
	 * Ustawia współrzędną na osi y.
	 * @param y współrzęna na osi y
	 */
	public void setY(int y) {
		this.y = new Double(y);
	}
	/**
	 * Dodaję podaną wartość do współrzędnej na osi y.
	 * @param y wartość która będzie dodana
	 */
	public void addY(double y) {
		this.y += y;
	}
	/**
	 * Zwraca szybkość spadania bomby.
	 * @return szybkość spadania bomby
	 */
	public double getSpeed() {
		return this.speed;
	}
	/**
	 * Ustawia szybkość spadania bomby.
	 * @param speed szybkość spadania bomby
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	/**
	 * Zwraca aktualny etap wybuchu bomby.
	 * @return aktualny etap wybuchu bomby
	 */
	public int getExplosionStep() {
		return explosionStep.intValue();
	}
	/**
	 * Ustawia aktualny etap wybuchu bomby.
	 * @param explosionStep etap wybuchu bomby
	 */
	public void setExplosionStep(int explosionStep) {
		this.explosionStep = new Double(explosionStep);
	}
	/**
	 * Dodaję podaną wartość do aktualnego etapu wybuchu bomby.
	 * @param explosionStep wartość która będzie dodana
	 */
	public void addExplosionStep(double explosionStep) {
		this.explosionStep += explosionStep;
	}
}
