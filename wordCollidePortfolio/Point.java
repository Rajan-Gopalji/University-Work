import java.util.Random;

/**
 * Write a description of class Point here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Point
{
    // instance variables - replace the example below with your own
    private int xCoordinate;
    private int yCoordinate;

    /**
     * Create a new point with random X and Y coordinates
     */
    public Point()
    {
        // initialise instance variables
        Random randomGenerator = new Random();
        xCoordinate = randomGenerator.nextInt(Canvas.getCanvas().getWidth());
        yCoordinate = randomGenerator.nextInt(Canvas.getCanvas().getHeight());
    }

    /**
     * Create a point with specified X and Y coordinates
     */
    public Point(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
    }

    /**
     * @return the X coordinate of this point.
     */
    public int getX() {
        return xCoordinate;
    }

    /**
     * @return the Y coordinate of this point.
     */
    public int getY() {
        return yCoordinate;
    }

    /**
     * Set a new value for the X coordinate of this point.
     * @param x the new X coordinate
     */
    public void setX(Integer x) {
        xCoordinate = x;
    }

    /**
     * Set a new value for the Y coordinate of this point.
     * @param y the new Y coordinate
     */
    public void setY(Integer y) {
        yCoordinate = y;
    }

}
