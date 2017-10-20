package com.uni.battleships;

import java.awt.*;

/**
 * User: dogmaan
 * Date: 08/06/12
 * Time: 05:25
 */

/**
 * A class that implements this interface
 * must have a width and height
 * this is used for grid collision detection
 * when placing a ship
 */
public interface GetDimensions
{
    public Dimension dimensions();
}
