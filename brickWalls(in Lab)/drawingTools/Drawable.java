package drawingTools;

import java.util.Random;
import java.util.ArrayList;

/**
 * A class to represent something that is drawable on the canvas.
 * Drawable things have a position, color, may or may not be visible on the
 * canvas, may be drawn as a filled or outline shape.
 * 
 * @author Michael Kolling and David J. Barnes. Modified by Tony Beaumont.
 * @version October 2013

 */
public abstract class Drawable {
    private int xCoordinate;
    private int yCoordinate;
    protected Point otherCoordinate;
    private String color;
    private boolean isVisible;
    private boolean isFilled;
    protected DrawControl control;
    protected static final int smallDistance=20;
    private double strokeWidth;

    public Drawable(int x, int y, String color, Point p)
    {
        xCoordinate = x;
        yCoordinate = y;
        this.color = color;
        isVisible = false;
        isFilled = true;
        otherCoordinate = p;
        strokeWidth = (float)1.0;
    }

    /**
     * Specify the object should be drawn as a solid filled shape.
     */
    public void setFilled() {
        isFilled = true;
        control.draw();
    }

    /**
     * Specify the object should be drawn as an outline only.
     */
    public void setOutline() {
        isFilled = false;
        control.draw();
    }
    
    /**
     * Get the stroke width.
     * @return the width of the stroke use to draw this object.
     */
    public float getStrokeWidth() {
        return (float) strokeWidth;
    }
    /**
     * Set the stroke width.  The default stroke width is 1.0.
     * @param width the width of the stroke use to draw this object.
     */
    public void setStrokeWidth(double width) {
        control.erase();
        strokeWidth = width;
        control.draw();
    }

    /**
     * Find out if the object is drawn filled.
     * @return true when the object is filled and false if outline only.
     */
    public boolean isFilled() {
        return isFilled;
    }

    /**
     * Make this object visible. If it was already visible, do nothing.
     */
    public void makeVisible()
    {
        isVisible = true;
        control.draw();
    }

    /**
     * Make this object invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible()
    {
        control.erase();
        isVisible = false;
    }

    /**
     * Move the object a few pixels to the right.
     */
    public void moveRight()
    {
        moveHorizontal(smallDistance);
    }

    /**
     * Move the object a few pixels to the left.
     */
    public void moveLeft()
    {
        moveHorizontal(-smallDistance);
    }

    /**
     * Move the object a few pixels up.
     */
    public void moveUp()
    {
        moveVertical(-smallDistance);
    }

    /**
     * Move the object a few pixels down.
     */
    public void moveDown()
    {
        moveVertical(smallDistance);
    }

    /**
     * Move the object horizontally by 'distance' pixels.
     * @param distance the distance to move the object.
     */
    public void moveHorizontal(int distance)
    {
        control.erase();
        xCoordinate = xCoordinate + distance;
        if (otherCoordinate != null) {
            otherCoordinate.setX(otherCoordinate.getX() + distance);
        }
        control.draw();
    }

    /**
     * Move the object vertically by 'distance' pixels.
     * @param distance the distance to move the object.
     */
    public void moveVertical(int distance)
    {
        control.erase();
        yCoordinate = yCoordinate + distance;
        if (otherCoordinate != null) {
            otherCoordinate.setY(otherCoordinate.getY() + distance);
        }
        control.draw();
    }

    /**
     * Slowly move the object horizontally by 'distance' pixels.
     * @param distance the distance to move the object.
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
            xCoordinate = xCoordinate + delta;
            if (otherCoordinate != null) {
                otherCoordinate.setX(otherCoordinate.getX() + delta);
            }
            control.draw();
        }
    }

    /**
     * Slowly move the object vertically by 'distance' pixels.
     * @param distance the distance to move the object.
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
            yCoordinate = yCoordinate + delta;
            if (otherCoordinate != null) {
                otherCoordinate.setY(otherCoordinate.getY() + delta);
            }
            control.draw();
        }
    }

    /**
     * Set a new position for the shape.
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    public void setPosition(int x, int y) {
        if (x >= 0 && y >= 0) {
            control.erase();
            int xDelta = xCoordinate - x;
            int yDelta = yCoordinate - y;
            xCoordinate = x;
            yCoordinate = y;
            if (otherCoordinate != null) {
                otherCoordinate.setX(otherCoordinate.getX() - xDelta);
                otherCoordinate.setY(otherCoordinate.getY() - yDelta);
            }
            control.draw();
        }
    }

    /**
     * Get the position of this shape.
     * @return a Point object containing the X and Y coordinates of the shape.
     */
    public Point getPosition() {
        return new Point(this.getX(), this.getY());
    }

    /**
     * Get the x coordinate of the shape 
     * @return the x coordinate of the shape
     */
    public int getX() {
        return xCoordinate;
    }

    /**
     * Get the y coordinate of the shape 
     * @return the y coordinate of the shape
     */
    public int getY() {
        return yCoordinate;
    }

    /**
     * Set the x coordinate of the shape 
     * @param x the x coordinate of the shape
     */
    public void setX(int x) {
        xCoordinate = x;
    }

    /**
     * Set the y coordinate of the shape 
     * @param y the y coordinate of the shape
     */
    public void setY(int y) {
        yCoordinate = y;
    }

    /**
     * Change the color. 
     * Valid colors are "black", "blue", "cyan", "darkGray", "gray", "green", "lightGray",
     * "magenta", "orange", "pink", "red", "white", "yellow".
     * You can also provide an RGB string of the format "r,g,b" where r, g and b are numbers
     * in the range 0 to 255.
     */
    public void changeColor(String newColor)
    {
        color = newColor;
        control.draw();
    }

    /**
     * Find out the color of the shape.
     * @return the color that this object is drawn in.
     */
    public String getColor() {
        return color;
    }

    /**
     * Find out if the object is visible or not
     * @return true when the object is visible on the canvas and false otherwise.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Position the shape in a new, random position.
     */
    public void randomizePosition() {
        Point p = new Point();
        this.setPosition(p.getX(), p.getY());
    }

}
