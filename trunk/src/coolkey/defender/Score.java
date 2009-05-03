package coolkey.defender;

public class Score {
	private int score;
	private long time;
	private int level;
	
	public Score(int score, long time, int level) {
		this.score = score;
		this.time = time;
		this.level = level;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getTime() {
		return this.time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
