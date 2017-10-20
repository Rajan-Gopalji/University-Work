package drawingTools;

import java.awt.geom.Ellipse2D;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author Michael Kolling and David J. Barnes. Modified by AJB.
 * @version 2011.07.25
 */

public class Circle extends Drawable {
    // instance variables - replace the example below with your own
    private int diameter;

    /**
     * Constructor for objects of class Circle.  
     * New Circle objects are blue and positioned with their centre at (100,150).
     * The initial diameter by default is 40.
     */
    public Circle() {
        // initialise instance variables
        this(40);
    }

    /**
     * Construct a new Circle with a given diameter.
     * New Circle objects are blue and positioned with their centre at (100,150).
     * @param initialDiameter The diameter of the new circle.
     */
    public Circle(int initialDiameter) {
        super(100, 150, "blue",null);
        diameter = initialDiameter;
        control = new Control(this);
    }

    /**
     * Change the diameter of the circle to the new size (in pixels). 
     * @param newDiameter the new diameter of the circle.
     */
    public void changeSize(int newDiameter) {
        control.erase();
        diameter = newDiameter;
        control.draw();
    }
    
    /**
     * @return the diameter of the circle.
     */
    public int getDiameter() {
        return diameter;
    }

    private class Control implements DrawControl {
        private Circle c;

        public Control(Circle c) {
            this.c = c;
        }

        /**
         * Draw the circle with current specifications on screen.
         */
        public void draw() {
            if (c.isVisible()) {
                Canvas canvas = Canvas.getCanvas();
                canvas.draw(c, new Ellipse2D.Double(c.getX() - (diameter / 2.0), 
                        c.getY() - (diameter / 2.0), diameter,
                        diameter));
                canvas.wait(10);
            }
        }

        /**
         * Erase the circle on screen.
         */
        public void erase() {
            if (c.isVisible()) {
                Canvas canvas = Canvas.getCanvas();
                canvas.erase(c);
            }
        }
    }

}
