package org.sgodden.echo.ext20.fieldgroup;

import java.util.EventListener;

/**
 * A listener for events that occur on the FieldGroupContainer component.
 * 
 * @author Lloyd Colling
 * 
 */
public interface FieldGroupListener extends EventListener {

    /**
     * Notification that a field group is going to be added.
     * @param e
     */
    public void willAddFieldGroup(FieldGroupEvent e);
    
    /**
     * Notification that a field group is going to be removed
     * @param e
     */
    public void willRemoveFieldGroup(FieldGroupEvent e);
}
