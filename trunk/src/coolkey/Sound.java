package coolkey;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private static final int EXTERNAL_BUFFER_SIZE = 1000;
	private static final String TYPING_SOUND_FILENAME = "data/sound/type.wav";
	private static final String MISTAKE_SOUND_FILENAME = "data/sound/mistake.wav";

	private AudioInputStream audioInputStream;
	private SourceDataLine line;
	private byte[] abData;
	private int	nBytesRead;
	private int nStreamLengthInBytes;

	/**
	 * Utwórz nowy dźwięk do otwarzania.
	 *
	 * @param soundId Identyfikator dźwięku. Do wyboru są:<br>
	 *                <code>0</code> - pisanie na maszynie<br>
	 *                <code>1</code> - dźwięk błędu
	 */
	public Sound(int soundId) {
		try {
			InputStream fileInputStream;
			if (soundId == 0) {
				fileInputStream = new FileInputStream(TYPING_SOUND_FILENAME);
			} else {
				fileInputStream = new FileInputStream(MISTAKE_SOUND_FILENAME);
			}
			InputStream inputStream = new BufferedInputStream(fileInputStream);

			audioInputStream = AudioSystem.getAudioInputStream(inputStream);
			if (!audioInputStream.markSupported()) {
				System.err.println("CoolKey: Sound: resetting not supported");
				System.exit(1);
			}
			AudioFormat	audioFormat = audioInputStream.getFormat();

			DataLine.Info info = new DataLine.Info(SourceDataLine.class,
					audioFormat);
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
			abData = new byte[EXTERNAL_BUFFER_SIZE];
			nBytesRead = 0;
			if (audioInputStream.getFrameLength() == AudioSystem.NOT_SPECIFIED ||
					audioFormat.getFrameSize() == AudioSystem.NOT_SPECIFIED) {
				System.out.println("CoolKey: Sound: cannot calculate length of"
						+ "AudioInputStream!");
				System.exit(1);
			}
			long lStreamLengthInBytes = audioInputStream.getFrameLength()
					* audioFormat.getFrameSize();
			if (lStreamLengthInBytes > Integer.MAX_VALUE) {
				System.err.println("CoolKey: Sound: length of AudioInputStream"
						+ "exceeds 2^31, cannot properly reset stream!");
				System.exit(1);
			}
			nStreamLengthInBytes = (int) lStreamLengthInBytes;

			line.start();

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			audioInputStream.mark(nStreamLengthInBytes);
			nBytesRead = 0;
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0) {
					line.write(abData, 0, nBytesRead);
				}
			}
			audioInputStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		line.drain();
		line.close();		
	}
}
