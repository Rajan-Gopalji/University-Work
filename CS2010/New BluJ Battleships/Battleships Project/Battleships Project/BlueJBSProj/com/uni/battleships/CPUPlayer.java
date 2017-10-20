package com.uni.battleships;

import com.uni.base.Observer;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.Random;

/**
 * User: dogmaan
 * Date: 06/06/12
 * Time: 16:33
 */


public class CPUPlayer extends Player  implements Observer
{
    private SceneController sceneController;
    private int playerNumber;
    private GamePhase gamePhase;
    char[] oldGrid;

    CPUPlayer(SceneController sceneController, int playerNumber)
    {
        this.sceneController = sceneController;
        this.playerNumber = playerNumber;
    }
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt)
    {
        if(evt.getPropertyName().equals(SceneController.GAME_PHASE_PROPERTY) )
        {
            gamePhase = (GamePhase)evt.getNewValue();
        }
        else if(evt.getPropertyName().equals(SceneController.AICPU_UPDATE_PROPERTY))
        {
            if (oldGrid == null)
                oldGrid = (char[])evt.getNewValue();
            if(gamePhase.ordinal() == playerNumber ||gamePhase.ordinal() == playerNumber + 2)
            {
                char[] grid = (char[])evt.getNewValue();
                Dimension dimension = sceneController.getGridDimensions();
                Random random = new Random();
                Point p = new Point(random.nextInt(dimension.width), random.nextInt(dimension.height));

                sceneController.gridButtonClicked(p.x, p.y);
            }
        }

       //System.out.println("testtest");
    }
}
