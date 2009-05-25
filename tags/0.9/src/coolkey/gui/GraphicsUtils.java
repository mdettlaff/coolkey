package coolkey.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

/**
 * Zawiera metody pomocnicze do rysowania.
 */
public class GraphicsUtils {
	/**
	 * Draws text vertically (rotates plus or minus 90 degrees).
	 * Uses the current font, color, and background.
	 * <dl>
	 * <dt><b>Styles: </b></dt>
	 * <dd>UP, DOWN</dd>
	 * </dl>
	 *
	 * @param string the text to draw
	 * @param x the x coordinate of the top left corner of the drawing rectangle
	 * @param y the y coordinate of the top left corner of the drawing rectangle
	 * @param gc the GC on which to draw the text
	 * @param style the style (SWT.UP or SWT.DOWN)
	 *          <p>
	 *          Note: Only one of the style UP or DOWN may be specified.
	 *          </p>
	 */
	public static void drawVerticalText(String string, int x, int y, GC gc,
			int style) {
		Display display = Display.getCurrent();
		if (display == null) SWT.error(SWT.ERROR_THREAD_INVALID_ACCESS);

		// determine string's dimensions
		Point pt = gc.textExtent(string);
		// create an image the same size as the string
		Image stringImage = new Image(display, pt.x, pt.y);
		// create a GC so we can draw the image
		GC stringGc = new GC(stringImage);

		// set attributes from the original GC to the new GC
		stringGc.setForeground(gc.getForeground());
		stringGc.setBackground(gc.getBackground());
		stringGc.setFont(gc.getFont());

		stringGc.drawText(string, 0, 0);
		drawVerticalImage(stringImage, x, y, gc, style);
		stringGc.dispose();
		stringImage.dispose();
	}

	/**
	 * Draws an image vertically (rotates plus or minus 90 degrees).
	 * <dl>
	 * <dt><b>Styles: </b></dt>
	 * <dd>UP, DOWN</dd>
	 * </dl>
	 *
	 * @param image the image to draw
	 * @param x the x coordinate of the top left corner of the drawing rectangle
	 * @param y the y coordinate of the top left corner of the drawing rectangle
	 * @param gc the GC on which to draw the image
	 * @param style the style (SWT.UP or SWT.DOWN)
	 *          <p>
	 *          Note: Only one of the style UP or DOWN may be specified.
	 *          </p>
	 */
	public static void drawVerticalImage(Image image, int x, int y, GC gc,
			int style) {
		Display display = Display.getCurrent();
		if (display == null) SWT.error(SWT.ERROR_THREAD_INVALID_ACCESS);

		// use the image's data to create a rotated image's data
		ImageData sd = image.getImageData();
		ImageData dd = new ImageData(sd.height, sd.width, sd.depth, sd.palette);
		// determine which way to rotate, depending on up or down
		boolean up = (style & SWT.UP) == SWT.UP;

		// run through the horizontal pixels
		for (int sx = 0; sx < sd.width; sx++) {
			// run through the vertical pixels
			for (int sy = 0; sy < sd.height; sy++) {
				// determine where to move pixel to in destination image data
				int dx = up ? sy : sd.height - sy - 1;
				int dy = up ? sd.width - sx - 1 : sx;
				// swap the x, y source data to y, x in the destination
				dd.setPixel(dx, dy, sd.getPixel(sx, sy));
			}
		}
		Image vertical = new Image(display, dd);
		gc.drawImage(vertical, x, y);
		vertical.dispose();
	}
}
