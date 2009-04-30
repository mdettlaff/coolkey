package coolkey;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
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
public class Sound implements Runnable {

	private File soundFile;
	private Clip sound;
	private int durationInMilliseconds;

	/**
	 * Tworzy nowy dźwięk.
	 *
	 * @param  soundFilePath            Ścieżka do pliku dźwiękowego.
	 * @throws LineUnavailableException jeśli karta dźwiękowa jest niedostępna.
	 */
	public Sound(String soundFilePath) throws LineUnavailableException {
		soundFile = new File(soundFilePath);

		try {
			AudioFileFormat aff = AudioSystem.getAudioFileFormat(soundFile);
			durationInMilliseconds = (int) (1000 * aff.getFrameLength()
					/ aff.getFormat().getFrameRate());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}

		initSound();
	}

	private void initSound() throws LineUnavailableException {
		try {
			AudioInputStream audioInputStream =
				AudioSystem.getAudioInputStream(soundFile);
			if (audioInputStream != null) {
				AudioFormat format = audioInputStream.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class, format);
				sound = (Clip) AudioSystem.getLine(info);
				sound.open(audioInputStream);
			} else {
				System.err.println("Sound: nie można odczytać pliku "
						+ soundFile.getName());
			}
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			Clip clip = sound;
			clip.loop(0);
			Thread.sleep(durationInMilliseconds + 1000);
			clip.close();
			clip = null;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Odtwarza dźwięk.
	 */
	public void play() {
		try {
			initSound();
			new Thread(this).start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
