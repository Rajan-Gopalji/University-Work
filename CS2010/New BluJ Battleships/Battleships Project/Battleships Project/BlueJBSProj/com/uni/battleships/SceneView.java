 
/**
 * Original Header:
 * SceneView:
 * 
 * @author Iain Martin 
 * @version 1.0
 * 
 * Notes to use SceneView
 *  BattleGuu is intended as a replacement for a Menu class for Battleships.
 *  Comments that start with BATTLEGUI mark where you might 
 *  add your own code. Please do not attempt to use this GUI until
 *  you have already met the minimum requirements of the project.
 * 
 * Notes:
 *  Event handlers have been set up for Menu Options
 *  NewGameDialog, LoadGame and Save Game.
 *  
 *  An Event handler has also been set up for a Mouse Click on
 *  the grid which calls fireShot(row, col).
 *  
 *  To add functionality to this GUI add you code to these functions
 *  which are at the end of this file. 
 *  
 *  Potential additions: FileChoosers could be implemented and the grid characters
 *  could be replaced with graphics by loading gifs or jpgs into the grid which is
 *  created from JButtons.
 */

/**
 * @author dogmaan
 * this is the main UI class based on a heavily modified version
 * supplied by dundee university
 */

package com.uni.battleships;

import com.uni.base.AbstractViewPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SceneView extends AbstractViewPanel  implements ActionListener
{
    // Default filename to use for saving and loading files
    // Possible improvement: replace with a FileChooser
    private final static String DEFAULT_FILENAME = "battlegui.txt";
    //private int GRID_SIZE = 6;
   // private Dimension dimensions = new Dimension(6, 6);
    private JPanel grid;
    private JFrame frame = new JFrame("Battleships");
    //private ShipSelector shipSelector;
    private JButton [] buttonArray; 
    private JLabel statusLabel = new JLabel("");
    private final Color statusLabelDefaultBackground;
    private int bg = 255;
    private final SceneController sceneController;

    public SceneView(SceneController sceneController)
    {
        setSystemLookAndFeel();
        this.sceneController = sceneController;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        GenerateFrameAndUI();
        frame.setJMenuBar(this.createMenu());
        // Display the window, setting the size
        frame.setSize(800, 600);
        frame.setVisible(true);
        statusLabel.setOpaque(true);
        //statusLabel.setBackground(new Color(234,238,224));
        statusLabel.setForeground(new Color(255,0,0));
        statusLabel.setBorder(new LineBorder(new Color(255, 0, 0), 1));
        statusLabelDefaultBackground = statusLabel.getBackground();
    }

    /**
     * sets the theme to the native system theme
     */
    private void setSystemLookAndFeel()
    {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }
    }

    /**
     * Creates the main menu
     * @return  JMenuBar
     */
    public JMenuBar createMenu()
    {
        JMenuBar menuBar  = new JMenuBar();
        JMenu menu = new JMenu("Battle Menu");
        JMenu subMenu = new JMenu("New Game");
        JMenuItem menuItem;
       
        menuBar.add(menu);

        // A group of JMenuItems. You can create other menu items here if desired

        JMenuItem singlePlayer = new JMenuItem("Single Player");
        singlePlayer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sceneController.setPlayerMode(PlayerMode.PLAYER_VS_CPU);
                sceneController.startGame();
            }
        });
        JMenuItem twoPlayer = new JMenuItem("Two Player");
        twoPlayer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sceneController.setPlayerMode(PlayerMode.PLAYER_VS_PLAYER);
                sceneController.startGame();
            }
        });
        subMenu.add(singlePlayer);
        subMenu.add(twoPlayer);
        menu.add(subMenu);

        menuItem = new JMenuItem("Load Game");
        menuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                LoadGame();
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Save Game");
        menuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SaveGame();
            }
        });
        menu.add(menuItem);

        //a submenu
        //menu.addSeparator();
        return menuBar;
    }

    /**
     * Creates the main grid content
     * @return  grid
     */
    public Container createContentPane() 
    {
        int numButtons = sceneController.getGridDimensions().width * sceneController.getGridDimensions().height;
        grid = new JPanel(new GridLayout(sceneController.getGridDimensions().height, sceneController.getGridDimensions().width));
        buttonArray = new JButton[numButtons];

        for (int y = 0; y < sceneController.getGridDimensions().height; ++y)
        {
            for (int x = 0; x < sceneController.getGridDimensions().width; ++x)
            {
                final int fx = x;
                final int fy = y;
                final int xy = x + (y * sceneController.getGridDimensions().width);
                Border bBorder;

                bBorder = genButtonArrayBorders(fx);
                buttonArray[xy] = new JButton("");
                buttonArray[xy].setBorder(bBorder);
                buttonArray[xy].setActionCommand(Integer.toString(xy));
                buttonArray[xy].addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        System.out.println("SceneView: Button x:" + fx + " y:" + fy + " pressed");
                        sceneController.gridButtonClicked(fx, fy);
                    }
                });

                buttonArray[xy].addMouseListener(new MouseListener()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                    }

                    @Override
                    public void mousePressed(MouseEvent e)
                    {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e)
                    {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e)
                    {
                        sceneController.gridButtonEntered(fx, fy);
                    }

                    @Override
                    public void mouseExited(MouseEvent e)
                    {
                        sceneController.gridButtonExited(fx, fy);
                    }
                });
                grid.add(buttonArray[xy]);
            }
        }
        return grid;
    }

    /**
     * generates the button border colours
     * @param fx
     * @return Border
     */
    private Border genButtonArrayBorders(int fx)
    {
        Border bBorder;
        if (fx < (sceneController.getGridDimensions().width / 2))
            bBorder = new LineBorder(Color.BLUE, 1);
        else
            bBorder = new LineBorder((Color.RED), 1);
        return bBorder;
    }

    /**
     * This method handles events from the Menu and the board.
     * This method is NOT USED
     */
    public void actionPerformed(ActionEvent e) 
    {
        String classname = getClassName(e.getSource());
        JComponent component = (JComponent)(e.getSource());
    
        if (classname.equals("JMenuItem"))
        {
            JMenuItem menusource = (JMenuItem)(e.getSource());
            String menutext  = menusource.getText();
            
            // Determine which menu option was chosen
            if (menutext.equals("Load Game"))
            {
                /* BATTLEGUI    Add your code here to handle Load Game **********/
                LoadGame();
            }
            else if (menutext.equals("Save Game"))
            {
                /* BATTLEGUI    Add your code here to handle Save Game **********/
                SaveGame();
            }
            else if (menutext.equals("New Game"))
            {
                /* BATTLEGUI    Add your code here to handle Save Game **********/

            }
        }
        // Handle the event from the user clicking on a command button
        else if (classname.equals("JButton"))
        {
            JButton button = (JButton)(e.getSource());
            int bnum = Integer.parseInt(button.getActionCommand());
            int row = bnum / sceneController.getGridDimensions().height;
            int col = bnum % sceneController.getGridDimensions().width;
                   
            /* BATTLEGUI    Add your code here to handle user clicking on the grid ***********/
            fireShot(row, col);
        }  
    }
    
    /**
     *  Returns the class name
     */
    protected String getClassName(Object o) 
    {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    /**
     * Create the GUI and show it.
     * For thread safety, this method should be invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI()
    {

    }
    
    /**
     * Sets a Gui grid square at row, col to display a character
     */
    public boolean setGuiSquare(int col, int row, char c)
    {
        int bnum = col + (sceneController.getGridDimensions().width * row);
        if (bnum >= (sceneController.getGridDimensions().width * sceneController.getGridDimensions().height ))
        {
            return false;
        }
        else
        {
            buttonArray[bnum].setText(Character.toString(c));
            grid.revalidate();
            grid.updateUI();

            frame.setContentPane(grid);
            frame.revalidate();
        }
        return true;
    }
    
    /**
     * This is a standard main function for a Java GUI
     */
    public static void startGUI()
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                createAndShowGUI();
            }
        });
    }
      


    /**
     * This method is called from the Menu event: Load Game.
     * Sceneview
     */
    public void LoadGame()
    {
        sceneController.loadGame("");
          System.out.println("Load game selected");
    }
    
    
    /**
     * This method is called from the Menu event: Save Game.
     * Sceneview
     */
    public void SaveGame()
    {
        sceneController.saveGame("");
          System.out.println("Save game selected");
    }
    
    /**
     * This method is called from the Mouse Click event.
     * Sceneview
     */
    public void fireShot(int row, int col)
    {
          System.out.println("Fire shot selected: at (" + row + ", " + col + ")");
    }

    /**
     * Any changes to the view model are sent here
     * any relevant changes are updated to the UI
     * @param evt The property change event from the model
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt)
    {

        String classname = getClassName(evt.getSource());
        //JComponent component = (JComponent)(evt.getSource());

        if(evt.getPropertyName().equals(SceneController.PLAYER_MODE_PROPERTY))
        {
             System.out.println("Player Mode Changed to " + (PlayerMode)evt.getNewValue());
        }
        else if (evt.getPropertyName().equals(SceneController.GRID_DIMENSIONS_PROPERTY))
        {

            //dimensions = (Dimension)evt.getNewValue();
            GenerateFrameAndUI();

        }
        else if (evt.getPropertyName().equals(SceneController.SHIP_GRID_PROPERTY))
        {
            int i = 0;
            for(char c : (char[])evt.getNewValue())
            {
                if (c == 'h')
                {
                    buttonArray[i].setText("Hit");
                }
                else if (c == 'm')
                {
                    buttonArray[i].setText("Miss");
                }
                else
                {
                    buttonArray[i].setText(Character.toString(c));
                }

                ++i;
            }
        }
        else if(evt.getPropertyName().equals(SceneController.ALERT_MESSAGE_PROPERTY))
        {
            JOptionPane.showMessageDialog(frame, (String)evt.getNewValue());
        }
        else if (evt.getPropertyName().equals(SceneController.STATUS_TEXT_PROPERTY))
        {
            bg = 255;
            statusLabel.setText((String)evt.getNewValue());
            new Timer(80, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                   if (bg <= statusLabelDefaultBackground.getRed())
                   {
                       bg = 0;
                       statusLabel.setBackground(statusLabelDefaultBackground);
                       ((Timer)e.getSource()).stop();
                   }
                   else
                   {
                       statusLabel.setBackground(new Color(bg, bg, bg));
                       //statusLabel.setForeground(new Color());
                       bg -= 1;
                   }

                }
            }).start();
            //Label n = new Label((String)evt.getNewValue());

            //GenerateFrameAndUI();
        }

        /*if (classname.equals("JButton"))
        {
            JButton button = (JButton)(evt.getSource());
            int bnum = Integer.parseInt(button.getActionCommand());
            int row = bnum / GRID_SIZE;
            int col = bnum % GRID_SIZE;

            // BATTLEGUI    Add your code here to handle user clicking on the grid
            fireShot(row, col);
        }        */
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Generates the User Interface
     */
    private void GenerateFrameAndUI()
    {
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        JButton orientation = new JButton("Toggle\nOrientation");
        orientation.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sceneController.toggleOrientationMode();
            }
        });
        JButton shotgrid = new JButton("Toggle\nShot Grid");
        grid = new JPanel();
        grid = (JPanel)createContentPane();
        grid.revalidate();
        grid.updateUI();

        bottomPanel.add(orientation);
        //bottomPanel.add(shotgrid);
        bottomPanel.add(statusLabel);

        //frame = new JFrame();
        frame.getContentPane().removeAll();
        frame.getContentPane().add(grid, BorderLayout.CENTER);
        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        frame.revalidate();
    }

    PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            modelPropertyChange(propertyChangeEvent);
        }
    };
}





