package coolkey.gui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * Okno pomocy programu.
 */
public class Help {
	private final static Color GRAY = new Color(GUI.display, 212, 208, 200);

	private Shell shell;
	private Browser browser;

	public Help() {
		final Image backIconSmall = new Image(GUI.display, "data"
				+ File.separator + "help" + File.separator + "back.png");
		final Image forwardIconSmall = new Image(GUI.display, "data"
				+ File.separator + "help" + File.separator + "forward.png");
		final Image homeIconSmall = new Image(GUI.display, "data"
				+ File.separator + "help" + File.separator + "home.png");
		final String homeUrl =
			"file://" + System.getProperty("user.dir") + File.separator
			+ "data" + File.separator + "help" + File.separator + "index.html";

		shell = new Shell(GUI.display, SWT.DIALOG_TRIM);
		shell.setSize(700, 598);
		shell.setText("Pomoc");
		shell.setLayout(new FillLayout());
		Monitor primary = GUI.display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);

		CTabFolder helpFolder = new CTabFolder(shell, SWT.SINGLE);
		helpFolder.setSimple(true);
		helpFolder.setBorderVisible(true);

		CTabItem helpItem = new CTabItem(helpFolder, SWT.NONE);
		Composite helpComp = new Composite(helpFolder, SWT.NONE);
		FillLayout layout = new FillLayout();
		layout.marginHeight = -2;
		layout.marginWidth = -2;
		helpComp.setLayout(layout);
		if (System.getProperty("os.name").equalsIgnoreCase("Linux")) {
			browser = new Browser(helpComp, SWT.MOZILLA);
		} else {
			browser = new Browser(helpComp, SWT.NONE);
		}
		browser.setUrl(homeUrl);
		helpItem.setControl(helpComp);
		helpFolder.setSelection(helpItem);
		helpFolder.setSelectionBackground(new Color[] {GRAY, GRAY, GRAY, GRAY},
				new int[] {20, 40, 100}, true);

		ToolBar browseToolBar = new ToolBar(helpFolder, SWT.FLAT );
		ToolItem goBack = new ToolItem(browseToolBar, SWT.PUSH );
		goBack.setImage(backIconSmall);
		goBack.setToolTipText("Wstecz");
		ToolItem goForward = new ToolItem(browseToolBar, SWT.PUSH );
		goForward.setImage(forwardIconSmall);
		goForward.setToolTipText("Dalej");
		ToolItem home = new ToolItem(browseToolBar, SWT.PUSH );
		home.setImage(homeIconSmall);
		home.setToolTipText("Strona główna");
		browseToolBar.pack();
		helpFolder.setTabHeight(Math.max(
				browseToolBar.computeSize(SWT.DEFAULT,	SWT.DEFAULT).y,
				helpFolder.getTabHeight()));
		helpFolder.setTopRight(browseToolBar);

		goBack.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				browser.back();
			}
		});
		goForward.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				browser.forward();
			}
		});
		home.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				browser.setUrl(homeUrl);
			}
		});
	}

	/**
	 * Otwiera okno pomocy.
	 */
	public void open() {
		shell.open();
	}
}
