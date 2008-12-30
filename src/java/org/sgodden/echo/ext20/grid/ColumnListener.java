package org.sgodden.echo.ext20.grid;

import java.util.EventListener;

/**
 * Listener interface for receiving notification when the grid wants to
 * add or remove columns.
 * @author Lloyd Colling
 *
 */
public interface ColumnListener extends EventListener {

    /**
     * Notification that the grid wishes a column to be removed
     * @param e
     */
    public void removeColumn(RemoveColumnEvent e);
    
    /**
     * Notification that the grid wishes to add a column
     */
    public void addColumn();
}
