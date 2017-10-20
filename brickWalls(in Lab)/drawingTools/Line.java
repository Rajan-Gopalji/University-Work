package drawingTools;

import java.awt.geom.Line2D;
/**
 * Write a description of class Line here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Line extends Drawable
{
    // instance variables - replace the example below with your own
    private Point end;

    /**
     * Constructor for Line objects.  
     * All new lines are positioned at (0,0) with the end of the line at (100,100).
     * New lines are black and initially not visible.
     */
    public Line() {
        this(0,0,100,100,"black");
    }

    /**
     * Constructor for objects of class Line.
     * @param startX the X coordinate of the start of the line.
     * @param startY the Y coordinate of the start of the line.
     * @param endX the X coordinate of the end of the line.
     * @param endY the Y coordinate of the end of the line.
     * @param color the color of the line.
     */
    public Line(int startX, int startY, int endX, int endY, String color)
    {
        super(startX,startY,color,new Point(endX, endY));
        this.end = super.otherCoordinate;
        control = new Control(this);
    }

    /**
     * @return the X coordinate of the start of the line.
     */
    public int getStartX() {
        return this.getX();
    }

    /**
     * @return the Y coordinate of the start of the line.
     */
    public int getStartY() {
        return this.getY();
    }

    /**
     * @return the X coordinate of the end of the line.
     */
    public int getEndX() {
        return this.end.getX();
    }

    /**
     * @return the Y coordinate of the end of the line.
     */
    public int getEndY() {
        return this.end.getY();
    }

    /**
     * Set a new position for the start of the line.
     * @param x the x coordinate of the start of the line.
     * @param y the y coordinate of the start of the line.
     */
    public void setStartPosition(int x, int y) {
        super.setPosition(x, y);
    }

    /**
     * Set a new position for the end of the line.
     * @param x the x coordinate of the end of the line.
     * @param y the y coordinate of the end of the line.
     */
    public void setEndPosition(int x, int y) {
        control.erase();
        this.end.setX(x);
        this.end.setY(y);
        control.draw();
    }

    private class Control implements DrawControl {
        private Line l;

        public Control(Line l) {
            this.l = l;
        }

        /**
         * Draw the line with current specifications on screen.
         */
        public void draw() {
            if (l.isVisible()) {
                Canvas canvas = Canvas.getCanvas();
                canvas.draw(l, new Line2D.Double(
                        l.getStartX(),l.getStartY(),
                        l.getEndX(),l.getEndY())
                );
                canvas.wait(10);
            }
        }

        /**
         * Erase the line on screen.
         */
        public void erase() {
            if (l.isVisible()) {
                Canvas canvas = Canvas.getCanvas();
                canvas.erase(l);
            }
        }
    }

}
