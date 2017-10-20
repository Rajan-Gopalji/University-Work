package com.uni.battleships;

import com.uni.base.AbstractController;
import com.uni.base.Observer;

import java.awt.*;

/**
 * User: dogmaan
 * Date: 05/06/12
 * Time: 19:30
 */


public class SceneController  extends AbstractController
{

    public static final String DOCUMENT_NAME_PROPERTY = "Name";
    public static final String GRID_DIMENSIONS_PROPERTY = "GridDimensions";
    public static final String SHIP_GRID_PROPERTY = "ShipGrid";
    public static final String SHOT_GRID_PROPERTY = "ShotGrid";
    public static final String GRID_BUTTON_CLICKED_PROPERTY = "SelectedCoord";
    public static final String GRID_BUTTON_ENTERED_PROPERTY = "EnteredCoord";
    public static final String GRID_BUTTON_EXITED_PROPERTY = "ExitedCoord";
    public static final String PLAYER_MODE_PROPERTY = "PlayerMode";
    public static final String GAME_PHASE_PROPERTY = "Phase";
    public static final String STATUS_TEXT_PROPERTY = "StatusText";
    public static final String ORIENTATION_MODE_PROPERTY = "OrientationMode";
    public static final String ALERT_MESSAGE_PROPERTY = "AlertMessage";
    public static final String AICPU_UPDATE_PROPERTY = "AICPUUpdateProperty";

    private static Player player1;
    private static Player player2;

    /**
     * set a Model property
     * @param propertyName The name of the property
     * @param newValue An object that represents the new value of the property.
     */
    @Override
    protected void setModelProperty(String propertyName, Object newValue)
    {
        super.setModelProperty(propertyName, newValue);
    }

    public SceneController()
    {
        super();
        setModelProperty(GAME_PHASE_PROPERTY, GamePhase.INITIAL);
    }

    /**
     * change the models grid dimensions
     * @param newDimension
     */
    public void changeGridDimensions(Dimension newDimension)
    {
        setModelProperty(GRID_DIMENSIONS_PROPERTY, newDimension);
    }

    /**
     * pass a button clicked event to the model
     * @param x
     * @param y
     */
    public void gridButtonClicked(int x, int y)
    {
        setModelProperty(GRID_BUTTON_CLICKED_PROPERTY, new Point(x, y) ) ;
    }

    /**
     * pass a button entered event to the model
     * @param x
     * @param y
     */
    public void gridButtonEntered(int x, int y)
    {
        setModelProperty(GRID_BUTTON_ENTERED_PROPERTY, new Point(x, y) ) ;
    }

    /**
     * pass a button exited event to the model
     * (Unused)
     * @param x
     * @param y
     */
    public void gridButtonExited(int x, int y)
    {
        setModelProperty(GRID_BUTTON_EXITED_PROPERTY, new Point(x, y) ) ;
    }

    /**
     * Set the battleships player mode
     * @param playerMode
     */
    public void setPlayerMode(PlayerMode playerMode)
    {
        ((SceneModel)registeredModels.get(0)).setPlayerMode(playerMode);
        if(playerMode == PlayerMode.PLAYER_VS_PLAYER)
        {
            if(player1 != null)
            {
                registeredViews.remove(player1);
            }
            if(player2 != null)
            {
                registeredViews.remove(player2);
            }
        }
        else if (playerMode == PlayerMode.PLAYER_VS_CPU)
        {
            if(player1 != null)
            {
                registeredViews.remove(player1);
            }
            player2 = new CPUPlayer(this, 2);
            this.addView((Observer)player2);
        }
        // redundant
        else if (playerMode == PlayerMode.CPU_VS_CPU)
        {
            player1 = new CPUPlayer(this, 1);
            player2 = new CPUPlayer(this, 2);

            this.addView((Observer)player1);
            this.addView((Observer)player2);
        }

    }

    /**
     * save the game (string param is ignored)
     * @param name
     */
    public void saveGame(String name)
    {
        ((SceneModel)registeredModels.get(0)).save();
    }

    /**
     * load the saved game (string param is ignored)
     * @param name
     */
    public void loadGame(String name)
    {
        removeModel(((SceneModel)registeredModels.get(0)));
        addModel(SceneModel.load());
        ((SceneModel)registeredModels.get(0)).onLoad();
        setPlayerMode(((SceneModel)registeredModels.get(0)).getPlayerMode());
    }

    /**
     * Start the game
     */
    public void startGame()
    {
        setModelProperty(GAME_PHASE_PROPERTY, GamePhase.INITIAL);
        setModelProperty(GAME_PHASE_PROPERTY, GamePhase.SELECTION_PLAYER1);
    }

    /**
     * toggle the ship selection orientation
     */
    public void toggleOrientationMode()
    {
        if(((SceneModel)registeredModels.get(0)).getOrientationMode() == Orientation.HORIZONTAL)
        {
            setModelProperty(ORIENTATION_MODE_PROPERTY, Orientation.VERTICAL);
        }
        else
        {
            setModelProperty(ORIENTATION_MODE_PROPERTY, Orientation.HORIZONTAL);
        }

    }

    /**
     * get the model grid dimensions
     * @return  grid dimensions
     */
    public Dimension getGridDimensions()
    {
        return ((SceneModel)registeredModels.get(0)).getGridDimensions();

    }


}
