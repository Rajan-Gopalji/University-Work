import drawingTools.*;

import java.util.ArrayList;
/**
 * Write a description of class BrickWall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BrickWall
{
    // width and height of each brick
    private int bWidth;
    private int bHeight;
    // number of rows of bricks in the wall
    private int numRows;
    // number of bricks in each row
    private int rowLength;
    // a list of colors to use for drawing
    private ArrayList<String> colors;
    // a list to hold all the rectangles you use to draw the wall
    private ArrayList<Rectangle> bricks;
    // whether to decrease the length of the wall
    private boolean isDecreasing;
    // whether the decrease in length of a row is symmetric
    private boolean isSymmetric;
    // whether the wall is drawn all the same colour or multiple colors
    private boolean isMultiColor;
    // the index of the current colour in the list of colors
    private int currentColor;
    // position of the first brick in the wall
    private int startX;
    private int startY;

    /**
     * Constructor for objects of class BrickWall.
     * @param rows The number of rows in the wall
     * @param rowlen The number of bricks in a row
     */
    public BrickWall(int rows, int rowLen)
    {
        // initialise instance variables
        setUpColors();
        // the width of a brick is 54
        bWidth = 54;
        // the height of a brick is 16
        bHeight = 16;
        // the first brick should be drawn at (10,550)
        // x coordinate of the first brick
        startX = 10;
        // y coordinate of the first brick
        startY = 550;
        // an Array list to hold every Rectangle you create
        // (each brick is actually a Rectangle)
        bricks = new ArrayList<Rectangle>();
        // you can use this field to hold the current color 
        // that you are using to draw bricks
        currentColor = 0;
        // store the number of rows to draw
        setNumRows(rows);
        setRowLength(rowLen);
        // initially the bricks are all the same color
        this.isMultiColor = false;
        // initially the length of each row is the same
        this.isDecreasing = false;
        this.isSymmetric = false;
    }

    private void setUpColors() {
        colors = new ArrayList<String>();
        // color 0 is red
        colors.add("red");
        // color 1 is yellow
        colors.add("yellow");
        // color 2 is blue
        colors.add("blue");
        // color 3 is green
        colors.add("green");
        // color 4 is magenta
        colors.add("magenta");
        //color 5 is pink
        colors.add("pink");
    }

    /**
     * Toggle whether the wall is multicoloured.
     */
    public void toggleMultiColour() {
        isMultiColor = !isMultiColor;
        currentColor = 0;
    }

    /**
     * Toggle whether the decrease in a row length is symmetric.
     */
    public void toggleSymmetric() {
        isSymmetric = !isSymmetric;
    }

    /**
     * Toggle whether the length of a new row is one less than the length of the previous row.
     */
    public void toggleDecrease() {
        isDecreasing = ! isDecreasing;
    }

    /**
     * @return the number of bricks in the current wall.
     */
    public int getNumberOfBricks() {
        return bricks.size();
    }

    /**
     * Set the length of a row.  There can be no more than 22 bricks in a row.
     * @param len The number of bricks in a row.  If len is less than or equal
     * to zero OR len is greater than 22, the row length will be assigned the value 22.
     * Otherwise the length of a row will be assigned the value of len.
     */
    public void setRowLength(int len) {
        if (len <= 0 || len > 22) {
            rowLength = 22;
        } else {
            rowLength = len;
        }
    }

    /**
     * Set the maximum number of rows in the wall.  If the length of a row decreases, 
     * there may not be this many rows in the wall.
     * @param rows The maximum number of rows in the wall.  If rows is less than
     * or equal to zero OR rows is greater than 30, the number of rows will be assigned the value 30.
     * Otherwise the number of rows will be assigned the value of rows.
     */
    public void setNumRows(int rows) {
        if (rows <= 0 || rows > 30) {
            numRows = 30;
        } else {
            numRows = rows;
        }
    }

    /**
     * Delete all the bricks in the current wall and clear the canvas.
     */
    public void eraseWall() {
        Canvas canvas = Canvas.getCanvas();
        for (int i=0; !bricks.isEmpty(); i++) {
            canvas.erase(bricks.remove(0));
        }
    }

    //--------------------------------------------------------------
    // Do not change anything above this line
    //--------------------------------------------------------------

    /**
     * Draw the wall.  The first brick will be positioned at the coordinates (10, 550).  
     * The number of bricks in a row is specified by setRowLength().  The maximum number of rows
     * is specified by setNumRows().  If isDecreasing is true, each subsequent row of bricks 
     * contains one brick less than the previous row.  If symmetric is true AND isDecreasing is true then
     * the wall is pyramid shaped.  If symmetric is false AND isDecreasing is true then the wall is shaped
     * like a right angle triangle.
     */
    public void draw()
    {
        eraseWall();
        //------------------------------
        // Only use ONE of the following 
        // 3 lines at any one time.
        // uncomment by removing the //
        //------------------------------
        //drawBrick(startX, startY);
        //drawRowOfBricks(startX, startY, rowLength);
        drawWall(startX, startY, rowLength, numRows);
    }

    private void drawBrick(int startX,int startY){
        Rectangle brick = new Rectangle();
        brick.changeSize(bWidth, bHeight);

        if (isMultiColor == true)
        {
            currentColor += 1;
            if (currentColor >= colors.size())
            {
                currentColor = 0;
            }
        }

        brick.changeColor(colors.get(currentColor));
        brick.setPosition(startX, startY);
        brick.makeVisible();
        bricks.add(brick);

    }

    private void drawRowOfBricks(int startX, int startY,int rowLength){

        int i = 0;
        while(i < rowLength){
            drawBrick(startX, startY);
            startX = startX + bWidth;
            i++;
        }

    }

    private void drawWall(int startX, int startY, int rowLength, int numRows){
        int i = 0;

        while(i < numRows){

            drawRowOfBricks(startX, startY, rowLength);
            // shorten the row by one if necessary
            if(isDecreasing == true)
            {

                rowLength = rowLength-1;
            }
			
            //shifts row by a factor of 1/2 in the positive x direction
            if (isDecreasing == true && isSymmetric == true)
            {

                startX = startX + bWidth/2;
            }
			//prepares to place next brick above the row by subtracting the height of the brick of the...
			//...current position. Subtracting moves it upwards in y direction 
            startY = startY - bHeight;
            i++;
        }
    }

}

