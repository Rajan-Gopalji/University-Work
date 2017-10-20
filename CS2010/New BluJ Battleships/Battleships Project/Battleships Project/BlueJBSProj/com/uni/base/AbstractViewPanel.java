package com.uni.base;

/**
 * User: dogmaan
 * Date: 06/06/12
 * Time: 18:25
 * This class is based on code obtained from http://www.oracle.com/technetwork/articles/javase/index-142890.html
 */

import javax.swing.*;
import java.beans.PropertyChangeEvent;

/**
 * This class provides the base level abstraction for views in this example. All
 * views that extend this class also extend JPanel (which is useful for performing
 * GUI manipulations on the view in NetBeans Matisse), as well as providing the
 * modelPropertyChange() method that controllers can use to propagate model
 * property changes.
 *
 * @author Robert Eckstein edited by dogmaan
 */

public abstract class AbstractViewPanel extends JPanel implements Observer
{

    /**
     * Called by the controller when it needs to pass along a property change
     * from a model.
     *
     * @param evt The property change event from the model
     */


    public abstract void modelPropertyChange(PropertyChangeEvent evt);


}
