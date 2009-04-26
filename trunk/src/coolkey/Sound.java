package coolkey;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Efekty dźwiękowe podczas przepisywania.
 */
public class Sound {
	private static final String SND_PATH = "data" + File.separator
			+ "sound" + File.separator;
	private static final String TYPING_SOUND_PATH = SND_PATH + "type.wav";
	private static final String MISTAKE_SOUND_PATH = SND_PATH + "mistake.wav";

	private File soundFile;
	private Clip sound;

	/**
	 * Utwórz nowy dźwięk do odtwarzania.
	 *
	 * @param soundId Identyfikator dźwięku. Do wyboru są:<br>
	 *                <code>0</code> - naciśnięcie klawisza na maszynie
	 *                                 do pisania<br>
	 *                <code>1</code> - błędne przepisanie znaku
	 */
	public Sound(int soundId) {
		if (soundId == 0) {
			soundFile = new File(TYPING_SOUND_PATH);
			initSound();
		} else {
			soundFile = new File(MISTAKE_SOUND_PATH);
		}
	}

	private void initSound() {
		try {
			AudioInputStream audioInputStream =
				AudioSystem.getAudioInputStream(soundFile);
			if (audioInputStream != null) {
				AudioFormat	format = audioInputStream.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class, format);
				sound = (Clip) AudioSystem.getLine(info);
				sound.open(audioInputStream);
			} else {
				System.err.println("ClipPlayer: can't get data from file "
						+ soundFile.getName());
			}
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Odtwarza dźwięk.
	 */
	public void play() {
		initSound();
		sound.loop(0);
	}
}
