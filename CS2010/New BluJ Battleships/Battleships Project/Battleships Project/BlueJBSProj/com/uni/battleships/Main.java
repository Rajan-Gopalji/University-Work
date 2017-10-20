package com.uni.battleships;

import java.awt.*;

/**
 * User: dogmaan
 * Date: 06/06/12
 * Time: 19:59
 */
public class Main
{
    public Main()
    {
        SceneModel sceneModel = new SceneModel();

        SceneController sceneController = new SceneController();
        sceneController.addModel(sceneModel);

        SceneView sceneView = new SceneView(sceneController);

        sceneController.addView(sceneView);


        sceneModel.initDefault();
        sceneController.changeGridDimensions(new Dimension(20, 10));
        //sceneView.setGuiSquare(0, 5, 'C');
    }

    public static void main(String args[])
    {
            Main main = new Main();
    }
}
