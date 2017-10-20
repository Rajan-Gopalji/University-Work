package com.uni.battleships;

/**
 * User: dogmaan
 * Date: 06/06/12
 * Time: 16:12
 */


public class ShotGrid extends GridBase<Shot> implements CharacterArray
{
    @Override
    public boolean addObject(int x, int y, Shot obj)
    {
        return super.addObject(x, y, obj);    //To change body of overridden methods use File | Settings | File
        // Templates.
    }

    public boolean willObjectFit(int x, int y, Shot obj)
    {
        if(x + obj.dimensions().width <= 0 || y + obj.dimensions().height <= 0)
        {
            return false;
        }
        if(x + obj.dimensions().width > width || x < 0 || y + obj.dimensions().height > height || y < 0)
        {
            return false;
        }
        return true;
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

        char[] tShots = new char[aSize];

        for (int i = 0; i < aWidth; ++i)
        {
            for(int j = 0; j < ((Object[][])getGrid())[i].length; ++j)
            {
                calculateShotChars(aWidth, tShots, i, j);
            }

        }

        return tShots;
    }

    private void calculateShotChars(int aWidth, char[] tShots, int i, int j)
    {
        if (((Shot)((Object[][])getGrid())[i][j]) != null)
        {
            if (((Shot)((Object[][])getGrid())[i][j]).getShotStatus() == ShotStatus.HIT)
            {
                tShots[i + (j * aWidth )] = 'h';
            }
            else if(((Shot)((Object[][])getGrid())[i][j]).getShotStatus() == ShotStatus.MISS)
            {
                tShots[i + (j * aWidth )] = 'm';
            }
        }
    }

}
