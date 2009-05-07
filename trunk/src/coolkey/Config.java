package coolkey;

import java.io.Serializable;

/**
 * Ustawienia programu wybrane przez użytkownika.
 */
public class Config implements Serializable {
	public static final int QWERTY = 0;
	public static final int DVORAK = 1;

	private static final long serialVersionUID = 1L;

	private boolean soundOn;
	private boolean showKeyboard;
	private boolean showGraphs;
	private boolean continueAtMistakes;
	private String lineBreakers;
	private int keyboardLayout;

	public Config() {
		soundOn = true;
		showKeyboard = true;
		showGraphs = true;
		continueAtMistakes = true;
		lineBreakers = "\n";
		keyboardLayout = QWERTY;
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

	public boolean isContinueAtMistakes() {
		return continueAtMistakes;
	}

	public void setContinueAtMistakes(boolean continueAtMistakes) {
		this.continueAtMistakes = continueAtMistakes;
	}

	public String getLineBreakers() {
		return lineBreakers;
	}

	public void setLineBreakers(String lineBreakers) {
		this.lineBreakers = lineBreakers;
	}

	public int getKeyboardLayout() {
		return keyboardLayout;
	}

	public void setKeyboardLayout(int keyboardLayout) {
		this.keyboardLayout = keyboardLayout;
	}
}
