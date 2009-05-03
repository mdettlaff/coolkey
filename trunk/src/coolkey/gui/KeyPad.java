package coolkey.gui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;

/**
 * Generuje klawisze klawiatury.
 * @author kd
 *
 */

public class KeyPad {
	private static String[] num = {"~\n`", "!\n1", "@\n2", "#\n3", "$\n4", "%\n5", "^\n6", "&\n7", "*\n8", "(\n9", ")\n0", "_\n-", "+\n=", "BckSpc"};
	private static String[] qwerty = {"tab", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "{\n[", "}\n]", "|\n\\"};
	private static String[] asd = {"CapsLock", "a", "s", "d", "f", "g", "h", "j", "k", "l", ":\n;", "\"\n'", "Enter"};
	private static String[] zxc = {"Shift", "z", "x", "c", "v", "b", "n", "m", "<\n,", ">\n.", "?\n/", "Shift"};
	private static String[] spacebar = {"Ctrl", " ", "Alt", "spacebar", "Alt", " ", " ", "Ctrl"};
	private static List<Button> qwertyList = new ArrayList<Button>();
	private static List<Button> numList = new ArrayList<Button>();
	private static List<Button> asdList = new ArrayList<Button>();
	private static List<Button> zxcList = new ArrayList<Button>();
	private static List<Button> spacebarList = new ArrayList<Button>();
	private static List<Button> allButtons= new ArrayList<Button>();
	
	public static void qwerty(Canvas canvas) {
		
		for (String c : num) {
			Button but = new Button(canvas, SWT.PUSH);
			but.setText(c);
			numList.add(but);
		}
		for (String c : qwerty) {
			Button but = new Button(canvas, SWT.PUSH);
			but.setText(c);
			qwertyList.add(but);
		}
		for (String c : asd) {
			Button but = new Button(canvas, SWT.PUSH);
			but.setText(c);
			asdList.add(but);
		}
		for (String c : zxc) {
			Button but = new Button(canvas, SWT.PUSH);
			but.setText(c);
			zxcList.add(but);
		}
		for (String c : spacebar) {
			Button but = new Button(canvas, SWT.PUSH);
			but.setText(c);
			spacebarList.add(but);
		}
		int x = 40;
		int y = 3;
		int width = 40;
		int height = 45;
		int spacer = 42;
		
		for (Button but : numList) {
			if (but.getText().equals("BckSpc")) {
				but.setBounds(x, y, 80, height);
				continue;
			}
			but.setBounds(x, y, width, height);
			x += spacer;
		}
		x = 47;
		y = 51;
		for (Button but : qwertyList) {
			if (but.getText().equals("tab")) {
				but.setBounds(x, y, 60, height);
				x += 65;
				continue;
			}
			but.setBounds(x, y, width, height);
			x += spacer;
		}
		x = 40;
		y = 99;
		for (Button but : asdList) {
			if (but.getText().equals("Enter") || but.getText().equals("CapsLock")) {
				but.setBounds(x, y, 80, height);
				x += 85;
				continue;
			}
			but.setBounds(x, y, width, height);
			x += spacer;
		}
		x = 47;
		y = 147;
		for (Button but : zxcList) {
			if (but.getText().equals("Shift")) {
				but.setBounds(x, y, 95, height);
				x += 100;
				continue;
			}
			but.setBounds(x, y, width, height);
			x += spacer;
		}
		x = 4;
		y = 195;
		for (Button but : spacebarList) {
			if (but.getText().equals("spacebar")) {
				but.setBounds(x, y, 300, height);
				but.setText("");
				x += 305;
				continue;
			}
			but.setBounds(x, y, 55, height);
			x += 57;
		}
		allButtons.addAll(numList);
		allButtons.addAll(qwertyList);
		allButtons.addAll(asdList);
		allButtons.addAll(zxcList);
		allButtons.addAll(spacebarList);
	}
	
	public static void setFocus(char keyCode) {
		for (Button but : allButtons) {
			/*if (keyCode == '\r' && but.getText() == "Enter") {
				but.setBackground(GUI.display.getSystemColor(SWT.COLOR_CYAN));
				return;
			}*/
			System.out.println(String.valueOf(keyCode));
			if (but.getText().charAt(0) == keyCode) {
				but.setBackground(GUI.display.getSystemColor(SWT.COLOR_CYAN));
				return;
			}
		}
	}
	public static void removeFocus(char keyCode) {
		for (Button but : allButtons) {
			/*if (keyCode == '\r' && but.getText() == "Enter") {
				but.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
				return;
			}*/
			if (but.getText().charAt(0) == keyCode) {
				but.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
			}
		}
	}
	/*public static void setFocusNext(char keyCode) {
		for (Button but : allButtons) {
			if (but.getText().charAt(0) == keyCode) {
				but.setBackground(GUI.display.getSystemColor(SWT.COLOR_GRAY));
			}
		}
	}*/
}
