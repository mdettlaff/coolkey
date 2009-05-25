package coolkey.defender;

import java.io.Serializable;
/**
 * Klasa przechowująca wynik zakończonej gry.
 */
public class Score implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Liczba zdobytych punktów.
	 */
	private int score;
	/**
	 * Czas trwania gry.
	 */
	private long time;
	/**
	 * Poziom na którym skończył gracz.
	 */
	private int level;
	
	/**
	 * Tworzy nowy wynik gry.
	 * @param score liczba zdobytych punktów
	 * @param time czas trwania gry
	 * @param level poziom na którym skończył gracz
	 */
	public Score(int score, long time, int level) {
		this.score = score;
		this.time = time;
		this.level = level;
	}
	/**
	 * Zwraca liczbę zdobytych punktów.
	 * @return liczba punktów
	 */
	public int getScore() {
		return this.score;
	}
	/**
	 * Ustawia liczbę zdobytych punktów.
	 * @param score liczba punktów
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * Zwraca czas trwania gry.
	 * @return czas trwania gry
	 */
	public long getTime() {
		return this.time;
	}
	/**
	 * Ustawia czas trwania gry.
	 * @param time czas trwania gry
	 */
	public void setTime(long time) {
		this.time = time;
	}
	/**
	 * Zwraca poziom na którym skończył gracz.
	 * @return poziom na którym skończył gracz
	 */
	public int getLevel() {
		return this.level;
	}
	/**
	 * Ustawia poziom na którym skończył gracz.
	 * @param level poziom na którym skończył gracz
	 */
	public void setLevel(int level) {
		this.level = level;
	}
}
