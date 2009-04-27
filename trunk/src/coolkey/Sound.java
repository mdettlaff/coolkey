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
 * Dźwięk do wielokrotnego odtwarzania.
 */
public class Sound {

	private File soundFile;
	private Clip sound;

	/**
	 * Tworzy nowy dźwięk.
	 *
	 * @param soundFilePath Ścieżka do pliku dźwiękowego.
	 */
	public Sound(String soundFilePath) {
		soundFile = new File(soundFilePath);
		initSound();
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
