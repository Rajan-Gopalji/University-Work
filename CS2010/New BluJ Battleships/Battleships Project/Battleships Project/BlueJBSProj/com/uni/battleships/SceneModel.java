 

/**
 * User: dogmaan
 * Date: 05/06/12
 * Time: 21:34
 */
package com.uni.battleships;



import com.uni.base.AbstractModel;

import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.Stack;

/**
 * SceneModel Class
 * Contains the Battleships game state and logic
 */
public class SceneModel extends AbstractModel implements Serializable
{

    private PlayerMode playerMode;
    private ShipGrid shipGrid;
    private ShotGrid shotGrid;
    private Dimension gridDimensions;

    /**
     * accessor method
     * @return  savefilename
     */
    public static String getSaveFileName()
    {
        return saveFileName;
    }

    /**
     * sets the save file name
     * @param newName
     */
    public static void setSaveFileName(String newName)
    {
        saveFileName = newName;
    }

    private static String saveFileName = "Battleships.sav";

    /**
     * get the alert message
     * @return  alert message
     */
    public String getAlertMessage()
    {
        return alertMessage;
    }

    /**
     * set alert message
     * and fire property change event
     * @param alertMessage
     */
    public void setAlertMessage(String alertMessage)
    {
        this.alertMessage = alertMessage;
        firePropertyChange(SceneController.ALERT_MESSAGE_PROPERTY, "", alertMessage);
        System.out.println(alertMessage);
    }

    private String alertMessage;


    /**
     * are the ships viewable
     * @return true or false
     */
    public boolean isViewShips()
    {
        return viewShips;
    }

    /**
     * set the ships viewable flag
     * @param viewShips
     */
    public void setViewShips(boolean viewShips)
    {
        this.viewShips = viewShips;
    }

    private boolean viewShips = false;

    /**
     * is debug view enabled
     * @return
     */
    public boolean isDebugViewAll()
    {
        return debugViewAll;
    }

    /**
     * set Debug view flag
     * @param debugViewAll
     */
    public void setDebugViewAll(boolean debugViewAll)
    {
        this.debugViewAll = debugViewAll;
    }

    private boolean debugViewAll = false;

    /**
     * get orientation
     * @return
     */
    public Orientation getOrientationMode()
    {
        return orientationMode;
    }

    /**
     * set the orientation mode
     * @param orientationMode
     */
    public void setOrientationMode(Orientation orientationMode)
    {
        if (this.orientationMode != orientationMode)
        {
            firePropertyChange(SceneController.ORIENTATION_MODE_PROPERTY, this.orientationMode, orientationMode);
            this.orientationMode = orientationMode;
            System.out.println("Orientation is: " + orientationMode.toString().toLowerCase());
        }
    }

    private Orientation orientationMode = Orientation.HORIZONTAL;

    /**
     * get the status text
     * @return
     */
    public String getStatusText()
    {
        return statusText;
    }

    /**
     * set the status text
     * @param statusText
     */
    public void setStatusText(String statusText)
    {
        firePropertyChange(SceneController.STATUS_TEXT_PROPERTY, "", statusText);
        this.statusText = statusText;
    }

    private String statusText = "";

    private int maxBattleShips = 1;
    private int maxCruisers = 2;
    private int maxDestroyers = 3;
    private int maxSubmarines = 3;

    private int winningScore = 0;
    private int[] playerScores = new int[]{0,0};

    private Stack<Ship> shipStack;


    /**
     * get the current game phase
     * @return
     */
    public GamePhase getPhase()
    {
        return Phase;
    }

    /**
     * set the current game phase
     * @param gamePhase
     */
    public void setPhase(GamePhase gamePhase)
    {
        if(gamePhase != this.Phase)
        {
            firePropertyChange(SceneController.GAME_PHASE_PROPERTY, Phase, gamePhase);
            this.Phase = gamePhase;
            if(Phase == GamePhase.INITIAL)
            {

            }
            if(Phase == GamePhase.SELECTION_PLAYER1 || Phase == GamePhase.SELECTION_PLAYER2)
            {
                if (Phase != GamePhase.SELECTION_PLAYER2)
                {
                    initDefault();
                    shipGrid = new ShipGrid();
                    shotGrid = new ShotGrid();
                    shipGrid.setDimensions(gridDimensions.width, gridDimensions.height);
                    shotGrid.setDimensions(gridDimensions.width, gridDimensions.height);
                }
                generateShipStack();
                if(winningScore == 0)
                {
                    for(Ship s : shipStack)
                    {
                        winningScore += (s.getDimensions().width * s.getDimensions().height);
                    }
                }
                setStatusText("Player " + Phase.ordinal() + " Please Place Your " + shipStack.peek().getClass().getSimpleName());
                firePropertyChange(SceneController.AICPU_UPDATE_PROPERTY, null, removeOppositePlayerShipsFromArray(shipGrid.getCharArray()));
            }
            if (Phase == GamePhase.SHOTTURN_PLAYER1 || Phase == GamePhase.SHOTTURN_PLAYER2)
            {
                if(getPlayerMode() == PlayerMode.PLAYER_VS_PLAYER)
                    setStatusText("player " + (this.getPhase().ordinal() - 2) + "'s Turn" );

                firePropertyChange(SceneController.AICPU_UPDATE_PROPERTY, null, generateCharArray());
            }
            else if (Phase == GamePhase.GAMEEND)
            {
                firePropertyChange(SceneController.SHOT_GRID_PROPERTY, generateCharArray(), overlayShots(shipGrid.getCharArray(), shotGrid.getCharArray()));
            }
            System.out.println("Game Phase is: " + Phase.toString().toLowerCase());
        }

    }

    private GamePhase Phase;

    /**
     * get the current player mode
     * @return
     */
    public PlayerMode getPlayerMode()
    {
        return playerMode;
    }

    /**
     * set the player mode
     * @param playerMode
     */
    public void setPlayerMode(PlayerMode playerMode)
    {
        if(this.playerMode != playerMode)
        {
            firePropertyChange(SceneController.PLAYER_MODE_PROPERTY, this.playerMode, playerMode);
            this.playerMode = playerMode;
            System.out.println("Player Mode set to: " + playerMode );
        }
    }

    /**
     * get the ship grid
     * @return
     */
    public ShipGrid getShipGrid()
    {
        return shipGrid;
    }

    /**
     * get the shot grid
     * @return
     */
    public ShotGrid getShotGrid()
    {
        return shotGrid;
    }

    /**
     * generate a character array to be used by the UI
     * @return
     */
    private char[] generateCharArray()
    {
        char[] chars =  getShipGrid().getCharArray();

        if(!isViewShips())
        {
            return getShotGrid().getCharArray();
        }


        chars = removeOppositePlayerShipsFromArray(chars);

        chars = overlayShots(chars, this.getShotGrid().getCharArray());
        return chars;

    }

    /**
     * remove the opposing players ships from the array so they cannot be viewed
     * by the other player
     * @param ships
     * @return
     */
    private char[] removeOppositePlayerShipsFromArray(char[] ships)
    {
        char[] chars = ships;
        if(!isDebugViewAll())
        {
            switch (getPhase())
            {
                case SELECTION_PLAYER1:
                    for(int x =  (getGridDimensions().width /2); x < getGridDimensions().width; ++x)
                    {
                        for (int y = 0; y < getGridDimensions().height; ++y)
                        {
                            chars[x + (y * getGridDimensions().width)] = '\u0000';
                        }
                    }
                    break;
                case SELECTION_PLAYER2:
                    for(int x = 0; x < (getGridDimensions().width /2); ++x)
                    {
                        for (int y = 0; y < getGridDimensions().height; ++y)
                        {
                            chars[x + (y * getGridDimensions().width)] = '\u0000';
                        }
                    }
                    break;
            }
            if(isViewShips())
            {
                switch (getPhase())
                {

                    case SHOTTURN_PLAYER1:
                        for(int x =  (getGridDimensions().width /2); x < getGridDimensions().width; ++x)
                        {
                            for (int y = 0; y < getGridDimensions().height; ++y)
                            {
                                chars[x + (y * getGridDimensions().width)] = '\u0000';
                            }
                        }
                        break;
                    case SHOTTURN_PLAYER2:
                        for(int x = 0; x < (getGridDimensions().width /2); ++x)
                        {
                            for (int y = 0; y < getGridDimensions().height; ++y)
                            {
                                chars[x + (y * getGridDimensions().width)] = '\u0000';
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return chars;
    }


    /**
     * get the grid dimensions
     * @return
     */
    public Dimension getGridDimensions()
    {
        return gridDimensions;
    }

    /**
     * set the grid dimensions
     * @param dimension
     */
    public void setGridDimensions(Dimension dimension)
    {
        if(this.gridDimensions != dimension)
        {
            Dimension oldDimensions = this.gridDimensions;
            gridDimensions = new Dimension(dimension);
            shipGrid.clear();
            shotGrid.clear();
            shipGrid.setDimensions(dimension.width, dimension.height);
            shotGrid.setDimensions(dimension.width, dimension.height );
            firePropertyChange(SceneController.GRID_DIMENSIONS_PROPERTY, oldDimensions, dimension);
        }
    }

    /**
     * initialise the class
     */
    @Override
    public void initDefault()
    {
        setName("SceneModel");
        winningScore = 0;
        playerScores = new int[]{0,0};
        setStatusText("Press New game or Load game to start");

    }

    /**
     * ser the class name
     * @param name
     */
    @Override
    public void setName(String name)
    {
        if(this.name != name)
        {
            String oldName = this.name;
            this.name = name;
            firePropertyChange(SceneController.DOCUMENT_NAME_PROPERTY, oldName, name);
        }
    }

    public boolean shotFired(Owner owner, int x, int y )
    {
        return true;
    }

    public SceneModel()
    {
        super();
        setPhase(GamePhase.INITIAL);
        gridDimensions = new Dimension();
        shipGrid = new ShipGrid();
        shotGrid = new ShotGrid();
        setGridDimensions(new Dimension(4, 4));
        shipGrid.setDimensions(gridDimensions.width, gridDimensions.height);
        shotGrid.setDimensions(gridDimensions.width, gridDimensions.height);
    }

    /**
     * generate the ship stack used for placing the ships
     */
    private void generateShipStack()
    {
        shipStack = new Stack<Ship>();

        for (int i = 0; i < maxSubmarines; ++i)
        {
            Submarine s = new Submarine();
            addShipToStack(s);
        }

        for (int i = 0; i < maxDestroyers; ++i)
        {
            Destroyer d = new Destroyer();
            addShipToStack(d);
        }

        for (int i = 0; i < maxCruisers; ++i)
        {
            Cruiser c = new Cruiser();
            addShipToStack(c);
        }


        for (int i = 0; i < maxBattleShips; ++i)
        {
            Battleship b = new Battleship();
            addShipToStack(b);
        }


    }

    /**
     * add a ship to the stack
     * @param b
     */
    private void addShipToStack(Ship b)
    {
        if(getPhase() == GamePhase.SELECTION_PLAYER1)
        {
            b.setOwner(Owner.PLAYER1);
        }
        else if (getPhase() == GamePhase.SELECTION_PLAYER2)
        {
            b.setOwner(Owner.PLAYER2);
        }
        else
            throw new IllegalStateException();
        shipStack.add(b);
    }

    /**
     * sets the selected coord
     * effects depend on state of the game
     * @param point
     */
    public void setSelectedCoord(Point point)
    {
        switch (getPhase())
        {
            case INITIAL:
                break;
            case SELECTION_PLAYER1:
                // TODO orientation
                if(!shipStack.empty())
                {
                    if(isWithinPlayerBoundaries(point))
                    {
                        updateSelectionPhaseViewData();
                        firePropertyChange(SceneController.AICPU_UPDATE_PROPERTY, null, removeOppositePlayerShipsFromArray(shipGrid.getCharArray()));
                        break;
                    }
                }
                addShipToPosition(point);
                firePropertyChange(SceneController.AICPU_UPDATE_PROPERTY, null, removeOppositePlayerShipsFromArray(shipGrid.getCharArray()));
                break;
            case SELECTION_PLAYER2:
                if(!shipStack.empty())
                {
                    if(isWithinPlayerBoundaries(point))
                    {
                        updateSelectionPhaseViewData();
                        firePropertyChange(SceneController.AICPU_UPDATE_PROPERTY, null, removeOppositePlayerShipsFromArray(shipGrid.getCharArray()));
                        break;
                    }
                }
                addShipToPosition(point);
                firePropertyChange(SceneController.AICPU_UPDATE_PROPERTY, null, removeOppositePlayerShipsFromArray(shipGrid.getCharArray()));
                break;
            case SHOTTURN_PLAYER1:
                attemptToFireShot(point);
                firePropertyChange(SceneController.AICPU_UPDATE_PROPERTY, null, generateCharArray());
                break;
            case SHOTTURN_PLAYER2:
                attemptToFireShot(point);
                firePropertyChange(SceneController.AICPU_UPDATE_PROPERTY, null, generateCharArray());
                break;
            case GAMEEND:
                break;
            default:
                break;
        }
    }

    /**
     * update the selection char[]
     */
    private void updateSelectionPhaseViewData()
    {
        firePropertyChange(SceneController.SHIP_GRID_PROPERTY, removeOppositePlayerShipsFromArray(shipGrid.getCharArray()), removeOppositePlayerShipsFromArray(shipGrid.getCharArray()));
    }

    /**
     * attempts to add the shot to the shot grid
     * @param point
     */
    private void attemptToFireShot(Point point)
    {
        char[] oldCharArray = generateCharArray();
        //TODO remove prints, change to messages
        if (isWithinPlayerBoundaries(point))
        {
            if(shotGrid.willObjectFit(point.x, point.y, new Shot(ShotStatus.HIT)))
            {
                if(((Shot)((Object[][])shotGrid.getGrid())[point.x][point.y]) != null)
                {
                    System.out.println("This Grid has already been fired on");
                }
                else
                if(shipGrid.getCharArray()[point.x + (point.y * getGridDimensions().width)] != '\u0000')
                {
                    shotGrid.addObject(point.x, point.y, new Shot(ShotStatus.HIT));
                    System.out.println("hit");
                    if(getPhase() == GamePhase.SHOTTURN_PLAYER1)
                    {
                        playerScores[0] += 1;
                        if(playerScores[0] == winningScore)
                        {
                            setPhase(GamePhase.GAMEEND);
                            setStatusText("Player 1 Wins");
                            setAlertMessage("Player 1 Wins");
                            setStatusText("Press New Game or Load game to play again");
                        }
                        else
                        {
                            setPhase(GamePhase.values()[this.getPhase().ordinal() + 1]);
                            if(getPlayerMode() == PlayerMode.PLAYER_VS_PLAYER)
                                setStatusText("player 2's Turn" );
                        }
                    }
                    else if(getPhase() == GamePhase.SHOTTURN_PLAYER2)
                    {
                        playerScores[1] += 1;
                        if(playerScores[1] == winningScore)
                        {
                            setPhase(GamePhase.GAMEEND);
                            setStatusText("Player 2 Wins");
                            setAlertMessage("Player 2 Wins");
                            setStatusText("Press New Game or Load game to play again");
                        }
                        else
                        {
                            setPhase(GamePhase.values()[this.getPhase().ordinal() - 1]);
                            setStatusText("player 1's Turn" );
                        }
                    }

                }
                else
                {
                    shotGrid.addObject(point.x, point.y, new Shot(ShotStatus.MISS));
                    System.out.println("miss");
                    if(getPhase() == GamePhase.SHOTTURN_PLAYER1)
                        setPhase(GamePhase.values()[this.getPhase().ordinal() + 1]);
                    else if(getPhase() == GamePhase.SHOTTURN_PLAYER2)
                        setPhase(GamePhase.values()[this.getPhase().ordinal() - 1]);

                    if(getPlayerMode() == PlayerMode.PLAYER_VS_PLAYER)
                        setStatusText("player " + (this.getPhase().ordinal() - 2) + " 's Turn" );
                }
            }
            else
            {
                // not within bounds
            }
            firePropertyChange(SceneController.SHIP_GRID_PROPERTY, oldCharArray, generateCharArray());

        }
    }

    /**
     * on load initialisation
     */
    public void onLoad()
    {
        firePropertyChange(SceneController.SHIP_GRID_PROPERTY, "", generateCharArray());
        setStatusText(getStatusText());
        setAlertMessage(getSaveFileName() + " Successfully loaded");
        setStatusText("player " + (this.getPhase().ordinal() - 2) + " 's Turn" );
        firePropertyChange(SceneController.PLAYER_MODE_PROPERTY, this.playerMode, playerMode);
    }

    /**
     * overlay the shots on the ship grid
     * @param ships
     * @param shots
     * @return
     */
    private char[] overlayShots(char[] ships, char[] shots)
    {
        if (ships.length != shots.length)
        {
            //TODO fix
            //Throw an exception that blueJ doesn't support!!!!
            System.out.println("Lets just pretend there was an exception here");
            return new char[0];
        }
        char[] chars = new char[ships.length];

        for (int i = 0; i < ships.length; ++i)
        {
            if (shots[i] != '\u0000')
            {
                chars[i] = shots[i];
            }
            else
            {
                chars[i] = ships [i];
            }
        }

        return chars;
    }


    /**
     * add a ship to the point coordinate
     * @param point
     */
    private void addShipToPosition(Point point)
    {
        if(!shipStack.empty() && (getPhase() == GamePhase.SELECTION_PLAYER1 || getPhase() == GamePhase.SELECTION_PLAYER2))
        {
            char [] oldGrid = generateCharArray();
            shipStack.peek().setShipOrientation(this.getOrientationMode());
            if (this.getShipGrid().willObjectFit(point.x, point.y, shipStack.peek()))
            {

                shipGrid.addObject(point.x, point.y, shipStack.pop());
                firePropertyChange(SceneController.SHIP_GRID_PROPERTY, oldGrid, removeOppositePlayerShipsFromArray(shipGrid.getCharArray()));
                if(shipStack.empty())
                {
                    setStatusText("");
                    GamePhase oldPhase = Phase;

                    firePropertyChange(SceneController.SHIP_GRID_PROPERTY, oldGrid, generateCharArray());
                    setPhase(GamePhase.values()[Phase.ordinal() + 1]);

                    if(oldPhase == GamePhase.SELECTION_PLAYER1 && getPlayerMode() == PlayerMode.PLAYER_VS_PLAYER)
                        setAlertMessage("Player 2, please place your ships\nPlayer1 NO PEEKING!");
                    else if (oldPhase == GamePhase.SELECTION_PLAYER2)
                    {
                        Random random = new Random();
                        int player = random.nextInt(100000);

                        if((player % 2) == 0)
                        {
                            setPhase(GamePhase.SHOTTURN_PLAYER1);
                            setAlertMessage("Player 1 Takes the first shot");
                        }
                        else
                        {
                            setPhase(GamePhase.SHOTTURN_PLAYER2);
                            setAlertMessage("Player 2 Takes the first shot");
                        }
                    }
                }
                else
                    setStatusText("Player " + Phase.ordinal() + " Please Place a " + shipStack.peek().getClass().getSimpleName());
            }
            else
            {
                firePropertyChange(SceneController.SHIP_GRID_PROPERTY, oldGrid, generateCharArray());
            }

        }
    }

    /**
     * set Entered coord
     * effects depend on game state
     * @param point
     */
    public void setEnteredCoord(Point point)
    {
        switch (getPhase())
        {
            case INITIAL:
                break;
            case SELECTION_PLAYER1:
                selectionPreview(point);
                break;
            case SELECTION_PLAYER2:
                selectionPreview(point);
                break;
            case SHOTTURN_PLAYER1:
                break;
            case SHOTTURN_PLAYER2:
                break;
            case GAMEEND:
                break;
            default:
                break;
        }
    }

    private void selectionPreview(Point point)
    {
        if(!shipStack.empty())
        {
            if(isWithinPlayerBoundaries(point))
            {
                updateSelectionPhaseViewData();

                return;
            }
        }
        gridPositionPreview(point);
    }

    /**
     * is the coordinate within the players portion of the grid
     * @param point
     * @return
     */
    private boolean isWithinPlayerBoundaries(Point point)
    {
        switch (getPhase())
        {
            case SELECTION_PLAYER1:
                if (orientationMode == Orientation.HORIZONTAL)
                {
                    return (point.x + shipStack.peek().getDimensions().width) > (getGridDimensions().width /2);
                }
                else if (orientationMode == Orientation.VERTICAL)
                {
                    return (point.x + shipStack.peek().getDimensions().height) > (getGridDimensions().width /2);
                }
            case SELECTION_PLAYER2:
                return(point.x) < (getGridDimensions().width /2);
            case SHOTTURN_PLAYER1:
                return (point.x) >= (getGridDimensions().width /2);
            case SHOTTURN_PLAYER2:
                return(point.x) < (getGridDimensions().width /2);
            default:
                return false;
        }

    }

    private void gridPositionPreview(Point point)
    {
        if(!shipStack.empty())
        {
            shipStack.peek().setShipOrientation(getOrientationMode());
            if (this.getShipGrid().willObjectFit(point.x, point.y, shipStack.peek()))
            {
                char[] tempArray = genArrayPreview(point);
                firePropertyChange(SceneController.SHIP_GRID_PROPERTY, removeOppositePlayerShipsFromArray(shipGrid.getCharArray()), removeOppositePlayerShipsFromArray(tempArray));
            }
            else
            {
                updateSelectionPhaseViewData();
            }
        }
    }

    private char[] genArrayPreview(Point point)
    {
        char[] tempArray = shipGrid.getCharArray();

        if (getOrientationMode() == Orientation.HORIZONTAL)
        {
            for(int i = 0; i < shipStack.peek().getDimensions().width; ++i)
            {
                for(int ii = 0; ii < shipStack.peek().getDimensions().height; ++ii)
                {
                      tempArray[(point.x + i) + ((point.y + ii) * getGridDimensions().width)] = shipStack.peek().getShipSymbol();
                }
            }
        }
        else if (getOrientationMode() == Orientation.VERTICAL)
        {
            for(int i = 0; i < shipStack.peek().getDimensions().height; ++i)
            {
                for(int ii = 0; ii < shipStack.peek().getDimensions().width; ++ii)
                {
                    tempArray[(point.x + i) + ((point.y + ii) * getGridDimensions().width)] = shipStack.peek().getShipSymbol();
                }
            }
        }
        return tempArray;
    }

    public void setExitedCoord(Point point)
    {
        switch (getPhase())
        {
            case INITIAL:
                break;
            case SELECTION_PLAYER1:
                //firePropertyChange(SceneController.SHIP_GRID_PROPERTY, shipGrid.);
                break;
            case SELECTION_PLAYER2:
                break;
            case SHOTTURN_PLAYER1:
                break;
            case SHOTTURN_PLAYER2:
                break;
            case GAMEEND:
                break;
            default:
                break;
        }
    }


    /**
     * save the scenemodel class
     */
    public void save()
    {
        if(getPhase() == GamePhase.SHOTTURN_PLAYER1 || getPhase() == GamePhase.SHOTTURN_PLAYER2)
        {
            try
            {
                FileOutputStream fs = new FileOutputStream(getSaveFileName());
                ObjectOutputStream os = new ObjectOutputStream(fs);
                os.writeObject(this);
                os.close();
                setAlertMessage(getSaveFileName() + " Saved Successfully");
            }
            catch (IOException e)
            {
                e.printStackTrace();
                setAlertMessage("Exception, Unable to Save");
            }
        }
        else
        {

            setAlertMessage("Cannot Save, game not in progress");
        }

    }

    /**
     * save the scenemodel class
     * @param filename
     */
    public void save(String filename)
    {
        String oldFileName = getSaveFileName();
        setSaveFileName(filename);
        save();
        setSaveFileName(oldFileName);
    }

    /**
     * load a scenemodel class
     * @return  scenemodel
     */
    public static SceneModel load()
    {
        SceneModel s = new SceneModel();
        try
        {
            FileInputStream in = new FileInputStream(getSaveFileName());
            ObjectInputStream is = new ObjectInputStream(in);
            s = (SceneModel)is.readObject();
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return s;
    }

    /**
     * load a scenemodel class
     * @param filename
     * @return
     */
    public static SceneModel load(String filename)
    {
        String oldFileName = getSaveFileName();
        setSaveFileName(filename);
        SceneModel s = load();
        setSaveFileName(oldFileName);
        return s;
    }
}
