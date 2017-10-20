 

/**
 * User: dogmaan
 * Date: 06/06/12
 * Time: 04:36
 */
package com.uni.battleships;

import java.io.Serializable;

/**
 * Base class for the various possible grid types
 * @param <T>
 */
public abstract class GridBase<T>  implements Serializable
{
    /**
     * this returns a generic multidimensional object array
     * it must be cast to the correct object type
     * @return  T[][]
     */
    public T[][] getGrid()
    {
        return (T[][])grid;
    }

    private T[][] grid;
    protected int width;
    protected int height;

    /**
     * Sets the dimensions of the grid
     * @param Width
     * @param Height
     */
    public void setDimensions(int Width, int Height)
    {
        grid = (T [][])new Object[Width][Height];
        width = Width;
        height = Height;
    }

    /**
     * abstract function to check if the object will fit
     * within the grid boundaries
     * @param x
     * @param y
     * @param obj
     * @return
     */
    public abstract boolean willObjectFit( int x, int y,T obj);

    /**
     * add object to grid
     * @param x
     * @param y
     * @param obj
     * @return
     */
    public boolean  addObject(int x, int y, T obj)
    {
        if(width <= 0 || height <= 0)
        {
            return false;
        }
        if(x > width || x < 0 || y > height || y < 0)
        {
            return false;
        }
        grid[x][y] = (T)obj;
        return true;
    }

    /**
     * Clear the grid
     */
    public void clear()
    {
        grid = (T[][])new Object[width][height];
    }

}
