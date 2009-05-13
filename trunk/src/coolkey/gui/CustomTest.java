package coolkey.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import coolkey.CoolKey;
import coolkey.Markov;
import coolkey.TypingTest;
import coolkey.Utils;

/**
 * Okno wyboru testu spersonalizowanego.
 */
public class CustomTest {

	private final int LOWEST_RESULTS_SIZE = 5;

	public CustomTest() {
		final Shell shell = new Shell(GUI.shell, SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL);
		shell.setText("Test spersonalizowany");
		shell.setLayout(new GridLayout(2, false));

		Composite settings = new Composite(shell, SWT.NONE);
		settings.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 2, 1));
		settings.setLayout(new GridLayout(2, false));

		Label minLines = new Label(settings, SWT.NONE);
		minLines.setText("Ilość linii (minimum):");
		final Spinner spinner = new Spinner(settings, SWT.BORDER);
		spinner.setMinimum(1);
		spinner.setMaximum(100);
		spinner.setSelection(10);
		spinner.setIncrement(1);
		spinner.setPageIncrement(5);

		Composite testTypeComp = new Composite(settings, SWT.FILL);
		testTypeComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		testTypeComp.setLayout(new GridLayout());
		Group testType = new Group(testTypeComp, SWT.SHADOW_IN);
		testType.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		testType.setLayout(new GridLayout());
		testType.setText("Rodzaj testu");
		final Button characters = new Button(testType, SWT.RADIO);
		characters.setText("znaki");
		final Button words = new Button(testType, SWT.RADIO);
		words.setText("słowa");
		words.setSelection(true);
		final Button sentences = new Button(testType, SWT.RADIO);
		sentences.setText("zdania");

		Label charsInfo = new Label(settings, SWT.NONE);
		charsInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		charsInfo.setText("Znaki do przećwiczenia:");
		final Text chars = new Text(settings, SWT.BORDER);
		chars.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		chars.setTextLimit(16);
		chars.setText(charsWithLowestSpeeds() != null ?
				Utils.join(charsToImprove(), "") : "");
		new Label(settings, SWT.NONE);

		Label info = new Label(settings, SWT.NONE);
		info.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		info.setText("Najmniejsza prędkość:\n"
				+ (charsWithLowestSpeeds() != null ?
						Utils.join(charsWithLowestSpeeds(), ", ") :
							"(brak danych)") + "\n"
				+ "Najmniejsza poprawność:\n"
				+ (charsWithLowestAccuracies() != null ?
						Utils.join(charsWithLowestAccuracies(), ", ") :
							"(brak danych)"));

		Composite buttons = new Composite(shell, SWT.NONE);
		buttons.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 2, 1));
		buttons.setLayout(new GridLayout(2, false));

		Composite left = new Composite(buttons, SWT.NONE);
		left.setLayout(new GridLayout());
		Composite right = new Composite(buttons, SWT.NONE);
		right.setLayout(new GridLayout());

		Button confirm = new Button(left, SWT.PUSH);
		confirm.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		confirm.setText(" Rozpocznij test ");
		Button cancel = new Button(right, SWT.PUSH);
		cancel.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		cancel.setText(" Anuluj ");

		Listener generate = new Listener() {
			@Override
			public void handleEvent(Event e) {
				Random random = new Random();
				int minGenTextLines = spinner.getSelection();
				String filter = "".equals(chars.getText().trim()) ? null
						: chars.getText();
				if (characters.getSelection()) {
					filter = filter != null ? filter += ' '
						: "qwertyuiopasdfghjklzxcvbnm,." + "     ";
					String text = " ";
					for (int i=0; text.length() < minGenTextLines
							* CoolKey.MAX_CHARS_IN_LINE - 4; i++) {
						text += filter.charAt(random.nextInt(
								text.charAt(i) == ' ' ? filter.length() - 1
										: filter.length()));
					}
					CoolKey.setCurrentTest(new TypingTest(text));
				} else if (words.getSelection()) {
					if (!chars.getText().matches("^\\p{javaLetter}*$")) {
		    			MessageBox messageBox = new MessageBox(GUI.shell, SWT.ICON_ERROR);
		    			messageBox.setText("Generator słów");
		    			messageBox.setMessage("Podane znaki muszą być literami.");
		    			messageBox.open();
		    			return;
					}
					List<String> filteredWords = CoolKey.getDictionary().words(
							filter);
					if (filteredWords.size() == 0) {
		    			MessageBox messageBox = new MessageBox(GUI.shell, SWT.ICON_ERROR);
		    			messageBox.setText("Generator słów");
		    			messageBox.setMessage("Nie znaleziono słów spełniających podane kryteria.");
		    			messageBox.open();
		    			return;
					}
					String text = "";
					while (text.length() < minGenTextLines
							* CoolKey.MAX_CHARS_IN_LINE - 20) {
						text += filteredWords.get(random.nextInt(
								filteredWords.size())) + ' ';
					}
					CoolKey.setCurrentTest(new TypingTest(text));
				} else if (sentences.getSelection()) {
					if (!chars.getText().matches("^\\p{javaLetter}*$")) {
		    			MessageBox messageBox = new MessageBox(GUI.shell, SWT.ICON_ERROR);
		    			messageBox.setText("Generator zdań");
		    			messageBox.setMessage("Podane znaki muszą być literami.");
		    			messageBox.open();
		    			return;
					}
					List<String> filteredWords = Utils.filter(
							Utils.words(CoolKey.TEXT_DIRECTORY), filter);
					int minGenTextLength = (minGenTextLines - 1) * (
							CoolKey.MAX_CHARS_IN_LINE - 1);
					CoolKey.setCurrentTest(new TypingTest(
						Markov.generateMarkovChain(
								filteredWords, minGenTextLength)));
				}
				GUI.refresh();
				shell.dispose();
			}
		};
		confirm.addListener(SWT.Selection, generate);
		spinner.addListener(SWT.DefaultSelection, generate);
		chars.addListener(SWT.DefaultSelection, generate);

		cancel.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				shell.close();
			}
		});

		shell.pack();
		shell.open();
	}

	private List<Character> charsWithLowestSpeeds() {
		return charsWithLowest(
				CoolKey.getUser().getStatistics().getCharSpeeds());
	}

	private List<Character> charsWithLowestAccuracies() {
		return charsWithLowest(
				CoolKey.getUser().getStatistics().getCharAccuracies());
	}

	private List<Character> charsWithLowest(Map<Character, Double> charCriteria) {
		List<Character> charsWithLowest = new ArrayList<Character>();
		List<Double> criteria = new ArrayList<Double>(charCriteria.values());
		if (criteria.size() < LOWEST_RESULTS_SIZE) {
			return null;
		}
		Collections.sort(criteria);
		Double limit = criteria.get(LOWEST_RESULTS_SIZE);
		for (Character c : charCriteria.keySet()) {
			if (charCriteria.get(c) < limit && Character.isLetter(c)) {
				charsWithLowest.add(c);
			}
		}
		return charsWithLowest;
	}

	private Set<Character> charsToImprove() {
		Set<Character> charsToImprove = new TreeSet<Character>();
		charsToImprove.addAll(charsWithLowestSpeeds());
		charsToImprove.addAll(charsWithLowestAccuracies());
		return charsToImprove;
	}
}
