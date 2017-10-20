package com.uni.base;

import java.beans.PropertyChangeEvent;

/**
 * User: dogmaan
 * Date: 18/07/12
 * Time: 17:39
 */

/**
 * Allows subscribed classes to receive model property change events
 */
public interface Observer
{
    public abstract void modelPropertyChange(PropertyChangeEvent evt);
}
