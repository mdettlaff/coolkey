package coolkey;

import java.io.Serializable;

/**
 * Ustawienia programu wybrane przez użytkownika.
 */
public class Config implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean soundOn;
	private boolean showKeyboard;
	private boolean showGraphs;
	private boolean stopAtMistakes;
	private String lineBreakers;

	public Config() {
		soundOn = true;
		showKeyboard = true;
		showGraphs = true;
		stopAtMistakes = false;
		lineBreakers = "\n";
	}

	public boolean isSoundOn() {
		return soundOn;
	}

	public void setSoundOn(boolean soundOn) {
		this.soundOn = soundOn;
	}

	public boolean isShowKeyboard() {
		return showKeyboard;
	}

	public void setShowKeyboard(boolean showKeyboard) {
		this.showKeyboard = showKeyboard;
	}

	public boolean isShowGraphs() {
		return showGraphs;
	}

	public void setShowGraphs(boolean showGraphs) {
		this.showGraphs = showGraphs;
	}

	public boolean isStopAtMistakes() {
		return stopAtMistakes;
	}

	public void setStopAtMistakes(boolean stopAtMistakes) {
		this.stopAtMistakes = stopAtMistakes;
	}

	public String getLineBreakers() {
		return lineBreakers;
	}

	public void setLineBreakers(String lineBreakers) {
		this.lineBreakers = lineBreakers;
	}
}
