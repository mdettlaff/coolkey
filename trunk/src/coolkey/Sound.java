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
	private Clip[] clipPool = new Clip[16];

	/**
	 * Tworzy nowy dźwięk.
	 *
	 * @param  soundFilePath            Ścieżka do pliku dźwiękowego.
	 * @throws LineUnavailableException jeśli karta dźwiękowa jest niedostępna.
	 */
	public Sound(String soundFilePath) throws LineUnavailableException {
		soundFile = new File(soundFilePath);
		try {
			for (int i=0; i < clipPool.length; i++) {
				AudioInputStream audioInputStream =
					AudioSystem.getAudioInputStream(soundFile);
				if (audioInputStream != null) {
					AudioFormat format = audioInputStream.getFormat();
					DataLine.Info info = new DataLine.Info(Clip.class, format);
					clipPool[i] = (Clip) AudioSystem.getLine(info);
					clipPool[i].open(audioInputStream);
				} else {
					System.err.println("Sound: nie można odczytać pliku "
							+ soundFile.getName());
				}
			}
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Odtwarza dźwięk.
	 */
	public void play() {
		for (int i=0; i < clipPool.length; i++) {
			if (!clipPool[i].isRunning()) {
				clipPool[i].setFramePosition(0);
				clipPool[i].loop(0);
				break;
			}
		}
	}
}
