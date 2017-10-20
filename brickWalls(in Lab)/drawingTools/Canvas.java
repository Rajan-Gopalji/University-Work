package drawingTools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas. This is
 * a modification of the general purpose Canvas, specially made for the BlueJ
 * "shapes" example.
 * 
 * @author: Bruce Quig
 * @author: Michael Kolling (mik)
 * @author: Tony Beaumont
 * 
 * @version 2008.03.30
 */
public class Canvas {
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean andrectangl1 simple for educational purposes.

    private static Canvas canvasSingleton;

    /**
     * Factory method to get the canvas singleton object.
     */
    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Graphics Tool", 800, 600,
                Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    // ----- instance part -----

    private Integer width;
    private Integer height;
    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColor;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;

    /**
     * Create a Canvas.
     * 
     * @param title
     *            title to appear in Canvas Frame
     * @param width
     *            the desired width for the canvas
     * @param height
     *            the desired height for the canvas
     * @param bgColor
     *            the desired background color of the canvas
     */
    private Canvas(String title, int width, int height, Color bgColor) {
        this.width = width;
        this.height = height;
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColor = bgColor;
        frame.pack();
        // first time: instantiate the offscreen image and fill it with
        // the background color
        Dimension size = canvas.getSize();
        canvasImage = canvas.createImage(size.width, size.height);
        graphic = (Graphics2D) canvasImage.getGraphics();
        graphic.setColor(backgroundColor);
        graphic.fillRect(0, 0, size.width, size.height);
        graphic.setColor(Color.black);
        objects = new ArrayList<Object>();
        shapes = new HashMap<Object, ShapeDescription>();
    }

    /**
     * Set the canvas visibility and brings canvas to the front of screen when
     * made visible. This method can also be used to bring an already visible
     * canvas to the front of other windows.
     * 
     * @param visible
     *            boolean value representing the desired visibility of the
     *            canvas (true or false)
     */
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    /**
     * Find out the width of the canvas.
     * 
     * @return the width of the canvas.
     */
    public Integer getWidth() {
        return this.width;
    }

    /**
     * Find out the height of the canvas.
     * 
     * @return the height of the canvas.
     */
    public Integer getHeight() {
        return this.height;
    }

    /**
     * Find out the width of a text string in a given font.
     * 
     * @param f
     *            the font used to print the text.
     * @param text
     *            the text to print
     */
    public Integer getTextWidth(Font f, String text) {
        return (new Double(f.getStringBounds(text,
                    graphic.getFontRenderContext()).getWidth())).intValue();
    }

    /**
     * Find out the height of a text string in a given font.
     * 
     * @param f
     *            the font used to print the text.
     * @param text
     *            the text itself.
     */
    public Integer getTextHeight(Font f, String text) {
        return (new Double(f.getStringBounds(text,
                    graphic.getFontRenderContext()).getHeight())).intValue();
    }

    /**
     * Draw a given shape onto the canvas.
     * 
     * @param referenceObject
     *            an object to define identity for this shape
     * @param color
     *            the color of the shape
     * @param shape
     *            the shape object to be drawn on the canvas
     */
    // Note: this is a slightly backwards way of maintaining the shape
    // objects. It is carefully designed to keep the visible shape interfaces
    // in this project clean and simple for educational purposes.
    public void draw(Drawable referenceObject, Shape shape) {
        objects.remove(referenceObject); // just in case it was already there
        objects.add(referenceObject); // add at the end
        shapes.put(referenceObject,
            new ShapeDescription(referenceObject, shape));
        redraw();
    }

    /**
     * Erase a given shape's from the screen.
     * 
     * @param referenceObject
     *            the shape object to be erased
     */
    public void erase(Object referenceObject) {
        objects.remove(referenceObject); // just in case it was already there
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Clear the canvas and remove all objects.
     */
    public void clearCanvas() {
        objects.clear();
        shapes.clear();
        redraw();
    }

    /**
     * Get the foreground color using the name of the colour.
     * Valid colors are "black", "blue", "cyan", "darkGray", "gray", "green", "lightGray",
     * "magenta", "orange", "pink", "red", "white", "yellow".
     * You can also provide an RGB string of the format "r,g,b" where r, g and b are numbers
     * in the range 0 to 255.
     * 
     * @param newColor
     *            the new color for the foreground of the Canvas
     */
    public Color getColor(String colorString) {
        if (colorString.equals("black")) {
            return (Color.black);
        } else if (colorString.equals("blue")) {
            return (Color.blue);
        } else if (colorString.equals("cyan")) {
            return (Color.cyan);
        } else if (colorString.equals("darkGray")) {
            return (Color.darkGray);
        } else if (colorString.equals("gray")) {
            return (Color.gray);
        } else if (colorString.equals("green")) {
            return (Color.green);
        } else if (colorString.equals("lightGray")) {
            return (Color.lightGray);
        } else if (colorString.equals("magenta")) {
            return (Color.magenta);
        } else if (colorString.equals("orange")) {
            return (Color.orange);
        } else if (colorString.equals("pink")) {
            return (Color.pink);
        } else if (colorString.equals("red")) {
            return (Color.red);
        } else if (colorString.equals("white")) {
            return (Color.white);
        } else if (colorString.equals("yellow")) {
            return (Color.yellow);
        } else {
            int[] rgb = getRGB(colorString);
            if (rgb != null) {
                return new Color(rgb[0], rgb[1], rgb[2]);
            } else {
                return (Color.black);
            }
        }
    }

    private int[] getRGB(String str) {
        int[] rgb = null;
        String[] values = str.split(",");
        if (values.length == 3) {
            try {
                int r = Integer.parseInt(values[0]);
                int g = Integer.parseInt(values[1]);
                int b = Integer.parseInt(values[2]);
                if (r >= 0 && r <= 255 && g >= 0 && g <= 255 & b >= 0
                && b <= 255) {
                    rgb = new int[3];
                    rgb[0] = r;
                    rgb[1] = g;
                    rgb[2] = b;
                }
            } catch (NumberFormatException e) {
            }
        }
        return rgb;
    }

    /**
     * Wait for a specified number of milliseconds before finishing. This
     * provides an easy way to specify a small delay which can be used when
     * producing animations.
     * 
     * @param milliseconds
     *            the number
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            // ignoring exception at the moment
        }
    }

    /**
     * Redraw all shapes currently on the Canvas.
     */
    private void redraw() {
        erase();
        for (Object shape : objects) {
            shapes.get(shape).draw(graphic);
        }
        canvas.repaint();
    }

    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase() {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColor);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }

    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = 7277188143012062070L;

        public void paint(Graphics g) {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }

    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class ShapeDescription {
        private Shape shape;
        private Drawable drawable;

        public ShapeDescription(Drawable drawable, Shape shape) {
            this.shape = shape;
            this.drawable = drawable;
        }

        public void draw(Graphics2D graphic) {
            graphic.setColor(getColor(drawable.getColor()));
            if (!(drawable instanceof Text)) {
                graphic.setStroke(new BasicStroke(drawable.getStrokeWidth()));
            }
            if (!(drawable instanceof Text)) {
                // its a shape, draw the shape
                if (!(drawable instanceof Line) && drawable.isFilled()) {
                    graphic.fill(shape);
                    graphic.setColor(Color.BLACK);
                    graphic.draw(shape);
                } else {
                    graphic.draw(shape);
                }
            } else {
                // its some text, draw the text
                Text txt = (Text) drawable;
                Font f = new Font(txt.getFontName(), Font.PLAIN, txt
                        .getFontSize().intValue());
                graphic.setFont(f);
                graphic.drawString(txt.getText(), txt.getX(), txt.getY());
            }
        }
    }

}
