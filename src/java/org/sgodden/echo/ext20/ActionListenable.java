package org.sgodden.echo.ext20;

import nextapp.echo.app.event.ActionListener;

/**
 * An interface to be implemented by objects which can have action listeners
 * attached to them.
 * @author sgodden
 */
public interface ActionListenable {
    
    /**
     * Adds the specified action listener.
     * @param listener
     */
    public void addActionListener(ActionListener listener);

}
