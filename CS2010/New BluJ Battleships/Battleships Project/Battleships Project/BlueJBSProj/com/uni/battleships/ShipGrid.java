 

/**
 * Created with IntelliJ IDEA.
 * User: dogmaan
 * Date: 06/06/12
 * Time: 06:07
 * To change this template use File | Settings | File Templates.
 */


package com.uni.battleships;
public class ShipGrid extends GridBase<Ship> implements CharacterArray
{
    @Override
    public boolean willObjectFit(int x, int y, Ship obj)
    {
        //  TODO: implement orientation check
        //  TODO: Collision check
        if(obj.getShipOrientation() == Orientation.HORIZONTAL)
        {
            if(x + obj.dimensions().width <= 0 || y + obj.dimensions().height <= 0)
            {
                return false;
            }
            if(x + obj.dimensions().width > width || x < 0 || y + obj.dimensions().height > height || y < 0)
            {
                return false;
            }
            for(int i = 0; i < obj.dimensions().width; ++i)
            {
                for (int ii = 0; ii < obj.dimensions().height; ++ii)
                {
                    if(getCharArray()[(x + i) + ((y + ii) * width)] != '\u0000' )
                        return false;
                }
            }
        }
        else if (obj.getShipOrientation() == Orientation.VERTICAL)
        {
            if(x + obj.dimensions().width <= 0 || y + obj.dimensions().height <= 0)
            {
                return false;
            }
            if(x + obj.dimensions().height > width || x < 0 || y + obj.dimensions().width > height || y < 0)
            {
                return false;
            }
            for(int i = 0; i < obj.dimensions().height; ++i)
            {
                for (int ii = 0; ii < obj.dimensions().width; ++ii)
                {
                    if(getCharArray()[(x + i) + ((y + ii) * width)] != '\u0000' )
                        return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean addObject(int x, int y, Ship obj)
    {
        return super.addObject(x, y, obj);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public char[] getCharArray()
    {
        int aWidth = ((Object[][])getGrid()).length;
        int aSize = 0;

        for (int i = 0; i < this.width; ++i)
        {
             aSize += ((Object[][])getGrid())[i].length;
        }

        char[] tShips = new char[aSize];

        for (int i = 0; i < aWidth; ++i)
        {
            for(int j = 0; j < ((Object[][])getGrid())[i].length; ++j)
            {
                calcShipChars(aWidth, tShips, i, j);
            }

        }

        return tShips;
    }

    private void calcShipChars(int aWidth, char[] tShips, int i, int j)
    {
        //ShipGrid[][] gridCopy = (ShipGrid[][])((Object[][])getGrid());
        //TODO refactor all the unreadable casting
        if (((Ship)((Object[][])getGrid())[i][j]) != null)
        {
            if(((Ship)((Object[][])getGrid())[i][j]).getShipOrientation() == Orientation.HORIZONTAL)
            {
                for (int xIter = 0; xIter < ((Ship)((Object[][])getGrid())[i][j]).getDimensions().width; ++xIter)
                {
                     for (int yIter = 0; yIter < ((Ship)((Object[][])getGrid())[i][j]).getDimensions().height; ++yIter)
                     {
                        tShips[(i + xIter) + ((j + yIter) * aWidth)] = ((Ship)((Object[][])getGrid())[i][j]).getShipSymbol();
                     }
                }
            }
            else
            if(((Ship)((Object[][])getGrid())[i][j]).getShipOrientation() == Orientation.VERTICAL)
            {
                for (int xIter = 0; xIter < ((Ship)((Object[][])getGrid())[i][j]).getDimensions().height; ++xIter)
                {
                    for (int yIter = 0; yIter < ((Ship)((Object[][])getGrid())[i][j]).getDimensions().width; ++yIter)
                    {
                        tShips[(i + xIter) + ((j + yIter) * aWidth)] = ((Ship)((Object[][])getGrid())[i][j]).getShipSymbol();
                    }
                }
            }
        }
    }
}
