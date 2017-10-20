 

/**
 * User: dogmaan
 * Date: 06/06/12
 * Time: 06:09
 */
package com.uni.battleships;

import java.awt.*;
import java.io.Serializable;

/**
 * base ship class
 */
public abstract class Ship implements GetDimensions, Serializable
{

    /**
     * get the ships char symbol
     * @return
     */
    public char getShipSymbol()
    {
        return ShipSymbol;
    }

    /**
     * sets the ships char symbol
     * @param shipSymbol
     */
    public void setShipSymbol(char shipSymbol)
    {
        ShipSymbol = shipSymbol;
    }

    private char ShipSymbol;

    /**
     * get the ships orientation
     * @return
     */
    public Orientation getShipOrientation()
    {
        return ShipOrientation;
    }

    /**
     * sets the ships orientation
     * @param shipOrientation
     */
    public void setShipOrientation(Orientation shipOrientation)
    {
        ShipOrientation = shipOrientation;
    }

    private Orientation ShipOrientation = Orientation.HORIZONTAL;

    /**
     * get the ships dimensions
     * @return
     */
    public Dimension getDimensions()
    {
        return shipDimensions;
    }

    /**
     * sets the ships dimensions
     * @param dimension
     */
    public void setDimensions(Dimension dimension)
    {
        this.shipDimensions = dimension;
    }

    private Dimension shipDimensions;
    public Owner getOwner()
    {
        return owner;
    }

    public void setOwner(Owner owner)
    {
        this.owner = owner;
    }

    private Owner owner;

    public Point getPosition()
    {
        return position;
    }

    /**
     * set the ships position
     * @param position
     */
    public void setPosition(Point position)
    {
        this.position = position;
    }

    private Point position;

    @Override
    public Dimension dimensions()
    {
        return shipDimensions;
    }
}
