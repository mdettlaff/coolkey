package coolkey.gui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;

import coolkey.Config;
import coolkey.CoolKey;

/**
 * Wizualizacja klawiatury, z podświetleniem następnego klawisza.
 */
public class Keyboard {
	private final int CANVAS_HEIGHT = 186;
	private final int KEY_WIDTH = 37;
	private final int KEY_HEIGHT = KEY_WIDTH;
	private final int KEY_X_PADDING = 8;
	private final int KEY_Y_PADDING = 5;
	private final int KEY_Y_TOP_PADDING = 3;
	private final int KEY_Y_BOTTOM_PADDING = 18;
	private final Point MARGIN = new Point(1, 0);
	private final int[] ROWS_X_MARGINS = { MARGIN.x, MARGIN.x + 55,
			MARGIN.x + 64, MARGIN.x + 83 };
	private final int[] ROWS_Y_MARGINS = { MARGIN.y, MARGIN.y + KEY_HEIGHT,
			MARGIN.y + KEY_HEIGHT * 2, MARGIN.y + KEY_HEIGHT * 3 };
	private final Point LEFT_SHIFT_COORDS = new Point(0, 111);
	private final Point RIGHT_SHIFT_COORDS = new Point(453, 111);
	private final Point SPACE_COORDS = new Point(148, 148);
	private final Point ENTER_COORDS = new Point(471, 74);
	private final Point ALT_GR_COORDS = new Point(370, 148);
	private final String[] QWERTY = {
	        "~`!1@2#3$4%5^6&7*8(9)0_-+=",
	        "qwertyuiop{[}]|\\",
	        "asdfghjkl:;\"'",
	        "zxcvbnm<,>.?/"
	};
	private final String[] Dvorak = {
	        "~`!1@2#3$4%5^6&7*8(9)0{[}]",
	        "\"'<,>.pyfgcrl?/+=|\\",
	            "aoeuidhtns_-",
	           ":;qjkxbmwvz"
	};
	private final String POLISH_CHARS = "ĄąĆćĘęŁłŃńÓóŚśŻżŹź";
	private final String POLISH2LATIN_CHARS = "AaCcEeLlNnOoSsZzZz";
	private final Canvas canvas;
	private final Image blankKeyboard;
	private final Image keyHighlighted;
	private final Image keyMistake;
	private final Image leftShiftHighlighted;
	private final Image leftShiftMistake;
	private final Image rightShiftHighlighted;
	private final Image rightShiftMistake;
	private final Image enterHighlighted;
	private final Image enterMistake;
	private final Image spaceHighlighted;
	private final Image spaceMistake;
	private final Image altGrHighlighted;
	private final Image altGrMistake;
	private final Image keyLongHighlighted;
	private final Image keyLongMistake;

	private Character nextChar;
	private boolean leftHand;

	public Keyboard() {
		canvas = new Canvas(GUI.shell, SWT.BORDER | SWT.DOUBLE_BUFFERED);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd.heightHint = CANVAS_HEIGHT;
		canvas.setLayoutData(gd);
		blankKeyboard = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "keyboard.png");
		keyHighlighted = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "key_highlighted.png");
		keyMistake = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "key_mistake.png");
		leftShiftHighlighted = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "left_shift_highlighted.png");
		leftShiftMistake = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "left_shift_mistake.png");
		rightShiftHighlighted = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "right_shift_highlighted.png");
		rightShiftMistake = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "right_shift_mistake.png");
		enterHighlighted = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "enter_highlighted.png");
		enterMistake = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "enter_mistake.png");
		spaceHighlighted = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "space_highlighted.png");
		spaceMistake = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "space_mistake.png");
		altGrHighlighted = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "altgr_highlighted.png");
		altGrMistake = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "altgr_mistake.png");
		keyLongHighlighted = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "key_long_highlighted.png");
		keyLongMistake = new Image(GUI.display, "data" + File.separator
				+ "images" + File.separator + "key_long_mistake.png");

		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent pe) {
				GC gc = pe.gc;
				Point canvasSize = canvas.getSize();
				gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLACK));
				gc.fillRectangle(0, 0, canvasSize.x, canvasSize.y); // tło
				gc.drawImage(blankKeyboard, 0, 0);
				String[] layout;
				if (CoolKey.getUser().getConfig().getKeyboardLayout()
						== Config.QWERTY) {
					layout = QWERTY;
				} else {
					layout = Dvorak;
				}
				boolean isShiftCharacter = false;
				// podświetl zwykłe klawisze z układu klawiatury
				for (int j=0; j < layout.length; j++) {
					int keyFromLeft = 0; // który z kolei klawisz od lewej
					int x = ROWS_X_MARGINS[j];
					for (int i=0; i < layout[j].length(); i++) {
						char c = layout[j].charAt(i);
						char keyChar = nextChar;
						int y = ROWS_Y_MARGINS[j];
						if (Character.isLetter(c)) { // podświetlanie liter
							if (POLISH_CHARS.indexOf(nextChar) != -1) {
								keyChar = POLISH2LATIN_CHARS.charAt(
										POLISH_CHARS.indexOf(nextChar));
							}
							if (c == keyChar
									|| Character.toUpperCase(c) == keyChar) {
								if (CoolKey.getCurrentLesson().isMistakeMade()) {
									gc.drawImage(keyMistake, x, y);
								} else {
									gc.drawImage(keyHighlighted, x, y);
								}
								leftHand = j > 0 ? keyFromLeft < 5
										: keyFromLeft < 7;
								isShiftCharacter = Character.isUpperCase(nextChar);
							}
							gc.drawString("" + Character.toUpperCase(c),
									x + KEY_X_PADDING, y + KEY_Y_PADDING, true);
						} else { // podświetlanie znaków specjalnych
							if (c == keyChar
									|| layout[j].charAt(i+1) == keyChar) {
								if (c == '|') {
									if (CoolKey.getCurrentLesson().isMistakeMade()) {
										gc.drawImage(keyLongMistake, x, y);
									} else {
										gc.drawImage(keyLongHighlighted, x, y);
									}
								} else {
									if (CoolKey.getCurrentLesson().isMistakeMade()) {
										gc.drawImage(keyMistake, x, y);
									} else {
										gc.drawImage(keyHighlighted, x, y);
									}
								}
								leftHand = j > 0 ? keyFromLeft < 5
										: keyFromLeft < 7;
								isShiftCharacter = c == keyChar;
							}
							gc.drawString("" + c, x + KEY_X_PADDING,
									y + KEY_Y_TOP_PADDING, true);
							i++;
							c = layout[j].charAt(i);
							gc.drawString("" + c, x + KEY_X_PADDING,
									y + KEY_Y_BOTTOM_PADDING, true);
						}
						x += KEY_WIDTH;
						keyFromLeft++;
					}
				}
				// podświetl spację
				if (nextChar == ' ') {
					if (CoolKey.getCurrentLesson().isMistakeMade()) {
						gc.drawImage(spaceMistake, SPACE_COORDS.x, SPACE_COORDS.y);
					} else {
						gc.drawImage(spaceHighlighted, SPACE_COORDS.x, SPACE_COORDS.y);
					}
				}
				// podświetl Alt Gr
				if (POLISH_CHARS.indexOf(nextChar) != -1) {
					if (CoolKey.getCurrentLesson().isMistakeMade()) {
						gc.drawImage(altGrMistake, ALT_GR_COORDS.x, ALT_GR_COORDS.y);
					} else {
						gc.drawImage(altGrHighlighted, ALT_GR_COORDS.x, ALT_GR_COORDS.y);
					}
				}
				// podświetl Shift
				if (isShiftCharacter) {
					if (CoolKey.getCurrentLesson().isMistakeMade()) {
						if (leftHand) {
							gc.drawImage(rightShiftMistake,
									RIGHT_SHIFT_COORDS.x, RIGHT_SHIFT_COORDS.y);
						} else {
							gc.drawImage(leftShiftMistake,
									LEFT_SHIFT_COORDS.x, LEFT_SHIFT_COORDS.y);
						}
					} else {
						if (leftHand) {
							gc.drawImage(rightShiftHighlighted,
									RIGHT_SHIFT_COORDS.x, RIGHT_SHIFT_COORDS.y);
						} else {
							gc.drawImage(leftShiftHighlighted,
									LEFT_SHIFT_COORDS.x, LEFT_SHIFT_COORDS.y);
						}
					}
				}
				// podświetl Enter
				if (nextChar == '\r') {
					if (CoolKey.getCurrentLesson().isMistakeMade()) {
						gc.drawImage(enterMistake, ENTER_COORDS.x, ENTER_COORDS.y);
					} else {
						gc.drawImage(enterHighlighted, ENTER_COORDS.x, ENTER_COORDS.y);
					}
				}
			}
		});

		refresh();
	}

	public void refresh() {
		nextChar = CoolKey.getCurrentLesson().getNextChar();
		canvas.redraw();
	}
}
