package com.uni.battleships;

import java.awt.*;
import java.io.Serializable;

/**
 * User: dogmaan
 * Date: 06/06/12
 * Time: 16:30
 */
public class Shot  implements GetDimensions, Serializable
{
    public Shot(ShotStatus shotStatus)
    {
        this.shotStatus = shotStatus;
    }


    public Owner getShotOwner()
    {
        return ShotOwner;
    }

    public void setShotOwner(Owner shotOwner)
    {
        ShotOwner = shotOwner;
    }

    private Owner ShotOwner;


    public ShotStatus getShotStatus()
    {
        return shotStatus;
    }

    public void setShotStatus(ShotStatus shotStatus)
    {
        this.shotStatus = shotStatus;
    }

    private ShotStatus shotStatus;

    @Override
    public Dimension dimensions()
    {
        return new Dimension(1,1);
    }
}