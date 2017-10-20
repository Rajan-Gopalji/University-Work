import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;
import java.awt.Font;
/**
 * A class to hold a piece of text to be displayed on the canvas.
 * 
 * @author Dr A J Beaumont
 * @version November 2011
 */
public class Text
{
    // instance variables - replace the example below with your own
    private int leftX, leftY;
    private String text;
    private int fontSize;
    private String color;
    private String fontName;
    private Boolean isVisible;
    private Font font;

    /**
     * Constructor for objects of class Text.
     * @param text a bit of text.
     */
    public Text(String text)
    {
        // initialise instance variables
        this.text = text;
        leftX = 50;
        leftY = 100;
        fontSize = 12;
        color = "black";
        fontName = "Arial";
        isVisible = false;
        setFont();
    }

    private void setFont() {
        font = new Font(fontName, Font.PLAIN, fontSize);
    }

    /**
     * Draw a graphical representation of the text on the canvas.
     * If it was already visible, do nothing.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    /**
     * Erase the graphical representation of the text on the canvas.
     * If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    /**
     * Reposition the text horizontally across the canvas by a 
     * measure of 20.
     */
    public void moveRight() {
        moveHorizontal(20);
    }

    /**
     * Reposition the text horizontally across the canvas by a 
     * measure of -20.
     */
    public void moveLeft() {
        moveHorizontal(-20);
    }

    /**
     * Reposition the text vertically by a measure of -20.
     */
    public void moveUp() {
        moveVertical(-20);
    }

    /**
     * Reposition the text vertically by a measure of 20.
     */
    public void moveDown() {
        moveVertical(20);
    }

    /**
     * Reposition the text horizontally across the canvas by a given amount.
     * @param distance The amount by which to move the text.
     */
    public void moveHorizontal(int distance) {
        erase();
        leftX = leftX + distance;
        draw();
    }

    /**
     * Reposition the text vertically across the canvas by a given amount.
     * @param distance The amount by which to move the text.
     */
    public void moveVertical(int distance) {
        erase();
        leftY = leftY + distance;
        draw();
    }

    /**
     * Slowly move the text horizontally across the canvas by a 
     * given amount.  The movement across the canvas will be visible. 
     * @param distance The amount by which to move the text.
     */
    public void slowMoveHorizontal(int distance)
    {
        int delta;

        if(distance < 0) 
        {
            delta = -1;
            distance = -distance;
        }
        else 
        {
            delta = 1;
        }

        for(int i = 0; i < distance; i++)
        {
            leftX += delta;
            draw();
        }
    }

    /**
     * Slowly move the text vertically across the canvas by a 
     * given amount.  The movement across the canvas will be visible. 
     * @param distance The amount by which to move the text.
     */
    public void slowMoveVertical(int distance)
    {
        int delta;

        if(distance < 0) 
        {
            delta = -1;
            distance = -distance;
        }
        else 
        {
            delta = 1;
        }

        for(int i = 0; i < distance; i++)
        {
            leftY += delta;
            draw();
        }
    }

    /**
     * Change the font size of the text to a given value. 
     * The value of newSize must be >= 0.
     * @param newSize the new font size of the text.
     */
    public void changeSize(int newSize)
    {
        erase();
        fontSize = newSize;
        setFont();
        draw();
    }

    /**
     * Change the color. Valid colors are "red", "yellow", "blue", 
     * "green", "magenta", "pink", "orange", "cyan" and "black".
     * @param newColor the name of the new colour.
     */
    public void changeColor(String newColor)
    {
        color = newColor;
        draw();
    }

    /**
     * Print the names of all the available fonts.
     */
    public void printFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] names = ge.getAvailableFontFamilyNames();
        for (String name : names ) {
            System.out.println(name);
        }
    }

    /**
     * Change the font. Valid font names can be found by calling printFonts().
     * @param newFontName the name of the new font family.
     */
    public void changeFont(String newFontName)
    {
        fontName = newFontName;
        setFont();
        draw();
    }

    /**
     * @return The X coordinate of the left of the text on the canvas.
     */
    public int getXPosition() {
        return leftX;
    }

    /**
     * @return The Y coordinate of the left of the text on the canvas.
     */
    public int getYPosition() {
        return leftY;
    }

    /**
     * @return the size of the font.
     */
    public int getFontSize() {
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
     * Set the left edge of the text using the coordinates of the supplied point.
     * @param xPos the new X coordinate of the left edge of the text
     * @param yPos the new Y coordinate of the left edge of the text
     */
    public void setPosition(int xPos, int yPos) {
        erase();
        leftX = xPos;
        leftY = yPos;
        draw();
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
            int rightX = p.getX() + c.getTextWidth(font, text);
            if (rightX > c.getWidth())
            {
                p.setX(p.getX() - (rightX - c.getWidth()));
            }
        }
        if (c.getTextHeight(font, text) < c.getHeight()) {
            int topY = p.getY() - c.getTextHeight(font, text);
            if (topY < 0)
            {
                p.setY(p.getY() + Math.abs(topY));
            }
        }
        this.setPosition(p.getX(), p.getY());
    }

    /**
     * Find the width of the text in the selected font.
     * @return the width of the text
     */
    public int getTextWidth() {
        Canvas c = Canvas.getCanvas();
        return c.getTextWidth(font, text);
    }

    /**
     * Find the height of the text in the selected font.
     * @return the height of the text
     */
    public int getTextHeight() {
        Canvas c = Canvas.getCanvas();
        return c.getTextHeight(font, text);
    }
    
    public void wait(int ms) {
        Canvas.getCanvas().wait(ms);
    }

    /**
     * Draw the text with current specifications on the canvas.
     */
    private void draw()
    {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color);
            canvas.wait(10);
        }
    }

    /**
     * Erase the text on the canvas.
     */
    private void erase()
    {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
