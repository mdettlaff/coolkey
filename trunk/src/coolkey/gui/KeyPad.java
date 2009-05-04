package coolkey.gui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;

import coolkey.CoolKey;

/**
 * Generuje klawisze klawiatury.
 * @author kd
 *
 */

public class KeyPad {
	
	private static Map<Integer, Button> keys = new HashMap<Integer, Button>();
	
	public static void qwerty(Canvas canvas) {
		
		int width = 40;
		int height = 45;
		int x = 3;
		int y = 3;
		
		Button but96 = new Button(canvas, SWT.PUSH);
		but96.setText("~\n`");
		but96.setBounds(x, y, width, height);
		keys.put(96, but96);
		x += 43;
		
		Button but49 = new Button(canvas, SWT.PUSH);
		but49.setText("!\n1");
		but49.setBounds(x, y, width, height);
		keys.put(49, but49);
		x += 43;
		
		Button but50 = new Button(canvas, SWT.PUSH);
		but50.setText("@\n2");
		but50.setBounds(x, y, width, height);
		keys.put(50, but50);
		x += 43;
		
		Button but51 = new Button(canvas, SWT.PUSH);
		but51.setText("#\n3");
		but51.setBounds(x, y, width, height);
		keys.put(51, but51);
		x += 43;
		
		Button but52 = new Button(canvas, SWT.PUSH);
		but52.setText("$\n4");
		but52.setBounds(x, y, width, height);
		keys.put(52, but52);
		x += 43;
		
		Button but53 = new Button(canvas, SWT.PUSH);
		but53.setText("%\n5");
		but53.setBounds(x, y, width, height);
		keys.put(53, but53);
		x += 43;
		
		Button but54 = new Button(canvas, SWT.PUSH);
		but54.setText("^\n6");
		but54.setBounds(x, y, width, height);
		keys.put(54, but54);
		x += 43;
		
		Button but55 = new Button(canvas, SWT.PUSH);
		but55.setText("&\n7");
		but55.setBounds(x, y, width, height);
		keys.put(55, but55);
		x += 43;
		
		Button but56 = new Button(canvas, SWT.PUSH);
		but56.setText("*\n8");
		but56.setBounds(x, y, width, height);
		keys.put(56, but56);
		x += 43;
		
		Button but57 = new Button(canvas, SWT.PUSH);
		but57.setText("(\n9");
		but57.setBounds(x, y, width, height);
		keys.put(57, but57);
		x += 43;
		
		Button but48 = new Button(canvas, SWT.PUSH);
		but48.setText(")\n0");
		but48.setBounds(x, y, width, height);
		keys.put(48, but48);
		x += 43;
		
		Button but45 = new Button(canvas, SWT.PUSH);
		but45.setText("_\n-");
		but45.setBounds(x, y, width, height);
		keys.put(45, but45);
		x += 43;
		
		Button but61 = new Button(canvas, SWT.PUSH);
		but61.setText("+\n=");
		but61.setBounds(x, y, width, height);
		keys.put(61, but61);
		x += 43;
		
		Button but8 = new Button(canvas, SWT.PUSH);
		but8.setText("Bckspc");
		but8.setBounds(x, y, 96, height);
		keys.put(8, but8);
		
		x = 3;
		y += 48;
		
		Button but9 = new Button(canvas, SWT.PUSH);
		but9.setText("Tab");
		but9.setBounds(x, y, 75, height);
		keys.put(9, but9);
		x += 78;
		
		Button but113 = new Button(canvas, SWT.PUSH);
		but113.setText("Q");
		but113.setBounds(x, y, width, height);
		keys.put(113, but113);
		x += 43;
		
		Button but119 = new Button(canvas, SWT.PUSH);
		but119.setText("W");
		but119.setBounds(x, y, width, height);
		keys.put(119, but119);
		x += 43;
		
		Button but101 = new Button(canvas, SWT.PUSH);
		but101.setText("E");
		but101.setBounds(x, y, width, height);
		keys.put(101, but101);
		x += 43;
		
		Button but114 = new Button(canvas, SWT.PUSH);
		but114.setText("R");
		but114.setBounds(x, y, width, height);
		keys.put(114, but114);
		x += 43;
		
		Button but116 = new Button(canvas, SWT.PUSH);
		but116.setText("T");
		but116.setBounds(x, y, width, height);
		keys.put(116, but116);
		x += 43;
		
		Button but121 = new Button(canvas, SWT.PUSH);
		but121.setText("Y");
		but121.setBounds(x, y, width, height);
		keys.put(121, but121);
		x += 43;
		
		Button but117 = new Button(canvas, SWT.PUSH);
		but117.setText("U");
		but117.setBounds(x, y, width, height);
		keys.put(117, but117);
		x += 43;
		
		Button but105 = new Button(canvas, SWT.PUSH);
		but105.setText("I");
		but105.setBounds(x, y, width, height);
		keys.put(105, but105);
		x += 43;
		
		Button but111 = new Button(canvas, SWT.PUSH);
		but111.setText("O");
		but111.setBounds(x, y, width, height);
		keys.put(111, but111);
		x += 43;
		
		Button but112 = new Button(canvas, SWT.PUSH);
		but112.setText("P");
		but112.setBounds(x, y, width, height);
		keys.put(112, but112);
		x += 43;
		
		Button but91 = new Button(canvas, SWT.PUSH);
		but91.setText("{\n[");
		but91.setBounds(x, y, width, height);
		keys.put(91, but91);
		x += 43;
		
		Button but93 = new Button(canvas, SWT.PUSH);
		but93.setText("}\n]");
		but93.setBounds(x, y, width, height);
		keys.put(93, but93);
		x += 43;
		
		Button but92 = new Button(canvas, SWT.PUSH);
		but92.setText("|\t\n\\\t");
		but92.setBounds(x, y, 61, height);
		keys.put(92, but92);
		
		x = 3;
		y += 48;
		
		Button but20 = new Button(canvas, SWT.PUSH);
		but20.setText("CapsLock");
		but20.setBounds(x, y, 85, height);
		keys.put(20, but20);
		x += 88;
		
		Button but97 = new Button(canvas, SWT.PUSH);
		but97.setText("A");
		but97.setBounds(x, y, width, height);
		keys.put(97, but97);
		x += 43;
		
		Button but115 = new Button(canvas, SWT.PUSH);
		but115.setText("S");
		but115.setBounds(x, y, width, height);
		keys.put(115, but115);
		x += 43;
		
		Button but100 = new Button(canvas, SWT.PUSH);
		but100.setText("D");
		but100.setBounds(x, y, width, height);
		keys.put(100, but100);
		x += 43;
		
		Button but102 = new Button(canvas, SWT.PUSH);
		but102.setText("F");
		but102.setBounds(x, y, width, height);
		keys.put(102, but102);
		x += 43;
		
		Button but103 = new Button(canvas, SWT.PUSH);
		but103.setText("G");
		but103.setBounds(x, y, width, height);
		keys.put(103, but103);
		x += 43;
		
		Button but104 = new Button(canvas, SWT.PUSH);
		but104.setText("H");
		but104.setBounds(x, y, width, height);
		keys.put(104, but104);
		x += 43;
		
		Button but106 = new Button(canvas, SWT.PUSH);
		but106.setText("J");
		but106.setBounds(x, y, width, height);
		keys.put(106, but106);
		x += 43;
		
		Button but107 = new Button(canvas, SWT.PUSH);
		but107.setText("K");
		but107.setBounds(x, y, width, height);
		keys.put(107, but107);
		x += 43;
		
		Button but108 = new Button(canvas, SWT.PUSH);
		but108.setText("L");
		but108.setBounds(x, y, width, height);
		keys.put(108, but108);
		x += 43;
		
		Button but59 = new Button(canvas, SWT.PUSH);
		but59.setText(":\n;");
		but59.setBounds(x, y, width, height);
		keys.put(59, but59);
		x += 43;
		
		Button but39 = new Button(canvas, SWT.PUSH);
		but39.setText("\"\n'");
		but39.setBounds(x, y, width, height);
		keys.put(39, but39);
		x += 43;
		
		Button but13 = new Button(canvas, SWT.PUSH);
		but13.setText("Enter");
		but13.setBounds(x, y, 94, height);
		keys.put(13, but13);
		
		x = 3;
		y += 48;
		
		Button but16 = new Button(canvas, SWT.PUSH);	//shift 1 keyCode = 16
		but16.setText("Shift");
		but16.setBounds(x, y, 102, height);
		keys.put(16, but16);
		x += 105;
		
		Button but122 = new Button(canvas, SWT.PUSH);
		but122.setText("Z");
		but122.setBounds(x, y, width, height);
		keys.put(122, but122);
		x += 43;
		
		Button but120 = new Button(canvas, SWT.PUSH);
		but120.setText("X");
		but120.setBounds(x, y, width, height);
		keys.put(120, but120);
		x += 43;
		
		Button but99 = new Button(canvas, SWT.PUSH);
		but99.setText("C");
		but99.setBounds(x, y, width, height);
		keys.put(99, but99);
		x += 43;
		
		Button but118 = new Button(canvas, SWT.PUSH);
		but118.setText("V");
		but118.setBounds(x, y, width, height);
		keys.put(118, but118);
		x += 43;
		
		Button but98 = new Button(canvas, SWT.PUSH);
		but98.setText("B");
		but98.setBounds(x, y, width, height);
		keys.put(98, but98);
		x += 43;
		
		Button but110 = new Button(canvas, SWT.PUSH);
		but110.setText("N");
		but110.setBounds(x, y, width, height);
		keys.put(110, but110);
		x += 43;
		
		Button but109 = new Button(canvas, SWT.PUSH);
		but109.setText("M");
		but109.setBounds(x, y, width, height);
		keys.put(109, but109);
		x += 43;
		
		Button but44 = new Button(canvas, SWT.PUSH);
		but44.setText("<\n,");
		but44.setBounds(x, y, width, height);
		keys.put(44, but44);
		x += 43;
		
		Button but46 = new Button(canvas, SWT.PUSH);
		but46.setText(">\n.");
		but46.setBounds(x, y, width, height);
		keys.put(46, but46);
		x += 43;
		
		Button but47 = new Button(canvas, SWT.PUSH);
		but47.setText("?\n/");
		but47.setBounds(x, y, width, height);
		keys.put(47, but47);
		x += 43;
		
		Button but18 = new Button(canvas, SWT.PUSH);	//Shift prawy keyCode = 18
		but18.setText("Shift");
		but18.setBounds(x, y, 120, height);
		keys.put(18, but18);
		
		x = 3;
		y += 48;
		
		Button but17 = new Button(canvas, SWT.PUSH);	//Ctrl lewy keycode = 17
		but17.setText("Ctrl");
		but17.setBounds(x, y, 55, height);
		keys.put(17, but17);
		x += 58;
		
		Button butWin1 = new Button(canvas, SWT.PUSH);	//Win lewy
		butWin1.setText(" ");
		butWin1.setBounds(x, y, 55, height);
		//keys.put(win, butWin);
		x += 58;
		
		Button but0 = new Button(canvas, SWT.PUSH);		//Alt lewy
		but0.setText("Alt");
		but0.setBounds(x, y, 55, height);
		keys.put(0, but0);
		x += 58;
		
		Button but32 = new Button(canvas, SWT.PUSH);
		but32.setText(" ");
		but32.setBounds(x, y, 250, height);
		keys.put(32, but32);
		x += 253;
		
		Button but1 = new Button(canvas, SWT.PUSH);		//Alt prawy
		but1.setText("Alt");
		but1.setBounds(x, y, 55, height);
		keys.put(1, but1);
		x += 58;
		
		Button butWin2 = new Button(canvas, SWT.PUSH);	//Win lPrawy
		butWin2.setText(" ");
		butWin2.setBounds(x, y, 55, height);
		//keys.put(win, butWin);
		x += 58;
		
		Button butFun = new Button(canvas, SWT.PUSH);	//funkcyjny
		butFun.setText(" ");
		butFun.setBounds(x, y, 55, height);
		//keys.put(win, butWin);
		x += 58;
		
		Button but14 = new Button(canvas, SWT.PUSH);	//Ctrl prawy keyCode = 14
		but14.setText("Ctrl");
		but14.setBounds(x, y, 55, height);
		keys.put(14, but14);

		
	}
	
	public static void setFocus(int keyCode) {
		Button but = keys.get((int)keyCode);
		if (but == null) {
			System.out.println("nie ma przycisku o kodzie: " + keyCode);
			return;
		}
		if (CoolKey.getCurrentLesson().isMistakeMade()) {
			but.setBackground(GUI.display.getSystemColor(SWT.COLOR_RED));
		} else {
			but.setBackground(GUI.display.getSystemColor(SWT.COLOR_CYAN));
		}
	}
	public static void removeFocus(int keyCode) {
		Button but = keys.get((int)keyCode);
		if (but == null) {
			System.out.println("nie ma przycisku o kodzie: " + keyCode);
			return;
		}
		but.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
	}
	/*public static void setFocusNext(char keyCode) {
		for (Button but : allButtons) {
			if (but.getText().charAt(0) == keyCode) {
				but.setBackground(GUI.display.getSystemColor(SWT.COLOR_GRAY));
			}
		}
	}*/
}
