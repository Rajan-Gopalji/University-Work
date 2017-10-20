package drawingTools;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

/**
 * A class to hold a piece of text to be displayed on the canvas.
 * 
 * @author Dr A J Beaumont
 * @version November 2011
 */
public class Text extends Drawable {
	// instance variables - replace the example below with your own
	private String text;
	private Integer fontSize;
	private String fontName;
	private Font font;

	/**
	 * Constructor for objects of class Text.
	 * New Text objects are black and positioned with their bottom left hand corner at (200,100).
	 * 
	 * @param text
	 *            a bit of text.
	 */
	public Text(String text) {
		super(200, 100, "black",null);
		// initialise instance variables
		this.text = text;
		fontSize = 12;
		fontName = "Arial";
		setFont();
		control = new Control(this);
	}

	private void setFont() {
		font = new Font(fontName, Font.PLAIN, fontSize.intValue());
	}

	/**
	 * Change the font size of the text to a given value. The value of newSize
	 * must be >= 0.
	 * 
	 * @param newSize
	 *            the new font size of the text.
	 */
	public void changeSize(Integer newSize) {
		control.erase();
		fontSize = newSize;
		setFont();
		control.draw();
	}

	/**
	 * Print the names of all the available fonts to the console.
	 */
	public void printFonts() {
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		String[] names = ge.getAvailableFontFamilyNames();
		for (String name : names) {
			System.out.println(name);
		}
	}

	/**
	 * Change the font. Valid font names can be found by calling printFonts().
	 * 
	 * @param newFontName
	 *            the name of the new font family.
	 */
	public void changeFont(String newFontName) {
		fontName = newFontName;
		setFont();
		control.draw();
	}

	/**
	 * @return the size of the font.
	 */
	public Integer getFontSize() {
		return fontSize;
	}

	/**
	 * @return the text itself.
	 */
	public String getText() {
		return text;
	}

	/**
	 * The name of the current font.
	 */
	public String getFontName() {
		return fontName;
	}

	/**
	 * Reposition the text at a new, random position on the canvas.
	 */
	public void randomizePosition() {
		Point p = new Point();
		Canvas c = Canvas.getCanvas();
		// If the text fits in the window,
		// Make sure the text doesn't go off the screen
		if (c.getTextWidth(font, text) < c.getWidth()) {
			Integer rightX = p.getX() + c.getTextWidth(font, text);
			if (rightX > c.getWidth()) {
				p.setX(p.getX() - (rightX - c.getWidth()));
			}
		}
		if (c.getTextHeight(font, text) < c.getHeight()) {
			Integer topY = p.getY() - c.getTextHeight(font, text);
			if (topY < 0) {
				p.setY(p.getY() + Math.abs(topY));
			}
		}
		this.setPosition(p.getX(), p.getY());
	}

	/**
	 * Find the width of the text in the selected font.
	 * 
	 * @return the width of the text
	 */
	public Integer getTextWidth() {
		Canvas c = Canvas.getCanvas();
		return c.getTextWidth(font, text);
	}

	/**
	 * Find the height of the text in the selected font.
	 * 
	 * @return the height of the text
	 */
	public Integer getTextHeight() {
		Canvas c = Canvas.getCanvas();
		return c.getTextHeight(font, text);
	}

	private void wait(Integer ms) {
		Canvas.getCanvas().wait(ms);
	}

	private class Control implements DrawControl {
		private Text t;

		public Control(Text t) {
			this.t = t;
		}

		/**
		 * Draw the text with current specifications on the canvas.
		 */
		public void draw() {
			if (t.isVisible()) {
				Canvas canvas = Canvas.getCanvas();
				canvas.draw(t, null);
				canvas.wait(10);
			}
		}

		/**
		 * Erase the text on the canvas.
		 */
		public void erase() {
			if (t.isVisible()) {
				Canvas canvas = Canvas.getCanvas();
				canvas.erase(t);
			}
		}
	}
}
