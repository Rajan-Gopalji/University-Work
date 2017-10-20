package com.uni.battleships;

import java.io.Serializable;

/**
 * User: dogmaan
 * Date: 06/06/12
 * Time: 16:33
 */

/**
 * Abstract Player class, most of the functionality is unused
 */
public abstract class Player  implements Serializable
{
    private int Score;
    private int hits;
    private int misses;
    private int shotsFired;

    public int getScore()
    {
        return Score;
    }

    public int getHits()
    {
        return hits;
    }

    public void setHits(int hits)
    {
        this.hits = hits;
    }

    public int getMisses()
    {
        return misses;
    }

    public void setMisses(int misses)
    {
        this.misses = misses;
    }

    public int getShotsFired()
    {
        return shotsFired;
    }

    public void setShotsFired(int shots)
    {
        this.shotsFired = shots;
    }


}
